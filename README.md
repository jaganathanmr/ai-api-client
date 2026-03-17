# AI API Client

A Java Spring Boot starter library for connecting to the OpenAI API. Provides auto-configuration, typed request/response DTOs, and a clean client interface for the most common OpenAI endpoints.

## Features

- **Spring Boot Auto-Configuration** — just add the dependency and set your API key
- **Chat Completions** — full support for the `/v1/chat/completions` endpoint with a builder pattern
- **Embeddings** — create embeddings via `/v1/embeddings`
- **Models** — list and retrieve models via `/v1/models`
- **Typed DTOs** — strongly-typed request and response objects with Jackson serialization
- **Error Handling** — custom `OpenAiApiException` with HTTP status and structured error details
- **Configurable** — API key, base URL, default models, and timeouts via Spring properties

## Requirements

- Java 17+
- Spring Boot 3.x

## Quick Start

### 1. Add the dependency

**Gradle (build.gradle):**

```groovy
implementation 'com.aiapiclient:ai-api-client:1.0.0-SNAPSHOT'
```

**Maven (pom.xml):**

```xml
<dependency>
    <groupId>com.aiapiclient</groupId>
    <artifactId>ai-api-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 2. Configure your API key

In `application.yml`:

```yaml
openai:
  api-key: ${OPENAI_API_KEY}
```

Or in `application.properties`:

```properties
openai.api-key=${OPENAI_API_KEY}
```

### 3. Inject and use the client

```java
import com.aiapiclient.client.OpenAiClient;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private final OpenAiClient openAiClient;

    public MyService(OpenAiClient openAiClient) {
        this.openAiClient = openAiClient;
    }

    public String askQuestion(String question) {
        return openAiClient.chat(question);
    }
}
```

## Usage Examples

### Chat Completions

**Simple chat:**

```java
String reply = openAiClient.chat("What is the capital of France?");
```

**Chat with system prompt:**

```java
String reply = openAiClient.chat(
    "You are a helpful travel assistant.",
    "What are the top attractions in Paris?"
);
```

**Full control with builder:**

```java
import com.aiapiclient.dto.chat.ChatCompletionRequest;
import com.aiapiclient.dto.chat.ChatCompletionResponse;

ChatCompletionRequest request = ChatCompletionRequest.builder()
    .model("gpt-4o")
    .addSystemMessage("You are a code reviewer.")
    .addUserMessage("Review this function: ...")
    .temperature(0.3)
    .maxTokens(500)
    .build();

ChatCompletionResponse response = openAiClient.chatCompletion(request);
String content = response.getFirstContent();
```

### Embeddings

**Simple embedding:**

```java
List<Double> vector = openAiClient.embed("Hello, world!");
```

**Full control:**

```java
import com.aiapiclient.dto.embedding.EmbeddingRequest;
import com.aiapiclient.dto.embedding.EmbeddingResponse;

EmbeddingRequest request = EmbeddingRequest.of("text-embedding-3-small", "Hello, world!");
EmbeddingResponse response = openAiClient.createEmbedding(request);
List<Double> vector = response.getFirstEmbedding();
```

### Models

**List all models:**

```java
import com.aiapiclient.dto.model.ModelListResponse;

ModelListResponse models = openAiClient.listModels();
models.getData().forEach(m -> System.out.println(m.getId()));
```

**Retrieve a specific model:**

```java
import com.aiapiclient.dto.model.Model;

Model model = openAiClient.retrieveModel("gpt-4o");
System.out.println(model.getOwnedBy());
```

### Error Handling

```java
import com.aiapiclient.exception.OpenAiApiException;

try {
    String reply = openAiClient.chat("Hello");
} catch (OpenAiApiException e) {
    System.err.println("Status: " + e.getStatusCode());
    System.err.println("Error type: " + e.getErrorType());
    System.err.println("Error code: " + e.getErrorCode());
    System.err.println("Message: " + e.getMessage());
}
```

## Configuration Reference

| Property                       | Default                        | Description                          |
|-------------------------------|--------------------------------|--------------------------------------|
| `openai.api-key`              | *(required)*                   | Your OpenAI API key                  |
| `openai.base-url`             | `https://api.openai.com`       | Base URL for the OpenAI API          |
| `openai.default-model`        | `gpt-4o`                       | Default model for chat completions   |
| `openai.default-embedding-model` | `text-embedding-3-small`    | Default model for embeddings         |
| `openai.connect-timeout`      | `10000`                        | Connection timeout in milliseconds   |
| `openai.read-timeout`         | `30000`                        | Read timeout in milliseconds         |

## Project Structure

```
src/main/java/com/aiapiclient/
├── client/
│   └── OpenAiClient.java              # Main client with API methods
├── config/
│   ├── OpenAiAutoConfiguration.java    # Spring Boot auto-configuration
│   └── OpenAiProperties.java          # Configuration properties
├── dto/
│   ├── chat/
│   │   ├── ChatChoice.java
│   │   ├── ChatCompletionRequest.java
│   │   ├── ChatCompletionResponse.java
│   │   └── ChatMessage.java
│   ├── common/
│   │   ├── OpenAiError.java
│   │   └── Usage.java
│   ├── embedding/
│   │   ├── EmbeddingData.java
│   │   ├── EmbeddingRequest.java
│   │   └── EmbeddingResponse.java
│   └── model/
│       ├── Model.java
│       └── ModelListResponse.java
└── exception/
    └── OpenAiApiException.java
```

## Building

```bash
./gradlew clean build
```

## Running Tests

```bash
./gradlew test
```

## License

This project is licensed under the MIT License.
