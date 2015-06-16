package com.elis.tsp;

import java.util.ArrayList;

/**
 * Created by elis on 5/6/2015.
 */
public class TourManager {
    //destinations holder
    private static ArrayList destinationCities = new ArrayList<City>();

    //add a destination
    public static void addCity(City city){
        destinationCities.add(city);
    }

    public static City getCity(int index){
        return (City)destinationCities.get(index);
    }

    //Get th number of destinations
    public  static int numberOfCities(){
        return destinationCities.size();
    }
}
