package com.ContructionModule;
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

public class UnitCreateModel {

	ObservableList<UnitCreateModel> UnitItem =FXCollections.observableArrayList();
	StringProperty UnitId,UnitName;

	
	public UnitCreateModel(String UnitId,String UnitName) {
		this.UnitId = new SimpleStringProperty(UnitId);
		this.UnitName = new SimpleStringProperty(UnitName);
	}

	
	public String getUnitId() {
		return UnitId.get();
	}

	public void setUnitId(String UnitId) {
		this.UnitId.set(UnitId); 
	}

	public String getUnitName() {
		return UnitName.get();
	}

	public void setUnitName(String UnitName) {
		this.UnitName.set(UnitName); 
	}

    
    public StringProperty UnitIdProperty() {
        return UnitId;
    }

    public StringProperty UnitNameProperty() {
        return UnitName;
    }

    
	public ObservableList<UnitCreateModel> getUnitItem(){
	    return(UnitItem);
	}	
}
