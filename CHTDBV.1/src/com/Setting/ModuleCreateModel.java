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

public class ModuleCreateModel {

	ObservableList<ModuleCreateModel> ModuleItem =FXCollections.observableArrayList();
	StringProperty ModuleId,ModuleName;

	
	public ModuleCreateModel(String ModuleId,String ModuleName) {
		this.ModuleId = new SimpleStringProperty(ModuleId);
		this.ModuleName = new SimpleStringProperty(ModuleName);
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

    
    public StringProperty moduleIdProperty() {
        return ModuleId;
    }

    public StringProperty moduleNameProperty() {
        return ModuleName;
    }

    
	public ObservableList<ModuleCreateModel> getModuleItem(){
	    return(ModuleItem);
	}	
}
