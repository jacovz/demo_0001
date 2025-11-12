package org.automatedtestdemo.person;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.automatedtestdemo.SpringBootDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SpringBootDemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PersonControllerTest {
	@LocalServerPort
	private int port;
	private String domainURL = "http://localhost:";
	private String baseURL = "/api/v1";

    @Test
    void test_getAllPersons() {
		List<Person> returnEntity = null;
		try {
			HttpClient client = HttpClient.newBuilder().build();

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(domainURL+port+baseURL + "/person/get-all"))
					.header("Content-Type", "application/json").header("Authorization", "Bearer ")
					.GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			assertEquals(response.statusCode(), 200);
			
//			returnEntity =  getObjectMapper().readValue(response.body(), getObjectMapper().getTypeFactory().constructCollectionType(List.class, getModlledEntityInstance().getClass()));

			System.out.println(response.body());
			
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
			assertThatException();
		}
		if(returnEntity==null) {
			returnEntity = new ArrayList<>();
		}
    }

}
