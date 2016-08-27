package obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import icg.math.FactoryImpl;
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
			} else {
				continue;
			}
		}
		reader.close();
		return m;

	}
	
	private static Face parseFace(String line){
		String[] faceArray = line.split(" ");
		String[] xFace = faceArray[1].split("/");
		String[] yFace = faceArray[2].split("/");
		String[] zFace = faceArray[3].split("/");
		
		int[] vertexIndices = new int[3];
		vertexIndices[0] = Integer.parseInt(xFace[0]);
		vertexIndices[1] = Integer.parseInt(yFace[0]);
		vertexIndices[2] = Integer.parseInt(zFace[0]);
		
		int[] normalIndices = new int[3];
		normalIndices[0] = Integer.parseInt(xFace[2]);
		normalIndices[1] = Integer.parseInt(yFace[2]);
		normalIndices[2] = Integer.parseInt(zFace[2]);
		
		return new Face(vertexIndices, normalIndices);
	}

	private static Vector parseVector(String line) {
		return FactoryImpl.vecmath.vector(Float.parseFloat(line.split(" ")[1]), Float.parseFloat(line.split(" ")[2]),
				Float.parseFloat(line.split(" ")[3]));
	}
}
