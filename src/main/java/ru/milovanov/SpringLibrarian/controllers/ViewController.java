package ru.milovanov.SpringLibrarian.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {
    @GetMapping("/books")
    public String getBooksPage(){
        return "books";
    }
    @GetMapping("/users")
    public String getUsersPage(){ return "users";}
    @GetMapping("/")
    public String getHomePage(){ return "home";}
}
