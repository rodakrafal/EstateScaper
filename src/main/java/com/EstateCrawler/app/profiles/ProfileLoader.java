package com.EstateCrawler.app.profiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ProfileLoader {
    private static final String PROFILE_FILE_PATH = "src/main/resources/config/";

    public static Profile loadProfile(String profileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = PROFILE_FILE_PATH + profileName + ".json";
        return objectMapper.readValue(new File(filePath), Profile.class);
    }
}
