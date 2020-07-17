package com.new_afterwave.mc.Util;

import com.new_afterwave.mc.dao.MySQLManager;
import org.bukkit.Material;
import org.bukkit.block.Block;


public class VentilatorStructure
{
    public static boolean isVentilator(Block block){
        if(!block.getType().equals(Material.CONCRETE_POWDER))
            return false;
        if(!MySQLManager.isVentilator(block.getLocation()))
            return false;
        return true;
    }
}
