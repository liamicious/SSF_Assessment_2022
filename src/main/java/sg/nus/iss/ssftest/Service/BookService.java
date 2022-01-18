package sg.nus.iss.ssftest.Service;

import java.util.List;
import java.util.logging.Logger;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
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
                logger.info(result.getJsonArray("docs").getJsonObject(i).getString("key").substring(7));

                bs.setBookTitle(result.getJsonArray("docs").getJsonObject(i).getString("title"));
                logger.info(result.getJsonArray("docs").getJsonObject(i).getString("title"));

                search.add(bs);
            }

        } catch (Exception ex) {

        }
        return search;
    }
}

/*
 * @Service
 * public class BookService {
 * final Logger logger = Logger.getLogger(BookService.class.getName());
 * 
 * public List<BookSearch> search(String title) {
 * 
 * List<BookSearch> searchResults = new ArrayList<>();
 * 
 * final String url = UriComponentsBuilder
 * .fromUriString("http://openlibrary.org/search.json?")
 * .queryParam("title", title)
 * .queryParam("limit", "20") // limit search to 20
 * .toUriString();
 * 
 * final RequestEntity<Void> req = RequestEntity.get(url).build();
 * final RestTemplate template = new RestTemplate();
 * final ResponseEntity<String> resp = template.exchange(req, String.class);
 * 
 * // Handle illegal args
 * if (resp.getStatusCode() != HttpStatus.OK)
 * throw new IllegalArgumentException(
 * "Error: status code %s".formatted(resp.getStatusCode().toString()));
 * 
 * final String body = resp.getBody();
 * try (InputStream is = new ByteArrayInputStream(body.getBytes("UTF-8"))) {
 * BookSearch bookSearch = new BookSearch();
 * JsonReader reader = Json.createReader(is);
 * JsonObject result = reader.readObject();
 * JsonArray array = result.getJsonArray("docs");
 * return array.stream()
 * .map(v -> (JsonObject) v)
 * .map(BookSearch::create)
 * .collect(Collectors.toList());
 * 
 * } catch (Exception ex) {
 * ex.printStackTrace();
 * }
 * 
 * // else return as empty list
 * return searchResults;
 * }
 * }
 */
