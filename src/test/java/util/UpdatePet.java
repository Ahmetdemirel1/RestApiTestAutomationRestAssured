package util;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class UpdatePet {

    private static Logger log = Logger.getLogger(UpdatePet.class);
    private static String logMessage = "";

    /**
     *
     * @param serviceAddress
     * @param id
     * @param petName
     * @return
     */
    public static ExtractableResponse<Response> updatePetName(String serviceAddress, int id, String petName) {
        logMessage = String.format("updatePetName Service endpoint: '%s' '%s'  ", serviceAddress, id);
        log.info(logMessage);
        log.info(String.format(" updatePetName service started."));


        ValidatableResponse updatePet =
                given()
                        //.header("Content-Type", "application/json")
                        //.header("accept", "application/json")
                        .formParam("name", petName)
                        .when()
                        .post(serviceAddress + "/" + id)
                        .then();

        logMessage = String.format("updatePetName service response: '%s' ", updatePet.extract().response().prettyPeek());
        log.info(logMessage);

        ExtractableResponse<Response> extractableResponse = updatePet
                .statusCode(200)
                .and().time(lessThan(7000L))
                .extract();

        return extractableResponse;
    }
}
