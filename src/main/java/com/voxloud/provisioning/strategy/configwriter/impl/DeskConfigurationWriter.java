package com.voxloud.provisioning.strategy.configwriter.impl;

import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.codecs;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.domain;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.password;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.port;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.timeout;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.username;
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
            builder.append(username.name()).append("=").append(model.getUsername()).append(eol);
        }
        if (model.getPassword() != null) {
            builder.append(password.name()).append("=").append(model.getPassword()).append(eol);
        }
        if (model.getDomain() != null) {
            builder.append(domain.name()).append("=").append(model.getDomain()).append(eol);
        }
        if (model.getPort() != null) {
            builder.append(port.name()).append("=").append(model.getPort()).append(eol);
        }
        if (model.getCodecs() != null) {
            builder.append(codecs.name()).append("=")
                    .append(String.join(",", model.getCodecs()));
            if (model.getTimeout() != null) {
                builder.append(eol);
            }
        }
        if (model.getTimeout() != null) {
            builder.append(timeout.name()).append("=").append(model.getTimeout());
        }
        return builder.toString();
    }
}
