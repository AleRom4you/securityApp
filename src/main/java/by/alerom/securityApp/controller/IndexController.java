package by.alerom.securityApp.controller;

import by.alerom.securityApp.dao.UserDAO;
import by.alerom.securityApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
//		ApplicationContext context =
//				new ClassPathXmlApplicationContext("Spring-Module.xml");
//        UserDAO userDAO = (UserDAO) context.getBean("userDAO");
//		userDAO.insert(new User("test_2", "password", "TestUser 2"));
//
//		User user = userDAO.findByUserId(1);
//        model.addAttribute("user", user);

        return "index";
    }
}
