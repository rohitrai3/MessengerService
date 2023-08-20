package com.messenger.MessengerService.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CheckUsernameExistOutputJsonTest {

    @Autowired
    private JacksonTester<CheckUsernameExistOutput> json;

    @Test
    @DisplayName("CheckUsernameExistOutput serialization test")
    public void checkUsernameExistOutputSerializationTest() throws IOException {
        CheckUsernameExistOutput output = CheckUsernameExistOutput.builder()
                .isUsernameExist(true)
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-check-username-exist-output.json");
        assertThat(json.write(output)).hasJsonPathBooleanValue("@.isUsernameExist");
        assertThat(json.write(output)).extractingJsonPathBooleanValue("@.isUsernameExist").isEqualTo(true);
    }

    @Test
    @DisplayName("CheckUsernameExistOutput deserialization test")
    public void checkUsernameExistOutputDeserializationTest() throws IOException {
        String expected = """
                {
                    "isUsernameExist": true
                }
                """;

        assertThat(json.parseObject(expected).getIsUsernameExist()).isEqualTo(true);
    }

}
