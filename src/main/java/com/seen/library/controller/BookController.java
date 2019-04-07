package com.seen.library.controller;

import com.seen.library.entity.Book;
import com.seen.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String welcome(){
        return "------------热烈欢迎～";
    }


    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getBookList(){
        List<Book> allBooks = bookService.getAllBooks();
        return allBooks;
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    @ResponseBody
    public Book addBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") int price
    ){
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        bookService.addBook(book);
        return book;
    }


}
