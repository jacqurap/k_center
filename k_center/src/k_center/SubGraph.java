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
public class SubGraph {
    
    
    ArrayList<Point> node; //coordonnées des villes
    private Point center;
    
    public SubGraph(){
        node = new ArrayList<Point>();
        center = new Point();
    }

    /**
     * @return the center
     */
    public Point getCenter() {
        return center;
    }
    
    public void add(Point p){
        node.add(new Point(p.x, p.y));
    }

    void updateCenter() {
        double xTot = 0, yTot = 0;
        for(Point p : node){
            xTot += p.x;
            yTot += p.y;
        }
        center.setLocation(xTot/node.size(), yTot/node.size());
    }

    void clear() {
        node.clear();
    }
    
    public String toString(){
        String s = "center = " + center.x + ", " + center.y;
        for(Point p : node){
            s += "\n" + p.x + ", " + p.y;
        }
        return s;
    }
}
