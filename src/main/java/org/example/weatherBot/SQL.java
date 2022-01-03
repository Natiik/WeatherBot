package org.example.weatherBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class SQL {
    static Connection connection = null;
    static Statement statement = null;

    static void insertDefault(String id){
        try{
            Class.forName("org.sqlite.JDBC");
            connection= DriverManager.getConnection("jdbc:sqlite:set.db");
            statement= connection.createStatement();

            String sql = "INSERT INTO setting VALUES ('%s', 'metric','ru',703448)".formatted(id);
            statement.execute(sql);
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try{
                    connection.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
