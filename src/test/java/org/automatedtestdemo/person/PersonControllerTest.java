package org.automatedtestdemo.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import org.automatedtestdemo.SpringBootDemoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(classes = SpringBootDemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PersonControllerTest {
	@LocalServerPort
	private int port;
	private String domainURL = "http://localhost:";
	private String baseURL = "/api/v1";
	
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		createExamplePerson();
	}
	
    @Test
	public void saveExample1() {
		try {
			HttpClient client = HttpClient.newBuilder().build();
			
			Person person = new Person();
			person.setUniqueIdentifier("TEST_PERSON_01");
			person.setFirstName("John Test 1");
			person.setLastName("Doe Test 1");
			person.setAddress("16 Main Street");
		
			String body = objectMapper.writeValueAsString(person);
						
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(domainURL+port+baseURL + "/person/save"))
					.header("Content-Type", "application/json").header("Authorization", "Bearer ")
					.POST(HttpRequest.BodyPublishers.ofString(body)).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			assertEquals(response.statusCode(), 200);
			
			Person savedPerson = objectMapper.readValue(response.body(), Person.class);		
			
			assertNotEquals(person.getId(), savedPerson.getId());
			
			assertEquals(person.getFirstName(), savedPerson.getFirstName());
			assertEquals(person.getLastName(), savedPerson.getLastName());
		
					
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
			assertTrue(false);//force failure- exception occurred
		}

	}
    
    @Test
	public void saveExample2_LoadFromFile() {
		try {
			HttpClient client = HttpClient.newBuilder().build();
			
		    File resource = new ClassPathResource(
		    	      "testdata/testperson_02.json").getFile();
		    String examplePersonFromFile = new String(Files.readAllBytes(resource.toPath()));
		
			String body = examplePersonFromFile;
						
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(domainURL+port+baseURL + "/person/save"))
					.header("Content-Type", "application/json").header("Authorization", "Bearer ")
					.POST(HttpRequest.BodyPublishers.ofString(body)).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			assertEquals(response.statusCode(), 200);
			
			Person savedPerson = objectMapper.readValue(response.body(), Person.class);		
			
			assertNotEquals(0, savedPerson.getId());
			
			assertEquals("John Test 2", savedPerson.getFirstName());
			assertEquals("Doe Test 2", savedPerson.getLastName());
		
					
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
			assertTrue(false);//force failure- exception occurred
		}

	}
	
    @Test
    public void test_getAllPersons() {
		List<Person> returnList = null;
		try {
			HttpClient client = HttpClient.newBuilder().build();

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(domainURL+port+baseURL + "/person/get-all"))
					.header("Content-Type", "application/json").header("Authorization", "Bearer ")
					.GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			assertEquals(response.statusCode(), 200);
			
			returnList =  objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));

			assertNotEquals(returnList.size(), 0);
			
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
			assertTrue(false);//force failure- exception occurred
		}
    }
    
    private void createExamplePerson() {
		try {
			HttpClient client = HttpClient.newBuilder().build();
			
			Person person = new Person();
			person.setUniqueIdentifier("EXAMPLE_PERSON_01");
			person.setFirstName("John Example 1");
			person.setLastName("Doe Example 1");
			person.setAddress("16 Main Street");
			person.setBirthDate(LocalDate.of(1999, 12, 31));
		
			String body = objectMapper.writeValueAsString(person);
						
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(domainURL+port+baseURL + "/person/save"))
					.header("Content-Type", "application/json").header("Authorization", "Bearer ")
					.POST(HttpRequest.BodyPublishers.ofString(body)).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			assertEquals(response.statusCode(), 200);
			
			System.out.println(response.body());
			
			Person savedPerson = objectMapper.readValue(response.body(), Person.class);		
			
			System.out.println(savedPerson);
					
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
			assertTrue(false);//force failure- exception occurred
		}
    }

}
