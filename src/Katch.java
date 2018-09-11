import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

/**
*
* @author anthony-pc
*/
public class Katch extends JComponent implements Observer {
	
	private final SuperRainbowReef srb;
	private int x;
	private int y;
	private int vx;
	private int vy;
	private short angle;
	private static BufferedImage img;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean FirePressed;
    private boolean fire = false;
    private boolean shoot = true;
    private Rectangle bounds;
    private int spawnPointX;
    private int spawnPointY;
    private boolean gameRunning = false;
    SoundPlayer sp;
    private Rectangle bounds_left;
    private Rectangle bounds_right;
    
    public Katch(SuperRainbowReef srb, BufferedImage img, int x, int y) {
    	this.srb = srb;
    	Katch.img = img;
    	this.x = x;
    	this.y = y;
    	//this.vx = vx;
    	//this.vy = vy;
    	//this.bounds = new Rectangle(x, y, Katch.img.getWidth(), Katch.img.getHeight());
    	this.bounds_left = new Rectangle(x, y, Katch.img.getWidth() /2, Katch.img.getHeight());
    	this.bounds_right = new Rectangle(x + Katch.img.getWidth()/2, y, Katch.img.getWidth() /2, Katch.img.getHeight());
    	System.out.println("boundsL: " + bounds_left);
    	System.out.println("boundsR: " + bounds_right);
    	System.out.println("Img width: " + img.getWidth());
    	this.spawnPointX = x;
    	this.spawnPointY = y;
    	this.sp = new SoundPlayer(2, "/Sound_katch.wav");
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
    
    public void setVX(int vx) {
    	this.vx = vx;
    }
    
    public void setVY(int vy) {
    	this.vy = vy;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    public BufferedImage getImg() {
    	return Katch.img;
    }
    
    public boolean getGameStatus() {
    	return gameRunning;
    }
    
    public Rectangle getBounds() { 
    	return this.bounds;
    }
    
    public Rectangle getBoundsLeft() {
    	return this.bounds_left;
    }
    
    public Rectangle getBoundsRight() {
    	return this.bounds_right;
    }
    
    public void toggleRightPressed() {
    	this.RightPressed = true;
    }
    
    public void toggleLeftPressed() {
    	this.LeftPressed = true;
    }
    
    public void toggleFirePressed() {
    	this.FirePressed = true;
    }
    
    public void unToggleRightPressed() {
    	this.RightPressed = false;
    }
    
    public void unToggleLeftPressed() {
    	this.LeftPressed = false;
    }
    
    public void unToggleFirePressed() {
    	this.FirePressed = false;
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    }
    
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated met
		
		if (this.FirePressed) {
			this.fire = true;
			this.gameRunning = true;
		}
		
		if (this.RightPressed && gameRunning) {
			this.moveRight();
			sp.play();
		}
		if (this.LeftPressed && gameRunning) {
			this.moveLeft();
			sp.play();
		}
		if (this.RightPressed && !gameRunning) {
			this.moveRight();
			this.srb.pop.setX(this.x + 20);
			sp.play();
		}
		if (this.LeftPressed && !gameRunning) {
			this.moveLeft();
			this.srb.pop.setX(this.x + 20);
			sp.play();
		}
		
		this.repaint();
	}
	
	public void moveRight() {
		x += 10;
		checkBorder();
		//this.bounds.setLocation(x,y);
		this.bounds_left.setLocation(x,y);
		this.bounds_right.setLocation(x + Katch.img.getWidth()/2,y);
		//System.out.println("boundsL: " + bounds_left);
    	//System.out.println("boundsR: " + bounds_right);
	}
	
	public void moveLeft() {
		x -= 10;
		checkBorder();
		//this.bounds.setLocation(x,y);
		this.bounds_left.setLocation(x,y);
		this.bounds_right.setLocation(x + Katch.img.getWidth()/2,y);
		//System.out.println("boundsL: " + bounds_left);
    	//System.out.println("boundsR: " + bounds_right);
	}
	
	public void checkBorder() {
		if(x < 0) {
			x = 30;
		}
		if(x >= 700) {
			x = 700;
		}
	}
	
	public void shootDisabled() {
		shoot = false;
	}
	
	public void shootEnabled() {
		shoot = true;
	}
	
	public boolean getFire() {
		return fire;
	}
	
	public void respawn() {
		this.x = this.spawnPointX;
		this.y = this.spawnPointY;
		//this.bounds.setLocation(x,y);
		this.bounds_left.setLocation(x,y);
		this.bounds_right.setLocation(x + Katch.img.getWidth() /2 ,y);
		System.out.println("boundsL: " + bounds_left);
    	System.out.println("boundsR: " + bounds_right);
		fire = false;
		gameRunning = false;
	}
	
	public void skipLevel() {
		this.srb.setCurrentLevel(this.srb.getCurrentLevel() + 1);
		this.srb.updateLoadLevel();
	}
	
	public void drawImage(Graphics g) {
		Graphics2D graphic2D = (Graphics2D) g;
		graphic2D.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
}
