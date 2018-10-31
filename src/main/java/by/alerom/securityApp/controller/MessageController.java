package by.alerom.securityApp.controller;

import by.alerom.securityApp.dao.ChatDAO;
import by.alerom.securityApp.dao.MessageDAO;
import by.alerom.securityApp.model.Message;
import by.alerom.securityApp.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    private ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");
    private MessageDAO messageDAO = (MessageDAO) context.getBean("messageDAO");
    private ChatDAO chatDAO = (ChatDAO) context.getBean("chatDAO");

    public void addMessage(Message message) {
        messageDAO.insert(message.getChatId(), message.getUserId(), message.getUserName(), message);
        chatDAO.updateDate(message.getChatId());
    }

    public void updateMessage(Message message) {
        messageDAO.updateById(message.getId(), message);
    }

    public boolean isMessageFromUser(int id, int userId) {
        Message message = messageDAO.findByIdAndUserId(id, userId);
        return message != null;
    }
}
