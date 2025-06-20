package com.freedomua.vpn.freedomuavpnbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "outline")
public class OutlineApiProperties {
    private String serverApiUrl;
    private String serverId;
    private String serverLocation;
    private boolean serverEnabled;
}
