/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package Graph;

import Node.Node;
import Node.NodeAdj;
import Node.NodeSat;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 *This class contains the algorithm portion of this project corresponding to
 * Brelaz's Dsatur algorithm. Node saturation and adjacency information is 
 * stored in a priority queue so that the node of highest saturation can 
 * easily be popped off the front.
 * @author Anne French
 */
public class DSatur {
    List<Color> colors = new ArrayList<Color>();
    PriorityQueue<NodeAdj> adjQueue;
    PriorityQueue<NodeSat> satQueue;
    List<Node> ns;
    List<NodeAdj> nAdjs;
    List<NodeSat> nSats;
    List<Edge> e;
    Random rand = new Random();

    /**
     * Initializes variables for Dsatur algorithm.
     * @param nodes
     * @param nodeAdjacencies
     * @param nodeSaturations
     * @param edges 
     */
    public DSatur(List<Node> nodes, List<NodeAdj> nodeAdjacencies, List<NodeSat> nodeSaturations, List<Edge> edges) {
        ns = nodes;
        e = edges;
        
        adjQueue = new PriorityQueue<NodeAdj>(nodeAdjacencies.size(), Collections.reverseOrder());
        adjQueue.addAll(nodeAdjacencies);
        nAdjs = nodeAdjacencies;
        
        satQueue = new PriorityQueue<NodeSat>(nodeSaturations.size(), Collections.reverseOrder());
        satQueue.addAll(nodeSaturations);
        nSats = nodeSaturations;
    }
    
    /**
     * Entry point to begin Dsatur algorithm; colors all nodes in graph according 
     * to Brelaz's Dsatur algorithm. A node cannot be the same color as any node 
     * it is adjacent to. Nodes must be colored with the lowest-ranked (first-used) 
     * color that they are possible of being colored. A new color is generated 
     * when a node needs to be colored that is adjacent to (shares an edge with) 
     * nodes that are colored with all current possible colors.
     * @return the number of colors used to color nodes
     */
    public int colorNodes() {
        Node n = adjQueue.poll();
        List<Node> aNodes = getAdjacentNodes(n);
        generateNewColor();
        setNodeColor(n, 0, aNodes);
        while(nAdjs.size() > 1) {
            Node nextNode = getNextNode();
            colorNode(nextNode);
        }
        colorNode(nAdjs.get(0));
        return colors.size();
    }
    
    /**
     * Gets nodes adjacent to given node and determines what color to paint
     * given node. Nodes are colored using the lowest-ranked (first-used) color
     * that they are possible of being colored. 
     * @param n node to paint
     */
    private void colorNode(Node n) {
        List<Node> aNodes = getAdjacentNodes(n);
        int ind = determineNodeColorIndex(aNodes);
        setNodeColor(n, ind, aNodes);
    }
    
    /**
     * Gets all nodes with saturation equal to Node with highest
     * saturation.
     * @param highestSatNode first Node with highest saturation
     * @param saturation largest saturation of all Nodes
     * @return list of all Nodes with greatest saturation
     */
    private List<Node> getAllHighestSatNodes(NodeSat highestSatNode, int saturation) {
        List<Node> highSatNodes = new ArrayList<Node>();
        highSatNodes.add(highestSatNode);
        int i = 0;
        NodeSat nextSatNode = satQueue.poll();
        while (nextSatNode.getSaturation() == saturation && i < satQueue.size()) {
            highSatNodes.add(nextSatNode);
            nextSatNode = satQueue.poll();
        }
        if(nextSatNode.getSaturation() == saturation)
            highSatNodes.add(nextSatNode);
        return highSatNodes;
    }
    
    /**
     * Given list of Nodes with highest saturation (saturation of all
     * nodes is equal), returns Node that also has highest adjacency.
     * @param highSatNodes list of Nodes with highest saturation
     * @return Node with highest saturation and highest adjacency
     */
    private Node nodeWithGreatestAdjGreatestSat (List<Node> highSatNodes) {
        NodeAdj highNode = equalAdjacentNode(highSatNodes.get(0));
        for (int i=1; i<highSatNodes.size(); i++) {
            for(int j=0; j<nAdjs.size(); j++) {
                NodeAdj nextAdj = nAdjs.get(j);
                if(nextAdj.equals(highSatNodes.get(i))) {
                    if (nextAdj.getAdjacency() > highNode.getAdjacency())
                        highNode = nextAdj;
                    break;
                }
            }
        }
        return highNode;
    }
    
    private Node getNextNode() {
        Node nextNode;
        NodeSat first = satQueue.poll();
        int saturation = first.getSaturation();
        if(saturation == satQueue.peek().getSaturation()) {
            List<Node> highSatNodes = getAllHighestSatNodes(first, saturation);            
            nextNode = nodeWithGreatestAdjGreatestSat(highSatNodes);
        }
        else nextNode = first;
        return nextNode;
    }
    
