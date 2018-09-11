import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

//REGULAR BORING BLOCK
public class Block extends JComponent {
	private final SuperRainbowReef srb;
	private BufferedImage img;
	private String type;
	private int x;
	private int y;
	private Rectangle bounds;
	private int health = 10;
	SoundPlayer sp;
	
	public Block(BufferedImage img, SuperRainbowReef srb, String type, int x, int y) {
		this.img = img;
		this.srb = srb;
		this.type = type;
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(x, y, img.getWidth(), img.getHeight());
		this.sp = new SoundPlayer(2, "/Sound_block.wav");
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void CollisionWithPop() {
			//System.out.println("Block bounds: " + this.bounds);
			//System.out.println("pop bounds: " + this.srb.pop.getBounds());
			if(this.bounds.intersects(this.srb.pop.getBounds())) {
				//System.out.println("CollsionWithBLock");
				//this.pop.CollisionWithBlock();
				//this.pop.getBounds().setLocation(this.pop.getX(), this.pop.getY());
				this.srb.pop.UpdateCollision();
				sp.play();
				if(type == "Double") {
					if(health > 0) {
						health -= 5;
					}
					if(health == 0) {
						this.srb.blockList.remove(this.srb.block);
						this.srb.pop.updateScore(10);
					}
				} else if (type == "Life") {
					this.srb.pop.setLives(this.srb.pop.getLives() + 1);;
					this.srb.blockList.remove(this.srb.block);
				} else if (type == "none") {
					this.srb.blockList.remove(this.srb.block);
					this.srb.pop.updateScore(30);
				}
			}
	}
	
	public void drawImage(Graphics g) {
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
}
