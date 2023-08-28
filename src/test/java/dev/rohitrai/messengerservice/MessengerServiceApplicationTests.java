package dev.rohitrai.messengerservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import dev.rohitrai.messengerservice.model.AddUserInput;
import dev.rohitrai.messengerservice.model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessengerServiceApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@DisplayName("Should add new user")
	@Order(1)
	@Test
	public void shouldAddNewUser() {
		UserData userData = UserData.builder()
				.username("johndoe")
				.name("John Doe")
				.photoUrl("https://example.com/johndoe.png")
				.build();
		AddUserInput input = AddUserInput.builder()
				.uid("1234abcd")
				.userData(userData)
				.build();

		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/user/add-user", input, Void.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createResponse.getHeaders().getLocation()).isNotNull();
	}

	@DisplayName("Should return username")
	@Order(2)
	@Test
	public void shouldReturnUsername() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/get-username/1234abcd", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String username = documentContext.read("@.username");

		assertThat(username).isEqualTo("johndoe");
	}

}
