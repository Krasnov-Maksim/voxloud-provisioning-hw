package com.voxloud.provisioning.strategy.modelupdater.impl;

import com.voxloud.provisioning.exception.IncompatibleTypeOfDataException;
import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.util.Utils;
import lombok.extern.log4j.Log4j2;

/**
 * The {@code ConferenceDeviceConfigurationModelUpdater} updates configuration for device
 * type "Conference".
 */
@Log4j2
public class ConferenceDeviceConfigurationModelUpdater extends AbstractConfigurationModelUpdater {
    private ConfigurationModel source = new ConfigurationModel();

    private ConferenceDeviceConfigurationModelUpdater(ConfigurationModel model) {
        super(model);
    }

    public static ConferenceDeviceConfigurationModelUpdater of(ConfigurationModel model) {
        return new ConferenceDeviceConfigurationModelUpdater(model);
    }

    /**
     * Updates {@code ConfigurationModel} using a temporary configuration created from a JSON.
     */
    @Override
    public void updateModel() {
        if (source.getUsername() != null) {
            getModel().setUsername(source.getUsername());
        }
        if (source.getPassword() != null) {
            getModel().setPassword(source.getPassword());
        }
        if (source.getDomain() != null) {
            getModel().setDomain(source.getDomain());
        }
        if (source.getPort() != null) {
            getModel().setPort(source.getPort());
        }
        if (source.getCodecs() != null) {
            getModel().setCodecs(source.getCodecs());
        }
        if (source.getTimeout() != null) {
            getModel().setTimeout(source.getTimeout());
        }
    }

    /**
     * Sets data to update the model
     *
     * @param data data of type {@code ConfigurationModel}
     */
    @Override
    public <T> ConferenceDeviceConfigurationModelUpdater setData(T data) {
        if (!(data instanceof ConfigurationModel)) {
            log.error(Utils.DATA_SHOULD_BE_OF_TYPE_CONFIGURATION_MODEL);
            throw new IncompatibleTypeOfDataException(
                    Utils.DATA_SHOULD_BE_OF_TYPE_CONFIGURATION_MODEL);
        }
        this.source = (ConfigurationModel) data;
        return this;
    }
}
