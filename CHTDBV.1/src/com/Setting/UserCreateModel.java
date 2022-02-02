package com.Setting;


import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class UserCreateModel {
	public ArrayList<String> ItemProductList=new ArrayList<String>();
	ObservableList<UserCreateModel> ModuleItem =FXCollections.observableArrayList();
	StringProperty ModuleId,ModuleName;
	private BooleanProperty CheckStatus;
	
	public UserCreateModel(String ModuleId,String ModuleName,boolean Status) {
		this.ModuleId = new SimpleStringProperty(ModuleId);
		this.ModuleName = new SimpleStringProperty(ModuleName);
		this.CheckStatus=new SimpleBooleanProperty (Status);
	}

	
	public String getModuleId() {
		return ModuleId.get();
	}

	public void setModuleId(String moduleId) {
		this.ModuleId.set(moduleId); 
	}

	public String getModuleName() {
		return ModuleName.get();
	}

	public void setModuleName(String moduleName) {
		this.ModuleName.set(moduleName); 
	}

    public boolean isCheckStatus() {
        return CheckStatus.get();
    }
    
    public StringProperty moduleIdProperty() {
        return ModuleId;
    }

    public StringProperty moduleNameProperty() {
        return ModuleName;
    }
		
    public void setCheckStatus(boolean CheckStatus) {
        this.CheckStatus.set(CheckStatus);
    }

    public BooleanProperty CheckStatusProperty() {
        return CheckStatus;
    }
    
	public ObservableList<UserCreateModel> getModuleItem(){
	    return(ModuleItem);
	}	
}
