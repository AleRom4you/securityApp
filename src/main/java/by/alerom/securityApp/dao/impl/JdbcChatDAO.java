package by.alerom.securityApp.dao.impl;

import by.alerom.securityApp.dao.ChatDAO;
import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcChatDAO implements ChatDAO {
    private DataSource dataSource;
    private ObjectMapper mapper = new ObjectMapper();

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Chat findById(int id) {
        String sql = "SELECT * FROM `chats` WHERE `id` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            Chat chat = null;
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                chat = new Chat(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("owner"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
            rs.close();
            ps.close();

            return chat;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public ArrayList<Chat> findAllByOwner(User user) {
        String sql = "SELECT * FROM `chats` WHERE `owner` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ArrayList<Chat> chats = new ArrayList<>();
            Chat chat = null;
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                chat = new Chat(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("owner"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );

                chats.add(chat);
            }
            rs.close();
            ps.close();

            return chats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public ArrayList<Chat> findAllByUser(User user) {
        ArrayList chatIds = findChatIdsByUser(user);
        if (chatIds == null)
            return null;

        ArrayList<Chat> chats = new ArrayList<>();
        for (Object id : chatIds) {
            chats.add(findById((int) id));
        }
        return chats;
    }

    @Override
    public ArrayList findChatIdsByUser(User user) {
        String sql = "SELECT * FROM `chat_user` WHERE `user_id` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ArrayList chatIds = new ArrayList();
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                chatIds.add(rs.getInt("chat_id"));
            }
            rs.close();
            ps.close();

            return chatIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public void addUser(Chat chat, User user) {
        String sql = "INSERT INTO `chat_user` " +
                "(`chat_id`, `user_id`) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, chat.getId());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public void updateDate(int id) {
        String sql = "UPDATE `chats` SET " +
                "`updated_at` = CURRENT_TIME " +
                "WHERE `id` = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
