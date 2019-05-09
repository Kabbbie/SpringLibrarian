package ru.milovanov.SpringLibrarian.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.milovanov.SpringLibrarian.ajax.Page;
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
    public Page getPageByNum(int pageNum,boolean ascSort,String sortParam){
        int totalRecords=getAll().size();
        //System.out.println(totalRecords);
        List<Book> page;
        int startElement=1;
        if(pageNum==1){
            startElement=0;
        }
        else{
            startElement=(pageNum-1)*5;
        }
        if(ascSort){
            page=jdbcTemplate.query("select * from books order by " +sortParam+" asc limit "+startElement+",5",this::mapRowToBook);
        }
        else{
            page=jdbcTemplate.query("select * from books order by " +sortParam+" desc limit "+startElement+",5",this::mapRowToBook);
        }
        return new Page(page,totalRecords);
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
    public Book updateUser(String isbn,String user){
        Book book=getOneByIsbn(isbn);
        book.setUsername(user);
        book=updateInfo(book);
        return book;
    }

    @Override
    public Book updateInfo(Book book) {
        jdbcTemplate.update("update books set author=?,title=?,username=? where isbn=?",
                book.getAuthor(),
                book.getTitle(),
                book.getUsername(),
                book.getIsbn());
        return getOneByIsbn(book.getIsbn());
    }


    @Override
    public int delete(String isbn) {
        return jdbcTemplate.update("delete from books where isbn=?",isbn);
    }


}
