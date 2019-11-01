# Dsatur
Graphical version of Brélaz's Dsatur algorithm described in https://dl.acm.org/citation.cfm?id=359101.

Brélaz's Dsatur algorithm provides a means of coloring vertices of a graph based on the graph's structure and degrees. The algorithm is called Dsatur because it uses degree saturation to color the vertices of the graph. The algorithm is described as follows:

Let G be a simple graph and C a partial coloration of G vertices. We define the saturation degree of a vertex as the number of different colors to which it is adjacent (colored vertices).

1. Arrange the vertices by decreasing order of degrees.
2. Color a vertex of maximal degree with color 1.
3. Choose a vertex with a maximal saturation degree. If there is an equality, choose any vertex of maximal degree in the uncolored subgraph.
4. Color the chosen vertex with the least possible (lowest numbered) color.
5. If all the vertices are colored, stop. Otherwise, return to 3. 

This application is designed such that the experiment described in section 3.1 of Brélaz's "New Methods to Color the Vertices of a Graph" can be performed, but uses 100 random graphs in each experiment. Each graph is generated randomly as follows: for each possible edge, a random number is generated from a uniform distribution between 0 and 1; if the number is less than the desired density for the experiment, the edge is included in the graph. This application allows the user to toggle density and the number of vertices for experiments.
