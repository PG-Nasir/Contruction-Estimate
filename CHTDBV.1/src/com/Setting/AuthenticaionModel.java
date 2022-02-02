package com.Setting;


import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AuthenticaionModel {
	public ArrayList<String> ItemProductList=new ArrayList<String>();
	public ObservableList<AuthenticaionModel> ModuleItem =FXCollections.observableArrayList();
	StringProperty ModuleId,ModuleName,ModuleItemName;
	private BooleanProperty CheckEntryStatus,CheckChangeStatus,CheckClearStatus,CheckRefundStatus,CheckBlockStatus;

	public AuthenticaionModel(String ModuleId,String ModuleName,String ModuleItemName,boolean checkStatus,boolean changeStatus,boolean clearStatus,boolean refundStatus,boolean blockStatus) {
		this.ModuleId = new SimpleStringProperty(ModuleId);
		this.ModuleName = new SimpleStringProperty(ModuleName);
		this.ModuleItemName = new SimpleStringProperty(ModuleItemName);
		this.CheckEntryStatus=new SimpleBooleanProperty (checkStatus);
		this.CheckChangeStatus=new SimpleBooleanProperty (changeStatus);
		this.CheckClearStatus=new SimpleBooleanProperty (clearStatus);
		this.CheckRefundStatus=new SimpleBooleanProperty (refundStatus);
		this.CheckBlockStatus=new SimpleBooleanProperty (blockStatus);
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

	public String getModuleItemName() {
		return ModuleItemName.get();
	}

	public void setModuleItemName(String moduleItemName) {
		this.ModuleItemName.set(moduleItemName); 
	}
	
	
    public boolean isCheckEntryStatus() {
        return CheckEntryStatus.get();
    }
    public boolean isCheckClearStatus() {
        return CheckClearStatus.get();
    }
    public boolean isCheckChangeStatus() {
        return CheckChangeStatus.get();
    }
    public boolean isCheckRefundStatus() {
        return CheckRefundStatus.get();
    }
    public boolean isCheckBlockStatus() {
        return CheckBlockStatus.get();
    }
    public StringProperty moduleIdProperty() {
        return ModuleId;
    }

    public StringProperty moduleNameProperty() {
        return ModuleName;
    }
		
    public void setCheckEntryStatus(boolean CheckStatus) {
        this.CheckEntryStatus.set(CheckStatus);
    }

    public BooleanProperty CheckEntryStatusProperty() {
        return CheckEntryStatus;
    }
    
    
    public void setCheckChangeStatus(boolean CheckStatus) {
        this.CheckChangeStatus.set(CheckStatus);
    }

    public BooleanProperty CheckChangeStatusProperty() {
        return CheckChangeStatus;
    }
    
    public void setCheckClearStatus(boolean CheckStatus) {
        this.CheckClearStatus.set(CheckStatus);
    }

    public BooleanProperty CheckClearStatusProperty() {
        return CheckClearStatus;
    }
    
    public void setCheckRefundStatus(boolean CheckStatus) {
        this.CheckRefundStatus.set(CheckStatus);
    }

    public BooleanProperty CheckRefundStatusProperty() {
        return CheckRefundStatus;
    }
    
    
    public void setCheckBlockStatus(boolean CheckStatus) {
        this.CheckBlockStatus.set(CheckStatus);
    }

    public BooleanProperty CheckBlockStatusProperty() {
        return CheckBlockStatus;
    }
      

	public ObservableList<AuthenticaionModel> getModuleItem(){
	    return(ModuleItem);
	}	
}
