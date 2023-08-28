package dev.rohitrai.messengerservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import dev.rohitrai.messengerservice.model.AcceptConnectionRequestInput;
import dev.rohitrai.messengerservice.model.AddConnectionRequestInput;
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

	@DisplayName("Should respond not found")
	@Order(3)
	@Test
	public void shouldResponseNotFound() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/get-username/username-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(getResponse.getBody()).isBlank();
	}

	@DisplayName("Should return user")
	@Order(4)
	@Test
	public void shouldReturnUser() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/get-user/johndoe", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String username = documentContext.read("$.userData.username");
		String name = documentContext.read("$.userData.name");
		String photoUrl = documentContext.read("$.userData.photoUrl");

		assertThat(username).isEqualTo("johndoe");
		assertThat(name).isEqualTo("John Doe");
		assertThat(photoUrl).isEqualTo("https://example.com/johndoe.png");
	}

	@DisplayName("Should respond not found")
	@Order(5)
	@Test
	public void shouldRespondNotFound() {
		ResponseEntity<AddUserInput> getResponse = restTemplate.getForEntity("/user/get-user/user-does-not-exist", AddUserInput.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(getResponse.getBody()).isNull();
	}

	@DisplayName("Should return true for existing uid")
	@Order(6)
	@Test
	public void shouldReturnTrueForExistingUid() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-uid-exist/1234abcd", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUidExist = documentContext.read("$.isUidExist");

		assertThat(isUidExist).isEqualTo(true);
	}

	@DisplayName("Should return false for non-existing uid")
	@Order(7)
	@Test
	public void shouldReturnFalseForNonExistingUid() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-uid-exist/uid-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUidExist = documentContext.read("$.isUidExist");

		assertThat(isUidExist).isEqualTo(false);
	}

	@DisplayName("Should return true for existing username")
	@Order(8)
	@Test
	public void shouldReturnTrueForExistingUsername() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-username-exist/johndoe", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUsernameExist = documentContext.read("$.isUsernameExist");

		assertThat(isUsernameExist).isEqualTo(true);
	}

	@DisplayName("Should return false for non-existing username")
	@Order(9)
	@Test
	public void shouldReturnFalseForNonExistingUsername() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-username-exist/username-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUsernameExist = documentContext.read("$.isUsernameExist");

		assertThat(isUsernameExist).isEqualTo(false);
	}

	@DisplayName("Should add new connection request")
	@Order(10)
	@Test
	public void shouldAddNewConnectionRequest() {
		AddConnectionRequestInput input = AddConnectionRequestInput.builder()
				.sender("johndoe")
				.receiver("alice")
				.build();

		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/connection/add-connection-request", input, Void.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createResponse.getHeaders().getLocation()).isNotNull();
	}

	@DisplayName("Should accept connection request")
	@Order(11)
	@Test
	public void shouldAcceptConnectionRequest() {
		AcceptConnectionRequestInput newConnection = AcceptConnectionRequestInput.builder()
				.user("johndoe")
				.connection("alice")
				.connectionRequestKey("12345")
				.build();
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/connection/accept-connection-request", newConnection, Void.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createResponse.getHeaders().getLocation()).isNotNull();
	}

}
