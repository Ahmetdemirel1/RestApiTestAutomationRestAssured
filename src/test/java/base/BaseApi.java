package base;

import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;

public class BaseApi {

    private Logger log = Logger.getLogger(BaseApi.class);
    private String logMessage = "";


    @Before
    public void beforeTest(){
        PropertyConfigurator.configure("src/test/resources/log4j.properties");

        RestAssured.baseURI = "https://petstore.swagger.io/";

        logMessage = String.format("Web Service test started.");
        log.info(logMessage);
    }


    @After
    public void afterTest(){

        RestAssured.reset();
        logMessage = String.format("Web Service test finished.");
        log.info(logMessage);
    }

}
