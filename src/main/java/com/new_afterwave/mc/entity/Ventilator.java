package com.new_afterwave.mc.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;


public class Ventilator extends ItemStack
{
    public Ventilator() {
        super(Material.CONCRETE_POWDER);
        ItemMeta itemMeta = this.getItemMeta();
        List<String> lore = Arrays.asList("能够放置的呼吸机");
        itemMeta.setLore(lore);
        itemMeta.setDisplayName("呼吸机");
        this.setItemMeta(itemMeta);
    }
}
