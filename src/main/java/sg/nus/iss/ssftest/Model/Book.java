package sg.nus.iss.ssftest.Model;

import jakarta.json.JsonObject;

public class Book {
    private String key; // TBD
    private String title;
    private String description;
    private String excerpt;

    public static Book create(JsonObject o) {
        Book b = new Book();
        b.setKey(o.getString("key"));
        return b;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

}
