package com.new_afterwave.mc.dao;

import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;


public class MySQLManager
{
    public static MySQLManager instance = null;

    public static Connection connection = null;

    private static MainPlugin plugin;

    public static void init(MainPlugin vplugin)
    {
        plugin = vplugin;
    }

    public static void enableMySQL()
    {
        connectMySQL();
        String cmd = SQLCommand.CREATE_HEALTHTABLE.commandToString();
        String ventCmd = SQLCommand.CREATE_VENT_TABLE.commandToString();
        try {
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement(ventCmd);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void connectMySQL()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + plugin.getConfig().getString("dbname") + ".db";
            plugin.getServer().getLogger().info(url);
            connection = DriverManager.getConnection(url);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public static void doCommand(PreparedStatement ps, CommandSender sender)
    {
        try
        {
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            sender.sendMessage("执行指令失败，以下为错误提示");
            e.printStackTrace();
        }
    }

    public static void shutdown()
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            //断开连接失败
            e.printStackTrace();
        }
    }

//    ('UUID','playerName','nearDis','dangerTime','isInfected','isMusked','isDisinfected'," +
//            "'isPotionTacked','isVaccine','maskTime','disinfectorTime')

    public static void insertHealthData(UUID uid, HealthList healthList, CommandSender sender)
    {
        try
        {
            PreparedStatement ps;
            String s = SQLCommand.ADD_DATA.commandToString();
            ps = connection.prepareStatement(s);
            //ps.setInt(1, Integer.parseInt(data1));
            ps.setString(1, String.valueOf(uid));
            ps.setString(2, String.valueOf(healthList.getPlayerName()));
            ps.setString(3, String.valueOf(healthList.getNearDis()));
            ps.setString(4, String.valueOf(healthList.getDangerTime()));
            ps.setString(5, String.valueOf(healthList.getDeadTime()));

            ps.setBoolean(6, healthList.isInfected());
            ps.setBoolean(7, healthList.isMasked());
            ps.setBoolean(8, healthList.isDisinfected());
            ps.setBoolean(9, healthList.isPotionTacked());
            ps.setBoolean(10, healthList.isVaccine());
            ps.setString(11, String.valueOf(healthList.getMaskTime()));
            ps.setString(12, String.valueOf(healthList.getDisinfectorTime()));

            doCommand(ps, sender);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteData(UUID uuid, CommandSender sender)
    {
        try
        {
            PreparedStatement ps;
            String s = SQLCommand.DELETE_DATA.commandToString();
            ps = connection.prepareStatement(s);
            ps.setString(1, String.valueOf(uuid));
            doCommand(ps, sender);
        }
        catch (SQLException e)
        {

            e.printStackTrace();
        }
    }

    //有数据返回1 无数据返回2 出错返回0
    public static int findData(UUID uuid, CommandSender sender)
    {
        try
        {
            String s = SQLCommand.SELECT_DATA.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1, String.valueOf(uuid));
            //sender.sendMessage("dao: Statement "+s);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next())
            {
                while (resultSet.next())
                {
                    String info = resultSet.getString("info");
                    sender.sendMessage(info);
                }
                // resultSet.previous();//游标返回
                return 1;
            }
            else
            {
                return 2;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            sender.sendMessage("manager 获取用户信息失败");
            return 0;
        }
    }

//    "'nearDis' varchar(20),"+3
//            "'dangerTime' varchar(20),"+4
    //          deadTime 5
//            "'isInfected' boolean,"+6
//            "'isMasked' boolean,"+7
//            "'isDisinfected' boolean,"+8
//            "'isPotionTacked' boolean,"+9
//            "'isVaccine' boolean,"+10
//            "'maskTime' varchar(20),"+11
//            "'disinfectorTime' varchar(20)"+12

    //将某个玩家信息保存到数据库
    public static void savePlayerHealth(Player player)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                //查找状态码
                //有数据返回1 无数据返回2 出错返回0
                try
                {
                    int state = MySQLManager.findData(player.getUniqueId(), player);
                    if (state == 0)
                    {
                        plugin.getLogger().info("数据库连接出错");
                    }
                    else if (state == 1)
                    {
                        plugin.getLogger().info("已经有该玩家信息，更新");
                        MySQLManager.DeleteData(player.getUniqueId(), player);
                        MySQLManager.insertHealthData(player.getUniqueId(), PluginInfo.getHealthList(player.getUniqueId()), player);
                    }
                    else
                    {
                        plugin.getLogger().info("无此玩家信息，插入信息");
                        MySQLManager.insertHealthData(player.getUniqueId(), PluginInfo.getHealthList(player.getUniqueId()), player);
                    }

                    plugin.getLogger().info(player.getName()+"保存健康信息完成");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.runTask(plugin);
    }

    //从数据库中读取健康表,并放入内存,如果没有信息，写入
    public static void loadPlayerHealth(Player player)
    {
        UUID uid = player.getUniqueId();
        String name = player.getName();
        new BukkitRunnable()
        {
            //初始化健康状态表
            HealthList stateList = new HealthList(uid, name);

            HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();

            @Override
            public void run()
            {
                //查找状态码
                //有数据返回1 无数据返回2 出错返回0
                //player.sendMessage("uuid:"+uid);
                int state = MySQLManager.findData(uid, player);
                if (state == 0)
                {
                    player.sendMessage(" 数据库连接出错");
                    return;
                }
                else if (state == 1)
                {
                    player.sendMessage(" 已经有该玩家信息，读取");
                    stateList = MySQLManager.getHealthList(player);//这一句从数据库中读
                }
                else
                {
                    player.sendMessage(" 无此玩家信息，插入初始信息");
                    MySQLManager.insertHealthData(player.getUniqueId(), stateList, player);
                }
                //下面用于内存的更新
                //如果内存中已经有了此玩家，清空,放入新信息
                if (allHealthList.containsKey(uid))
                {
                    allHealthList.remove(uid);
                }
                allHealthList.put(uid, stateList);
                player.sendMessage("已更新健康信息 " + name);
                if (stateList.isInfected())
                {
                    player.sendMessage("已感染");
                }
                else
                {
                    player.sendMessage("未感染");
                }

                PluginInfo.setAllHealthList(allHealthList);
            }
        }.runTaskAsynchronously(plugin);
    }

    public static HealthList getHealthList(Player player)
    {
        HealthList healthList = new HealthList(player.getUniqueId(), player.getName());
        try
        {
            String s = SQLCommand.SELECT_DATA.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                healthList.setNearDis(Double.parseDouble(resultSet.getString(3)));
                healthList.setDangerTime(Long.parseLong(resultSet.getString(4)));
                healthList.setDeadTime(Long.parseLong(resultSet.getString(5)));
                healthList.setInfected(resultSet.getBoolean(6));
                healthList.setMasked(resultSet.getBoolean(7));
                healthList.setDisinfected(resultSet.getBoolean(8));
                healthList.setPotionTacked(resultSet.getBoolean(9));
                healthList.setVaccine(resultSet.getBoolean(10));
                healthList.setMaskTime(Long.parseLong(resultSet.getString(11)));
                healthList.setDisinfectorTime(Long.parseLong(resultSet.getString(12)));
                healthList.setLoginInfect(healthList.isInfected());
            }
            return healthList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            player.sendMessage("获取用户信息失败");
        }
        return null;
    }
    public static void insertVentilator(String ID, Location location, Player player){
        try{
            String s = SQLCommand.INSERT_VENT_TABLE.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);
            /*ps.setInt(1,ID);
            ps.setInt(2,location.getBlockX());
            ps.setInt(3,location.getBlockY());
            ps.setInt(4,location.getBlockZ());*/
            ps.setString(1,ID);
            ps.setString(2,String.valueOf(location.getBlockX()));
            ps.setString(3,String.valueOf(location.getBlockY()));
            ps.setString(4,String.valueOf(location.getBlockZ()));
            doCommand(ps,player);
            return;
        }
        catch (SQLException e){
            e.printStackTrace();;
            player.sendMessage("获取呼吸机信息失败");
        }
    }

    public static void showVentilators(Player player){
        try{
            String s = SQLCommand.SELECT_VENT_TABLE.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                player.sendMessage("呼吸机ID:" + resultSet.getString(1) +"    呼吸机坐标:"
                        + resultSet.getString(2) + ","
                        + resultSet.getString(3) + ","
                        + resultSet.getString(4)
                );
            }
            return;
        }
        catch (SQLException e){
            e.printStackTrace();;
            player.sendMessage("获取呼吸机信息失败");
        }
    }
    public static boolean isVentilator(Location location){
        try{
            String s = SQLCommand.SELECT_VENT_TABLE_BY_POSITION.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1,String.valueOf(location.getBlockX()));
            ps.setString(2,String.valueOf(location.getBlockY()));
            ps.setString(3,String.valueOf(location.getBlockZ()));
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                return false;
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
