package com.spiso4nik.productservice.service.perecrestok;

import com.spiso4nik.productservice.service.Authentication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuthenticationPerecrestok implements Authentication<MultiValueMap<String, String>> {

    private final WebClient.Builder webClient;

    public AuthenticationPerecrestok(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public MultiValueMap<String, String> getAuthenticationFromStore() {
        String strUrl = "https://www.perekrestok.ru/";
        var sessionCookie = webClient.build()
                .get()
                .uri(strUrl)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .blockOptional()
                .orElseThrow()
                .cookies().get("session").get(0).getValue();

        List<String> tokens = Arrays.stream(sessionCookie.replace("j%3A%7B%22accessToken%22%3A%22", "!!!!!")
                        .replace("%22%2C%22refreshToken%22%3A%22", "!!!!!")
                        .split("!!!!!"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("accessToken", List.of(tokens.get(0)));
        multiValueMap.put("refreshToken", List.of(tokens.get(1)));

        return multiValueMap;
    }
}
