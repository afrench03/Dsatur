/*
 * Copyright (C) 2017  Anne French frencha@uw.edu
 * 
 * This file is part of the Raft project.
 */
package TCSS543;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author annemacbook
 */
public class ControlPanel extends JToolBar {
    
    private int numVertices;
    private Action start = new StartAction("Start");
    
    public ControlPanel() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(Color.lightGray);
        
        JSpinner js = new JSpinner();
        js.setModel(new SpinnerNumberModel(10, 10, 500, 10));
        js.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner s = (JSpinner) e.getSource();
                numVertices = (Integer) s.getValue();
            }
        });
        
        this.add(new JLabel("Vertice Count:"));
        this.add(js);
        
        
    }
}
