package org.example.sit.rest.frontend;

import static org.hamcrest.Matchers.startsWith;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.example.sit.rest.RequestSpecificationType;
import org.example.sit.rest.RestResourceTestFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for the common behaviour of the frontend REST resource {@code ResourceE}.
 */
@RunWith(Parameterized.class)
public class ResourceECommonIT extends RestResourceTestFactory {
   private static final String BASIC_AUTH_USERNAME_NO_ROLE = "rest-no-role";
   private static final String BASIC_AUTH_PASSWORD_NO_ROLE = "rest-no-role";
   
   @Parameter
   public RequestSpecificationType testParameter;
   
   @Parameters
   public static RequestSpecificationType[] testParameters() {
      return RequestSpecificationType.values();
   }
   
   @BeforeClass
   public static void initRequestSpecification() {
      initRequestSpecifications(PATH_REST_FRONTEND_BASE);
   }
   
   @Test
   public void test_RedirectFromHttpToHttps() {
      if (this.testParameter.isSecuredConnection() ||
            !this.testParameter.isSupportedContentType()) {
         return;
      }
      
      final RequestSpecification requestSpecification =
            requestSpecifications.get(this.testParameter);
      RestAssured.given().spec(requestSpecification).when().redirects().follow(false).get("/")
            .then().statusCode(HttpStatus.SC_MOVED_TEMPORARILY).and().assertThat()
            .header(HttpHeaders.LOCATION, startsWith("https"));
   }
   
   @Test
   public void test_HttpsWithoutBasicAuth() {
      if (!this.testParameter.isSecuredConnection() ||
            !this.testParameter.isSupportedContentType()) {
         return;
      }
      
      final RequestSpecification requestSpecification =
            requestSpecifications.get(this.testParameter);
      RestAssured.given().spec(requestSpecification).when().auth().none()
            .get(PATH_REST_FRONTEND_RESOURCE_E).then().statusCode(HttpStatus.SC_UNAUTHORIZED);
   }
   
   @Test
   public void test_HttpsWithBasicAuthInvalidCredentials() {
      if (!this.testParameter.isSecuredConnection() ||
            !this.testParameter.isSupportedContentType()) {
         return;
      }
      
      final RequestSpecification requestSpecification =
            requestSpecifications.get(this.testParameter);
      RestAssured.given().spec(requestSpecification).when().auth()
            .basic(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD + BASIC_AUTH_PASSWORD)
            .get(PATH_REST_FRONTEND_RESOURCE_E).then().statusCode(HttpStatus.SC_UNAUTHORIZED);
   }
   
   @Test
   public void test_HttpsWithBasicAuthInvalidRole() {
      if (!this.testParameter.isSecuredConnection() ||
            !this.testParameter.isSupportedContentType()) {
         return;
      }
      
      final RequestSpecification requestSpecification =
            requestSpecifications.get(this.testParameter);
      RestAssured.given().spec(requestSpecification).when().auth()
            .basic(BASIC_AUTH_USERNAME_NO_ROLE, BASIC_AUTH_PASSWORD_NO_ROLE)
            .get(PATH_REST_FRONTEND_RESOURCE_E).then().statusCode(HttpStatus.SC_FORBIDDEN);
   }
   
   @Test
   public void test_UnsupportedContentType() {
      if (this.testParameter.isSupportedContentType() ||
            !this.testParameter.isSecuredConnection()) {
         return;
      }
      
      final RequestSpecification requestSpecification =
            requestSpecifications.get(this.testParameter);
      RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
      RestAssured.given().spec(requestSpecification).when()
            .get(PATH_REST_FRONTEND_RESOURCE_E).then().statusCode(HttpStatus.SC_NOT_ACCEPTABLE);
   }
}
