package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{
	private BufferedImage img;
	
	public ImagePanel(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("ImagePanel Error: " + e);
			System.exit(1);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
