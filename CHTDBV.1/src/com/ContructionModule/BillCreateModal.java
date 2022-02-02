package com.ContructionModule;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.ComShare.DBUtil;
import com.ComShare.FxComboBox;
import com.ComShare.Notification;
import com.ComShare.NumberField;
import com.ComShare.TextRead;
import com.Setting.AuthenticaionModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class BillCreateModal {
	ObservableList<BillCreateModal> ItemCodeList =FXCollections.observableArrayList();
	StringProperty SL,ItemCode,ItemDescription,Unit,Amount,Qty;
	public NumberField txtExecuteExecuteQty,txtRate;
	@SuppressWarnings("unchecked")

	
	CheckBox checkBillConfrim;
	private BooleanProperty CheckBillConfrimStatus;

	DecimalFormat df = new DecimalFormat("#.000");
	@SuppressWarnings("unchecked")
	
	String ProjectId="",BillId="";
	public BillCreateModal(String Sl,CheckBox CheckBillConfrim, boolean checkbillConfrimStatus,String itemCode,String itemdescription,String unit,String Qty,String ExecuteQty,String rate,String amount,String projectId,String billId) {

		this.SL = new SimpleStringProperty(Sl);
		this.ItemCode = new SimpleStringProperty(itemCode);
		this.ItemDescription = new SimpleStringProperty(itemdescription);
		this.Unit = new SimpleStringProperty(unit);
		this.Amount = new SimpleStringProperty(amount);
	
		
		this.checkBillConfrim =new CheckBox();
		this.checkBillConfrim.setSelected(checkbillConfrimStatus);

		checkBillConfrim.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
				if(new_val){
					txtExecuteExecuteQty.setText("0");
					txtExecuteExecuteQty.setEditable(true);
				}
				else{
					txtExecuteExecuteQty.setText("0");
					txtExecuteExecuteQty.setEditable(true);
				}

			}
		});


		this.Qty = new SimpleStringProperty(Qty);
		
		this.txtExecuteExecuteQty = new NumberField();
		this.txtExecuteExecuteQty.setText(ExecuteQty);
		this.txtExecuteExecuteQty.setPrefWidth(70);
		
		
		this.txtRate = new NumberField();
		this.txtRate.setText(rate);
		this.txtRate.setPrefWidth(70);
		
		
		this.ProjectId=projectId;
		this.BillId=billId;
		
		setRateAutoHit();
		
		//this.CheckBillConfrimStatus=new SimpleBooleanProperty (checkbillConfrimStatus);
		//this.checkBillConfrim.setSelected(checkbillConfrimStatus);
	}

	private void setRateAutoHit(){
		txtExecuteExecuteQty.textProperty().addListener((observable, oldValue, newValue) -> {
			
			double qty=Double.parseDouble(getQty().trim().toString().equals("0")?"0":getQty().trim().toString());
			double exeqty=Double.parseDouble(getExecuteQty().getText().trim().toString().isEmpty()?"0":getExecuteQty().getText().trim().toString());
		
			double totalInputQty=exeqty+getPreviousExecuteQty();
			
			
			if(qty>=totalInputQty){
				double Amount=Double.parseDouble(getRate().getText().trim().toString().equals("0")?"0":getRate().getText().trim().toString())*Double.parseDouble(txtExecuteExecuteQty.getText().trim().toString().isEmpty()?"0":txtExecuteExecuteQty.getText().trim().toString());
				setAmount(df.format(Amount));
			}
			else{
				txtExecuteExecuteQty.setText("");
				setAmount(df.format(0));
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Executed Quantity Can't be more than qty");
			}


		});
		
		txtRate.textProperty().addListener((observable, oldValue, newValue) -> {
			
			double qty=Double.parseDouble(getQty().trim().toString().equals("0")?"0":getQty().trim().toString());
			double exeqty=Double.parseDouble(getExecuteQty().getText().trim().toString().isEmpty()?"0":getExecuteQty().getText().trim().toString());
		
			double totalInputQty=exeqty+getPreviousExecuteQty();
			
			
			if(qty>=totalInputQty){
				double Amount=Double.parseDouble(getRate().getText().trim().toString().equals("0")?"0":getRate().getText().trim().toString())*Double.parseDouble(txtExecuteExecuteQty.getText().trim().toString().isEmpty()?"0":txtExecuteExecuteQty.getText().trim().toString());
				setAmount(df.format(Amount));
			}
			else{
				txtExecuteExecuteQty.setText("");
				setAmount(df.format(0));
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Executed Quantity Can't be more than qty");
			}


		});
	}
	
	private double getPreviousExecuteQty(){
		
		double qty =0;
		try {
			String selectStmt = "select IFNULL(sum(ExecuteQty),0) as ExecuteQty from TbProjectBillDetails where  ProjectId='"+ProjectId+"' and BillId!='"+BillId+"' and ItemCode='"+ItemCode.getValue()+"'";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				qty=qty+Double.parseDouble(rs.getString("ExecuteQty"));
			}
			System.out.println("qty "+qty);
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return qty;
	}
	
	public String getSL() {
		return SL.get();
	}

	public void setSL(String sL) {
		this.SL.set(sL); 
	}

	public StringProperty SLProperty() {
		return SL;
	}
	
	
	public String getItemCode() {
		return ItemCode.get();
	}

	public void setItemCode(String itemCode) {
		this.ItemCode.set(itemCode); 
	}

	public StringProperty ItemCodeProperty() {
		return ItemCode;
	}


	public String getDescription() {
		return ItemDescription.get();
	}

	public void setDescription(String itemCode) {
		this.ItemDescription.set(itemCode); 
	}

	public StringProperty DescriptionProperty() {
		return ItemDescription;
	}
	
	public String getUnit() {
		return Unit.get();
	}

	public void setUnit(String unit) {
		this.Unit.set(unit); 
	}

	public StringProperty UnitProperty() {
		return Unit;
	}
	
	
	
	public String getQty() {
		return Qty.get();
	}

	public void setQty(String qty) {
		this.Qty.set(qty); 
	}

	public StringProperty QtyProperty() {
		return Qty;
	}
	

	public String getAmount() {
		return Amount.get();
	}

	public void setAmount(String amount) {
		this.Amount.set(amount); 
	}

	public StringProperty AmountProperty() {
		return Amount;
	}
	
	
	public CheckBox getBillConfrimCheck() {
		return checkBillConfrim;
	}

	public void setBillConfrimCheck(CheckBox billConfrim) {
		this.checkBillConfrim=billConfrim;
	}

	
	public NumberField getExecuteQty() {
		return txtExecuteExecuteQty;
	}
	public void setExecuteQty(NumberField ExecuteQty) {
		this.txtExecuteExecuteQty=ExecuteQty;
	}
	
	public NumberField getRate() {
		return txtRate;
	}

	public void setRate(NumberField rate) {
		this.txtRate=rate;
	}

	
	public CheckBox getBillConfrim() {
		return checkBillConfrim;
	}
	public void setBillConfrim(CheckBox billConfrim) {
		this.checkBillConfrim=billConfrim;
	}
	
/*	public boolean isCheckBillConfrimStatus() {
		return CheckBillConfrimStatus.get();
	}

	public void setCheckBillConfrimStatus(boolean CheckStatus) {
		this.CheckBillConfrimStatus.set(CheckStatus);
	}

	public BooleanProperty CheckBillConfrimStatusProperty() {
		return CheckBillConfrimStatus;
	}*/

	
	public ObservableList<BillCreateModal> getItemCodeList(){
		return(ItemCodeList);
	}

}
