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

public class ModuleItemCreateModel {

	ObservableList<ModuleItemCreateModel> ModuleItem =FXCollections.observableArrayList();
	StringProperty ModuleItemId,ModuleName,ModuleItemName;

	
	public ModuleItemCreateModel(String ModuleItemId,String ModuleName,String ModuleItemName) {
		this.ModuleItemId = new SimpleStringProperty(ModuleItemId);
		this.ModuleName = new SimpleStringProperty(ModuleName);
		this.ModuleItemName = new SimpleStringProperty(ModuleItemName);
	}

	
	public String getModuleItemId() {
		return ModuleItemId.get();
	}

	public void setModuleItemId(String moduleItemId) {
		this.ModuleItemId.set(moduleItemId); 
	}
	
	public String getModuleItemName() {
		return ModuleItemName.get();
	}

	public void setModuleItemName(String moduleItemName) {
		this.ModuleItemName.set(moduleItemName); 
	}

	public String getModuleName() {
		return ModuleName.get();
	}

	public void setModuleName(String moduleName) {
		this.ModuleName.set(moduleName); 
	}

    
    public StringProperty moduleItemIdProperty() {
        return ModuleItemId;
    }

    public StringProperty moduleItemNameProperty() {
        return ModuleItemName;
    }
    
    public StringProperty moduleNameProperty() {
        return ModuleName;
    }

    
	public ObservableList<ModuleItemCreateModel> getModuleItem(){
	    return(ModuleItem);
	}	
}
