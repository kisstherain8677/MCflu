package com.new_afterwave.mc.entity;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;


/**
 * @author by
 * @description:
 * @date 2020/7/10 17:08
 */
public class Disinfector extends ItemStack
{
    public Disinfector()
    {
        super(Material.SPLASH_POTION, 1);
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName("消毒剂");
        itemMeta.setLore(Arrays.asList("右击使用", "可以免疫新冠病毒的接触传播300秒", "希望你能活久点"));
        this.setItemMeta(itemMeta);
    }
}
