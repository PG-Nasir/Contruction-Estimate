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

public class ModuleCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","","","");
	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,820, 400);;
	public Stage mainItemStage=new Stage();
	Button btnFind=new Button("Find");
	private TableView<ModuleCreateModel> table = new TableView<ModuleCreateModel>();
	FxComboBox cmbModuleFind=new FxComboBox();
	TextField txtModuleName;
	TextRead txtModuleId;

	ModuleCreateModel model=new ModuleCreateModel("", "");
	boolean insert=true,update=false,find=false;
	
	public ModuleCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Module Create");
		addCmp();
		btnActionEvent();
		setModuleName();
		ComponentEnable(true);
		ButtonEnable(true);
		loadModuleName();
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
		if(checkDoplicateName(cmbModuleFind.getValue().toString())){
			try {
				String selectStmt = "select * from TbModuleInfo where ModuleName='"+cmbModuleFind.getValue()+"' ";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

				int i=0;

				while(rs.next()){
					txtModuleId.setText(rs.getString("ModuleId"));
					txtModuleName.setText(rs.getString("ModuleName"));
					cButton.btnNew.setDisable(true);
					cButton.btnEdit.setDisable(false);
					
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
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid module name never allow");
		}
	}
	private void btnSaveEvent(){
		if(checkEmptyFeild()){
				if(insert){
					if(!checkDoplicateName(txtModuleName.getText().trim().toString())){
						if(insertData()){
							new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
							btnRefreshEvent();
							setModuleName();
							loadModuleName();
						}
						else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
						}
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Doplicate name never allow");
					}
				}
				else if(update){
					if(!checkDoplicateName(txtModuleName.getText().trim().toString())){
						if(updateData("1")){
							new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
							btnRefreshEvent();
							setModuleName();
							loadModuleName();
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
							loadModuleName();
						}
						else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
						}
					}
				}
		}
	}
	private boolean insertData(){
		try {
			String ModuleId=getMaxModuleId();
			String insertSql="insert into TbModuleInfo (ModuleId,ModuleName,EntryTime,CreateBy) values "
					+ "('"+ModuleId+"',"
					+ "'"+txtModuleName.getText().trim().toString()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"')";

			DBUtil.dbExecuteUpdate(insertSql);
			
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
			String ModuleId=txtModuleId.getText().trim().toString();
			String updateSql="";
			if(Type.equals("1")){
				 updateSql="update TbModuleInfo set  "
						+ "ModuleName='"+txtModuleName.getText().trim().toString()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where ModuleId='"+ModuleId+"'";

			}
			else{
				 updateSql="update TbModuleInfo set  "
						+ "ModuleName='"+txtModuleName.getText().trim().toString()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where ModuleId='"+ModuleId+"'";
			}
			DBUtil.dbExecuteUpdate(updateSql);
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
			String selectStmt = "select ModuleName from TbModuleInfo where ModuleName='"+username+"'";
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
	public void loadModuleName(){
		try {
			cmbModuleFind.data.clear();
			String selectStmt = "select ModuleName from TbModuleInfo order by ModuleName asc";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbModuleFind.data.add(rs.getString("ModuleName"));
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
	}
	private void ButtonEnable(boolean t){
		cButton.btnSave.setDisable(t);
		cButton.btnEdit.setDisable(t);
	}
	private void setEmptyText(){
		txtModuleId.setText(getMaxModuleId());
		txtModuleName.setText("");
		insert=true;
		update=false;
		find=false;
	}
	private void ComponentEnable(boolean t){
		txtModuleName.setDisable(t);
		table.setDisable(t);

	}
	private String getMaxModuleId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(ModuleId),0)+1)as ModuleId from TbModuleInfo";
		try {
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

	private void setModuleName(){
		model.getModuleItem().clear();
		String selectStmt = "select ModuleId,ModuleName from TbModuleInfo order by ModuleId";

		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				model.getModuleItem().add(new ModuleCreateModel(rs.getString("ModuleId"),rs.getString("ModuleName")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private boolean checkEmptyFeild()
	{
		if(!txtModuleName.getText().trim().toString().isEmpty()){
			return true;
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Module Name.");
		}
		return false;
	} 
	@SuppressWarnings("unchecked")
	private void addCmp(){

		cmbModuleFind.setLayoutX(10);
		cmbModuleFind.setLayoutY(10);
		cmbModuleFind.setMinWidth(255);
		cmbModuleFind.setMinHeight(28);

		btnFind.setLayoutX(270);
		btnFind.setLayoutY(10);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);


		final TableColumn<ModuleCreateModel, String> firstNameCol = new TableColumn<ModuleCreateModel, String>("Module Id");
		final TableColumn<ModuleCreateModel, String> lastNameCol = new TableColumn<ModuleCreateModel, String>("Module Name");

		table.getColumns().addAll(firstNameCol, lastNameCol);

		firstNameCol.setCellValueFactory(new PropertyValueFactory<ModuleCreateModel, String>("moduleId"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<ModuleCreateModel, String>("moduleName"));
		table.setEditable(true);
		

		firstNameCol.setPrefWidth(90);
		lastNameCol.setPrefWidth(365);


		table.setPrefWidth(460);
		table.setPrefHeight(320);
		table.setEditable(true);



		table.setLayoutX(10);
		table.setLayoutY(50);

		table.setItems(model.getModuleItem());

		Label lblModuleId=new Label("Module Id");
		lblModuleId.setLayoutX(500);
		lblModuleId.setLayoutY(180);

		txtModuleId=new TextRead();
		txtModuleId.setMaxWidth(120);
		txtModuleId.setLayoutX(600);
		txtModuleId.setLayoutY(175);

		txtModuleId.setText(getMaxModuleId());
		Label lblName=new Label("Module Name");
		lblName.setLayoutX(500);
		lblName.setLayoutY(208);

		txtModuleName=new TextField();
		txtModuleName.setMinWidth(180);
		txtModuleName.setLayoutX(600);
		txtModuleName.setLayoutY(205);

		cButton.setLayoutX(475);
		cButton.setLayoutY(300);
		pane.getChildren().addAll(cmbModuleFind,btnFind,table,cButton,lblModuleId,txtModuleId,
				lblName,txtModuleName);

		final Control ob[] = {txtModuleId,txtModuleName};	
		new FocusMoveByEnter(ob);

	}


}


