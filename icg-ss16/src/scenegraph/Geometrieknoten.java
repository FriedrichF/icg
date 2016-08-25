package scenegraph;

import java.util.List;

import ogl.app.Vertex;
import ogl.vecmath.Matrix;

public class Geometrieknoten extends Knoten {

	Vertex[] vertices;
	
	public Geometrieknoten(String name, Matrix matrix, Vertex[] vertices) {
		super(name, matrix);
		this.vertices = vertices;
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
		
	}
	
	@Override
	public void accept(Traverser t) {
		t.visit(this);
	}

}
