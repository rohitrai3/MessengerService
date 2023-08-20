package com.messenger.MessengerService.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CheckUidExistOutputJsonTest {

    @Autowired
    private JacksonTester<CheckUidExistOutput> json;

    @Test
    @DisplayName("CheckUidExistOutput serialization test")
    public void checkUidExistOutputSerializationTest() throws IOException {
        CheckUidExistOutput output = CheckUidExistOutput.builder()
                .isUidExist(true)
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-check-uid-exist-output.json");
        assertThat(json.write(output)).hasJsonPathBooleanValue("@.isUidExist");
        assertThat(json.write(output)).extractingJsonPathBooleanValue("@.isUidExist").isEqualTo(true);
    }

    @Test
    @DisplayName("CheckUidExistOutput deserialization test")
    public void checkUidExistOutputDeserializationTest() throws IOException {
        String expected = """
                {
                    "isUidExist": true
                }
                """;

        assertThat(json.parseObject(expected).getIsUidExist()).isEqualTo(true);
    }

}
