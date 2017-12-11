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
    public static void merge(ArrayList<SubGraph> groups, Instance inst){
        boolean stillMerging = true;
        while(stillMerging && groups.size() >= 2){ //ensure that we still have at least 2 groups and that we didn't stop
            
            //research of the 2 nearest groups (using centers)
            SubGraph sg1 = null, sg2 = null;
            double currentMinDistance = Double.MAX_VALUE;
            for(int i=0; i<groups.size()-1; i++){
                for(int j=i+1; j<groups.size(); j++){
                    if(groups.get(i).getCenter().distance(groups.get(j).getCenter()) < currentMinDistance){
                        sg1 = groups.get(i);
                        sg2 = groups.get(j);
                        currentMinDistance = groups.get(i).getCenter().distance(groups.get(j).getCenter());
                    }
                }
            }
            
            //TODO using only centers is probably very bad, so we should look for the closest nodes from diferents groups
            //we could then switch between one and the other to have defferent efficiencies
            
            
            if(sg1.getCenter().distance(sg2.getCenter())*inst.C0 < inst.Ch){ //if those groups a close enough, we merge them
                sg1.add(sg2);
                groups.remove(sg2);
            }
            else stillMerging = false; //else we won't found any close enough groups, so we can stop
        }
    }
    
    /**
     * calcul uns valeur aprochee du coup en trajet de chaque groupe restants
     */
    public static void tsp(){
        //TODO
    }
}
