package by.alerom.securityApp.config;

import java.util.UUID;

public class Config {
    public static String salt = UUID.randomUUID().toString();
}
