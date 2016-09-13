package ogl.app;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import icg.math.FactoryImpl;
import scenegraph.Geometrieknoten;

//Auxiliary class to represent a Vertex Array Object (VAO)
public class VertexArrayObject {
	// The attribute indices for the vertex data.
	public static int vertexAttribIdx = 0;
	public static int colorAttribIdx = 1;
	public static int normalAttribIdx = 2;
	public static int textureAttribIdx = 3;

	// the VAO id
	private final int id;
	// the number of stored vertices
	private final int numberOfVertices;
	// the vertex buffer objects associated with this VAO
	private ArrayList<VertexBufferObject> vbos = new ArrayList<VertexBufferObject>();
	
	public Geometrieknoten knoten;

	// Auxiliary class to represent a Vertex Buffer Object (VBO)
	private class VertexBufferObject {
		private final int id;
		private final int attributeIdx;

		public VertexBufferObject(int attributeIdx, FloatBuffer data) {
			this.id = glGenBuffers();
			this.attributeIdx = attributeIdx;

			glBindBuffer(GL_ARRAY_BUFFER, id);
			glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
			glVertexAttribPointer(attributeIdx, vecmath.vectorSize(), GL_FLOAT, false, 0, 0);
		}

		public void enable() {
			glEnableVertexAttribArray(attributeIdx);
		}

		public void cleanUp() {
			glDeleteBuffers(id);
		}
	}

	/**
	 * Creates a vertex array object from the given array of vertices
	 * (representing triangles)
	 * 
	 * @param vertices
	 *            the vertices to be inserted into the VAO
	 * @return the created vertex array object's id
	 */
	public VertexArrayObject(Geometrieknoten knoten) throws Exception {
		// create the VAO id
		if (OpenGLApp.getGLMajor() >= 3)
			id = GL30.glGenVertexArrays();
		else
			id = -1;

		Vertex[] vertices = knoten.vertices; 
		
		this.knoten = knoten;
		
		// store the number of vertices
		numberOfVertices = vertices.length;

		// Compile vertex data into a Java Buffer data structures that can be
		// passed to the OpenGL API efficiently.
		// create buffers
		FloatBuffer positionData = BufferUtils.createFloatBuffer(numberOfVertices * FactoryImpl.vecmath.vectorSize());
		FloatBuffer colorData = BufferUtils.createFloatBuffer(numberOfVertices * FactoryImpl.vecmath.colorSize());
		FloatBuffer normalData = BufferUtils.createFloatBuffer(numberOfVertices * FactoryImpl.vecmath.vectorSize());
		FloatBuffer textureData = null;

		if (vertices[0].texture != null)
			textureData = BufferUtils.createFloatBuffer(numberOfVertices * FactoryImpl.vecmath.colorSize());

		// fill buffers
		for (Vertex v : vertices) {
			positionData.put(v.position.asArray());
			colorData.put(v.color.asArray());
			normalData.put(v.normal.asArray());
			if (vertices[0].texture != null)
				textureData.put(v.texture.asArray());
		}

		// rewind buffers
		positionData.rewind();
		colorData.rewind();
		normalData.rewind();
		if (vertices[0].texture != null)
			textureData.rewind();

		// activate vertex array object
		if (OpenGLApp.getGLMajor() >= 3)
			GL30.glBindVertexArray(id);

		// register Vertex Buffer (VBO)
		vbos.add(new VertexBufferObject(vertexAttribIdx, positionData));

		// register Color Buffer
		vbos.add(new VertexBufferObject(colorAttribIdx, colorData));

		// register Normal Buffer
		vbos.add(new VertexBufferObject(normalAttribIdx, normalData));

		// register Texture Buffer
		if (vertices[0].texture != null)
			vbos.add(new VertexBufferObject(textureAttribIdx, textureData));
	}

	public void unbind() {
		// Restore state
		glDisableVertexAttribArray(0);
		if (OpenGLApp.getGLMajor() >= 3)
			GL30.glBindVertexArray(0);
	}

	/**
	 * delete allocated buffers and this VAO
	 */
	public void cleanUp() {
		// unbind this VAO
		unbind();
		// delete all VBOs
		for (VertexBufferObject vbo : vbos)
			vbo.cleanUp();

		// delete this VAO
		if (OpenGLApp.getGLMajor() >= 3)
			GL30.glDeleteVertexArrays(id);
	}

	/**
	 * activate this VAO
	 */
	public void bind() {
		// activate this VAO
		if (OpenGLApp.getGLMajor() >= 3)
			GL30.glBindVertexArray(id);

		// enable all related VBOs
		for (VertexBufferObject vbo : vbos)
			vbo.enable();
	}

	/**
	 * draw this VAO
	 */
	public void draw() {
		glDrawArrays(GL_TRIANGLES, 0, numberOfVertices);
	}
}
