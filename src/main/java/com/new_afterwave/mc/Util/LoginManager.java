package com.new_afterwave.mc.Util;

import com.new_afterwave.mc.dao.LoginSQLiteManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @author by
 * @description:
 * @date 2020/7/6 19:36
 */
public class LoginManager
{
    private static JavaPlugin plugin;

    private static ArrayList<String> unLoginPlayerList = new ArrayList<>();

    public static boolean initLoginManager(JavaPlugin _plugin)
    {
        plugin = _plugin;
        if (plugin == null)
            return false;
        return true;
    }

    public static void setUnLoginState(String playerName, boolean unLogin)
    {
        if (unLogin)
            unLoginPlayerList.add(playerName);
        else
            unLoginPlayerList.remove(playerName);
    }

    //根据config.yml来判断是否注册
    public static boolean isRegister(String playerName)
    {
        ResultSet rs = LoginSQLiteManager.selectLoginInfo(playerName);
        try
        {
            if (rs.next())
                return true;
            else
                return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //根据unLoginPlayerList来判断是否登录
    public static boolean isLogin(String playerName)
    {
        if (unLoginPlayerList.contains(playerName))
            return false;
        return true;
    }

    //false：密码错误或未注册
    public static boolean login(String playerName, String password)
    {
        if (isRegister(playerName))
        {
            ResultSet rs = LoginSQLiteManager.selectLoginInfo(playerName);
            try
            {
                if (rs.next() && playerName.equalsIgnoreCase(rs.getString("playerName")) &&
                    password.equalsIgnoreCase(rs.getString("password")))
                {
                    unLoginPlayerList.remove(playerName);
                    return true;
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    //false：已注册
    public static boolean register(String playerName, String password)
    {
        if (!isRegister(playerName))
        {
            LoginSQLiteManager.insertLoginInfo(playerName, password);
            return true;
        }
        return false;
    }
}
