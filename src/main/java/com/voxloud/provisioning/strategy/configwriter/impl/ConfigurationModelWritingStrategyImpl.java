package com.voxloud.provisioning.strategy.configwriter.impl;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.UnknownDeviceModelException;
import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.strategy.configwriter.ConfigurationModelWritingStrategy;
import com.voxloud.provisioning.strategy.configwriter.ConfigurationWriter;
import com.voxloud.provisioning.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * The {@code ConfigurationModelWritingStrategyImpl} provides {@code ConfigurationWriter}
 * for the specified device type.
 */
@Component
@Log4j2
public class ConfigurationModelWritingStrategyImpl implements ConfigurationModelWritingStrategy {

    /**
     * creates {@code ConfigurationWriter} for the specified device type.
     *
     * @param device             device to which writer will be created.
     * @param configurationModel model containing configuration.
     * @return {@code ConfigurationWriter} for the specified device.
     */
    @Override
    public ConfigurationWriter getWriter(Device device, ConfigurationModel configurationModel) {
        switch (device.getModel()) {
            case CONFERENCE:
                return getConferenceConfigurationWriter(configurationModel);
            case DESK:
                return getDeskConfigurationWriter(configurationModel);
            default:
                log.error(Utils.UNKNOWN_DEVICE_MODEL);
                throw new UnknownDeviceModelException(Utils.UNKNOWN_DEVICE_MODEL);
        }
    }

    private ConfigurationWriter getDeskConfigurationWriter(ConfigurationModel configurationModel) {
        return DeskConfigurationWriter.of(configurationModel);
    }

    private ConfigurationWriter getConferenceConfigurationWriter(
            ConfigurationModel configurationModel) {
        return ConferenceConfigurationWriter.of(configurationModel);
    }
}
