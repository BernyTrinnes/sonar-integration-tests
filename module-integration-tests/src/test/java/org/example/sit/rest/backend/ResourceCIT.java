package org.example.sit.rest.backend;

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
import java.util.Objects;

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
 * Tests for the backend REST resource {@code ResourceC}.
 */
class ResourceCIT extends RestResourceTestFactory {
   private static final String PATH_REST_BACKEND_AVAILABILITY = PATH_REST_BACKEND_RESOURCE_C + "/availability";
   
   private static RequestSpecification requestSpecification;
   
   @BeforeAll
   public static void initRequestSpecification() {
      initRequestSpecifications(PATH_REST_BACKEND_BASE);
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
            .delete(PATH_REST_BACKEND_RESOURCE_C)
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
            .get(PATH_REST_BACKEND_AVAILABILITY)
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("available", allOf(instanceOf(Boolean.class), equalTo(Boolean.TRUE)))
            .body("timestamp", allOf(instanceOf(Long.class), greaterThanOrEqualTo(timestampBeforeRequest),
                  lessThan(System.currentTimeMillis())));
   }
   
   @Test
   void test_createResourceC_MalformedData() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body("invalid-json")
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
      .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert500Response(responseData);
      assertNoResourceCAvailable();
   }
   
   private void assertNoResourceCAvailable() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_BACKEND_RESOURCE_C)
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert404Response(responseData);
   }
   
   @Test
   void test_createResourceC_BadRequest() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .contentType(ContentType.JSON)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
      .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert400Response(responseData);
      assertNoResourceCAvailable();
   }
   
   @Test
   void test_createResourceC_Conflict() {
      final Map<String, Object> data = createAndPostRandomResourceCData();
      
      given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
      .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertOneResourceCAvailable(data);
   }
   
   private Map<String, Object> createAndPostRandomResourceCData() {
      final Map<String, Object> data = createRandomResourceCData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
      .then()
            .statusCode(HttpStatus.SC_CREATED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(data, responseData);
      assertOneResourceCAvailable(data);
      return data;
   }
   
   private Map<String, Object> createRandomResourceCData() {
      final Map<String, Object> data = new LinkedHashMap<>();
      data.put("id", RandomUtils.nextLong());
      data.put("param1", RandomStringUtils.randomAlphanumeric(15));
      data.put("param2", RandomUtils.nextInt());
      data.put("param3", RandomStringUtils.randomAlphanumeric(16));
      data.put("param4", RandomStringUtils.randomAlphanumeric(17));
      data.put("param5", RandomUtils.nextBoolean());
      data.put("param6", RandomUtils.nextLong());
      data.put("param7", Collections.singletonList(RandomStringUtils.randomAlphanumeric(7)));
      return data;
   }
   
   private void assertResourceCDataIsCorrect(final Map<String, Object> pData, final Map<String, Object> pResponseData) {
      assertResourceCDataIsCorrect(pData, null, pResponseData);
   }
   
   private void assertResourceCDataIsCorrect(final Map<String, Object> pOrigData,
         final Map<String, Object> pUpdatedData, final Map<String, Object> pResponseData) {
      assertThat(pResponseData).containsOnlyKeys("id", "param1", "param2", "param3", "param4", "param5", "param6",
            "param7");
      assertThat(pResponseData).extractingByKey("id").isInstanceOf(Long.class).isEqualTo(pOrigData.get("id"));
      
      if (Objects.isNull(pUpdatedData) || pUpdatedData.isEmpty()) {
         assertThat(pResponseData).extractingByKey("param1").isInstanceOf(String.class)
               .isEqualTo(pOrigData.get("param1"));
         assertThat(pResponseData).extractingByKey("param2").isInstanceOf(Integer.class)
               .isEqualTo(pOrigData.get("param2"));
         assertThat(pResponseData).extractingByKey("param3").isInstanceOf(String.class)
               .isEqualTo(pOrigData.get("param3"));
         assertThat(pResponseData).extractingByKey("param4").isInstanceOf(String.class)
               .isEqualTo(pOrigData.get("param4"));
         assertThat(pResponseData).extractingByKey("param5").isInstanceOf(Boolean.class)
               .isEqualTo(pOrigData.get("param5"));
         assertThat(pResponseData).extractingByKey("param6").isInstanceOf(Long.class)
               .isEqualTo(pOrigData.get("param6"));
         assertThat(pResponseData).extractingByKey("param7").isInstanceOf(List.class).asList().hasSize(1)
               .isEqualTo(pOrigData.get("param7"));
         return;
      }
      
      if (pUpdatedData.containsKey("param1")) {
         assertThat(pResponseData).extractingByKey("param1").isInstanceOf(String.class)
               .isEqualTo(pUpdatedData.get("param1")).isNotEqualTo(pOrigData.get("param1"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param1");
      }
      if (pUpdatedData.containsKey("param2")) {
         assertThat(pResponseData).extractingByKey("param2").isInstanceOf(Integer.class)
               .isEqualTo(pUpdatedData.get("param2")).isNotEqualTo(pOrigData.get("param2"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param2");
      }
      if (pUpdatedData.containsKey("param3")) {
         assertThat(pResponseData).extractingByKey("param3").isInstanceOf(String.class)
               .isEqualTo(pUpdatedData.get("param3")).isNotEqualTo(pOrigData.get("param3"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param3");
      }
      if (pUpdatedData.containsKey("param4")) {
         assertThat(pResponseData).extractingByKey("param4").isInstanceOf(String.class)
               .isEqualTo(pUpdatedData.get("param4")).isNotEqualTo(pOrigData.get("param4"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param4");
      }
      if (pUpdatedData.containsKey("param5")) {
         assertThat(pResponseData).extractingByKey("param5").isInstanceOf(Boolean.class)
               .isEqualTo(pUpdatedData.get("param5")).isNotEqualTo(pOrigData.get("param5"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param5");
      }
      if (pUpdatedData.containsKey("param6")) {
         assertThat(pResponseData).extractingByKey("param6").isInstanceOf(Long.class)
               .isEqualTo(pUpdatedData.get("param6")).isNotEqualTo(pOrigData.get("param6"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param6");
      }
      if (pUpdatedData.containsKey("param7")) {
         assertThat(pResponseData).extractingByKey("param7").isInstanceOf(List.class).asList().hasSize(1)
               .isEqualTo(pUpdatedData.get("param7")).isNotEqualTo(pOrigData.get("param7"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param7");
      }
   }
   
   private void assertOneResourceCAvailable(final Map<String, Object> pResourceCData) {
      final List<Map<String, Object>> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_BACKEND_RESOURCE_C)
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertThat(responseData).hasSize(1);
      assertResourceCDataIsCorrect(pResourceCData, responseData.get(0));
   }
   
   @Test
   void test_createResourceC_Success() {
      final Map<String, Object> data = createRandomResourceCData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
      .then()
            .statusCode(HttpStatus.SC_CREATED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(data, responseData);
      assertOneResourceCAvailable(data);
   }
   
   @Test
   void test_updateResourceC_IdNotNumber() {
      assertNoResourceCAvailable();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
            .body(createRandomResourceCData())
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(is(emptyOrNullString()));
      
      assertNoResourceCAvailable();
   }
   
   @Test
   void test_updateResourceC_NotFound() {
      assertNoResourceCAvailable();
      
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
            .body(createRandomResourceCData())
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
      
      assertNoResourceCAvailable();
   }
   
   @Test
   void test_updateResourceC_BadRequest() {
      final Map<String, Object> data = createAndPostRandomResourceCData();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertOneResourceCAvailable(data);
   }
   
   @Test
   void test_updateResourceC_AllFields_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param1", RandomStringUtils.randomAlphanumeric(20));
      dataUpdated.put("param2", RandomUtils.nextInt());
      dataUpdated.put("param3", RandomStringUtils.randomAlphanumeric(21));
      dataUpdated.put("param4", RandomStringUtils.randomAlphanumeric(22));
      dataUpdated.put("param5", !((Boolean) dataOrig.get("param5")));
      dataUpdated.put("param6", RandomUtils.nextLong());
      dataUpdated.put("param7", Collections.singletonList(RandomStringUtils.randomAlphanumeric(7)));
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, responseData);
      assertOneResourceCAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceC_param1_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param1", RandomStringUtils.randomAlphanumeric(20));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      dataUpdated.put("param7", dataOrig.get("param7"));
      assertOneResourceCAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceC_param2_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param2", RandomUtils.nextInt());
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      dataUpdated.put("param7", dataOrig.get("param7"));
      assertOneResourceCAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceC_param3_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param3", RandomStringUtils.randomAlphanumeric(21));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      dataUpdated.put("param7", dataOrig.get("param7"));
      assertOneResourceCAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceC_param4_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param4", RandomStringUtils.randomAlphanumeric(22));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      dataUpdated.put("param7", dataOrig.get("param7"));
      assertOneResourceCAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceC_param5_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param5", !((Boolean) dataOrig.get("param5")));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      dataUpdated.put("param7", dataOrig.get("param7"));
      assertOneResourceCAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceC_param6_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param6", RandomUtils.nextLong());
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param7", dataOrig.get("param7"));
      assertOneResourceCAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceC_param7_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceCData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param7", Collections.singletonList(RandomStringUtils.randomAlphanumeric(7)));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceCDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      assertOneResourceCAvailable(dataUpdated);
   }
}
