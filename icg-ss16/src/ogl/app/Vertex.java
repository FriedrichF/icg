package ogl.app;

import ogl.vecmath.Color;
import ogl.vecmath.Vector;

//Auxiliary class to represent a single vertex.
public class Vertex {
	public final Vector position;
	public final Color color;
	public final Vector normal;

	public Vertex(Vector p, Color c, Vector normal) {
		position = p;
		color = c;
		this.normal = normal;
	}
}
