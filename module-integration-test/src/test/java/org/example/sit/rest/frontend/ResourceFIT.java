package org.example.sit.rest.frontend;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpStatus;
import org.example.sit.rest.RequestSpecificationType;
import org.example.sit.rest.RestResourceTestFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the frontend REST resource {@code ResourceF}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceFIT extends RestResourceTestFactory {
   private static RequestSpecification requestSpecification;
   
   @BeforeClass
   public static void initRequestSpecification() {
      initRequestSpecifications(PATH_REST_FRONTEND_BASE);
      requestSpecification = requestSpecifications.get(RequestSpecificationType.JSON_SECURED);
   }
   
   @Before
   public void setUp() {
      // Reset the data before each test
      given().spec(requestSpecification)
            .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_F)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("cleared", equalTo(Boolean.TRUE));
   }
   
   @Test
   public void test_getAllResourceF_NotFound() {
      assertNoResourceFAvailable();
   }
   
   private void assertNoResourceFAvailable() {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F)
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body(isEmptyOrNullString());
   }
   
   @Test
   public void test_getAllResourceF_Success() {
      final Map<String, Object> resourceF = createAndPostRandomResourceFData();
      assertOneResourceFAvailable(resourceF);
   }
   
   private Map<String, Object> createAndPostRandomResourceFData() {
      final Map<String, Object> data = createRandomResourceFData();
      
      given().spec(requestSpecification)
            .body(data)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_FRONTEND_RESOURCE_F)
            .then()
            .statusCode(HttpStatus.SC_CREATED);
      
      return data;
   }
   
   private Map<String, Object> createRandomResourceFData() {
      final Map<String, Object> data = new LinkedHashMap<>();
      data.put("id", RandomUtils.nextLong());
      data.put("param1", RandomStringUtils.randomAlphanumeric(10));
      data.put("param2", RandomUtils.nextInt());
      data.put("param3", RandomStringUtils.randomAlphanumeric(11));
      data.put("param4", RandomStringUtils.randomAlphanumeric(12));
      data.put("param5", RandomUtils.nextBoolean());
      data.put("param6", RandomUtils.nextLong());
      data.put("param7", RandomStringUtils.randomAlphanumeric(13));
      data.put("param8", Collections.singletonList(RandomStringUtils.randomAlphanumeric(5)));
      return data;
   }
   
   private void assertOneResourceFAvailable(final Map<String, Object> pResourceFData) {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("size()", is(1))
            .body("[0].id", allOf(instanceOf(Long.class), equalTo(pResourceFData.get("id"))))
            .body("[0].param1",
                  allOf(instanceOf(String.class), equalTo(pResourceFData.get("param1"))))
            .body("[0].param2",
                  allOf(instanceOf(Integer.class), equalTo(pResourceFData.get("param2"))))
            .body("[0].param3",
                  allOf(instanceOf(String.class), equalTo(pResourceFData.get("param3"))))
            .body("[0].param4",
                  allOf(instanceOf(String.class), equalTo(pResourceFData.get("param4"))))
            .body("[0].param5",
                  allOf(instanceOf(Boolean.class), equalTo(pResourceFData.get("param5"))))
            .body("[0].param6",
                  allOf(instanceOf(Long.class), equalTo(pResourceFData.get("param6"))))
            .body("[0].param7",
                  allOf(instanceOf(String.class), equalTo(pResourceFData.get("param7"))))
            .body("[0].param8", allOf(hasSize(1), equalTo(pResourceFData.get("param8"))));
   }
   
   @Test
   public void test_getResourceF_IdNotNumber() {
      assertNoResourceFAvailable();
      given().spec(requestSpecification)
            .pathParam("id", "no-id")
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_getResourceF_NotFound() {
      assertNoResourceFAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .pathParam("id", id)
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_getResourceF_Success() {
      final Map<String, Object> resourceF = createAndPostRandomResourceFData();
      assertOneResourceFAvailable(resourceF);
      
      given().spec(requestSpecification)
            .pathParam("id", resourceF.get("id"))
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("id", allOf(instanceOf(Long.class), equalTo(resourceF.get("id"))))
            .body("param1", allOf(instanceOf(String.class), equalTo(resourceF.get("param1"))))
            .body("param2", allOf(instanceOf(Integer.class), equalTo(resourceF.get("param2"))))
            .body("param3", allOf(instanceOf(String.class), equalTo(resourceF.get("param3"))))
            .body("param4", allOf(instanceOf(String.class), equalTo(resourceF.get("param4"))))
            .body("param5", allOf(instanceOf(Boolean.class), equalTo(resourceF.get("param5"))))
            .body("param6", allOf(instanceOf(Long.class), equalTo(resourceF.get("param6"))))
            .body("param7", allOf(instanceOf(String.class), equalTo(resourceF.get("param7"))))
            .body("param8", allOf(hasSize(1), equalTo(resourceF.get("param8"))));
   }
}
