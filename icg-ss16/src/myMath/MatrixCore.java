package myMath;

public class MatrixCore {

	float[] matrix = new float[16];

	public MatrixCore(float[] vals) {
		this.matrix = vals;
	}

	public float[] getMatrix() {
		return matrix;
	}

	public void setMatrix(float[] matrix) {
		this.matrix = matrix;
	}

	public float get(int c, int r) {
		return matrix[4 * r + c];
	}

	public MatrixCore mult(MatrixCore m) {
		float[] result = new float[16];

		for (int resultCell = 0; resultCell < result.length; resultCell++) {
			for (int i = 0; i < 4; i++) {
				float that = get(i,(int) resultCell / 4);
				float other = m.get(resultCell % 4, i);
				result[resultCell] += that * other;
			}
		}

		return new MatrixCore(result);
	}

}
