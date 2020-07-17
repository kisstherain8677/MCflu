package com.new_afterwave.mc.Util;


import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Tools {
    public static boolean hasmoney(Player player, double money) {
        if (money <=VaultUtil.seemoney(player.getUniqueId())) {
            player.sendMessage("购买前的金钱数为：" + VaultUtil.seemoney(player.getUniqueId()) + ",商品总价为：" + money);
            player.sendMessage("你有足够的金钱，交易完成");
            VaultUtil.pay(player.getUniqueId(), money);
            player.sendMessage("购买完成的金钱数量为" + VaultUtil.seemoney(player.getUniqueId()));
            return true;
        } else {
            player.sendMessage("购买前的金钱数为：" + VaultUtil.seemoney(player.getUniqueId()) + ",商品总价为：" + money);
            player.sendMessage("余额不足，交易取消");
            return false;
        }
    }
}