package scenegraph;

import icg.math.FactoryImpl;
import ogl.app.Input;
import ogl.vecmath.Vector;

public class Rotor extends Entity {

	private Vector rotateAxis;
	private float speed;

	public Rotor(String name, Knoten knoten, Vector rotateAxis, float speed) {
		super(name, knoten);
		this.rotateAxis = rotateAxis;
		this.speed = speed;
	}

	public Vector getRotateAxis() {
		return rotateAxis;
	}

	public void setRotateAxis(Vector rotateAxis) {
		this.rotateAxis = rotateAxis;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public void simulate(float elapsed, Input input) {
		this.getKnoten().setTransformMatrix(this.getKnoten().getTransformMatrix()
				.mult(FactoryImpl.vecmath.rotationMatrix(rotateAxis, speed * elapsed)));
	}

}
