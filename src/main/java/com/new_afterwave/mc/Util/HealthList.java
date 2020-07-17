package com.new_afterwave.mc.Util;

import org.bukkit.entity.Player;

import java.util.UUID;


public class HealthList
{

    UUID uid;

    String playerName;

    long deadTime;//感染后多久死亡

    double nearDis;//最近无防护感染者距离

    long dangerTime;//在距离阈值内的持续时间（最小单位为20L）

    boolean isInfected;//是否感染

    boolean isMasked;//是否戴口罩

    boolean isDisinfected;//是否消毒

    boolean isPotionTacked;//是否服药

    boolean isVaccine;//是否打过疫苗

    long maskTime;//口罩持续时间

    long disinfectorTime;//消毒剂持续时间

    boolean loginInfect;//登录时感染

    boolean isVentilated;//是否在使用呼吸机

    public HealthList(UUID uid, String playerName)
    {
        this.uid = uid;
        this.playerName = playerName;
        this.deadTime = PluginInfo.getDeadTime();
        this.nearDis = 9999;//初始最短距离
        this.dangerTime = PluginInfo.getMaxTime();//初始危险时间
        this.isInfected = false;
        this.isDisinfected = false;
        this.isMasked = false;
        this.isPotionTacked = false;
        this.isVaccine = false;
        this.maskTime = 0;
        this.disinfectorTime = 0;
        this.loginInfect = false;
        this.isVentilated = false;
    }

    public void setVentilated(boolean ventilated)
    {
        isVentilated = ventilated;
    }

    public boolean isVentilated()
    {
        return isVentilated;
    }

    public boolean isLoginInfect()
    {
        return loginInfect;
    }

    public void setLoginInfect(boolean loginInfect)
    {
        this.loginInfect = loginInfect;
    }

    public static void resetHealth(Player player)
    {
        try
        {
            HealthList healthList = PluginInfo.getAllHealthList().get(player.getUniqueId());
            healthList.setDeadTime(PluginInfo.getDeadTime());
            healthList.setNearDis(9999);
            healthList.setDangerTime(PluginInfo.getMaxTime());
            healthList.setInfected(false);
            healthList.setPotionTacked(false);
            healthList.setVentilated(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public double getAttackInfectRate()
    {
        return isDisinfected ? 0 : 50;
    }

    public double getEatInfectRate()
    {
        return 50;
    }

    public double getDeadRate()
    {
        return isVaccine ? 2 : isPotionTacked ? 10 : 30;
    }

    public long getDeadTime()
    {
        return deadTime;
    }

    public void setDeadTime(long deadTime)
    {
        this.deadTime = deadTime;
    }

    public long getDangerTime()
    {
        return dangerTime;
    }

    public void setDangerTime(long dangerTime)
    {
        this.dangerTime = dangerTime;
    }

    public double getNearDis()
    {
        return nearDis;
    }

    public void setNearDis(double nearDis)
    {
        this.nearDis = nearDis;
    }

    public UUID getUid()
    {
        return uid;
    }

    public void setUid(UUID uid)
    {
        this.uid = uid;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public boolean isInfected()
    {
        return isInfected;
    }

    public void setInfected(boolean infected)
    {
        isInfected = infected;
    }

    public boolean isMasked()
    {
        return isMasked;
    }

    public void setMasked(boolean masked)
    {
        isMasked = masked;
    }

    public boolean isDisinfected()
    {
        return isDisinfected;
    }

    public void setDisinfected(boolean disinfected)
    {
        isDisinfected = disinfected;
    }

    public boolean isPotionTacked()
    {
        return isPotionTacked;
    }

    public void setPotionTacked(boolean potionTacked)
    {
        isPotionTacked = potionTacked;
    }

    public boolean isVaccine()
    {
        return isVaccine;
    }

    public void setVaccine(boolean vaccine)
    {
        isVaccine = vaccine;
    }

    public long getMaskTime()
    {
        return maskTime;
    }

    public void setMaskTime(long maskTime)
    {
        this.maskTime = maskTime;
    }

    public long getDisinfectorTime()
    {
        return disinfectorTime;
    }

    public void setDisinfectorTime(long disinfectorTime)
    {
        this.disinfectorTime = disinfectorTime;
    }
}
