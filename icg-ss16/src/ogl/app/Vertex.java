package ogl.app;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;

//Auxiliary class to represent a single vertex.
public class Vertex {
	public final Vector position;
	public final Color color;
	public final Vector normal;
	public final Vector texture;

	public Vertex(Vector p, Color c, Vector normal, Vector texture) {
		position = p;
		color = c;
		this.normal = normal;
		this.texture = texture;
	}
}
