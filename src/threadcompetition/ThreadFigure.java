/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Sergio Hidalgo
 */
public class ThreadFigure extends Thread{
    private final int height = 45;
    private final int width = 25;
    private SpeedEnum speed;
    private float xPosition;
    private float yPosition;
    private float finishLimit;
    private float barrierLimit;
    private Image image;
    private ArrayList<Image> sprite;
    
    public ThreadFigure(float pXPos, float pYPos, SpeedEnum pSpeedType, float pFinishLimit, float pBarrierLimit) throws FileNotFoundException, IOException {   
        this.xPosition = pXPos;
        this.yPosition = pYPos;
        this.speed = pSpeedType;
        this.finishLimit = pFinishLimit;
        this.barrierLimit = pBarrierLimit;
        this.sprite = new ArrayList<>();
        
        this.sprite.add(ImageIO.read(getClass().getResource("/Assets/blue_car.jpg")));
        this.sprite.add(ImageIO.read(getClass().getResource("/Assets/red_car.jpg")));
        this.sprite.add(ImageIO.read(getClass().getResource("/Assets/green_car.jpg")));
        //Elias es un iota
        switch(speed.getValue()){
            case (1): 
                this.image = sprite.get(0);
                break;
            case (2): 
                this.image = sprite.get(1);
                break;
            case (3): 
                this.image = sprite.get(2);
                break;
        }
    }
    
    public SpeedEnum getSpeed() {
        return speed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public boolean move(int direction) {
        float limit;
        yPosition += (height * direction);
        
        if(direction == 1){
            limit = finishLimit - height;
            if(yPosition > limit){
                //La figura llego al final del camino
                return false;
            }
        }
        else {
            if(yPosition < finishLimit){
                //La figura llego al final del camino
                return false;
            }
        }
        return true;
    }
    
    public boolean move(int direction, boolean barrier){
        float limit;
        if(direction == 1){
            limit = barrierLimit - height;
            if(yPosition > limit){ //la figura se encuentra despues de la barrera
                yPosition += height;
                limit = finishLimit - height;
                if(yPosition > limit){
                    //La figura llego al final del camino
                    return false;
                }
            }
            else{
                if((yPosition+height) < limit){
                    yPosition += height;
                }
            }
        }
        else {
            limit = barrierLimit;
            if(yPosition < limit){ //la figura se encuentra despues de la barrera
                yPosition -= height;
                if(yPosition < finishLimit){
                    //La figura llego al final del camino
                    return false;
                }
            }
            else {
                if((yPosition-height) > limit){
                    yPosition -= height;
                }
            }
        }
        return true;
    }

    public void draw(Graphics g) {
        /*ImageObserver obvserver = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }*/
        g.drawImage(image,(int) xPosition,(int) yPosition, null);
        
    }
    
}


