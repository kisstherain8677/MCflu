package com.new_afterwave.mc.VexView;

import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexImage;
import lk.vexview.gui.components.VexPlayerDraw;
import org.bukkit.entity.Player;


public class VentilatorGUI extends VexGui
{
    private static VentilatorGUI ventilatorGUI = null;
    public static String vGUI = "呼吸机";

    public VentilatorGUI(String a, int a2, int a3, int a4, int a5) {
        super(a, a2, a3, a4, a5);
    }

    public static void show(Player player){
        player.closeInventory();
        ventilatorGUI = new VentilatorGUI("[local]gui.png",-1,-1,300,150);
        ventilatorGUI.addComponent(new VexImage("[local]windows.png",5,5,290,140));
        ventilatorGUI.addComponent(new VexButton("vent_btn1","使用呼吸机","[local]button.png","[local]button.png",220,50,40,16));
        ventilatorGUI.addComponent(new VexButton("vent_btn2","购买药水","[local]button.png","[local]button.png",220,100,40,16));
        ventilatorGUI.addComponent(new VexPlayerDraw(100,100,30,player));
        VexViewAPI.openGui(player,ventilatorGUI);
    }
}
