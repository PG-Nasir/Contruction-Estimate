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

public class DivisionCodeCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","","","");
	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,1020, 400);;
	public Stage mainItemStage=new Stage();
	Button btnFind=new Button("Find");
	private TableView<DivisionCreateModel> table = new TableView<DivisionCreateModel>();
	FxComboBox cmbDivisionFind=new FxComboBox();
	TextField txtDivisionCode;
	TextArea txtDescription;
	TextRead txtDivisionId;

	DivisionCreateModel model=new DivisionCreateModel("", "","");
	boolean insert=true,update=false,find=false;

	public DivisionCodeCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;
		
	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Division Code Create");
		addCmp();
		btnActionEvent();
		setDivisionCode();
		ComponentEnable(true);
		ButtonEnable(true);
		loadDivisionCode();
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
		if(checkDoplicateName(cmbDivisionFind.getValue().toString())){
			try {
				String selectStmt = "select * from TbDivisionInfo where DivisionCode='"+cmbDivisionFind.getValue()+"' ";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				
				
				
				int i=0;
				while(rs.next()){
					txtDivisionId.setText(rs.getString("DivisionId"));
					txtDivisionCode.setText(rs.getString("DivisionCode"));
					txtDescription.setText(rs.getString("Description"));
					cButton.btnNew.setDisable(true);
					cButton.btnEdit.setDisable(false);					
					find=true;
				}
				

				if(find){
					cButton.btnSave.setDisable(true);
				}
				btnEditEvent();
			} catch (Exception e) {
				e.printStackTrace();
				new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid Division name never allow");
		}
	}
	private void btnSaveEvent(){
		if(checkEmptyFeild()){
			if(insert){
				if(!checkDoplicateName(txtDivisionCode.getText().trim().toString())){
					if(insertData()){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setDivisionCode();
						loadDivisionCode();
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
				if(!checkDoplicateName(txtDivisionCode.getText().trim().toString())){
					if(updateData("1")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setDivisionCode();
						loadDivisionCode();
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
					}
				}
				else{
					if(updateData("2")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setDivisionCode();
						loadDivisionCode();
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
			String DivionId=getDivisionId();
			String insertSql="insert into TbDivisionInfo (DivisionId,DivisionCode,Description,EntryTime,UserId) values "
					+ "('"+DivionId+"',"
					+ "'"+txtDivisionCode.getText().trim().toString()+"',"
					+ "'"+txtDescription.getText().trim().toString().replace("'", "`")+"',"
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
			String DivisionId=txtDivisionId.getText().trim().toString();
			String updateSql="";
			String divisionCode=txtDivisionCode.getText().trim().toString();
			if(Type.equals("1")){
				updateSql="update TbDivisionInfo set  "
						+ "DivisionCode='"+txtDivisionCode.getText().trim().toString()+"',"
						+ "Description='"+txtDescription.getText().trim().toString().replace("'", "`")+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "UserId='"+sessionBeam.getUserId()+"' where DivisionId='"+DivisionId+"'";
			}
			else{
				updateSql="update TbDivisionInfo set  "
						+ "Description='"+txtDescription.getText().trim().toString().replace("'", "`")+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "UserId='"+sessionBeam.getUserId()+"' where DivisionId='"+DivisionId+"'";
			}
			DBUtil.dbExecuteUpdate(updateSql);
			
			String sql="update TbItemInfo set DivisionId='"+DivisionId+"' where ItemCode like '"+divisionCode+"%'";
			System.out.println(sql);
			DBUtil.dbExecuteUpdate(sql);
			update=false;
			return true;
		} catch (Exception e) {
			update=true;
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false;
	}

	private boolean checkDoplicateName(String name){
		try {
			String selectStmt = "select DivisionCode from TbDivisionInfo where DivisionCode='"+name+"'";
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
	public void loadDivisionCode(){
		try {
			cmbDivisionFind.data.clear();
			String selectStmt = "select DivisionCode from TbDivisionInfo order by DivisionCode asc";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbDivisionFind.data.add(rs.getString("DivisionCode"));
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
		txtDivisionCode.requestFocus();
		ComponentEnable(false);
		setEmptyText();
	}
	private void ButtonEnable(boolean t){
		cButton.btnSave.setDisable(t);
		cButton.btnEdit.setDisable(t);
	}
	private void setEmptyText(){
		txtDivisionId.setText(getDivisionId());
		txtDivisionCode.setText("");
		txtDescription.setText("1");
		insert=true;
		update=false;
		find=false;
	}
	private void ComponentEnable(boolean t){
		txtDivisionCode.setDisable(t);
		txtDescription.setDisable(t);
		table.setDisable(t);

	}
	private String getDivisionId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(DivisionId),0)+1)as DivisionId from TbDivisionInfo";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("DivisionId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	private void setDivisionCode(){
		model.getModuleItem().clear();
		String selectStmt = "select DivisionId,DivisionCode,Description from TbDivisionInfo order by DivisionId";

		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				model.getModuleItem().add(new DivisionCreateModel(rs.getString("DivisionId"),rs.getString("DivisionCode"),rs.getString("Description")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private boolean checkEmptyFeild()
	{
		if(!txtDivisionCode.getText().trim().toString().isEmpty()){
			if(!txtDescription.getText().trim().toString().isEmpty()){
				return true;
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Description");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Division Code");
		}
		return false;
	} 
	@SuppressWarnings("unchecked")
	private void addCmp(){

		cmbDivisionFind.setLayoutX(10);
		cmbDivisionFind.setLayoutY(10);
		cmbDivisionFind.setMinWidth(255);
		cmbDivisionFind.setMinHeight(28);

		btnFind.setLayoutX(270);
		btnFind.setLayoutY(10);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);


		final TableColumn<DivisionCreateModel, String> firstNameCol = new TableColumn<DivisionCreateModel, String>("Division Id");
		final TableColumn<DivisionCreateModel, String> lastNameCol = new TableColumn<DivisionCreateModel, String>("Division Code");
		final TableColumn<DivisionCreateModel, String> descriptionCol = new TableColumn<DivisionCreateModel, String>("Description");

		table.getColumns().addAll(firstNameCol, lastNameCol,descriptionCol);

		firstNameCol.setCellValueFactory(new PropertyValueFactory<DivisionCreateModel, String>("DivisionId"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<DivisionCreateModel, String>("DivisionCode"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<DivisionCreateModel, String>("Description"));
		
		table.setEditable(true);


		firstNameCol.setPrefWidth(40);
		lastNameCol.setPrefWidth(120);
		descriptionCol.setPrefWidth(365);


		table.setPrefWidth(460);
		table.setPrefHeight(320);
		table.setEditable(true);



		table.setLayoutX(10);
		table.setLayoutY(50);

		table.setItems(model.getModuleItem());

		Label lblDivisionId=new Label("Division Id");
		lblDivisionId.setLayoutX(500);
		lblDivisionId.setLayoutY(160);

		txtDivisionId=new TextRead();
		txtDivisionId.setMaxWidth(120);
		txtDivisionId.setLayoutX(600);
		txtDivisionId.setLayoutY(155);
		txtDivisionId.setText(getDivisionId());

		Label lblDivisionCode=new Label("Division Code");
		lblDivisionCode.setLayoutX(500);
		lblDivisionCode.setLayoutY(188);

		txtDivisionCode=new TextField();
		txtDivisionCode.setMinWidth(180);
		txtDivisionCode.setLayoutX(600);
		txtDivisionCode.setLayoutY(185);

		Label lblDescription=new Label("Description");
		lblDescription.setLayoutX(500);
		lblDescription.setLayoutY(218);

		txtDescription=new TextArea();
		txtDescription.setPrefWidth(380);
		txtDescription.setPrefHeight(90);
		txtDescription.setLayoutX(600);
		txtDescription.setLayoutY(215);

		cButton.btnNew.setText("_New");
		cButton.setLayoutX(475);
		cButton.setLayoutY(320);
		pane.getChildren().addAll(cmbDivisionFind,btnFind,table,cButton,lblDivisionId,txtDivisionId,
				lblDivisionCode,txtDivisionCode,lblDescription,txtDescription);

		final Control ob[] = {txtDivisionId,txtDivisionCode};	
		new FocusMoveByEnter(ob);

	}


}


