package com.elis.tsp;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by elis on 6/6/2015.
 */
public class GA {

    /* GA parameters */
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;




    public static Population evolvePopulation(Population population, int select){
        Population newPopulation = new Population(population.populationSize(),false);

        int elitismOffset = 0;
        if (elitism){
            newPopulation.saveTour(0, population.getFittest());
            elitismOffset =1;
        }

        //crossover Population
        for(int i = elitismOffset; i <newPopulation.populationSize();i++){
            Tour parent1 ;
            Tour parent2 ;
            if(select==1){
                //select parents wheel-roulette
                 parent1 = wheelRouletteSelection(population);
                 parent2 = wheelRouletteSelection(population);
            }else {
                //select parents wheel-roulette
                parent1 = rankSelection(population);
                parent2 = rankSelection(population);
            }

            //crosover parents

            Tour child = crossover(parent1,parent2);

            newPopulation.saveTour(i,child);
        }
        // Mutate the new population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }
        return newPopulation;
    }

    // Mutate a tour using swap mutation
    private static void mutate(Tour tour) {
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());

                // Get the cities at target position in tour
                City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);

                // Swap them around
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
            }
        }
    }





    private static Tour crossover(Tour parent1, Tour parent2) {
        // Create new child tour
        Tour child = new Tour();

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() * parent1.tourSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.tourSize(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }

        return child;
    }

    private static Tour wheelRouletteSelection(Population population){
        float sum =0;
        for(int i=0;i<population.populationSize();i++){
            sum += population.getTour(i).getFitness();
        }
        Random rand = new Random();
        float randomN = rand.nextFloat() * (sum) ;
        float partSum = 0 ;
        for(int i=0;i<population.populationSize();i++){
            partSum += population.getTour(i).getFitness();
            if(partSum >= randomN){
                return population.getTour(i);
            }
        }
        return null;
    }



    private static Tour rankSelection(Population population){
        //set rank
        Arrays.sort(population.tours);
        for (int i=0; i<population.populationSize();i++){
            population.getTour(i).setRank(i);
        }

        int sum =0;
        for(int i=0;i<population.populationSize();i++){
            sum += population.getTour(i).getRank();
        }

        Random rand = new Random();
        int randomN = rand.nextInt(sum)  ;

        int partSum = 0 ;
        for(int i=0;i<population.populationSize();i++){
            partSum += population.getTour(i).getRank();
            if(partSum >= randomN){
                return population.getTour(i);
            }
        }
        return null;
    }


    private static Tour tournamentSelection(Population population) {
        //create tournament chromosomes
        Population tournament = new Population(tournamentSize, false);
        //for each place in the turnament add a random cromosome
        for (int i = 0; i<tournamentSize;i++){
            int randomIndex = (int)(Math.random()*population.populationSize());
            tournament.saveTour(i, population.getTour(randomIndex));
        }

        //get the fittest tour
        Tour fittest = tournament.getFittest();

        return fittest;

    }
}
