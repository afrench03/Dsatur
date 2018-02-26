/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package TCSS543;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Anne French and Ted Callow
 */
public class GraphPanel extends JComponent {
    private static final int WIDE = 640;
    private static final int HIGH = 480;
    private static final int RADIUS = 35;
    
    private List<Node> nodes = new ArrayList<Node>();
    private List<Edge> edges = new ArrayList<Edge>();
    
    public GraphPanel() {
        this.setOpaque(true);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : edges) {
            e.draw(g);
        }
        for (Node n : nodes) {
            n.draw(g);
        }
    }
}
