package by.alerom.securityApp.dao;

import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.User;

import java.util.ArrayList;

public interface ChatDAO {
    public Chat findById(int id);

    public ArrayList<Chat> findAllByOwner(User user);
    public ArrayList<Chat> findAllByUser(User user);

    public ArrayList findChatIdsByUser(User user);

    public void addUser(Chat chat, User user);
    public void updateDate(int id);
}
