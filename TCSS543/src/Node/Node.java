/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package Node;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Setters and getters for location and drawing information for each node.
 * @author Anne French
 */
public class Node {
    
    private static final int RADIUS = 10;
    private Point p;
    private Color color;
    private Rectangle b = new Rectangle();
    
    public Node(Point p, Color color) {
        this.p = p;
        this.color = color;
        setBoundary(b);
    }
    
    private void setBoundary(Rectangle b) {
        b.setBounds(p.x - RADIUS, p.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(b.x, b.y, b.width, b.height);
    }
    
    public Point getLocation() {
        return p;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void updateColor(Color color) {
        this.color = color;
    }
    
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Node)) {
            return false;
        }

        Node other = (Node) obj;

        return p.getLocation().x == other.getLocation().x && p.getLocation().y == other.getLocation().y;
    }
}
