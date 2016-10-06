package obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import ogl.app.Vertex;
import ogl.vecmath.Color;
import ogl.vecmath.Vector;

public class objLoader {
	public static Model loadFile(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));

		Model m = new Model();
		String line;
		while ((line = reader.readLine()) != null) {
			String prefix = line.split(" ")[0];
			if (prefix.equals("#")) {
				continue;
			} else if (prefix.equals("v")) {
				m.getVertices().add(parseVector(line));
			} else if (prefix.equals("vn")) {
				m.getNormals().add(parseVector(line));
			} else if (prefix.equals("f")) {
				m.getFaces().add(parseFace(line));
			} else if (prefix.equals("vt")) {
				m.getTextures().add(parseVector(line));
			} else {
				continue;
			}
		}
		reader.close();
		return m;

	}

	private static Face parseFace(String line) {
		String[] faceArray = line.split(" ");
		String[] xFace = faceArray[1].split("/");
		String[] yFace = faceArray[2].split("/");
		String[] zFace = faceArray[3].split("/");

		int[] vertexIndices = new int[3];
		vertexIndices[0] = Integer.parseInt(xFace[0]);
		vertexIndices[1] = Integer.parseInt(yFace[0]);
		vertexIndices[2] = Integer.parseInt(zFace[0]);

		int[] textureIndices = new int[3];
		if (!xFace[1].equals("")) {
			textureIndices[0] = Integer.parseInt(xFace[1]);
			textureIndices[1] = Integer.parseInt(yFace[1]);
			textureIndices[2] = Integer.parseInt(zFace[1]);
		}

		int[] normalIndices = new int[3];
		normalIndices[0] = Integer.parseInt(xFace[2]);
		normalIndices[1] = Integer.parseInt(yFace[2]);
		normalIndices[2] = Integer.parseInt(zFace[2]);

		return new Face(vertexIndices, normalIndices, textureIndices);
	}

	public static Vertex[] getVertexArray(Model m, Color c) {
		List<Vertex> bunnyVertex = new ArrayList<Vertex>();

		for (Face face : m.getFaces()) {
			// x
			Vector texturVecX;
			int x = face.getTexturIndex()[0];
			if(x == 0)
				texturVecX = null;
			else
				texturVecX = m.getTextures().get(face.getTexturIndex()[0] - 1);
			
			bunnyVertex.add(new Vertex(m.getVertices().get(face.getVertexIndex()[0] - 1), c,
					m.getNormals().get(face.getNormalIndex()[0] - 1),
					texturVecX));
			// y
			Vector texturVecY;
			if(face.getTexturIndex()[1] == 0)
				texturVecY = null;
			else
				texturVecY = m.getTextures().get(face.getTexturIndex()[1] - 1);
			bunnyVertex.add(new Vertex(m.getVertices().get(face.getVertexIndex()[1] - 1), c,
					m.getNormals().get(face.getNormalIndex()[1] - 1),
					texturVecY));
			// z
			Vector texturVecZ;
			if(face.getTexturIndex()[2] == 0)
				texturVecZ = null;
			else
				texturVecZ = m.getTextures().get(face.getTexturIndex()[2] - 1);
			bunnyVertex.add(new Vertex(m.getVertices().get(face.getVertexIndex()[2] - 1), c,
					m.getNormals().get(face.getNormalIndex()[2] - 1),
					texturVecZ));
		}

		Vertex[] bunnyArray = new Vertex[bunnyVertex.size()];
		return bunnyVertex.toArray(bunnyArray);
	}

	private static Vector parseVector(String line) {
		return FactoryImpl.vecmath.vector(Float.parseFloat(line.split(" ")[1]), Float.parseFloat(line.split(" ")[2]),
				Float.parseFloat(line.split(" ")[3]));
	}
}
