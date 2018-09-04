/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sergio Hidalgo
 */
public class Road implements Runnable{
    private float xPosition;
    private float yPosition;
    private float width;
    private float height;
    private int direction;
    private boolean barrier;
    private ArrayList<MoveThreadFigure> figureList;
    private boolean running;
    private InitInterfaceAndThreads movingMain;
    
    public Road(InitInterfaceAndThreads pMovingMain, float pXPosition, float pYPosition, float pWidth, float pHeight){
        this.movingMain = pMovingMain;
        this.xPosition = pXPosition;
        this.yPosition = pYPosition;
        this.width = pWidth;
        this.height = pHeight;
        this.direction = 1;
        this.barrier = false;
        this.figureList = new ArrayList<>();
        this.running = false;
    }
    
    @Override
    public void run (){
        this.running = true;
        while(running){
            figureList.forEach((currentMove) -> {
                    try {
                        Thread.sleep(currentMove.getSleepTime());
                        if(!currentMove.getRunning() && currentMove.getMyObject() != null){
                            new Thread(currentMove).start();
                            currentMove.setRunning(true);
                            
                        }
                        else {
                            //TODO CLEAN FUNCTION
                            //cleanAllMoves(roadObjectArray);
                        }
                        repaintJPanel();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InitInterfaceAndThreads.class.getName()).log(Level.SEVERE, null, ex);
                    }
            });
        }
    }
    
    private void repaintJPanel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                movingMain.repaintMovingPanel();
                
            }
        });
    }
    
    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void changeBarrierStatus(){
        this.barrier = !this.barrier;
    }

    public boolean isBarrier() {
        return barrier;
    }

    public void setBarrier(boolean barrier) {
        this.barrier = barrier;
    }

    public ArrayList<MoveThreadFigure> getFigureList() {
        return figureList;
    }

    public void setFigureList(ArrayList<MoveThreadFigure> figureList) {
        this.figureList = figureList;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    
    
    
    
    public void reversePath(){
        if(this.direction == 1){
            setDirection(-1);
            figureList.forEach((currentFigure) -> {
                currentFigure.getMyObject().setFinishLimit(0);
            });
        }
        else {
            setDirection(1);
            figureList.forEach((currentFigure) -> {
                currentFigure.getMyObject().setFinishLimit(height);
            });
        }
    }
    
    public void draw(Graphics g){
        g.setColor(new Color(255,255,255));
        g.fillRect((int)xPosition, (int)yPosition,(int) width,(int) height);
        g.setColor(new Color(33,33,33));
        g.fillRect((int)xPosition+3, (int)yPosition+3,(int) width-4,(int) height-3);
        figureList.forEach((MoveThreadFigure currentFigure) -> {
               
                currentFigure.getMyObject().draw(g);
            });
        
    }
    
    public void cleanFigures(){
        for(MoveThreadFigure figure : this.figureList){
            if(figure.getMyObject() == null){ //Como se que termino 
                this.figureList.remove(figure);
            }
        }
    }
    
    
    
}
