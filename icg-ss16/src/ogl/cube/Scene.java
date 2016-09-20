package ogl.cube;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import obj.Face;
import obj.Model;
import obj.objLoader;
import ogl.app.App;
import ogl.app.Input;
import ogl.app.OpenGLApp;
import ogl.app.Texture;
import ogl.app.Vertex;
import ogl.vecmath.Color;
import ogl.vecmath.Vector;
import opengl.Shader;
import scenegraph.Driver;
import scenegraph.Entity;
import scenegraph.Geometrieknoten;
import scenegraph.Gruppenknoten;
import scenegraph.Jumper;
import scenegraph.Kameraknoten;
import scenegraph.Knoten;
import scenegraph.Lichtknoten;
import scenegraph.Rotor;
import scenegraph.TextureNormal;
import scenegraph.Traverser;
import scenegraph.TraverserObject;
import scenegraph.TraverserTexture;

public class Scene implements App {
	static public void main(String[] args) {
		System.setProperty("java.awt.headless", "true");

		new OpenGLApp("Scene", new Scene()).start();
	}

	Knoten knotenRoot;

	@Override
	public void init() {
		Model m = new Model();
		try {
			m = objLoader.loadFile("objFiles/naboo.obj");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		shader = Shader.getInstance();

//		List<TextureNormal> textures = new ArrayList<TextureNormal>();
//		textures.add(new TextureNormal("154.jpg", false));
//		textures.add(new TextureNormal("154_norm.jpg", true));
//		textures.add(new TextureNormal("earth.jpg", false));
//		textures.add(new TextureNormal("sun.jpg", false));
//		textures.add(new TextureNormal("moonmap1k.jpg", false));
//		textures.add(new TextureNormal("sterne.jpg", false));
//		textures.add(new TextureNormal("earth_norm.jpg", true));
//
//		shader.initTextures(textures);

		Vertex[] sphere = objLoader.getVertexArray(m, col(0.1f, 0, 0));

		knotenRoot = new Gruppenknoten("Root", FactoryImpl.vecmath.translationMatrix(0, 0, 0));
//		Knoten knotenLicht = new Gruppenknoten("Root", FactoryImpl.vecmath.translationMatrix(0, 0, 0));
//		Knoten knotenA = new Gruppenknoten("KamerKnoten", FactoryImpl.vecmath.translationMatrix(0, 0, 0));
//		Knoten bunnyKnoten = new Gruppenknoten("BunnyKnoten", FactoryImpl.vecmath.translationMatrix(0, -1f, 0));
//		Knoten kugelKnoten = new Gruppenknoten("Kugel Knoten", FactoryImpl.vecmath.translationMatrix(1, 0, 0));
//		Knoten kugel = new Geometrieknoten("Kugel", FactoryImpl.vecmath.scaleMatrix(0.005f, 0.005f, 0.005f), sphere, "earth.jpg",	"");
//		Knoten kamera = new Kameraknoten("kamera", FactoryImpl.vecmath.translationMatrix(0, 0, 0));
//		Knoten knotenCube = new Geometrieknoten("Cube", FactoryImpl.vecmath.translationMatrix(0, 0, -3), cubeVertices, "154.jpg", "154_norm.jpg");
//		Knoten knotenCube2 = new Geometrieknoten("Cube2", FactoryImpl.vecmath.translationMatrix(1.5f, 0, -3), cubeVertices, "154.jpg", "");
//		Knoten knotenCube3 = new Geometrieknoten("Cube3", FactoryImpl.vecmath.translationMatrix(0, -1, -1),	cubeVertices, "154.jpg", "");
//
//		Knoten lichtKnoten = new Lichtknoten("Licht", FactoryImpl.vecmath.translationMatrix(0, 1, 1));
//
//		knotenRoot.setChild(knotenA);
//		knotenRoot.setChild(knotenCube);
//		knotenRoot.setChild(kugelKnoten);
//		knotenRoot.setChild(knotenLicht);
//		kugelKnoten.setChild(kugel);
//		knotenLicht.setChild(lichtKnoten);
//		knotenA.setChild(kamera);
//		knotenA.setChild(knotenCube3);
//
//		Entity jumper = new Jumper("Jumger", kugel, vec(0, 1, 0), 2f, 100f);
//		Entity rotorLicht = new Rotor("Licht rotation", knotenLicht, vec(0, 1, 0), 60);
//		Entity rotor3 = new Rotor("Rotation", knotenCube, vec(1, 0, 0), 30);
//		Entity driver = new Driver("Driver", kamera, 0.01f);
//
////		entities.add(rotorLicht);
//		entities.add(rotor3);
//		entities.add(driver);
//		entities.add(jumper);
		
		Knoten knotenLicht = new Gruppenknoten("knoten Sonne", FactoryImpl.vecmath.translationMatrix(0, 0, 0));
		Knoten sonne = new Geometrieknoten("Sonne", FactoryImpl.vecmath.scaleMatrix(0.005f, 0.005f, 0.005f), sphere, "sun.jpg",	"");
		Knoten sky = new Geometrieknoten("Sky", FactoryImpl.vecmath.scaleMatrix(0.4f, 0.4f, 0.4f), sphere, "sterne.jpg", "");
		Knoten erdeKnoten = new Gruppenknoten("BunnyKnoten", FactoryImpl.vecmath.translationMatrix(3f, 0, 0));
		Knoten erde = new Geometrieknoten("Erde", FactoryImpl.vecmath.scaleMatrix(0.005f, 0.005f, 0.005f).mult(FactoryImpl.vecmath.rotationMatrix(vec(1,0,0), 90)), sphere, "earth.jpg", "earth_norm.jpg");
		Knoten mond = new Geometrieknoten("Mond", FactoryImpl.vecmath.translationMatrix(1f,0,0).mult(FactoryImpl.vecmath.scaleMatrix(0.001f, 0.001f, 0.001f)).mult(FactoryImpl.vecmath.rotationMatrix(vec(1,0,0), 90)), sphere, "moonmap1k.jpg", "");
		
		Knoten kamera = new Kameraknoten("kamera", FactoryImpl.vecmath.translationMatrix(0, -1, 0));
		
		Knoten lichtKnoten = new Lichtknoten("Licht", FactoryImpl.vecmath.translationMatrix(0, 0, 0));
		
		knotenRoot.setChild(kamera);
		knotenRoot.setChild(knotenLicht);
		knotenRoot.setChild(lichtKnoten);
		knotenRoot.setChild(sky);
		knotenRoot.setChild(sonne);
		knotenLicht.setChild(erdeKnoten);
		erdeKnoten.setChild(erde);
		erdeKnoten.setChild(mond);
		
		Entity driver = new Driver("Driver", kamera, 0.01f);
		Entity sonnenRot = new Rotor("Sonnen rotation", knotenLicht, vec(0, 1, 0), 20);
		Entity erdeUmlauf = new Rotor("Erd umlaufbahn", erdeKnoten, vec(0, 1, 0), 10);
		Entity erdeRot = new Rotor("Erd rotation", erde, vec(0, 0, 1), 50);
		
		entities.add(sonnenRot);
		entities.add(erdeUmlauf);
		entities.add(erdeRot);
		entities.add(driver);
		
		TraverserTexture texTraverser = new TraverserTexture();
		knotenRoot.accept(texTraverser);
		
		shader.initTextures(texTraverser.getTextures());

		traverser = new TraverserObject();
		knotenRoot.accept(traverser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cg2.cube.App#simulate(float, cg2.cube.Input)
	 */
	@Override
	public void simulate(float elapsed, Input input) {
		for (Entity entity : entities) {
			entity.simulate(elapsed, input);
		}
		cleanUp();
		shader.clearLists();

		knotenRoot.accept(traverser);
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
	Traverser traverser;

	// Width, depth and height of the cube divided by 2.
	float w2 = 0.5f;
	float h2 = 0.5f;
	float d2 = 0.5f;

	// Make construction of vertices easy on the eyes.
	private Vertex v(Vector p, Color c, Vector n, Vector tex) {
		return new Vertex(p, c, n, tex);
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

	private Vector[] t = { vec(0, 0, 0), vec(1, 0, 0), vec(1, 1, 0), vec(0, 1, 0) };

	// The colors of the cube vertices.
	// private Color[] c = { col(0, 0, 0), col(1, 0, 0), col(1, 1, 0), col(0, 1,
	// 0), col(1, 0, 1), col(0, 0, 1),
	// col(0, 1, 1), col(1, 1, 1) };
	private Color[] c = { col(0f, 0, 0), col(0f, 0, 0), col(0f, 0, 0), col(0f, 0, 0), col(0f, 0, 0), col(0f, 0, 0),
			col(0f, 0f, 0), col(0, 0f, 0) };

	private Vector[] n = { vec(0f, 0f, 0.5f), vec(0f, 0f, -0.5f), vec(0.5f, 0f, 0f), vec(0f, 0.5f, 0f),
			vec(-0.5f, 0f, 0f), vec(0f, -0.5f, 0f) };

	// Vertices combine position and color information. Every four vertices
	// define
	// one side of the cube.
	private Vertex[] cubeVertices = {
			// front 1
			v(p[0], c[0], n[0], t[0]), v(p[1], c[1], n[0], t[1]), v(p[2], c[2], n[0], t[2]),
			// front 2
			v(p[2], c[2], n[0], t[2]), v(p[3], c[3], n[0], t[3]), v(p[0], c[0], n[0], t[0]),
			// back 1
			v(p[4], c[4], n[1], t[0]), v(p[5], c[5], n[1], t[1]), v(p[6], c[6], n[1], t[2]),
			// back 2
			v(p[6], c[6], n[1], t[2]), v(p[7], c[7], n[1], t[3]), v(p[4], c[4], n[1], t[0]),
			// right 1
			v(p[1], c[1], n[2], t[0]), v(p[4], c[4], n[2], t[1]), v(p[7], c[7], n[2], t[2]),
			// right 2
			v(p[7], c[7], n[2], t[2]), v(p[2], c[2], n[2], t[3]), v(p[1], c[1], n[2], t[0]),
			// top 1
			v(p[3], c[3], n[3], t[0]), v(p[2], c[2], n[3], t[1]), v(p[7], c[7], n[3], t[2]),
			// top 2
			v(p[7], c[7], n[3], t[2]), v(p[6], c[6], n[3], t[3]), v(p[3], c[3], n[3], t[0]),
			// left 1
			v(p[5], c[5], n[4], t[0]), v(p[0], c[0], n[4], t[1]), v(p[3], c[3], n[4], t[2]),
			// left 2
			v(p[3], c[3], n[4], t[2]), v(p[6], c[6], n[4], t[3]), v(p[5], c[5], n[4], t[0]),
			// bottom 1
			v(p[5], c[5], n[5], t[0]), v(p[4], c[4], n[5], t[1]), v(p[1], c[1], n[5], t[2]),
			// bottom 2
			v(p[1], c[1], n[5], t[2]), v(p[0], c[0], n[5], t[3]), v(p[5], c[5], n[5], t[0]) };

	private Vector[] p2 = { vec(-w2 + 0.5f, -h2 + 0.5f, d2 + 0.5f), vec(w2 + 0.5f, -h2 + 0.5f, d2 + 0.5f),
			vec(w2 + 0.5f, h2 + 0.5f, d2 + 0.5f), vec(-w2 + 0.5f, h2 + 0.5f, d2 + 0.5f),
			vec(w2 + 0.5f, -h2 + 0.5f, -d2 + 0.5f), vec(-w2 + 0.5f, -h2 + 0.5f, -d2 + 0.5f),
			vec(-w2 + 0.5f, h2 + 0.5f, -d2 + 0.5f), vec(w2 + 0.5f, h2 + 0.5f, -d2 + 0.5f) };

	// private Vertex[] cubeVertices2 = {
	// // front 1
	// v(p2[0], c[0], n[0]), v(p2[1], c[1], n[0]), v(p2[2], c[2], n[0]),
	// // front 2
	// v(p2[2], c[2], n[0]), v(p2[3], c[3], n[0]), v(p2[0], c[0], n[0]),
	// // back 1
	// v(p2[4], c[4], n[1]), v(p2[5], c[5], n[1]), v(p2[6], c[6], n[1]),
	// // back 2
	// v(p2[6], c[6], n[1]), v(p2[7], c[7], n[1]), v(p2[4], c[4], n[1]),
	// // right 1
	// v(p2[1], c[1], n[2]), v(p2[4], c[4], n[2]), v(p2[7], c[7], n[2]),
	// // right 2
	// v(p2[7], c[7], n[2]), v(p2[2], c[2], n[2]), v(p2[1], c[1], n[2]),
	// // top 1
	// v(p2[3], c[3], n[3]), v(p2[2], c[2], n[3]), v(p2[7], c[7], n[3]),
	// // top 2
	// v(p2[7], c[7], n[3]), v(p2[6], c[6], n[3]), v(p2[3], c[3], n[3]),
	// // left 1
	// v(p2[5], c[5], n[4]), v(p2[0], c[0], n[4]), v(p2[3], c[3], n[4]),
	// // left 2
	// v(p2[3], c[3], n[4]), v(p2[6], c[6], n[4]), v(p2[5], c[5], n[4]),
	// // bottom 1
	// v(p2[5], c[5], n[5]), v(p2[4], c[4], n[5]), v(p2[1], c[1], n[5]),
	// // bottom 2
	// v(p2[1], c[1], n[5]), v(p2[0], c[0], n[5]), v(p2[5], c[5], n[5]) };
}
