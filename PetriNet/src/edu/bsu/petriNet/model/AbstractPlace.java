package edu.bsu.petriNet.model;

import edu.bsu.petriNet.model.AbstractGraphNode;

public class AbstractPlace extends AbstractGraphNode {
	private Integer tokens;

	public AbstractPlace(Integer x, Integer y, Integer tokens, String name) {
		super(x, y, name);
		this.tokens=tokens;
	}

	public AbstractPlace(Integer x, Integer y, Integer tokens) {
		super(x, y);
		this.tokens=tokens;
	}

	public Integer getTokens() {
		return tokens;
	}

	public void setTokens(Integer tokens) {
		this.tokens = tokens;
	}
	
	
	
	

}
