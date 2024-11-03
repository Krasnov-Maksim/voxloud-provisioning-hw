package com.voxloud.provisioning.strategy.modelupdater;

/**
 * The {@code ConfigurationModelUpdater} updates configuration for device.
 */
public interface ConfigurationModelUpdater {
    void updateModel();

    <T> ConfigurationModelUpdater setData(T data);
}
