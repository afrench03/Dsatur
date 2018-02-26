/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package TCSS543;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

/**
 *
 * @author Anne French and Ted Callow
 */
public class Node {
    private Point p;
    private int r;
    private Color color;
    private Rectangle b = new Rectangle();
    
    public Node(Point p, int r, Color color) {
        this.p = p;
        this.r = r;
        this.color = color;
        setBoundary(b);
    }
    
    private void setBoundary(Rectangle b) {
        b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
    }
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(b.x, b.y, b.width, b.height);
    }
    
    public Point getLocation() {
        return p;
    }
    
    public void updateColor(Color color) {
        this.color = color;
    }
}
