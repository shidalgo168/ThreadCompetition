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
    private static final int NUMGAMEOBJECTS = 20;
    private boolean runningThread;
    
    private ThreadFigure[] gameObjectsArray = new ThreadFigure[NUMGAMEOBJECTS];
    private MoveThreadFigure[] moveObjectArray = new MoveThreadFigure[NUMGAMEOBJECTS];

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
        
        for (int i = 0; i < gameObjectsArray.length; i++) {
            
            gameObjectsArray[i] = new ThreadFigure(0,0, SpeedEnum.Slow, 710, 350);
            moveObjectArray[i] = new MoveThreadFigure(gameObjectsArray[i],
                                                    gameObjectsArray[i].getSpeed(), 
                                                    "Thread " + i,
                                                    this.runningThread,
                                                    1, false);
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

        movingPanel = new FiguresPanel(gameObjectsArray, DRAWING_WIDTH);
        frame.add(movingPanel);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        //START THREADS
        for (MoveThreadFigure myCurrentThread : moveObjectArray) {
            new Thread(myCurrentThread).start();
        } //end for
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
