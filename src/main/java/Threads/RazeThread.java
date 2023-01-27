/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/**
 *
 * @author Dam
 */
public class RazeThread extends Thread {
    private JProgressBar runner;
    private int timeSleep;
    
    public RazeThread(JProgressBar runner) {
        this.runner = runner;
        this.timeSleep = Math.round((float)(Math.random() * 10 + 10));
    }
    
    @Override
    public void run() {
        int min = runner.getMinimum();
        int max = runner.getMaximum();
        
        for (int i = min; i < max; i++) {
            runner.setValue(runner.getValue() + 1);
            runner.repaint();
            try {
                Thread.sleep(timeSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(RazeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            timeSleep = Math.round((float)(Math.random() * 10 + 10));
        }
    }
    
}