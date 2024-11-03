package com.voxloud.provisioning.strategy.configwriter;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.model.ConfigurationModel;

/**
 * The {@code ConfigurationModelWritingStrategy} provides {@code ConfigurationWriter} for device.
 */
public interface ConfigurationModelWritingStrategy {
    ConfigurationWriter getWriter(Device device, ConfigurationModel configurationModel);
}
