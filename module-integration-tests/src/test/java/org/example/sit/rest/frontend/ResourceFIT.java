package org.example.sit.rest.frontend;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpStatus;
import org.example.sit.rest.RequestSpecificationType;
import org.example.sit.rest.RestResourceTestFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the frontend REST resource {@code ResourceF}.
 */
class ResourceFIT extends RestResourceTestFactory {
   private static final String PATH_REST_FRONTEND_AVAILABILITY = PATH_REST_FRONTEND_RESOURCE_F + "/availability";
   
   private static RequestSpecification requestSpecification;
   
   @BeforeAll
   public static void initRequestSpecification() {
      initRequestSpecifications(PATH_REST_FRONTEND_BASE);
      requestSpecification = requestSpecifications.get(RequestSpecificationType.JSON_SECURED);
   }
   
   /**
    * Reset the data before each test.
    */
   @BeforeEach
   public void setUp() {
      given()
            .spec(requestSpecification)
      .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_F)
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("cleared", allOf(instanceOf(Boolean.class), equalTo(Boolean.TRUE)));
   }
   
   @Test
   void test_getAvailability_Success() {
      final long timestampBeforeRequest = System.currentTimeMillis();
      
      given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_FRONTEND_AVAILABILITY)
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("available", allOf(instanceOf(Boolean.class), equalTo(Boolean.TRUE)))
            .body("timestamp", allOf(instanceOf(Long.class), greaterThanOrEqualTo(timestampBeforeRequest),
                  lessThan(System.currentTimeMillis())));
   }
   
   @Test
   void test_getAllResourceF_NotFound() {
      assertNoResourceFAvailable();
   }
   
   private void assertNoResourceFAvailable() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F)
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert404Response(responseData);
   }
   
   @Test
   void test_getAllResourceF_Success() {
      createAndPostRandomResourceFData();
   }
   
   private Map<String, Object> createAndPostRandomResourceFData() {
      final Map<String, Object> data = createRandomResourceFData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_FRONTEND_RESOURCE_F)
      .then()
            .statusCode(HttpStatus.SC_CREATED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceFDataIsCorrect(data, responseData);
      assertOneResourceFAvailable(data);
   
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
   
   private void assertResourceFDataIsCorrect(final Map<String, Object> pData, final Map<String, Object> pResponseData) {
      assertThat(pResponseData).extractingByKey("param1").isInstanceOf(String.class).isEqualTo(pData.get("param1"));
      assertThat(pResponseData).extractingByKey("param2").isInstanceOf(Integer.class).isEqualTo(pData.get("param2"));
      assertThat(pResponseData).extractingByKey("param3").isInstanceOf(String.class).isEqualTo(pData.get("param3"));
      assertThat(pResponseData).extractingByKey("param4").isInstanceOf(String.class).isEqualTo(pData.get("param4"));
      assertThat(pResponseData).extractingByKey("param5").isInstanceOf(Boolean.class).isEqualTo(pData.get("param5"));
      assertThat(pResponseData).extractingByKey("param6").isInstanceOf(Long.class).isEqualTo(pData.get("param6"));
      assertThat(pResponseData).extractingByKey("param7").isInstanceOf(String.class).isEqualTo(pData.get("param7"));
      assertThat(pResponseData).extractingByKey("param8").isInstanceOf(List.class).asList().hasSize(1)
            .isEqualTo(pData.get("param8"));
   }
   
   private void assertOneResourceFAvailable(final Map<String, Object> pResourceFData) {
      final List<Map<String, Object>> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F)
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertThat(responseData).hasSize(1);
      assertResourceFDataIsCorrect(pResourceFData, responseData.get(0));
   }
   
   @Test
   void test_getResourceF_IdNotNumber() {
      assertNoResourceFAvailable();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert404Response(responseData);
      assertNoResourceFAvailable();
   }
   
   @Test
   void test_getResourceF_NotFound() {
      assertNoResourceFAvailable();
      
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
      
      assertNoResourceFAvailable();
   }
   
   @Test
   void test_getResourceF_Success() {
      final Map<String, Object> data = createAndPostRandomResourceFData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_F + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceFDataIsCorrect(data, responseData);
      assertOneResourceFAvailable(data);
   }
}
