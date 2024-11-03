package com.voxloud.provisioning.strategy.fragmentparser.impl;

import com.voxloud.provisioning.util.Utils;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;

/**
 * The {@code PropertyFragmentParser} reads configuration fragment for device type "Desk".
 */
@Log4j2
public class PropertyFragmentParser extends AbstractFragmentParser {

    private PropertyFragmentParser(String fragment) {
        super(fragment);
    }

    public static PropertyFragmentParser of(String fragment) {
        return new PropertyFragmentParser(fragment);
    }

    /**
     * Reads configuration from string representing {@code Properties}.
     *
     * @return {@code Properties} object
     */
    @SuppressWarnings("unchecked")
    @Override
    public Properties parseFragment() {
        try {
            Properties properties = new Properties();
            properties.load(new StringReader(getFragment()));
            return properties;
        } catch (IOException e) {
            log.error(Utils.PROPERTIES_PARSING_ERROR);
            throw new RuntimeException(e);
        }
    }
}
