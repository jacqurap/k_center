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

    public final static boolean DISPLAY = true; //mettre des if(DISPLAY) a tous les affichage, cela permettra de tous les supprimer pour les tests de performance
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        //creation de l'instance de test
        Generator generator = new Generator();
        Instance instance = generator.newInstance();
        System.out.println(instance);
        
        System.out.println("----------------------");
        
        //resolution du probleme
        ArrayList<SubGraph> list = Solver.k_mean(instance, 4);
        for(SubGraph sg : list){
            System.out.println(sg);
        }
        System.out.println("----------------------");
        
        Solver.merge(list, instance);
        for(SubGraph sg : list){
            System.out.println(sg);
        }
        System.out.println("there is still " + list.size() + " groups");
    }
    
}
