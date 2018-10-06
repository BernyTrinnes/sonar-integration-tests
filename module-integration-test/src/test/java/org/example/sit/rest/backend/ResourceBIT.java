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
 * Tests for the backend REST resource {@code ResourceB}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceBIT extends RestResourceTestFactory {
   private static final String PATH_REST_BACKEND_AVAILABILITY = PATH_REST_BACKEND_RESOURCE_B +
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
            .delete(PATH_REST_BACKEND_RESOURCE_B)
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
   public void test_getAllResourceB_NotFound() {
      assertNoResourceBAvailable();
   }
   
   private void assertNoResourceBAvailable() {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_BACKEND_RESOURCE_B)
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body(isEmptyOrNullString());
   }
   
   @Test
   public void test_getAllResourceB_Success() {
      final Map<String, Object> resourceB = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceB);
   }
   
   private void assertOneResourceBAvailable(final Map<String, Object> pResourceBData) {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_BACKEND_RESOURCE_B)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("size()", is(1))
            .body("[0].id", equalTo(pResourceBData.get("id")))
            .body("[0].param1", equalTo(pResourceBData.get("param1")))
            .body("[0].param2", equalTo(pResourceBData.get("param2")))
            .body("[0].param3", equalTo(pResourceBData.get("param3")))
            .body("[0].param4", equalTo(pResourceBData.get("param4")))
            .body("[0].param5", allOf(hasSize(1), equalTo(pResourceBData.get("param5"))));
   }
   
   private Map<String, Object> createAndPostRandomResourceBData() {
      final Map<String, Object> data = createRandomResourceBData();
      
      given().spec(requestSpecification)
            .body(data)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
            .then()
            .statusCode(HttpStatus.SC_CREATED);
      
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
   
   @Test
   public void test_createResourceB_MalformedData() {
      given().spec(requestSpecification)
            .body("garbage")
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      assertNoResourceBAvailable();
   }
   
   @Test
   public void test_createResourceB_BadRequest() {
      given().spec(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
      assertNoResourceBAvailable();
   }
   
   @Test
   public void test_createResourceB_Conflict() {
      final Map<String, Object> resourceB = createAndPostRandomResourceBData();
      
      given().spec(requestSpecification)
            .body(resourceB)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
            .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .and()
            .body("id", equalTo(resourceB.get("id")));
      assertOneResourceBAvailable(resourceB);
   }
   
   @Test
   public void test_createResourceB_Success() {
      final Map<String, Object> resourceB = createRandomResourceBData();
   
      given().spec(requestSpecification)
            .body(resourceB)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_BACKEND_RESOURCE_B)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .body("id", equalTo(resourceB.get("id")))
            .body("param1", equalTo(resourceB.get("param1")))
            .body("param2", equalTo(resourceB.get("param2")))
            .body("param3", equalTo(resourceB.get("param3")))
            .body("param4", equalTo(resourceB.get("param4")))
            .body("param5", allOf(hasSize(1), equalTo(resourceB.get("param5"))));
      assertOneResourceBAvailable(resourceB);
   }
   
   @Test
   public void test_getResourceB_IdNotNumber() {
      assertNoResourceBAvailable();
      given().spec(requestSpecification)
            .pathParam("id", "no-id")
            .when()
            .get(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_getResourceB_NotFound() {
      assertNoResourceBAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .pathParam("id", id)
            .when()
            .get(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_getResourceB_Success() {
      final Map<String, Object> resourceB = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceB);
      
      given().spec(requestSpecification)
            .pathParam("id", resourceB.get("id"))
            .when()
            .get(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("id", equalTo(resourceB.get("id")))
            .body("param1", equalTo(resourceB.get("param1")))
            .body("param2", equalTo(resourceB.get("param2")))
            .body("param3", equalTo(resourceB.get("param3")))
            .body("param4", equalTo(resourceB.get("param4")))
            .body("param5", allOf(hasSize(1), equalTo(resourceB.get("param5"))));
   }
   
   @Test
   public void test_deleteResourceB_IdNotNumber() {
      given().spec(requestSpecification)
            .pathParam("id", "no-id")
            .when()
            .delete(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_deleteResourceB_NotFound() {
      assertNoResourceBAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .pathParam("id", id)
            .when()
            .delete(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_deleteResourceB_Success() {
      final Map<String, Object> resourceB = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceB);
      
      given().spec(requestSpecification)
            .pathParam("id", resourceB.get("id"))
            .when()
            .delete(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("deleted", equalTo(resourceB.get("id")));
   }
   
   @Test
   public void test_updateResourceB_IdNotNumber() {
      assertNoResourceBAvailable();
      given().spec(requestSpecification)
            .body(createRandomResourceBData())
            .contentType(ContentType.JSON)
            .pathParam("id", "no-id")
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_updateResourceB_NotFound() {
      assertNoResourceBAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .body(createRandomResourceBData())
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_updateResourceB_BadRequest() {
      final Map<String, Object> resourceB = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceB);
      given().spec(requestSpecification)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceB.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .and()
            .body("id", equalTo(resourceB.get("id")));
   }
   
   @Test
   public void test_updateResourceB_AllFields_Success() {
      final Map<String, Object> resourceBOrig = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceBOrig);
      
      final Map<String, Object> resourceBToUpdate = new LinkedHashMap<>();
      resourceBToUpdate.put("id", resourceBOrig.get("id"));
      resourceBToUpdate.put("param1", RandomUtils.nextInt());
      resourceBToUpdate.put("param2", RandomStringUtils.randomAlphanumeric(12));
      resourceBToUpdate.put("param3", RandomStringUtils.randomAlphanumeric(13));
      resourceBToUpdate.put("param4", !((Boolean) resourceBOrig.get("param4")).booleanValue());
      resourceBToUpdate.put("param5", Collections.singletonList(RandomUtils.nextInt()));
      
      given().spec(requestSpecification)
            .body(resourceBToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceBToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceBToUpdate.get("id")))
            .body("param1", allOf(equalTo(resourceBToUpdate.get("param1")),
                  not(equalTo(resourceBOrig.get("param1")))))
            .body("param2", allOf(equalTo(resourceBToUpdate.get("param2")),
                  not(equalTo(resourceBOrig.get("param2")))))
            .body("param3", allOf(equalTo(resourceBToUpdate.get("param3")),
                  not(equalTo(resourceBOrig.get("param3")))))
            .body("param4", allOf(equalTo(resourceBToUpdate.get("param4")),
                  not(equalTo(resourceBOrig.get("param4")))))
            .body("param5", allOf(hasSize(1), equalTo(resourceBToUpdate.get("param5")),
                  not(equalTo(resourceBOrig.get("param5")))));
      assertOneResourceBAvailable(resourceBToUpdate);
   }
   
   @Test
   public void test_updateResourceB_param1_Success() {
      final Map<String, Object> resourceBOrig = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceBOrig);
      
      final Map<String, Object> resourceBToUpdate = new LinkedHashMap<>();
      resourceBToUpdate.put("id", resourceBOrig.get("id"));
      resourceBToUpdate.put("param1", RandomUtils.nextInt());
      
      given().spec(requestSpecification)
            .body(resourceBToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceBToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceBToUpdate.get("id")))
            .body("param1", allOf(equalTo(resourceBToUpdate.get("param1")),
                  not(equalTo(resourceBOrig.get("param1")))))
            .body("param2", equalTo(resourceBOrig.get("param2")))
            .body("param3", equalTo(resourceBOrig.get("param3")))
            .body("param4", equalTo(resourceBOrig.get("param4")))
            .body("param5", equalTo(resourceBOrig.get("param5")));
      
      resourceBToUpdate.put("param2", resourceBOrig.get("param2"));
      resourceBToUpdate.put("param3", resourceBOrig.get("param3"));
      resourceBToUpdate.put("param4", resourceBOrig.get("param4"));
      resourceBToUpdate.put("param5", resourceBOrig.get("param5"));
      assertOneResourceBAvailable(resourceBToUpdate);
   }
   
   @Test
   public void test_updateResourceB_param2_Success() {
      final Map<String, Object> resourceBOrig = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceBOrig);
      
      final Map<String, Object> resourceBToUpdate = new LinkedHashMap<>();
      resourceBToUpdate.put("id", resourceBOrig.get("id"));
      resourceBToUpdate.put("param2", RandomStringUtils.randomAlphanumeric(12));
      
      given().spec(requestSpecification)
            .body(resourceBToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceBToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceBToUpdate.get("id")))
            .body("param1", equalTo(resourceBOrig.get("param1")))
            .body("param2", allOf(equalTo(resourceBToUpdate.get("param2")),
                  not(equalTo(resourceBOrig.get("param2")))))
            .body("param3", equalTo(resourceBOrig.get("param3")))
            .body("param4", equalTo(resourceBOrig.get("param4")))
            .body("param5", equalTo(resourceBOrig.get("param5")));
      
      resourceBToUpdate.put("param1", resourceBOrig.get("param1"));
      resourceBToUpdate.put("param3", resourceBOrig.get("param3"));
      resourceBToUpdate.put("param4", resourceBOrig.get("param4"));
      resourceBToUpdate.put("param5", resourceBOrig.get("param5"));
      assertOneResourceBAvailable(resourceBToUpdate);
   }
   
   @Test
   public void test_updateResourceB_param3_Success() {
      final Map<String, Object> resourceBOrig = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceBOrig);
      
      final Map<String, Object> resourceBToUpdate = new LinkedHashMap<>();
      resourceBToUpdate.put("id", resourceBOrig.get("id"));
      resourceBToUpdate.put("param3", RandomStringUtils.randomAlphanumeric(13));
      
      given().spec(requestSpecification)
            .body(resourceBToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceBToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceBToUpdate.get("id")))
            .body("param1", equalTo(resourceBOrig.get("param1")))
            .body("param2", equalTo(resourceBOrig.get("param2")))
            .body("param3", allOf(equalTo(resourceBToUpdate.get("param3")),
                  not(equalTo(resourceBOrig.get("param3")))))
            .body("param4", equalTo(resourceBOrig.get("param4")))
            .body("param5", equalTo(resourceBOrig.get("param5")));
      
      resourceBToUpdate.put("param1", resourceBOrig.get("param1"));
      resourceBToUpdate.put("param2", resourceBOrig.get("param2"));
      resourceBToUpdate.put("param4", resourceBOrig.get("param4"));
      resourceBToUpdate.put("param5", resourceBOrig.get("param5"));
      assertOneResourceBAvailable(resourceBToUpdate);
   }
   
   @Test
   public void test_updateResourceB_param4_Success() {
      final Map<String, Object> resourceBOrig = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceBOrig);
      
      final Map<String, Object> resourceBToUpdate = new LinkedHashMap<>();
      resourceBToUpdate.put("id", resourceBOrig.get("id"));
      resourceBToUpdate.put("param4", !((Boolean) resourceBOrig.get("param4")).booleanValue());
      
      given().spec(requestSpecification)
            .body(resourceBToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceBToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceBToUpdate.get("id")))
            .body("param1", equalTo(resourceBOrig.get("param1")))
            .body("param2", equalTo(resourceBOrig.get("param2")))
            .body("param3", equalTo(resourceBOrig.get("param3")))
            .body("param4", allOf(equalTo(resourceBToUpdate.get("param4")),
                  not(equalTo(resourceBOrig.get("param4")))))
            .body("param5", equalTo(resourceBOrig.get("param5")));
      
      resourceBToUpdate.put("param1", resourceBOrig.get("param1"));
      resourceBToUpdate.put("param2", resourceBOrig.get("param2"));
      resourceBToUpdate.put("param3", resourceBOrig.get("param3"));
      resourceBToUpdate.put("param5", resourceBOrig.get("param5"));
      assertOneResourceBAvailable(resourceBToUpdate);
   }
   
   @Test
   public void test_updateResourceB_param5_Success() {
      final Map<String, Object> resourceBOrig = createAndPostRandomResourceBData();
      assertOneResourceBAvailable(resourceBOrig);
      
      final Map<String, Object> resourceBToUpdate = new LinkedHashMap<>();
      resourceBToUpdate.put("id", resourceBOrig.get("id"));
      resourceBToUpdate.put("param5", Collections.singletonList(RandomUtils.nextInt()));
      
      given().spec(requestSpecification)
            .body(resourceBToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceBToUpdate.get("id"))
            .when()
            .put(PATH_REST_BACKEND_RESOURCE_B + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceBToUpdate.get("id")))
            .body("param1", equalTo(resourceBOrig.get("param1")))
            .body("param2", equalTo(resourceBOrig.get("param2")))
            .body("param3", equalTo(resourceBOrig.get("param3")))
            .body("param4", equalTo(resourceBOrig.get("param4")))
            .body("param5", allOf(hasSize(1), equalTo(resourceBToUpdate.get("param5")),
                  not(equalTo(resourceBOrig.get("param5")))));
      
      resourceBToUpdate.put("param1", resourceBOrig.get("param1"));
      resourceBToUpdate.put("param2", resourceBOrig.get("param2"));
      resourceBToUpdate.put("param3", resourceBOrig.get("param3"));
      resourceBToUpdate.put("param4", resourceBOrig.get("param4"));
      assertOneResourceBAvailable(resourceBToUpdate);
   }
}
