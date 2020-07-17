package com.new_afterwave.mc.dao;

import org.bukkit.Bukkit;

import java.sql.*;


/**
 * @author by
 * @description:
 * @date 2020/7/8 16:04
 */
public class LoginSQLiteManager
{
    private static Connection connection;

    public static boolean init()
    {
        String sql = LoginSQLStatement.CREATE_TABLE1.getStatement();
        try
        {
            connection = MySQLManager.connection;
            executeStatement(connection.prepareStatement(sql));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized static boolean executeStatement(PreparedStatement ps)
    {
        try
        {
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("执行指令失败，以下为错误提示：");
            e.printStackTrace();
            return false;
        }
    }

    public synchronized static boolean insertLoginInfo(String playerName, String password)
    {
        try
        {
            String sql = LoginSQLStatement.ADD_DATA.getStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, playerName);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized static boolean deleteLoginInfo(String playerName)
    {
        try
        {
            String sql = LoginSQLStatement.DELETE_DATA.getStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, playerName);
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized static ResultSet selectLoginInfo(String playerName)
    {
        try
        {
            String sql = LoginSQLStatement.SELECT_DATA.getStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, playerName);
            return ps.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void close()
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("关闭MySQL连接失败，以下为错误提示：");
            e.printStackTrace();
        }
    }

}
