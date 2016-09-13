package opengl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
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
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL13;

import icg.math.FactoryImpl;
import ogl.app.MatrixUniform;
import ogl.app.Texture;
import ogl.app.Util;
import ogl.app.Vertex;
import ogl.app.VertexArrayObject;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import scenegraph.Geometrieknoten;
import scenegraph.TextureNormal;

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

	private int texLoc;
	private int normalLoc;
	private int hasNormalLoc;
	private int lightPos;

	// the Vertex Array Object which will represent the cube
	private List<VertexArrayObject> vertexObjects = new ArrayList<VertexArrayObject>();
	private List<Matrix[]> matrixArray = new ArrayList<Matrix[]>();
	private Vector LightPos;
	private Matrix viewMatrix;

	public void addLightPos(Vector v) {
		LightPos = v;
	}

	public void addViewMatrix(Matrix view) {
		this.viewMatrix = view;
	}

	public void addVertexArrayObject(Geometrieknoten k) {
		// create Vertex Array Object (VAO) from the cube's vertices
		try {
			this.vertexObjects.add(new VertexArrayObject(k));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearLists() {
		this.vertexObjects.clear();
		this.matrixArray.clear();
	}

	public void addMatrices(Matrix modelMatrix, Matrix normalMatrix) {
		Matrix[] mArray = { modelMatrix, normalMatrix };
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
		glBindAttribLocation(program, VertexArrayObject.textureAttribIdx, "texCoord");

		// Link the shader program.
		glLinkProgram(program);
		Util.checkLinkage(program);
		glUseProgram(program);

		// Bind the matrix uniforms to locations on this shader program. This
		// needs
		// to be done *after* linking the program.
		modelMatrixUniform = new MatrixUniform(program, "modelMatrix");
		viewMatrixUniform = new MatrixUniform(program, "viewMatrix");
		projectionMatrixUniform = new MatrixUniform(program, "projectionMatrix");
		normalMatrixUniform = new MatrixUniform(program, "normalMatrix");

		lightPos = glGetUniformLocation(program, "lightPos");
		texLoc = glGetUniformLocation(program, "tex");
		normalLoc = glGetUniformLocation(program, "normalTex");
		hasNormalLoc = glGetUniformLocation(program, "hasNormal");
	}

	public void display(int width, int height) {
		// Adjust the the viewport to the actual window size. This makes the
		// rendered image fill the entire window.
		glViewport(0, 0, width, height);

		// Clear all buffers.
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glUniform3f(lightPos, LightPos.x(), LightPos.y(), LightPos.z());

		// Assemble the transformation matrix that will be applied to all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = FactoryImpl.vecmath.perspectiveMatrix(60f, aspect, 0.1f, 100f);

		Matrix modelMatrix;
		Matrix normalMatrix;

		for (int i = 0; i < vertexObjects.size(); i++) {
			if(vertexObjects.get(i).knoten.getNormalIndex() == -1){
				glUniform1f(hasNormalLoc, 0.0f);
			}else{
				glUniform1f(hasNormalLoc, 1.0f);
				glUniform1f(normalLoc, vertexObjects.get(i).knoten.getNormalIndex());
			}
			
			
			glUniform1i(texLoc, vertexObjects.get(i).knoten.getTextureIndex());

			// The modeling transformation. Object space to world space.
			modelMatrix = matrixArray.get(i)[0];

			normalMatrix = matrixArray.get(i)[1].transpose();

			// Activate the shader program and set the transformation matrices
			// to
			// the
			// uniform variables.

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

	public void initTextures(List<TextureNormal> textures) {
		for(int i = 0; i < textures.size(); i++){
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			Texture texture = new Texture(new File(textures.get(i).getTexturePath()));
			texture.bind();
			if(textures.get(i).isNormal())
				glUniform1i(normalLoc, i);
			else
				glUniform1i(texLoc, i);
		}

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
}
