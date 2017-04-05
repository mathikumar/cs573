package edu.bsu.petriNet.helper;

import edu.bsu.petriNet.model.PetriNet;

public class BranchedHistoryProvider {
	
	private HistoryProvider mainHistory, branchHistory;
	private PetriNet current;
	Boolean currentOnMain;
	
	public BranchedHistoryProvider(){
		this.mainHistory = new HistoryProvider();
		this.branchHistory = new HistoryProvider();
	}
	
	public void checkPoint(PetriNet pnet, boolean branch){
		if(branch){
			this.branchHistory.savePetriNetCheckPoint(pnet);
			currentOnMain = false;
		}else{
			this.mainHistory.savePetriNetCheckPoint(pnet);
			this.branchHistory.reset();
			currentOnMain = true;
		}
		this.current = pnet;
	}
	
	public void reset(){
		this.mainHistory.reset();
		this.branchHistory.reset();
	}
	
	public Boolean undoBranch(){
		if(this.mainHistory.hasState()){
			this.branchHistory.reset();
			this.current = this.mainHistory.getCurretPetriNet();
			return true;
		}
		return false;
	}
	
	public PetriNet getCurrentPetriNet(){
		return this.current;
	}
	
	public Boolean isUndoPossible(){
		return this.mainHistory.isUndoPossible() || 
				this.branchHistory.isUndoPossible() ||
				(!this.currentOnMain && this.mainHistory.hasState());
	}
	
	public Boolean isRedoPossible(){
		return this.mainHistory.isRedoPossible() || 
				this.branchHistory.isRedoPossible() ||
				(this.currentOnMain && this.branchHistory.hasState());
	}
	
	public Boolean undo(){
		if(this.currentOnMain){
			if(this.mainHistory.isUndoPossible()){
				System.out.println("onMain ; undo");
				this.mainHistory.undo();
				this.current = this.mainHistory.getCurretPetriNet();
				return true;
			}else{
				System.out.println("onMain ; no-undo");
				return false;
			}
		}else{
			if(this.branchHistory.isUndoPossible()){
				System.out.println("onBranch ; undo");
				this.branchHistory.undo();
				this.current = this.branchHistory.getCurretPetriNet();
				return true;
			}else if(this.mainHistory.hasState()){
				System.out.println("onBranch ; no-undo -> main");
				this.current = this.mainHistory.getCurretPetriNet();
				this.currentOnMain = true;
				return true;
			}else{
				System.out.println("onBranch ; no-undo; no-main");
				return false;
			}
			
		}
	}
	
	public Boolean redo(){
		if(this.currentOnMain){
			if(this.mainHistory.isRedoPossible()){
				this.mainHistory.redo();
				this.current = this.mainHistory.getCurretPetriNet();
				return true;
			}else if(this.branchHistory.hasState()){
				this.current = this.branchHistory.getCurretPetriNet();
				this.currentOnMain = false;
				return true;
			}else{
				return false;
			}
			
		}else{
			if(this.branchHistory.isRedoPossible()){
				this.branchHistory.redo();
				this.current = this.branchHistory.getCurretPetriNet();
				return true;
			}else{
				return false;
			}
			
		}
	}

}
