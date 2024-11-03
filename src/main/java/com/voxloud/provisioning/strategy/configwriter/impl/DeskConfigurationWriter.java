package com.voxloud.provisioning.strategy.configwriter.impl;

import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.CODECS;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.DOMAIN;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.PASSWORD;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.PORT;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.TIMEOUT;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.USERNAME;
import static java.lang.System.lineSeparator;

import com.voxloud.provisioning.model.ConfigurationModel;

/**
 * The {@code DeskConfigurationWriter}  writes configuration for device type "Desk".
 */
public class DeskConfigurationWriter extends AbstractConfigurationWriter {
    private DeskConfigurationWriter(ConfigurationModel configurationModel) {
        super(configurationModel);
    }

    public static DeskConfigurationWriter of(ConfigurationModel configurationModel) {
        return new DeskConfigurationWriter(configurationModel);
    }

    /**
     * Writes configuration as {@code java.util.Properties} string.
     *
     * @return String formatted as Properties.
     */
    @Override
    public String write() {
        StringBuilder builder = new StringBuilder();
        final ConfigurationModel model = getModel();
        final String eol = lineSeparator();
        if (model.getUsername() != null) {
            builder.append(USERNAME.getFieldName()).append("=")
                    .append(model.getUsername()).append(eol);
        }
        if (model.getPassword() != null) {
            builder.append(PASSWORD.getFieldName()).append("=")
                    .append(model.getPassword()).append(eol);
        }
        if (model.getDomain() != null) {
            builder.append(DOMAIN.getFieldName()).append("=")
                    .append(model.getDomain()).append(eol);
        }
        if (model.getPort() != null) {
            builder.append(PORT.getFieldName()).append("=")
                    .append(model.getPort()).append(eol);
        }
        if (model.getCodecs() != null) {
            builder.append(CODECS.getFieldName()).append("=")
                    .append(String.join(",", model.getCodecs()));
            if (model.getTimeout() != null) {
                builder.append(eol);
            }
        }
        if (model.getTimeout() != null) {
            builder.append(TIMEOUT.getFieldName()).append("=").append(model.getTimeout());
        }
        return builder.toString();
    }
}
