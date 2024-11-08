package com.voxloud.provisioning.strategy.fragmentparser.impl;

import static com.voxloud.provisioning.util.Utils.JSON_PARSING_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voxloud.provisioning.exception.FragmentParsingException;
import com.voxloud.provisioning.model.ConfigurationModel;
import lombok.extern.log4j.Log4j2;

/**
 * The {@code JsonFragmentParser} reads configuration fragment for device type "Conference".
 */
@Log4j2
public class JsonFragmentParser extends AbstractFragmentParser {
    private JsonFragmentParser(String fragment) {
        super(fragment);
    }

    public static JsonFragmentParser of(String fragment) {
        return new JsonFragmentParser(fragment);
    }

    /**
     * Reads configuration from JSON.
     *
     * @return configuration model
     */
    @SuppressWarnings("unchecked")
    @Override
    public ConfigurationModel parseFragment() {
        try {
            return new ObjectMapper().readValue(getFragment(), ConfigurationModel.class);
        } catch (JsonProcessingException e) {
            log.error(JSON_PARSING_ERROR);
            throw new FragmentParsingException(JSON_PARSING_ERROR);
        }
    }
}
