package scenegraph;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import ogl.app.Input;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class Kamera {
	// 3d vector to store the camera's position in
	private Vector position = null;
	private float mousePositionX = 0;
	private float mousePositionY = 0;

	// the rotation around the Y axis of the camera
	private float yaw = 0.0f;
	// the rotation around the X axis of the camera
	private float pitch = 0.0f;

	public Kamera(float x, float y, float z) {
		// instantiate position Vector3f to the x y z params.
		position = new VectorImpl(x, y, z);
	}
	
	public void setMousePosition(float x, float y){
		mousePositionX = x;
		mousePositionY = y;
	}
	
	public float getDX(Input input){
		float newX = input.getMousePosition().x();
		float delta = newX - mousePositionX;
		mousePositionX = newX;
		return delta;
	}
	
	public float getDY(Input input){
		float newY = input.getMousePosition().y();
		float delta = newY - mousePositionY;
		mousePositionY = newY;
		return delta;
	}

	// increment the camera's current yaw rotation
	public void yaw(float amount) {
		// increment the yaw by the amount param
		yaw += amount;
	}

	// increment the camera's current yaw rotation
	public void pitch(float amount) {
		// increment the pitch by the amount param
		pitch += amount;
	}

	// moves the camera forward relative to its current rotation (yaw)
	public void walkForward(float distance) {
		position.asArray()[0] -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.asArray()[1] += distance * (float) Math.sin(Math.toRadians(pitch));
		position.asArray()[2] += distance * (float) Math.cos(Math.toRadians(yaw));
	}

	// moves the camera backward relative to its current rotation (yaw)
	public void walkBackwards(float distance) {
		position.asArray()[0] += distance * (float) Math.sin(Math.toRadians(yaw));
		position.asArray()[1] -= distance * (float) Math.sin(Math.toRadians(pitch));
		position.asArray()[2] -= distance * (float) Math.cos(Math.toRadians(yaw));
	}

	// strafes the camera left relitive to its current rotation (yaw)
	public void strafeLeft(float distance) {
		position.asArray()[0] -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.asArray()[2] += distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	// strafes the camera right relitive to its current rotation (yaw)
	public void strafeRight(float distance) {
		position.asArray()[0] -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
		position.asArray()[2] += distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}

	// translates and rotate the matrix so that it looks through the camera
	// this dose basic what gluLookAt() does
	public Matrix lookThrough() {
		Matrix viewMatrix = FactoryImpl.vecmath.identityMatrix()
				.mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(1, 0, 0), pitch))
				.mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(0, 1, 0), yaw))
				.mult(FactoryImpl.vecmath.translationMatrix(position));
		
		return viewMatrix;
	}
}
