package by.alerom.securityApp.model;

import by.alerom.securityApp.config.Config;
import by.alerom.securityApp.rsa.Encryption;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;

public class Message implements Serializable {
    private int id;
    private int chatId;
    private int userId;
    private String userName;
    private String message;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Message() {
    }

    public Message(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public Message(int chatId, int userId, String userName, String message) {
        this.chatId = chatId;
        this.userId = userId;
        this.userName = userName;
        this.message = message;
    }

    public Message(int id, int userId, String userName, String message, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.message = decodeMessage(message);
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Message(int id, int chatId, int userId, String userName, String message, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
        this.userName = userName;
        this.message = decodeMessage(message);
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String decodeMessage(String message) {
        Encryption ac = null;
        try {
            ac = new Encryption();
            PublicKey publicKey = ac.getPublic(Config.publicKeyFile);

            return ac.decryptText(message, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    public String encodeMessage(String message) {
        Encryption ac = null;
        try {
            ac = new Encryption();
            PrivateKey privateKey = ac.getPrivate(Config.privateKeyFile);

            return ac.encryptText(message, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
