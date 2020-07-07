package com.kiss_the_rain8677.DisShow;

import java.util.UUID;

public class HealthList {

    UUID uid;
    String playerName;
    boolean isInfected;//是否感染
    boolean isMusked;//是否戴口罩
    boolean isDisinfected;//是否消毒
    boolean isDruged;//是否服药
    boolean isVaccine;//是否打过疫苗

    public HealthList(UUID uid,String playerName) {
        this.uid=uid;
        this.playerName = playerName;
        this.isInfected=false;
        this.isDisinfected=false;
        this.isMusked=false;
        this.isDruged=false;
        this.isVaccine=false;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
    }

    public boolean isMusked() {
        return isMusked;
    }

    public void setMusked(boolean musked) {
        isMusked = musked;
    }

    public boolean isDisinfected() {
        return isDisinfected;
    }

    public void setDisinfected(boolean disinfected) {
        isDisinfected = disinfected;
    }

    public boolean isDruged() {
        return isDruged;
    }

    public void setDruged(boolean druged) {
        isDruged = druged;
    }

    public boolean isVaccine() {
        return isVaccine;
    }

    public void setVaccine(boolean vaccine) {
        isVaccine = vaccine;
    }


}
