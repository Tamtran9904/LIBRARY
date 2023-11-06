package sparkminds.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sparkminds.library.entities.Book;
import sparkminds.library.services.impl.BookServiceImpl;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/find-all")
    public Page<Book> findAll(@RequestParam(defaultValue = "0") int number,
        @RequestParam(defaultValue = "10") int size) {
        return bookService.findAll(number, size);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.badRequest().body("Hello");
    }
}
