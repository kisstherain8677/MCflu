package com.new_afterwave.mc.Util;

import java.util.HashMap;
import java.util.UUID;


public class PluginInfo
{

    public static void init(double minDis, long maxTime, long deadTime)
    {
        //以下数据用于初始化HealthList
        PluginInfo.minDis = minDis;
        PluginInfo.maxTime = maxTime;
        PluginInfo.deadTime = deadTime;//感染后死亡时间
        allHealthList = new HashMap<>();
    }

    private static HashMap<UUID, HealthList> allHealthList; //玩家健康表（插件新增）

    private static double minDis;//阈值距离，设定进入此距离开始倒计时

    private static long maxTime;//阈值时间，设定倒计时的长度

    private static long deadTime;

    public static HashMap<UUID, HealthList> getAllHealthList()
    {
        return allHealthList;
    }

    public static long getDeadTime()
    {
        return PluginInfo.deadTime;
    }

    public static void setDeadTime(long deadTime)
    {
        PluginInfo.deadTime = deadTime;
    }

    public static void setAllHealthList(HashMap<UUID, HealthList> allHealthList)
    {
        PluginInfo.allHealthList = allHealthList;
    }

    public static double getMinDis()
    {
        return minDis;
    }

    public static void setMinDis(double minDis)
    {
        PluginInfo.minDis = minDis;
    }

    public static long getMaxTime()
    {
        return maxTime;
    }

    public static void setMaxTime(long maxTime)
    {
        PluginInfo.maxTime = maxTime;
    }

    public static HealthList getHealthList(UUID uuid)
    {
        return allHealthList.get(uuid);
    }
}
