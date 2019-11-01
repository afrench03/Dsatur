/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package Node;

import java.awt.Color;
import java.awt.Point;

/**
 *Implements saturation information of a node.
 * @author Anne French
 */
public class NodeSat extends Node implements Comparable<NodeSat> {

    private int saturationDegree;
    
    public NodeSat(Point p, Color color) {
        super(p, color);
    }
    
    public NodeSat(Point p, Color color, int saturation) {
        super(p, color);
        saturationDegree = saturation;
    }
    
    public NodeSat(Node n) {
        super(n.getLocation(), n.getColor());
    }
   
    public void incrementSaturation() {
        saturationDegree++;
    }
    
    public int getSaturation() {
        return saturationDegree;
    }

    @Override
    public int compareTo(NodeSat other) {
        if(this.equals(other))
            return 0;
        else if(saturationDegree > other.getSaturation())
            return 1;
        else
            return -1;
    }
}
