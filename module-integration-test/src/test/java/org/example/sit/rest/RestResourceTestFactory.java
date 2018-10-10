package org.example.sit.rest;

import java.util.HashMap;
import java.util.Map;

import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * Base class for all tests of the REST resources.
 */
@SuppressWarnings("javadoc")
public abstract class RestResourceTestFactory {
   protected static Map<RequestSpecificationType, RequestSpecification> requestSpecifications =
         new HashMap<>(RequestSpecificationType.values().length);
   protected static final String BASIC_AUTH_USERNAME = "rest";
   protected static final String BASIC_AUTH_PASSWORD = "rest";
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
   
   protected static void initRequestSpecifications(final String pBasePath) {
      final String testServerAddress =
            System.getProperty(PROPERTY_TEST_SERVER_ADDRESS, "localhost");
      
      for (final RequestSpecificationType requestSpecificationType : RequestSpecificationType
            .values()) {
         final RequestSpecBuilder reqSpecBuilder = new RequestSpecBuilder();
         reqSpecBuilder.setAccept(requestSpecificationType.getContentType());
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
            reqSpecBuilder.setPort(Integer.parseInt(
                  System.getProperty(PROPERTY_TEST_PORT_HTTP, String.valueOf(DEFAULT_PORT_HTTP))));
         }
         reqSpecBuilder.setBasePath(pBasePath);
         
         requestSpecifications.put(requestSpecificationType, reqSpecBuilder.build());
      }
   }
}
