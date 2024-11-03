package com.voxloud.provisioning.service;

import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.CODECS;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.DOMAIN;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.PASSWORD;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.PORT;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.TIMEOUT;
import static com.voxloud.provisioning.model.ConfigurationModel.FieldNames.USERNAME;
import static com.voxloud.provisioning.util.TestUtils.DESK_OVERRIDE_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.EXPECTED_DESK_WITH_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_DESK_WITH_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_NOT_VALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.DeviceNotFoundException;
import com.voxloud.provisioning.model.ConfigurationModel;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.strategy.configwriter.ConfigurationModelWritingStrategy;
import com.voxloud.provisioning.strategy.configwriter.impl.DeskConfigurationWriter;
import com.voxloud.provisioning.strategy.fragmentparser.FragmentParsingStrategy;
import com.voxloud.provisioning.strategy.fragmentparser.impl.PropertyFragmentParser;
import com.voxloud.provisioning.strategy.modelupdater.ConfigurationModelUpdatingStrategy;
import com.voxloud.provisioning.strategy.modelupdater.impl.DeskDeviceConfigurationModelUpdater;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProvisioningServiceTest {
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private FragmentParsingStrategy fragmentParsingStrategy;
    @Mock
    private ConfigurationModelUpdatingStrategy configurationModelUpdatingStrategy;
    @Mock
    private ConfigurationModelWritingStrategy configurationModelWritingStrategy;
    @Mock
    private ConfigurationModel configurationModel;
    @InjectMocks
    private ProvisioningServiceImpl provisioningService;
    @Mock
    private Device device;

    @Test
    void getProvisioningFile_withNotValidMacAddress_throwDeviceNotFoundException() {
        when(deviceRepository.findById(MAC_ADDRESS_NOT_VALID))
                .thenThrow(DeviceNotFoundException.class);
        assertThrows(DeviceNotFoundException.class,
                () -> deviceRepository.findById(MAC_ADDRESS_NOT_VALID));
    }

    @Test
    void getProvisioningFile_withValidMacAddressAndDeskWithFragment_returnCorrectString() {
        doReturn(Optional.of(device))
                .when(deviceRepository)
                .findById(anyString());

        DeskConfigurationWriter writerMock = Mockito.mock(DeskConfigurationWriter.class);
        doReturn(writerMock)
                .when(configurationModelWritingStrategy)
                .getWriter(device, configurationModel);

        doReturn(EXPECTED_DESK_WITH_FRAGMENT).when(writerMock).write();

        String actual = provisioningService.getProvisioningFile(MAC_ADDRESS_DESK_WITH_FRAGMENT);

        assertThat(actual).isEqualTo(EXPECTED_DESK_WITH_FRAGMENT);
    }

    @Test
    void updateConfigurationModel_withDeskAndFragment_updateModel() {
        ReflectionTestUtils.setField(device, "overrideFragment", DESK_OVERRIDE_FRAGMENT);

        doReturn("walter").when(device).getUsername();
        doReturn("white").when(device).getPassword();
        doReturn(DESK_OVERRIDE_FRAGMENT).when(device).getOverrideFragment();

        doReturn("walter").when(configurationModel).getUsername();
        doReturn("white").when(configurationModel).getPassword();
        doReturn("sip.anotherdomain.com").when(configurationModel).getDomain();
        doReturn("5161").when(configurationModel).getPort();
        doReturn(createCodecList()).when(configurationModel).getCodecs();
        doReturn(10).when(configurationModel).getTimeout();

        doAnswer((Answer<Void>) invocation -> {
            ReflectionTestUtils.setField(configurationModel, USERNAME.getFieldName(), "walter");
            return null;
        })
                .when(configurationModel)
                .setUsername(anyString());

        doAnswer((Answer<Void>) invocation -> {
            ReflectionTestUtils.setField(configurationModel, PASSWORD.getFieldName(), "white");
            return null;
        })
                .when(configurationModel)
                .setPassword(anyString());

        PropertyFragmentParser parser = Mockito.mock(PropertyFragmentParser.class);
        doReturn(parser)
                .when(fragmentParsingStrategy)
                .getParser(device);

        Properties properties = Mockito.mock(Properties.class);
        doReturn(properties)
                .when(parser)
                .parseFragment();

        DeskDeviceConfigurationModelUpdater updater =
                Mockito.mock(DeskDeviceConfigurationModelUpdater.class);
        doReturn(updater)
                .when(configurationModelUpdatingStrategy)
                .getModelUpdater(device, configurationModel);

        doReturn(updater)
                .when(updater)
                .setData(properties);

        doAnswer((Answer<Void>) invocation -> {
            ArrayList<String> codecsList = createCodecList();
            ReflectionTestUtils.setField(configurationModel, DOMAIN.getFieldName(),
                    "sip.anotherdomain.com");
            ReflectionTestUtils.setField(configurationModel, PORT.getFieldName(), "5161");
            ReflectionTestUtils.setField(configurationModel, CODECS.getFieldName(), codecsList);
            ReflectionTestUtils.setField(configurationModel, TIMEOUT.getFieldName(), 10);

            return null;
        })
                .when(updater)
                .updateModel();

        ReflectionTestUtils.invokeMethod(provisioningService, "updateConfigurationModel",
                device, configurationModel);

        String actual = DeskConfigurationWriter.of(configurationModel).write();
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(EXPECTED_DESK_WITH_FRAGMENT);
    }

    private ArrayList<String> createCodecList() {
        ArrayList<String> codecsList = new ArrayList<>();
        codecsList.add("G711");
        codecsList.add("G729");
        codecsList.add("OPUS");
        return codecsList;
    }
}
