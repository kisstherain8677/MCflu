package com.new_afterwave.mc.Gui;

import com.new_afterwave.mc.entity.Disinfector;
import com.new_afterwave.mc.entity.N95;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopGUI {


    public static String PlayerrenqiGui = "收购界面";
    public static void PlayerrenqiGui(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 54, PlayerrenqiGui);
        ItemStack itemStack = new ItemStack(Material.GLASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§7界面图");
        itemStack.setItemMeta(itemMeta);
        for (int i = 0; i <9 ; i++) {
            inventory.setItem(i,itemStack);

        }
        inventory.setItem(9,itemStack);
        inventory.setItem(18 ,itemStack);
        inventory.setItem(27,itemStack);
        inventory.setItem(36,itemStack);
        inventory.setItem(17,itemStack);
        inventory.setItem(26,itemStack);
        inventory.setItem(35,itemStack);
        inventory.setItem(44,itemStack);
        for (int i = 45; i <54; i++) {
            inventory.setItem(i,itemStack);
        }
        player.openInventory(inventory);
        ItemStack itemStack1 = new ItemStack(Material.WOOD_BUTTON);
        itemMeta.setDisplayName("收购按钮");
        itemStack1.setItemMeta(itemMeta);
        inventory.setItem(49,itemStack1);
    }
    public static String buygui = "买药界面";
    public static void buyGui(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 54, buygui);
        ItemStack itemStack = new ItemStack(Material.GLASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§7界面图");
        itemStack.setItemMeta(itemMeta);
        for (int i = 0; i <9 ; i++) {
            inventory.setItem(i,itemStack);

        }
        N95 n95 = new N95(1);
        inventory.setItem(9,itemStack);
        inventory.setItem(10,n95);
        Disinfector disinfector = new Disinfector();
        inventory.setItem(11,disinfector);

        inventory.setItem(18 ,itemStack);
        inventory.setItem(27,itemStack);
        inventory.setItem(36,itemStack);
        inventory.setItem(17,itemStack);
        inventory.setItem(26,itemStack);
        inventory.setItem(35,itemStack);
        inventory.setItem(44,itemStack);
        for (int i = 45; i <54; i++) {
            inventory.setItem(i,itemStack);
        }
        player.openInventory(inventory);
        ItemStack itemStack1 = new ItemStack(Material.WOOD_BUTTON);
        itemMeta.setDisplayName("购买口罩*10");
        itemStack1.setItemMeta(itemMeta);
        inventory.setItem(49,itemStack1);
    }
}
