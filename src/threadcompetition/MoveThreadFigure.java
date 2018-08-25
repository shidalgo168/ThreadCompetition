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
    private String name;
    private int direction;
    private boolean barrier;
    
    public MoveThreadFigure(ThreadFigure pObj, SpeedEnum pSpeed, String pName, boolean pRunning, int pDirection, boolean pBarrier){
        this.myObject = pObj;
        this.running = pRunning;
        this.name = pName;
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
        while(running){
            myObject.move(direction);
            
            try {
                //System.out.println("Thread name: " + this.name);
                Thread.sleep(this.sleepTime);
                if(barrier){
                    myObject.move(direction, barrier);
                }
                else{
                    myObject.move(direction);
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(MoveThreadFigure.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }//end while
    }
    
    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
}
