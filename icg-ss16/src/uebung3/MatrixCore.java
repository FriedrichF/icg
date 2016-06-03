package uebung3;

import ogl.vecmath.Matrix;

public class MatrixCore {
	
	float[] matrix = new float[16];
	
	public MatrixCore(float[] vals){
		this.matrix = vals;
	}
	
	public float get(int c, int r) {
		return matrix[4 * r + c];
	}
	
	public MatrixCore mult(MatrixCore m) {
		float[] result = new float[16];

		for (int resultCell = 0; resultCell < result.length; resultCell++) {
			for (int i = 0; i < 4; i++) {
				result[resultCell] += get(resultCell, i) * m.get(i, resultCell);
			}
		}

		return new MatrixCore(result);
	}

}
