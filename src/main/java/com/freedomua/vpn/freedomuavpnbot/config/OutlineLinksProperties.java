package com.freedomua.vpn.freedomuavpnbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "outline-links")
public class OutlineLinksProperties {
    private String windows;
    private String macos;
    private String linux;
    private String chromeos;
    private String ios;
    private String android;
    private String apk;
}
