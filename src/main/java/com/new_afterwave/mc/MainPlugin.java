package com.new_afterwave.mc;

import com.new_afterwave.mc.Commands.LoginCommand;
import com.new_afterwave.mc.Commands.RegisterCommand;
import com.new_afterwave.mc.Listener.*;
import com.new_afterwave.mc.Runnable.TestDis;
import com.new_afterwave.mc.Runnable.UpdateDis;
import com.new_afterwave.mc.Util.PluginInfo;
import com.new_afterwave.mc.Util.VaultUtil;
import com.new_afterwave.mc.Commands.MyCommand;

import com.new_afterwave.mc.dao.MySQLManager;
import com.new_afterwave.mc.dao.LoginSQLiteManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;


public class MainPlugin extends JavaPlugin
{
    public static JavaPlugin plugin;

    @Override
    public void onEnable()
    {
        plugin = this;
        //距离阈值，时间阈值
        //进入距离阈值开始计时
        //超过时间阈值感染：2分钟
        PluginInfo.init(20, 400L, 1200L);
        if (!getDataFolder().exists())
        {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
        {
            saveDefaultConfig();
        }

        //连接数据库和创建表（如果表不存在）
        MySQLManager.init(this);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                MySQLManager.enableMySQL();
                LoginSQLiteManager.init();
            }
        }.runTaskAsynchronously(this);

        //initial
        if (!VaultUtil.setupEconomy())
        {
            getLogger().info("初始化经济插件失败");
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("VexView"))
        {
            getLogger().info("初始化vexView失败");
        }

        //Listener
        new PlayerListener(this);
        new EntityListener(this);
        new InventoryGUIListener(this);
        new VexListener(this);
        getServer().getPluginManager().registerEvents(new LoginGuiListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginManagerListener(), this);
        new VentilatorListener(this);

        //Command
        this.getCommand("showDis").setExecutor(new MyCommand(this));
        this.getCommand("setMaxHealth").setExecutor(new MyCommand(this));
        this.getCommand("showMaxHealth").setExecutor(new MyCommand(this));
        this.getCommand("infect").setExecutor(new MyCommand(this));
        this.getCommand("disInfect").setExecutor(new MyCommand(this));
        this.getCommand("invent").setExecutor(new MyCommand(this));
        this.getCommand("showHealthState").setExecutor(new MyCommand(this));
        this.getCommand("give").setExecutor(new MyCommand(this));
        this.getCommand("saveHealth").setExecutor(new MyCommand(this));
        this.getCommand("login").setExecutor(new LoginCommand(this));
        this.getCommand("register").setExecutor(new RegisterCommand(this));
        this.getCommand("vent").setExecutor(new MyCommand(this));
        this.getCommand("showvents").setExecutor(new MyCommand(this));

        getLogger().info("插件安装成功！");

        //new task 20L one time
        new UpdateDis(this).runTaskTimer(this, 0, 20L);

        new TestDis(this, PluginInfo.getMinDis(), PluginInfo.getMaxTime())
            .runTaskTimer(this, 0, 20L);

    }

    @Override
    public void onDisable()
    {
        getLogger().info("插件卸载成功！");
        LoginSQLiteManager.close();
    }
}