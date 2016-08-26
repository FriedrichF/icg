package scenegraph;

import ogl.app.Input;

public abstract class Entity {
	
	private String name;
	private Knoten knoten;

	public Entity(String name, Knoten knoten){
		this.name = name;
		this.knoten = knoten;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Knoten getKnoten() {
		return knoten;
	}

	public void setKnoten(Knoten knoten) {
		this.knoten = knoten;
	}

	public abstract void simulate(float elapsed, Input input);
}
