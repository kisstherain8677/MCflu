package com.new_afterwave.mc.Listener;

import com.new_afterwave.mc.Gui.ShopGUI;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.Tools;
import com.new_afterwave.mc.Util.VaultUtil;
import com.new_afterwave.mc.entity.Disinfector;
import com.new_afterwave.mc.entity.N95;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.tools.Tool;

public class InventoryGUIListener implements Listener {

    MainPlugin plugin;

    //获取plugin，注册事件
    public InventoryGUIListener(MainPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void InventoryrenqiGui(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        String title = inventory.getTitle();
        if (title.equalsIgnoreCase(ShopGUI.PlayerrenqiGui)) {
            HumanEntity whoClicked = event.getWhoClicked();
            Player player = (Player) whoClicked;
            int rawSlot = event.getRawSlot();
            System.out.println(rawSlot);
            if (event.getRawSlot() == 49) {
                // todo 进行收购
                event.setCancelled(true);
                player.sendMessage("收购成功");


                int count = 0;
                for (int i = 1; i < 5; i++) {
                    for (int j = 1; j < 8; j++) {
                        if (inventory.getItem(9 * i + j) != null) {
                            if (inventory.getItem(9 * i + j).getType() == Material.EMERALD) {
                                int num = inventory.getItem(9 * i + j).getAmount();
                                count = count + num;
                                player.sendMessage((9 * i + j) + "号格子含有绿宝石" + num + "个");
                                inventory.clear(9 * i + j);
                            }
                        }

                    }
                }

                player.sendMessage("当前的金钱数量为" + VaultUtil.seemoney(player.getUniqueId()));
                player.sendMessage("总共收购绿宝石的数量为" + count + "个,单价为100,总共获得了" + 100 * count + "的收入");
                VaultUtil.give(player.getUniqueId(), 100 * count);
                player.sendMessage("当前的金钱数量为" + VaultUtil.seemoney(player.getUniqueId()));
            } else if (rawSlot >= 0 && rawSlot <= 8 || rawSlot <= 53 && rawSlot >= 45 || rawSlot == 9 || rawSlot == 18 || rawSlot == 27 || rawSlot == 36 || rawSlot == 26 || rawSlot == 35 || rawSlot == 44) {
                event.setCancelled(true);
                player.sendMessage("这个不能点击");
            }

        } else if (title.equalsIgnoreCase("买药界面")) {
            HumanEntity whoClicked = event.getWhoClicked();
            Player player = (Player) whoClicked;
            int rawSlot = event.getRawSlot();
            if (0 <= rawSlot && rawSlot <= 53) {


                if (event.getRawSlot() == 49) {
                    // todo 进行收购
/*                    event.setCancelled(true);
                    player.sendMessage("收购成功");


                    int count = 0;
                    for (int i = 1; i <5 ; i++) {
                        for (int j = 1; j <8 ; j++) {
                            if (inventory.getItem(9*i+j)!=null){
                                if(inventory.getItem(9*i+j).getType()== Material.EMERALD)

                                {   int num=inventory.getItem(9*i+j).getAmount();
                                    count=count+num;
                                    player.sendMessage((9*i+j)+"号格子含有绿宝石"+num+"个");
                                    inventory.clear(9*i+j);}
                            }

                        }
                    }

                    player.sendMessage("当前的金钱数量为"+VaultUtil.seemoney(player.getUniqueId()));
                    player.sendMessage("总共收购绿宝石的数量为"+count+"个,单价为100,总共获得了"+100*count+"的收入");
                    VaultUtil.give(player.getUniqueId(),100*count);
                    player.sendMessage("当前的金钱数量为"+VaultUtil.seemoney(player.getUniqueId()));*/
                    if(Tools.hasmoney(player,20*10))
                        player.getInventory().addItem(new N95(10));
                } else if (rawSlot == 10) {
                    if(Tools.hasmoney(player,20))
                        player.getInventory().addItem(new N95(1));
                } else if (rawSlot == 11) {
                    if(Tools.hasmoney(player,20))
                        player.getInventory().addItem(new Disinfector());
                } else {

                    player.sendMessage("这个不能点击");
                }

                event.setCancelled(true);
                player.closeInventory();
            }
        }
    }

}





