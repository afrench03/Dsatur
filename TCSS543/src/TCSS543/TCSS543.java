/*
 * Copyright (C) 2018  Anne French frencha@uw.edu
 * 
 * This file is part of the TCSS 543 Brelaz's Dsatur project.
 */
package TCSS543;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Anne French and Ted Callow
 */
public class TCSS543 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("GraphPanel");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphPanel gp = new GraphPanel();
               // f.add(gp.control, BorderLayout.NORTH);
               // f.add(new JScrollPane(gp), BorderLayout.CENTER);
               // f.getRootPane().setDefaultButton(gp.control.defaultButton);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }
    
}
