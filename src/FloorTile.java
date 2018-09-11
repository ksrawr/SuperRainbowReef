import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class FloorTile extends JComponent {
	private final SuperRainbowReef srb;
	private static BufferedImage img;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public FloorTile(SuperRainbowReef srb) {
		this.srb = srb;
		width = SuperRainbowReef.screen_width;
		height = SuperRainbowReef.screen_height;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		//System.out.println("Floor: x: " + this.x + " y: " + this.y );
	}
	
	public void setImg(BufferedImage img) {
		FloorTile.img = img;
	}
	
	public void drawImage(Graphics g) {
		g.drawImage(img, this.x, this.y, FloorTile.img.getWidth(), FloorTile.img.getHeight(), null);
	}
}
