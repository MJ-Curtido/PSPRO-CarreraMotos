/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Threads;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import views.InitialPanel;

/**
 *
 * @author Dam
 */
public class RazeThread extends Thread {

    private InitialPanel panel;
    private JProgressBar runner;
    private int timeSleep;
    private int vuelta;
    private final int NUM_VUELTAS = 2;
    private ArrayList<RazeThread> listaHilos;
    private JLabel lblVueltas;
    private JLabel lblGanador;
    private JLabel lblAccidente;
    private Boolean detenido;
    private Random rnd;

    public RazeThread(JProgressBar runner, ArrayList<RazeThread> listaHilos, JLabel lblVueltas, JLabel lblGanador, JLabel lblAccidente, InitialPanel panel) {
        this.runner = runner;
        this.timeSleep = Math.round((float) (Math.random() * 70 + 10));
        this.vuelta = 0;
        this.listaHilos = listaHilos;
        this.lblGanador = lblGanador;
        this.lblVueltas = lblVueltas;
        this.lblAccidente = lblAccidente;
        this.panel = panel;
        this.detenido = false;
        this.rnd = new Random();
    }

    public void setDetenidoTrue() {
        this.detenido = true;
    }

    public synchronized void setDetenidoFalse() {
        this.detenido = false;

        notifyAll();
    }

    public Boolean getDetenido() {
        return this.detenido;
    }

    @Override
    public void run() {
        iniciar();
    }

    public synchronized void iniciar() {
        final int MIN_RUNNER = runner.getMinimum();
        final int MAX_RUNNER = runner.getMaximum();

        for (vuelta = 0; vuelta < NUM_VUELTAS && !panel.getTerminar(); vuelta++) {
            runner.setValue(runner.getMinimum());
            runner.repaint();

            this.lblVueltas.setText(vuelta + "/" + NUM_VUELTAS);

            for (int i = MIN_RUNNER; i < MAX_RUNNER && !panel.getTerminar(); i++) {
                runner.setValue(runner.getValue() + 1);
                runner.repaint();
                try {
                    Thread.sleep(timeSleep);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RazeThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                timeSleep = Math.round((float) (Math.random() * 70 + 10));
                System.out.println(this.getDetenido());
                
                if (rnd.nextInt(100) == 1) {
                    lblAccidente.setVisible(true);
                    setDetenidoTrue();
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RazeThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setDetenidoFalse();
                    lblAccidente.setVisible(false);
                }
                
                if (this.getDetenido()) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RazeThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        vuelta = NUM_VUELTAS;
        this.lblVueltas.setText(vuelta + "/" + NUM_VUELTAS);

        if (!panel.getTerminar()) {
            this.listaHilos.add(this);

            if (this.listaHilos.indexOf(this) == 0) {
                this.lblGanador.setText("Ganador! :)");
            } else {
                this.lblGanador.setText("Perdedor :(");
            }
        } else {
            panel.setTerminar(false);
        }
    }
}
