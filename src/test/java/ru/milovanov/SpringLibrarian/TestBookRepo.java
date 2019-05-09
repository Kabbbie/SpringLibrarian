package ru.milovanov.SpringLibrarian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.milovanov.SpringLibrarian.dao.impl.BookDaoImpl;
import ru.milovanov.SpringLibrarian.dao.interfaces.BookDao;
import ru.milovanov.SpringLibrarian.dao.objects.Book;

public class TestBookRepo {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;
    private BookDao bRepo;
    @Before
    public void setUp(){
        db=new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);

        bRepo= new BookDaoImpl(jdbcTemplate);
    }
    @Test
    public void testFindAll(){
        Assert.assertNotNull(bRepo.getAll());
        int size=bRepo.getAll().size();
        Assert.assertEquals(size,bRepo.getAll().size());
    }
    @Test
    public void testFindOne(){
        Assert.assertNotNull(bRepo.getOneByIsbn("2"));
        Assert.assertNull(bRepo.getOneByIsbn("15"));
    }
    @Test
    public void testCreate(){
        Book book=new Book("2212","misha","karas`",null);
        Assert.assertEquals(book,bRepo.create(book));
    }
    @Test
    public void testUpdate(){
        Book book=bRepo.getOneByIsbn("2");
        book.setUsername("admin");
        Assert.assertEquals(book,bRepo.updateUser("2","admin"));
    }
    @Test
    public void testDelete(){
        Book book=new Book("2212","misha","karas`",null);
        bRepo.create(book);
        bRepo.delete(book.getIsbn());
        Assert.assertNull(bRepo.getOneByIsbn(book.getIsbn()));
    }
    @After
    public void tearDown(){
        db.shutdown();
    }
}
