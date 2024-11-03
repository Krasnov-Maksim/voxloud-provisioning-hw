package com.voxloud.provisioning.strategy.configwriter.impl;

import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.strategy.configwriter.ConfigurationWriter;
import lombok.Getter;

public abstract class AbstractConfigurationWriter implements ConfigurationWriter {
    @Getter
    private final ConfigurationModel model;

    protected AbstractConfigurationWriter(ConfigurationModel model) {
        this.model = model;
    }
}
