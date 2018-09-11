import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class Squid extends JComponent {
	private final SuperRainbowReef srb;
	private BufferedImage img;
	private String type;
	private int x;
	private int y;
	private Rectangle bounds;
	private int count = 0;
	SoundPlayer sp;
	
	public Squid(SuperRainbowReef srb, BufferedImage img, String type, int x, int y) {
		this.srb = srb;
		this.img = img;
		this.type = type;
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(x, y, img.getWidth(), img.getHeight());
		this.sp = new SoundPlayer(2, "/Sound_bigleg.wav");
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void CollisionWithPop() {
		count = this.srb.squidList.size();
		if(this.bounds.intersects(this.srb.pop.getBounds())) {
			//System.out.println("Collision with Squid");
			this.srb.squidList.remove(this.srb.squid);
			sp.play();
			count--;
			//System.out.println("squid ct: " + count);
			this.srb.pop.updateScore(60);
			if(count > 0) {
				this.srb.pop.UpdateCollision();
			} else if (count == 0 && this.srb.getCurrentLevel() != 3){
				//this.srb.updateWin();
				this.srb.setCurrentLevel(this.srb.getCurrentLevel() + 1);
				this.srb.updateLoadLevel();
			} else if (count == 0 && this.srb.getCurrentLevel() == 3){
				this.srb.pop.respawn();
				this.srb.updateWin();
			}
			
		}
	}
	
	public void drawImage(Graphics g) {
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
}
