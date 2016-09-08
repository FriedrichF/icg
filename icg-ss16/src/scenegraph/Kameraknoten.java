package scenegraph;

import java.util.List;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Kameraknoten extends Knoten {

	Kamera camera;

	public Kameraknoten(String name, Matrix matrix) {
		super(name, matrix);
		this.camera = new Kamera(0,0,0);
	}

	public Kamera getCamera() {
		return camera;
	}

	public void setCamera(Kamera camera) {
		this.camera = camera;
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
