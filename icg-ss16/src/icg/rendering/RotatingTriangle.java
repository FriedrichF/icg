package icg.rendering;

import icg.math.RGBColorImpl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ogl.vecmath.*;
import static ogl.vecmathimp.FactoryDefault.vecmath;

public class RotatingTriangle extends RotatingTriangleBase{
	/**
	 * the default background color
	 */
	public static Color defaultBackgroundColor =  MyMathFactory.color(0, 0, 0);
	/**
	 * the default line color
	 */
	public static Color defaultLineColor = new RGBColorImpl(1,1,1);
	/**
	 * the vertices of this triangle
	 */
	protected ArrayList<Vector> positions = new ArrayList<Vector>();
	protected ArrayList<Color> colors = new ArrayList<Color>();
	private ArrayList<LineSpec> lineIndexList = new ArrayList<LineSpec>();
	
	protected Vector rotationAxis = MyMathFactory.vector(0, 0, -1);
	
	/**
	 * the view matrix
	 */
	private Matrix view = MyMathFactory.translationMatrix(MyMathFactory.vector(0, 0, -3));

	/**
	 * constructor setting the vertices of this triangle, line color is set to defaultLineColor
	 * @param p1 vertex 1
	 * @param p2 vertex 2
	 * @param p3 vertex 3
	 */
	public RotatingTriangle(Vector p1, Vector p2, Vector p3) {
		this(new Vertex(p1, defaultLineColor), 
				new Vertex(p2, defaultLineColor), 
				new Vertex(p3, defaultLineColor)
		);
	}

	/**
	 * constructor setting the vertices of this triangle
	 * @param p1 vertex 1
	 * @param p2 vertex 2
	 * @param p3 vertex 3
	 */
	public RotatingTriangle(Vertex p1, Vertex p2, Vertex p3) {
		this(new Vertex[]{p1, p2, p3}, new int[]{0, 1, 2});
	}

	/**
	 * multiple rotating triangles
	 * @param vs the vertices of the triangles
	 * @param indices the indices of the triangles. Every three consecutive indices reference the corners (in vs) of a triangle
	 */
	public RotatingTriangle(Vertex[] vs, int[] indices) {
		for(int i=0; i < indices.length; i +=3){
			addTriangle(vs[indices[i]], vs[indices[i+1]], vs[indices[i+2]]);
		}
	}
	
	/**
	 * create a internal triangle representation from the given three vertices
	 * @param p1 vertex 1
	 * @param p2 vertex 2
	 * @param p3 vertex 3
	 */
	private void addTriangle(Vertex p1, Vertex p2, Vertex p3){
		int i1 = positions.size(), i2 = i1+1, i3 = i1+2;
		// add positions
		positions.add(p1.position);
		positions.add(p2.position);
		positions.add(p3.position);
		// add lines
		colors.add(p1.color);
		colors.add(p2.color);
		colors.add(p3.color);
		// add indices
		lineIndexList.add(new LineSpec(i1, i2));
		lineIndexList.add(new LineSpec(i2, i3));
		lineIndexList.add(new LineSpec(i3, i1));
	}

	/**
	 * provide a rotation axis
	 * @return a rotation axis
	 */
	public Vector getRotationAxis() {
		return rotationAxis;
	}
	
	public void setRotationAxis(Vector axis){
		rotationAxis = axis;
	}
	
	
	
	public void setViewDistance(float zPos){
		view = MyMathFactory.translationMatrix(MyMathFactory.vector(0, 0, -zPos));
	}

	/**
	 * fills the given image
	 * @param img the image to be filled	  
	 */
	public void fillImage(BufferedImage img) {
		Vector scaleAndCenter = MyMathFactory.vector(img.getWidth()/2, img.getHeight()/2, 0);
		// matrices
		Matrix projection = vecmath.perspectiveMatrix(60f, ((float)img.getWidth())/((float)img.getHeight()), 0.1f, 100f);		
		Matrix modelViewProjection = projection.mult(view.mult(getTransform()));
		// lines to be created
		ArrayList<Line> lines = new ArrayList<Line>(); 
		for (LineSpec t : lineIndexList){
			// apply model view projection matrix to points of line
			Vector tp1 = modelViewProjection.transformPoint(positions.get(t.p1));
			Vector tp2 = modelViewProjection.transformPoint(positions.get(t.p2));
			// perspective divide and scale to image size plus centering (to match pixel values)
			tp1 = tp1.mult(1f/tp1.z()).mult(scaleAndCenter).add(scaleAndCenter);
			tp2 = tp2.mult(1f/tp2.z()).mult(scaleAndCenter).add(scaleAndCenter);
			// add line to list of processed lines
			lines.add(new LineImpl(new Vertex(tp1, colors.get(t.p1)), new Vertex(tp2, colors.get(t.p2))));
		}
		// render lines to image
		for (Line l : lines) 
			l.fillImage(img);
	}

	// internal helper class to represent a tuple of indices
	class LineSpec{
		int p1, p2;

		public LineSpec(int p1, int p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	}
}
