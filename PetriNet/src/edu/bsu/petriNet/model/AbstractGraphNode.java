package edu.bsu.petriNet.model;

public class AbstractGraphNode {
	private Integer ID;
	private Integer x;
	private Integer y;
	private String name;
	
	public AbstractGraphNode(Integer ID){
		this.ID = ID;
	}
	
	public AbstractGraphNode(){
		this.x=0;
		this.y=0;
		this.name="";
	}
	
	public AbstractGraphNode(Integer x, Integer y){
		this.x = x;
		this.y = y;
	}
	
	public AbstractGraphNode(Integer x, Integer y, String name){
		this.x = x;
		this.y = y;
		this.name=name;
	}
	
	public AbstractGraphNode(AbstractGraphNode n){
		this.x = n.x;
		this.y = n.y;
		this.name = n.name;
	}
	
	public void setID(Integer iD) {
		if(this.ID != null){
			ID = iD;
		}
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getID() {
		return ID;
	}
	public Integer getX() {
		return x;
	}
	public Integer getY() {
		return y;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof AbstractGraphNode){
			return this.ID == ((AbstractGraphNode)o).ID;
		}
		return false;
	}

}
