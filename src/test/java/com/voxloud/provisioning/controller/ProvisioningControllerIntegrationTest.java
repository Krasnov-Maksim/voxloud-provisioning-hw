package com.voxloud.provisioning.controller;

import static com.voxloud.provisioning.util.TestUtils.EXPECTED_CONFERENCE_WITHOUT_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.EXPECTED_CONFERENCE_WITH_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.EXPECTED_DESK_WITHOUT_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.EXPECTED_DESK_WITH_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_CONFERENCE_WITHOUT_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_CONFERENCE_WITH_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_DESK_WITHOUT_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_DESK_WITH_FRAGMENT;
import static com.voxloud.provisioning.util.TestUtils.MAC_ADDRESS_NOT_VALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.voxloud.provisioning.util.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProvisioningControllerIntegrationTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext applicationContext;

    @BeforeAll
    void beforeAll() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
    }

    @Test
    void getProvisioning_withNotValidMacAddress_returnError()
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/v1/provisioning/{macAddress}", MAC_ADDRESS_NOT_VALID)
                )
                .andExpect(status().isForbidden())
                .andReturn();

        String actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).contains(Utils.REQUEST_DENIED);
    }

    @Test
    void getProvisioning_withValidMacAddressAndDeskWithoutFragment_returnCorrectString()
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/v1/provisioning/{macAddress}",
                                MAC_ADDRESS_DESK_WITHOUT_FRAGMENT)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).isEqualTo(EXPECTED_DESK_WITHOUT_FRAGMENT);
    }

    @Test
    void getProvisioning_withValidMacAddressAndDeskWithFragment_returnCorrectString()
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/v1/provisioning/{macAddress}",
                                MAC_ADDRESS_DESK_WITH_FRAGMENT)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).isEqualTo(EXPECTED_DESK_WITH_FRAGMENT);
    }

    @Test
    void getProvisioning_withValidMacAddressAndConferenceWithoutFragment_returnCorrectString()
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/v1/provisioning/{macAddress}",
                                MAC_ADDRESS_CONFERENCE_WITHOUT_FRAGMENT)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).isEqualTo(EXPECTED_CONFERENCE_WITHOUT_FRAGMENT);
    }

    @Test
    void getProvisioning_withValidMacAddressAndConferenceWithFragment_returnCorrectString()
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/v1/provisioning/{macAddress}",
                                MAC_ADDRESS_CONFERENCE_WITH_FRAGMENT)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).isEqualTo(EXPECTED_CONFERENCE_WITH_FRAGMENT);
    }
}
