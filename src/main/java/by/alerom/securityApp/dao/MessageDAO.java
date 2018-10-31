package by.alerom.securityApp.dao;

import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.Message;

import java.util.ArrayList;

public interface MessageDAO {
    public Message findById(int id);
    public Message findByIdAndUserId(int id, int userId);

    public ArrayList<Message> findAllByChat(Chat chat, int start, int limit);

    public void insert(int chatId, int userId, String userName, Message message);
    public void updateById(int id, Message message);
}
