/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package Graph;

import Node.NodeAdj;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 *Setters and getters for location and drawing information for each edge.
 * @author Anne French
 */
public class Edge {
    private NodeAdj n1;
    private NodeAdj n2;
    private double randomUniform;
    private Random rand = new Random();

    /**
     * Sets the nodes adjacent to the edge and the random uniform number associated
     * with the edge value. The random uniform edge value is the deciding factor on
     * whether or not the edge gets drawn as it is compared to the density set on
     * the density slider. If the random uniform is greater than the density, it is 
     * not drawn.
     * @param n1
     * @param n2 
     */
    public Edge(NodeAdj n1, NodeAdj n2) {
        this.n1 = n1;
        this.n2 = n2;
        randomUniform = rand.nextDouble();
    }

    public void draw(Graphics g) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g.setColor(Color.darkGray);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
    
    public NodeAdj node1() {
        return n1;
    }
    
    public NodeAdj node2() {
        return n2;
    }
    
    /**
     * 
     * @return the random uniform value associated with the edge density
     */
    public double density() {
        return randomUniform;
    }
    
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Edge)) {
            return false;
        }

        Edge other = (Edge) obj;

        return n1.getLocation().x == other.n1.getLocation().x && n1.getLocation().y == other.n1.getLocation().y && n2.getLocation().x == other.n2.getLocation().x && n2.getLocation().y == other.n2.getLocation().y;
    }
}
