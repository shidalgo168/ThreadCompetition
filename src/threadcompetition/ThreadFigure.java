/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sergio Hidalgo
 */
public class ThreadFigure {
    private final int size = 50;
    private final int initialAngle = 0;
    private final int endOfAngle = 70;
    private SpeedEnum speed;
    private float xPosition;
    private float yPosition;
    private float finishLimit;
    private float barrierLimit;
    private Color figureColor;
    private boolean running;
    
    public ThreadFigure(float pXPos, float pYPos, SpeedEnum pSpeedType, float pFinishLimit, float pBarrierLimit) {   
        this.xPosition = pXPos;
        this.yPosition = pYPos;
        this.speed = pSpeedType;
        this.finishLimit = pFinishLimit;
        this.barrierLimit = pBarrierLimit;
        this.running = true;
        
        switch(speed.getValue()){
            case (1): 
                this.figureColor = new Color(232,28,24);
                break;
            case (2): 
                this.figureColor = new Color(255,253,29);
                break;
            case (3): 
                this.figureColor = new Color(36,232,64);
                break;
        }
    }

    public SpeedEnum getSpeed() {
        return speed;
    }

    public void setSpeed(SpeedEnum speed) {
        this.speed = speed;
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

    public float getFinishLimit() {
        return finishLimit;
    }

    public void setFinishLimit(float finishLimit) {
        this.finishLimit = finishLimit;
    }

    public void move(int direction) {
        float limit;
        yPosition += (size * direction);
        
        if(direction == 1){
            limit = finishLimit - size;
            if(yPosition > limit){
                //La figura llego al final del camino
                running = false;
            }
        }
        else {
            if(yPosition < finishLimit){
                //La figura llego al final del camino
                running = false;
            }
        }    
    }
    
    public void move(int direction, boolean barrier){
        float limit;
        if(direction == 1){
            limit = barrierLimit - size;
            if(yPosition > limit){ //la figura se encuentra despues de la barrera
                yPosition += size;
                limit = finishLimit - size;
                if(yPosition > limit){
                    //La figura llego al final del camino
                    running = false;
                }
            }
            else{
                if((yPosition+size) < limit){
                    yPosition += size;
                }
            }
        }
        else {
            limit = barrierLimit;
            if(yPosition < limit){ //la figura se encuentra despues de la barrera
                yPosition -= size;
                if(yPosition < finishLimit){
                    //La figura llego al final del camino
                    running = false;
                }
            }
            else {
                if((yPosition-size) > limit){
                    yPosition -= size;
                }
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(this.figureColor);
        switch(speed.getValue()){
            case (1): 
                g.fillRect((int) xPosition, (int) yPosition, size, size);
                break;
            case (2): 
                g.fillOval((int) xPosition, (int) yPosition, size, size);
                break;
            case (3): 
                g.fillArc((int) xPosition, (int) yPosition, size, size, initialAngle, endOfAngle);
                break;
        }
        
    }
    
}


