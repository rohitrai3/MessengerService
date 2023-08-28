package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GetConnectionRequestsOutputJsonTest {

    @Autowired
    private JacksonTester<GetConnectionRequestsOutput> json;

    @Test
    @DisplayName("GetConnectionRequestsOutput serialization test")
    public void getConnectionRequestsOutputSerializationTest() throws IOException {
        Map<String, UserData> requestIdToUserData = new HashMap<>();
        requestIdToUserData.put("12345", UserData.builder()
                .username("johndoe")
                .name("John Doe")
                .photoUrl("https://example.com/johndoe.png")
                .build());
        requestIdToUserData.put("67890", UserData.builder()
                .username("alice")
                .name("Alice")
                .photoUrl("https://example.com/alice.png")
                .build());

        GetConnectionRequestsOutput output = GetConnectionRequestsOutput.builder()
                .requestIdToUserData(requestIdToUserData)
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-get-connection-requests-output.json");
        assertThat(json.write(output)).hasJsonPathMapValue("@.requestIdToUserData");
        assertThat(json.write(output)).extractingJsonPathMapValue("@.requestIdToUserData").size().isEqualTo(2);
        assertThat(json.write(output)).extractingJsonPathMapValue("@.requestIdToUserData").extracting("12345").extracting("username").isEqualTo("johndoe");
        assertThat(json.write(output)).extractingJsonPathMapValue("@.requestIdToUserData").extracting("12345").extracting("name").isEqualTo("John Doe");
        assertThat(json.write(output)).extractingJsonPathMapValue("@.requestIdToUserData").extracting("12345").extracting("photoUrl").isEqualTo("https://example.com/johndoe.png");
        assertThat(json.write(output)).extractingJsonPathMapValue("@.requestIdToUserData").extracting("67890").extracting("username").isEqualTo("alice");
        assertThat(json.write(output)).extractingJsonPathMapValue("@.requestIdToUserData").extracting("67890").extracting("name").isEqualTo("Alice");
        assertThat(json.write(output)).extractingJsonPathMapValue("@.requestIdToUserData").extracting("67890").extracting("photoUrl").isEqualTo("https://example.com/alice.png");
    }

    @Test
    @DisplayName("GetConnectionRequestsOutput deserialization test")
    public void getConnectionRequestsOutputDeserializationTest() throws IOException {
        String expected = """
                {
                    "requestIdToUserData": {
                        "12345": {
                            "username": "johndoe",
                            "name": "John Doe",
                            "photoUrl": "https://example.com/johndoe.png"
                        },
                        "67890": {
                            "username": "alice",
                            "name": "Alice",
                            "photoUrl": "https://example.com/alice.png"
                        }
                    }
                }
                """;

        assertThat(json.parseObject(expected).getRequestIdToUserData()).size().isEqualTo(2);
        assertThat(json.parseObject(expected).getRequestIdToUserData()).extracting("12345").extracting("username").isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getRequestIdToUserData()).extracting("12345").extracting("name").isEqualTo("John Doe");
        assertThat(json.parseObject(expected).getRequestIdToUserData()).extracting("12345").extracting("photoUrl").isEqualTo("https://example.com/johndoe.png");
        assertThat(json.parseObject(expected).getRequestIdToUserData()).extracting("67890").extracting("username").isEqualTo("alice");
        assertThat(json.parseObject(expected).getRequestIdToUserData()).extracting("67890").extracting("name").isEqualTo("Alice");
        assertThat(json.parseObject(expected).getRequestIdToUserData()).extracting("67890").extracting("photoUrl").isEqualTo("https://example.com/alice.png");
    }

}
