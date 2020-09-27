package util;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class GetPet {

    private static Logger log = Logger.getLogger(GetPet.class);
    private static String logMessage = "";

    /**
     *
     * @param serviceAddress
     * @param id
     * @return
     */
    public static ExtractableResponse<Response> getPetList(String serviceAddress, int id) {
        logMessage = String.format("getPet Service endpoint: '%s' '%s'  ", serviceAddress, id);
        log.info(logMessage);
        log.info(String.format(" getPetList service started."));


        ValidatableResponse getPetList =
                given()
                        .header("Content-Type", "application/json")
                        .header("accept", "application/json")

                .when()
                        .get(serviceAddress + "/" + id)

                .then();

        logMessage = String.format("getPetList service response: '%s' ", getPetList.extract().response().prettyPeek());
        log.info(logMessage);

        ExtractableResponse<Response> extractableResponse = getPetList
                .and().time(lessThan(7000L))
                .extract();

        return extractableResponse;
    }

}
