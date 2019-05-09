package ru.milovanov.SpringLibrarian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.milovanov.SpringLibrarian.dao.impl.UserDaoImpl;
import ru.milovanov.SpringLibrarian.dao.interfaces.UserDao;
import ru.milovanov.SpringLibrarian.dao.objects.User;

public class TestUserRepo {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;
    private UserDao uRepo;
    @Before
    public void setUp(){
        db=new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);

        uRepo= new UserDaoImpl(jdbcTemplate);
    }
    @Test
    public void testGetAll(){
        Assert.assertNotNull(uRepo.getAll());
        int size=uRepo.getAll().size();
        Assert.assertEquals(size,uRepo.getAll().size());
    }
    @Test
    public void testGetOne(){
        Assert.assertNotNull(uRepo.getUserByUsername("admin"));
        Assert.assertNull(uRepo.getUserByUsername("no-exist"));
    }
    @Test
    public void testCreate(){
        User user=new User("me","1535");
        Assert.assertEquals(user,uRepo.create(user));
    }
    @Test
    public void testUpdate(){
        User user=uRepo.getUserByUsername("admin");
        user.setPassword("qwerty");
        Assert.assertEquals(user,uRepo.update(user));
    }
    @Test
    public void testDelete(){
        //Assert.assertNotNull(uRepo.getOne("me"));
        User user=new User("me","qwerty");
        uRepo.create(user);
        uRepo.delete(user.getUsername());
        Assert.assertNull(uRepo.getUserByUsername(user.getUsername()));
    }
    @After
    public void tearDown(){
        db.shutdown();
    }
}
