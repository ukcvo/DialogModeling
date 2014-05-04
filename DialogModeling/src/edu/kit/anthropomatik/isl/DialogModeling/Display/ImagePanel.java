package edu.kit.anthropomatik.isl.DialogModeling.Display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage image; // the current image to display
	
	public ImagePanel() {
		super();
		this.image = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, null);

		this.repaint();
	}
}
