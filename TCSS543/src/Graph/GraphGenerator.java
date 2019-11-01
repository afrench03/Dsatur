/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package Graph;

import Node.Node;
import Node.NodeAdj;
import Node.NodeSat;
import Graphics.GraphPanel;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *Generates nodes and edges of graphs.
 * @author Anne French
 */
public class GraphGenerator {

    private static final Color INIT_COLOR = Color.BLACK;
    private static final Random RAND = new Random();
    
    private GraphPanel gp;
    private CopyOnWriteArrayList<Node> nodes;
    private List<NodeAdj> nodeAdjs;
    private List<NodeSat> nodeSats;
    private CopyOnWriteArrayList<Edge> edges;
    
    public GraphGenerator(GraphPanel graphPanel) {
        gp = graphPanel;
    }
    
    /**
     * Entry point to generate and display new graph.
     * @param frameWidth
     * @param frameHeight
     * @param numVertices number of graph nodes
     * @param density proportion of graph coverage; density of 0 means there are no 
     * edges between any nodes, density of 1 means there is an edge going from each
     * node to every other node
     */
    public void generateGraph(int frameWidth, int frameHeight, int numVertices, double density) {
        int numColors;
        long startTime;
        long endTime;
        nodes = new CopyOnWriteArrayList<Node>();
        nodeAdjs = new ArrayList<NodeAdj>();
        nodeSats = new ArrayList<NodeSat>();
        edges = new CopyOnWriteArrayList<Edge>();
        createNodes(frameWidth, frameHeight, numVertices);
        addEdges(density);
        DSatur dSatur = new DSatur(nodes, nodeAdjs, nodeSats, edges);
        startTime = System.nanoTime();
        numColors = dSatur.colorNodes();
        endTime = System.nanoTime();
        double runtime = (endTime-startTime)/(double)1000000;
        gp.updatePanel(runtime, numColors);
    }
    
    /**
     * Get list of all graph nodes for drawing graph.
     * @return list of all nodes for drawing graph
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Get list of all graph edges for drawing graph.
     * @return list of all edges in graph
     */
    public List<Edge> getEdges() {
        return edges;
    }
    
    /**
     * Fetches total number of edges in the graph.
     * @return total number of edges in the graph
     */
    public int numEdges() {
        return edges.size();
    }
    
    private void createNodes(int frameWidth, int frameHeight, int numVertices) {
        for(int i=0; i<numVertices; i++) {
            Point p = new Point(RAND.nextInt(frameWidth-10)+10, RAND.nextInt(frameHeight-10)+10);
            Node n = new Node(p, INIT_COLOR);
            nodes.add(n);
            nodeSats.add(new NodeSat(n));
            nodeAdjs.add(new NodeAdj(n));
        }
    }
    
    /**
     * Sets random uniform value of each edge, compares that value to the density 
     * set in the density slider, and only keeps edges whose random uniform values 
     * are less than the density value set in the density slider on the panel.
     * @param density The density set in the density slider. If the randomly
     * generated density of an edge is greater than an edge's density, that
     * edge is not drawn.
     */
    private void addEdges(double density) {
        //Have to loop through all nodes and set each edge in order to set
        //random uniform edge value.
        for (NodeAdj n1 : nodeAdjs) {
            for (NodeAdj n2 : nodeAdjs) {
                if(!n1.equals(n2)) {
                    Edge reverseEdge = new Edge(n2, n1);
                    if(!edges.contains(reverseEdge)) 
                        edges.add(new Edge(n1, n2));
                }
            }
        }
        //Remove edges with random uniform density value greater than density set
        //in slider.
        for (int i=0; i<edges.size(); i++) {
            if (edges.get(i).density() > density)
                edges.remove(i);
        }
        //Set adjacency of nodes connected by remaining edges.
        for (int j = 0; j<nodeAdjs.size(); j++) {
            for (Edge e : edges){
                if(e.node1().equals(nodeAdjs.get(j)) || e.node2().equals(nodeAdjs.get(j)))
                    nodeAdjs.get(j).incrementAdjacency();
            }
        }
    }
}
