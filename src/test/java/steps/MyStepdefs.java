package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.JsonArray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.ErrorObject;
import models.User;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.testng.Assert;

import utils.Endpoints;
import utils.TestNGListener;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MyStepdefs {
    User user,user1;
    JSONObject jsonObject,jsonObject1;
    ObjectMapper objectMapper = new ObjectMapper();
    Response response;
    User responseUser;
    JsonPath jsonPath;
    JSONArray jsonArray;


    @Given("user details")
    public void userDetails() {

        jsonObject = (JSONObject) TestNGListener.data.get("createRequest");
    }

    @When("creating a user")
    public void creatingAUser() {

        user = new User((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

    }

    @Then("user must be created")
    public void userMustBeCreated() throws JsonProcessingException {
        response = given()
                .body(user)
                .when().post(Endpoints.addUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        responseUser = objectMapper.readValue(response.asString(), User.class);
        User responseUser = objectMapper.readValue(response.asString(), User.class);
        Assert.assertEquals(user.getName(), responseUser.getName());
    }
    @When("get user")
    public void getUser() {
        response=  given()
                .when().get(Endpoints.getUsersEndpoint)
                .then().body(matchesJsonSchemaInClasspath("user_schema.json"))
                .statusCode(200).extract().response();

    }





    @When("create a user with blank name field")
    public void createAUserWithBlankNameField() {
        user = new User((String) jsonObject.get(""),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
    }

    @Then("display error message name is required")
    public void displayErrorMessageNameIsRequired() {
        response = given()
                .body(user)
                .when().post(Endpoints.addUserEndpoint)
                .then()
                .statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Name is required");
    }

    @When("create a user with blank address field")
    public void createAUserWithBlankAddressField() {
        user = new User((String) jsonObject.get("name"),
                (String) jsonObject.get(""),
                (Long) jsonObject.get("marks"));
    }

    @Then("display error message address is required")
    public void displayErrorMessageAddressIsRequired() {
        response = given()
                .body(user)
                .when().post(Endpoints.addUserEndpoint)
                .then()
                .statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Address is required");
    }

    @When("updating a user")
    public void updatingAUser() throws JsonProcessingException {
        jsonObject = (JSONObject) TestNGListener.data.get("createRequest");
        user = new User((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        response = given()
                .body(user)
                .when().post(Endpoints.addUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(response.asString(), User.class);

        jsonPath = new JsonPath(response.asString());
    }

    @Then("user is updated")
    public void userIsUpdated() throws JsonProcessingException {
        jsonObject = (JSONObject) TestNGListener.data.get("updateRequest");
        int userID;
        user = new User(userID = jsonPath.getInt("id"), (String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        Response putresponse = given()
                .body(user)
                .when().put(Endpoints.updateUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        jsonPath = new JsonPath(putresponse.asString());
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(putresponse.asString(), User.class);
        Assert.assertEquals(user.getName(), "Prazid");
    }


    @When("updating the user name")
    public void updatingTheUser() {

        jsonObject = (JSONObject) TestNGListener.data.get("updateRequest");
        user = new User(1,
                (String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
    }

    @Then("user name is updated")
    public void usernameIsUpdated() throws JsonProcessingException {
        response = given()
                .body(user)
                .when().put(Endpoints.updateUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        responseUser = objectMapper.readValue(response.asString(), User.class);
        User responseUser = objectMapper.readValue(response.asString(), User.class);
        Assert.assertEquals(user.getName(), "Prazid");
        Assert.assertEquals(user.getMarks(), responseUser.getMarks());
    }

    @When("Updating the user address")
    public void updatingTheUserAddress() {

        jsonObject = (JSONObject) TestNGListener.data.get("updateRequest");
        user = new User(1,
                (String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
    }

    @Then("user address is updated")
    public void userAddressIsUpdated() throws JsonProcessingException {
        response = given()
                .body(user)
                .when().put(Endpoints.updateUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        responseUser = objectMapper.readValue(response.asString(), User.class);
        User responseUser = objectMapper.readValue(response.asString(), User.class);
        Assert.assertEquals(user.getAddress(), "22 next level jump street");
    }

    @When("updating the marks")
    public void updatingTheMarks() {
        jsonObject = (JSONObject) TestNGListener.data.get("updateRequest");
        user = new User(1,
                (String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
    }

    @Then("user marks is updated")
    public void userMarksIsUpdated() throws JsonProcessingException {
        response = given()
                .body(user)
                .when().put(Endpoints.updateUserEndpoint)
                .then()
                .statusCode(200).extract().response();

        User responseUser = objectMapper.readValue(response.asString(), User.class);
        Assert.assertEquals(user.getMarks(), 98);
    }
    @When("get updated user")
    public void getDeletedUser() {
        response=  given()
                .when().get("user/1")
                .then()
                .statusCode(200).extract().response();
    }
    @Then("display updated user")
    public void displayBlank() {
        response=  given()
                .when().get("user/1")
                .then()
                .statusCode(200).extract().response();
    }

    @When("update user with blank name")
    public void updateUserWithBlankName() throws JsonProcessingException {
        jsonObject = (JSONObject) TestNGListener.data.get("createRequest");
        user = new User((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        response = given()
                .body(user)
                .when().post(Endpoints.addUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(response.asString(), User.class);

        jsonPath = new JsonPath(response.asString());
    }

    @Then("display error name is required")
    public void displayErrorNameIsRequired() throws JsonProcessingException {
        jsonObject = (JSONObject) TestNGListener.data.get("updateRequest");
        int userID;
        user = new User(userID = jsonPath.getInt("id"), (String) jsonObject.get(""),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        Response putresponse = given()
                .body(user)
                .when().put(Endpoints.updateUserEndpoint)
                .then()
                .statusCode(400).extract().response();

        jsonPath = new JsonPath(putresponse.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Name is required");


    }


    @When("update user with blank address")
    public void updateUserWithBlankAddress() throws JsonProcessingException {
        jsonObject = (JSONObject) TestNGListener.data.get("createRequest");
        user = new User((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));

        response = given()
                .body(user)
                .when().post(Endpoints.addUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(response.asString(), User.class);

        jsonPath = new JsonPath(response.asString());
    }

    @Then("display error address is required")
    public void displayErrorAddressIsRequired() {
        jsonObject = (JSONObject) TestNGListener.data.get("updateRequest");
        int userID;
        user = new User(userID = jsonPath.getInt("id"), (String) jsonObject.get("name"),
                (String) jsonObject.get(""),
                (Long) jsonObject.get("marks"));

        Response putresponse = given()
                .body(user)
                .when().put(Endpoints.updateUserEndpoint)
                .then()
                .statusCode(400).extract().response();

        jsonPath = new JsonPath(putresponse.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Address is required");
    }

    @When("delete a user")
    public void deleteAUser() throws JsonProcessingException {
        jsonObject = (JSONObject) TestNGListener.data.get("createRequest");
        user = new User((String) jsonObject.get("name"),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
        response = given()
                .body(user)
                .when().post(Endpoints.addUserEndpoint)
                .then()
                .statusCode(200).extract().response();
        objectMapper = new ObjectMapper();
        responseUser = objectMapper.readValue(response.asString(), User.class);
    }

    @Then("user is deleted")
    public void userIsDeleted() {
        jsonPath = new JsonPath(response.asString());
        int userID;
        userID = jsonPath.getInt("id");
        response = given()
                .body(user)
                .when().delete(Endpoints.deleteUserEndpoint + "/" + userID)
                .then()
                .statusCode(200).extract().response();
    }

    @When("creating multiple users")
    public void creatingMultipleUsers() throws JsonProcessingException {
        jsonArray = (JSONArray) TestNGListener.data.get("multipleRequest");
        jsonObject=(JSONObject) jsonArray.get(0);
       jsonObject1=(JSONObject) jsonArray.get(1);

       user = new User((String) jsonObject.get("name"),
                    (String) jsonObject.get("address"),
                    (Long) jsonObject.get("marks"));
        user1 = new User((String) jsonObject1.get("name"),
                (String) jsonObject1.get("address"),
                (Long) jsonObject1.get("marks"));
    }
    @Then("multiple users are created")
    public void multipleUsersAreCreated() throws JsonProcessingException {
        User[] array=new User[2];
        array[0]=user;
        array[1]=user1;
        response = given()
                .body(array)
                .when().post(Endpoints.multipleUserEndpoint)
                .then()
                .statusCode(200).extract().response();
    }


    @When("create multiple users with blank name")
    public void createMultipleUsersWithBlankName() {
        jsonArray = (JSONArray) TestNGListener.data.get("multipleRequest");
        jsonObject=(JSONObject) jsonArray.get(0);
        jsonObject1=(JSONObject) jsonArray.get(1);


        user = new User((String) jsonObject.get(""),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
        user1 = new User((String) jsonObject.get(""),
                (String) jsonObject.get("address"),
                (Long) jsonObject.get("marks"));
    }

    @Then("error message name is required")
    public void errorMessageNameIsRequired() {
        User[] array=new User[2];
        array[0]=user;
        array[1]=user1;
        response = given()
                .body(array)
                .when().post(Endpoints.multipleUserEndpoint)
                .then()
                .statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Name is required");
    }

    @When("create multiple users with blank address")
    public void createMultipleUsersWithBlankAddress() {
        jsonArray = (JSONArray) TestNGListener.data.get("multipleRequest");
        jsonObject=(JSONObject) jsonArray.get(0);
        jsonObject1=(JSONObject) jsonArray.get(1);


        user = new User((String) jsonObject.get("name"),
                (String) jsonObject.get(""),
                (Long) jsonObject.get("marks"));
        user1 = new User((String) jsonObject.get("name"),
                (String) jsonObject.get(""),
                (Long) jsonObject.get("marks"));
    }

    @Then("error message address is required")
    public void errorMessageAddressIsRequired() {
        User[] array=new User[2];
        array[0]=user;
        array[1]=user1;
        response = given()
                .body(array)
                .when().post(Endpoints.multipleUserEndpoint)
                .then()
                .statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Address is required");
    }






}
