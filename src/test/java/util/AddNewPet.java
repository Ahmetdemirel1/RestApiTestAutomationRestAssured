package util;

import base.BaseApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import pojo.AddNewPetPojo;
import pojo.Category;
import pojo.Tag;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class AddNewPet  {

    private static Logger log = Logger.getLogger(AddNewPet.class);
    private static String logMessage = "";


    /**
     *
     * @param jsonFileName
     * @param serviceAddress
     * @param id
     * @param categoryName
     * @param petName
     * @param categoryId
     * @param tagId
     * @param tagName
     * @return
     */
    public static ExtractableResponse<Response> addNewPet(String jsonFileName, String serviceAddress, int id, String categoryName,
                                                          String petName, int categoryId, int tagId, String tagName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader fileReader;
        String jsonString = "";
        try {
            fileReader = new FileReader("src/test/resources/json/" + jsonFileName + ".json");
            AddNewPetPojo addNewPet = gson.fromJson(fileReader, AddNewPetPojo.class);
            Category category = addNewPet.getCategory();
            Tag[] tag = addNewPet.getTags();
            addNewPet.setId(id);
            category.setId(categoryId);
            category.setName(categoryName);
            addNewPet.setName(petName);
            tag[0].setId(tagId);
            tag[0].setName(tagName);
            jsonString = gson.toJson(addNewPet);
            logMessage = String.format("Service body: '%s' ", jsonString);
            log.info(logMessage);
        }catch (FileNotFoundException e){
            log.error(String.format("Json dosyası bulunamadı! Hata mesajı: '%s'", e));
        }
        log.info(String.format(" AddNewPet service started."));

        ValidatableResponse addNew =
                given()
                    .header("Content-Type","application/json")
                    .header("accept","application/json")
                    .body(jsonString)
                .when()
                    .post(serviceAddress)
                .then();
        logMessage = String.format("AddNewPet service response: '%s' ", addNew.extract().response().prettyPeek());
        log.info(logMessage);

        ExtractableResponse<Response> extractableResponse = addNew
                .statusCode(200)
                .and().time(lessThan(7000L))
                .extract();

        return extractableResponse;
    }


}
