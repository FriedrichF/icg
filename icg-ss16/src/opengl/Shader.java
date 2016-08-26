package opengl;

import static ogl.vecmathimp.FactoryDefault.vecmath;
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
import java.util.ArrayList;
import java.util.List;

import ogl.app.MatrixUniform;
import ogl.app.Util;
import ogl.app.Vertex;
import ogl.app.VertexArrayObject;
import ogl.vecmath.Matrix;

public class Shader {

	private static Shader instance = null;

	// The shader program.
	private int program;

	// Initialize the rotation angle of the cube.
	private float angle = 0;

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	// The location of the "mvpMatrix" uniform variable.
	private MatrixUniform modelMatrixUniform;
	private MatrixUniform viewMatrixUniform;
	private MatrixUniform projectionMatrixUniform;
	private MatrixUniform normalMatrixUniform;

	// the Vertex Array Object which will represent the cube
	private List<VertexArrayObject> vertexObjects = new ArrayList<VertexArrayObject>();
	private List<Matrix[]> matrixArray = new ArrayList<Matrix[]>();

	public void addVertexArrayObject(Vertex[] vertices) {
		// create Vertex Array Object (VAO) from the cube's vertices
		try {
			this.vertexObjects.add(new VertexArrayObject(vertices));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearLists(){
		this.vertexObjects.clear();
		this.matrixArray.clear();
	}
	
	public void addMatrices(Matrix modelMatrix, Matrix normalMatrix){
		Matrix[] mArray = {modelMatrix, normalMatrix};
		matrixArray.add(mArray);
	}

	public static Shader getInstance() {
		if (instance == null)
			instance = new Shader();
		return instance;
	}

	private Shader() {
		// Set background color to black.
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Enable depth testing.
		glEnable(GL_DEPTH_TEST);

		int vs = glCreateShader(GL_VERTEX_SHADER);

		glShaderSource(vs, readFile("shaderCode/vertexShader.c"));
		glCompileShader(vs);
		Util.checkCompilation(vs);

		// Create and compile the fragment shader.
		int fs = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(fs, readFile("shaderCode/fragmentShader.c"));
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

		for (int i = 0; i < vertexObjects.size(); i++) {
			// The modeling transformation. Object space to world space.
			Matrix modelMatrix = matrixArray.get(i)[0];

			Matrix normalMatrix = matrixArray.get(i)[1].transpose();

			// Activate the shader program and set the transformation matrices
			// to
			// the
			// uniform variables.
			glUseProgram(program);
			modelMatrixUniform.set(modelMatrix);
			viewMatrixUniform.set(viewMatrix);
			projectionMatrixUniform.set(projectionMatrix);
			normalMatrixUniform.set(normalMatrix);

			// bind the cube's VAO
			vertexObjects.get(i).bind();

			// draw the cube
			vertexObjects.get(i).draw();

			// unbind the cube's VAO
			vertexObjects.get(i).unbind();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ogl.app.App#cleanUp()
	 */
	public void cleanUp() {
		for (VertexArrayObject vertexArrayObject : vertexObjects) {
			if (vertexArrayObject != null)
				vertexArrayObject.cleanUp();
		}

	}

	private String readFile(String path) {
		try {
			BufferedReader vertexBufferReader = new BufferedReader(new FileReader(path));
			String str;
			String result = "";

			while ((str = vertexBufferReader.readLine()) != null)
				result += str + "\n";

			vertexBufferReader.close();
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
