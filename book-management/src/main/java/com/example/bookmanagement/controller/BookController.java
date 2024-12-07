package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }


    @GetMapping("/book/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/book/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveOrUpdateBook(book);
        return "redirect:/";
    }

    @GetMapping("/book/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "edit-book"; // Return to the edit-book page
        }
        return "redirect:/"; // If book not found, redirect to the book list
    }

        @PostMapping("/book/update/{id}")
        public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book) {
            book.setId(id);
            bookService.saveOrUpdateBook(book);
            return "redirect:/";
        }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }
}
