package com.aiapiclient.config;

import com.aiapiclient.client.OpenAiClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Auto-configuration for the OpenAI API client.
 *
 * <p>This configuration is activated when the property {@code openai.api-key} is present.
 * It creates a {@link RestClient} configured with the OpenAI base URL and authentication,
 * and an {@link OpenAiClient} bean that provides typed methods for interacting with the API.
 */
@AutoConfiguration
@EnableConfigurationProperties(OpenAiProperties.class)
@ConditionalOnProperty(prefix = "openai", name = "api-key")
public class OpenAiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "openAiRestClient")
    public RestClient openAiRestClient(OpenAiProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(properties.getConnectTimeout());
        requestFactory.setReadTimeout(properties.getReadTimeout());

        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + properties.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .requestFactory(requestFactory)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAiClient openAiClient(RestClient openAiRestClient, OpenAiProperties properties) {
        return new OpenAiClient(openAiRestClient, properties);
    }
}
