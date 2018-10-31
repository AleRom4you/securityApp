package by.alerom.securityApp.controller;

import by.alerom.securityApp.config.Config;
import by.alerom.securityApp.dao.ChatDAO;
import by.alerom.securityApp.dao.MessageDAO;
import by.alerom.securityApp.dao.UserDAO;
import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.Message;
import by.alerom.securityApp.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class ChatController {
    private ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");
    private ChatDAO chatDAO = (ChatDAO) context.getBean("chatDAO");
    private UserDAO userDAO = (UserDAO) context.getBean("userDAO");
    private MessageDAO messageDAO = (MessageDAO) context.getBean("messageDAO");

    public boolean isChatSavedById(int id) {
        Chat chat = chatDAO.findById(id);
        return chat != null;
    }

    public boolean isUserFromChat(Chat chat, User user) {
        if(chat.getOwner() != user.getId()) {
            boolean found = false;
            for (User userFromChat : chat.getUsers()) {
                if (userFromChat.getId() == user.getId()) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }

        return true;
    }

    public ArrayList<Chat> getChatsByUser(User user) {
        ArrayList<Chat> chats = chatDAO.findAllByOwner(user);
        chats.addAll(chatDAO.findAllByUser(user));

        for (Chat chat : chats) {
            chat.setUsers((ArrayList<User>) userDAO.findAllByChat(chat));
            chat.setMessages(messageDAO.findAllByChat(chat, 0, 1));
        }

        Collections.sort(chats);
        Collections.reverse(chats);
        return chats;
    }

    public void addUser(Chat chat, User user) {
        chat.addUser(user);

        chatDAO.addUser(chat, user);
    }

    public Chat getChatById(int id, int start) {
        Chat chat = chatDAO.findById(id);
        chat.setUsers((ArrayList<User>) userDAO.findAllByChat(chat));
        ArrayList<Message> messages = messageDAO.findAllByChat(chat, start, Config.paginationLimit);
        Collections.reverse(messages);
        chat.setMessages(messages);

        return chat;
    }

    public Chat getChatAndUsersById(int id) {
        Chat chat = chatDAO.findById(id);
        chat.setUsers((ArrayList<User>) userDAO.findAllByChat(chat));

        return chat;
    }
}
