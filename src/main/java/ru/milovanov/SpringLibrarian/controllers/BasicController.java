package ru.milovanov.SpringLibrarian.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.milovanov.SpringLibrarian.ajax.ServiceResponse;
import ru.milovanov.SpringLibrarian.dao.impl.BookDaoImpl;
import ru.milovanov.SpringLibrarian.dao.objects.Book;

import java.util.List;

@RestController
public class BasicController {

    @Autowired
    private BookDaoImpl bRepo;

    @GetMapping("/getPage")
    public  List<Book> page(@RequestParam int page){
        return bRepo.getPageByNum(page);
    }
    @GetMapping("/temp")
    public ResponseEntity<Object> temp(@RequestParam int page){
        List<Book> list=bRepo.getPageByNum(page);
        ServiceResponse<List<Book>> response=new ServiceResponse<>("success",list);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
    @GetMapping("/test")
    public Book test(){
        return bRepo.getOneByIsbn("3");
    }
    @PostMapping("/addBook")
    public ResponseEntity<Object> add(@RequestBody Book book){
        bRepo.create(book);
        ServiceResponse<Book> response = new ServiceResponse<Book>("success", book);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
