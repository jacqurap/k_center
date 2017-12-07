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
        
        
        Instance inst = new Instance();
        inst.C0 = 1;
        inst.Ch = 10;
        inst.k = 3;
        inst.node = new ArrayList<Point>();
        for (int i = 0; i<10; i++){
            inst.node.add(new Point(rand.nextInt(100),rand.nextInt(100)));
        }
        return inst;
    }
}
