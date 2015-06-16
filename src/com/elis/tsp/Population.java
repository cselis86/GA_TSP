package com.elis.tsp;

import java.util.Collections;

/**\
 * Created by elis on 5/6/2015.
 */
public class Population{

    // Holds population of tours
    Tour[] tours;


    // Construct a population
    public Population(int populationSize, boolean initialise) {
        tours = new Tour[populationSize];
        // If we need to initialise a population of tours do so
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < populationSize(); i++) {
                Tour newTour = new Tour();
                newTour.generateIndividual();
                saveTour(i, newTour);
            }
        }
    }
    // Saves a tour
    public void saveTour(int index, Tour tour) {
        tours[index] = tour;
    }

    // Gets a tour from population
    public Tour getTour(int index) {
        return tours[index];
    }

    // Gets the best tour in the population
    public Tour getFittest() {
        Tour fittest = tours[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }
    // Gets the worst tour in the population
    public Tour getWorst() {
        Tour worst = tours[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++) {
            if (worst.getFitness() >= getTour(i).getFitness()) {
                worst = getTour(i);
            }
        }
        return worst;
    }

    //
    public void setRanking(){

        Tour worst = getWorst();
        worst.setRank(1);
        Tour fittest = getFittest();
        fittest.setRank(populationSize());

        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++) {
            if (worst.getFitness() >= getTour(i).getFitness()) {
                worst = getTour(i);
            }
        }


    }

    // Gets population size
    public int populationSize() {
        return tours.length;
    }



}