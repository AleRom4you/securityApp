package by.alerom.securityApp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Chat implements Serializable, Comparable<Chat> {
    private int id;
    private String name;
    private int owner;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private Timestamp created_at;
    private Timestamp updated_at;

    public Chat() {
    }

    public Chat(int owner) {
        this.owner = owner;
    }

    public Chat(int owner, String name) {
        this.name = name;
        this.owner = owner;
    }

    public Chat(int id, String name, int owner, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void deleteMessage(Message message) {
        this.messages.remove(message);
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

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", users=" + users +
                ", messages=" + messages +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    @Override
    public int compareTo(Chat o) {
        return this.getUpdated_at().compareTo(o.getUpdated_at());
    }
}
