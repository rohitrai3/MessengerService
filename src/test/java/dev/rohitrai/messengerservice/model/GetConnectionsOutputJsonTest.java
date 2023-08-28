package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GetConnectionsOutputJsonTest {

    @Autowired
    private JacksonTester<GetConnectionsOutput> json;

    @Test
    @DisplayName("GetConnectionsOutput serialization test")
    public void getConnectionsOutputSerializationTest() throws IOException {
        List<UserData> userDataList = new ArrayList<>();
        userDataList.add(UserData.builder()
                .username("johndoe")
                .name("John Doe")
                .photoUrl("https://example.com/johndoe.png")
                .build());
        userDataList.add(UserData.builder()
                .username("alice")
                .name("Alice")
                .photoUrl("https://example.com/alice.png")
                .build());

        GetConnectionsOutput output = GetConnectionsOutput.builder()
                .userDataList(userDataList)
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-get-connections-output.json");
        assertThat(json.write(output)).hasJsonPathArrayValue("@.userDataList");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.userDataList").size().isEqualTo(2);
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.userDataList").element(0).extracting("username").isEqualTo("johndoe");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.userDataList").element(0).extracting("name").isEqualTo("John Doe");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.userDataList").element(0).extracting("photoUrl").isEqualTo("https://example.com/johndoe.png");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.userDataList").element(1).extracting("username").isEqualTo("alice");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.userDataList").element(1).extracting("name").isEqualTo("Alice");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.userDataList").element(1).extracting("photoUrl").isEqualTo("https://example.com/alice.png");
    }

    @Test
    @DisplayName("GetConnectionsOutput deserialization test")
    public void getConnectionsOutputDeserializationTest() throws IOException {
        String expected = """
                {
                    "userDataList": [
                        {
                            "username": "johndoe",
                            "name": "John Doe",
                            "photoUrl": "https://example.com/johndoe.png"
                        },
                        {
                            "username": "alice",
                            "name": "Alice",
                            "photoUrl": "https://example.com/alice.png"
                        }
                    ]
                }
                """;

        assertThat(json.parseObject(expected).getUserDataList()).size().isEqualTo(2);
        assertThat(json.parseObject(expected).getUserDataList()).element(0).extracting("username").isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getUserDataList()).element(0).extracting("name").isEqualTo("John Doe");
        assertThat(json.parseObject(expected).getUserDataList()).element(0).extracting("photoUrl").isEqualTo("https://example.com/johndoe.png");
        assertThat(json.parseObject(expected).getUserDataList()).element(1).extracting("username").isEqualTo("alice");
        assertThat(json.parseObject(expected).getUserDataList()).element(1).extracting("name").isEqualTo("Alice");
        assertThat(json.parseObject(expected).getUserDataList()).element(1).extracting("photoUrl").isEqualTo("https://example.com/alice.png");
    }

}
