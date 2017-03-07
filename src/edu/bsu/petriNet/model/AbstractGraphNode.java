package edu.bsu.petriNet.model;

public class AbstractGraphNode extends GraphElement {
	private Integer x;
	private Integer y;
	
	
	public AbstractGraphNode(Integer id, Integer x, Integer y, String name){
		super(id,name);
		this.x = x;
		this.y = y;
	}
	
	public AbstractGraphNode(Integer ID){
		super(ID);
	}
	
	public AbstractGraphNode(){
		super();
		this.x=0;
		this.y=0;
	}
	
	public AbstractGraphNode(Integer x, Integer y){
		super();
		this.x = x;
		this.y = y;
	}
	
	public AbstractGraphNode(Integer x, Integer y, String name){
		super(name);
		this.x = x;
		this.y = y;
	}
	
	public AbstractGraphNode(AbstractGraphNode n){
		super(n.getName());
		this.x = n.x;
		this.y = n.y;
	}

	/*
	public void setID(Integer iD) {
		if(this.ID != null){
			ID = iD;
		}
	}
	*/

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getX() {
		return x;
	}
	public Integer getY() {
		return y;
	}
	


}
