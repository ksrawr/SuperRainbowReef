import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class BlockLife extends JComponent {
	private final SuperRainbowReef srb;
	private static BufferedImage img;
	private int x;
	private int y;
	private Rectangle bounds;
	
	public BlockLife(SuperRainbowReef srb, BufferedImage img, int x, int y) {
		this.srb = srb;
		BlockLife.img = img;
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public void drawImage(Graphics g) {
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
}
