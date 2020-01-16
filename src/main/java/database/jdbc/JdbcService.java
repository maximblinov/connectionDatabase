package database.jdbc;

import database.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JdbcService {

    //вставка пользователя в таблицу
    public static void insertUser(User user) throws SQLException {
        //кверя для бдшки
        String insertQuery = "insert into user values(?,?,?,?,?)";
        //соединение с бдшкой
        Connection con = JdbcUtils.getConnection();
        //создание запроса
        PreparedStatement stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        //тут задается значения для строки таблицы
        stmt.setNull(1, java.sql.Types.INTEGER);
        stmt.setString(2, user.getFirstName());
        stmt.setString(3, user.getLastName());
        stmt.setString(4, user.getLogin());
        stmt.executeUpdate();
        //получение айдишки от базы
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        }
        stmt.close();
    }

    public  static void updateUser(User user) throws SQLException{
        String updateQuery = "update user set firstname= ?, lastname=?, login=? where id=?";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement stmt = con.prepareStatement(updateQuery);
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3,user.getLogin());
        stmt.setInt(4,user.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public static User getUserByIdUsingColNames(int userId) throws SQLException {
        String selectQuery = "select * from user where id =?";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectQuery);
        stmt.setInt(1, userId);
        //получение множества результатов с бдшки
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            //парсим результат из бдшки по именам столбцов таблицы
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String login = rs.getString("login");
            User user = new User(userId, firstName,lastName,login);
            return user;
        } else {
            return null;
        }
    }

    public static User getUserByIdUsingColNumbers(int userId) throws SQLException {
        String selectQuery = "select * from user where id =?";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectQuery);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            //парсим результат из бдшки по номерам столбцов таблицы
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            String login = rs.getString(4);
            User user = new User(userId, firstName,lastName,login);
            return user;
        } else {
            return null;
        }
    }

    public static List<User> getUsersUsingColNames() throws SQLException {
        List<User> users = new ArrayList<>();
        String selectQuery = "select * from user";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String login = rs.getString("login");
            User user = new User(id,firstName,lastName,login);
            users.add(user);
        }
        if (users.size() == 0) {
            return Collections.emptyList();
        }
        return users;
    }

    public static List<User> getUsersUsingColNumbers() throws SQLException {
        List<User> users = new ArrayList<>();
        String selectQuery = "select * from user";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement stmt = con.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            String login = rs.getString(4);
            User user = new User(id,firstName,lastName,login);
            users.add(user);
        }
        if (users.size() == 0) {
            return Collections.emptyList();
        }
        return users;
    }

    public static void deleteUser(User user) throws SQLException {
        String deleteQuery = "delete from user where id=?";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement stmt = con.prepareStatement(deleteQuery);
        stmt.setInt(1, user.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public static void deleteAllUsers() throws SQLException {
        String deleteQuery = "delete from user";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement stmt = con.prepareStatement(deleteQuery);
        stmt.executeUpdate();
        stmt.close();
    }
}
