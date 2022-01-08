package org.example.sit.rest.backend;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

import java.util.Map;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.example.sit.rest.RequestSpecificationType;
import org.example.sit.rest.RestResourceTestFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for the common behaviour of the backend REST resource {@code ResourceC}.
 */
class ResourceCCommonIT extends RestResourceTestFactory {
   @BeforeAll
   public static void initRequestSpecification() {
      initRequestSpecifications(PATH_REST_BACKEND_BASE + PATH_REST_BACKEND_RESOURCE_C);
   }
   
   @Test
   void test_RedirectFromHttpToHttps() {
      final RequestSpecification requestSpecification =
            requestSpecifications.get(RequestSpecificationType.JSON_UNSECURED);
      
      given()
            .spec(requestSpecification)
      .when()
            .redirects().follow(false)
            .get()
      .then()
            .statusCode(HttpStatus.SC_SEE_OTHER)
            .header(HttpHeaders.LOCATION, startsWith("https"))
            .body(is(emptyOrNullString()));
   }
   
   @Test
   void test_HttpsWithoutBasicAuth() {
      final RequestSpecification requestSpecification =
            requestSpecifications.get(RequestSpecificationType.JSON_SECURED);
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .auth().none()
            .get()
      .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert401Response(responseData);
   }
   
   @Test
   void test_HttpsWithBasicAuthInvalidCredentials() {
      final RequestSpecification requestSpecification =
            requestSpecifications.get(RequestSpecificationType.JSON_SECURED);
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .auth().basic(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD + BASIC_AUTH_PASSWORD)
            .get()
      .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert401Response(responseData);
   }
   
   @Test
   void test_HttpsWithBasicAuthInvalidRole() {
      final RequestSpecification requestSpecification =
            requestSpecifications.get(RequestSpecificationType.JSON_SECURED);
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .auth().basic(BASIC_AUTH_USERNAME_NO_ROLE, BASIC_AUTH_PASSWORD_NO_ROLE)
            .get()
      .then()
            .statusCode(HttpStatus.SC_FORBIDDEN)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert403Response(responseData);
   }
   
   @Test
   void test_UnsupportedContentType() {
      final RequestSpecification requestSpecification =
            requestSpecifications.get(RequestSpecificationType.UNSUPPORTED_TYPE_SECURED);
      
      final String responseData = given()
            .spec(requestSpecification)
      .when()
            .get()
      .then()
            .statusCode(HttpStatus.SC_NOT_ACCEPTABLE)
            .body(not(is(emptyOrNullString())))
            .extract().body().asString();
      
      assert406Response(responseData);
   }
}
