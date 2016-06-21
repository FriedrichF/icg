package opengl;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ogl.app.App;
import ogl.app.Input;
import ogl.app.MatrixUniform;
import ogl.app.OpenGLApp;
import ogl.app.Util;
import ogl.app.Vertex;
import ogl.app.VertexArrayObject;
import ogl.vecmath.Color;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Opengl implements App {

	static public void main(String[] args) {
		new OpenGLApp("Own Cube", new Opengl()).start();
	}

	private String readFile(String path) {
		try {
			BufferedReader vertexBufferReader = new BufferedReader(new FileReader(path));
			String str;
			String result = "";

			while ((str = vertexBufferReader.readLine()) != null)
				result += str+"\n";

			vertexBufferReader.close();
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void init() {
		// Set background color to black.
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Enable depth testing.
		glEnable(GL_DEPTH_TEST);

		// create Vertex Array Object (VAO) from the cube's vertices
		try {
			cube = new VertexArrayObject(cubeVertices);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int vs = glCreateShader(GL_VERTEX_SHADER);

		glShaderSource(vs, readFile("E:/GIT/ICG/icg/icg-ss16/src/opengl/vertexShader.c"));
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(fs, readFile("E:/GIT/ICG/icg/icg-ss16/src/opengl/fragmentShader.c"));
		glCompileShader(fs);
		Util.checkCompilation(fs);

		// Create the shader program and link vertex and fragment shader
		// together.
		program = glCreateProgram();
		glAttachShader(program, vs);
		glAttachShader(program, fs);

		// Bind the vertex attribute data locations for this shader program. The
		// shader expects to get vertex and color data from the mesh. This needs
		// to
		// be done *before* linking the program.
		glBindAttribLocation(program, VertexArrayObject.vertexAttribIdx, "vertex");
		glBindAttribLocation(program, VertexArrayObject.colorAttribIdx, "color");
		glBindAttribLocation(program, VertexArrayObject.normalAttribIdx, "normal");

		// Link the shader program.
		glLinkProgram(program);
		Util.checkLinkage(program);

		// Bind the matrix uniforms to locations on this shader program. This
		// needs
		// to be done *after* linking the program.
		modelMatrixUniform = new MatrixUniform(program, "modelMatrix");
		viewMatrixUniform = new MatrixUniform(program, "viewMatrix");
		projectionMatrixUniform = new MatrixUniform(program, "projectionMatrix");
		normalMatrixUniform = new MatrixUniform(program, "normalMatrix");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cg2.cube.App#simulate(float, cg2.cube.Input)
	 */
	@Override
	public void simulate(float elapsed, Input input) {
		// Pressing key 'r' toggles the cube animation.
		if (input.isKeyToggled(GLFW_KEY_R))
			// Increase the angle with a speed of 90 degrees per second.
			angle += 90 * elapsed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cg2.cube.App#display(int, int, javax.media.opengl.GL2ES2)
	 */
	@Override
	public void display(int width, int height) {
		// Adjust the the viewport to the actual window size. This makes the
		// rendered image fill the entire window.
		glViewport(0, 0, width, height);

		// Clear all buffers.
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Assemble the transformation matrix that will be applied to all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = vecmath.perspectiveMatrix(60f, aspect, 0.1f, 100f);

		// The inverse camera transformation. World space to camera space.
		Matrix viewMatrix = vecmath.lookatMatrix(vecmath.vector(0f, 0f, 3f), vecmath.vector(0f, 0f, 0f),
				vecmath.vector(0f, 1f, 0f));

		// The modeling transformation. Object space to world space.
		Matrix modelMatrix = vecmath.rotationMatrix(vecmath.vector(1, 1, 1), angle);

		Matrix normalMatrix = modelMatrix.invertFull().transpose();

		// Activate the shader program and set the transformation matrices to
		// the
		// uniform variables.
		glUseProgram(program);
		modelMatrixUniform.set(modelMatrix);
		viewMatrixUniform.set(viewMatrix);
		projectionMatrixUniform.set(projectionMatrix);
		normalMatrixUniform.set(normalMatrix);

		// bind the cube's VAO
		cube.bind();

		// draw the cube
		cube.draw();

		// unbind the cube's VAO
		cube.unbind();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ogl.app.App#cleanUp()
	 */
	public void cleanUp() {
		if (cube != null)
			cube.cleanUp();
	}

	/************************
	 * Variable definitions *
	 ************************/

	// the Vertex Array Object which will represent the cube
	private VertexArrayObject cube = null;

	// The shader program.
	private int program;

	// The location of the "mvpMatrix" uniform variable.
	private MatrixUniform modelMatrixUniform;
	private MatrixUniform viewMatrixUniform;
	private MatrixUniform projectionMatrixUniform;
	private MatrixUniform normalMatrixUniform;

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
		return vecmath.vector(x, y, z);
	}

	// Make construction of colors easy on the eyes.
	private Color col(float r, float g, float b) {
		return vecmath.color(r, g, b);
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

	// Initialize the rotation angle of the cube.
	private float angle = 0;

	// The positions of the cube vertices.
	private Vector[] p = { vec(-w2, -h2, d2), vec(w2, -h2, d2), vec(w2, h2, d2), vec(-w2, h2, d2), vec(w2, -h2, -d2),
			vec(-w2, -h2, -d2), vec(-w2, h2, -d2), vec(w2, h2, -d2) };

	// The colors of the cube vertices.
	private Color[] c = { col(0, 0, 0), col(1, 0, 0), col(1, 1, 0), col(0, 1, 0), col(1, 0, 1), col(0, 0, 1),
			col(0, 1, 1), col(1, 1, 1) };

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
}
