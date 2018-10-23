package by.alerom.securityApp.dao;

import by.alerom.securityApp.model.User;

import java.util.List;

public interface UserDAO {
    public List<User> findAll();

    public User findByUserLogin(String login);
    public User findByUserId(int id);

    public void insert(User user);
}
