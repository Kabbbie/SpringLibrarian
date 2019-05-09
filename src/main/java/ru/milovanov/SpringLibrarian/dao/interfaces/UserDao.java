package ru.milovanov.SpringLibrarian.dao.interfaces;

import ru.milovanov.SpringLibrarian.dao.objects.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    User getUserByUsername(String username);
    User create(User user);
    User update(User user);
    int delete(String username);
}
