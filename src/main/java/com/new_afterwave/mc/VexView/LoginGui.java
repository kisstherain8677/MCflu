package com.new_afterwave.mc.VexView;

import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author by
 * @description:
 * @date 2020/7/9 9:38
 */
public class LoginGui
{

    public static VexGui getGui(Player player)
    {
        List<VexComponents> vexComponents = new ArrayList<>();
        vexComponents.add(new VexImage("[local]login.jpg", 8, 6, 284, 188));
        vexComponents.add(new VexText(120, 20, Arrays.asList("请先登录/注册"), 1.5));
        vexComponents.add(new VexPlayerDraw(80, 150, 40, player.getUniqueId(), player.getName()));
        vexComponents.add(new VexText(170, 90, Arrays.asList("用户名：  " + player.getName(), "", "密  码：")));
        vexComponents.add(new VexButton(1, "登录", "[local]button.png", "[local]button_.png", 170, 140, 40, 20));
        vexComponents.add(new VexButton(2, "注册", "[local]button.png", "[local]button_.png", 230, 140, 40, 20));
        vexComponents.add(new VexTextField(203, 110, 60, 10, 30, 3));
        return new VexGui("[local]gui.png", -1, -1, 300, 200, vexComponents);
    }
}
