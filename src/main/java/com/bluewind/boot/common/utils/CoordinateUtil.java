package com.bluewind.boot.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 地理位置坐标转换工具类
 *
 * @author liuxingyu01
 * @date 2021-03-07-19:18
 * @description 在线经纬度计算网址：https://www.hhlink.com/%E7%BB%8F%E7%BA%AC%E5%BA%A6/
 **/
public class CoordinateUtil {

    /**
     * 长半轴
     */
    private static final double MAJOR_SEMI_AXIS = 6378245.0;

    /**
     * 扁率
     */
    private static final double FLAT_RATE = 0.00669342162296594323;

    private static final double X_PI = Math.PI * 3000.0 / 180.0;

    /**
     * 地球半径，单位：千米
     */
    private static final double EARTH_RADIUS = 6371.0;

    /**
     * 坐标组内坐标值个数，[lng, lat]则包含经度和纬度两个坐标值
     */
    private static final int COORDINATE_NUM = 2;

    /**
     * The enum Coordinate type.
     */
    enum CoordinateType {
        /**
         * WGS-84: 原始坐标
         */
        WGS_84("WGS-84", "原始坐标"),

        /**
         * GCJ-02: 国家局坐标, 高德、谷歌地图使用
         */
        GCJ_02("GCJ-02", "国家局坐标"),

        /**
         * BD-09: 百度坐标，百度地图使用
         */
        BD_09("BD-09", "百度坐标"),
        ;

        /**
         * 坐标类型编码
         */
        private final String code;

        /**
         * 坐标类型描述
         */
        private final String desc;

        CoordinateType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        /**
         * Gets code.
         *
         * @return the code
         */
        public String getCode() {
            return code;
        }

        /**
         * Gets desc.
         *
         * @return the desc
         */
        public String getDesc() {
            return desc;
        }
    }