    /**
     * Updates lists that hold node, node adjacency, and node 
     * saturation information so that all reflect the correct
     * color for Node n.
     * @param n the Node that needs a color update
     * @param colorIndex the index of the list of colors
     */
    private void updateNodeColor(Node n, int colorIndex) {
        int i = ns.indexOf(n);
        Color c = colors.get(colorIndex);
        ns.get(i).updateColor(c);
        for(Edge edge : e) {
            if(edge.node1().equals(n)) edge.node1().updateColor(c);
            if(edge.node2().equals(n)) edge.node2().updateColor(c);
        }
    }
    
    /**
     * Sets color of Node, removes Node from lists that will cause it to be
     * considered for coloring again, and updates adjacency of Nodes adjacent to 
     * just-colored Node.
     * @param n
     * @param colorIndex
     * @param adjNodes 
     */
    private void setNodeColor(Node n, int colorIndex, List<Node> adjNodes) {
        updateNodeColor(n, colorIndex);
        removeNode(n);
        
        for (Node node : adjNodes) {
            for (NodeSat satNode : nSats) {
                if (satNode.equals(node)) {
                    satNode.incrementSaturation();
                    break;
                }
            }
        }
    }
    
    /**
     * Gets all nodes adjacent to a given node.
     * @param n the node we want to find adjacent nodes for
     * @return list of nodes adjacent to node passed in
     */
    private List<Node> getAdjacentNodes(Node n) {
        List<Node> adjacentNodes = new ArrayList();
        e.forEach((edge) -> {
            if (edge.node1().equals(n))
                adjacentNodes.add(equalNode(edge.node2()));
            else if (edge.node2().equals(n))
                adjacentNodes.add(equalNode(edge.node1()));
        });
        return adjacentNodes;
    }
    
    /**
     * Generates new random node color when the number of nodes adjacent to a
     * given node exceed the current number of node colors offered.
     */
    private void generateNewColor() {
        int r, g, b;
        do {
            r = rand.nextInt(256);
            g = rand.nextInt(256);
            b = rand.nextInt(256);
        } while (r==255 && g==255 && b==255);
        colors.add(new Color(r,g,b));
    }
    
    /**
     * Determines what color to paint a node based on adjacency. A new color is 
     * generated when a node needs to be colored that is adjacent to (shares an 
     * edge with) nodes that are colored with all current possible colors.
     * @param aNodes list of nodes adjacent to node that must be colored
     * @return the index associated with the color the new node should be
     * painted
     */
    private int determineNodeColorIndex(List<Node> aNodes) {
        List<Color> adjacentColors = new ArrayList<Color>();
        
        //first determine colors of adjacent nodes
        for (Node n : aNodes) {
            if(!adjacentColors.contains(n.getColor()) && !n.getColor().equals(Color.BLACK)) 
                adjacentColors.add(n.getColor());
        }
        
        //if the list of colors of adjacent nodes is greater than the size of
        //the list of node colors, add a new colors to the list and return the
        //last index of the list of node colors.
        if(adjacentColors.size() >= colors.size()) {
            generateNewColor();
            return colors.size()-1;
        }
        
        //loop adjacent and all colors until a color from all colors is found
        //to not be contained in adjacent colors
        else {
            int colorIndex=0;
            for (int i=0; i<colors.size(); i++) {
                if(!adjacentColors.contains(colors.get(i)))
                    colorIndex = i;
            }
            return colorIndex;
        }
    }
    
    /**
     * Clears priority queue holding node saturation information. Should
     * be called after the adjacency of a node is checked.
     */
    private void resetSaturationQueue() {
        satQueue.clear();
        satQueue.addAll(nSats);
    }
    
    /**
     * Clears priority queue holding node adjacency information. Should
     * be called after the adjacency of a node is checked.
     */
    private void resetAdjacencyQueue() {
        adjQueue.clear();
        adjQueue.addAll(nAdjs);
    }
    
    /**
     * Removes colored node from adjacency and saturation
     * lists so that it won't be considered for coloring again.
     * @param n Node to be removed
     */
    private void removeNode(Node n) {
        nAdjs.remove(equalAdjacentNode(n));
        nSats.remove(equalSaturationNode(n));
        resetSaturationQueue();
        resetAdjacencyQueue();
    }
    
    /**
     * Checks priority queue of adjacent nodes for a given node.
     * @param n node to look for
     * @return node from adjacency priority queue matching node given
     */
    private NodeAdj equalAdjacentNode(Node n) {
        int ind = nAdjs.indexOf(n);
        return nAdjs.get(ind);
    }
    
    /**
     * Checks priority queue of node saturation info for a given node.
     * @param n node to look for
     * @return node from saturation priority queue matching node given
     */
    private NodeSat equalSaturationNode(Node n) {
        int ind = nSats.indexOf(n);
        return nSats.get(ind);
    }
    
    /**
     * Check list of all nodes in graph for a given node.
     * @param n node to check for
     * @return node corresponding to node given
     */
    private Node equalNode(Node n) {
        int ind = ns.indexOf(n);
        return ns.get(ind);
    }
}
