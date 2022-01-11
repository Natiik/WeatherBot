package org.example.weatherBot.bot;

import org.example.weatherBot.user_entity.Language;
import org.example.weatherBot.user_entity.Metrics;
import org.example.weatherBot.user_entity.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL {
    public static final String DB = "jdbc:sqlite:set.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertDefault(String id) {
        if (!ifExist(id)) {
            try (Statement statement = DriverManager.getConnection(DB).createStatement()) {
                String sql = "INSERT INTO setting VALUES ('%s', 'metric','ru',703448);".formatted(id);
                statement.execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private ArrayList<User> selectAll() {
        try (Statement statement = DriverManager.getConnection(DB).createStatement()) {
            String sql = "SELECT * FROM setting;";
            ResultSet set = statement.executeQuery(sql);
            ArrayList<User> list = new ArrayList<>();
            while (set.next()) {
                list.add(new User(set.getString(1),
                        Metrics.valueOf(set.getString(2).toUpperCase()),
                        Language.valueOf(set.getString(3).toUpperCase()),
                        set.getInt(4)));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean ifExist(String id) {
        ArrayList<User> list = selectAll();
        if (null == list) {
            return false;
        }
        return list.stream().anyMatch(user -> id.equals(user.getId()));
    }

    public void update(String id, String column, String value) {
        try (Statement statement = DriverManager.getConnection(DB).createStatement()) {
            String sql = "UPDATE setting SET %s = '%s' WHERE Id = %s;".formatted(column, value, id);
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
