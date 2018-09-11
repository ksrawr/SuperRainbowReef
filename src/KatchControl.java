/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

/**
 *
 * @author anthony-pc
 */
public class KatchControl extends Observable implements KeyListener {

    private Katch k;
    private final int right;
    private final int left;
    private final int fire;
    private final int nextLevel;

    public KatchControl(Katch k, int left, int right, int fire, int nextLevel) {
        this.k = k;
        this.right = right;
        this.left = left;
        this.fire = fire;
        this.nextLevel = nextLevel;
    }

    public void keyTyped(KeyEvent ke) {

    }

    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == left) {
            this.k.toggleLeftPressed();
            //System.out.println(keyPressed);
        }
        if (keyPressed == right) {
            this.k.toggleRightPressed();
            //System.out.println(keyPressed);
        }
        if (keyPressed == fire) {
        	this.k.toggleFirePressed();
        	//System.out.println(keyPressed);
        }
        if (keyPressed == nextLevel) {
        	this.k.skipLevel();
        }
    }

    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == left) {
            this.k.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.k.unToggleRightPressed();
        }
        if (keyReleased == fire ) {
        	this.k.unToggleFirePressed();
        }
    }
}
