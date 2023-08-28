package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GetUsernameOutputJsonTest {

    @Autowired
    private JacksonTester<GetUsernameOutput> json;

    @Test
    @DisplayName("GetUsernameOutput serialization test")
    public void getUsernameOutputSerializationTest() throws IOException {
        GetUsernameOutput output = GetUsernameOutput.builder()
                .username("johndoe")
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-get-username-output.json");
        assertThat(json.write(output)).hasJsonPathStringValue("@.username");
        assertThat(json.write(output)).extractingJsonPathStringValue("@.username").isEqualTo("johndoe");
    }

    @Test
    @DisplayName("GetUsernameOutput deserialization test")
    public void getUsernameOutputDeserializationTest() throws IOException {
        String expected = """
                {
                    "username": "johndoe"
                }
                """;

        assertThat(json.parseObject(expected).getUsername()).isEqualTo("johndoe");
    }

}
