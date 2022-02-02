package com.Setting;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import com.ComShare.CommonButton;
import com.ComShare.DBUtil;
import com.ComShare.FocusMoveByEnter;
import com.ComShare.FxComboBox;
import com.ComShare.Notification;
import com.ComShare.SessionBean;
import com.ComShare.TextRead;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class AuthenticationCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,960, 400);;
	public Stage mainItemStage=new Stage();
	Button btnFind=new Button("Find");
	Button btnConfirm=new Button("Confirm");
	private TableView<AuthenticaionModel> table = new TableView<AuthenticaionModel>();
	FxComboBox cmbUserFind=new FxComboBox();
	FxComboBox cmbModuelName=new FxComboBox();
	CheckBox checkAllModule;

	AuthenticaionModel model=new AuthenticaionModel("","" ,"", false,false,false,false,false);
	boolean insert=true,update=false,find=false;


	private CheckBox headerEntryCheckbox;
	private boolean headerEntryCheckboxAction;


	private CheckBox headerChangeCheckbox;
	private boolean headerChangeCheckboxAction;

	private CheckBox headerClearCheckbox;
	private boolean headerClearCheckboxAction;

	private CheckBox headerRefundCheckbox;
	private boolean headerRefundCheckboxAction;

	private CheckBox headerBlockCheckbox;
	private boolean headerBlockCheckboxAction;

	int accessModuleCount=0;
	public AuthenticationCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("User Authentication");
		addCmp();
		btnActionEvent();
		loadUserName();
	}
	
	@SuppressWarnings("unchecked")
	private void btnActionEvent(){

		checkAllModule.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(checkAllModule.isSelected()){
					cmbModuelName.setDisable(true);
					cmbModuelName.setValue(null);
				}
				else{
					cmbModuelName.setDisable(false);
					cmbModuelName.setValue(null);
				}
			}
		});
		cmbUserFind.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String BrandName) {
				if(cmbUserFind.getValue()!=null ){
					loadModuleName();
				}
			}    
		});
		btnFind.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				headerEntryCheckbox.setSelected(false);
				headerChangeCheckbox.setSelected(false);
				headerClearCheckbox.setSelected(false);
				headerRefundCheckbox.setSelected(false);	
				headerBlockCheckbox.setSelected(false);
				btnFindEvent();
			}
		});

		btnConfirm.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnConfirmEvent();
			}
		});
		headerEntryCheckbox.setOnAction(event -> {

			ObservableList<AuthenticaionModel> data = table.getItems();
			headerEntryCheckboxAction = true;

			for (int i = 0; i < data.size(); i++) {

				if (headerEntryCheckbox.isSelected()) {
					data.get(i).setCheckEntryStatus(true);
				}
				else if (! headerEntryCheckbox.isSelected()) {
					data.get(i).setCheckEntryStatus(false);
				}
				else {
					// Do nothing here
				}
			}

			headerEntryCheckboxAction = false;
		});

		headerChangeCheckbox.setOnAction(event -> {

			ObservableList<AuthenticaionModel> data = table.getItems();
			headerChangeCheckboxAction = true;

			for (int i = 0; i < data.size(); i++) {

				if (headerChangeCheckbox.isSelected()) {
					data.get(i).setCheckChangeStatus(true);
				}
				else if (! headerChangeCheckbox.isSelected()) {
					data.get(i).setCheckChangeStatus(false);
				}
				else {
					// Do nothing here
				}
			}

			headerChangeCheckboxAction = false;
		});

		headerClearCheckbox.setOnAction(event -> {

			ObservableList<AuthenticaionModel> data = table.getItems();
			headerClearCheckboxAction = true;

			for (int i = 0; i < data.size(); i++) {

				if (headerClearCheckbox.isSelected()) {
					data.get(i).setCheckClearStatus(true);
				}
				else if (! headerClearCheckbox.isSelected()) {
					data.get(i).setCheckClearStatus(false);
				}
				else {
					// Do nothing here
				}
			}

			headerClearCheckboxAction = false;
		});

		headerRefundCheckbox.setOnAction(event -> {

			ObservableList<AuthenticaionModel> data = table.getItems();
			headerRefundCheckboxAction = true;

			for (int i = 0; i < data.size(); i++) {

				if (headerRefundCheckbox.isSelected()) {
					data.get(i).setCheckRefundStatus(true);
				}
				else if (! headerRefundCheckbox.isSelected()) {
					data.get(i).setCheckRefundStatus(false);
				}
				else {
					// Do nothing here
				}
			}

			headerRefundCheckboxAction = false;
		});

		headerBlockCheckbox.setOnAction(event -> {

			ObservableList<AuthenticaionModel> data = table.getItems();
			headerBlockCheckboxAction = true;

			for (int i = 0; i < data.size(); i++) {

				if (headerBlockCheckbox.isSelected()) {
					data.get(i).setCheckBlockStatus(true);
				}
				else if (! headerBlockCheckbox.isSelected()) {
					data.get(i).setCheckBlockStatus(false);

				}
				else {
					// Do nothing here
				}
			}

			headerBlockCheckboxAction = false;

		});
	}



	private void btnFindEvent(){

		if(checkDoplicateName(cmbUserFind.getValue().toString())){
			if(checkAllModule.isSelected() && cmbModuelName.getValue()==null){
				table.getItems().clear();
				model.getModuleItem().clear();
				try {
					
					String userId=getUserId(cmbUserFind.getValue().toString());
					if(checkUserWiseAlreadyExit(userId)){
						setCheckBoxFalseValue();
						String selectStmt = "select ModuleItemId,(select ModuleName from TbModuleInfo where ModuleId=TbAuthentication.ModuleId) as ModuleName,(select ModuleItemName from TbModuleItemInfo where ModuleItemId=TbAuthentication.ModuleItemId) as ModuleItemName,Entry,Change,Clear,Refund,Block from TbAuthentication where userId='"+userId+"' order by ModuleId ";
						ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
						while(rs.next()){
							
							model.getModuleItem().add(new AuthenticaionModel(rs.getString("ModuleItemId"),rs.getString("moduleName"),rs.getString("ModuleItemName"),(rs.getString("Entry").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false)),rs.getString("Change").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false),rs.getString("Clear").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false),rs.getString("Refund").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false),rs.getString("Block").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false)));

						}
						table.setItems(getDataRoutine());
						UpdateHeaderCheckbox();
					}
					else{
						

						setCheckBoxFalseValue();
						String selectStmt = "select b.ModuleItemId,a.moduleName,b.ModuleItemName from tblogindetails a join TbModuleItemInfo b  on a.moduleId=b.ModuleId where a.userId=(select user_id from TbLogin where username='"+cmbUserFind.getValue()+"') order by a.moduleId ";
						System.out.print(selectStmt);
						ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
						while(rs.next()){

							model.getModuleItem().add(new AuthenticaionModel(rs.getString("ModuleItemId"),rs.getString("moduleName"),rs.getString("ModuleItemName"),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)));

						}
						table.setItems(getDataRoutine());
						UpdateHeaderCheckbox();
					}
					

				} catch (Exception e) {
					e.printStackTrace();
					new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
				}
			}
			else{
				if(cmbModuelName.getValue()!=null){
					table.getItems().clear();
					model.getModuleItem().clear();
					
					String userId=getUserId(cmbUserFind.getValue().toString());
					String moduleId=getModuleId(cmbModuelName.getValue().toString());

					
					try {
						if(checkUserWiseModuleItemAlreadyExit(userId,moduleId)){
							setCheckBoxFalseValue();
							String selectStmt = "select ModuleItemId,(select ModuleName from TbModuleInfo where ModuleId=TbAuthentication.ModuleId) as ModuleName,(select ModuleItemName from TbModuleItemInfo where ModuleItemId=TbAuthentication.ModuleItemId) as ModuleItemName,Entry,Change,Clear,Refund,Block from TbAuthentication where userId='"+userId+"' and ModuleId='"+moduleId+"' order by ModuleId ";
							System.out.print(selectStmt);
							ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
							while(rs.next()){

								model.getModuleItem().add(new AuthenticaionModel(rs.getString("ModuleItemId"),rs.getString("moduleName"),rs.getString("ModuleItemName"),(rs.getString("Entry").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false)),rs.getString("Change").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false),rs.getString("Clear").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false),rs.getString("Refund").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false),rs.getString("Block").equalsIgnoreCase("True")?new Boolean(true):new Boolean(false)));
							}

							table.setItems(getDataRoutine());
							UpdateHeaderCheckbox();
						}
						else{
							setCheckBoxFalseValue();
							String selectStmt = "select b.ModuleItemId,a.moduleName,b.ModuleItemName from tblogindetails a join TbModuleItemInfo b  on a.moduleId=b.ModuleId where a.userId=(select user_id from TbLogin where username='"+cmbUserFind.getValue()+"') and a.moduleId=(select ModuleId from TbModuleInfo where ModuleName='"+cmbModuelName.getValue().toString()+"') order by a.moduleId ";
							System.out.print(selectStmt);
							ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
							while(rs.next()){

								model.getModuleItem().add(new AuthenticaionModel(rs.getString("ModuleItemId"),rs.getString("moduleName"),rs.getString("ModuleItemName"),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)));
							}

							table.setItems(getDataRoutine());
							UpdateHeaderCheckbox();
						}
						

					} catch (Exception e) {
						e.printStackTrace();
						new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
					}
				}
				else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Provide Module Name!!");
				}
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid Username never allow");
		}
	}
	private void btnConfirmEvent(){

		Alert alert = new Alert(AlertType.CONFIRMATION, "Are You Sure To Confrim " +" ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES) {
			int count=table.getItems().size();
			if(count>0){
				for(int a=0;a<table.getItems().size();a++){
					
					String ModuleId=getModuleId(table.getItems().get(a).getModuleName());
					String ModuleItemId=table.getItems().get(a).getModuleId();
					String Entry=table.getItems().get(a).CheckEntryStatusProperty().toString().substring(table.getItems().get(a).CheckEntryStatusProperty().toString().indexOf(":")+1, table.getItems().get(a).CheckEntryStatusProperty().toString().length()-1);
					String Change=table.getItems().get(a).CheckChangeStatusProperty().toString().substring(table.getItems().get(a).CheckChangeStatusProperty().toString().indexOf(":")+1, table.getItems().get(a).CheckChangeStatusProperty().toString().length()-1);
					String Clear=table.getItems().get(a).CheckClearStatusProperty().toString().substring(table.getItems().get(a).CheckClearStatusProperty().toString().indexOf(":")+1, table.getItems().get(a).CheckClearStatusProperty().toString().length()-1);
					String Refund=table.getItems().get(a).CheckRefundStatusProperty().toString().substring(table.getItems().get(a).CheckRefundStatusProperty().toString().indexOf(":")+1, table.getItems().get(a).CheckRefundStatusProperty().toString().length()-1);
					String Block=table.getItems().get(a).CheckBlockStatusProperty().toString().substring(table.getItems().get(a).CheckBlockStatusProperty().toString().indexOf(":")+1, table.getItems().get(a).CheckBlockStatusProperty().toString().length()-1);
					
					try {
						
						String userId=getUserId(cmbUserFind.getValue().toString());
						
						if(!checkUserWiseAlearyExist(userId,ModuleId,ModuleItemId)){
							String sql="insert into TbAuthentication (userId,ModuleId"
									+ ",ModuleItemId"
									+ ",Entry,"
									+ "Change,"
									+ "Clear,"
									+ "Refund,"
									+ "Block,"
									+ "EntryTime,"
									+ "CreateBy) values ('"+userId+"','"+ModuleId+"','"+ModuleItemId+"','"+Entry.trim()+"','"+Change.trim()+"','"+Clear.trim()+"','"+Refund.trim()+"','"+Block.trim()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"')";
							DBUtil.dbExecuteUpdate(sql);
							
							String Udsql="insert into TbUdAuthentication (userId,ModuleId"
									+ ",ModuleItemId"
									+ ",Entry,"
									+ "Change,"
									+ "Clear,"
									+ "Refund,"
									+ "Block,"
									+ "EntryTime,"
									+ "CreateBy,Flag) values ('"+userId+"','"+ModuleId+"','"+ModuleItemId+"','"+Entry.trim()+"','"+Change.trim()+"','"+Clear.trim()+"','"+Refund.trim()+"','"+Block.trim()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','New')";
							DBUtil.dbExecuteUpdate(Udsql);
							
							
						}
						else{
							
							String sql="update TbAuthentication set ModuleId='"+ModuleId+"',ModuleItemId='"+ModuleItemId+"',Entry='"+Entry.trim()+"',Change='"+Change.trim()+"',Clear='"+Clear.trim()+"',Refund='"+Refund.trim()+"',Block='"+Block.trim()+"',EntryTime=CURRENT_TIMESTAMP,CreateBy='"+sessionBeam.getUserId()+"' where userId='"+userId+"' and ModuleId='"+ModuleId+"' and ModuleItemId='"+ModuleItemId+"'";
							DBUtil.dbExecuteUpdate(sql);
							
							String Udsql="insert into TbUdAuthentication (userId,ModuleId"
									+ ",ModuleItemId"
									+ ",Entry,"
									+ "Change,"
									+ "Clear,"
									+ "Refund,"
									+ "Block,"
									+ "EntryTime,"
									+ "CreateBy,Flag) values ('"+userId+"','"+ModuleId+"','"+ModuleItemId+"','"+Entry.trim()+"','"+Change.trim()+"','"+Clear.trim()+"','"+Refund.trim()+"','"+Block.trim()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','New')";
							DBUtil.dbExecuteUpdate(Udsql);
							
							
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
					}
				}
				
				new Notification(Pos.TOP_CENTER, "Information graphic", "Information!", "Authentication Complete Succesfully");
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Provide Module Item Name!!");
			}
		}
		
	}
	private boolean checkUserWiseAlearyExist(String User,String ModuleId,String ModuleItemId){
		
		try {
			String selectStmt = "select * from TbAuthentication where UserId='"+User+"' and ModuleId='"+ModuleId+"' and ModuleItemId='"+ModuleItemId+"' ";
			System.out.println(selectStmt);
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false;
	}
	private boolean checkUserWiseAlreadyExit(String User){
		try {
			String selectStmt = "select * from TbAuthentication where UserId='"+User+"' ";
			System.out.println(selectStmt);
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false;
	}
	private boolean checkUserWiseModuleItemAlreadyExit(String User,String ModuleId){
		try {
			String selectStmt = "select * from TbAuthentication where UserId='"+User+"' and ModuleId='"+ModuleId+"' ";
			System.out.println(selectStmt);
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false;
	}
	private String getUserId(String UserName){
		String Id="";
		try {
			String selectStmt = "select user_id from TbLogin where username='"+UserName+"'";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("user_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}
	private String getModuleId(String ModuleName){
		String Id="";
		try {
			String selectStmt = "select ModuleId from TbModuleInfo where ModuleName='"+ModuleName+"'";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("ModuleId");
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}
	private boolean checkDoplicateName(String username){
		try {
			String selectStmt = "select username from TbLogin where username='"+username+"'";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false;
	}
	public void loadUserName(){
		try {
			cmbUserFind.data.clear();
			String selectStmt = "select username from TbLogin where username!='admin' order by username asc";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbUserFind.data.add(rs.getString("username"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	public void loadModuleName(){
		try {
			cmbModuelName.data.clear();
			String selectStmt = "select ModuleName from tblogindetails where userId=(select user_id from TbLogin where username='"+cmbUserFind.getValue()+"') order by moduleName";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbModuelName.data.add(rs.getString("ModuleName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private  boolean checkAccessAnyModule(){
		accessModuleCount=0;

		for (AuthenticaionModel p : table.getItems()) {
			if(p.isCheckEntryStatus()){
				accessModuleCount++;
			}
		}
		if(accessModuleCount>0){
			return true;
		}

		return false;

	}


	@SuppressWarnings("unchecked")
	private ObservableList<AuthenticaionModel> getDataRoutine() {


		
		ObservableList<AuthenticaionModel> data =
				FXCollections.observableArrayList(param -> new Observable [] {
						param.CheckEntryStatusProperty(),param.CheckChangeStatusProperty(),param.CheckClearStatusProperty(),param.CheckRefundStatusProperty(),param.CheckBlockStatusProperty()});
		data.addAll(model.getModuleItem());



		data.addListener((ListChangeListener) change -> {

			if (headerEntryCheckboxAction) {
				return;
			}
			else if (headerChangeCheckboxAction) {
				return;
			}
			else if (headerClearCheckboxAction) {
				return;
			}
			else if (headerBlockCheckboxAction) {
				return;
			}
			else if (headerRefundCheckboxAction) {
				return;
			}
			
			

			while (change.next()) {

				if (change.wasUpdated()) {

					int checkedEntry  = 0;
					int checkedChange  = 0;
					int checkedClear  = 0;
					int checkedRefund  = 0;
					int checkedBlock  = 0;

					for (AuthenticaionModel book : table.getItems()) {
						
						
						if (book.isCheckEntryStatus()) {
							checkedEntry++;
						}   
				    	if (book.isCheckChangeStatus()) {
							checkedChange++;
						}
						if (book.isCheckClearStatus()) {
							checkedClear++;
						}
						if (book.isCheckRefundStatus()) {
							checkedRefund++;
						}
						if (book.isCheckBlockStatus()) {
							checkedBlock++;
						}
					}
					if (checkedEntry == table.getItems().size()) {
						headerEntryCheckbox.setSelected(true);
					}
					else if (checkedEntry == 0) {
						headerEntryCheckbox.setSelected(false);
					}
					else if (checkedEntry > 0) {
						headerEntryCheckbox.setSelected(false);
					}
					
					
					
					if (checkedChange == table.getItems().size()) {
						headerChangeCheckbox.setSelected(true);
					}
					else if (checkedChange == 0) {
						headerChangeCheckbox.setSelected(false);
					}
					else if (checkedChange > 0) {
						headerChangeCheckbox.setSelected(false);
					}
					
					if (checkedClear == table.getItems().size()) {
						headerClearCheckbox.setSelected(true);
					}
					else if (checkedClear == 0) {
						headerClearCheckbox.setSelected(false);
					}
					else if (checkedClear > 0) {
						headerClearCheckbox.setSelected(false);
					}
					
					
					if (checkedRefund == table.getItems().size()) {
						headerRefundCheckbox.setSelected(true);
					}
					else if (checkedRefund == 0) {
						headerRefundCheckbox.setSelected(false);
					}
					else if (checkedRefund > 0) {
						headerRefundCheckbox.setSelected(false);
					}
					
					if (checkedBlock == table.getItems().size()) {
						headerBlockCheckbox.setSelected(true);
					}
					else if (checkedBlock == 0) {
						headerBlockCheckbox.setSelected(false);
					}
					else if (checkedBlock > 0) {
						headerBlockCheckbox.setSelected(false);
					}
					
				}

			}
		});
		
		

		return data;
	}

	private void UpdateHeaderCheckbox(){
		int checkedEntry  = 0;
		int checkedChange  = 0;
		int checkedClear  = 0;
		int checkedRefund  = 0;
		int checkedBlock  = 0;

		for (AuthenticaionModel book : table.getItems()) {
			if (book.isCheckEntryStatus()) {
				checkedEntry++;
			}   
	    	if (book.isCheckChangeStatus()) {
				checkedChange++;
			}
			if (book.isCheckClearStatus()) {
				checkedClear++;
			}
			if (book.isCheckRefundStatus()) {
				checkedRefund++;
			}
			if (book.isCheckBlockStatus()) {
				checkedBlock++;
			}
			
		
			
		}
		if (checkedEntry == table.getItems().size()) {
			headerEntryCheckbox.setSelected(true);
		}
		else if (checkedEntry == 0) {
			headerEntryCheckbox.setSelected(false);
		}
		else if (checkedEntry > 0) {
			headerEntryCheckbox.setSelected(false);
		}
		
		
		
		if (checkedChange == table.getItems().size()) {
			headerChangeCheckbox.setSelected(true);
		}
		else if (checkedChange == 0) {
			headerChangeCheckbox.setSelected(false);
		}
		else if (checkedChange > 0) {
			headerChangeCheckbox.setSelected(false);
		}
		
		if (checkedClear == table.getItems().size()) {
			headerClearCheckbox.setSelected(true);
		}
		else if (checkedClear == 0) {
			headerClearCheckbox.setSelected(false);
		}
		else if (checkedClear > 0) {
			headerClearCheckbox.setSelected(false);
		}
		
		
		if (checkedRefund == table.getItems().size()) {
			headerRefundCheckbox.setSelected(true);
		}
		else if (checkedRefund == 0) {
			headerRefundCheckbox.setSelected(false);
		}
		else if (checkedRefund > 0) {
			headerRefundCheckbox.setSelected(false);
		}
		
		if (checkedBlock == table.getItems().size()) {
			headerBlockCheckbox.setSelected(true);
		}
		else if (checkedBlock == 0) {
			headerBlockCheckbox.setSelected(false);
		}
		else if (checkedBlock > 0) {
			headerBlockCheckbox.setSelected(false);
		}

	}

    private void addButtonToTable() {
        TableColumn<AuthenticaionModel, Void> colBtn = new TableColumn("Edit");

        colBtn.setMinWidth(60);
        colBtn.setMaxWidth(60);
        Callback<TableColumn<AuthenticaionModel, Void>, TableCell<AuthenticaionModel, Void>> cellFactory = new Callback<TableColumn<AuthenticaionModel, Void>, TableCell<AuthenticaionModel, Void>>() {
            @Override
            public TableCell<AuthenticaionModel, Void> call(final TableColumn<AuthenticaionModel, Void> param) {
                final TableCell<AuthenticaionModel, Void> cell = new TableCell<AuthenticaionModel, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	
                    		Alert alert = new Alert(AlertType.CONFIRMATION, "Are You Sure To Edit Information " +" ?", ButtonType.YES, ButtonType.NO);
                    		alert.showAndWait();
                    		
                    		if (alert.getResult() == ButtonType.YES) {
                            	try {
                                	String ModuleId=getModuleId(table.getItems().get(getIndex()).getModuleName());
                					String ModuleItemId=table.getItems().get(getIndex()).getModuleId();
                					String Entry=table.getItems().get(getIndex()).CheckEntryStatusProperty().toString().substring(table.getItems().get(getIndex()).CheckEntryStatusProperty().toString().indexOf(":")+1, table.getItems().get(getIndex()).CheckEntryStatusProperty().toString().length()-1);
                					String Change=table.getItems().get(getIndex()).CheckChangeStatusProperty().toString().substring(table.getItems().get(getIndex()).CheckChangeStatusProperty().toString().indexOf(":")+1, table.getItems().get(getIndex()).CheckChangeStatusProperty().toString().length()-1);
                					String Clear=table.getItems().get(getIndex()).CheckClearStatusProperty().toString().substring(table.getItems().get(getIndex()).CheckClearStatusProperty().toString().indexOf(":")+1, table.getItems().get(getIndex()).CheckClearStatusProperty().toString().length()-1);
                					String Refund=table.getItems().get(getIndex()).CheckRefundStatusProperty().toString().substring(table.getItems().get(getIndex()).CheckRefundStatusProperty().toString().indexOf(":")+1, table.getItems().get(getIndex()).CheckRefundStatusProperty().toString().length()-1);
                					String Block=table.getItems().get(getIndex()).CheckBlockStatusProperty().toString().substring(table.getItems().get(getIndex()).CheckBlockStatusProperty().toString().indexOf(":")+1, table.getItems().get(getIndex()).CheckBlockStatusProperty().toString().length()-1);
                					
                					String UserId=getUserId(cmbUserFind.getValue().toString());
        							String sql="update TbAuthentication set ModuleId='"+ModuleId+"',ModuleItemId='"+table.getItems().get(getIndex()).getModuleId()+"',Entry='"+Entry.trim()+"',Change='"+Change.trim()+"',Clear='"+Clear.trim()+"',Refund='"+Refund.trim()+"',Block='"+Block.trim()+"',EntryTime=CURRENT_TIMESTAMP,CreateBy='"+sessionBeam.getUserId()+"' where userId='"+UserId+"' and ModuleId='"+ModuleId+"' and ModuleItemId='"+ModuleItemId+"'";
        							DBUtil.dbExecuteUpdate(sql);
        							
        							String Udsql="insert into TbUdAuthentication (userId,ModuleId"
        									+ ",ModuleItemId"
        									+ ",Entry,"
        									+ "Change,"
        									+ "Clear,"
        									+ "Refund,"
        									+ "Block,"
        									+ "EntryTime,"
        									+ "CreateBy,Flag) values ('"+UserId+"','"+ModuleId+"','"+ModuleItemId+"','"+Entry.trim()+"','"+Change.trim()+"','"+Clear.trim()+"','"+Refund.trim()+"','"+Block.trim()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','New')";
        							DBUtil.dbExecuteUpdate(Udsql);
    							} catch (Exception e) {
    								e.printStackTrace();
    								new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
    							}
                    		}

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }
	private void setCheckBoxFalseValue(){
		for(int a=0;a<table.getItems().size();a++){
			table.getItems().get(a).setCheckEntryStatus(false);
			table.getItems().get(a).setCheckChangeStatus(false);
			table.getItems().get(a).setCheckClearStatus(false);
			table.getItems().get(a).setCheckRefundStatus(false);
			table.getItems().get(a).setCheckBlockStatus(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void addCmp(){

		cmbUserFind.setLayoutX(10);
		cmbUserFind.setLayoutY(10);
		cmbUserFind.setMinWidth(20);
		cmbUserFind.setMinHeight(28);

		cmbModuelName.setLayoutX(210);
		cmbModuelName.setLayoutY(10);
		cmbModuelName.setMinWidth(260);
		cmbModuelName.setMinHeight(28);
		cmbModuelName.setDisable(true);

		checkAllModule=new CheckBox("All");

		checkAllModule.setLayoutX(475);
		checkAllModule.setLayoutY(15);
		checkAllModule.setSelected(true);

		btnFind.setLayoutX(520);
		btnFind.setLayoutY(10);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);


		final TableColumn<AuthenticaionModel, String> moduleIdCol = new TableColumn<AuthenticaionModel, String>("M.Item Id");
		final TableColumn<AuthenticaionModel, String> moduleNameCol = new TableColumn<AuthenticaionModel, String>("Module Name");
		final TableColumn<AuthenticaionModel, String> ModuleItemNameCol = new TableColumn<AuthenticaionModel, String>("Module Item Name");
		final TableColumn<AuthenticaionModel, Boolean> checkEntryCol = new TableColumn<AuthenticaionModel, Boolean>("");
		final TableColumn<AuthenticaionModel, Boolean> checkChangeCol = new TableColumn<AuthenticaionModel, Boolean>("");
		final TableColumn<AuthenticaionModel, Boolean> checkClearCol = new TableColumn<AuthenticaionModel, Boolean>("");
		final TableColumn<AuthenticaionModel, Boolean> checkRefundCol = new TableColumn<AuthenticaionModel, Boolean>("");
		final TableColumn<AuthenticaionModel, Boolean> checkBlockCol = new TableColumn<AuthenticaionModel, Boolean>("");


		table.getColumns().addAll(moduleIdCol, moduleNameCol,ModuleItemNameCol, checkEntryCol,checkChangeCol,checkClearCol,checkRefundCol,checkBlockCol);

		addButtonToTable();

		headerEntryCheckbox = new CheckBox("Entry");
		checkEntryCol.setGraphic(headerEntryCheckbox);

		headerChangeCheckbox = new CheckBox("Change");
		checkChangeCol.setGraphic(headerChangeCheckbox);

		headerClearCheckbox = new CheckBox("Clear");
		checkClearCol.setGraphic(headerClearCheckbox);

		headerRefundCheckbox = new CheckBox("Refund");
		checkRefundCol.setGraphic(headerRefundCheckbox);


		headerBlockCheckbox = new CheckBox("Block");
		checkBlockCol.setGraphic(headerBlockCheckbox);

		moduleIdCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, String>("moduleId"));
		moduleNameCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, String>("moduleName"));
		ModuleItemNameCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, String>("moduleItemName"));
		checkEntryCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, Boolean>("CheckEntryStatus"));
		checkChangeCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, Boolean>("CheckChangeStatus"));
		checkClearCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, Boolean>("CheckClearStatus"));
		checkRefundCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, Boolean>("CheckRefundStatus"));
		checkBlockCol.setCellValueFactory(new PropertyValueFactory<AuthenticaionModel, Boolean>("CheckBlockStatus"));


		checkEntryCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkEntryCol));
		checkChangeCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkChangeCol));
		checkClearCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkClearCol));
		checkRefundCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkRefundCol));
		checkBlockCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkBlockCol));



		checkEntryCol.setEditable(true);
		checkChangeCol.setEditable(true);

		checkClearCol.setEditable(true);
		checkRefundCol.setEditable(true);
		checkBlockCol.setEditable(true);
		table.setEditable(true);


		moduleIdCol.setPrefWidth(80);
		moduleNameCol.setPrefWidth(160);
		ModuleItemNameCol.setPrefWidth(230);
		checkEntryCol.setPrefWidth(80);
		checkChangeCol.setPrefWidth(90);
		checkClearCol.setPrefWidth(80);
		checkRefundCol.setPrefWidth(80);
		checkBlockCol.setPrefWidth(80);
		

		table.setPrefWidth(945);
		table.setPrefHeight(300);
		table.setEditable(true);

		table.setLayoutX(10);
		table.setLayoutY(50);


		table.setItems(getDataRoutine());

		
		btnConfirm.setLayoutX(400);
		btnConfirm.setLayoutY(355);
		btnConfirm.setPrefWidth(110);
		btnConfirm.setPrefHeight(32);

		pane.getChildren().addAll(cmbUserFind,cmbModuelName,checkAllModule,btnFind,table,btnConfirm);
	}


}


