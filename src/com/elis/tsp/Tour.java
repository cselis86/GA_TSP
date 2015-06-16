package com.elis.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by elis on 5/6/2015.
 */
public class Tour implements Comparable<Tour> {
    //holds a tour list
    private ArrayList tour = new ArrayList<City>();

    private double fitness = 0;
    private int distance =0;
    private int rank = 0;

    //create a blanck tour
    public Tour(){
        for (int i=0; i < TourManager.numberOfCities();i++){
            tour.add(null);
        }
    }

    public Tour(ArrayList tour){
        this.tour = tour;
    }

    //Create random rout
    public void generateIndividual(){
        //add all the available destination
        for(int i=0;i<TourManager.numberOfCities();i++){
            setCity(i,TourManager.getCity(i));
        }
        //randomly reorder the tour
        Collections.shuffle(tour);
    }
    //get city from tour
    public City getCity(int tourPosition){
        return (City)tour.get(tourPosition);
    }
    //set city in a certen position within tour
    public void setCity(int tourPosition, City city) {
        tour.set(tourPosition,city);
        //if the tour has been altered we need to resaet the fitness and sistance
        fitness = 0;
        distance = 0;
    }

    //get the tours fitness

    public double getFitness(){
        if(fitness==0){
            fitness=1/(double)getDistance();
        }
        return fitness;
    }

    //gets the total distance of the tour
    public int getDistance() {
        if(distance==0){
            int tourDistance = 0;
            for(int iCity=0;iCity<tourSize();iCity++){
                //get city we are traveling from
                City fromCity = getCity(iCity);
                // destination
                City destinationCity;
                // Check we're not on our tour's last city, if we are set our
                // tour's final destination city to our starting city
                if(iCity+1 < tourSize()){
                    destinationCity = getCity(iCity+1);
                }
                else{
                    destinationCity = getCity(0);
                }

                //get distance between the two cities
                tourDistance +=fromCity.distanceTo(destinationCity);
            }
            distance=tourDistance;
        }
        return distance;
    }

    public int tourSize() {
        return tour.size();
    }

    //check if the tour contains a city
    public boolean containsCity(City city){
        return tour.contains(city);
    }
    public void setRank(int r){
        this.rank=r;
    }
    public int getRank(){
        return rank;
    }
    @Override
    public String toString(){
        String geneString = " | ";
        for (int i=0;i< tourSize();i++){
            geneString += getCity(i)+" | ";
        }
        return geneString;
    }

    @Override
    public int compareTo(Tour t) {
        //ascending order
        return this.getDistance() - t.getDistance() ;

        //descending order
        //return compareQuantity - this.quantity;
    }


}
