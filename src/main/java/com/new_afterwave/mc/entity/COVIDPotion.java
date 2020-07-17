package com.new_afterwave.mc.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;


public class COVIDPotion extends ItemStack
{
    public COVIDPotion() {
        super(Material.POTION);
        ItemMeta itemMeta = this.getItemMeta();
        List<String> lore = Arrays.asList("服用此药水可以使肺炎病人的死亡率下降到10%");
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("§7肺炎药水");
        this.setItemMeta(itemMeta);
    }
}
