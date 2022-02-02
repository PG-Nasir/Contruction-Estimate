package com.ContructionModule;

import java.sql.ResultSet;
import java.text.DecimalFormat;

import com.ComShare.DBUtil;
import com.ComShare.FxComboBox;
import com.ComShare.Notification;
import com.ComShare.NumberField;
import com.ComShare.TextRead;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

public class AditionalItemModel {

	ObservableList<AditionalItemModel> aditionalItemList =FXCollections.observableArrayList();
	private BooleanProperty CheckAditionalStatus;
	public  FxComboBox cmbItemCode;
	
	public TextRead txtRate;
	DecimalFormat df = new DecimalFormat("#.000");
	public AditionalItemModel(boolean aditionalStatus,FxComboBox ItemCode,String ItemCodeValue,String rate) {
		this.cmbItemCode =new FxComboBox<>();
		if(ItemCode!=null){
			this.cmbItemCode.data.addAll(ItemCode.data);
			this.cmbItemCode.setValue(ItemCodeValue);
		}
		this.txtRate = new TextRead();
		this.txtRate.setText(rate);
		this.CheckAditionalStatus=new SimpleBooleanProperty (aditionalStatus);
		
		setItemCodeChange();
		cmbItemCode.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String BrandName) {
				setItemCodeChange();
				
			}    
		});
		
	}
	
	private void setItemCodeChange(){
		if(cmbItemCode.getValue()!=null ){
			double TRate=getUnitRateItem();
			txtRate.setText(df.format(TRate));
		}
	}
	
	private double getUnitRateItem(){
		
		double Rate=0;
		
		try {
			String selectStmt = "select Rate from tbiteminfo where ItemCode='"+cmbItemCode.getValue()+"'";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Rate=Double.parseDouble(rs.getString("Rate"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Rate;
	}
	
    public FxComboBox getAditionalItemCode() {
        return cmbItemCode;
    }
    public void setAditionalItemCode(FxComboBox itemCode) {
        this.cmbItemCode=itemCode;
    }
    
    public TextRead getRate() {
        return txtRate;
    }
    public void setRate(TextRead rate) {
        this.txtRate=rate;
    }
    
    public boolean isCheckAditonalStatus() {
        return CheckAditionalStatus.get();
    }
    
    public void setCheckAditonalStatus(boolean CheckStatus) {
        this.CheckAditionalStatus.set(CheckStatus);
    }

    public BooleanProperty CheckAditonalStatusProperty() {
        return CheckAditionalStatus;
    }
    
	public ObservableList<AditionalItemModel> getAditionalItemCodeList(){
	    return(aditionalItemList);
	}
}
