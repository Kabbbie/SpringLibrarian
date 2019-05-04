package ru.milovanov.SpringLibrarian.dao.interfaces;


import org.springframework.jdbc.core.RowMapper;
import ru.milovanov.SpringLibrarian.dao.objects.Book;

import java.sql.ResultSet;
import java.util.List;

public interface BookDao  {
    RowMapper ROW_MAPPER=(ResultSet resultSet, int rowNum)->{
        return new Book(resultSet
                .getString("isbn")
                ,resultSet.getString("author")
                ,resultSet.getString("title")
                ,resultSet.getString("username"));
    };
    public List<Book> getAll();
    public List<Book> getByUsername(String username);
    public Book getOneByIsbn(String isbn);
    public Book create(Book book);
    public Book update(Book book);
    public int delete(String isbn);
}
