package ru.milovanov.SpringLibrarian.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.milovanov.SpringLibrarian.dao.interfaces.UserDao;
import ru.milovanov.SpringLibrarian.dao.objects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate){this.jdbcTemplate=jdbcTemplate;}

    private User mapRowToUser(ResultSet resultSet,int rowNum)
            throws SQLException {
        return new User(
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }
    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users",this::mapRowToUser);
    }

    @Override
    public User getOne(String username) {
        User user=null;
        try{
            user=jdbcTemplate.queryForObject("select * from users where username=?",this::mapRowToUser,username);
        }catch(DataAccessException dataAccessExeption){}
        return user;
    }

    @Override
    public User create(User user) {
        jdbcTemplate.update("insert into users(username,password) values (?,?)",
                user.getUsername(),
                user.getPassword());
        return getOne(user.getUsername());
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update("update users set password=? where username=?",
                user.getPassword(),
                user.getUsername());
        return getOne(user.getUsername());
    }

    @Override
    public int delete(String username) {
        return jdbcTemplate.update("delete from users where username=?",username);
    }
}
