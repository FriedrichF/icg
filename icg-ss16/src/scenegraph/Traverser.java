package scenegraph;

import java.util.Stack;

import icg.math.FactoryImpl;
import ogl.vecmath.Matrix;
import opengl.Shader;

public class Traverser {
	Stack<Matrix> matrixStack = new Stack<Matrix>();

	public void visit(Gruppenknoten k) {
		if (matrixStack.isEmpty())
			matrixStack.push(k.getTransformMatrix());
		else
			matrixStack.push(matrixStack.peek().mult(k.getTransformMatrix()));

		for (Knoten child : k.getChilds()) {
			child.accept(this);
		}
		matrixStack.pop();
	}

	public void visit(Geometrieknoten k) {
		if (matrixStack.isEmpty())
			matrixStack.push(k.getTransformMatrix());
		else
			matrixStack.push(matrixStack.peek().mult(k.getTransformMatrix()));
		
		Matrix topMatrix = matrixStack.pop();
		
		Shader.getInstance().addVertexArrayObject(k.vertices);
		Shader.getInstance().addMatrices(topMatrix, topMatrix.invertFull());
		
	}

	public void visit(Kameraknoten kameraknoten) {
		if (matrixStack.isEmpty())
			matrixStack.push(kameraknoten.getTransformMatrix());
		else
			matrixStack.push(matrixStack.peek().mult(kameraknoten.getTransformMatrix()));
		
		Matrix pop = matrixStack.pop();
		
		Shader.getInstance().addKamera(FactoryImpl.vecmath.getTranslation(pop), FactoryImpl.vecmath.getTranslation(pop.mult(FactoryImpl.vecmath.translationMatrix(kameraknoten.getCenter()))), kameraknoten.getUp());
		
	}
}
