package util;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class DeletePet {

    private static Logger log = Logger.getLogger(DeletePet.class);
    private static String logMessage = "";

    /**
     *
     * @param serviceAddress
     * @param id
     * @return
     */
    public static ExtractableResponse<Response> DeletePet(String serviceAddress, int id, int statusCode) {
        logMessage = String.format("DeletePet Service endpoint: '%s' '%s'  ", serviceAddress, id);
        log.info(logMessage);
        log.info(String.format(" DeletePet service started."));


        ValidatableResponse updatePet =
                given()
                        .header("Content-Type", "application/json")
                        .header("accept", "application/json")
                        .when()
                        .delete(serviceAddress + "/" + id)
                        .then();

        logMessage = String.format("DeletePet service response: '%s' ", updatePet.extract().response().prettyPeek());
        log.info(logMessage);

        ExtractableResponse<Response> extractableResponse = updatePet
                .statusCode(statusCode)
                .and().time(lessThan(7000L))
                .extract();

        return extractableResponse;
    }
}
