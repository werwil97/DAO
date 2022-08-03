package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

        private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
        private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? where id = ?";
        private static final String SELECT_USER_QUERY = "SELECT * FROM users WHERE id = ?";
        private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
        private static final String SELECT_allUSER_QUERY = "SELECT * FROM users";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement preStmt = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(1,user.getUserName());
            preStmt.setString(2,user.getEmail());
            preStmt.setString(3,hashPassword(user.getPassword()));
            preStmt.executeUpdate();
            ResultSet rs = preStmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void select(int userId) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement preStmt = conn.prepareStatement(SELECT_USER_QUERY);
            preStmt.setInt(1,userId);
            preStmt.executeQuery();
            ResultSet resultSet = preStmt.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                System.out.println(id + ", " + name + ", " + email + ", " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(User user) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement preStmt = conn.prepareStatement(UPDATE_USER_QUERY);
            preStmt.setString(1,user.getUserName());
            preStmt.setString(2,user.getEmail());
            preStmt.setString(3,user.getPassword());
            preStmt.setInt(4,user.getId());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement preStmt = conn.prepareStatement(DELETE_USER_QUERY);
            preStmt.setInt(1,userId);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAll() {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement preStmt = conn.prepareStatement(SELECT_allUSER_QUERY);
            preStmt.executeQuery();
            ResultSet resultSet = preStmt.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                System.out.println(id + ", " + name + ", " + email + ", " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
