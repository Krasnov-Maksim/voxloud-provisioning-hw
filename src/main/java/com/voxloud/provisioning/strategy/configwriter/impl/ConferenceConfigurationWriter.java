package com.voxloud.provisioning.strategy.configwriter.impl;

import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.CODECS;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.DOMAIN;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.PASSWORD;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.PORT;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.TIMEOUT;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.USERNAME;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.util.Utils;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

/**
 * The {@code ConferenceConfigurationWriter} writes configuration for device type "Conference".
 */
@Log4j2
public class ConferenceConfigurationWriter extends AbstractConfigurationWriter {
    private ConferenceConfigurationWriter(ConfigurationModel model) {
        super(model);
    }

    public static ConferenceConfigurationWriter of(ConfigurationModel configurationModel) {
        return new ConferenceConfigurationWriter(configurationModel);
    }

    /**
     * Writes configuration as JSON string.
     *
     * @return String formatted as JSON.
     */
    @Override
    public String write() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule module = new SimpleModule();
        module.addSerializer(ConfigurationModel.class, new ConfigurationModelToJsonSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(ConfigurationModel.copyOf(getModel()));
        } catch (JsonProcessingException e) {
            log.error(Utils.JSON_PARSING_ERROR);
            throw new RuntimeException(e);
        }
    }

    private static class ConfigurationModelToJsonSerializer
            extends StdSerializer<ConfigurationModel> {
        protected ConfigurationModelToJsonSerializer(Class<ConfigurationModel> t) {
            super(t);
        }

        public ConfigurationModelToJsonSerializer() {
            this(null);
        }

        @Override
        public void serialize(ConfigurationModel model, JsonGenerator gen,
                SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField(USERNAME.getFieldName(), model.getUsername());
            gen.writeStringField(PASSWORD.getFieldName(), model.getPassword());
            gen.writeStringField(DOMAIN.getFieldName(), model.getDomain());
            gen.writeStringField(PORT.getFieldName(), model.getPort());

            String codecsString = model.getCodecs().stream().map(s -> "\"" + s + "\"")
                    .collect(Collectors.joining(",", "[", "]"));
            gen.writeFieldName(CODECS.getFieldName());
            gen.writeRawValue(codecsString);

            if (model.getTimeout() != null) {
                gen.writeNumberField(TIMEOUT.getFieldName(), model.getTimeout());
            }
            gen.writeEndObject();
        }
    }
}
