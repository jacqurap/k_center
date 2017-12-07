/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k_center;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author raf
 */
public class Instance {
    
    int Ch; //cost of a hub
    int C0; //cost of an arc
    int k; //maximum number of hubs
    ArrayList<Point> node; //coordonnées des villes
    

    public Instance(){}
    public Instance(int Ch, int C0, int k, ArrayList<Point> node){
        this.Ch = Ch;
        this.C0 = C0;
        this.k = k;
        this.node = new ArrayList<Point>();
        node.forEach((p) -> {
            this.node.add(new Point(p.x, p.y));
        });
    }
    
    public String toString(){
        String s = "Ch = " + Ch + "\nC0 = " + C0 + "\nk = " + k + "\nnodes = ";
        s = this.node.stream().map((p) -> "\n" + p.x + "\t" + p.y).reduce(s, String::concat);
        return s;
    }
}
