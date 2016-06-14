package scenegraph;

import java.util.List;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Geometrieknoten extends Knoten {

	Vector vector;
	
	public Geometrieknoten(String name, Matrix matrix, Vector vector) {
		super(name, matrix);
		this.vector = vector;
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
		System.out.println(getTransformMatrix().mult(m).transformPoint(vector));
	}
	
	@Override
	public void accept(Traverser t) {
		t.visit(this);
	}

}
