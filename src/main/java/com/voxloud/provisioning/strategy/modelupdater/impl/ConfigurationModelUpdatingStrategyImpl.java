package com.voxloud.provisioning.strategy.modelupdater.impl;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.UnknownDeviceModelException;
import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.strategy.modelupdater.ConfigurationModelUpdater;
import com.voxloud.provisioning.strategy.modelupdater.ConfigurationModelUpdatingStrategy;
import com.voxloud.provisioning.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * class {@code ConfigurationModelUpdatingStrategyImpl} provides {@code ConfigurationModelUpdater}
 * for the specified device type.
 */
@Component
@Log4j2
public class ConfigurationModelUpdatingStrategyImpl implements ConfigurationModelUpdatingStrategy {

    /**
     * creates {@code ConfigurationModelUpdater} for the specified device type.
     *
     * @param device device to which updater will be created.
     * @param model  model to be updated.
     * @return updater for {@code ConfigurationModel}
     */
    @Override
    public ConfigurationModelUpdater getModelUpdater(Device device, ConfigurationModel model) {
        switch (device.getModel()) {
            case CONFERENCE:
                return getConferenceModelUpdater(model);
            case DESK:
                return getDeskModelUpdater(model);
            default:
                log.error(Utils.UNKNOWN_DEVICE_MODEL);
                throw new UnknownDeviceModelException(Utils.UNKNOWN_DEVICE_MODEL);
        }
    }

    private ConfigurationModelUpdater getDeskModelUpdater(ConfigurationModel model) {
        return DeskDeviceConfigurationModelUpdater.of(model);
    }

    private ConfigurationModelUpdater getConferenceModelUpdater(ConfigurationModel model) {
        return ConferenceDeviceConfigurationModelUpdater.of(model);
    }
}
