package ogl.cube;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

import java.util.ArrayList;
import java.util.List;

import icg.math.FactoryImpl;
import ogl.app.App;
import ogl.app.Input;
import ogl.app.OpenGLApp;
import ogl.app.Vertex;
import ogl.vecmath.Color;
import ogl.vecmath.Vector;
import opengl.Shader;
import scenegraph.Entity;
import scenegraph.Geometrieknoten;
import scenegraph.Gruppenknoten;
import scenegraph.Knoten;
import scenegraph.Rotor;
import scenegraph.Traverser;

public class Scene implements App {
	static public void main(String[] args) {
		new OpenGLApp("Scene", new Scene()).start();
	}
	
	Knoten knotenRoot;

	@Override
	public void init() {
		shader = Shader.getInstance();
		knotenRoot = new Gruppenknoten("Root", FactoryImpl.vecmath.translationMatrix(0, 0, 0));
		Knoten knotenA = new Gruppenknoten("Root", FactoryImpl.vecmath.translationMatrix(0, 1, -1.5f));
		Knoten knotenCube = new Geometrieknoten("Cube", FactoryImpl.vecmath.translationMatrix(0, 0, 0), cubeVertices);
		Knoten knotenCube2 = new Geometrieknoten("Cube", FactoryImpl.vecmath.translationMatrix(0, 0, 0), cubeVertices);

		knotenRoot.setChild(knotenCube);
		knotenRoot.setChild(knotenA);
		knotenA.setChild(knotenCube2);

		Entity rotor = new Rotor("Rotation", knotenRoot, vec(0, 1, 1), 300);
		entities.add(rotor);

		knotenRoot.accept(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cg2.cube.App#simulate(float, cg2.cube.Input)
	 */
	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(GLFW_KEY_R)) {
			for (Entity entity : entities) {
				entity.simulate(elapsed, input);
			}
		}
		shader.clearLists();
		knotenRoot.accept(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cg2.cube.App#display(int, int, javax.media.opengl.GL2ES2)
	 */
	@Override
	public void display(int width, int height) {
		shader.display(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ogl.app.App#cleanUp()
	 */
	public void cleanUp() {
		Shader.getInstance().cleanUp();
	}

	/************************
	 * Variable definitions *
	 ************************/

	private Shader shader;

	private List<Entity> entities = new ArrayList<Entity>();
	Traverser t = new Traverser();

	// Width, depth and height of the cube divided by 2.
	float w2 = 0.5f;
	float h2 = 0.5f;
	float d2 = 0.5f;

	// Make construction of vertices easy on the eyes.
	private Vertex v(Vector p, Color c, Vector n) {
		return new Vertex(p, c, n);
	}

	// Make construction of vectors easy on the eyes.
	private Vector vec(float x, float y, float z) {
		return FactoryImpl.vecmath.vector(x, y, z);
	}

	// Make construction of colors easy on the eyes.
	private Color col(float r, float g, float b) {
		return FactoryImpl.vecmath.color(r, g, b);
	}

	//
	// 6 ------- 7
	// / | / |
	// 3 ------- 2 |
	// | | | |
	// | 5 -----|- 4
	// | / | /
	// 0 ------- 1
	//

	// The positions of the cube vertices.
	private Vector[] p = { vec(-w2, -h2, d2), vec(w2, -h2, d2), vec(w2, h2, d2), vec(-w2, h2, d2), vec(w2, -h2, -d2),
			vec(-w2, -h2, -d2), vec(-w2, h2, -d2), vec(w2, h2, -d2) };

	// The colors of the cube vertices.
	// private Color[] c = { col(0, 0, 0), col(1, 0, 0), col(1, 1, 0), col(0, 1,
	// 0), col(1, 0, 1), col(0, 0, 1),
	// col(0, 1, 1), col(1, 1, 1) };
	private Color[] c = { col(0.1f, 0, 0), col(0.1f, 0, 0), col(0.1f, 0, 0), col(0.1f, 0, 0), col(0.1f, 0, 0),
			col(0.1f, 0, 0), col(0.1f, 0, 0), col(0.1f, 0, 0) };

	private Vector[] n = { vec(0f, 0f, 0.5f), vec(0f, 0f, -0.5f), vec(0.5f, 0f, 0f), vec(0f, 0.5f, 0f),
			vec(-0.5f, 0f, 0f), vec(0f, -0.5f, 0f) };

	// Vertices combine position and color information. Every four vertices
	// define
	// one side of the cube.
	private Vertex[] cubeVertices = {
			// front 1
			v(p[0], c[0], n[0]), v(p[1], c[1], n[0]), v(p[2], c[2], n[0]),
			// front 2
			v(p[2], c[2], n[0]), v(p[3], c[3], n[0]), v(p[0], c[0], n[0]),
			// back 1
			v(p[4], c[4], n[1]), v(p[5], c[5], n[1]), v(p[6], c[6], n[1]),
			// back 2
			v(p[6], c[6], n[1]), v(p[7], c[7], n[1]), v(p[4], c[4], n[1]),
			// right 1
			v(p[1], c[1], n[2]), v(p[4], c[4], n[2]), v(p[7], c[7], n[2]),
			// right 2
			v(p[7], c[7], n[2]), v(p[2], c[2], n[2]), v(p[1], c[1], n[2]),
			// top 1
			v(p[3], c[3], n[3]), v(p[2], c[2], n[3]), v(p[7], c[7], n[3]),
			// top 2
			v(p[7], c[7], n[3]), v(p[6], c[6], n[3]), v(p[3], c[3], n[3]),
			// left 1
			v(p[5], c[5], n[4]), v(p[0], c[0], n[4]), v(p[3], c[3], n[4]),
			// left 2
			v(p[3], c[3], n[4]), v(p[6], c[6], n[4]), v(p[5], c[5], n[4]),
			// bottom 1
			v(p[5], c[5], n[5]), v(p[4], c[4], n[5]), v(p[1], c[1], n[5]),
			// bottom 2
			v(p[1], c[1], n[5]), v(p[0], c[0], n[5]), v(p[5], c[5], n[5]) };

	private Vector[] p2 = { vec(-w2 + 0.5f, -h2 + 0.5f, d2 + 0.5f), vec(w2 + 0.5f, -h2 + 0.5f, d2 + 0.5f),
			vec(w2 + 0.5f, h2 + 0.5f, d2 + 0.5f), vec(-w2 + 0.5f, h2 + 0.5f, d2 + 0.5f),
			vec(w2 + 0.5f, -h2 + 0.5f, -d2 + 0.5f), vec(-w2 + 0.5f, -h2 + 0.5f, -d2 + 0.5f),
			vec(-w2 + 0.5f, h2 + 0.5f, -d2 + 0.5f), vec(w2 + 0.5f, h2 + 0.5f, -d2 + 0.5f) };

	private Vertex[] cubeVertices2 = {
			// front 1
			v(p2[0], c[0], n[0]), v(p2[1], c[1], n[0]), v(p2[2], c[2], n[0]),
			// front 2
			v(p2[2], c[2], n[0]), v(p2[3], c[3], n[0]), v(p2[0], c[0], n[0]),
			// back 1
			v(p2[4], c[4], n[1]), v(p2[5], c[5], n[1]), v(p2[6], c[6], n[1]),
			// back 2
			v(p2[6], c[6], n[1]), v(p2[7], c[7], n[1]), v(p2[4], c[4], n[1]),
			// right 1
			v(p2[1], c[1], n[2]), v(p2[4], c[4], n[2]), v(p2[7], c[7], n[2]),
			// right 2
			v(p2[7], c[7], n[2]), v(p2[2], c[2], n[2]), v(p2[1], c[1], n[2]),
			// top 1
			v(p2[3], c[3], n[3]), v(p2[2], c[2], n[3]), v(p2[7], c[7], n[3]),
			// top 2
			v(p2[7], c[7], n[3]), v(p2[6], c[6], n[3]), v(p2[3], c[3], n[3]),
			// left 1
			v(p2[5], c[5], n[4]), v(p2[0], c[0], n[4]), v(p2[3], c[3], n[4]),
			// left 2
			v(p2[3], c[3], n[4]), v(p2[6], c[6], n[4]), v(p2[5], c[5], n[4]),
			// bottom 1
			v(p2[5], c[5], n[5]), v(p2[4], c[4], n[5]), v(p2[1], c[1], n[5]),
			// bottom 2
			v(p2[1], c[1], n[5]), v(p2[0], c[0], n[5]), v(p2[5], c[5], n[5]) };
}
