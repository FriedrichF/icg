package scenegraph;

import java.util.ArrayList;
import java.util.List;

import ogl.vecmath.Matrix;

public class Gruppenknoten extends Knoten {
	
	private List<Knoten> childs = new ArrayList<Knoten>();

	public Gruppenknoten(String name, Matrix matrix) {
		super(name, matrix);
	}
	
	public List<Knoten> getChilds(){
		return childs;
	}

	@Override
	public void setChild(Knoten child) {
		childs.add(child);
	}

	@Override
	public void draw(Matrix m) {
		System.out.println(this);
		for (Knoten child : childs) {
			child.draw(getTransformMatrix().mult(m));
		}
	}
	
	@Override
	public void accept(Traverser t) {
		t.visit(this);
	}

}
