package scenegraph;

import java.util.List;

import ogl.app.Vertex;
import ogl.vecmath.Matrix;

public class Geometrieknoten extends Knoten {

	public Vertex[] vertices;
	private int textureIndex;
	private int normalIndex;
	private String texturePath;
	private String normalPath;
	
	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public int getNormalIndex() {
		return normalIndex;
	}

	public void setNormalIndex(int normalIndex) {
		this.normalIndex = normalIndex;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}

	public String getNormalPath() {
		return normalPath;
	}

	public void setNormalPath(String normalPath) {
		this.normalPath = normalPath;
	}

	public Geometrieknoten(String name, Matrix matrix, Vertex[] vertices, String texturePath, String normalPath) {
		super(name, matrix);
		this.vertices = vertices;
		this.texturePath = texturePath;
		this.normalPath = normalPath;
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
