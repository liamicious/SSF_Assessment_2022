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
public class BookCacheService {

    @Autowired
    BookRepository bookRepo;

    private final Logger logger = Logger.getLogger(BookCacheService.class.getName());

}
