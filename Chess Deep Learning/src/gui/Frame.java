package gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ComponentListener {
	private JPanel mainContent;
	private int width = 1280, height = 720;

	private ChessboardPanel chessboard;

	public Frame() {
		mainContent = new JPanel();
		chessboard = new ChessboardPanel();

		this.setTitle("Chess - KI");
		this.addComponentListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.add(mainContent);

		mainContent.add(chessboard);

//		mainContent.setPreferredSize(mainContent.getPreferredSize());
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		width = getContentPane().getWidth();
		height = getContentPane().getHeight();

		int size, posX;

		if (width <= height) {
			size = width;
			posX = 0;
		} else {
			size = height;
			posX = (width - height) / 2;
		}

		chessboard.setLocation(posX, 0);
		chessboard.setSize(size);
		chessboard.updatePositions();
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}
}
