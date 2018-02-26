/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package TCSS543;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Anne French and Ted Callow
 */
public class Edge {
    private Node n1;
    private Node n2;

    public Edge(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public void draw(Graphics g) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g.setColor(Color.darkGray);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
}
