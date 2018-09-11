import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

// SSCOREPANEL
public class ScoreStock extends JComponent {
	private final SuperRainbowReef srb;
	private static BufferedImage img;
	
	public ScoreStock(SuperRainbowReef srb) {//, BufferedImage img) {
		this.srb = srb;
		//ScoreStock.img = img;
	}
	
	public void drawImage(Graphics g) {
		/*
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, SuperRainbowReef.screen_width/2, 700, img.getWidth(), img.getHeight(), null);
		*/
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.ORANGE);
		g2.drawRect(SuperRainbowReef.screen_width/4 + SuperRainbowReef.screen_width/2 - 60, 695, 250, 30);
		g2.fillRect(SuperRainbowReef.screen_width/4 + SuperRainbowReef.screen_width/2 - 60, 695, 250, 30);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Helvetica", Font.BOLD, 28));
		g2.drawString(this.srb.pop.toStringScore() , SuperRainbowReef.screen_width/4 + SuperRainbowReef.screen_width/2 - 40, 720);
	}
}
