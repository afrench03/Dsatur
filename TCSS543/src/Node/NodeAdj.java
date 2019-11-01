/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package Node;

import java.awt.Color;
import java.awt.Point;

/**
 * Captures information for each node, including location, color, and adjacency.
 * Adjacency of a node is equal to the number of edges connected to it. 
 * @author Anne French
 */
public class NodeAdj extends Node implements Comparable<NodeAdj> {

    private int adjacencyDegree;
        
    public NodeAdj(Point p, Color c) {
        super(p, c);
    }
    
    public NodeAdj(Point p, Color c, int a) {
        super(p, c);
        adjacencyDegree = a;
    }
    
    public NodeAdj(Node n) {
        super(n.getLocation(), n.getColor());
    }
     
    /**
     * Increments the adjacency of a node; should only be used when
     * initializing nodes.
     */
    public void incrementAdjacency() {
        adjacencyDegree++;
    }
    
    public int getAdjacency() {
        return adjacencyDegree;
    }
    
    @Override
    public int compareTo(NodeAdj other) {
        if(this.equals(other))
            return 0;
        else if(adjacencyDegree > other.getAdjacency())
            return 1;
        else
            return -1;
    }
}
