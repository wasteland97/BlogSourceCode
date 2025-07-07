package com.ali.interviewknowledge.NormalProblem.cheapHotel;

/**
 * @author wfhstart
 * @create 2025-04-02
 */
public class Hotel {
    private int hotelId;

    private double price;

    private double distance;

    public Hotel(int hotelId, double price, double distance) {
        this.hotelId = hotelId;
        this.price = price;
        this.distance = distance;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", price=" + price +
                ", distance=" + distance +
                '}';
    }
}
