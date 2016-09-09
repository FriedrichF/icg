package obj;

import java.util.ArrayList;
import java.util.List;

import ogl.vecmath.Vector;

public class Model {
	private List<Vector> vertices = new ArrayList<Vector>();
	private List<Vector> normals = new ArrayList<Vector>();
	private List<Vector> textures = new ArrayList<Vector>();
	public List<Vector> getTextures() {
		return textures;
	}

	public void setTextures(List<Vector> textures) {
		this.textures = textures;
	}

	private List<Face> faces = new ArrayList<Face>();

	public Model() {
	}

	public List<Vector> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vector> vertices) {
		this.vertices = vertices;
	}

	public List<Vector> getNormals() {
		return normals;
	}

	public void setNormals(List<Vector> normals) {
		this.normals = normals;
	}

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

}
