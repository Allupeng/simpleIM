package com.oneorange.simpleimclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "simpleim-client")
public class SimpleIMClientConfig {
    @Getter
    @Setter
    private int port;//端口号

    @Getter
    @Setter
    private String hostname;//主机名
}
