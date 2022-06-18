package com.gjx.instrument.entity;

import java.util.Objects;

public class GjxFile {
    private String index;
    private String aqi;
    private String co;
    private String no2;
    private String o3;
    private String pm10;
    private String pm2_5;
    private String quality;
    private String rank;
    private String so2;
    private String timePoint;
    private String city;

    public GjxFile() {
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GjxFile gjxFile = (GjxFile) o;
        return Objects.equals(index, gjxFile.index) && Objects.equals(aqi, gjxFile.aqi) && Objects.equals(co, gjxFile.co) && Objects.equals(no2, gjxFile.no2) && Objects.equals(o3, gjxFile.o3) && Objects.equals(pm10, gjxFile.pm10) && Objects.equals(pm2_5, gjxFile.pm2_5) && Objects.equals(quality, gjxFile.quality) && Objects.equals(rank, gjxFile.rank) && Objects.equals(so2, gjxFile.so2) && Objects.equals(timePoint, gjxFile.timePoint) && Objects.equals(city, gjxFile.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, aqi, co, no2, o3, pm10, pm2_5, quality, rank, so2, timePoint, city);
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(String pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
