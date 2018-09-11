import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LifeStock extends JComponent {
	private final SuperRainbowReef srb;
	private static BufferedImage img;
	
	public LifeStock(SuperRainbowReef srb, BufferedImage img) {
		this.srb = srb;
		LifeStock.img = img;
	}
	
	public void drawImage(Graphics g) {
		/////////////
		// Draw Katch Lives
		int space = 30;
		int xHealthOffset = 20; 

		for (int i = 0; i < this.srb.pop.getLives(); i++) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(img, xHealthOffset + space, 700, img.getWidth(), img.getHeight(), null);
			xHealthOffset += space;
		}
		
	}
}
