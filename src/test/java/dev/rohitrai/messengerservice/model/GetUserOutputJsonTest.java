package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GetUserOutputJsonTest {

    @Autowired
    private JacksonTester<GetUserOutput> json;

    @Test
    @DisplayName("GetUserOutput serialization test")
    public void getUserOutputSerializationTest() throws IOException {
        GetUserOutput output = GetUserOutput.builder()
                .userData(UserData.builder()
                        .username("johndoe")
                        .name("John Doe")
                        .photoUrl("https://example.com/johndoe.png")
                        .build())
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-get-user-output.json");
        assertThat(json.write(output)).hasJsonPathStringValue("@.userData.username");
        assertThat(json.write(output)).extractingJsonPathStringValue("@.userData.username").isEqualTo("johndoe");
        assertThat(json.write(output)).hasJsonPathStringValue("@.userData.name");
        assertThat(json.write(output)).extractingJsonPathStringValue("@.userData.name").isEqualTo("John Doe");
        assertThat(json.write(output)).hasJsonPathStringValue("@.userData.photoUrl");
        assertThat(json.write(output)).extractingJsonPathStringValue("@.userData.photoUrl").isEqualTo("https://example.com/johndoe.png");
    }

    @Test
    @DisplayName("GetUserOutput deserialization test")
    public void getUserOutputDeserializationTest() throws IOException {
        String expected = """
                {
                    "userData": {
                        "username": "johndoe",
                        "name": "John Doe",
                        "photoUrl": "https://example.com/johndoe.png"
                    }
                }
                """;

        assertThat(json.parseObject(expected).getUserData().getUsername()).isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getUserData().getName()).isEqualTo("John Doe");
        assertThat(json.parseObject(expected).getUserData().getPhotoUrl()).isEqualTo("https://example.com/johndoe.png");
    }

}
