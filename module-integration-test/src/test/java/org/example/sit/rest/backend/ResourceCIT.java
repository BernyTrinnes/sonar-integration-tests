package org.example.sit.rest.backend;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

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
 * Tests for the backend REST resource {@code ResourceC}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceCIT extends RestResourceTestFactory {
   private static final String PATH_REST_BACKEND_AVAILABILITY = PATH_REST_BACKEND_RESOURCE_C +
         "/availability";
   
   private static RequestSpecification requestSpecification;
   
   @BeforeClass
   public static void initRequestSpecification() {
      initRequestSpecifications(PATH_REST_BACKEND_BASE);
      requestSpecification = requestSpecifications.get(RequestSpecificationType.JSON_SECURED);
   }
   
   @Before
   public void setUp() {
      // Reset the data before each test
      given().spec(requestSpecification)
            .when()
            .delete(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("cleared", equalTo(Boolean.TRUE));
   }
   
   @Test
   public void test_getAvailability_Success() {
      final long timestampBeforeRequest = System.currentTimeMillis();
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_BACKEND_AVAILABILITY)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body(not(isEmptyOrNullString()))
            .body("available", equalTo(Boolean.TRUE))
            .body("timestamp", allOf(greaterThanOrEqualTo(timestampBeforeRequest),
                  lessThan(System.currentTimeMillis())));
   }
   
   @Test
   public void test_createResourceC_MalformedData() {
      given().spec(requestSpecification)
            .body("garbage")
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      assertNoResourceCAvailable();
   }
   
   private void assertNoResourceCAvailable() {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body(isEmptyOrNullString());
   }
   
   @Test
   public void test_createResourceC_BadRequest() {
      given().spec(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
      assertNoResourceCAvailable();
   }
   
   @Test
   public void test_createResourceC_Conflict() {
      final Map<String, Object> resourceC = createAndPostRandomResourceCData();
      
      given().spec(requestSpecification)
            .body(resourceC)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .and()
            .body("id", equalTo(resourceC.get("id")));
      assertOneResourceCAvailable(resourceC);
   }
   
   private Map<String, Object> createAndPostRandomResourceCData() {
      final Map<String, Object> data = createRandomResourceCData();
      
      given().spec(requestSpecification)
            .body(data)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_CREATED);
      
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
   
   private void assertOneResourceCAvailable(final Map<String, Object> pResourceCData) {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("size()", is(1))
            .body("[0].id", equalTo(pResourceCData.get("id")))
            .body("[0].param1", equalTo(pResourceCData.get("param1")))
            .body("[0].param2", equalTo(pResourceCData.get("param2")))
            .body("[0].param3", equalTo(pResourceCData.get("param3")))
            .body("[0].param4", equalTo(pResourceCData.get("param4")))
            .body("[0].param5", equalTo(pResourceCData.get("param5")))
            .body("[0].param6", equalTo(pResourceCData.get("param6")))
            .body("[0].param7", allOf(hasSize(1), equalTo(pResourceCData.get("param7"))));
   }
   
   @Test
   public void test_createResourceC_Success() {
      final Map<String, Object> resourceC = createRandomResourceCData();
   
      given().spec(requestSpecification)
            .body(resourceC)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_C)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .body("id", equalTo(resourceC.get("id")))
            .body("param1", equalTo(resourceC.get("param1")))
            .body("param2", equalTo(resourceC.get("param2")))
            .body("param3", equalTo(resourceC.get("param3")))
            .body("param4", equalTo(resourceC.get("param4")))
            .body("param5", equalTo(resourceC.get("param5")))
            .body("param6", equalTo(resourceC.get("param6")))
            .body("param7", allOf(hasSize(1), equalTo(resourceC.get("param7"))));
      assertOneResourceCAvailable(resourceC);
   }
   
   @Test
   public void test_updateResourceC_IdNotNumber() {
      assertNoResourceCAvailable();
      given().spec(requestSpecification)
            .body(createRandomResourceCData())
            .contentType(ContentType.JSON)
            .pathParam("id", "no-id")
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_updateResourceC_NotFound() {
      assertNoResourceCAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .body(createRandomResourceCData())
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_updateResourceC_BadRequest() {
      final Map<String, Object> resourceC = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceC);
      given().spec(requestSpecification)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceC.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .and()
            .body("id", equalTo(resourceC.get("id")));
   }
   
   @Test
   public void test_updateResourceC_AllFields_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param1", RandomStringUtils.randomAlphanumeric(20));
      resourceCToUpdate.put("param2", RandomUtils.nextInt());
      resourceCToUpdate.put("param3", RandomStringUtils.randomAlphanumeric(21));
      resourceCToUpdate.put("param4", RandomStringUtils.randomAlphanumeric(22));
      resourceCToUpdate.put("param5", !((Boolean) resourceCOrig.get("param5")).booleanValue());
      resourceCToUpdate.put("param6", RandomUtils.nextLong());
      resourceCToUpdate.put("param7",
            Collections.singletonList(RandomStringUtils.randomAlphanumeric(7)));
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", allOf(equalTo(resourceCToUpdate.get("param1")),
                  not(equalTo(resourceCOrig.get("param1")))))
            .body("param2", allOf(equalTo(resourceCToUpdate.get("param2")),
                  not(equalTo(resourceCOrig.get("param2")))))
            .body("param3", allOf(equalTo(resourceCToUpdate.get("param3")),
                  not(equalTo(resourceCOrig.get("param3")))))
            .body("param4", allOf(equalTo(resourceCToUpdate.get("param4")),
                  not(equalTo(resourceCOrig.get("param4")))))
            .body("param5", allOf(equalTo(resourceCToUpdate.get("param5")),
                  not(equalTo(resourceCOrig.get("param4")))))
            .body("param6", allOf(equalTo(resourceCToUpdate.get("param6")),
                  not(equalTo(resourceCOrig.get("param4")))))
            .body("param7", allOf(hasSize(1), equalTo(resourceCToUpdate.get("param7")),
                  not(equalTo(resourceCOrig.get("param7")))));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
   
   @Test
   public void test_updateResourceC_param1_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param1", RandomStringUtils.randomAlphanumeric(20));
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", allOf(equalTo(resourceCToUpdate.get("param1")),
                  not(equalTo(resourceCOrig.get("param1")))))
            .body("param2", equalTo(resourceCOrig.get("param2")))
            .body("param3", equalTo(resourceCOrig.get("param3")))
            .body("param4", equalTo(resourceCOrig.get("param4")))
            .body("param5", equalTo(resourceCOrig.get("param5")))
            .body("param6", equalTo(resourceCOrig.get("param6")))
            .body("param7", equalTo(resourceCOrig.get("param7")));
      
      resourceCToUpdate.put("param2", resourceCOrig.get("param2"));
      resourceCToUpdate.put("param3", resourceCOrig.get("param3"));
      resourceCToUpdate.put("param4", resourceCOrig.get("param4"));
      resourceCToUpdate.put("param5", resourceCOrig.get("param5"));
      resourceCToUpdate.put("param6", resourceCOrig.get("param6"));
      resourceCToUpdate.put("param7", resourceCOrig.get("param7"));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
   
   @Test
   public void test_updateResourceC_param2_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param2", RandomUtils.nextInt());
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", equalTo(resourceCOrig.get("param1")))
            .body("param2", allOf(equalTo(resourceCToUpdate.get("param2")),
                  not(equalTo(resourceCOrig.get("param2")))))
            .body("param3", equalTo(resourceCOrig.get("param3")))
            .body("param4", equalTo(resourceCOrig.get("param4")))
            .body("param5", equalTo(resourceCOrig.get("param5")))
            .body("param6", equalTo(resourceCOrig.get("param6")))
            .body("param7", equalTo(resourceCOrig.get("param7")));
      
      resourceCToUpdate.put("param1", resourceCOrig.get("param1"));
      resourceCToUpdate.put("param3", resourceCOrig.get("param3"));
      resourceCToUpdate.put("param4", resourceCOrig.get("param4"));
      resourceCToUpdate.put("param5", resourceCOrig.get("param5"));
      resourceCToUpdate.put("param6", resourceCOrig.get("param6"));
      resourceCToUpdate.put("param7", resourceCOrig.get("param7"));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
   
   @Test
   public void test_updateResourceC_param3_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param3", RandomStringUtils.randomAlphanumeric(21));
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", equalTo(resourceCOrig.get("param1")))
            .body("param2", equalTo(resourceCOrig.get("param2")))
            .body("param3", allOf(equalTo(resourceCToUpdate.get("param3")),
                  not(equalTo(resourceCOrig.get("param3")))))
            .body("param4", equalTo(resourceCOrig.get("param4")))
            .body("param5", equalTo(resourceCOrig.get("param5")))
            .body("param6", equalTo(resourceCOrig.get("param6")))
            .body("param7", equalTo(resourceCOrig.get("param7")));
      
      resourceCToUpdate.put("param1", resourceCOrig.get("param1"));
      resourceCToUpdate.put("param2", resourceCOrig.get("param2"));
      resourceCToUpdate.put("param4", resourceCOrig.get("param4"));
      resourceCToUpdate.put("param5", resourceCOrig.get("param5"));
      resourceCToUpdate.put("param6", resourceCOrig.get("param6"));
      resourceCToUpdate.put("param7", resourceCOrig.get("param7"));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
   
   @Test
   public void test_updateResourceC_param4_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param4", RandomStringUtils.randomAlphanumeric(22));
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", equalTo(resourceCOrig.get("param1")))
            .body("param2", equalTo(resourceCOrig.get("param2")))
            .body("param3", equalTo(resourceCOrig.get("param3")))
            .body("param4", allOf(equalTo(resourceCToUpdate.get("param4")),
                  not(equalTo(resourceCOrig.get("param4")))))
            .body("param5", equalTo(resourceCOrig.get("param5")))
            .body("param6", equalTo(resourceCOrig.get("param6")))
            .body("param7", equalTo(resourceCOrig.get("param7")));
      
      resourceCToUpdate.put("param1", resourceCOrig.get("param1"));
      resourceCToUpdate.put("param2", resourceCOrig.get("param2"));
      resourceCToUpdate.put("param3", resourceCOrig.get("param3"));
      resourceCToUpdate.put("param5", resourceCOrig.get("param5"));
      resourceCToUpdate.put("param6", resourceCOrig.get("param6"));
      resourceCToUpdate.put("param7", resourceCOrig.get("param7"));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
   
   @Test
   public void test_updateResourceC_param5_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param5", !((Boolean) resourceCOrig.get("param5")).booleanValue());
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", equalTo(resourceCOrig.get("param1")))
            .body("param2", equalTo(resourceCOrig.get("param2")))
            .body("param3", equalTo(resourceCOrig.get("param3")))
            .body("param4", equalTo(resourceCOrig.get("param4")))
            .body("param5", allOf(equalTo(resourceCToUpdate.get("param5")),
                  not(equalTo(resourceCOrig.get("param5")))))
            .body("param6", equalTo(resourceCOrig.get("param6")))
            .body("param7", equalTo(resourceCOrig.get("param7")));
      
      resourceCToUpdate.put("param1", resourceCOrig.get("param1"));
      resourceCToUpdate.put("param2", resourceCOrig.get("param2"));
      resourceCToUpdate.put("param3", resourceCOrig.get("param3"));
      resourceCToUpdate.put("param4", resourceCOrig.get("param4"));
      resourceCToUpdate.put("param6", resourceCOrig.get("param6"));
      resourceCToUpdate.put("param7", resourceCOrig.get("param7"));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
   
   @Test
   public void test_updateResourceC_param6_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param6", RandomUtils.nextLong());
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", equalTo(resourceCOrig.get("param1")))
            .body("param2", equalTo(resourceCOrig.get("param2")))
            .body("param3", equalTo(resourceCOrig.get("param3")))
            .body("param4", equalTo(resourceCOrig.get("param4")))
            .body("param5", equalTo(resourceCOrig.get("param5")))
            .body("param6", allOf(equalTo(resourceCToUpdate.get("param6")),
                  not(equalTo(resourceCOrig.get("param6")))))
            .body("param7", equalTo(resourceCOrig.get("param7")));
      
      resourceCToUpdate.put("param1", resourceCOrig.get("param1"));
      resourceCToUpdate.put("param2", resourceCOrig.get("param2"));
      resourceCToUpdate.put("param3", resourceCOrig.get("param3"));
      resourceCToUpdate.put("param4", resourceCOrig.get("param4"));
      resourceCToUpdate.put("param5", resourceCOrig.get("param5"));
      resourceCToUpdate.put("param7", resourceCOrig.get("param7"));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
   
   @Test
   public void test_updateResourceC_param7_Success() {
      final Map<String, Object> resourceCOrig = createAndPostRandomResourceCData();
      assertOneResourceCAvailable(resourceCOrig);
      
      final Map<String, Object> resourceCToUpdate = new LinkedHashMap<>();
      resourceCToUpdate.put("id", resourceCOrig.get("id"));
      resourceCToUpdate.put("param7",
            Collections.singletonList(RandomStringUtils.randomAlphanumeric(7)));
      
      given().spec(requestSpecification)
            .body(resourceCToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceCToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_C + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceCToUpdate.get("id")))
            .body("param1", equalTo(resourceCOrig.get("param1")))
            .body("param2", equalTo(resourceCOrig.get("param2")))
            .body("param3", equalTo(resourceCOrig.get("param3")))
            .body("param4", equalTo(resourceCOrig.get("param4")))
            .body("param5", equalTo(resourceCOrig.get("param5")))
            .body("param6", equalTo(resourceCOrig.get("param6")))
            .body("param7", allOf(hasSize(1), equalTo(resourceCToUpdate.get("param7")),
                  not(equalTo(resourceCOrig.get("param7")))));
      
      resourceCToUpdate.put("param1", resourceCOrig.get("param1"));
      resourceCToUpdate.put("param2", resourceCOrig.get("param2"));
      resourceCToUpdate.put("param3", resourceCOrig.get("param3"));
      resourceCToUpdate.put("param4", resourceCOrig.get("param4"));
      resourceCToUpdate.put("param5", resourceCOrig.get("param5"));
      resourceCToUpdate.put("param6", resourceCOrig.get("param6"));
      assertOneResourceCAvailable(resourceCToUpdate);
   }
}
