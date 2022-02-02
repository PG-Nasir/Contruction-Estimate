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
import net.sourceforge.jtds.jdbc.UniqueIdentifier;

public class ItemCreateModel {
	
	ObservableList<ItemCreateModel> ProductItem =FXCollections.observableArrayList();
	StringProperty ItemId,ItemCode,Description,CategoryName,SupplierName,UnitName,Rate;

	
	public ItemCreateModel(String ItemId,String ItemCode,String Description,String CategoryName,String unitName,String Rate) {
		this.ItemId = new SimpleStringProperty(ItemId);
		this.ItemCode = new SimpleStringProperty(ItemCode);
		this.Description = new SimpleStringProperty(Description);
		this.CategoryName = new SimpleStringProperty(CategoryName);
		this.UnitName = new SimpleStringProperty(unitName);
		this.Rate = new SimpleStringProperty(Rate);
	}

	
	public String getProductId() {
		return ItemId.get();
	}

	public void setProductId(String ItemId) {
		this.ItemId.set(ItemId); 
	}

	public String getItemCode() {
		return ItemCode.get();
	}

	public void setItemCode(String ItemCode) {
		this.ItemCode.set(ItemCode); 
	}
	
	public String getDescription() {
		return Description.get();
	}

	public void setDescription(String Description) {
		this.Description.set(Description); 
	}

	public String getRate() {
		return Rate.get();
	}
	
	public void setRate(String rate) {
		this.Rate.set(rate); 
	}
	
	public String getCategoryName() {
		return CategoryName.get();
	}

	public void setCategoryName(String CategoryName) {
		this.CategoryName.set(CategoryName); 
	}
	

	
	public String getUnitName() {
		return UnitName.get();
	}

	public void setUnitName(String unitName) {
		this.UnitName.set(unitName); 
	}

    public StringProperty ProductIdProperty() {
        return ItemId;
    }

    public StringProperty ItemCodeProperty() {
        return ItemCode;
    }
    
    public StringProperty RateProperty() {
        return Rate;
    }
    
    public StringProperty CategoryNameProperty() {
        return CategoryName;
    }
    
    public StringProperty UnitNameProperty() {
        return UnitName;
    }


	public ObservableList<ItemCreateModel> getProductItem(){
	    return(ProductItem);
	}	
}
