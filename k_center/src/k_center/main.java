/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k_center;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

/**
 *
 * @author raf
 */
public class main {

    public final static boolean DISPLAY = false; //mettre des if(DISPLAY) a tous les affichage, cela permettra de tous les supprimer pour les tests de performance
    public final static boolean DISPLAY_SOLUCE = false;
    public final static boolean DISPLAY_INSTANCE = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //creation de l'instance de test
        Generator generator = new Generator();
        //generator.newInstance(n, k, C0, Ch, nbGroups);
        Instance instance = generator.newInstance(100, 10, 1, 100, 5);
        if (DISPLAY_INSTANCE) {
            System.out.println(instance);
            System.out.println("----------------------");
        }
        
        DecimalFormat df = new DecimalFormat("#.000");
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(sym);
        
        
        //resolution du probleme
        //solve(instance, the minimum number of hub to try, depth of the k_mean, number of retry k-mean, number of retry tsp)
        double cost = Solver.solve(instance, 1, 5, 5, 10);
        //test valeur en fonction de nb hub
        /*for(int k = instance.k; k > 0; k--){
            instance.k = k;
        double cost = Solver.solve(instance, k, 5, 5, 5);
        //System.out.println("k_mean = " + k + " \t cost = " + cost);
        
        System.out.println(k + " \t " + (cost));
        }*/
        
        //test valeur et temps execution en fonction de k_mean_depth
        /*for(int depth = 1; depth < 10; depth++){
            instance.k = 10;
            long startTime = System.nanoTime();
            double cost = Solver.solve(instance, 1, depth, 1, 5);
            long endTime = System.nanoTime();
            long searchTime = endTime - startTime;
            System.out.println(depth + "\t" + df.format(cost) + "\t " + searchTime);
        }*/
        
        
        //test nb retry de k_mean
        /*for(int nb_k_mean = 1; nb_k_mean < 10; nb_k_mean++){
            instance.k = 10;
            long startTime = System.nanoTime();
            double cost = Solver.solve(instance, 1, 1, nb_k_mean, 5);
            long endTime = System.nanoTime();
            long searchTime = endTime - startTime;
            System.out.println(nb_k_mean + "\t" + df.format(cost) + "\t " + searchTime);
        }*/
        
        //test nb retry de tsp
        /*double[] costs = new double[100];
        double[] searchTime = new double[100];
        for(int i = 0; i<100; i++){
            costs[i] = 0;
            searchTime[i] = 0;
        }
        for(int i = 0; i<10; i++){
            instance = generator.newInstance(100, 10, 1, 100, 5);
            for(int nb_tsp = 1; nb_tsp < 100; nb_tsp++){
                instance.k = 10;
                long startTime = System.nanoTime();
                costs[nb_tsp] += Solver.solve(instance, 1, 1, 1, nb_tsp);
                long endTime = System.nanoTime();
                searchTime[nb_tsp] += endTime - startTime;
            }
        }
            
        for(int i = 0; i<100; i++){
            System.out.println(i + "\t" + df.format(costs[i]/100) + "\t " + df.format(searchTime[i]/100));
        }*/
        
        System.out.println(cost);
    }

}
