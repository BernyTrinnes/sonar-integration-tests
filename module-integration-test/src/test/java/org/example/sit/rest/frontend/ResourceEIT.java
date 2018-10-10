package org.example.sit.rest.frontend;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
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
 * Tests for the frontend REST resource {@code ResourceE}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceEIT extends RestResourceTestFactory {
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
            .delete(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("cleared", equalTo(Boolean.TRUE));
   }
   
   @Test
   public void test_getAllResourceE_NotFound() {
      assertNoResourceEAvailable();
   }
   
   private void assertNoResourceEAvailable() {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body(isEmptyOrNullString());
   }
   
   @Test
   public void test_getAllResourceE_Success() {
      final Map<String, Object> resourceE = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceE);
   }
   
   private void assertOneResourceEAvailable(final Map<String, Object> pResourceEData) {
      given().spec(requestSpecification)
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("size()", is(1))
            .body("[0].id", equalTo(pResourceEData.get("id")))
            .body("[0].param1", equalTo(pResourceEData.get("param1")))
            .body("[0].param2", equalTo(pResourceEData.get("param2")))
            .body("[0].param3", equalTo(pResourceEData.get("param3")))
            .body("[0].param4", equalTo(pResourceEData.get("param4")))
            .body("[0].param5", equalTo(pResourceEData.get("param5")))
            .body("[0].param6", allOf(hasSize(1), equalTo(pResourceEData.get("param6"))));
   }
   
   private Map<String, Object> createAndPostRandomResourceEData() {
      final Map<String, Object> data = createRandomResourceEData();
      
      given().spec(requestSpecification)
            .body(data)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_CREATED);
      
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
   
   @Test
   public void test_createResourceE_MalformedData() {
      given().spec(requestSpecification)
            .body("garbage")
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      assertNoResourceEAvailable();
   }
   
   @Test
   public void test_createResourceE_BadRequest() {
      given().spec(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
      assertNoResourceEAvailable();
   }
   
   @Test
   public void test_createResourceE_Conflict() {
      final Map<String, Object> resourceE = createAndPostRandomResourceEData();
      
      given().spec(requestSpecification)
            .body(resourceE)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .and()
            .body("id", equalTo(resourceE.get("id")));
      assertOneResourceEAvailable(resourceE);
   }
   
   @Test
   public void test_createResourceE_Success() {
      final Map<String, Object> resourceE = createRandomResourceEData();
   
      given().spec(requestSpecification)
            .body(resourceE)
            .contentType(ContentType.JSON)
            .when()
            .post(PATH_REST_FRONTEND_RESOURCE_E)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .body("id", equalTo(resourceE.get("id")))
            .body("param1", equalTo(resourceE.get("param1")))
            .body("param2", equalTo(resourceE.get("param2")))
            .body("param3", equalTo(resourceE.get("param3")))
            .body("param4", equalTo(resourceE.get("param4")))
            .body("param5", equalTo(resourceE.get("param5")))
            .body("param6", allOf(hasSize(1), equalTo(resourceE.get("param6"))));
      assertOneResourceEAvailable(resourceE);
   }
   
   @Test
   public void test_getResourceE_IdNotNumber() {
      assertNoResourceEAvailable();
      given().spec(requestSpecification)
            .pathParam("id", "no-id")
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_getResourceE_NotFound() {
      assertNoResourceEAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .pathParam("id", id)
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_getResourceE_Success() {
      final Map<String, Object> resourceE = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceE);
      
      given().spec(requestSpecification)
            .pathParam("id", resourceE.get("id"))
            .when()
            .get(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("id", equalTo(resourceE.get("id")))
            .body("param1", equalTo(resourceE.get("param1")))
            .body("param2", equalTo(resourceE.get("param2")))
            .body("param3", equalTo(resourceE.get("param3")))
            .body("param4", equalTo(resourceE.get("param4")))
            .body("param5", equalTo(resourceE.get("param5")))
            .body("param6", allOf(hasSize(1), equalTo(resourceE.get("param6"))));
   }
   
   @Test
   public void test_deleteResourceE_IdNotNumber() {
      given().spec(requestSpecification)
            .pathParam("id", "no-id")
            .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_deleteResourceE_NotFound() {
      assertNoResourceEAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .pathParam("id", id)
            .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_deleteResourceE_Success() {
      final Map<String, Object> resourceE = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceE);
      
      given().spec(requestSpecification)
            .pathParam("id", resourceE.get("id"))
            .when()
            .delete(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .body("deleted", equalTo(resourceE.get("id")));
   }
   
   @Test
   public void test_updateResourceE_IdNotNumber() {
      assertNoResourceEAvailable();
      given().spec(requestSpecification)
            .body(createRandomResourceEData())
            .contentType(ContentType.JSON)
            .pathParam("id", "no-id")
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
   }
   
   @Test
   public void test_updateResourceE_NotFound() {
      assertNoResourceEAvailable();
      final Long id = RandomUtils.nextLong();
      given().spec(requestSpecification)
            .body(createRandomResourceEData())
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .and()
            .body("id", equalTo(id));
   }
   
   @Test
   public void test_updateResourceE_BadRequest() {
      final Map<String, Object> resourceE = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceE);
      given().spec(requestSpecification)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceE.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .and()
            .body("id", equalTo(resourceE.get("id")));
   }
   
   @Test
   public void test_updateResourceE_AllFields_Success() {
      final Map<String, Object> resourceEOrig = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceEOrig);
      
      final Map<String, Object> resourceEToUpdate = new LinkedHashMap<>();
      resourceEToUpdate.put("id", resourceEOrig.get("id"));
      resourceEToUpdate.put("param1", RandomUtils.nextInt());
      resourceEToUpdate.put("param2", RandomStringUtils.randomAlphanumeric(12));
      resourceEToUpdate.put("param3", RandomStringUtils.randomAlphanumeric(13));
      resourceEToUpdate.put("param4", RandomUtils.nextLong());
      resourceEToUpdate.put("param5", !((Boolean) resourceEOrig.get("param5")).booleanValue());
      resourceEToUpdate.put("param6", Collections.singletonList(RandomUtils.nextInt()));
      
      given().spec(requestSpecification)
            .body(resourceEToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceEToUpdate.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceEToUpdate.get("id")))
            .body("param1", allOf(equalTo(resourceEToUpdate.get("param1")),
                  not(equalTo(resourceEOrig.get("param1")))))
            .body("param2", allOf(equalTo(resourceEToUpdate.get("param2")),
                  not(equalTo(resourceEOrig.get("param2")))))
            .body("param3", allOf(equalTo(resourceEToUpdate.get("param3")),
                  not(equalTo(resourceEOrig.get("param3")))))
            .body("param4", allOf(equalTo(resourceEToUpdate.get("param4")),
                  not(equalTo(resourceEOrig.get("param4")))))
            .body("param5", allOf(equalTo(resourceEToUpdate.get("param5")),
                  not(equalTo(resourceEOrig.get("param5")))))
            .body("param6", allOf(hasSize(1), equalTo(resourceEToUpdate.get("param6")),
                  not(equalTo(resourceEOrig.get("param6")))));
      assertOneResourceEAvailable(resourceEToUpdate);
   }
   
   @Test
   public void test_updateResourceE_param1_Success() {
      final Map<String, Object> resourceEOrig = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceEOrig);
      
      final Map<String, Object> resourceEToUpdate = new LinkedHashMap<>();
      resourceEToUpdate.put("id", resourceEOrig.get("id"));
      resourceEToUpdate.put("param1", RandomUtils.nextInt());
      
      given().spec(requestSpecification)
            .body(resourceEToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceEToUpdate.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceEToUpdate.get("id")))
            .body("param1", allOf(equalTo(resourceEToUpdate.get("param1")),
                  not(equalTo(resourceEOrig.get("param1")))))
            .body("param2", equalTo(resourceEOrig.get("param2")))
            .body("param3", equalTo(resourceEOrig.get("param3")))
            .body("param4", equalTo(resourceEOrig.get("param4")))
            .body("param5", equalTo(resourceEOrig.get("param5")))
            .body("param6", equalTo(resourceEOrig.get("param6")));
      
      resourceEToUpdate.put("param2", resourceEOrig.get("param2"));
      resourceEToUpdate.put("param3", resourceEOrig.get("param3"));
      resourceEToUpdate.put("param4", resourceEOrig.get("param4"));
      resourceEToUpdate.put("param5", resourceEOrig.get("param5"));
      resourceEToUpdate.put("param6", resourceEOrig.get("param6"));
      assertOneResourceEAvailable(resourceEToUpdate);
   }
   
   @Test
   public void test_updateResourceE_param2_Success() {
      final Map<String, Object> resourceEOrig = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceEOrig);
      
      final Map<String, Object> resourceEToUpdate = new LinkedHashMap<>();
      resourceEToUpdate.put("id", resourceEOrig.get("id"));
      resourceEToUpdate.put("param2", RandomStringUtils.randomAlphanumeric(12));
      
      given().spec(requestSpecification)
            .body(resourceEToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceEToUpdate.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceEToUpdate.get("id")))
            .body("param1", equalTo(resourceEOrig.get("param1")))
            .body("param2", allOf(equalTo(resourceEToUpdate.get("param2")),
                  not(equalTo(resourceEOrig.get("param2")))))
            .body("param3", equalTo(resourceEOrig.get("param3")))
            .body("param4", equalTo(resourceEOrig.get("param4")))
            .body("param5", equalTo(resourceEOrig.get("param5")))
            .body("param6", equalTo(resourceEOrig.get("param6")));
      
      resourceEToUpdate.put("param1", resourceEOrig.get("param1"));
      resourceEToUpdate.put("param3", resourceEOrig.get("param3"));
      resourceEToUpdate.put("param4", resourceEOrig.get("param4"));
      resourceEToUpdate.put("param5", resourceEOrig.get("param5"));
      resourceEToUpdate.put("param6", resourceEOrig.get("param6"));
      assertOneResourceEAvailable(resourceEToUpdate);
   }
   
   @Test
   public void test_updateResourceE_param3_Success() {
      final Map<String, Object> resourceEOrig = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceEOrig);
      
      final Map<String, Object> resourceEToUpdate = new LinkedHashMap<>();
      resourceEToUpdate.put("id", resourceEOrig.get("id"));
      resourceEToUpdate.put("param3", RandomStringUtils.randomAlphanumeric(13));
      
      given().spec(requestSpecification)
            .body(resourceEToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceEToUpdate.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceEToUpdate.get("id")))
            .body("param1", equalTo(resourceEOrig.get("param1")))
            .body("param2", equalTo(resourceEOrig.get("param2")))
            .body("param3", allOf(equalTo(resourceEToUpdate.get("param3")),
                  not(equalTo(resourceEOrig.get("param3")))))
            .body("param4", equalTo(resourceEOrig.get("param4")))
            .body("param5", equalTo(resourceEOrig.get("param5")))
            .body("param6", equalTo(resourceEOrig.get("param6")));
      
      resourceEToUpdate.put("param1", resourceEOrig.get("param1"));
      resourceEToUpdate.put("param2", resourceEOrig.get("param2"));
      resourceEToUpdate.put("param4", resourceEOrig.get("param4"));
      resourceEToUpdate.put("param5", resourceEOrig.get("param5"));
      resourceEToUpdate.put("param6", resourceEOrig.get("param6"));
      assertOneResourceEAvailable(resourceEToUpdate);
   }
   
   @Test
   public void test_updateResourceE_param4_Success() {
      final Map<String, Object> resourceEOrig = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceEOrig);
      
      final Map<String, Object> resourceEToUpdate = new LinkedHashMap<>();
      resourceEToUpdate.put("id", resourceEOrig.get("id"));
      resourceEToUpdate.put("param4", RandomUtils.nextLong());
      
      given().spec(requestSpecification)
            .body(resourceEToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceEToUpdate.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceEToUpdate.get("id")))
            .body("param1", equalTo(resourceEOrig.get("param1")))
            .body("param2", equalTo(resourceEOrig.get("param2")))
            .body("param3", equalTo(resourceEOrig.get("param3")))
            .body("param4", allOf(equalTo(resourceEToUpdate.get("param4")),
                  not(equalTo(resourceEOrig.get("param4")))))
            .body("param5", equalTo(resourceEOrig.get("param5")))
            .body("param6", equalTo(resourceEOrig.get("param6")));
      
      resourceEToUpdate.put("param1", resourceEOrig.get("param1"));
      resourceEToUpdate.put("param2", resourceEOrig.get("param2"));
      resourceEToUpdate.put("param3", resourceEOrig.get("param3"));
      resourceEToUpdate.put("param5", resourceEOrig.get("param5"));
      resourceEToUpdate.put("param6", resourceEOrig.get("param6"));
      assertOneResourceEAvailable(resourceEToUpdate);
   }
   
   @Test
   public void test_updateResourceE_param5_Success() {
      final Map<String, Object> resourceEOrig = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceEOrig);
      
      final Map<String, Object> resourceEToUpdate = new LinkedHashMap<>();
      resourceEToUpdate.put("id", resourceEOrig.get("id"));
      resourceEToUpdate.put("param5", !((Boolean) resourceEOrig.get("param5")).booleanValue());
      
      given().spec(requestSpecification)
            .body(resourceEToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceEToUpdate.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceEToUpdate.get("id")))
            .body("param1", equalTo(resourceEOrig.get("param1")))
            .body("param2", equalTo(resourceEOrig.get("param2")))
            .body("param3", equalTo(resourceEOrig.get("param3")))
            .body("param4", equalTo(resourceEOrig.get("param4")))
            .body("param5", allOf(equalTo(resourceEToUpdate.get("param5")),
                  not(equalTo(resourceEOrig.get("param5")))))
            .body("param6", equalTo(resourceEOrig.get("param6")));
      
      resourceEToUpdate.put("param1", resourceEOrig.get("param1"));
      resourceEToUpdate.put("param2", resourceEOrig.get("param2"));
      resourceEToUpdate.put("param3", resourceEOrig.get("param3"));
      resourceEToUpdate.put("param4", resourceEOrig.get("param4"));
      resourceEToUpdate.put("param6", resourceEOrig.get("param6"));
      assertOneResourceEAvailable(resourceEToUpdate);
   }
   
   @Test
   public void test_updateResourceE_param6_Success() {
      final Map<String, Object> resourceEOrig = createAndPostRandomResourceEData();
      assertOneResourceEAvailable(resourceEOrig);
      
      final Map<String, Object> resourceEToUpdate = new LinkedHashMap<>();
      resourceEToUpdate.put("id", resourceEOrig.get("id"));
      resourceEToUpdate.put("param6", Collections.singletonList(RandomUtils.nextInt()));
      
      given().spec(requestSpecification)
            .body(resourceEToUpdate)
            .contentType(ContentType.JSON)
            .pathParam("id", resourceEToUpdate.get("id"))
            .when()
            .put(PATH_REST_FRONTEND_RESOURCE_E + "/{id}")
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            .and()
            .body("id", equalTo(resourceEToUpdate.get("id")))
            .body("param1", equalTo(resourceEOrig.get("param1")))
            .body("param2", equalTo(resourceEOrig.get("param2")))
            .body("param3", equalTo(resourceEOrig.get("param3")))
            .body("param4", equalTo(resourceEOrig.get("param4")))
            .body("param5", equalTo(resourceEOrig.get("param5")))
            .body("param6", allOf(hasSize(1), equalTo(resourceEToUpdate.get("param6")),
                  not(equalTo(resourceEOrig.get("param6")))));
      
      resourceEToUpdate.put("param1", resourceEOrig.get("param1"));
      resourceEToUpdate.put("param2", resourceEOrig.get("param2"));
      resourceEToUpdate.put("param3", resourceEOrig.get("param3"));
      resourceEToUpdate.put("param4", resourceEOrig.get("param4"));
      resourceEToUpdate.put("param5", resourceEOrig.get("param5"));
      assertOneResourceEAvailable(resourceEToUpdate);
   }
}
