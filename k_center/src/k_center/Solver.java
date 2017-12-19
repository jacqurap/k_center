/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k_center;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author raf
 */
public class Solver {

    /**
     * solve the instance
     *
     * @param inst the instance to optimize
     * @param k_min the minimum number of hub to try (1 to try for every number
     * of hub and inst.k to try only the maximum) knowing that small values of k
     * are the longest to compute and the optimal value is usually at higher
     * value of k
     * @param k_mean_depth the depth of the k_mean algorithm (the larger the
     * better, but slow the computation)
     * @param k_mean_shuffle the number of retry of the k_mean algorithm (the
     * larger the better, but slow the computation)
     * @param tsp_shuffle the number of retry of the tsp algorithm (the larger
     * the better, but slow the computation)
     * @return the best found value
     */
    public static double solve(Instance inst, int k_min, int k_mean_depth, int k_mean_shuffle, int tsp_shuffle) {

        double cost = Double.MAX_VALUE;
        for (int i = inst.k; i >= k_min; i--) {
            inst.k = i;
            for (int j = 0; j < k_mean_shuffle; j++) {
                Collections.shuffle(inst.node);
                ArrayList<SubGraph> list = k_mean(inst, 4);
                if (main.DISPLAY) {
                    for (SubGraph sg : list) {
                        System.out.println(sg);
                    }
                    System.out.println("----------------------");
                }

                //merge(list, inst);
                if (main.DISPLAY) {
                    for (SubGraph sg : list) {
                        System.out.println(sg);
                    }
                    System.out.println("there is still " + list.size() + " groups");
                }
                for (int k = 0; k < tsp_shuffle; k++) {
                    double dist = tsp(list);
                    double currentCost = dist * inst.C0 + list.size() * inst.Ch;
                    if (main.DISPLAY) {
                        System.out.println("k = " + inst.k + " \t, cost = " + currentCost);
                    }
                    cost = Double.min(cost, currentCost);
                }
            }
        }
        return cost; //return the total cost
    }

    /**
     * regroupe les nodes en k groupes
     *
     * @param depth number of depth fo this algorithm, more depth = more
     * precise, but slower
     * @return an arraylist of subgraph, each subgraph contains a subset of the
     * initial nodes and the last center used by this algo
     */
    public static ArrayList<SubGraph> k_mean(Instance inst, int depth) {
        int nbGroups = Integer.min(inst.k, inst.node.size()); //just to be sure that we don't have more groups than nodes
        ArrayList<SubGraph> groups = new ArrayList<>();
        for (int i = 0; i < nbGroups; i++) { //initialisation of each groups (each one needs an initial center)
            SubGraph sg = new SubGraph();
            sg.add(inst.node.get(i));
            sg.updateCenter();
            groups.add(sg);
        }

        for (int i = 0; i < depth; i++) {
            for (SubGraph g : groups) { //clear the list of nodes of each group
                g.clear();
            }
            inst.node.forEach((p) -> { //for each node, we put it in the subgraph of the nearest center
                double distance = Double.MAX_VALUE;
                SubGraph grMinDistance = null;
                for (SubGraph g : groups) {
                    if (p.distance(g.getCenter()) < distance) {
                        distance = p.distance(g.getCenter());
                        grMinDistance = g;
                    }
                }
                grMinDistance.add(p);
            });
            for (SubGraph g : groups) { //then we update the center for him to be at the exact center of all its nex nodes
                g.updateCenter();
            }
        }
        return groups;
    }

    /**
     * merge les k groups en fonction de leurs proximite et du rapport entre le
     * coup des hubs et des trajets
     */
    public static void merge(ArrayList<SubGraph> groups, Instance inst) {
        boolean stillMerging = true;
        while (stillMerging && groups.size() >= 2) { //ensure that we still have at least 2 groups and that we didn't stop

            //research of the 2 nearest groups (using centers)
            SubGraph sg1 = null, sg2 = null;
            double currentMinDistance = Double.MAX_VALUE;
            for (int i = 0; i < groups.size() - 1; i++) {
                for (int j = i + 1; j < groups.size(); j++) {
                    if (groups.get(i).getCenter().distance(groups.get(j).getCenter()) < currentMinDistance) {
                        sg1 = groups.get(i);
                        sg2 = groups.get(j);
                        currentMinDistance = groups.get(i).getCenter().distance(groups.get(j).getCenter());
                    }
                }
            }

            //TODO using only centers is probably very bad, so we should look for the closest nodes from diferents groups
            //we could then switch between one and the other to have defferent efficiencies
            if (sg1.getCenter().distance(sg2.getCenter()) * inst.C0 < inst.Ch) { //if those groups a close enough, we merge them
                sg1.add(sg2);
                groups.remove(sg2);
            } else {
                stillMerging = false; //else we won't found any close enough groups, so we can stop
            }
        }
    }

    /**
     * calcul uns valeur aprochee du coup en trajet de chaque groupe restants
     * this is a greedy algorithm wich select the nearest neighbour as the next
     * step
     */
    public static double tsp(ArrayList<SubGraph> groups) {
        double dtot = 0; // the total distance
        ArrayList<SubGraph> myGroups = new ArrayList<>();
        for (SubGraph s : groups) {
            myGroups.add(s.clone());
        }
        for (SubGraph s : myGroups) { //foreach subgraph
            Random rand = new Random();
            int r = rand.nextInt(s.node.size());
            Point firstPoint = s.node.get(r);
            s.node.remove(r);
            Point currentClosestPoint = firstPoint;
            while (!s.node.isEmpty()) {
                double currentMinDistance = Double.MAX_VALUE;
                for (Point p : s.node) { //select the nearest neighbour
                    if (currentClosestPoint.distance(p) < currentMinDistance) {
                        currentMinDistance = currentClosestPoint.distance(p);
                        currentClosestPoint = p;
                    }
                }
                dtot += currentMinDistance;
                s.node.remove(currentClosestPoint);
            }
            dtot += firstPoint.distance(currentClosestPoint); //add the cost to return to the hub (0 if the hub is alone in this group)
        }
        return dtot;
    }
}
