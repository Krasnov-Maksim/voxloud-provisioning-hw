package com.voxloud.provisioning.strategy.modelupdater.impl;

import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.codecs;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.domain;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.password;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.port;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.timeout;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.username;

import com.voxloud.provisioning.exception.IncompatibleTypeOfDataException;
import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;

/**
 * The {@code DeskDeviceConfigurationModelUpdater} updates configuration for device
 * type "Desk".
 */
@Log4j2
public class DeskDeviceConfigurationModelUpdater extends AbstractConfigurationModelUpdater {
    private Properties properties = new Properties();

    private DeskDeviceConfigurationModelUpdater(ConfigurationModel model) {
        super(model);
    }

    public static DeskDeviceConfigurationModelUpdater of(ConfigurationModel model) {
        return new DeskDeviceConfigurationModelUpdater(model);
    }

    /**
     * Updates {@code ConfigurationModel} using data from {@code java.util.Properties}
     */
    @Override
    public void updateModel() {
        properties.forEach((key, value) -> {
            if (username.name().equals(key)) {
                getModel().setUsername((String) value);
            } else if (password.name().equals(key)) {
                getModel().setPassword((String) value);
            } else if (domain.name().equals(key)) {
                getModel().setDomain((String) value);
            } else if (port.name().equals(key)) {
                getModel().setPort((String) value);
            } else if (codecs.name().equals(key)) {
                getModel().setCodecs(
                        new ArrayList<>(Arrays.asList(((String) value).split(","))));
            } else if (timeout.name().equals(key)) {
                getModel().setTimeout(Integer.valueOf((String) value));
            }
        });
    }

    /**
     * Sets data to update the model
     *
     * @param data data of type {@code Properties}
     */
    @Override
    public <T> DeskDeviceConfigurationModelUpdater setData(T data) {
        if (!(data instanceof Properties)) {
            log.error(Utils.DATA_SHOULD_BE_OF_TYPE_PROPERTIES);
            throw new IncompatibleTypeOfDataException(Utils.DATA_SHOULD_BE_OF_TYPE_PROPERTIES);
        }
        this.properties = (Properties) data;
        return this;
    }
}
