package by.alerom.securityApp.dao.impl;

import by.alerom.securityApp.dao.RoleDAO;
import by.alerom.securityApp.model.Role;
import by.alerom.securityApp.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRoleDAO implements RoleDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Role findById(int id) {
        String sql = "SELECT * FROM `roles` WHERE `id` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            Role role = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = new Role(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
            rs.close();
            ps.close();
            return role;
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
    public Role findByName(String name) {
        String sql = "SELECT * FROM `roles` WHERE `name` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            Role role = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = new Role(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
            rs.close();
            ps.close();
            return role;
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
    public int findRoleIdByUser(User user) {
        String sql = "SELECT * FROM `role_user` WHERE `user_id` = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());
            int roleId = 0;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roleId = rs.getInt("role_id");
            }
            rs.close();
            ps.close();
            return roleId;
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
