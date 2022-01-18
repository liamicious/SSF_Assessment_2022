package sg.nus.iss.ssftest.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.iss.ssftest.Model.BookDetails;
import sg.nus.iss.ssftest.Model.BookSearch;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class.getName());

    public List<BookSearch> Search(String title) {

        List<BookSearch> search = new ArrayList<>();

        String url = UriComponentsBuilder
                .fromUriString("http://openlibrary.org/search.json")
                .queryParam("title", title)
                .queryParam("limit", "20") // limit 20
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error code: %d".formatted(resp.getStatusCode().toString()));
        }

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();

            // do 20 times since max is 20 set in query
            for (int i = 0; i < 20; i++) {
                BookSearch bs = new BookSearch();
                bs.setBookId(result.getJsonArray("docs").getJsonObject(i).getString("key").substring(7));

                bs.setBookTitle(result.getJsonArray("docs").getJsonObject(i).getString("title"));

                search.add(bs);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return search;
    }

    public BookDetails detailSearch(String bookID) {

        String url = UriComponentsBuilder
                // insert bookID into the URL
                .fromUriString("http://openlibrary.org/works/" + bookID + ".json")
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error code: %d".formatted(resp.getStatusCode().toString()));
        }

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();

            String bookdetailID = "";
            try {
                bookdetailID = result.getString("key");
            } catch (Exception e) {
                // bao jiak :D
            }

            String bookTitle = "";
            try {
                bookTitle = result.getString("title");
            } catch (Exception e) {
                // bao jiak :D
            }

            String bookDescription = "";
            try {
                bookDescription = result.getString("description");
            } catch (Exception e) {
            }

            try {
                bookDescription = result.getJsonObject("description").getString("value");
            } catch (Exception e) {
            }

            logger.info("test");

            List<String> bookExcerpt;
            JsonArray array = result.getJsonArray("excerpts");
            if (array != null) {
                bookExcerpt = array.stream()
                        .map(v -> (JsonObject) v)
                        .map(v -> v.getString("excerpt"))
                        .collect(Collectors.toList());
                logger.info("test 2>>");
                return new BookDetails(bookdetailID, bookTitle, bookDescription, bookExcerpt);
            }
            logger.info("test 3>>");
            return new BookDetails(bookdetailID, bookTitle, bookDescription);

        } catch (Exception ex) {
            return new BookDetails();

        }
    }
}
