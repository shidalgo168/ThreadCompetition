/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;
import javax.swing.SwingUtilities;
/**
 *
 * @author Sergio Hidalgo
 */


public class PanelRepaint implements Runnable {

    private boolean running;
    private MainGUI movingMain;
    private int sleepTimePaint;


    public PanelRepaint(MainGUI pMovingMain, int sleepTimePaint, boolean running) {

        this.movingMain = pMovingMain;
        this.sleepTimePaint = sleepTimePaint;
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            sleep();
            movingMain.startAllThreads();
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

    private void sleep() {
        try {
            Thread.sleep(this.sleepTimePaint);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
    
}
