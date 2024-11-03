package com.voxloud.provisioning.controller;

import static com.voxloud.provisioning.util.Utils.MAC_ADDRESS_REGEXP;
import static com.voxloud.provisioning.util.Utils.REQUEST_DENIED;

import com.voxloud.provisioning.exception.DeviceNotFoundException;
import com.voxloud.provisioning.service.ProvisioningService;
import java.util.regex.Pattern;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProvisioningController {
    private final ProvisioningService provisioningService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/provisioning/{macAddress}")
    public String getProvisioning(@PathVariable @Valid String macAddress) {
        if (!isValidMacAddress(macAddress)) {
            throw new DeviceNotFoundException(REQUEST_DENIED);
        }
        return provisioningService.getProvisioningFile(macAddress);
    }

    private boolean isValidMacAddress(String macAddress) {
        return Pattern.compile(MAC_ADDRESS_REGEXP)
                .matcher(macAddress)
                .matches();
    }
}
