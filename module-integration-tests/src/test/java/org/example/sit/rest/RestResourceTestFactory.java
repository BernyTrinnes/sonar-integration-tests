package org.example.sit.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Base class for all tests of the REST resources.
 */
public abstract class RestResourceTestFactory {
   protected static Map<RequestSpecificationType, RequestSpecification> requestSpecifications =
         new HashMap<>(RequestSpecificationType.values().length);
   protected static final String BASIC_AUTH_USERNAME = "rest";
   protected static final String BASIC_AUTH_PASSWORD = "rest";
   protected static final String BASIC_AUTH_USERNAME_NO_ROLE = "rest-no-role";
   protected static final String BASIC_AUTH_PASSWORD_NO_ROLE = "rest-no-role";
   protected static final String PATH_REST_BACKEND_BASE = "/backend-rest";
   protected static final String PATH_REST_BACKEND_RESOURCE_B = "/resource-b";
   protected static final String PATH_REST_BACKEND_RESOURCE_C = "/resource-c";
   protected static final String PATH_REST_FRONTEND_BASE = "/frontend-rest";
   protected static final String PATH_REST_FRONTEND_RESOURCE_E = "/resource-e";
   protected static final String PATH_REST_FRONTEND_RESOURCE_F = "/resource-f";
   
   private static final int DEFAULT_PORT_HTTP = 8080;
   private static final int DEFAULT_PORT_HTTPS = 8081;
   private static final String PROPERTY_TEST_PORT_HTTP = "test.port.http";
   private static final String PROPERTY_TEST_PORT_HTTPS = "test.port.https";
   private static final String PROPERTY_TEST_SERVER_ADDRESS = "test.address";
   private static final String PROPERTY_TEST_RESPONSE_DEBUG = "test.response.debug";
   private static final String HTTP_BAD_REQUEST_CODE = "400";
   private static final String HTTP_BAD_REQUEST_MESSAGE = "Bad Request";
   private static final String HTTP_UNAUTHORIZED_CODE = "401";
   private static final String HTTP_UNAUTHORIZED_MESSAGE = "Unauthorized";
   private static final String HTTP_FORBIDDEN_CODE = "403";
   private static final String HTTP_FORBIDDEN_MESSAGE = "!role";
   private static final String HTTP_NOT_FOUND_CODE = "404";
   private static final String HTTP_NOT_FOUND_MESSAGE = "Not Found";
   private static final String HTTP_NOT_ACCEPTABLE_CODE = "406";
   private static final String HTTP_NOT_ACCEPTABLE_MESSAGE = "Not Acceptable";
   private static final String HTTP_INTERNAL_SERVER_ERROR_CODE = "500";
   private static final String HTTP_INTERNAL_SERVER_ERROR_MESSAGE = "Request failed.";
   
   protected static void initRequestSpecifications(final String pBasePath) {
      final String testServerAddress = System.getProperty(PROPERTY_TEST_SERVER_ADDRESS, "localhost");
      RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
      
      for (final RequestSpecificationType requestSpecificationType : RequestSpecificationType.values()) {
         final RequestSpecBuilder reqSpecBuilder = new RequestSpecBuilder();
         reqSpecBuilder.setAccept(requestSpecificationType.getContentType());
         reqSpecBuilder.setContentType(ContentType.JSON);
         if (requestSpecificationType.isSecuredConnection()) {
            reqSpecBuilder.setBaseUri("https://" + testServerAddress);
            reqSpecBuilder.setPort(Integer.parseInt(System.getProperty(PROPERTY_TEST_PORT_HTTPS,
                  String.valueOf(DEFAULT_PORT_HTTPS))));
            reqSpecBuilder.setRelaxedHTTPSValidation();
            final BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
            basicAuthScheme.setUserName(BASIC_AUTH_USERNAME);
            basicAuthScheme.setPassword(BASIC_AUTH_PASSWORD);
            reqSpecBuilder.setAuth(basicAuthScheme);
         } else {
            reqSpecBuilder.setBaseUri("http://" + testServerAddress);
            reqSpecBuilder.setPort(
                  Integer.parseInt(System.getProperty(PROPERTY_TEST_PORT_HTTP, String.valueOf(DEFAULT_PORT_HTTP))));
         }
         reqSpecBuilder.setBasePath(pBasePath);
         // Add filter to log request/response/errors - used for debugging
         if (Boolean.parseBoolean(System.getProperty(PROPERTY_TEST_RESPONSE_DEBUG, Boolean.FALSE.toString()))) {
            reqSpecBuilder.addFilter(new RequestLoggingFilter());
            reqSpecBuilder.addFilter(new ResponseLoggingFilter());
            reqSpecBuilder.addFilter(new ErrorLoggingFilter());
         }
         
         requestSpecifications.put(requestSpecificationType, reqSpecBuilder.build());
      }
   }
   
   protected void assert400Response(final Map<String, Object> pResponseData) {
      assertThat(pResponseData).isNotNull().containsKeys("status", "message");
      assertThat(pResponseData).extractingByKey("status").isInstanceOf(String.class).isEqualTo(HTTP_BAD_REQUEST_CODE);
      assertThat(pResponseData).extractingByKey("message").isInstanceOf(String.class)
            .isEqualTo(HTTP_BAD_REQUEST_MESSAGE);
   }
   
   protected void assert401Response(final Map<String, Object> pResponseData) {
      assertThat(pResponseData).isNotNull().containsKeys("status", "message");
      assertThat(pResponseData).extractingByKey("status").isInstanceOf(String.class).isEqualTo(HTTP_UNAUTHORIZED_CODE);
      assertThat(pResponseData).extractingByKey("message").isInstanceOf(String.class)
            .isEqualTo(HTTP_UNAUTHORIZED_MESSAGE);
   }
   
   protected void assert403Response(final Map<String, Object> pResponseData) {
      assertThat(pResponseData).isNotNull().containsKeys("status", "message");
      assertThat(pResponseData).extractingByKey("status").isInstanceOf(String.class).isEqualTo(HTTP_FORBIDDEN_CODE);
      assertThat(pResponseData).extractingByKey("message").isInstanceOf(String.class)
            .isEqualTo(HTTP_FORBIDDEN_MESSAGE);
   }
   
   protected void assert404Response(final Map<String, Object> pResponseData) {
      assertThat(pResponseData).isNotNull().containsKeys("status", "message");
      assertThat(pResponseData).extractingByKey("status").isInstanceOf(String.class).isEqualTo(HTTP_NOT_FOUND_CODE);
      assertThat(pResponseData).extractingByKey("message").isInstanceOf(String.class).isEqualTo(HTTP_NOT_FOUND_MESSAGE);
   }
   
   protected void assert406Response(final String pResponseData) {
      assertThat(pResponseData).isNotNull();
      assertThat(pResponseData).contains(HTTP_NOT_ACCEPTABLE_CODE, HTTP_NOT_ACCEPTABLE_MESSAGE);
   }
   
   protected void assert500Response(final Map<String, Object> pResponseData) {
      assertThat(pResponseData).isNotNull().containsKeys("status", "message");
      assertThat(pResponseData).extractingByKey("status").isInstanceOf(String.class)
            .isEqualTo(HTTP_INTERNAL_SERVER_ERROR_CODE);
      assertThat(pResponseData).extractingByKey("message").isInstanceOf(String.class)
            .isEqualTo(HTTP_INTERNAL_SERVER_ERROR_MESSAGE);
   }
}
