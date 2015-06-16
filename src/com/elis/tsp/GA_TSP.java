package com.elis.tsp;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Scanner;

import java.awt.geom.Line2D;

import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elis on 6/6/2015.
 */
public class GA_TSP {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Type the num Of cities :");
        int numberOfCities = sc.nextInt();

        System.out.println("Type the number of Population");
        int numberOfPopulation = sc.nextInt();

        System.out.println("Type the number of gennerations ");
        int numberOfGenerations = sc.nextInt();

        System.out.println("Type the number 1 (one) for wheel-roulette or an Integer for Rank  ");
        int sType = sc.nextInt();




        // Create and add our cities
        for (int i = 0 ; i< numberOfCities; i++){
            City city = new City();
            TourManager.addCity(city);
        }



        //create distance map
        double[][] distanceMap = new double[TourManager.numberOfCities()][TourManager.numberOfCities()];

        for (int from=0; from<TourManager.numberOfCities();from++){
            for (int to=0; to<TourManager.numberOfCities();to++){
                if(TourManager.getCity(from).equals(TourManager.getCity(to))){
                    distanceMap[from][to] = 0;
                }
                else{
                    distanceMap[from][to] = TourManager.getCity(from).distanceTo(TourManager.getCity(to));
                }
            }
        }

        NumberFormat formatter = new DecimalFormat("#0.00");
        //dispaly distances
        for(int i=0; i < TourManager.numberOfCities(); i++){
            for(int j=0; j < TourManager.numberOfCities(); j++) {
                System.out.print(formatter.format(distanceMap[i][j]) + " ");
            }
            System.out.println();
        }




        // Initialize population
        Population population = new Population(numberOfPopulation, true);
        System.out.println("Initial distance: " + population.getFittest().getDistance());


        //loop on selection
        boolean evolve = true;
        while(evolve){

            //start Timer
            long tStart = System.currentTimeMillis();

            // Evolve population for numberOfGeneration generations
            population = GA.evolvePopulation(population,sType);
            for (int i = 0; i < numberOfGenerations; i++) {
                population = GA.evolvePopulation(population,sType);
            }

            System.out.println();

            // Print final results
            System.out.println("Finished for " + TourManager.numberOfCities() + " Cities" );
            System.out.println("Final distance: " + population.getFittest().getDistance());
            System.out.println("Solution:");
            System.out.println(population.getFittest());
            final Tour tour = population.getFittest();

            long tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta / 1000.0;

            System.out.println("Time : " + elapsedSeconds + " " +population.getFittest().getDistance());



            System.out.println("Type the number 1 (one) for wheel-roulette or an Integer for Rank Selection ");
            sType = sc.nextInt();

            System.out.println("Type the number 1 to evolve again  or 0 to stop ");
            if (sc.nextInt()==1){
                evolve = true;
            }else evolve = false;


            // Initialize population
            population = new Population(numberOfPopulation, true);
            System.out.println("Initial distance: " + population.getFittest().getDistance());

        }
        final Tour tour = population.getFittest();


        //plot the rout
        Runnable r = new Runnable() {
            public void run() {
                LineComponent lineComponent = new LineComponent(400,400);

                for (int ii=0; ii<tour.tourSize()-1; ii++) {
                    lineComponent.addLine(tour.getCity(ii),tour.getCity(ii+1));
                }
                lineComponent.addLine(tour.getCity(tour.tourSize()-1),tour.getCity(0));
                JOptionPane.showMessageDialog(null, lineComponent);

                Graphics g = null;
                for (int ii=0; ii<tour.tourSize()-1; ii++) {

                    g.drawLine(tour.getCity(ii).getX(), tour.getCity(ii).getY(), tour.getCity(ii).getX(),tour.getCity(ii).getY());

                }
            }
        };
        SwingUtilities.invokeLater(r);


    }
}

class LineComponent extends JComponent {

    ArrayList<Line2D.Double> lines;
    Random random;

    LineComponent(int width, int height) {
        super();
        setPreferredSize(new Dimension(width,height));
        lines = new ArrayList<Line2D.Double>();
        random = new Random();
    }

    public void addLine(City cityF,City cityT) {
        int width = (int)getPreferredSize().getWidth();
        int height = (int)getPreferredSize().getHeight();

        Line2D.Double line = new Line2D.Double(
                cityF.getX(),
                cityF.getY(),
                cityT.getX(),
                cityT.getY()
        );
        lines.add(line);
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getPreferredSize();
        g.setColor(Color.black);
        for (Line2D.Double line : lines) {
            g.drawLine(
                    (int)line.getX1(),
                    (int)line.getY1(),
                    (int)line.getX2(),
                    (int)line.getY2()
            );
        }
    }
}
