package scenegraph;

import java.util.List;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Kameraknoten extends Knoten {
	
	public Vector getUp() {
		return up;
	}

	public void setUp(Vector up) {
		this.up = up;
	}

	public Vector getCenter() {
		return center;
	}

	public void setCenter(Vector center) {
		this.center = center;
	}

	private Vector up;
	private Vector center;

	public Kameraknoten(String name, Matrix matrix, Vector center, Vector up) {
		super(name, matrix);
		this.center = center;
		this.up = up;
	}

	@Override
	public List<Knoten> getChilds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChild(Knoten child) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Matrix m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accept(Traverser t) {
		t.visit(this);
	}

}
