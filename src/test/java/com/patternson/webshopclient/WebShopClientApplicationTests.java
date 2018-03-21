package com.patternson.webshopclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebShopClientApplicationTests {

	@Test
	public void contextLoads() {
	}


//	@Test
//	public void getAccessTokenViaSpringSecurityOAuthClient() {
//		try{
//
//			ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
//			resourceDetails.setClientSecret("secret");
//			resourceDetails.setClientId("SampleClientId");
//			resourceDetails.setAccessTokenUri("http://localhost:8081/auth/oauth/token");
//
//			OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(resourceDetails);
//
//			org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//			headers.setContentType( MediaType.APPLICATION_JSON );
//
//			OAuth2AccessToken token = oAuthRestTemplate.getAccessToken();
//			System.out.println(oAuthRestTemplate.getResource());
//			System.out.println(oAuthRestTemplate.getOAuth2ClientContext());
//			System.out.println(token);
//
//			assertTrue(token != null);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void assertTrue(boolean b) {
//	}


}
