package com.domain.helpers;

import com.typesafe.config.ConfigFactory;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AppParametersHelper {

    private static final String APP_PARAMETERS_FILE_NAME = "application.properties";

    @NoArgsConstructor(access = PRIVATE)
    public static class AutomationPractice {
        private static String getPathFromAppConfig(String key) {
            return ConfigFactory.load(APP_PARAMETERS_FILE_NAME).getString(key);
        }

        public static String getBaseUrl() {
            String baseUrl = "baseUrl";
            return System.getProperty(baseUrl, getPathFromAppConfig(baseUrl));
        }
    }
}
