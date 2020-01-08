package com.unpas.chatak.model;

public class SliderModel {

    private  int banner;
    private String backgroudnColor;

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getBackgroudnColor() {
        return backgroudnColor;
    }

    public void setBackgroudnColor(String backgroudnColor) {
        this.backgroudnColor = backgroudnColor;
    }

    public SliderModel(int banner, String backgroudnColor) {
        this.banner = banner;
        this.backgroudnColor = backgroudnColor;
    }
}
