package opengl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glRotatef;
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
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL13;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import ogl.app.MatrixUniform;
import ogl.app.Texture;
import ogl.app.Util;
import ogl.app.Vertex;
import ogl.app.VertexArrayObject;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

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
	private int lightPos;
	private Texture texture1;

	// the Vertex Array Object which will represent the cube
	private List<VertexArrayObject> vertexObjects = new ArrayList<VertexArrayObject>();
	private List<Matrix[]> matrixArray = new ArrayList<Matrix[]>();
	private Vector LightPos;
	private Vector kameraArray;
	private float pitch = 0;
	private float yaw = 0;	
	private Matrix viewMatrix;
	
	public void addLightPos(Vector v){
		LightPos = v;
	}
	
	public void addKamera(Vector kamera, float yaw, float pitch){
		kameraArray = kamera;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public void addViewMatrix(Matrix view){
		this.viewMatrix = view;
	}

	public void addVertexArrayObject(Vertex[] vertices) {
		// create Vertex Array Object (VAO) from the cube's vertices
		try {
			this.vertexObjects.add(new VertexArrayObject(vertices));
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
	
	private Matrix lookThrough(){
		Matrix look = FactoryImpl.vecmath.identityMatrix().mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(1,0,0), (float) Math.toDegrees(pitch)));
		look = look.mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(0,1,0), (float) Math.toDegrees(yaw)));
		look = look.mult(FactoryImpl.vecmath.translationMatrix(kameraArray));
		return look;
	}

	private Shader() {
		texture1 = new Texture(new File("checkered.png"));
		
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
		
		texLoc = glGetUniformLocation(program, "text");
		lightPos = glGetUniformLocation(program, "lightPos");
		
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
		
		
		// Textur - Einheit waehlen
		glEnable(GL_TEXTURE_2D);
		GL13.glActiveTexture ( GL13.GL_TEXTURE0 );
		// gespeicherte Textur aktivieren
		texture1.bind ();
		// Verknuepfung mit Shader herstellen
		glUniform1i( texLoc , 0);
		
		glUniform3f(lightPos, LightPos.x(), LightPos.y(), LightPos.z());


		// Assemble the transformation matrix that will be applied to all
		// vertices in the vertex shader.
		float aspect = (float) width / (float) height;

		// The perspective projection. Camera space to NDC.
		Matrix projectionMatrix = FactoryImpl.vecmath.perspectiveMatrix(60f, aspect, 0.1f, 100f);

		// The inverse camera transformation. World space to camera space.
//		Matrix viewMatrix = FactoryImpl.vecmath.lookatMatrix(FactoryImpl.vecmath.vector(0f, 0f, 3f),
//				FactoryImpl.vecmath.vector(0f, 0f, 0f), FactoryImpl.vecmath.vector(0f, 1f, 0f));
//		Matrix viewMatrix = FactoryImpl.vecmath.FPSViewRH(kameraArray[0],kameraArray[1],kameraArray[2]);
//		Matrix viewMatrix = FactoryImpl.vecmath.FPSViewRH(kameraArray,pitch,yaw);
		
		
		Matrix modelMatrix;
		Matrix normalMatrix;

		for (int i = 0; i < vertexObjects.size(); i++) {
			// The modeling transformation. Object space to world space.
			modelMatrix = matrixArray.get(i)[0];

			normalMatrix = matrixArray.get(i)[1].transpose();

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
