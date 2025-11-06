package se.yrgo.bookclub.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.yrgo.bookclub.domain.Book;


@Controller
public class BookController {
    private List<Book> books;
    public BookController() {
        this.books = List.of(new Book("The Lord of the Rings", "J.R.R Tolkien", "Fantasy")
                                  ,new Book("Shark Heart", "Emily Habeck", "Drama")
                                  ,new Book("Mort", "Terry Pratchett", "Fantasy")
                                  ,new Book("Reaper Man", "Terry Pratchett", "Fantasy")
                                  ,new Book("Factfulness", "Hans Rosling", "Discussion")
                                  ,new Book("Snowcrash", "Neal Stephenson", "Sci-fi"));
    }
    @GetMapping("/home")
    public ModelAndView welcomePage() {
        LocalDate today = LocalDate.now();
        return new ModelAndView("home", "date", today);
    }

    @GetMapping("/books")
    public ModelAndView getBooks() {
        return new ModelAndView("booklist", "books", books);
    }

    @GetMapping("/genre")
    public ModelAndView getBooksByGenre(@RequestParam(required = false) String type) {
        HashMap<String, ArrayList<Book>> booksByGenre = new HashMap<>();
        for (Book book : books) {
            if(book.getGenre().toLowerCase().equals(type) || type == null) {
                if(booksByGenre.containsKey(book.getGenre())) {
                    booksByGenre.get(book.getGenre()).add(book);
                } else {
                    ArrayList<Book> newList = new ArrayList<>();
                    newList.add(book);
                    booksByGenre.put(book.getGenre(), newList);
                }
            }
        }
        return new ModelAndView("genre", "booksByGenre", booksByGenre);
    } 
}
