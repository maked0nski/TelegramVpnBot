package com.freedomua.vpn.freedomuavpnbot.client;

import com.freedomua.vpn.freedomuavpnbot.config.OutlineApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OutlineApiClient {

    private final RestTemplate insecureRestTemplate;
    private final OutlineApiProperties outlineApiProperties;

    public ResponseEntity<String> getServerApiUrl() {
        String url = outlineApiProperties.getServerApiUrl() + "/server";
        return insecureRestTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }
}
