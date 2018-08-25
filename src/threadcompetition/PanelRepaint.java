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
    private InitInterfaceAndThreads movingSquaresMain;
    private int sleepTimePaint;


    public PanelRepaint(InitInterfaceAndThreads movingSquares, int sleepTimePaint, boolean running) {

        this.movingSquaresMain = movingSquares;
        this.sleepTimePaint = sleepTimePaint;
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            repaintJPanel();
            sleep();
        }
    }

    private void repaintJPanel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                movingSquaresMain.repaintMovingPanel();
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
