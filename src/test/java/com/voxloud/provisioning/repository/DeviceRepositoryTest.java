package com.voxloud.provisioning.repository;

import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_NOT_VALID;
import static org.assertj.core.api.Assertions.assertThat;

import com.voxloud.provisioning.entity.Device;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeviceRepositoryTest {
    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void findById_withValidMacAddress_returnDevice() {
        Optional<Device> optionalWithDevice = deviceRepository.findById("aa-bb-cc-dd-ee-ff");
        assertThat(optionalWithDevice).isNotEmpty();
        Device device = optionalWithDevice.get();
        assertThat(device.getUsername()).isNotNull();
    }

    @Test
    void findById_withNotValidMacAddress_returnEmptyOptional() {
        Optional<Device> optionalWithDevice = deviceRepository.findById(MAC_ADDRESS_NOT_VALID);
        assertThat(optionalWithDevice).isEmpty();
    }
}
