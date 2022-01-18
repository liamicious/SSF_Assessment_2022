package sg.nus.iss.ssftest.Controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.ssftest.Model.BookDetails;
import sg.nus.iss.ssftest.Service.BookService;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    Logger logger = Logger.getLogger(BookController.class.getName());

    @GetMapping("/book/{bookID}")
    public String getBook(@PathVariable String bookID, Model model) {

        logger.info("started>>>>>>>>" + bookID);

        BookDetails detailBook = bookService.detailSearch(bookID);
        model.addAttribute("detailBook", detailBook);
        model.addAttribute("detailExcerpt", detailBook.getBookExcerpt().toString());
        logger.info(detailBook.getBookId());

        logger.info(detailBook.toString());

        return "book";

    }

}