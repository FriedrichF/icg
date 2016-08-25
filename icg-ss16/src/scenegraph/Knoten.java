package scenegraph;

import java.util.List;

import ogl.vecmath.Matrix;


public abstract class Knoten {
	
	private String name;
	private Matrix transformMatrix;
	
	public Knoten(String name, Matrix matrix){
		this.name = name;
		transformMatrix = matrix;
	}
	
	public String toString(){
		return name;
	}
	
	public Matrix getTransformMatrix(){
		return transformMatrix;
	}
	
	public void setTransformMatrix(Matrix matrix){
		transformMatrix = matrix;
	}
	
	public abstract List<Knoten> getChilds();
	public abstract void setChild(Knoten child);
	public abstract void draw(Matrix m);
	public abstract void accept(Traverser t);

}
