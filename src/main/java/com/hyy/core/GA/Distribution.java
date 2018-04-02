package com.hyy.core.GA;

import org.springframework.stereotype.Component;

/**
 * Created by HengYanYan on 2016/5/2  23:05
 */
@Component
public class Distribution {
    // 经度
    private double longitude;
    // 维度
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public Distribution(double longitude,double latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Distribution(){}

    /*
   * 计算两点之间距离
   *
   * @param start
   *
   * @param end
   *
   * @return 米
   */
    public double getDistance(Distribution end)
    {

        double lon1 = (Math.PI / 180) * this.longitude;
        double lon2 = (Math.PI / 180) * end.longitude;
        double lat1 = (Math.PI / 180) * this.latitude;
        double lat2 = (Math.PI / 180) * end.latitude;

        // double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
        // double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
        // double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
        // double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);

        // 地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

        return d * 1000;
    }
}
