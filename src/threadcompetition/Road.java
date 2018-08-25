/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Sergio Hidalgo
 */
public class Road {
    private float xPosition;
    private float yPosition;
    private float width;
    private float height;
    private int direction;
    private boolean barrier;
    private ArrayList<MoveThreadFigure> figureList;
    
    public Road(float pXPosition, float pYPosition, float pWidth, float pHeight){
        this.xPosition = pXPosition;
        this.yPosition = pYPosition;
        this.width = pWidth;
        this.height = pHeight;
        this.direction = 1;
        this.barrier = false;
        this.figureList = new ArrayList<>();
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
    
}
