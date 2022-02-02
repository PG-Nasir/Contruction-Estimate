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

import com.ComShare.BackhandIdStore;
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
import javafx.collections.ObservableList;
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

public class ModuleItemCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","","","");
	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,950, 400);;
	public Stage mainItemStage=new Stage();
	Button btnFind=new Button("Find");
	private TableView<ModuleItemCreateModel> table = new TableView<ModuleItemCreateModel>();
	FxComboBox cmbModuleItemFind=new FxComboBox();
	ObservableList<BackhandIdStore> ModuleId = FXCollections.observableArrayList();
	FxComboBox cmbModuleName=new FxComboBox();
	TextField txtModuleItemName;
	TextRead txtModuleItemId;

	ModuleItemCreateModel model=new ModuleItemCreateModel("", "","");
	boolean insert=true,update=false,find=false;

	public ModuleItemCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Module Item Create");
		addCmp();
		btnActionEvent();
		setModuleName();
		ComponentEnable(true);
		ButtonEnable(true);
		loadModuleItemName();
		setModuleItemName();
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
		if(checkDoplicateName(cmbModuleItemFind.getValue().toString())){
			try {
				String selectStmt = "select ModuleItemId,ModuleName,ModuleItemName from TbModuleItemInfo where  ModuleItemName='"+cmbModuleItemFind.getValue()+"' ";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

				int i=0;

				while(rs.next()){
					txtModuleItemId.setText(rs.getString("ModuleItemId"));
					cmbModuleName.setValue(rs.getString("ModuleName"));
					txtModuleItemName.setText(rs.getString("ModuleItemName"));
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
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid Module Item Name never allow");
		}
	}
	private void btnSaveEvent(){
		if(checkEmptyFeild()){
			if(insert){
				if(!checkDoplicateName(txtModuleItemName.getText().trim().toString())){
					if(insertData()){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setModuleItemName();
						loadModuleItemName();
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
				if(!checkDoplicateName(cmbModuleName.getValue().toString())){
					if(updateData("1")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setModuleItemName();
						loadModuleItemName();
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
					}
				}
				else{
					if(updateData("2")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setModuleItemName();
						loadModuleItemName();
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
			String ModuleItemId=getMaxModuleItemId();
			String ModuleId=getModuleId();
			String insertSql="insert into TbModuleItemInfo "
					+ "("
					+ "ModuleItemId,"
					+ "ModuleId,"
					+ "ModuleName,"
					+ "ModuleItemName,"
					+ "EntryTime,"
					+ "CreateBy"
					+ ")"
					+ " values "
					+ "('"+ModuleItemId+"',"
					+ "'"+ModuleId+"',"
					+ "'"+cmbModuleName.getValue()+"',"
					+ "'"+txtModuleItemName.getText().trim().toString()+"',"
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
			String ModuleItemId=txtModuleItemId.getText().trim().toString();
			String updateSql="";
			if(Type.equals("1")){
				updateSql="update TbModuleItemInfo set  "
						+ "ModuleName='"+cmbModuleName.getValue()+"',"
						+ "ModuleItemName='"+txtModuleItemName.getText().trim().toString()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where ModuleItemId='"+ModuleItemId+"'";

			}
			else{
				updateSql="update TbModuleItemInfo set  "
						+ "ModuleName='"+cmbModuleName.getValue()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where ModuleItemId='"+ModuleItemId+"'";
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
			String selectStmt = "select ModuleItemName from TbModuleItemInfo where ModuleItemName='"+username+"'";
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
	public void loadModuleItemName(){
		try {
			cmbModuleItemFind.data.clear();
			String selectStmt = "select ModuleItemName from TbModuleItemInfo order by ModuleItemName";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbModuleItemFind.data.add(rs.getString("ModuleItemName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private void setModuleName(){

		cmbModuleName.data.clear();
		String selectStmt = "select ModuleId,ModuleName from TbModuleInfo order by ModuleName";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbModuleName.data.add(rs.getString("ModuleName"));
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
		txtModuleItemId.setText(getMaxModuleItemId());
		cmbModuleName.setValue(null);
		txtModuleItemName.setText("");
		insert=true;
		update=false;
		find=false;
	}
	private void ComponentEnable(boolean t){
		cmbModuleName.setDisable(t);
		table.setDisable(t);
		txtModuleItemName.setDisable(t);

	}
	private String getMaxModuleItemId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(ModuleItemId),0)+1)as ModuleItemId from TbModuleItemInfo";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("ModuleItemId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}
	private String getModuleId(){
		String Id="";
		String selectStmt = "select ModuleId from TbModuleInfo where ModuleName='"+cmbModuleName.getValue()+"'";
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
	private void setModuleItemName(){
		model.getModuleItem().clear();
		String selectStmt = "select ModuleItemId,ModuleName,ModuleItemName from TbModuleItemInfo order by ModuleName";

		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				model.getModuleItem().add(new ModuleItemCreateModel(rs.getString("ModuleItemId"),rs.getString("ModuleName"),rs.getString("ModuleItemName")));
			}



			//table.getItems().get(2).setcheckStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private boolean checkEmptyFeild()
	{
		if(cmbModuleName.getValue()!=null){
			if(!txtModuleItemName.getText().trim().toString().isEmpty()){
				return true;
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Module Item Name.");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Module Name.");
		}
		return false;
	} 



	@SuppressWarnings("unchecked")
	private void addCmp(){

		cmbModuleItemFind.setLayoutX(10);
		cmbModuleItemFind.setLayoutY(10);
		cmbModuleItemFind.setMinWidth(255);
		cmbModuleItemFind.setMinHeight(28);

		btnFind.setLayoutX(270);
		btnFind.setLayoutY(10);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);


		final TableColumn<ModuleItemCreateModel, String> moduleItemCol = new TableColumn<ModuleItemCreateModel, String>("Item Id");
		final TableColumn<ModuleItemCreateModel, String> moduleCol = new TableColumn<ModuleItemCreateModel, String>("Module Name");
		final TableColumn<ModuleItemCreateModel, String> moduleItemNameCol = new TableColumn<ModuleItemCreateModel, String>("Module Item Name");

		table.getColumns().addAll(moduleItemCol,moduleCol, moduleItemNameCol);

		moduleItemCol.setCellValueFactory(new PropertyValueFactory<ModuleItemCreateModel, String>("moduleItemId"));
		moduleCol.setCellValueFactory(new PropertyValueFactory<ModuleItemCreateModel, String>("moduleName"));
		moduleItemNameCol.setCellValueFactory(new PropertyValueFactory<ModuleItemCreateModel, String>("moduleItemName"));
		table.setEditable(true);


		moduleItemCol.setPrefWidth(80);
		moduleCol.setPrefWidth(220);
		moduleItemNameCol.setPrefWidth(280);


		table.setPrefWidth(560);
		table.setPrefHeight(320);
		table.setEditable(true);



		table.setLayoutX(10);
		table.setLayoutY(50);

		table.setItems(model.getModuleItem());

		Label lblModuleId=new Label("Module Id");
		lblModuleId.setLayoutX(600);
		lblModuleId.setLayoutY(180);

		txtModuleItemId=new TextRead();
		txtModuleItemId.setMaxWidth(120);
		txtModuleItemId.setLayoutX(720);
		txtModuleItemId.setLayoutY(175);

		txtModuleItemId.setText(getMaxModuleItemId());
		Label lblName=new Label("Module Name");
		lblName.setLayoutX(600);
		lblName.setLayoutY(208);



		cmbModuleName.setMaxWidth(180);
		cmbModuleName.setLayoutX(720);
		cmbModuleName.setLayoutY(205);

		Label lblModuleItemName=new Label("Module Item Name");
		lblModuleItemName.setLayoutX(600);
		lblModuleItemName.setLayoutY(240);

		txtModuleItemName=new TextField();
		txtModuleItemName.setMinWidth(220);
		txtModuleItemName.setLayoutX(720);
		txtModuleItemName.setLayoutY(235);

		cButton.setLayoutX(575);
		cButton.setLayoutY(300);
		pane.getChildren().addAll(cmbModuleItemFind,btnFind,table,cButton,lblModuleId,txtModuleItemId,
				lblName,cmbModuleName,lblModuleItemName,txtModuleItemName);

		final Control ob[] = {txtModuleItemId,cmbModuleName};	
		new FocusMoveByEnter(ob);

	}


}


