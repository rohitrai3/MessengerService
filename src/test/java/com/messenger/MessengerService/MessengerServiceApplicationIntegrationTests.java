package com.messenger.MessengerService;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.messenger.MessengerService.model.AcceptConnectionRequestInput;
import com.messenger.MessengerService.model.AddConnectionRequestInput;
import com.messenger.MessengerService.model.AddMessageInput;
import com.messenger.MessengerService.model.AddUserInput;
import com.messenger.MessengerService.model.MessageData;
import com.messenger.MessengerService.model.UserData;
import net.minidev.json.JSONArray;
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
class MessengerServiceApplicationIntegrationTests {

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

	@DisplayName("Should add new connection request")
	@Order(2)
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

	@DisplayName("Should add new message")
	@Order(3)
	@Test
	public void shouldAddNewMessage() {
		MessageData messageData = MessageData.builder()
				.sender("johndoe")
				.receiver("alice")
				.message("Hello! Alice.")
				.timestamp(1234567890L)
				.build();
		AddMessageInput input = AddMessageInput.builder()
				.messageData(messageData)
				.build();

		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/chat/add-message", input, Void.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createResponse.getHeaders().getLocation()).isNotNull();
	}

	@DisplayName("Should accept connection request")
	@Order(4)
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

	@DisplayName("Should return username when data is saved")
	@Order(5)
	@Test
	public void shouldReturnUsernameWhenDataIsSaved() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/get-username/1234abcd", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String username = documentContext.read("@.username");

		assertThat(username).isEqualTo("johndoe");
	}

	@DisplayName("Should respond not found")
	@Order(6)
	@Test
	public void shouldResponseNotFound() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/get-username/username-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(getResponse.getBody()).isBlank();
	}

	@DisplayName("Should fetch user")
	@Order(7)
	@Test
	public void shouldFetchUser() {
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
	@Order(8)
	@Test
	public void shouldRespondNotFound() {
		ResponseEntity<AddUserInput> getResponse = restTemplate.getForEntity("/user/get-user/user-does-not-exist", AddUserInput.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(getResponse.getBody()).isNull();
	}

	@DisplayName("Should return list of all connection requests")
	@Order(9)
	@Test
	public void shouldReturnListOfAllConnectionRequests() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/connection/get-connection-requests/alice", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		int connectionRequestsCount = documentContext.read("$.requestIdToUserData.length()");
		JSONArray usernameList = documentContext.read("$.requestIdToUserData..username");
		JSONArray nameList = documentContext.read("$.requestIdToUserData..name");
		JSONArray photoUrlList = documentContext.read("$.requestIdToUserData..photoUrl");

		assertThat(connectionRequestsCount).isEqualTo(2);
		assertThat(usernameList).containsExactlyInAnyOrder("johndoe", "bob");
		assertThat(nameList).containsExactlyInAnyOrder("John Doe", "Bob");
		assertThat(photoUrlList).containsExactlyInAnyOrder("https://example.com/johndoe.png", "https://example.com/bob.png");
	}

	@DisplayName("Should return empty list of connection requests")
	@Order(10)
	@Test
	public void shouldReturnEmptyListOfConnectionRequests() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/connection/get-connection-requests/user-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		int connectionRequestsCount = documentContext.read("$.requestIdToUserData.length()");

		assertThat(connectionRequestsCount).isEqualTo(0);
	}

	@DisplayName("Should return list of all connections")
	@Order(11)
	@Test
	public void shouldReturnListOfAllConnections() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/connection/get-connections/johndoe", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		int connectionRequestsCount = documentContext.read("$.userDataList.length()");
		JSONArray usernameList = documentContext.read("$.userDataList..username");
		JSONArray nameList = documentContext.read("$.userDataList..name");
		JSONArray photoUrlList = documentContext.read("$.userDataList..photoUrl");

		assertThat(connectionRequestsCount).isEqualTo(2);
		assertThat(usernameList).containsExactlyInAnyOrder("alice", "bob");
		assertThat(nameList).containsExactlyInAnyOrder("Alice", "Bob");
		assertThat(photoUrlList).containsExactlyInAnyOrder("https://example.com/alice.png", "https://example.com/bob.png");
	}

	@DisplayName("Should return empty list of connections")
	@Order(12)
	@Test
	public void shouldReturnEmptyListOfConnections() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/connection/get-connections/user-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		int connectionRequestsCount = documentContext.read("$.userDataList.length()");

		assertThat(connectionRequestsCount).isEqualTo(0);
	}

	@DisplayName("Should return list of all messages")
	@Order(13)
	@Test
	public void shouldReturnListOfAllMessages() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/chat/get-messages?sender=johndoe&receiver=alice", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		int messagesCount = documentContext.read("$.messageDataList.length()");
		JSONArray senderList = documentContext.read("$.messageDataList..sender");
		JSONArray receiverList = documentContext.read("$.messageDataList..receiver");
		JSONArray messageList = documentContext.read("$.messageDataList..message");
		JSONArray timestampList = documentContext.read("$.messageDataList..timestamp");

		assertThat(messagesCount).isEqualTo(2);
		assertThat(senderList).containsExactlyInAnyOrder("johndoe", "alice");
		assertThat(receiverList).containsExactlyInAnyOrder("alice", "johndoe");
		assertThat(messageList).containsExactlyInAnyOrder("Hello! Alice.", "Hi! John. How are you?");
		assertThat(timestampList).containsExactlyInAnyOrder(1234567890, 1234567891);
	}

	@DisplayName("Should return empty list of messages")
	@Order(14)
	@Test
	public void shouldReturnEmptyListOfMessages() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/chat/get-messages?sender=johndoe&receiver=user-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		int messagesCount = documentContext.read("$.messageDataList.length()");

		assertThat(messagesCount).isEqualTo(0);
	}

	@DisplayName("Should return true for existing uid")
	@Order(16)
	@Test
	public void shouldReturnTrueForExistingUid() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-uid-exist/1234abcd", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUidExist = documentContext.read("$.isUidExist");

		assertThat(isUidExist).isEqualTo(true);
	}

	@DisplayName("Should return false for non-existing uid")
	@Order(17)
	@Test
	public void shouldReturnFalseForNonExistingUid() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-uid-exist/uid-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUidExist = documentContext.read("$.isUidExist");

		assertThat(isUidExist).isEqualTo(false);
	}

	@DisplayName("Should return true for existing username")
	@Order(18)
	@Test
	public void shouldReturnTrueForExistingUsername() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-username-exist/johndoe", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUsernameExist = documentContext.read("$.isUsernameExist");

		assertThat(isUsernameExist).isEqualTo(true);
	}

	@DisplayName("Should return false for non-existing username")
	@Order(19)
	@Test
	public void shouldReturnFalseForNonExistingUsername() {
		ResponseEntity<String> getResponse = restTemplate.getForEntity("/user/check-username-exist/username-does-not-exist", String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		boolean isUsernameExist = documentContext.read("$.isUsernameExist");

		assertThat(isUsernameExist).isEqualTo(false);
	}

}
