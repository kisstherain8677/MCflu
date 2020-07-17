package com.new_afterwave.mc.VexView;

import com.new_afterwave.mc.Util.HealthList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexComponents;
import lk.vexview.gui.components.VexImage;
import lk.vexview.gui.components.VexText;

public class FluGui {
    private HealthList healthList;

    public FluGui(HealthList healthList) {
        this.healthList = healthList;
    }

    public VexGui getFluGui() {
        List<VexComponents> vexComponentsList = new ArrayList<>();
        double titleSize = 2.0D;
        double conSize = 1.3D;
        int interval = 12;
        int leftint = 120;
        vexComponentsList.add(new VexImage("[local]bg.jpg", -1, -1, 400, 200));
        vexComponentsList.add(new VexText(leftint+10, 20, Arrays.asList( "用户"+this.healthList.getPlayerName()+"的健康状态"), titleSize));
        vexComponentsList.add(new VexText(leftint, 55, Arrays.asList( "感染状态：               " + (this.healthList.isInfected() ? "已感染":"未感染")), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + interval, Arrays.asList("是否服药：               " + (this.healthList.isPotionTacked() ? "已服药":"未服药")), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 2 * interval, Arrays.asList("是否戴口罩：             " + (this.healthList.isMasked() ? "已戴":"未戴")), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 3 * interval, Arrays.asList("是否消毒：               " + (this.healthList.isDisinfected() ? "已消毒":"未消毒")), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 4 * interval, Arrays.asList("是否免疫：               " + (this.healthList.isVaccine() ? "已免疫":"未免疫")), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 5 * interval, Arrays.asList("进食感染概率：           " + this.healthList.getEatInfectRate()+"%"), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 6 * interval, Arrays.asList("感染死亡概率：           " + this.healthList.getDeadRate()+"%"), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 7 * interval, Arrays.asList("最近疑似感染源距离：    " + this.healthList.getNearDis()), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 8 * interval, Arrays.asList("N95口罩剩余时间：        " + this.healthList.getMaskTime()/20+"s"), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 9 * interval, Arrays.asList("消毒剂剩余时间：         " + this.healthList.getDisinfectorTime()/20+"s"), conSize));
        vexComponentsList.add(new VexText(leftint, 55 + 10 * interval, Arrays.asList("感染结束剩余时间：       " + this.healthList.getDeadTime()/20+"s"), conSize));
        return new VexGui("[local]gui.png", -1, -1, 400, 200, vexComponentsList);
    }

    public HealthList getHealthList() {
        return this.healthList;
    }

    public void setHealthList(HealthList healthList) {
        this.healthList = healthList;
    }
}
