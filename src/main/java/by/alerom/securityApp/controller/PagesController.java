package by.alerom.securityApp.controller;

import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.Message;
import by.alerom.securityApp.model.User;
import by.alerom.securityApp.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PagesController {
    private ApplicationContext context =
				new ClassPathXmlApplicationContext("Spring-Module.xml");
    private ValidateController validate = new ValidateController();
    private UserController userController = new UserController();
    private ChatController chatController = new ChatController();
    private MessageController messageController = new MessageController();

    @Autowired
    private RecaptchaService recaptchaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {
        User user = userController.getUserFromSession(session);
        if (user == null)
            return "login";

        model.addAttribute("chats", chatController.getChatsByUser(user));
        return "chats";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        User user = userController.getUserFromSession(session);
        if (user != null)
            return "redirect:/";

        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model, HttpSession session) {
        User user = userController.getUserFromSession(session);
        if (user != null)
            return "redirect:/";

        return "register";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String chat(Model model,
                        HttpSession session,
                        @PathVariable("id") String id) {
        User user = userController.getUserFromSession(session);
        if (user == null)
            return "login";

        if(!validate.isNumber(id)) {
            model.addAttribute("error", "Неверный идентификатор чата!");
            model.addAttribute("user", user);
            model.addAttribute("chats", chatController.getChatsByUser(user));
            return "chats";
        }

        if(!chatController.isChatSavedById(Integer.parseInt(id))) {
            model.addAttribute("error", "Такого чата ещё не было!");
            model.addAttribute("user", user);
            model.addAttribute("chats", chatController.getChatsByUser(user));
            return "chats";
        }

        Chat chat = chatController.getChatById(Integer.parseInt(id), 0);
        if (!chatController.isUserFromChat(chat, user)) {
            model.addAttribute("error", "Вас в этом чате не ждут!");
            model.addAttribute("user", user);
            model.addAttribute("chats", chatController.getChatsByUser(user));
            return "chats";
        }

        model.addAttribute("user", user);
        model.addAttribute("chats", chatController.getChatsByUser(user));
        model.addAttribute("chat", chat);
        return "chats";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(Model model,
                            @RequestParam(name="login") String login,
                            @RequestParam(name="password") String password,
                            @RequestParam(name="g-recaptcha-response") String recaptchaResponse,
                            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage = recaptchaService.verifyRecaptcha(ip, recaptchaResponse);

//        if (!StringUtils.isEmpty(captchaVerifyMessage)) {
//            System.out.println(captchaVerifyMessage);
//            model.addAttribute("error", "Google говорит Вы не человек!");
//            return "login";
//        }

        login = validate.login(login);
        if (login == null) {
            model.addAttribute("error", "Проверьте введённый логин!");
            return "login";
        }

        if (validate.password(password) == null) {
            model.addAttribute("error", "Проверьте введённый пароль!");
            return "login";
        }

        User user = userController.loginUser(new User(login, password));
        if (user == null) {
            model.addAttribute("error", "Введённый логин или пароль не верен!");
            return "login";
        }

        request.getSession().
                setAttribute("user", user);

        System.out.println("Authentication successful!");
        return "redirect:/";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(Model model,
                                   @RequestParam(name="login") String login,
                                   @RequestParam(name="password") String password,
                                   @RequestParam(name="re-password") String repassword,
                                   @RequestParam(name="name") String name,
                                   @RequestParam(name="g-recaptcha-response") String recaptchaResponse,
                                   HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage = recaptchaService.verifyRecaptcha(ip, recaptchaResponse);

        if (!StringUtils.isEmpty(captchaVerifyMessage)) {
            System.out.println(captchaVerifyMessage);
            model.addAttribute("error", "Google говорит Вы не человек");
            return "register";
        }

        login = validate.login(login);
        if (login == null) {
            model.addAttribute("error", "Проверьте введённый логин!");
            return "register";
        }

        name = validate.name(name);
        if (name == null) {
            model.addAttribute("error", "Проверьте введённое имя!");
            return "register";
        }

        if (validate.passwords(password, repassword) == null) {
            model.addAttribute("error", "Проверьте введённые пароли!");
            return "register";
        }
        password = validate.createPassword(password);

        if (userController.isUserSavedByLogin(login)) {
            model.addAttribute("error", "Такой пользователь уже зарегистрирован!\n\nЕсли это Вы, можете авторизоваться!");
            return "register";
        }

        User user = userController.addUser(new User(login, password, name));
        System.out.println("User " + user.getLogin() + " saved successful");

        model.addAttribute("success", "Регистрация прошла успешно, можете авторизоваться!");
        return "register";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String postMessage(Model model,
                              HttpSession session,
                              @PathVariable("id") String chatId,
                              @RequestParam(name="message") String message,
                              @RequestParam(name="message-id") String messageId) {
        User user = userController.getUserFromSession(session);
        if (user == null)
            return "login";

        if(!validate.isNumber(chatId)) {
            model.addAttribute("error", "Неверный идентификатор чата!");
            model.addAttribute("user", user);
            model.addAttribute("chats", chatController.getChatsByUser(user));
            return "chats";
        }

        if(!chatController.isChatSavedById(Integer.parseInt(chatId))){
            model.addAttribute("error", "Такого чата ещё не было!");
            model.addAttribute("user", user);
            model.addAttribute("chats", chatController.getChatsByUser(user));
            return "chats";
        }

        Chat chat = chatController.getChatAndUsersById(Integer.parseInt(chatId));
        if (!chatController.isUserFromChat(chat, user)) {
            model.addAttribute("error", "Вас в этом чате не ждут!");
            model.addAttribute("user", user);
            model.addAttribute("chats", chatController.getChatsByUser(user));
            return "chats";
        }

        chat = chatController.getChatById(Integer.parseInt(chatId), 0);
        message = validate.message(message);
        if(message == null) {
            model.addAttribute("error", "Сообщение было пустым!");
            model.addAttribute("user", user);
            model.addAttribute("chats", chatController.getChatsByUser(user));
            model.addAttribute("chat", chat);
            return "chats";
        }

        if(messageId.isEmpty()) {
            messageController.addMessage(new Message(Integer.parseInt(chatId), user.getId(), user.getName(), message));
        } else {
            if(!validate.isNumber(messageId)) {
                model.addAttribute("error", "Пожалуйста попробуйте ещё раз!");
                model.addAttribute("user", user);
                model.addAttribute("chats", chatController.getChatsByUser(user));
                model.addAttribute("chat", chat);
                return "chats";
            }

            if(!messageController.isMessageFromUser(Integer.parseInt(messageId), user.getId())) {
                model.addAttribute("error", "Может не будет редактировать чужие сообщения!");
                model.addAttribute("user", user);
                model.addAttribute("chats", chatController.getChatsByUser(user));
                model.addAttribute("chat", chat);
                return "chats";
            }

            messageController.updateMessage(new Message(Integer.parseInt(messageId), message));
        }

        return "redirect:/" + chatId;
    }
}
