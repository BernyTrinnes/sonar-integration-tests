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
 * Tests for the backend REST resource {@code ResourceB}.
 */
class ResourceBIT extends RestResourceTestFactory {
   private static final String PATH_REST_BACKEND_AVAILABILITY = PATH_REST_BACKEND_RESOURCE_B + "/availability";
   
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
            .delete(PATH_REST_BACKEND_RESOURCE_B)
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
   void test_getAllResourceB_NotFound() {
      assertNoResourceBAvailable();
   }
   
   private void assertNoResourceBAvailable() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_BACKEND_RESOURCE_B)
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert404Response(responseData);
   }
   
   @Test
   void test_getAllResourceB_Success() {
      createAndPostRandomResourceBData();
   }
   
   private Map<String, Object> createAndPostRandomResourceBData() {
      final Map<String, Object> data = createRandomResourceBData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
      .then()
            .statusCode(HttpStatus.SC_CREATED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(data, responseData);
      assertOneResourceBAvailable(data);
      
      return data;
   }
   
   private Map<String, Object> createRandomResourceBData() {
      final Map<String, Object> data = new LinkedHashMap<>();
      data.put("id", RandomUtils.nextLong());
      data.put("param1", RandomUtils.nextInt());
      data.put("param2", RandomStringUtils.randomAlphanumeric(10));
      data.put("param3", RandomStringUtils.randomAlphanumeric(11));
      data.put("param4", RandomUtils.nextBoolean());
      data.put("param5", Collections.singletonList(RandomUtils.nextInt()));
      return data;
   }
   
   private void assertResourceBDataIsCorrect(final Map<String, Object> pData, final Map<String, Object> pResponseData) {
      assertResourceBDataIsCorrect(pData, null, pResponseData);
   }
   
   private void assertResourceBDataIsCorrect(final Map<String, Object> pOrigData,
         final Map<String, Object> pUpdatedData, final Map<String, Object> pResponseData) {
      assertThat(pResponseData).containsOnlyKeys("id", "param1", "param2", "param3", "param4", "param5");
      assertThat(pResponseData).extractingByKey("id").isInstanceOf(Long.class).isEqualTo(pOrigData.get("id"));
      
      if (Objects.isNull(pUpdatedData) || pUpdatedData.isEmpty()) {
         assertThat(pResponseData).extractingByKey("param1").isInstanceOf(Integer.class)
               .isEqualTo(pOrigData.get("param1"));
         assertThat(pResponseData).extractingByKey("param2").isInstanceOf(String.class)
               .isEqualTo(pOrigData.get("param2"));
         assertThat(pResponseData).extractingByKey("param3").isInstanceOf(String.class)
               .isEqualTo(pOrigData.get("param3"));
         assertThat(pResponseData).extractingByKey("param4").isInstanceOf(Boolean.class)
               .isEqualTo(pOrigData.get("param4"));
         assertThat(pResponseData).extractingByKey("param5").isInstanceOf(List.class).asList().hasSize(1)
               .isEqualTo(pOrigData.get("param5"));
         return;
      }
      
      if (pUpdatedData.containsKey("param1")) {
         assertThat(pResponseData).extractingByKey("param1").isInstanceOf(Integer.class)
               .isEqualTo(pUpdatedData.get("param1")).isNotEqualTo(pOrigData.get("param1"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param1");
      }
      if (pUpdatedData.containsKey("param2")) {
         assertThat(pResponseData).extractingByKey("param2").isInstanceOf(String.class)
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
         assertThat(pResponseData).extractingByKey("param4").isInstanceOf(Boolean.class)
               .isEqualTo(pUpdatedData.get("param4")).isNotEqualTo(pOrigData.get("param4"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param4");
      }
      if (pUpdatedData.containsKey("param5")) {
         assertThat(pResponseData).extractingByKey("param5").isInstanceOf(List.class).asList().hasSize(1)
               .isEqualTo(pUpdatedData.get("param5")).isNotEqualTo(pOrigData.get("param5"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param5");
      }
   }
   
   private void assertOneResourceBAvailable(final Map<String, Object> pResourceBData) {
      final List<Map<String, Object>> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_BACKEND_RESOURCE_B)
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertThat(responseData).hasSize(1);
      assertResourceBDataIsCorrect(pResourceBData, responseData.get(0));
   }
   
   @Test
   void test_createResourceB_MalformedData() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body("invalid-json")
            .contentType(ContentType.JSON)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
      .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert500Response(responseData);
      assertNoResourceBAvailable();
   }
   
   @Test
   void test_createResourceB_BadRequest() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
      .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert400Response(responseData);
      assertNoResourceBAvailable();
   }
   
   @Test
   void test_createResourceB_Conflict() {
      final Map<String, Object> data = createAndPostRandomResourceBData();
      
      given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
      .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertOneResourceBAvailable(data);
   }
   
   @Test
   void test_createResourceB_Success() {
      final Map<String, Object> data = createRandomResourceBData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
      .then()
            .statusCode(HttpStatus.SC_CREATED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(data, responseData);
      assertOneResourceBAvailable(data);
   }
   
   @Test
   void test_getResourceB_IdNotNumber() {
      assertNoResourceBAvailable();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
      .when()
            .get(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert404Response(responseData);
      assertNoResourceBAvailable();
   }
   
   @Test
   void test_getResourceB_NotFound() {
      assertNoResourceBAvailable();
      
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
      .when()
            .get(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
      
      assertNoResourceBAvailable();
   }
   
   @Test
   void test_getResourceB_Success() {
      final Map<String, Object> data = createAndPostRandomResourceBData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .get(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(data, responseData);
      assertOneResourceBAvailable(data);
   }
   
   @Test
   void test_deleteResourceB_IdNotNumber() {
      assertNoResourceBAvailable();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
      .when()
            .delete(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(is(emptyOrNullString()));
   }
   
   @Test
   void test_deleteResourceB_NotFound() {
      assertNoResourceBAvailable();
      
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
      .when()
            .delete(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
   }
   
   @Test
   void test_deleteResourceB_Success() {
      final Map<String, Object> data = createAndPostRandomResourceBData();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .delete(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("deleted", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertNoResourceBAvailable();
   }
   
   @Test
   void test_updateResourceB_IdNotNumber() {
      assertNoResourceBAvailable();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
            .body(createRandomResourceBData())
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(is(emptyOrNullString()));
      
      assertNoResourceBAvailable();
   }
   
   @Test
   void test_updateResourceB_NotFound() {
      assertNoResourceBAvailable();
      
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
            .body(createRandomResourceBData())
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
      
      assertNoResourceBAvailable();
   }
   
   @Test
   void test_updateResourceB_BadRequest() {
      final Map<String, Object> data = createAndPostRandomResourceBData();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertOneResourceBAvailable(data);
   }
   
   @Test
   void test_updateResourceB_AllFields_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceBData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param1", RandomUtils.nextInt());
      dataUpdated.put("param2", RandomStringUtils.randomAlphanumeric(12));
      dataUpdated.put("param3", RandomStringUtils.randomAlphanumeric(13));
      dataUpdated.put("param4", !((Boolean) dataOrig.get("param4")));
      dataUpdated.put("param5", Collections.singletonList(RandomUtils.nextInt()));
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(dataOrig, dataUpdated, responseData);
      assertOneResourceBAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceB_param1_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceBData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param1", RandomUtils.nextInt());
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      assertOneResourceBAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceB_param2_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceBData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param2", RandomStringUtils.randomAlphanumeric(12));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      assertOneResourceBAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceB_param3_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceBData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param3", RandomStringUtils.randomAlphanumeric(13));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      assertOneResourceBAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceB_param4_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceBData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param4", !((Boolean) dataOrig.get("param4")));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      assertOneResourceBAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceB_param5_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceBData();
   
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param5", Collections.singletonList(RandomUtils.nextInt()));
   
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .body(dataUpdated)
            .contentType(ContentType.JSON)
            .pathParam("id", dataUpdated.get("id"))
      .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceBDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      assertOneResourceBAvailable(dataUpdated);
   }
}
