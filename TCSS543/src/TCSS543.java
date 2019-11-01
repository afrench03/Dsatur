/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */


import Graphics.GraphPanel;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Anne French 
 */
public class TCSS543 {

    /**
     * Entry point of project. Initializes main window/frame.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("Brelaz's DSatur");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphPanel gp = new GraphPanel(f);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }
    
}
