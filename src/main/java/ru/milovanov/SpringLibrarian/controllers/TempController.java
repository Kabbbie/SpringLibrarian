package ru.milovanov.SpringLibrarian.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.milovanov.SpringLibrarian.dao.impl.BookDaoImpl;
import ru.milovanov.SpringLibrarian.dao.objects.Book;

import java.util.List;

@Controller
@RequestMapping("/book")
public class TempController {
    @Autowired
    BookDaoImpl bRepo;
    @GetMapping
    //@ResponseBody
    public String list(ModelAndView model){
        model.addObject("result",bRepo.getAll());
        //return bRepo.getAll();
        return "books";
    }
}
