package com.kiss_the_rain8677.DisShow.util;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class VaultUtil {
    public static Economy economy=null;
    public static boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvide=getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvide!=null){
            economy=economyProvide.getProvider();
        }
        return (economy!=null);
    }

    public static boolean give(UUID PlayerUUid, double price){
        OfflinePlayer offplayer= Bukkit.getOfflinePlayer(PlayerUUid);
        return economy.depositPlayer(offplayer,price).transactionSuccess();
    }

    public static double seemoney(UUID PlayerUUid){
        OfflinePlayer offplayer=Bukkit.getOfflinePlayer(PlayerUUid);
        return economy.getBalance(offplayer);
    }

    public static boolean pay(UUID PlayerUUid,double price){
        OfflinePlayer offplayer=Bukkit.getOfflinePlayer(PlayerUUid);
        return economy.has(offplayer,price)&&economy.withdrawPlayer(offplayer,price).transactionSuccess();
    }
}
