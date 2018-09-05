/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
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
    private MainGUI movingMain;
    
    public Road(MainGUI pMovingMain, float pXPosition, float pYPosition, float pWidth, float pHeight){
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
        MoveThreadFigure currentMove = null;
        MoveThreadFigure prevMove = null;
        ThreadFigure currentCar = null;
        ThreadFigure prevCar = null;
        while(running){
            Iterator<MoveThreadFigure> index = this.figureList.iterator();
            while (index.hasNext()) {
                try {
                    prevMove = currentMove;
                    currentMove = index.next();
                    currentCar = currentMove.getMyObject();
                    if(direction == 1){
                        if(prevMove != null && prevMove != currentMove){
                            prevCar = prevMove.getMyObject();
                            if(currentCar.getyPosition()+currentCar.getHeight() >= prevCar.getHeight() ){
                                currentMove.setSleepTime(prevMove.getSleepTime());
                            }
                        }
                    }
                    else{
                        if(prevMove != null && prevMove != currentMove){
                            prevCar = prevMove.getMyObject();
                            if(prevCar.getyPosition()-prevCar.getHeight() <= currentCar.getHeight() ){
                                prevCar.setSpeed(currentCar.getSpeed());
                            }
                        }
                    }
                    if(!currentMove.getRunning()){
                        cleanFigures();
                    }
                    repaintJPanel();
                    Thread.sleep(currentMove.getSleepTime());
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ConcurrentModificationException ex) {
                    break;
                }
            }
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

    public void switchBarrier() {
        this.barrier = !this.barrier;
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
    
    public void reversePath() {
        if (this.direction == 1) {
            setDirection(-1);
            figureList.forEach((currentFigure) -> {
                currentFigure.getMyObject().setFinishLimit(0);
                currentFigure.setDirection(-1);
                switch (currentFigure.getMyObject().getSpeed().getValue()) {
                    case (1):
                        currentFigure.getMyObject().setImage(currentFigure.getMyObject().getSprite().get(3));
                        break;
                    case (2):
                        currentFigure.getMyObject().setImage(currentFigure.getMyObject().getSprite().get(4));
                        break;
                    case (3):
                        currentFigure.getMyObject().setImage(currentFigure.getMyObject().getSprite().get(5));
                        break;
                }
            });
        } else {
            setDirection(1);
            figureList.forEach((currentFigure) -> {
                currentFigure.getMyObject().setFinishLimit(height);
                currentFigure.setDirection(1);
                switch (currentFigure.getMyObject().getSpeed().getValue()) {
                    case (1):
                        currentFigure.getMyObject().setImage(currentFigure.getMyObject().getSprite().get(0));
                        break;
                    case (2):
                        currentFigure.getMyObject().setImage(currentFigure.getMyObject().getSprite().get(1));
                        break;
                    case (3):
                        currentFigure.getMyObject().setImage(currentFigure.getMyObject().getSprite().get(2));
                        break;
                }
            });
        }
    }
    
    public void draw(Graphics g){
        g.setColor(new Color(255,255,255));
        g.fillRect((int)xPosition, (int)yPosition,(int) width,(int) height);
        g.setColor(new Color(33,33,33));
        g.fillRect((int)xPosition+3, (int)yPosition+3,(int) width-4,(int) height-3);
        Iterator<MoveThreadFigure> index = this.figureList.iterator();
        while (index.hasNext()) {
            MoveThreadFigure figure = index.next(); // must be called before you can call i.remove()
            // Do something
            figure.getMyObject().draw(g);
        }
        if(barrier){
            g.setColor(new Color(255,233,18));
            g.fillRect((int)xPosition+3, (int)height/2-10,(int) width-3,10);
        }
    }
    
    public void cleanFigures(){
        Iterator<MoveThreadFigure> index = this.figureList.iterator();
        while (index.hasNext()) {
            MoveThreadFigure figure = index.next(); // must be called before you can call i.remove()
            // Do something
            if(figure.getMyObject()==null){
                index.remove();
            }
        }
    }
    
    
    
}