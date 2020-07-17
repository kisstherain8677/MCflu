package com.new_afterwave.mc.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;


/**
 * @author by
 * @description:
 * @date 2020/7/6 23:16
 */
public class N95 extends ItemStack
{
    public N95(int amount)
    {
        super(Material.PAPER, amount);
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName("N95口罩");
        itemMeta.setLore(Arrays.asList("右击使用", "可以免疫新冠病毒的空气传播600秒", "希望你能活久点"));
        this.setItemMeta(itemMeta);
    }
}
