package com.EstateCrawler.app;

import com.EstateCrawler.app.estates.Estate;
import com.EstateCrawler.app.estates.EstateManager;
import com.EstateCrawler.app.database.DataBase;
import com.EstateCrawler.app.profiles.AvailableProfiles;
import com.EstateCrawler.app.profiles.Profile;
import com.EstateCrawler.app.profiles.ProfileLoader;
import com.EstateCrawler.app.shared.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Currency;

import static com.EstateCrawler.app.shared.Constant.*;

public class ApiCall {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(OLX_DATE_FORMAT);

  public static void main(String[] args) {
    String uniqueId = System.getenv("OTODOM_UNIQUE_ID");
    int currentPage = 1;
    int maxPages = 1;
    EstateManager estateManager = new EstateManager();

    try {
      Profile profile = ProfileLoader.loadProfile(AvailableProfiles.WARSAW.getProfileName());
      ObjectMapper mapper = new ObjectMapper();

      while (currentPage <= maxPages) {
        HttpURLConnection connection = getHttpURLConnection(uniqueId, profile, currentPage);

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {

          BufferedReader in =
              new BufferedReader(new InputStreamReader(connection.getInputStream()));
          String inputLine;
          StringBuilder response = new StringBuilder();

          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();

          JsonNode rootNode = mapper.readTree(response.toString()).path("pageProps");
          JsonNode pageCountNode = rootNode.path("tracking").path("listing").path("page_count");

          if (pageCountNode != null && pageCountNode.isInt()) {
            int pages = pageCountNode.asInt();
            if (maxPages < pages) {
              maxPages = pages;
              System.out.println("Updated maxPages: " + maxPages);
            }
          }

          JsonNode searchAdsItems = rootNode.path("data").path("searchAds").path("items");
          saveData(searchAdsItems, estateManager);

          JsonNode searchAdsPromotedItems =
              rootNode.path("data").path("searchAdsRandomPromoted").path("items");
          saveData(searchAdsPromotedItems, estateManager);

          currentPage++;
        } else {
          System.out.println("GET request failed. Response Code: " + responseCode);
        }
      }

    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    } finally {
      DataBase.shutdown();
    }
  }

  private static void saveData(JsonNode itemNode, EstateManager estateManager) {
    itemNode.forEach(
        item -> {
          Estate estate = createEstate(item);
          try {
            estateManager.insertOrUpdate(estate.toDTO());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          System.out.println(estate);
        });
  }

  private static Estate createEstate(JsonNode item) {
    String id = item.path("id").asText();
    String title = item.path("title").asText();
    Property property =
        getEnumValue(Property.class, item.path("estate").asText(), Property.UNKNOWN);
    String developmentUrl = item.path("developmentUrl").asText(null);
    String offerUrl = BASE_URL_OLX + OFFER_URL_SUFFIX + item.path("slug").asText();
    Location location = createLocation(item);
    Price price = createPrice(item.path("totalPrice"));
    Price rentPrice = createPrice(item.path("rentPrice"));
    Float areaInSquareMeters = item.path("areaInSquareMeters").floatValue();
    Floor floor = getEnumValue(Floor.class, item.path("floorNumber").asText(), Floor.UNKNOWN);
    Room room = getEnumValue(Room.class, item.path("roomsNumber").asText(), Room.UNKNOWN);
    LocalDateTime creationDate = parseDate(item.path("dateCreatedFirst"));
    String shortDescription = item.path("shortDescription").asText();

    return Estate.of(
        id,
        offerUrl,
        title,
        property,
        developmentUrl,
        location,
        price,
        rentPrice,
        areaInSquareMeters,
        floor,
        room,
        creationDate,
        shortDescription);
  }

  public static LocalDateTime parseDate(JsonNode item) {
    try {
      return LocalDateTime.parse(item.asText(), formatter);
    } catch (DateTimeParseException e) {
      System.err.println("Error parsing date: " + e.getMessage());
      return LocalDateTime.MIN;
    }
  }

  private static Location createLocation(JsonNode item) {
    JsonNode locationNode = item.path("location");
    JsonNode addressNode = locationNode.path("address");
    JsonNode streetNode = addressNode.path("street");

    String province = addressNode.path("province").path("name").asText();
    String city = addressNode.path("city").path("name").asText();
    String streetName = streetNode.path("name").asText();
    String streetNumber = streetNode.path("number").asText();

    JsonNode locationsFullName = locationNode.path("reverseGeocoding").path("locations");
    String district = null;
    String neighborhood = null;

    if (locationsFullName.isArray() && !locationsFullName.isEmpty()) {
      String fullName =
          locationsFullName.get(locationsFullName.size() - 1).path("fullName").asText();
      String[] locations = fullName.split(",");

      district = locations.length > 0 ? locations[0].trim() : null;
      neighborhood = locations.length > 1 ? locations[1].trim() : null;
    }

    return Location.of(province, city, streetName, streetNumber, district, neighborhood);
  }

  private static Price createPrice(JsonNode item) {
    if (!item.isEmpty() && !item.isMissingNode()) {
      float value = item.path("value").floatValue();
      String currencyCode = item.path("currency").asText();

      try {
        Currency currency = Currency.getInstance(currencyCode);
        return Price.of(value, currency.getCurrencyCode());
      } catch (IllegalArgumentException e) {
        System.err.println("Invalid currency code: " + currencyCode);
      }
    }
    return null;
  }

  private static <T extends Enum<T>> T getEnumValue(
      Class<T> enumClass, String value, T defaultValue) {
    try {
      return value != null ? Enum.valueOf(enumClass, value) : defaultValue;
    } catch (IllegalArgumentException e) {
      return defaultValue;
    }
  }

  private static HttpURLConnection getHttpURLConnection(
      String uniqueId, Profile profile, int currentPage) throws IOException {
    OtodomUrlBuilder otodomUrl = new OtodomUrlBuilder(uniqueId, profile);
    String stringURL = otodomUrl.buildUrl(currentPage);
    System.out.println("URL: " + stringURL);
    URL url = new URL(stringURL);

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:131.0) Gecko/20100101 Firefox/131.0");
    connection.setRequestProperty("Accept", "application/json");
    connection.setRequestProperty("Referer", BASE_URL_OLX);
    connection.setRequestProperty("x-nextjs-data", "1");

    return connection;
  }
}
