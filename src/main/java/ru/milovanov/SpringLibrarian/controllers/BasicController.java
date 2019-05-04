package ru.milovanov.SpringLibrarian.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.milovanov.SpringLibrarian.dao.impl.BookDaoImpl;
import ru.milovanov.SpringLibrarian.dao.objects.Book;

import java.util.List;

@RestController
public class BasicController {
    @Autowired
    private BookDaoImpl bRepo;
    @GetMapping("welcome")
    public String say(){
        return "Hello,mzfcka";
    }
    @GetMapping("temp")
    public  List<Book> page(@RequestParam int page){
        return bRepo.getPageByNum(page);
    }
    @GetMapping("books")
    public List<Book> list(){
        return bRepo.getAll();
    }
    @GetMapping("delete")
    public void delete(){
        String a="2";
        bRepo.delete(a);
    }
    @GetMapping("update")
    public void upd(){
        Book b=bRepo.getOneByIsbn("3");
        b.setUsername("admin");
        bRepo.update(b);
    }
    @GetMapping("create")
    public void create(){
        Book b=new Book("2212","rest","here",null);
        bRepo.create(b);
    }

}
