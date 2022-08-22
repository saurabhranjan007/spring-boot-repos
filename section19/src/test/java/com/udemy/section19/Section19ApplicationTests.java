package com.udemy.section19;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

@SpringBootTest
class Section19ApplicationTests {

	private String DOWNLOAD_DIR="C:\\pproject-files\\JAVA\\Udemy\\udemy-sub-projects\\section19\\documents\\downloads";

	private static final String UPLOAD_URL="http://localhost:8080/upload/";

	private static final String DOWNLOAD_URL="http://localhost:8080/download/";

	// Getting REST template from Spring Web
	@Autowired
	RestTemplate restTemplate;

	// Test case for Upload
	@Test
	public void testUpload() {

		// Mocking the test the way we make calls using postman
		// Upload Headers:
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		// Upload Body:
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		// in postman, we add "key" and "value" in the body mocking the same functionality here
		body.add("file",new ClassPathResource("101.jpg"));

		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
		// Making the call using "RestTemplate"
		ResponseEntity<Boolean> response = restTemplate.postForEntity(UPLOAD_URL, httpEntity, Boolean.class);

		System.out.println("Response: "+response.getBody());
	}

	// test case for Download
	@Test
	public void testDownload() throws IOException {

		String fileName="101.jpg";

		HttpHeaders headers = new HttpHeaders();
		// this will tell the server we're expecting an "Octet Stream" or binary data
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		// ".exchange()" - to download files
		// This method takes four arguments, 1. url, 2. Http - type, 3. Https-entity(defined data), and 4. return type
		ResponseEntity<byte[]> response = restTemplate.exchange(DOWNLOAD_URL+fileName, HttpMethod.GET, httpEntity, byte[].class);

		// From this above function we have response. Now we write the response to a file.
		Files.write(Paths.get(DOWNLOAD_DIR+fileName),response.getBody());
	}
}
