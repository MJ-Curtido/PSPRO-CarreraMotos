/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Threads;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Dam
 */
public class RazeThread extends Thread {

    private JProgressBar runner;
    private int timeSleep;
    private int vuelta;
    private final int NUM_VUELTAS = 5;
    private ArrayList<RazeThread> listaHilos;
    private JLabel lblVueltas;
    private JLabel lblGanador;

    public RazeThread(JProgressBar runner, ArrayList<RazeThread> listaHilos,  JLabel lblVueltas, JLabel lblGanador) {
        this.runner = runner;
        this.timeSleep = Math.round((float) (Math.random() * 70 + 10));
        this.vuelta = 0;
        this.listaHilos = listaHilos;
        this.lblGanador = lblGanador;
        this.lblVueltas = lblVueltas;
    }

    @Override
    public void run() {
        final int MIN_RUNNER = runner.getMinimum();
        final int MAX_RUNNER = runner.getMaximum();

        for (vuelta = 0; vuelta <= NUM_VUELTAS; vuelta++) {
            runner.setValue(runner.getMinimum());
            runner.repaint();
            
            this.lblVueltas.setText(vuelta + "/" + NUM_VUELTAS);
            
            for (int i = MIN_RUNNER; i < MAX_RUNNER; i++) {
                runner.setValue(runner.getValue() + 1);
                runner.repaint();
                try {
                    Thread.sleep(timeSleep);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RazeThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                timeSleep = Math.round((float) (Math.random() * 70 + 10));
            }
        }
        
        this.listaHilos.add(this);
        
        if (this.listaHilos.indexOf(this) == 0) {
            this.lblGanador.setText("Ganador! :)");
        }
        else {
            this.lblGanador.setText("Perdedor :(");
        }
    }
}
