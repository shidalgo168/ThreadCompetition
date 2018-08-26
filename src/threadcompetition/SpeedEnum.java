/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.util.Random;

/**
 *
 * @author Sergio Hidalgo
 */
public enum SpeedEnum {
    Slow(1) ,  Intermediate(2), Fast(3);
    
    private int value;
    private SpeedEnum(int pValue){
        this.value = pValue;
    }
    public int getValue(){
        return value;
    }
    
    public static SpeedEnum getRandomSpeed() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
}
