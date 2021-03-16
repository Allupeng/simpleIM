package com.orange.simpleimserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "simpleim-server")
public class SimpleIMServerConfig {
    @Getter
    @Setter
    private int port ;

}
