package com.kiss_the_rain8677.DisShow.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class test {
    public static void PlayerTestGui(Player player){
        Inventory inventory= Bukkit.createInventory(null,54,"Test");
        ItemStack itemStack=new ItemStack(Material.GLASS);
        ItemMeta itemMeta=itemStack.getItemMeta();
        itemMeta.setDisplayName("dis");
        itemStack.setItemMeta(itemMeta);
        for(int i=0;i<=8;i++){
            inventory.setItem(i,itemStack);
        }
        inventory.setItem(9,itemStack);
        inventory.setItem(18,itemStack);
        inventory.setItem(27,itemStack);
        inventory.setItem(36,itemStack);
        inventory.setItem(17,itemStack);
        inventory.setItem(26,itemStack);
        inventory.setItem(35,itemStack);
        inventory.setItem(44,itemStack);

        for(int i=44;i<=53;i++){
            inventory.setItem(i,itemStack);
        }
        player.openInventory(inventory);
        ItemStack button=new ItemStack(Material.WOOD_BUTTON);
    }
}
