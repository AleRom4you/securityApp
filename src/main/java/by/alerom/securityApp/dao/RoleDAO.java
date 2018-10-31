package by.alerom.securityApp.dao;

import by.alerom.securityApp.model.Role;
import by.alerom.securityApp.model.User;

public interface RoleDAO {
    public Role findById(int id);
    public Role findByName(String name);

    public int findRoleIdByUser(User user);
}
