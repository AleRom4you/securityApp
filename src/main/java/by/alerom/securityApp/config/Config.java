package by.alerom.securityApp.config;

import java.util.UUID;

public class Config {
    public static int paginationLimit = 1000;

    // Lengths field
    public static int loginLength   = 25;
    public static int nameLength    = 20;
    public static int messageLength = 250;

    // For BCrypt
    public static int bcryptN = 16384;
    public static int bcryptR = 8;
    public static int bcryptP = 1;

    // Paths
    public static String privateKeyFile = "KeyPair/privateKey";
    public static String publicKeyFile = "KeyPair/publicKey";
}
