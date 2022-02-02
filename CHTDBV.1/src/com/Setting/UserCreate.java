package com.Setting;




import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import com.ComShare.CommonButton;
import com.ComShare.DBUtil;
import com.ComShare.FocusMoveByEnter;
import com.ComShare.FxComboBox;
import com.ComShare.Notification;
import com.ComShare.SessionBean;
import com.ComShare.TextRead;



import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class UserCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","","","");
	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,820, 400);;
	public Stage mainItemStage=new Stage();
	Button btnFind=new Button("Find");
	private TableView<UserCreateModel> table = new TableView<UserCreateModel>();
	FxComboBox cmbUserFind=new FxComboBox();
	TextField txtName,txtUserName;
	PasswordField txtPassword;
	TextRead txtUserId;
	ComboBox cmbDepartment,cmbUserType,cmbStatus;
	TextArea txtAddress;
	UserCreateModel model=new UserCreateModel("", "", false);
	boolean insert=true,update=false,find=false;


	int accessModuleCount=0;
	public UserCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("User Create");
		addCmp();
		btnActionEvent();
		setModuleName();
		setJobName();
		ComponentEnable(true);
		ButtonEnable(true);
		loadUserName();
	}
	
	
	private void btnActionEvent(){
		cButton.btnNew.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnNewEvent();
			}
		});
		cButton.btnRefresh.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnRefreshEvent();
			}
		});
		cButton.btnSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnSaveEvent();
			}
		});
		cButton.btnEdit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnEditEvent();
			}
		});
		btnFind.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnFindEvent();
			}
		});

	}
	private void btnFindEvent(){
		if(checkDoplicateName(cmbUserFind.getValue().toString())){
			try {
				setCheckBoxFalseValue();
				String selectStmt = "select a.user_id, a.name,a.Department,a.userType,a.username,a.password,a.active,b.moduleId,b.moduleName  from  tblogindetails b join TbLogin a on a.user_id=b.userId where a.username='"+cmbUserFind.getValue()+"' and a.username!='admin'";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

				int i=0;

				while(rs.next()){
					txtUserId.setText(rs.getString("user_id"));
					txtName.setText(rs.getString("name"));
					cmbDepartment.setValue(rs.getString("Department"));
					cmbUserType.setValue(rs.getString("userType"));
					txtUserName.setText(rs.getString("username"));
					txtPassword.setText(rs.getString("password"));
					cmbStatus.setValue(rs.getString("active").toString().equals("1")?"Active":"Inactive");
					cButton.btnNew.setDisable(true);
					cButton.btnEdit.setDisable(false);

					setModuleAccessStatus(rs.getString("moduleName").toString());
					find=true;
				}


				if(find){
					cButton.btnSave.setDisable(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid Username never allow");
		}
	}
	private void btnSaveEvent(){
		if(checkEmptyFeild()){
			if(checkAccessAnyModule()){
				if(insert){
					if(!checkDoplicateName(txtUserName.getText().trim().toString())){
						if(insertData()){
							new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
							btnRefreshEvent();
							setModuleName();
							loadUserName();
						}
						else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
						}
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Doplicate Username never allow");
					}
				}
				else if(update){
					if(!checkDoplicateName(txtUserName.getText().trim().toString())){
						if(updateData("1")){
							new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
							btnRefreshEvent();
							setModuleName();
							loadUserName();
						}
						else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
						}
					}
					else{
						if(updateData("2")){
							new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
							btnRefreshEvent();
							setModuleName();
							loadUserName();
						}
						else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
						}
					}
				}
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","At least select one module");
			}
		}
	}
	private boolean insertData(){
		try {
			String UserId=getMaxUserId();
			String active=cmbStatus.getValue().toString().equals("Active")?"1":"0";
			String insertSql="insert into TbLogin (user_id,name,Department,userType,username,password,active,EntryTime,CreateBy) values "
					+ "('"+UserId+"',"
					+ "'"+txtName.getText().trim().toString()+"',"
					+ "'"+cmbDepartment.getValue().toString()+"',"
					+ "'"+cmbUserType.getValue().toString()+"',"
					+ "'"+txtUserName.getText().trim().toString()+"',"
					+ "'"+txtPassword.getText().trim().toString()+"',"
					+ "'"+active+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"')";

			DBUtil.dbExecuteUpdate(insertSql);
			String UdinsertSql="insert into TbUdLogin (user_id,name,Department,userType,username,password,active,EntryTime,CreateBy,Flag) values "
					+ "('"+UserId+"',"
					+ "'"+txtName.getText().trim().toString()+"',"
					+ "'"+cmbDepartment.getValue().toString()+"',"
					+ "'"+cmbUserType.getValue().toString()+"',"
					+ "'"+txtUserName.getText().trim().toString()+"',"
					+ "'"+txtPassword.getText().trim().toString()+"',"
					+ "'"+active+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"','Flag')";

			DBUtil.dbExecuteUpdate(UdinsertSql);


			int a=0;
			for (UserCreateModel p : table.getItems()) {

				if(p.isCheckStatus()){
					String insertDetailsSql="insert into tblogindetails (userId,Department,userType,moduleId,moduleName,status,EntryTime,CreateBy) values "
							+ "('"+UserId+"',"
							+ "'"+cmbDepartment.getValue().toString()+"',"
							+ "'"+cmbUserType.getValue().toString()+"',"
							+ "'"+p.getModuleId()+"',"
							+ "'"+p.getModuleName()+"',"
							+ "'"+1+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"')";

					DBUtil.dbExecuteUpdate(insertDetailsSql);

					System.out.println(insertDetailsSql);

					String insertUdDetailsSql="insert into tbUdlogindetails (userId,Department,userType,moduleId,moduleName,status,EntryTime,CreateBy,Flag) values "
							+ "('"+UserId+"',"
							+ "'"+cmbDepartment.getValue().toString()+"',"
							+ "'"+cmbUserType.getValue().toString()+"',"
							+ "'"+p.getModuleId()+"',"
							+ "'"+p.getModuleName()+"',"
							+ "'"+1+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"','NEW')";

					System.out.println(insertUdDetailsSql);
					DBUtil.dbExecuteUpdate(insertUdDetailsSql);
				}
				a++;
			}


			insert=false;
			return true;
		} catch (Exception e) {

			insert=true;
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false;
	}
	
	private boolean updateData(String Type){
		try {
			String UserId=txtUserId.getText().trim().toString();
			String active=cmbStatus.getValue().toString().equals("Active")?"1":"0";
			String updateSql="";
			if(Type.equals("1")){
				 updateSql="update TbLogin set  "
						+ "name='"+txtName.getText().trim().toString()+"',"
						+ "Department='"+cmbDepartment.getValue().toString()+"',"
						+ "userType='"+cmbUserType.getValue().toString()+"',"
						+ "username='"+txtUserName.getText().trim().toString()+"',"
						+ "password='"+txtPassword.getText().trim().toString()+"',"
						+ "active='"+active+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where user_id='"+UserId+"'";
			}
			else{
				 updateSql="update TbLogin set  "
						+ "name='"+txtName.getText().trim().toString()+"',"
						+ "Department='"+cmbDepartment.getValue().toString()+"',"
						+ "userType='"+cmbUserType.getValue().toString()+"',"
						+ "password='"+txtPassword.getText().trim().toString()+"',"
						+ "active='"+active+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where user_id='"+UserId+"'";
			}


			DBUtil.dbExecuteUpdate(updateSql);
			String UdinsertSql="insert into TbUdLogin (user_id,name,Department,userType,username,password,active,EntryTime,CreateBy,Flag) values "
					+ "('"+UserId+"',"
					+ "'"+txtName.getText().trim().toString()+"',"
					+ "'"+cmbDepartment.getValue().toString()+"',"
					+ "'"+cmbUserType.getValue().toString()+"',"
					+ "'"+txtUserName.getText().trim().toString()+"',"
					+ "'"+txtPassword.getText().trim().toString()+"',"
					+ "'"+active+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"','Flag')";

			DBUtil.dbExecuteUpdate(UdinsertSql);
			
			
			
			String deleteUserSql="delete from tblogindetails where userId='"+UserId+"'";
			DBUtil.dbExecuteUpdate(deleteUserSql);
			
			
			int a=0;
			for (UserCreateModel p : table.getItems()) {

				if(p.isCheckStatus()){
					String insertDetailsSql="insert into tblogindetails (userId,Department,userType,moduleId,moduleName,status,EntryTime,CreateBy) values "
							+ "('"+UserId+"',"
							+ "'"+cmbDepartment.getValue().toString()+"',"
							+ "'"+cmbUserType.getValue().toString()+"',"
							+ "'"+p.getModuleId()+"',"
							+ "'"+p.getModuleName()+"',"
							+ "'"+1+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"')";

					DBUtil.dbExecuteUpdate(insertDetailsSql);

					System.out.println(insertDetailsSql);

					String insertUdDetailsSql="insert into tbUdlogindetails (userId,Department,userType,moduleId,moduleName,status,EntryTime,CreateBy,Flag) values "
							+ "('"+UserId+"',"
							+ "'"+cmbDepartment.getValue().toString()+"',"
							+ "'"+cmbUserType.getValue().toString()+"',"
							+ "'"+p.getModuleId()+"',"
							+ "'"+p.getModuleName()+"',"
							+ "'"+1+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"','NEW')";

					System.out.println(insertUdDetailsSql);
					DBUtil.dbExecuteUpdate(insertUdDetailsSql);
				}
				a++;
			}

			
			
			update=false;
			return true;
		} catch (Exception e) {
			update=true;
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false;
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
	private void btnEditEvent(){
		cButton.btnEdit.setDisable(true);
		cButton.btnSave.setDisable(false);
		ComponentEnable(false);
		update=true;
		insert=false;
	}
	private void btnRefreshEvent(){
		cButton.btnNew.setDisable(false);
		cButton.btnSave.setDisable(true);
		cButton.btnEdit.setDisable(true);
		ComponentEnable(true);
		setEmptyText();
	}
	private void btnNewEvent(){
		cButton.btnNew.setDisable(true);
		cButton.btnSave.setDisable(false);
		cButton.btnEdit.setDisable(true);
		ComponentEnable(false);
		setEmptyText();
		setCheckBoxFalseValue();
	}
	private void ButtonEnable(boolean t){
		cButton.btnSave.setDisable(t);
		cButton.btnEdit.setDisable(t);
	}
	private void setEmptyText(){
		txtUserId.setText(getMaxUserId());
		txtName.setText("");
		cmbDepartment.setValue(null);
		cmbUserType.setValue(null);
		txtUserName.setText("");
		txtPassword.setText("");
		setCheckBoxFalseValue();
		cmbStatus.setValue(null);
		insert=true;
		update=false;
		find=false;
	}
	private void ComponentEnable(boolean t){
		txtName.setDisable(t);
		cmbDepartment.setDisable(t);
		cmbUserType.setDisable(t);
		txtUserName.setDisable(t);
		txtPassword.setDisable(t);
		cmbStatus.setDisable(t);
		table.setDisable(t);

	}
	private String getMaxUserId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(user_id),0)+1)as user_id from TbLogin";
		try {
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
	private  boolean checkAccessAnyModule(){
		accessModuleCount=0;


		for (UserCreateModel p : table.getItems()) {

			if(p.isCheckStatus()){
				accessModuleCount++;
			}
		}


		if(accessModuleCount>0){
			return true;
		}

		return false;

	}
	private void setModuleAccessStatus(String ModuleName){
		for(int a=0;a<table.getItems().size();a++){

			String name=table.getItems().get(a).getModuleName().toString();

			if(name.equals(ModuleName)){
				table.getItems().get(a).setCheckStatus(true);
			}
		}
	}
	
	private void setCheckBoxFalseValue(){
		for(int a=0;a<table.getItems().size();a++){
			table.getItems().get(a).setCheckStatus(false);
		}
	}
	private void setModuleName(){
		model.getModuleItem().clear();
		String selectStmt = "select ModuleId,ModuleName from TbModuleInfo order by ModuleId";

		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				model.getModuleItem().add(new UserCreateModel(rs.getString("ModuleId"),rs.getString("ModuleName"),new Boolean(false)));
			}
			//table.getItems().get(2).setcheckStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private void setJobName(){
		cmbDepartment.getItems().clear();
		String selectStmt = "select JobId,JobName from TbJobPositionInfo order by JobId";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbDepartment.getItems().add(rs.getString("JobName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private boolean checkEmptyFeild()
	{
		if(!txtName.getText().trim().toString().isEmpty()){
			if(cmbDepartment.getValue()!=null){
				if(cmbUserType.getValue()!=null){
					if(!txtUserName.getText().trim().toString().isEmpty()){
						if(!txtPassword.getText().trim().toString().isEmpty()){
							if(cmbStatus.getValue()!=null){
								return true;
							}
							else{
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide status");
							}
						}
						else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide password");
						}
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide User Name");
					}
				}
				else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide User Type");
				}
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide department");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Full Name.");
		}
		return false;
	} 
	@SuppressWarnings("unchecked")
	private void addCmp(){

		cmbUserFind.setLayoutX(10);
		cmbUserFind.setLayoutY(10);
		cmbUserFind.setMinWidth(255);
		cmbUserFind.setMinHeight(28);

		btnFind.setLayoutX(270);
		btnFind.setLayoutY(10);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);


		final TableColumn<UserCreateModel, String> firstNameCol = new TableColumn<UserCreateModel, String>("Module Id");
		final TableColumn<UserCreateModel, String> lastNameCol = new TableColumn<UserCreateModel, String>("Module Name");
		final TableColumn<UserCreateModel, Boolean> checkStatusCol = new TableColumn<UserCreateModel, Boolean>("ALL");
		table.getColumns().addAll(firstNameCol, lastNameCol, checkStatusCol);

		firstNameCol.setCellValueFactory(new PropertyValueFactory<UserCreateModel, String>("moduleId"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<UserCreateModel, String>("moduleName"));
		checkStatusCol.setCellValueFactory(new PropertyValueFactory<UserCreateModel, Boolean>("CheckStatus"));
		checkStatusCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkStatusCol));
		checkStatusCol.setEditable(true);
		table.setEditable(true);




		firstNameCol.setPrefWidth(80);
		lastNameCol.setPrefWidth(300);


		table.setPrefWidth(460);
		table.setPrefHeight(320);
		table.setEditable(true);



		table.setLayoutX(10);
		table.setLayoutY(50);

		table.setItems(model.getModuleItem());

		Label lblUserId=new Label("User Id");
		lblUserId.setLayoutX(500);
		lblUserId.setLayoutY(80);

		txtUserId=new TextRead();
		txtUserId.setMaxWidth(120);
		txtUserId.setLayoutX(600);
		txtUserId.setLayoutY(75);

		txtUserId.setText(getMaxUserId());
		Label lblName=new Label("Name");
		lblName.setLayoutX(500);
		lblName.setLayoutY(108);

		txtName=new TextField();
		txtName.setMinWidth(180);
		txtName.setLayoutX(600);
		txtName.setLayoutY(105);

		Label lblNameR=new Label("*");
		lblNameR.setLayoutX(790);
		lblNameR.setLayoutY(108);
		lblNameR.setId("Required");

		Label lblDepartment=new Label("Department");
		lblDepartment.setLayoutX(500);
		lblDepartment.setLayoutY(138);

		cmbDepartment=new ComboBox();
		cmbDepartment.setMinWidth(180);
		cmbDepartment.setLayoutX(600);
		cmbDepartment.setLayoutY(135);

		Label lblUserType=new Label("User Type");
		lblUserType.setLayoutX(500);
		lblUserType.setLayoutY(168);

		cmbUserType=new ComboBox();
		cmbUserType.setMinWidth(120);
		cmbUserType.setLayoutX(600);
		cmbUserType.setLayoutY(165);
		cmbUserType.getItems().addAll("Admin","General");

		Label lblUserName=new Label("User Name");
		lblUserName.setLayoutX(500);
		lblUserName.setLayoutY(198);

		txtUserName=new TextField();
		txtUserName.setMinWidth(120);
		txtUserName.setLayoutX(600);
		txtUserName.setLayoutY(195);

		Label lblPasword=new Label("Password");
		lblPasword.setLayoutX(500);
		lblPasword.setLayoutY(228);

		txtPassword=new PasswordField();
		txtPassword.setMinWidth(120);
		txtPassword.setLayoutX(600);
		txtPassword.setLayoutY(225);

		Label lblStatus=new Label("Status");
		lblStatus.setLayoutX(500);
		lblStatus.setLayoutY(258);

		cmbStatus=new ComboBox();
		cmbStatus.setMinWidth(120);
		cmbStatus.setLayoutX(600);
		cmbStatus.setLayoutY(255);
		cmbStatus.getItems().addAll("Active","Inactive");

		cButton.setLayoutX(475);
		cButton.setLayoutY(350);
		pane.getChildren().addAll(cmbUserFind,btnFind,table,cButton,lblUserId,txtUserId,
				lblName,txtName,lblNameR,lblDepartment,cmbDepartment,lblUserType,cmbUserType,lblUserName,
				txtUserName,lblPasword,txtPassword,lblStatus,cmbStatus);

		final Control ob[] = {txtUserId,txtName,cmbDepartment,cmbUserType,
				txtUserName,txtPassword,cmbStatus};	
		new FocusMoveByEnter(ob);

	}


}


