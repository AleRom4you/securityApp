package by.alerom.securityApp.dao.impl;

import by.alerom.securityApp.config.Config;
import by.alerom.securityApp.dao.MessageDAO;
import by.alerom.securityApp.model.Chat;
import by.alerom.securityApp.model.Message;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcMessageDAO implements MessageDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Message findById(int id) {
        String sql = "SELECT * FROM `messages` WHERE `id` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            Message message = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = new Message(
                        rs.getInt("id"),
                        rs.getInt("chat_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("message"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
            rs.close();
            ps.close();
            return message;
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
    public Message findByIdAndUserId(int id, int userId) {
        String sql = "SELECT * FROM `messages` WHERE `id` = ? AND `user_id` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, userId);
            Message message = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = new Message(
                        rs.getInt("id"),
                        rs.getInt("chat_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("message"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
            rs.close();
            ps.close();
            return message;
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
    public ArrayList<Message> findAllByChat(Chat chat, int start, int limit) {
        String sql = "SELECT * FROM `messages` WHERE `chat_id` = ? ORDER BY `created_at` DESC LIMIT ?,?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, chat.getId());
            ps.setInt(2, start);
            ps.setInt(3, limit);
            ArrayList<Message> messages = new ArrayList<>();
            Message message = null;
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                message = new Message(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("message"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );

                messages.add(message);
            }
            rs.close();
            ps.close();

            return messages;
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
    public void insert(int chatId, int userId, String userName, Message message) {
        String sql = "INSERT INTO `messages` " +
                "(`chat_id`, `user_id`, `user_name`, `message`, `created_at`, `updated_at`) VALUES (?, ?, ?, ?, CURRENT_TIME, CURRENT_TIME)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, chatId);
            ps.setInt(2, userId);
            ps.setString(3, userName);
            ps.setString(4, message.encodeMessage(message.getMessage()));
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
    public void updateById(int id, Message message) {
        String sql = "UPDATE `messages` SET " +
                "`message` = ?, " +
                "`updated_at` = CURRENT_TIME " +
                "WHERE `id` = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, message.encodeMessage(message.getMessage()));
            ps.setInt(2, id);
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
