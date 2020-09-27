package test;

import base.BaseApi;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import util.AddNewPet;
import util.DeletePet;
import util.GetPet;
import util.UpdatePet;


public class serviceTest extends BaseApi {

    ExtractableResponse<Response> res;

    private int id = 23523;
    private String categoryName = "petCategoryName123";
    private String petName = "petName12341";
    private int categoryId = 124345;
    private int tagId = 29837465;
    private String tagName = "tagName3452";
    private String serviceEndPoint = "v2/pet";



    @Test
    public void addNewPet(){

        res = AddNewPet.addNewPet("AddNewPet", serviceEndPoint, id, categoryName,
                petName, categoryId, tagId, tagName);


        int id = res.path("id");

        res = GetPet.getPetList(serviceEndPoint, id);

        String responseCategoryName = res.path("category.name");
        Assert.assertEquals("Error!", categoryName, responseCategoryName);

        res = UpdatePet.updatePetName(serviceEndPoint, id,"PetNameUpdate");

        res = GetPet.getPetList(serviceEndPoint, id);

        String updatedPetName = res.path("name");
        Assert.assertEquals("Error!", "PetNameUpdate", updatedPetName);

        res = DeletePet.DeletePet(serviceEndPoint, id, 200);

        res = GetPet.getPetList(serviceEndPoint, id);
        Assert.assertEquals("Pet Did Not Delete", res.path("message"), "Pet not found");
    }

    @Test
    public void addNewPetNegativeScenarios(){

        res = AddNewPet.addNewPet("AddNewPet", serviceEndPoint, id, categoryName,
                petName, categoryId, tagId, tagName);


        int id = res.path("id");

        res = GetPet.getPetList(serviceEndPoint, id);

        String responseCategoryName = res.path("category.name");
        Assert.assertEquals("Error!", categoryName, responseCategoryName);

        res = UpdatePet.updatePetName(serviceEndPoint, id,"PetNameUpdate");

        res = GetPet.getPetList(serviceEndPoint, id);

        String updatedPetName = res.path("name");
        Assert.assertEquals("Error!", "PetNameUpdate", updatedPetName);

        res = DeletePet.DeletePet(serviceEndPoint, id+2534, 404);

    }

}
