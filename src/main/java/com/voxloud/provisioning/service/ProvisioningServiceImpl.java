package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.DeviceNotFoundException;
import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.strategy.configwriter.ConfigurationModelWritingStrategy;
import com.voxloud.provisioning.strategy.configwriter.ConfigurationWriter;
import com.voxloud.provisioning.strategy.fragmentparser.FragmentParser;
import com.voxloud.provisioning.strategy.fragmentparser.FragmentParsingStrategy;
import com.voxloud.provisioning.strategy.modelupdater.ConfigurationModelUpdater;
import com.voxloud.provisioning.strategy.modelupdater.ConfigurationModelUpdatingStrategy;
import com.voxloud.provisioning.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProvisioningServiceImpl implements ProvisioningService {
    private final DeviceRepository deviceRepository;
    private final FragmentParsingStrategy fragmentParsingStrategy;
    private final ConfigurationModelUpdatingStrategy configurationModelUpdatingStrategy;
    private final ConfigurationModelWritingStrategy configWriterStrategy;
    private final ConfigurationModel configurationModel;

    @Override
    public String getProvisioningFile(String macAddress) {
        Device device = deviceRepository.findById(macAddress)
                .orElseThrow(() -> new DeviceNotFoundException(Utils.REQUEST_DENIED));
        updateConfigurationModel(device, configurationModel);
        ConfigurationWriter writer = configWriterStrategy.getWriter(device, configurationModel);
        return writer.write();
    }

    /**
     * Updates {@code ConfigurationModel} for specified device.
     *
     * @param device device to which configuration will be updated.
     * @param model  configuration model to be updated.
     */
    private void updateConfigurationModel(Device device, ConfigurationModel model) {
        model.setUsername(device.getUsername());
        model.setPassword(device.getPassword());
        if (device.getOverrideFragment() == null) {
            return;
        }
        FragmentParser parser = fragmentParsingStrategy.getParser(device);
        ConfigurationModelUpdater updater =
                configurationModelUpdatingStrategy.getModelUpdater(device, model);
        updater.setData(parser.parseFragment())
                .updateModel();
    }
}
