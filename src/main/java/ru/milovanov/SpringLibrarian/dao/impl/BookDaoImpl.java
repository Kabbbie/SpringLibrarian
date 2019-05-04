package ru.milovanov.SpringLibrarian.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.milovanov.SpringLibrarian.dao.interfaces.BookDao;
import ru.milovanov.SpringLibrarian.dao.objects.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    private Book mapRowToBook(ResultSet resultSet,int rowNum)
            throws SQLException {
        return new Book(
                resultSet.getString("isbn"),
                resultSet.getString("author"),
                resultSet.getString("title"),
                resultSet.getString("username")
        );
    }
    public List<Book> getPageByNum(int pageNum){
        List<Book> page;
        int startElement=1;
        if(pageNum==1){
            startElement=0;
        }
        else{
            startElement=(pageNum-1)*5;
        }
        page=jdbcTemplate.query("select * from books limit "+startElement+",5",this::mapRowToBook);
        return page;
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("select * from books",this::mapRowToBook);
    }

    @Override
    public List<Book> getByUsername(String username) {
        return jdbcTemplate.query("select * from books where username=?",this::mapRowToBook,username);
    }

    @Override
    public Book getOneByIsbn(String isbn) {
        Book book=null;
        try{
            book=jdbcTemplate.queryForObject("select * from books where isbn=?",this::mapRowToBook,isbn);
        }catch(DataAccessException dataAccessExeption){}
        return book;
    }

    @Override
    public Book create(Book book) {
        jdbcTemplate.update("insert into books(isbn,author,title,username) values (?,?,?,?)",
                book.getIsbn(),
                book.getAuthor(),
                book.getTitle(),
                book.getUsername());
        return getOneByIsbn(book.getIsbn());
    }



    @Override
    public Book update(Book book) {
        jdbcTemplate.update("update books set author=?1,title=?2,username=?3 where isbn=?4",
                book.getAuthor(),
                book.getTitle(),
                book.getUsername(),
                book.getIsbn());
        //System.out.println(book.getIsbn());
        return getOneByIsbn(book.getIsbn());
    }

    @Override
    public int delete(String isbn) {
        return jdbcTemplate.update("delete from books where isbn=?",isbn);
    }


}
