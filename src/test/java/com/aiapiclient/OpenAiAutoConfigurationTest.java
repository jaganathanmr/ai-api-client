package com.aiapiclient;

import com.aiapiclient.client.OpenAiClient;
import com.aiapiclient.config.OpenAiAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenAiAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(OpenAiAutoConfiguration.class));

    @Test
    void shouldNotCreateOpenAiClientWhenApiKeyIsMissing() {
        contextRunner.run(context -> {
            assertFalse(context.containsBeanDefinition("openAiClient"));
            assertFalse(context.containsBeanDefinition("openAiRestClient"));
        });
    }

    @Test
    void shouldCreateBeansWhenApiKeyIsPresent() {
        contextRunner.withPropertyValues("openai.api-key=sk-test-key")
                .run(context -> {
                    assertTrue(context.containsBean("openAiClient"));
                    assertTrue(context.containsBean("openAiRestClient"));
                    OpenAiClient client = context.getBean(OpenAiClient.class);
                    assertTrue(client != null);
                });
    }
}
