package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.batik.swing.JSVGCanvas;

@SuppressWarnings("serial")
public class SvgRenderer extends JPanel {

	private JSVGCanvas svg;

	public SvgRenderer() {
		svg = new JSVGCanvas();
		svg.setURI("");
	}

	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame();
		f.setSize(1000, 1000);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSVGCanvas svg = new JSVGCanvas();
		svg.setURI("file:\\D:\\Dokumente\\eclipse-workspace\\Chess Deep Learning\\src\\gui\\svg\\Queen.svg");
//		svg.setMinimumSize(new Dimension(1000, 1000));

		File imgFile = new File("");
	
		BufferedImage img = ImageIO.read(imgFile);
		
		f.add(new JLabel(new ImageIcon(img)));

		f.setVisible(true);
	}

}
