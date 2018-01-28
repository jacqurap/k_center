/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k_center;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author raf
 */
public class Generator {
    
    Random rand;
    private int seed;
    
    public Generator(){
        rand = new Random();
        seed = rand.nextInt();
        if(main.DISPLAY)
            System.out.println("seed = " + seed);
        rand = new Random(seed);
    }
    
    public Instance newInstance(){
        return newInstance(100, 10, 1, 100, 5);
    }
    
    public Instance newInstance(int n, int k, int C0, int Ch, int nbGroups){
        if(n<nbGroups)
            return null;
        Instance inst = new Instance();
        inst.C0 = C0;
        inst.Ch = Ch;
        inst.k = k;
        inst.node = new ArrayList<Point>();
        int citiesSize = 1000/nbGroups -1;
        
        for (int i = 0; i<nbGroups; i++){
            inst.node.add(new Point(rand.nextInt(1000-citiesSize)+citiesSize/2,rand.nextInt(1000-citiesSize)+citiesSize/2));
        }
        if(main.DISPLAY_INSTANCE){
            System.out.println(inst);
            System.out.println("-----------------------------");
        }
        for (int i = nbGroups; i<n; i++){
            Point group = inst.node.get(rand.nextInt(nbGroups));
            inst.node.add(new Point((group.x + rand.nextInt(citiesSize) - citiesSize/2),group.y + rand.nextInt(citiesSize) - citiesSize/2));
        }
        return inst;
    }
}
