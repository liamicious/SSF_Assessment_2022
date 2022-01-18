package sg.nus.iss.ssftest.Model;

import java.util.Collections;
import java.util.List;

import jakarta.json.JsonObject;

public class BookDetails {
    public String bookId;
    public String bookTitle;
    public String bookDescription;
    public List bookExcerpt;
    public boolean isCached;

    public BookDetails(String bookId, String bookTitle, String bookDescription) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        isCached = false;
        bookExcerpt = Collections.emptyList();
    }

    public BookDetails(String bookId, String bookTitle, String bookDescription, List bookExcerpt) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.bookExcerpt = bookExcerpt;
        isCached = false;
    }

    public BookDetails() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public List getBookExcerpt() {
        return bookExcerpt;
    }

    public void setBookExcerpt(List bookExcerpt) {
        this.bookExcerpt = bookExcerpt;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean isCached) {
        this.isCached = isCached;
    }

}
