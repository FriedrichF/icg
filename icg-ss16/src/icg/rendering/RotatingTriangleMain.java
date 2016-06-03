package icg.rendering;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import ogl.vecmath.*;

public class RotatingTriangleMain extends JFrame{
	private static final long serialVersionUID = 1270967605637096622L;
	/**
	 * the image into which the triangle shall be rendered
	 */
	private BufferedImage imgToRender;

	/**
	 * current width and height of the image / window
	 */
	private int width = 200, height = 200;

	/**
	 * the triangle instance to be rendered
	 */
	private RotatingTriangleBase toRender = makePyramide(0.5f, 0.5f, 0.5f);

	/**
	 * a timer
	 */
	private static long lastStep = System.currentTimeMillis();

	public static void main(String[] args) {
		// create an instance of this class
		RotatingTriangleMain instance = new RotatingTriangleMain();

		long stepTime = 0L;
		// initialize timer
		resetTimer();
		// loop until window is closed
		while(true){
			// update window/image bounds
			instance.updateWindow();
			// update triangle vertices
			instance.simulate( stepTime = getElapsedMillis() );
			// store time of last r
			// render the triangle
			instance.render();

			// wait a (milli)second...
			sleep(Math.max(0, 16 - stepTime));
		}
	}

	/**
	 * initialize program
	 */
	public RotatingTriangleMain(){
		// initialize window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setVisible(true);

		// if no object to be rendered was set before, create a new triangle
		if (toRender == null)
			toRender = new RotatingTriangle(
					new Vertex(MyMathFactory.vector(-0.5f, -0.5f, 0.5f), MyMathFactory.color(1, 0, 0)), 
					new Vertex(MyMathFactory.vector( 0.5f, -0.5f, 0.5f), MyMathFactory.color(0, 1, 0)),
					new Vertex(MyMathFactory.vector( 0.5f,  0.5f, 0.5f), MyMathFactory.color(0, 0, 1))
					);		
		
		// set the rotation axis
		toRender.setRotationAxis(MyMathFactory.vector(1, 0, -1));
	}

	/**
	 * updates the image size
	 */
	public void updateWindow(){
		// check for new window size
		width = getWidth();
		height = getHeight();

		// if either there is no image, or its dimension does not match the current window size, create a new image
		if (imgToRender == null || imgToRender.getWidth() != width || imgToRender.getHeight() != height)
			imgToRender = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = imgToRender.createGraphics();
		g.setColor(new Color(RotatingTriangle.defaultBackgroundColor.toAwtColor()));
		g.fillRect(0, 0, width, height);
	}


	/**
	 * updates the vertices of the rendered triangle
	 * @param elapsedMilliseconds the time in milliseconds that has passed since the last call to this method
	 */
	public void simulate(long elapsedMilliseconds){
		// get matrix from the triangle
		Matrix currMat = toRender.getTransform();

		// use default implementation (i.e. rotate by 90 degrees per second) if retrieved transform is null
		currMat = currMat.mult(MyMathFactory.rotationMatrix( toRender.getRotationAxis().normalize(), 90f * elapsedMilliseconds / 1000f));

		// apply the transformation to the triangle
		toRender.setTransform(currMat);
	}



	// create new rotating triangle instance with multiple triangles (compare to rotating cube)
	public RotatingTriangle makeCube(float cwidth, float cheight, float cdepth) {
		return new RotatingTriangle(new Vertex[]{
				new Vertex(MyMathFactory.vector(-cwidth, -cheight, cdepth), MyMathFactory.color(1, 0, 0)), 
				new Vertex(MyMathFactory.vector( cwidth, -cheight, cdepth), MyMathFactory.color(0, 1, 0)),
				new Vertex(MyMathFactory.vector( cwidth,  cheight, cdepth), MyMathFactory.color(0, 0, 1)),
				new Vertex(MyMathFactory.vector(-cwidth,  cheight, cdepth), MyMathFactory.color(1, 0, 1)),

				new Vertex(MyMathFactory.vector( cwidth, -cheight, -cdepth), MyMathFactory.color(1, 0, 0)), 
				new Vertex(MyMathFactory.vector(-cwidth, -cheight, -cdepth), MyMathFactory.color(0, 1, 0)),
				new Vertex(MyMathFactory.vector(-cwidth,  cheight, -cdepth), MyMathFactory.color(0, 0, 1)),
				new Vertex(MyMathFactory.vector( cwidth,  cheight, -cdepth), MyMathFactory.color(1, 0, 1))
		}, new int[]{
				//front
				0,1,2,  2,3,0, 
				//back
				4,5,6, 	6,7,4,
				// right
				1,4,7,	7,2,1,
				// top
				3,2,7,	7,6,3,
				//left
				5,0,3,	3,6,5,
				//bottom
				5,4,1,	1,0,5
		});
	}

	public RotatingTriangle makePyramide(float cwidth, float cheight, float cdepth) {
		return new RotatingTriangle(new Vertex[]{
				new Vertex(MyMathFactory.vector(-cwidth, -cheight, cdepth), MyMathFactory.color(1, 0, 0)), 
				new Vertex(MyMathFactory.vector( 0, cheight, 0), MyMathFactory.color(0, 1, 0)),
				new Vertex(MyMathFactory.vector( cwidth,  -cheight, cdepth), MyMathFactory.color(0, 0, 1)),
				new Vertex(MyMathFactory.vector(cwidth,  -cheight, -cdepth), MyMathFactory.color(1, 0, 1)),
				new Vertex(MyMathFactory.vector(-cwidth,  -cheight, -cdepth), MyMathFactory.color(1, 0, 1))
		}, new int[]{
				//front
				0,1,2,
				//right
				2,1,3,
				// back
				3,1,4,
				// left
				4,1,0
		});
	}

	/**
	 * render the triangle
	 */
	public void render(){
		// call the fill image function of the triangle
		toRender.fillImage(imgToRender);
		// repaint this window
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		// directly draw the image, if available
		if (imgToRender != null)
			g.drawImage( imgToRender, 0, 0, null);
	}

	/********************
	 * Helper Functions *
	 ********************/

	/**
	 * reset the timer
	 */
	private static void resetTimer(){
		lastStep = System.currentTimeMillis();
	}

	/**
	 * @return the time in milliseconds since the method was invoked the last time
	 */
	private static long getElapsedMillis(){
		long retVal = System.currentTimeMillis() - lastStep;
		lastStep += retVal;
		return retVal;		
	}

	/**
	 * sleep for some time (simply avoids annoying exception handling)
	 * @param time the time in milliseconds to sleep
	 */
	private static void sleep(long time){
		try {  Thread.sleep(time); } catch (InterruptedException e) { e.printStackTrace(); }
	}
}


