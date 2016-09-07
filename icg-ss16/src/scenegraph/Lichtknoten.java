package scenegraph;

import java.util.List;

import ogl.vecmath.Matrix;

public class Lichtknoten extends Knoten {

	public Lichtknoten(String name, Matrix matrix) {
		super(name, matrix);
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
