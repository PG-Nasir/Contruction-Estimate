package com.ContructionModule;

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

public class UnitCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","","","");
	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,820, 400);;
	public Stage mainItemStage=new Stage();
	
	Button btnFind=new Button("Find");
	private TableView<UnitCreateModel> table = new TableView<UnitCreateModel>();
	FxComboBox cmbUnitFind=new FxComboBox();

	TextField txtUnitName,txtInputField;
	TextRead txtUnitId;

	UnitCreateModel model=new UnitCreateModel("", "");
	boolean insert=true,update=false,find=false;

	public UnitCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Unit Create");
		addCmp();
		btnActionEvent();
		setUnitName();
		ComponentEnable(true);
		ButtonEnable(true);
		loadUnitName();
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
		if(checkDoplicateName(cmbUnitFind.getValue().toString())){
			try {
				String selectStmt = "select * from TbUnitInfo where UnitName='"+cmbUnitFind.getValue()+"' ";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

				int i=0;

				while(rs.next()){
					txtUnitId.setText(rs.getString("UnitId"));
					txtUnitName.setText(rs.getString("UnitName"));
					txtInputField.setText(rs.getString("InFQty"));
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
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid Unit name never allow");
		}
	}
	private void btnSaveEvent(){
		if(checkEmptyFeild()){
			if(insert){
				if(!checkDoplicateName(txtUnitName.getText().trim().toString())){
					if(insertData()){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setUnitName();
						loadUnitName();
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
				if(!checkDoplicateName(txtUnitName.getText().trim().toString())){
					if(updateData("1")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setUnitName();
						loadUnitName();
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
					}
				}
				else{
					if(updateData("2")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setUnitName();
						loadUnitName();
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
			String UnitId=getMaxUnitId();
			String insertSql="insert into TbUnitInfo (UnitId,UnitName,InFQty,EntryTime,CreateBy) values "
					+ "('"+UnitId+"',"
					+ "'"+txtUnitName.getText().trim().toString()+"',"
					+ "'"+txtInputField.getText().trim().toString()+"',"
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
			String InputId=txtUnitId.getText().trim().toString();
			String updateSql="";
			if(Type.equals("1")){
				updateSql="update TbUnitInfo set  "
						+ "UnitName='"+txtUnitName.getText().trim().toString()+"',"
						+ "InFQty='"+txtInputField.getText().trim().toString()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where UnitId='"+InputId+"'";

			}
			else{
				updateSql="update TbUnitInfo set  "
						+ "InFQty='"+txtInputField.getText().trim().toString()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where UnitId='"+InputId+"'";
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
			String selectStmt = "select UnitName from TbUnitInfo where UnitName='"+username+"'";
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
	public void loadUnitName(){
		try {
			cmbUnitFind.data.clear();
			String selectStmt = "select UnitName from TbUnitInfo order by UnitName asc";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbUnitFind.data.add(rs.getString("UnitName"));
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
		txtUnitId.setText(getMaxUnitId());
		txtUnitName.setText("");
		txtInputField.setText("1");
		insert=true;
		update=false;
		find=false;
	}
	private void ComponentEnable(boolean t){
		txtUnitName.setDisable(t);
		txtInputField.setDisable(t);
		table.setDisable(t);

	}
	private String getMaxUnitId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(UnitId),0)+1)as UnitId from TbUnitInfo";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("UnitId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	private void setUnitName(){
		model.getUnitItem().clear();
		String selectStmt = "select UnitId,UnitName from TbUnitInfo order by UnitId";

		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				model.getUnitItem().add(new UnitCreateModel(rs.getString("UnitId"),rs.getString("UnitName")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private boolean checkEmptyFeild()
	{
		if(!txtUnitName.getText().trim().toString().isEmpty()){
			if(!txtInputField.getText().trim().toString().isEmpty()){
				return true;
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Input Field");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Unit Name.");
		}
		return false;
	} 
	@SuppressWarnings("unchecked")
	private void addCmp(){

		cmbUnitFind.setLayoutX(10);
		cmbUnitFind.setLayoutY(10);
		cmbUnitFind.setMinWidth(255);
		cmbUnitFind.setMinHeight(28);

		btnFind.setLayoutX(270);
		btnFind.setLayoutY(10);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);


		final TableColumn<UnitCreateModel, String> firstNameCol = new TableColumn<UnitCreateModel, String>("Unit Id");
		final TableColumn<UnitCreateModel, String> lastNameCol = new TableColumn<UnitCreateModel, String>("Unit Name");

		table.getColumns().addAll(firstNameCol, lastNameCol);

		firstNameCol.setCellValueFactory(new PropertyValueFactory<UnitCreateModel, String>("UnitId"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<UnitCreateModel, String>("UnitName"));
		table.setEditable(true);


		firstNameCol.setPrefWidth(90);
		lastNameCol.setPrefWidth(365);


		table.setPrefWidth(460);
		table.setPrefHeight(320);
		table.setEditable(true);



		table.setLayoutX(10);
		table.setLayoutY(50);

		table.setItems(model.getUnitItem());

		Label lblUnitId=new Label("Unit Id");
		lblUnitId.setLayoutX(500);
		lblUnitId.setLayoutY(160);

		txtUnitId=new TextRead();
		txtUnitId.setMaxWidth(120);
		txtUnitId.setLayoutX(600);
		txtUnitId.setLayoutY(155);
		txtUnitId.setText(getMaxUnitId());

		Label lblName=new Label("Unit Name");
		lblName.setLayoutX(500);
		lblName.setLayoutY(188);

		txtUnitName=new TextField();
		txtUnitName.setMinWidth(180);
		txtUnitName.setLayoutX(600);
		txtUnitName.setLayoutY(185);

		Label lblInputField=new Label("Input Field");
		lblInputField.setLayoutX(500);
		lblInputField.setLayoutY(218);

		txtInputField=new TextField();
		txtInputField.setMaxWidth(80);
		txtInputField.setLayoutX(600);
		txtInputField.setLayoutY(215);

		cButton.setLayoutX(475);
		cButton.setLayoutY(300);
		pane.getChildren().addAll(cmbUnitFind,btnFind,table,cButton,lblUnitId,txtUnitId,
				lblName,txtUnitName,lblInputField,txtInputField);

		final Control ob[] = {txtUnitId,txtUnitName};	
		new FocusMoveByEnter(ob);

	}


}


