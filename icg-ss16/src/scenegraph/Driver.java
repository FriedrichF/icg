package scenegraph;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import ogl.app.Input;
import ogl.vecmath.Vector;

public class Driver extends Entity {

	private float speed;

	public Driver(String name, Knoten knoten, float speed) {
		super(name, knoten);
		this.speed = speed;
	}

	@Override
	public void simulate(float elapsed, Input input) {
		int xRichtung = 0;
		int zRichtung = 0;

		if (input.isKeyDown(GLFW_KEY_W))
			zRichtung = -1;
		if (input.isKeyDown(GLFW_KEY_S))
			zRichtung += 1;
		if (input.isKeyDown(GLFW_KEY_A))
			this.getKnoten().setTransformMatrix(this.getKnoten().getTransformMatrix().mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(0,1f,0), 90*elapsed)));
		if (input.isKeyDown(GLFW_KEY_D))
			this.getKnoten().setTransformMatrix(this.getKnoten().getTransformMatrix().mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(0,1f,0), -90*elapsed)));
		

		this.getKnoten().setTransformMatrix(this.getKnoten().getTransformMatrix()
				.mult(FactoryImpl.vecmath.translationMatrix(xRichtung * speed, 0, zRichtung * speed)));
	}

}
