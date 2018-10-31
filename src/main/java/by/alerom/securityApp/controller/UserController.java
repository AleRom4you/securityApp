package by.alerom.securityApp.controller;

import by.alerom.securityApp.dao.RoleDAO;
import by.alerom.securityApp.dao.UserDAO;
import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.User;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class UserController {
    private ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");
    private UserDAO userDAO = (UserDAO) context.getBean("userDAO");
    private RoleDAO roleDAO = (RoleDAO) context.getBean("roleDAO");

    public boolean isUserSavedByLogin(String login) {
        User user = userDAO.findByLogin(login);
        return user != null;
    }

    public User addUser(User user) {
		userDAO.insert(user);

		user = userDAO.findByLogin(user.getLogin());
        userDAO.attachRole(user, roleDAO.findByName("User"));

        return user;
    }

    public User loginUser(User user) {
        User response = userDAO.findByLogin(user.getLogin());

        if (!SCryptUtil.check(user.getPassword(), response.getPassword()))
            return null;

        response.setRole(roleDAO.findById(roleDAO.findRoleIdByUser(response)));

        return response;
    }

    public User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null)
            return null;

        return user;
    }
}
