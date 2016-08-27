package obj;

public class Face {
	private int[] vertexIndex;
	private int[] normalIndex;
	
	public Face(int[] vertexIndex, int[] normalIndex){
		this.vertexIndex = vertexIndex;
		this.normalIndex = normalIndex;
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
