import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class StarFish extends JComponent {
	private final SuperRainbowReef srb;
	private static BufferedImage img;
	private Katch k;
	private int x;
	private int y;
	private Rectangle bounds;
	private int rotation = 0;
	private int width;
	private int height;
    private int lives = 4;
    private int dx = -1;
    private int dy = -1;
	private boolean gameRunning = false;
	private int score = 0;
	private boolean collision = false;
	private int spawnPointX;
	private int spawnPointY;
	SoundPlayer sp;
	
	public StarFish(SuperRainbowReef srb, Katch k, BufferedImage img, int x, int y) {
		this.srb = srb;
		this.k = k;
		StarFish.img = img;
		this.x = x; // location
		this.y = y; // location
		this.width = StarFish.img.getWidth();
		this.height = StarFish.img.getHeight();
		this.bounds = new Rectangle(x, y, StarFish.img.getWidth(), StarFish.img.getHeight());
		this.spawnPointX = x;
		this.spawnPointY = y;
		this.sp = new SoundPlayer(2, "/Sound_lost.wav");
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getDX() {
		return this.dx;
	}
	
	public int getDY() {
		return this.dy;
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setDX( int dx ) {
		this.dx = dx;
	}
	
	public void setDY( int dy ) {
		this.dy = dy;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public String toStringScore() {
		return "SCORES: " + this.score;
	}
	
	public void UpdateCollision() {
		this.collision = true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.update();
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(rotation), img.getWidth() / 2, img.getHeight() / 2);
		//g2.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
		g2.drawImage(img, at, null);
	}
	
	public void update() {
		this.updateGameStatus();
		this.CollisionWithBlock();
		this.move();
		//this.repaint();
	}
	
	private void updateGameStatus() {
		if (this.k.getGameStatus() && this.k.getFire()) {
			this.gameRunning = this.k.getGameStatus();
		}
	}
	
	public void CollisionWithBlock() {
		if(collision) {
			dy *= -1;
		}
		collision = false;
	}
	
	public void updateScore(int score) {
		this.score += score;
	}
	
	//////////////
	// StarFish Movement
	/*
	 * Legit to make things easy just setXY of StarFish right about Katch
	 * to code for gravity and then to aim just position Katch accordingly
	 * before spawning.
	 * 
	 */
	
	private void move() {
		if(gameRunning) {
			//System.out.println("StarFish MOVE!");
			if(this.bounds.intersects(this.k.getBoundsLeft())) {
				System.out.println("Katch left collision!");
				dy *= -1;
				if(dx > 0) {
					dx *= -1;
				}
			} else if (this.bounds.intersects(this.k.getBoundsRight())) {
				System.out.println("Katch Right collision!");
				dy *= -1;
				if(dx < 0) {
					dx *= -1;
				}
			}
			
			x += dx;
			y += dy;
			//System.out.println("Move x: " + x);
			//System.out.println("Move y: " + y);
			if(x < 25) {
				dx *= -1;
			}
			if(y > 700) {
				dy *= -1;
			}
			if(y < 0) {
				dy *= -1;
			}
			if(x > 720) {
				dx *= -1;
			}
			this.bounds.setLocation(x,y);
			checkBorder();
			rotation += 1;
		}
	}
	
	
	public void checkBorder() {
		if(this.y > this.k.getY() + 100) {
			sp.play();
			score -= score / 10;
			lives--;
			respawn();
		}
	}
	
	public void respawn() {
		if(lives < 0) {
			this.srb.updateLose();
			this.k.respawn();
			this.x = spawnPointX;
			this.y = spawnPointY;
			this.bounds.setLocation(x, y);
			gameRunning = false;
			rotation = 0;
		} else {
			this.k.respawn();
			this.x = spawnPointX;
			this.y = spawnPointY;
			this.bounds.setLocation(x, y);
			gameRunning = false;
			rotation = 0;
		}
	}
	
	public void drawImage(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.update();
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(rotation), img.getWidth() / 2, img.getHeight() / 2);
		g2.drawImage(img, at, null);
	}
}
