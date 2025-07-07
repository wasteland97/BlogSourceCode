package com.ali.interviewknowledge.NormalProblem.cheapHotel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wfhstart
 * @create 2025-04-02
 */
public class TestEnter {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(2, 112.9, 3.1);
        Hotel hotel1 = new Hotel(99, 202.9, 3.1);
        Hotel hotel2 = new Hotel(56, 101.9, 3.1);
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel1);
        hotels.add(hotel2);
        List<Hotel> res = getCheapHotel(hotels);
        for (Hotel item : res) {
            System.out.println(item.getHotelId());
        }
    }

    private static List<Hotel> getCheapHotel(List<Hotel> hotels){
        Set<Integer> needRemoveHotels = new HashSet<>();
        for (int i = 0; i < hotels.size(); i++) {
            Hotel baseHotel = hotels.get(i);
            for (int j = i + 1; j < hotels.size(); j++) {
                Hotel compareHotel = hotels.get(j);
                if (compareHotel.getPrice() < baseHotel.getPrice() || compareHotel.getDistance() < baseHotel.getDistance()) {
                    needRemoveHotels.add(compareHotel.getHotelId());
                    break;
                }
            }
        }
        List<Hotel> res = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (!needRemoveHotels.contains(hotel.getHotelId())) {
                res.add(hotel);
            }
        }
        return res;
    }
}
