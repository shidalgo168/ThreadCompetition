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

    public ArrayList<Road> getRoadObjectArray() {
        return roadObjectArray;
    }

    public void setRoadObjectArray(ArrayList<Road> roadObjectArray) {
        this.roadObjectArray = roadObjectArray;
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
    
    public void paramGenerator(int figureQty, int speedSelection) throws IOException{
        int figureFlag = 0;
        int freeRoadPos = 0;
        
        
        while(figureFlag<figureQty){
            freeRoadPos = selectRoad(this.getRoadObjectArray());
            float roadXPos = this.getRoadObjectArray().get(freeRoadPos).getxPosition();
            float roadYPos = this.getRoadObjectArray().get(freeRoadPos).getyPosition();
            float roadHeight = this.getRoadObjectArray().get(freeRoadPos).getHeight();
            
            ThreadFigure tf = new ThreadFigure(roadXPos, roadYPos, SpeedEnum.Slow, roadHeight,roadHeight/2);
            //TODO add to road
            //this.getRoadObjectArray().get(freeRoadPos).getFigureList().;
            
        }
    }

    
    public int selectRoad(ArrayList<Road> roads){
        Road freeRoad = roads.get(0);
        int freeRoadPos = 0;
        for (Road road : roads) {
            if(road.getFigureList().size()<freeRoad.getFigureList().size()){
                freeRoad = road;
                freeRoadPos=roads.indexOf(freeRoad);
            }
        }
        return freeRoadPos;
    }

}
