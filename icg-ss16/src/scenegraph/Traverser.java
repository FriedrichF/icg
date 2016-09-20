package scenegraph;

public abstract class Traverser {
	public abstract void visit(Gruppenknoten k);

	public abstract void visit(Geometrieknoten k);

	public abstract void visit(Kameraknoten kameraknoten);

	public abstract void visit(Lichtknoten lichtknoten);
}
