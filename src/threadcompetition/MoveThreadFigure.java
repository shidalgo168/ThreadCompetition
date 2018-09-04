/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.util.concurrent.ThreadFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio Hidalgo
 */
public class MoveThreadFigure implements Runnable{
    private boolean running;
    private ThreadFigure myObject;
    private int sleepTime;
    private int direction;
    private boolean barrier;
    
    public MoveThreadFigure(ThreadFigure pObj, SpeedEnum pSpeed, boolean pRunning, int pDirection, boolean pBarrier){
        this.myObject = pObj;
        this.running = pRunning;
        this.direction = pDirection;
        this.barrier = pBarrier;
        
        switch(pSpeed.getValue()){
            case (1): 
                this.sleepTime = 700;
                break;
            case (2): 
                this.sleepTime = 450;
                break;
            case (3): 
                this.sleepTime = 200;
                break;
        }
        
    }

    @Override
    public void run() {
        while(this.running){
            this.myObject.move(this.direction);
            
            try {
                //System.out.println("Thread name: " + this.name);
                Thread.sleep(this.sleepTime);
                
                if(this.barrier){
                    if(!this.myObject.move(this.direction, this.barrier)){
                        this.running = false;
                    }
                }
                else{
                    if(!this.myObject.move(this.direction)){
                        this.running = false;
                    }
                    
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(MoveThreadFigure.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(MoveThreadFigure.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }//end while
    }
    
    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
    
    public synchronized boolean getRunning() {
        return this.running;
    }

    public ThreadFigure getMyObject() {
        return myObject;
    }

    public void setMyObject(ThreadFigure myObject) {
        this.myObject = myObject;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isBarrier() {
        return barrier;
    }

    public void setBarrier(boolean barrier) {
        this.barrier = barrier;
    }
    
    
}
