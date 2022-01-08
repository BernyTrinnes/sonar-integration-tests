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
 * Tests for the frontend REST resource {@code ResourceE}.
 */
class ResourceEIT extends RestResourceTestFactory {
   private static final String PATH_REST_FRONTEND_AVAILABILITY = PATH_REST_FRONTEND_RESOURCE_E + "/availability";
   
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
            .delete(PATH_REST_FRONTEND_RESOURCE_E)
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
   void test_getAllResourceE_NotFound() {
      assertNoResourceEAvailable();
   }
   
   private void assertNoResourceEAvailable() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E)
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert404Response(responseData);
   }
   
   @Test
   void test_getAllResourceE_Success() {
      createAndPostRandomResourceEData();
   }
   
   private Map<String, Object> createAndPostRandomResourceEData() {
      final Map<String, Object> data = createRandomResourceEData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body(data)
            .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(data, responseData);
      assertOneResourceEAvailable(data);
      
      return data;
   }
   
   private Map<String, Object> createRandomResourceEData() {
      final Map<String, Object> data = new LinkedHashMap<>();
      data.put("id", RandomUtils.nextLong());
      data.put("param1", RandomUtils.nextInt());
      data.put("param2", RandomStringUtils.randomAlphanumeric(10));
      data.put("param3", RandomStringUtils.randomAlphanumeric(11));
      data.put("param4", RandomUtils.nextLong());
      data.put("param5", RandomUtils.nextBoolean());
      data.put("param6", Collections.singletonList(RandomUtils.nextInt()));
      return data;
   }
   
   private void assertResourceEDataIsCorrect(final Map<String, Object> pData, final Map<String, Object> pResponseData) {
      assertResourceEDataIsCorrect(pData, null, pResponseData);
   }
   
   private void assertResourceEDataIsCorrect(final Map<String, Object> pOrigData,
         final Map<String, Object> pUpdatedData, final Map<String, Object> pResponseData) {
      assertThat(pResponseData).containsOnlyKeys("id", "param1", "param2", "param3", "param4", "param5", "param6");
      assertThat(pResponseData).extractingByKey("id").isInstanceOf(Long.class).isEqualTo(pOrigData.get("id"));
      
      if (Objects.isNull(pUpdatedData) || pUpdatedData.isEmpty()) {
         assertThat(pResponseData).extractingByKey("param1").isInstanceOf(Integer.class)
               .isEqualTo(pOrigData.get("param1"));
         assertThat(pResponseData).extractingByKey("param2").isInstanceOf(String.class)
               .isEqualTo(pOrigData.get("param2"));
         assertThat(pResponseData).extractingByKey("param3").isInstanceOf(String.class)
               .isEqualTo(pOrigData.get("param3"));
         assertThat(pResponseData).extractingByKey("param4").isInstanceOf(Long.class)
               .isEqualTo(pOrigData.get("param4"));
         assertThat(pResponseData).extractingByKey("param5").isInstanceOf(Boolean.class)
               .isEqualTo(pOrigData.get("param5"));
         assertThat(pResponseData).extractingByKey("param6").isInstanceOf(List.class).asList().hasSize(1)
               .isEqualTo(pOrigData.get("param6"));
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
         assertThat(pResponseData).extractingByKey("param4").isInstanceOf(Long.class)
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
         assertThat(pResponseData).extractingByKey("param6").isInstanceOf(List.class).asList().hasSize(1)
               .isEqualTo(pUpdatedData.get("param6")).isNotEqualTo(pOrigData.get("param6"));
      } else {
         assertThat(pUpdatedData).doesNotContainKey("param6");
      }
   }
   
   private void assertOneResourceEAvailable(final Map<String, Object> pResourceEData) {
      final List<Map<String, Object>> responseData = given()
            .spec(requestSpecification)
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E)
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertThat(responseData).hasSize(1);
      assertResourceEDataIsCorrect(pResourceEData, responseData.get(0));
   }
   
   @Test
   void test_createResourceE_MalformedData() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body("invalid-json")
      .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
      .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert500Response(responseData);
      assertNoResourceEAvailable();
   }
   
   @Test
   void test_createResourceE_BadRequest() {
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
      .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
      .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert400Response(responseData);
      assertNoResourceEAvailable();
   }
   
   @Test
   void test_createResourceE_Conflict() {
      final Map<String, Object> data = createAndPostRandomResourceEData();
      
      given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
      .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertOneResourceEAvailable(data);
   }
   
   @Test
   void test_createResourceE_Success() {
      final Map<String, Object> data = createRandomResourceEData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .body(data)
      .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
      .then()
            .statusCode(HttpStatus.SC_CREATED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(data, responseData);
      assertOneResourceEAvailable(data);
   }
   
   @Test
   void test_getResourceE_IdNotNumber() {
      assertNoResourceEAvailable();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assert404Response(responseData);
      assertNoResourceEAvailable();
   }
   
   @Test
   void test_getResourceE_NotFound() {
      assertNoResourceEAvailable();
      
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
      
      assertNoResourceEAvailable();
   }
   
   @Test
   void test_getResourceE_Success() {
      final Map<String, Object> data = createAndPostRandomResourceEData();
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(data, responseData);
      assertOneResourceEAvailable(data);
   }
   
   @Test
   void test_deleteResourceE_IdNotNumber() {
      assertNoResourceEAvailable();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
      .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(is(emptyOrNullString()));
   }
   
   @Test
   void test_deleteResourceE_NotFound() {
      assertNoResourceEAvailable();
   
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
      .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
   }
   
   @Test
   void test_deleteResourceE_Success() {
      final Map<String, Object> data = createAndPostRandomResourceEData();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("deleted", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertNoResourceEAvailable();
   }
   
   @Test
   void test_updateResourceE_IdNotNumber() {
      assertNoResourceEAvailable();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", "no-id")
            .body(createRandomResourceEData())
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(is(emptyOrNullString()));
      
      assertNoResourceEAvailable();
   }
   
   @Test
   void test_updateResourceE_NotFound() {
      assertNoResourceEAvailable();
      
      final Long id = RandomUtils.nextLong();
      given()
            .spec(requestSpecification)
            .pathParam("id", id)
            .body(createRandomResourceEData())
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(id)));
      
      assertNoResourceEAvailable();
   }
   
   @Test
   void test_updateResourceE_BadRequest() {
      final Map<String, Object> data = createAndPostRandomResourceEData();
      
      given()
            .spec(requestSpecification)
            .pathParam("id", data.get("id"))
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .body("id", allOf(instanceOf(Long.class), equalTo(data.get("id"))));
      
      assertOneResourceEAvailable(data);
   }
   
   @Test
   void test_updateResourceE_AllFields_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceEData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param1", RandomUtils.nextInt());
      dataUpdated.put("param2", RandomStringUtils.randomAlphanumeric(12));
      dataUpdated.put("param3", RandomStringUtils.randomAlphanumeric(13));
      dataUpdated.put("param4", RandomUtils.nextLong());
      dataUpdated.put("param5", !((Boolean) dataOrig.get("param5")));
      dataUpdated.put("param6", Collections.singletonList(RandomUtils.nextInt()));
      
      final Map<String, Object> responseData = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(dataOrig, dataUpdated, responseData);
      assertOneResourceEAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceE_param1_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceEData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param1", RandomUtils.nextInt());
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      assertOneResourceEAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceE_param2_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceEData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param2", RandomStringUtils.randomAlphanumeric(12));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      assertOneResourceEAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceE_param3_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceEData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param3", RandomStringUtils.randomAlphanumeric(13));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      assertOneResourceEAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceE_param4_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceEData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param4", RandomUtils.nextLong());
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      assertOneResourceEAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceE_param5_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceEData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param5", !((Boolean) dataOrig.get("param5")));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param6", dataOrig.get("param6"));
      assertOneResourceEAvailable(dataUpdated);
   }
   
   @Test
   void test_updateResourceE_param6_Success() {
      final Map<String, Object> dataOrig = createAndPostRandomResourceEData();
      
      final Map<String, Object> dataUpdated = new LinkedHashMap<>();
      dataUpdated.put("id", dataOrig.get("id"));
      dataUpdated.put("param6", Collections.singletonList(RandomUtils.nextInt()));
      
      final Map<String, Object> dataResponse = given()
            .spec(requestSpecification)
            .pathParam("id", dataUpdated.get("id"))
            .body(dataUpdated)
      .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
      .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .contentType(ContentType.JSON)
            .body(not(is(emptyOrNullString())))
            .extract().as(new TypeRef<>() {});
      
      assertResourceEDataIsCorrect(dataOrig, dataUpdated, dataResponse);
      
      dataUpdated.put("param1", dataOrig.get("param1"));
      dataUpdated.put("param2", dataOrig.get("param2"));
      dataUpdated.put("param3", dataOrig.get("param3"));
      dataUpdated.put("param4", dataOrig.get("param4"));
      dataUpdated.put("param5", dataOrig.get("param5"));
      assertOneResourceEAvailable(dataUpdated);
   }
}
