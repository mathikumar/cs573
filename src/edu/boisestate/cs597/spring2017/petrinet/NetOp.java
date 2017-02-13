package edu.boisestate.cs597.spring2017.petrinet;

public class NetOp {
	public enum OPTYPE {ADD, DEL, SET};
	private OPTYPE op;
	private GraphNode node;
	
	public NetOp(OPTYPE op, GraphNode node){
		this.op = op;
		this.node = node;
	}

	public OPTYPE getOp() {
		return op;
	}

	public GraphNode getNode() {
		return node;
	}
	
	

}
