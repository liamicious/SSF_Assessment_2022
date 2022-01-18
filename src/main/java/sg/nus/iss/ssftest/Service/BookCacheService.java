package sg.nus.iss.ssftest.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.ssftest.Model.*;
import sg.nus.iss.ssftest.Repository.*;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class CacheService {

    @Autowired
    ClassRepository repo;

    private final Logger logger = Logger.getLogger(CacheService.class.getName());




    //For Arrays
        //converting into a list of Weather objects
        List<ClassModel> thatList  = jsonarray.stream()
            .map(v->(JsonObject)v)     //cast as a stream of Json Objects
            .map(ClassModel::createfromJsonArray) //cast as a stream of Weather objects
            .collect(Collectors.toList()); //collect as a Collection List of Weather Objects
        return Optional.of(thatList);



    private JsonArray parseJsonArray(String jsonString){
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            return reader.readArray();            
        } catch (Exception e) {
            //Log errors
        }
        return Json.createArrayBuilder().build();
    }
    
    private JsonObject parseJsonObject(String jsonString){
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            return reader.readObject();            
        } catch (Exception e) {
            //Log errors
        }
        return Json.createObjectBuilder().build();
    }
}
