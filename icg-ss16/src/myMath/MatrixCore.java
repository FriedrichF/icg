package myMath;

public class MatrixCore {

	float[] matrix = new float[16];

	public MatrixCore(float[] vals) {
		setMatrix(vals);
	}
	
	public MatrixCore(){
		matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1;
	}

	public float[] getMatrix() {
		return matrix;
	}

	public void setMatrix(float[] matrix) {
		this.matrix = matrix;
	}

	public float get(int c, int r) {
		return matrix[4 * c + r];
	}

	public MatrixCore mult(float[] m) {
		float[] result = new float[16];

		for (int resultCell = 0; resultCell < result.length; resultCell++) {
			for (int i = 0; i < 4; i++) {
				float that = get(i, resultCell % 4);
				float other = m[((int) resultCell / 4) * 4 + i];
				result[resultCell] += that * other;
			}
		}

		return new MatrixCore(result);
	}

}
