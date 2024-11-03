package com.voxloud.provisioning.strategy.modelupdater;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.model.ConfigurationModel;

/**
 * {@code ConfigurationModelUpdatingStrategy} provides {@code ConfigurationModelUpdater} for device.
 */
public interface ConfigurationModelUpdatingStrategy {
    ConfigurationModelUpdater getModelUpdater(Device device, ConfigurationModel model);
}
