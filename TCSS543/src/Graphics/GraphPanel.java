/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package Graphics;

import Graph.Edge;
import Graph.GraphGenerator;
import Node.Node;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *Methods used for painting graph panel of project.
 * @author Anne French
 */
public class GraphPanel extends JComponent {
    private static final int WIDE = 900;
    private static final int HIGH = 600;
    private static final int MAX_GRAPHS = 100;
    
    private JFrame frame;
    private ControlPanel control;
    private GraphGenerator graphGenerator;
    private int numRuns;
    private double totalRuntime;
    private int totalColors;
    
    /**
     * Initializes graph panel of project and creates timer that controls painting 
     * of frame.
     * @param f
     */
    public GraphPanel(JFrame f) {
        numRuns = 0;
        System.out.println("Run Number,Vertices,Density,Average Edges,Average Colors,Average Runtime (ms)");
        control = new ControlPanel();
        frame = f;
        frame.add(control, BorderLayout.NORTH);
        frame.add(new JScrollPane(this), BorderLayout.CENTER);
        frame.setResizable(false);
        this.setOpaque(true);
        graphGenerator = new GraphGenerator(this);
        
        Random r = new Random();
        Timer timer = new Timer(r.nextInt(5000)+1000, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                repaint();
                control.repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        if(graphGenerator.getEdges()!=null && graphGenerator.getNodes()!=null) {
            for (Edge e : graphGenerator.getEdges()) {
                e.draw(g);
            }
            for (Node n : graphGenerator.getNodes()) {
                n.draw(g);
            }
        }
    }
    
    /**
     * Loops through generation of graphs up to max number of graphs
     * set in MAX_GRAPHS constant.
     */
    public void generateGraphs() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                control.numGraphs = 0;
                totalRuntime = 0;
                totalColors = 0;
                int numVs = control.numVertices;
                int totalEdges = 0;
                double density = control.density;
                while (control.numGraphs<MAX_GRAPHS) {
                    graphGenerator.generateGraph(WIDE, HIGH, numVs, density);
                    totalEdges+=graphGenerator.numEdges();
                }
                double avgEdges = totalEdges/(double)MAX_GRAPHS;
                double avgRuntime = totalRuntime/(double)MAX_GRAPHS;
                double avgColors = totalColors/(double)MAX_GRAPHS;
                numRuns++;
                System.out.println(String.format("%d,%d,%.2f,%.2f,%.2f,%f", numRuns, numVs, density, avgEdges, avgColors, avgRuntime));
            }
        });
        thread.start();
    }
    
    /**
     * Updates graph panel with current graph information.
     * @param runtime
     * @param numColors
     */
    public void updatePanel(double runtime, int numColors) {
  //      control.setAverageRuntime(runtime);
        totalRuntime+=runtime;
        totalColors+=numColors;
        control.incrementNumGraphs();
    }
    
    private class ControlPanel extends JToolBar {
        
        private static final int INIT_NODES = 10;
        private static final double INIT_DENSITY = 0.26;
    
        private Action start = new StartAction("Start");
        private int numGraphs = 0;
        public int numVertices = INIT_NODES;
        public double density = INIT_DENSITY;
        private long averageRuntime = 0;
        private Action startAction = new StartAction("Start");
        private JButton startButton = new JButton(startAction);
        private JSpinner densitySpin;
        private JSpinner vertSpin;
    
        public ControlPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);
        
            densitySpin = new JSpinner();
            densitySpin.setModel(new SpinnerNumberModel(INIT_DENSITY, INIT_DENSITY, 1.00, 0.01));
            densitySpin.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner spin = (JSpinner) e.getSource();
                    density = (double) spin.getValue();
                    GraphPanel.this.repaint();
                }
            });
            
            this.add(new JLabel("Density:"));
            this.add(densitySpin);
            
            vertSpin = new JSpinner();
            vertSpin.setModel(new SpinnerNumberModel(INIT_NODES, INIT_NODES, 500, 5));
            vertSpin.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner s = (JSpinner) e.getSource();
                    numVertices = (Integer) s.getValue();
                    GraphPanel.this.repaint();
                }
            });
        
            this.add(new JLabel("   Vertices:"));
            this.add(vertSpin);
            this.add(startButton);
        }
        
        public void incrementNumGraphs() {
            numGraphs++;   
            GraphPanel.this.repaint();
            if(numGraphs == MAX_GRAPHS) {
                this.startButton.setEnabled(true);
                this.densitySpin.setEnabled(true);
                this.vertSpin.setEnabled(true);
            }
        }
      
        public void disableControls() {
            this.startButton.setEnabled(false);
            this.densitySpin.setEnabled(false);
            this.vertSpin.setEnabled(false);
        }
    }
    
    private class StartAction extends AbstractAction {
    
        public StartAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            control.disableControls();
            generateGraphs();
        }
    }
}
