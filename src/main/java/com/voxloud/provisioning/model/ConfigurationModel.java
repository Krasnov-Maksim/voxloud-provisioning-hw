package com.voxloud.provisioning.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class ConfigurationModel {
    private String username;
    private String password;
    @Value("${provisioning.domain}")
    private String domain;
    @Value("${provisioning.port}")
    private String port;
    @Value("${provisioning.codecs}")
    private List<String> codecs;
    private Integer timeout;

    public static ConfigurationModel copyOf(ConfigurationModel model) {
        ConfigurationModel copy = new ConfigurationModel();
        copy.username = model.getUsername();
        copy.password = model.getPassword();
        copy.domain = model.getDomain();
        copy.port = model.getPort();
        copy.codecs = new ArrayList<>();
        copy.codecs.addAll(model.getCodecs());

        if (model.getTimeout() != null) {
            copy.timeout = model.getTimeout();
        }
        return copy;
    }

    public enum FieldNames {
        USERNAME("username"),
        PASSWORD("password"),
        DOMAIN("domain"),
        PORT("port"),
        CODECS("codecs"),
        TIMEOUT("timeout");

        FieldNames(String fieldName) {
            this.fieldName = fieldName;
        }

        @Getter
        private final String fieldName;
    }
}
