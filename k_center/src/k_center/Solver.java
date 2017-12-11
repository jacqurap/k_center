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
public class Solver {
    
    
    /**
     * regroupe les nodes en k groupes
     * @param step number of step fo this algorithm, more step = more precise, but slower
     * @return an arraylist of subgraph, each subgraph contains a subset of the initial nodes and the last center used by this algo
     */
    public static ArrayList<SubGraph> k_mean(Instance inst, int step){
        int nbGroups = Integer.min(inst.k, inst.node.size()); //just to be sure that we don't have more groups than nodes
        ArrayList<SubGraph> groups = new ArrayList<>();
        
        for(int i=0; i<nbGroups; i++){ //initialisation of each groups (each one needs an initial center)
            SubGraph sg = new SubGraph();
            sg.add(inst.node.get(i));
            sg.updateCenter();
            groups.add(sg);
        }
        
        for(int i=0; i<step; i++){
            for(SubGraph g : groups){ //clear the list of nodes of each group
                g.clear();
            }
            inst.node.forEach((p) -> { //for each node, we put it in the subgraph of the nearest center
                double distance = Double.MAX_VALUE;
                SubGraph grMinDistance = null;
                for(SubGraph g : groups){
                    if(p.distance(g.getCenter()) < distance){
                        distance = p.distance(g.getCenter());
                        grMinDistance = g;
                    }
                }
                grMinDistance.add(p);
            });
            for(SubGraph g : groups){ //then we update the center for him to be at the exact center of all its nex nodes
                g.updateCenter();
            }
        }
        return groups;
    }
    
    /**
     * merge les k groups en fonction de leurs proximite et du rapport entre le coup des hubs et des trajets
     */
    public static void merge(){
        //TODO
    }
    
    /**
     * calcul uns valeur aprochee du coup en trajet de chaque groupe restants
     */
    public static void tsp(){
        //TODO
    }
}
