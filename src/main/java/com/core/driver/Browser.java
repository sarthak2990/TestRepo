package com.core.driver;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

public enum Browser {
    CHROME,
    FIREFOX;

    public static Browser getBrowserByName(String name) {
        return EnumSet.allOf(Browser.class).stream().
                filter(browsers -> StringUtils.equalsIgnoreCase(browsers.name(), name))
                .findFirst().orElseThrow(() -> new NoSuchFieldError(String.format("No [%s] browser found", name)));
    }
}
