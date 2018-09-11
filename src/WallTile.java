import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class WallTile extends JComponent {
	private static BufferedImage img;
	private int x;
	private int y;
	
	public WallTile(BufferedImage img, int x, int y) {
		WallTile.img = img;
		this.x = x;
		this.y = y;
	}
	
	/*
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		//System.out.println("Wall: x: " + this.x + " y: " + this.y );
	}
	*/
	
	public void drawImage(Graphics g) {
		g.drawImage(img, this.x, this.y, WallTile.img.getWidth(), WallTile.img.getHeight(), null);
	}
}
