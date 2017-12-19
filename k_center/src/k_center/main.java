/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k_center;

import java.util.ArrayList;

/**
 *
 * @author raf
 */
public class main {

    public final static boolean DISPLAY = false; //mettre des if(DISPLAY) a tous les affichage, cela permettra de tous les supprimer pour les tests de performance

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //creation de l'instance de test
        Generator generator = new Generator();
        Instance instance = generator.newInstance();
        if (DISPLAY) {
            System.out.println(instance);
            System.out.println("----------------------");
        }
        //resolution du probleme
        //solve(instance, the minimum number of hub to try, depth of the k_mean, number of retry k-mean, number of retry tsp)
        double cost = Solver.solve(instance, 1, 10, 10, 10);

        System.out.println(cost);
    }

}