    /**
     * 坐标类型转换
     *
     * @param lng      经度
     * @param lat      纬度
     * @param fromType 原坐标类型
     * @param toType   目标坐标类型
     *
     * @return 目标坐标数组[longitude, latitude]
     */
    public static double[] convertCoordinates(double lng, double lat, CoordinateType fromType, CoordinateType toType) {
        switch (fromType) {
            case WGS_84:
                switch (toType) {
                    case WGS_84:
                        throw new IllegalArgumentException("请输入不同的坐标类型进行转换");
                    case GCJ_02:
                        return wgs84ToGcj02(lng, lat);
                    case BD_09:
                        return wgs84ToBd09(lng, lat);
                    default:
                        return null;
                }
            case BD_09:
                switch (toType) {
                    case WGS_84:
                        return bd09ToWgs84(lng, lat);
                    case GCJ_02:
                        return bd09ToGcj02(lng, lat);
                    case BD_09:
                        throw new IllegalArgumentException("请输入不同的坐标类型进行转换");
                    default:
                        return null;
                }
            case GCJ_02:
                switch (toType) {
                    case WGS_84:
                        return gcj02ToWgs84(lng, lat);
                    case GCJ_02:
                        throw new IllegalArgumentException("请输入不同的坐标类型进行转换");
                    case BD_09:
                        return gcj02ToBd09(lng, lat);
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    /**
     * 坐标类型转换
     *
     * @param coo      原坐标数组[lng, lat]
     * @param fromType 原坐标类型
     * @param toType   目标坐标类型
     *
     * @return 目标坐标数组[longitude, latitude]
     */
    public static double[] convertCoordinates(double[] coo, CoordinateType fromType, CoordinateType toType) {
        if (coo == null || coo.length != COORDINATE_NUM) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        return convertCoordinates(coo[0], coo[1], fromType, toType);
    }

    /**
     * GCJ-02(国家局、高德、谷歌使用)坐标 转换为 BD-09(百度使用)坐标
     *
     * @param lng 经度
     * @param lat 纬度
     *
     * @return BD -09(百度使用)坐标组[longitude, latitude]
     */
    public static double[] gcj02ToBd09(double lng, double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
        double resultX = new BigDecimal(z * Math.cos(theta) + 0.0065).setScale(6, RoundingMode.HALF_UP).doubleValue();
        double resultY = new BigDecimal(z * Math.sin(theta) + 0.006).setScale(6, RoundingMode.HALF_UP).doubleValue();
        return new double[]{resultX, resultY};
    }

    /**
     * GCJ-02(国家局、高德、谷歌使用)坐标 转换为 BD-09(百度使用)坐标
     *
     * @param gcj02 GCJ-02坐标组[lng, lat]
     *
     * @return BD -09(百度使用)坐标组
     */
    public static double[] gcj02ToBd09(double[] gcj02) {
        if (gcj02 == null || gcj02.length != COORDINATE_NUM) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        return gcj02ToBd09(gcj02[0], gcj02[1]);
    }

    /**
     * BD-09(百度使用)坐标 转换为 GCJ-02(国家局、高德、谷歌使用)坐标
     *
     * @param lng 经度
     * @param lat 纬度
     *
     * @return GCJ -02(国家局、高德、谷歌使用)坐标组[longitude, latitude]
     */
    public static double[] bd09ToGcj02(double lng, double lat) {
        lng = lng - 0.0065;
        lat = lat - 0.006;
        double z = Math.sqrt(lng * lng + lat * lat) - 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) - 0.000003 * Math.cos(lng * X_PI);
        double resultX = new BigDecimal(z * Math.cos(theta)).setScale(6, RoundingMode.HALF_UP).doubleValue();
        double resultY = new BigDecimal(z * Math.sin(theta)).setScale(6, RoundingMode.HALF_UP).doubleValue();
        return new double[]{resultX, resultY};
    }

    /**
     * BD-09(百度使用)坐标 转换为 GCJ-02(国家局、高德、谷歌使用)坐标
     *
     * @param bd09 BD-09(百度使用)坐标组[lng, lat]
     *
     * @return GCJ -02(国家局、高德、谷歌使用)坐标组[longitude, latitude]
     */
    public static double[] bd09ToGcj02(double[] bd09) {
        if (bd09 == null || bd09.length != COORDINATE_NUM) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        return bd09ToGcj02(bd09[0], bd09[1]);
    }

    /**
     * WGS-84(原始)坐标 转换为 GCJ-02(国家局、高德、谷歌使用)坐标<br>
     * 默认坐标在中国版图内，如果超过中国版图则直接返回原坐标
     *
     * @param lng 经度
     * @param lat 纬度
     *
     * @return GCJ -02(国家局、高德、谷歌使用)坐标组[longitude, latitude]
     */
    public static double[] wgs84ToGcj02(double lng, double lat) {
        return wgs84ToGcj02(lng, lat, true);
    }

    /**
     * Wgs 84 to gcj 02 double [ ].
     *
     * @param wgs84 the wgs 84
     *
     * @return the double [ ]
     */
    public static double[] wgs84ToGcj02(double[] wgs84) {
        if (wgs84 == null || wgs84.length != COORDINATE_NUM) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        return wgs84ToGcj02(wgs84[0], wgs84[1]);
    }

    /**
     * WGS-84(原始)坐标 转换为 GCJ-02(国家局、高德、谷歌使用)坐标
     *
     * @param lng     经度
     * @param lat     纬度
     * @param inChina 是否在中国境内
     *
     * @return GCJ -02(国家局、高德、谷歌使用)坐标组[longitude, latitude]
     */
    public static double[] wgs84ToGcj02(double lng, double lat, boolean inChina) {
        // 如果要求坐标在中国地图版图内，且传入的坐标超出中国版图，则直接返回原坐标
        if (inChina && isCoordinateOutOfChina(lng, lat)) {
            return new double[]{lng, lat};
        }
        double dLat = transLat(lng - 105.0, lat - 35.0);
        double dLon = transLon(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - FLAT_RATE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((MAJOR_SEMI_AXIS * (1 - FLAT_RATE)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (MAJOR_SEMI_AXIS / sqrtMagic * Math.cos(radLat) * Math.PI);
        double resultX = new BigDecimal(lat + dLat).setScale(6, RoundingMode.HALF_UP).doubleValue();
        double resultY = new BigDecimal(lng + dLon).setScale(6, RoundingMode.HALF_UP).doubleValue();
        return new double[]{resultX, resultY};
    }

    /**
     * WGS-84(原始)坐标 转换为 GCJ-02(国家局、高德、谷歌使用)坐标
     *
     * @param wgs84   WGS-84(原始)坐标组[lng, lat]
     * @param inChina 是否在中国版图内
     *
     * @return GCJ -02(国家局、高德、谷歌使用)坐标组[longitude, latitude]
     */
    public static double[] wgs84ToGcj02(double[] wgs84, boolean inChina) {
        if (wgs84 == null || wgs84.length != COORDINATE_NUM) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        return wgs84ToGcj02(wgs84[0], wgs84[1], inChina);
    }

    /**
     * GCJ-02(国家局、高德、谷歌使用)坐标 转换为 WGS-84(原始)坐标<br>
     * 默认坐标在中国版图内，如果超过中国版图则直接返回原坐标的转换格式
     *
     * @param lng 经度
     * @param lat 纬度
     *
     * @return WGS -84(原始)坐标组[longitude, latitude]
     */
    public static double[] gcj02ToWgs84(double lng, double lat) {
        double[] wgLoc = new double[2];
        double wgX = lng, wgY = lat;
        double dX, dY;
        int count = 0;
        while (true) {
            double[] currGcLoc = wgs84ToGcj02(wgX, wgY);
            dX = lng - currGcLoc[0];
            dY = lat - currGcLoc[1];
            // 1e-6 centimeter level accuracy
            if (Math.abs(dY) < 1e-6 && Math.abs(dX) < 1e-6) {
                // Result of experiment: Most of the time 2 iterations would be enough for an 1e-8 accuracy (millimeter level).
                wgLoc[0] = new BigDecimal(wgX).setScale(6, RoundingMode.HALF_UP).doubleValue();
                wgLoc[1] = new BigDecimal(wgY).setScale(6, RoundingMode.HALF_UP).doubleValue();
                break;
            }
            wgX += dX;
            wgY += dY;
            //超过 10000 次计算未满足条件，返回 0
            if (count > 10000) {
                wgLoc[0] = 0;
                wgLoc[1] = 0;
                break;
            }
            count++;
        }
        return wgLoc;
    }

    /**
     * GCJ-02(国家局、高德、谷歌使用)坐标 转换为 WGS-84(原始)坐标<br>
     * 默认坐标在中国版图内，如果超过中国版图则直接返回原坐标的转换格式
     *
     * @param gcj02 GCJ-02(国家局、高德、谷歌使用)坐标组[lng, lat]
     *
     * @return WGS -84(原始)坐标组[longitude, latitude]
     */
    public static double[] gcj02ToWgs84(double[] gcj02) {
        if (gcj02 == null || gcj02.length != COORDINATE_NUM) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        return gcj02ToWgs84(gcj02[0], gcj02[1]);
    }

    /**
     * WGS-84(原始)坐标 转换为 BD-09(百度使用)坐标
     *
     * @param lng 经度
     * @param lat 纬度
     *
     * @return BD -09(百度使用)坐标组[longitude, latitude]
     */
    public static double[] wgs84ToBd09(double lng, double lat) {
        return gcj02ToBd09(wgs84ToGcj02(lng, lat));
    }

    /**
     * WGS-84(原始)坐标 转换为 BD-09(百度使用)坐标
     *
     * @param wgs84 WGS-84(原始)坐标组[lng, lat]
     *
     * @return BD -09(百度使用)坐标组[longitude, latitude]
     */
    public static double[] wgs84ToBd09(double[] wgs84) {
        return gcj02ToBd09(wgs84ToGcj02(wgs84));
    }

    /**
     * BD-09(百度使用)坐标 转换为 WGS-84(原始)坐标
     *
     * @param lng 经度
     * @param lat 纬度
     *
     * @return WGS -84(原始)坐标组[longitude, latitude]
     */
    public static double[] bd09ToWgs84(double lng, double lat) {
        return gcj02ToWgs84(bd09ToGcj02(lng, lat));
    }

    /**
     * BD-09(百度使用)坐标 转换为 WGS-84(原始)坐标
     *
     * @param bd09 BD-09(百度使用)坐标组[lng, lat]
     *
     * @return WGS -84(原始)坐标组[longitude, latitude]
     */
    public static double[] bd09ToWgs84(double[] bd09) {
        return gcj02ToWgs84(bd09ToGcj02(bd09));
    }

    /**
     * 计算两坐标点之间的球面距离（单位公里）
     *
     * @param lng1 坐标点1经度 - 角度值
     * @param lat1 坐标点1纬度 - 角度值
     * @param lng2 坐标点2经度 - 角度值
     * @param lat2 坐标点2纬度 - 角度值
     *
     * @return 两坐标点之间的球面距离 double
     */
    public static double distanceBetween2Coordinates(double lng1, double lat1, double lng2, double lat2) {
        // 先将经纬度坐标转换为弧度值
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);
        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);
        // 差值
        double vLon = Math.abs(lng1 - lng2);
        double vLat = Math.abs(lat1 - lat2);
        // h是一个球体上的切面，它的圆心即球心的一个周长最大的圆
        double h = haversine(vLat) + Math.cos(lat1) * Math.cos(lat2) * haversine(vLon);
        return 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
    }

    /**
     * 计算两坐标点之间的球面距离（单位公里）
     *
     * @param coo1 坐标点1 [latitude1, longitude1] - 角度值
     * @param coo2 坐标点2 [latitude2, longitude2] - 角度值
     *
     * @return 两坐标点之间的球面距离 double
     */
    public static double distanceBetween2Coordinates(double[] coo1, double[] coo2) {
        if (coo1 == null || coo2 == null) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        if (coo1.length != COORDINATE_NUM || coo2.length != COORDINATE_NUM) {
            throw new IllegalArgumentException("参数错误, 请传入待转换的经纬度数组");
        }
        return distanceBetween2Coordinates(coo1[0], coo1[1], coo2[0], coo2[1]);
    }

    /**
     * 判断坐标是否超出中国版图
     *
     * @param x 经度
     * @param y 纬度
     *
     * @return 坐标是否超出中国版图, 超出返回{@code true}, 否则返回{@code false}
     */
    private static boolean isCoordinateOutOfChina(double x, double y) {
        return !(x >= 72.004 && x <= 137.8347 && y >= 0.8293 && y <= 55.8271);
    }

    private static double transLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(x > 0 ? x : -x);
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(x > 0 ? x : -x);
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 半正矢公式，用于计算球面两点之间的距离
     *
     * @param theta 经度差值或纬度差值
     *
     * @return 球面两点之间的距离
     */
    private static double haversine(double theta) {
        double v = Math.sin(theta / 2);
        return v * v;
    }

}
