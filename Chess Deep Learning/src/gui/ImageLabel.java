package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ImageLabel extends JLabel {
	BufferedImage img, scaledImg;
	int size;

	public ImageLabel(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		size = this.getWidth();
		g.drawImage(img, 0, 0, size, size, this);

	}
}
