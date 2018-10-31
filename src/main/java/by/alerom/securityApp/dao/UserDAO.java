package by.alerom.securityApp.dao;

import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.Role;
import by.alerom.securityApp.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserDAO {
    public User findByLogin(String login);
    public User findById(int id);

    public List<User> findAll();
    public List<User> findAllByChat(Chat chat);

    public ArrayList findUserIdsByChat(Chat chat);

    public void insert(User user);
    public void attachRole(User user, Role role);
}
