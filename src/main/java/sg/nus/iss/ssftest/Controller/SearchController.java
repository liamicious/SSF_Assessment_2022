package sg.nus.iss.ssftest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.ssftest.Service.BookService;

import static sg.nus.iss.ssftest.Constants.*;

@Controller
public class SearchController {

    @Autowired
    BookService bookService;

    @GetMapping("/book")
    public String getBook(@RequestParam(required = true) String title, Model model) {
        String normalizeTitle = title.trim().replaceAll(" ", "+");
        model.addAttribute("searchTitleName", title);
        model.addAttribute("searchBookIdList", bookService.Search(normalizeTitle));
        return "SearchResult";
    }

}
