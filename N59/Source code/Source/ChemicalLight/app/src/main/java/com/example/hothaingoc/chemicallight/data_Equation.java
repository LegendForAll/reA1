package com.example.hothaingoc.chemicallight;

public class data_Equation {
    private  String TG;
    private  String SP;
    private  String DK;

    public data_Equation() {

    }

    public data_Equation(String TG, String SP, String DK) {
        this.TG = TG;
        this.SP = SP;
        this.DK = DK;
    }

    public String getTG() {
        return TG;
    }

    public void setTG(String TG) {
        this.TG = TG;
    }

    public String getSP() {
        return SP;
    }

    public void setSP(String SP) {
        this.SP = SP;
    }

    public String getDK() {
        return DK;
    }

    public void setDK(String DK) {
        this.DK = DK;
    }
}
