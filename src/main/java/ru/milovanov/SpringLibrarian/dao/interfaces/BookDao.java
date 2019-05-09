package ru.milovanov.SpringLibrarian.dao.interfaces;


import ru.milovanov.SpringLibrarian.dao.objects.Book;

import java.util.List;

public interface BookDao  {
    public List<Book> getAll();
    public List<Book> getByUsername(String username);
    public Book getOneByIsbn(String isbn);
    public Book create(Book book);
    public Book updateUser(String isbn,String user);
    public Book updateInfo(Book book);
    public int delete(String isbn);
}
