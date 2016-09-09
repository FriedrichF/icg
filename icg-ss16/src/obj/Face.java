package obj;

public class Face {
	private int[] vertexIndex;
	private int[] normalIndex;
	private int[] texturIndex;
	
	public Face(int[] vertexIndex, int[] normalIndex, int[] textureIndex){
		this.vertexIndex = vertexIndex;
		this.normalIndex = normalIndex;
		this.texturIndex = textureIndex;
	}

	public int[] getTexturIndex() {
		return texturIndex;
	}

	public void setTexturIndex(int[] texturIndex) {
		this.texturIndex = texturIndex;
	}

	public int[] getVertexIndex() {
		return vertexIndex;
	}

	public void setVertexIndex(int[] vertexIndex) {
		this.vertexIndex = vertexIndex;
	}

	public int[] getNormalIndex() {
		return normalIndex;
	}

	public void setNormalIndex(int[] normalIndex) {
		this.normalIndex = normalIndex;
	}
	
	
}
