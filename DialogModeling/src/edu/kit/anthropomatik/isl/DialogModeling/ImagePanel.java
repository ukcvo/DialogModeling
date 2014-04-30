package edu.kit.anthropomatik.isl.DialogModeling;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	
	public ImagePanel() {
		super();
		image = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	public void setImage(BufferedImage img) {
		this.image = img;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
}
