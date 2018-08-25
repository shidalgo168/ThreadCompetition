/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.lang.Math.round;
import java.util.ArrayList;
import javax.swing.JFrame;
/**
 *
 * @author Sergio Hidalgo
 */



public class InitInterfaceAndThreads implements Runnable {
    private static final int DRAWING_WIDTH = 700;
    private static final int ROADS_COUNT = 5;
    private static final int ROAD_WIDTH = 30;
    private boolean runningThread;
    
    private ArrayList<Road> roadObjectArray = new ArrayList<>();

    private JFrame frame;
    private FiguresPanel movingPanel;
    private PanelRepaint panelRepaint;
    private int sleepThreadTime;
    private int sleepTimePaint;

    //constructor
    public InitInterfaceAndThreads() throws IOException {
        this.sleepThreadTime = 10;
        this.sleepTimePaint = 20;
        this.runningThread = true;
        
        for (int i = 0; i < ROADS_COUNT; i++) {
           roadObjectArray.add( new Road(ROAD_WIDTH*i, 10, ROAD_WIDTH, DRAWING_WIDTH));
        }//end for
    }

    @Override
    public void run() {
        frame = new JFrame();
        frame.setTitle("Moving Figures with different threads!");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });

        movingPanel = new FiguresPanel(DRAWING_WIDTH);
        movingPanel.updateFigures(roadObjectArray);
        frame.add(movingPanel);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        //START THREADS
        roadObjectArray.forEach((currentRoad) -> {
            currentRoad.getFigureList().forEach((moveThread) -> {
                new Thread(moveThread).start();
            });
        }); //end for
            
        panelRepaint = new PanelRepaint(this, this.sleepTimePaint, this.runningThread);
        new Thread(panelRepaint).start();
    }

    private void exitProcedure() {
        this.runningThread = false;
        panelRepaint.setRunning(false);
        frame.dispose();
        System.exit(0);
    }

    public void repaintMovingPanel() {
        movingPanel.repaint();
    }

    
}
