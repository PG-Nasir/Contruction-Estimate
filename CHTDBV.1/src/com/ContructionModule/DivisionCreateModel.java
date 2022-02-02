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

public class DivisionCreateModel {

	ObservableList<DivisionCreateModel> DivisionItem =FXCollections.observableArrayList();
	StringProperty DivisionId,DivisionCode,Description;

	
	public DivisionCreateModel(String DivisionId,String DivisionName,String Description) {
		this.DivisionId = new SimpleStringProperty(DivisionId);
		this.DivisionCode = new SimpleStringProperty(DivisionName);
		this.Description = new SimpleStringProperty(Description);
	}

	
	public String getDivisionId() {
		return DivisionId.get();
	}

	public void setDivisionId(String DivisionId) {
		this.DivisionId.set(DivisionId); 
	}

	public String getDivisionCode() {
		return DivisionCode.get();
	}

	public void setDivisionCode(String divisionCode) {
		this.DivisionCode.set(divisionCode); 
	}

	public String getDescription() {
		return Description.get();
	}

	public void setDescription(String description) {
		this.Description.set(description); 
	}
	
    public StringProperty divisionIdProperty() {
        return DivisionId;
    }

    public StringProperty DivisionCodeProperty() {
        return DivisionCode;
    }
    
    
    public StringProperty DescriptionProperty() {
        return Description;
    }
    
	public ObservableList<DivisionCreateModel> getModuleItem(){
	    return(DivisionItem);
	}	
}
