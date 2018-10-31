package by.alerom.securityApp.controller;

import by.alerom.securityApp.config.Config;
import com.lambdaworks.crypto.SCryptUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateController {
    public boolean isNumber(String number) {
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public String login(String string) {
        if (string.isEmpty())
            return null;

        string = clearString(string);
        string = string.toLowerCase();

        if (!checkWithRegExp(string, Config.loginLength))
            return null;

        return string;
    }

    public String name(String string) {
        if (string.isEmpty())
            return null;

        string = clearString(string);

        if (string.length() > Config.nameLength)
            return null;

        return string;
    }

    public String password(String password) {
        if (password.isEmpty())
            return null;

        return password;
    }

    public String passwords(String password, String repassword) {
        if (password.isEmpty() || repassword.isEmpty() || !password.equals(repassword))
            return null;

        return password;
    }

    public String message(String string) {
        if (string.isEmpty())
            return null;

        string = string.trim();
        if (string.isEmpty() || string == null)
            return null;

//        string = clearStringCyrillic(string);

        if (string.length() > Config.messageLength)
            return null;

        return string;
    }

    public static boolean checkWithRegExp(String userNameString, int length){
        Pattern p = Pattern.compile("^[a-z0-9_-]{3," + length + "}$");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }

    public String createPassword(String string) {
        return SCryptUtil.scrypt(string, Config.bcryptN, Config.bcryptR, Config.bcryptP);
    }

    private String clearString(String string) {
        string = string.trim();
        return string.replaceAll("[^\\w\\s]","");
    }

    private String clearStringCyrillic(String string) {
        string = string.trim();
        return string.replaceAll("[^\\w\\sа-яёA-ЯЁ]","");
    }
}
