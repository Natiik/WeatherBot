package org.example.weatherBot.utility;

import org.example.weatherBot.settingObject.Language;
import org.example.weatherBot.settingObject.Metrics;
import org.example.weatherBot.settingObject.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class SQL {
    static Connection connection = null;
    static Statement statement = null;

    public static void insertDefault(String id) {
        try {
            if (!isPresent(id)) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:set.db");
                statement = connection.createStatement();

                String sql = "INSERT INTO setting VALUES ('%s', 'metric','ru',703448);".formatted(id);
                statement.execute(sql);
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static ArrayList<User> selectAll() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:set.db");
            statement = connection.createStatement();
            String sql = "SELECT * FROM setting;";
            ResultSet set = statement.executeQuery(sql);

            ArrayList<User> list = new ArrayList<>();
            while (set.next()) {
                list.add(new User(set.getString(1),
                                  Metrics.valueOf(set.getString(2).toUpperCase()),
                                  Language.valueOf(set.getString(3).toUpperCase()),
                                  set.getInt(4)));
            }
            statement.close();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean isPresent(String id) {
        try {
            ArrayList<User> list = selectAll();
            if (list != null) {
                for (User user : list) {
                    if (user.getId().equals(id)) {
                        return true;
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void update (String id, String column, String value){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:set.db");
            statement = connection.createStatement();
            String sql = "UPDATE setting SET %s = \'%s\' WHERE Id=%s;".formatted(column, value,id);
//            System.out.println(sql);
            statement.execute(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
