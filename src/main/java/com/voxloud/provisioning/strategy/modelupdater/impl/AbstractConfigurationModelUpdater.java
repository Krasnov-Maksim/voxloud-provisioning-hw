package com.voxloud.provisioning.strategy.modelupdater.impl;

import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.strategy.modelupdater.ConfigurationModelUpdater;
import lombok.Getter;

public abstract class AbstractConfigurationModelUpdater implements ConfigurationModelUpdater {
    @Getter
    private final ConfigurationModel model;

    protected AbstractConfigurationModelUpdater(ConfigurationModel model) {
        this.model = model;
    }

    @Override
    public void updateModel() {

    }
}
