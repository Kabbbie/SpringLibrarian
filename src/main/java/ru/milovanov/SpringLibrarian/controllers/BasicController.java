package ru.milovanov.SpringLibrarian.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.milovanov.SpringLibrarian.ajax.Page;
import ru.milovanov.SpringLibrarian.ajax.ServiceResponse;
import ru.milovanov.SpringLibrarian.dao.impl.BookDaoImpl;
import ru.milovanov.SpringLibrarian.dao.impl.UserDaoImpl;
import ru.milovanov.SpringLibrarian.dao.objects.Book;
import ru.milovanov.SpringLibrarian.dao.objects.User;

import java.util.List;

@RestController
public class BasicController {

    @Autowired
    private BookDaoImpl bRepo;
    @Autowired
    private UserDaoImpl uRepo;


    public String getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("api/getBooksPage")
    public ResponseEntity<Object> getBooksPage(@RequestParam int page,
                                       @RequestParam boolean ascSort,
                                       @RequestParam String sortParam){
        Page currentPage=bRepo.getPageByNum(page,ascSort,sortParam);
        ServiceResponse<Page> response=new ServiceResponse<>("success",getCurrentUser(),currentPage);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("api/getUsersPage")
    public ResponseEntity<Object> getUsersPage(@RequestParam int page){
        Page currentPage=uRepo.getPageByNum(page);
        ServiceResponse<Page> response=new ServiceResponse<>("success",getCurrentUser(),currentPage);
        return new ResponseEntity<Object>(response,HttpStatus.OK);
    }

    @PostMapping("api/createUser")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        String temp=user.getUsername();
        if(uRepo.getUserByUsername(temp)==null){
            String pass=user.getPassword();
            pass=new BCryptPasswordEncoder().encode(pass);
            user.setPassword(pass);
            uRepo.create(user);
            ServiceResponse<User> sucCreateResponse = new ServiceResponse<User>("success",getCurrentUser(), user);
            return new ResponseEntity<Object>(sucCreateResponse, HttpStatus.OK);
        }else{
            ServiceResponse<User> errCreateResponse = new ServiceResponse<User>("error",getCurrentUser(), user);
            return new ResponseEntity<Object>(errCreateResponse, HttpStatus.OK);
        }
    }

    @PostMapping("api/saveBook")
    public ResponseEntity<Object> add(@RequestBody Book book){
        String temp=book.getIsbn();
        if(bRepo.getOneByIsbn(temp)==null){
            bRepo.create(book);
            ServiceResponse<Book> sucCreateResponse = new ServiceResponse<Book>("success-create",getCurrentUser(), book);
            return new ResponseEntity<Object>(sucCreateResponse, HttpStatus.OK);
        }else{
            ServiceResponse<Book> sucUpdateResponse = new ServiceResponse<Book>("error-create",getCurrentUser(), book);
            return new ResponseEntity<Object>(sucUpdateResponse, HttpStatus.OK);
        }
    }

    @PutMapping("api/updateBook")
    public  ResponseEntity<Object> updateBook(@RequestBody Book book){
        ServiceResponse<Book> response;
        String reqIsbn=book.getIsbn();
        Book dbBook=bRepo.getOneByIsbn(reqIsbn);
        String dbIsbn="";
        if(dbBook!=null){
            dbIsbn=dbBook.getIsbn();
            book.setUsername(dbBook.getUsername());
        }
        if(reqIsbn.equals(dbIsbn)){
            book=bRepo.updateInfo(book);
            response=new ServiceResponse<Book>("success",getCurrentUser(),book);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }else{
            response=new ServiceResponse<Book>("error",getCurrentUser(),book);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

    @PutMapping("api/updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody User user){
        ServiceResponse<User> response;
        String reqUsername=user.getUsername();
        User dbUser=uRepo.getUserByUsername(reqUsername);
        String dbUsername="";
        if(dbUser!=null){
            dbUsername=dbUser.getUsername();
        }
        System.out.println(reqUsername.equals(dbUsername));
        if(reqUsername.equals(dbUsername)){
            String pass=user.getPassword();
            pass=new BCryptPasswordEncoder().encode(pass);
            user.setPassword(pass);
            user=uRepo.update(user);
            response=new ServiceResponse<User>("success",getCurrentUser(),user);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }else{
            response=new ServiceResponse<User>("error",getCurrentUser(),user);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

    @PutMapping("api/giveBookToUser")
    public ResponseEntity<Object> giveBookToUser(@RequestParam String isbn){
        System.out.println("mama "+isbn);
        Book book=bRepo.updateUser(isbn,getCurrentUser());
        System.out.println(getCurrentUser());
        ServiceResponse<Book> response = new ServiceResponse<Book>("success",getCurrentUser(), book);
        return new ResponseEntity<Object>(response,HttpStatus.OK);
    }

    @PutMapping("api/returnBookFromUser")
    public ResponseEntity<Object> returnBookFromUser(@RequestParam String isbn){
        Book book=bRepo.updateUser(isbn,null);
        ServiceResponse<Book> response = new ServiceResponse<Book>("success",getCurrentUser(), book);
        return new ResponseEntity<Object>(response,HttpStatus.OK);
    }

    @DeleteMapping("api/deleteBook")
    public ResponseEntity<Object> deleteBook(@RequestParam String isbn){
        Book book=bRepo.getOneByIsbn(isbn);
        ServiceResponse<Book> response;
        int isDelete;

        if(book.getUsername()==null){
            isDelete=bRepo.delete(isbn);
            if(isDelete==1){
                response=new ServiceResponse<>("success",getCurrentUser(),null);
                return new ResponseEntity<Object>(response,HttpStatus.OK);
            }else{
                response=new ServiceResponse<>("deleteError",getCurrentUser(),null);
                return new ResponseEntity<Object>(response,HttpStatus.OK);
            }
        }else{
            response=new ServiceResponse<>("error",getCurrentUser(),null);
            return new ResponseEntity<Object>(response,HttpStatus.OK);
        }
    }

    @DeleteMapping("api/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam String username){
        ServiceResponse<Book> response;
        if(username.equals(getCurrentUser())){
            response=new ServiceResponse<>("current-user-error",getCurrentUser(),null);
            return new ResponseEntity<Object>(response,HttpStatus.OK);
        }
        if(!bRepo.getByUsername(username).isEmpty()){
            response=new ServiceResponse<>("has-book-error",getCurrentUser(),null);
            return new ResponseEntity<Object>(response,HttpStatus.OK);
        }else{
            uRepo.delete(username);
            response=new ServiceResponse<>("success",getCurrentUser(),null);
            return new ResponseEntity<Object>(response,HttpStatus.OK);
        }
    }

}
