package com.EstateCrawler.app.database;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DataBase {
  @Getter private static final SessionFactory factory;

  static {
    Configuration config =
        new Configuration()
            .configure()
            .setProperty(
                "hibernate.connection.url",
                String.format(
                    "jdbc:postgresql://%s:%s/%s",
                    System.getenv("POSTGRES_HOST"),
                    System.getenv("POSTGRES_PORT"),
                    System.getenv("POSTGRES_DB")))
            .setProperty("hibernate.connection.username", System.getenv("POSTGRES_USER"))
            .setProperty("hibernate.connection.password", System.getenv("POSTGRES_PASSWORD"))
            .setProperty("hibernate.show_sql", System.getenv("SHOW_SQL"));
    try {
      factory = config.configure().buildSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("There is issue in hibernate util");
    }
  }

  public static void shutdown() {
    if (factory != null && !factory.isClosed()) {
      factory.close();
    }
  }
}
