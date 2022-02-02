package com.ContructionModule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.ComShare.CommonButton;
import com.ComShare.DBUtil;
import com.ComShare.FocusMoveByEnter;
import com.ComShare.FxComboBox;
import com.ComShare.Notification;
import com.ComShare.NumberField;
import com.ComShare.SessionBean;
import com.ComShare.TextRead;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ItemCodeCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","Preview","Print","Exit");

	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,1300, 480);;
	public Stage mainItemStage=new Stage();

	Button btnFind=new Button("Find");
	Button btnTypeAdd=new Button("+");
	Button btnDepartmentAdd=new Button("+");
	private TableView table = new TableView();
	FxComboBox cmbItemCode=new FxComboBox();
	FxComboBox cmbUnitType;



	TreeView treeView = new TreeView();
	TreeItem rootItem = new TreeItem("Primary Group");
	public TreeItem[] CategoryAll=new TreeItem[100];
	String headvalue="",selectedHead="";

	TextField txtItemCode;
	NumberField txtRate;
	TextRead txtItemId,txtCategoryName;
	TextArea txtDescription;

	private RadioButton btnItemCode=new RadioButton("Individual Item Code");
	private RadioButton btnItemRate=new RadioButton("Rate For All Item");
	private ToggleGroup gpCR=new ToggleGroup();

	boolean insert=true,update=false,find=false;

	ItemCreateModel model=new ItemCreateModel("","","","","","");
	FxComboBox cmbDivisionCode=new FxComboBox();
	public ItemCodeCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Item Create");
		addCmp();
		btnActionEvent();
		ComponentEnable(true);
		ButtonEnable(true);
		loadProductName(1);
		loadUnitName();
		setProductInfo();
		ass(rootItem,CategoryAll,headvalue="1");
		loadDivisionCode();
	}
	
	@SuppressWarnings("unchecked")
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
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

			@Override
			public void changed(
					ObservableValue<? extends TreeItem<String>> observable,
							TreeItem<String> old_val, TreeItem<String> new_val) {
				TreeItem<String> selectedItem = new_val;
				selectedHead=selectedItem.getValue().toString();
				txtCategoryName.setText(selectedHead);
			}
		});
		btnItemCode.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadProductName(1);
			}
		});

		btnItemRate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadProductName(2);
			}
		});
	}
	private void btnFindEvent(){

		if(btnItemCode.isSelected()){
			if(checkDoplicateName(cmbItemCode.getValue().toString())){
				try {

					String CatId="";
					String selectStmt = " select *,(select categoryTitle from tbCategoryInfo where categoryid=TbItemInfo.categoryId) as CategroyName,(select UnitName from tbUnitInfo where unitId=TbItemInfo.UnitId) as UnitName,(select DivisionCode from tbDivisionInfo where DivisionId=TbItemInfo.divisionId) as DivisionCode from TbItemInfo where ItemCode='"+cmbItemCode.getValue()+"' ";
					ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
					int i=0;
					String PHeadId="";
					btnRefreshEvent();
					while(rs.next()){
						txtItemId.setText(rs.getString("itemId"));
						cmbDivisionCode.setValue(rs.getString("DivisionCode")==null?"":rs.getString("DivisionCode"));
						txtItemCode.setText(rs.getString("ItemCode"));
						txtRate.setText(rs.getString("Rate").toString().trim());
						txtDescription.setText(rs.getString("Description"));
						CatId=rs.getString("categoryId");
						txtCategoryName.setText(rs.getString("CategroyName"));
						cmbUnitType.setValue(rs.getString("UnitName"));
						cButton.btnNew.setDisable(true);
						cButton.btnEdit.setDisable(false);
						find=true;
					}


					String selectStmt1= "select categoryTitle from tbCategoryInfo where categoryid='"+CatId+"' ";
					ResultSet rs1 = DBUtil.dbExecuteQuery(selectStmt1);

					String PHeadName="";
					while(rs1.next()){
						PHeadName=rs1.getString("categoryTitle");
					}

					System.out.println("PHeadName "+PHeadName);
					for(int a=0;a<treeView.getExpandedItemCount();a++){
						String value=treeView.getTreeItem(a).toString();
						String SendValue="";
						StringTokenizer token=new StringTokenizer(value.toString().substring(value.toString().indexOf("[")+2, value.toString().indexOf("]")),":");
						while(token.hasMoreTokens()){
							SendValue=token.nextToken().trim().toString();
						}
						if(SendValue.toString().equals(PHeadName)){
							treeView.getSelectionModel().select(treeView.getTreeItem(a));
						}
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
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid name never allow");
			}
		}
		else if(btnItemRate.isSelected()){
			model.getProductItem().clear();
			String selectStmt = "select itemId,itemcode,description,(select categoryTitle from tbCategoryInfo where categoryid=TbItemInfo.categoryId) as CategroyName,(select UnitName from tbUnitInfo where unitId=TbItemInfo.UnitId) as UnitName,Rate from TbItemInfo where Rate='"+cmbItemCode.getValue()+"' order by categoryId,itemcode";
			try {
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				while(rs.next()){
					System.out.println("itemId "+rs.getString("itemId"));
					model.getProductItem().add(new ItemCreateModel(rs.getString("itemId"),rs.getString("itemcode"),rs.getString("description"),rs.getString("CategroyName"),rs.getString("UnitName"),Double.toString(Double.parseDouble(rs.getString("Rate").toString().isEmpty()?"0":rs.getString("Rate").toString()))));
				}
				table.setDisable(false);
			} catch (Exception e) {
				e.printStackTrace();
				new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
			}
		}

	}
	private void btnSaveEvent(){
		if(checkEmptyFeild()){
			if(insert){
				if(!checkDoplicateName(txtItemCode.getText().trim().toString())){
					if(insertData()){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						loadProductName(1);
						setProductInfo();
						rootItem.getChildren().clear();
						ass(rootItem,CategoryAll,headvalue="1");
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
				if(!checkDoplicateName(txtCategoryName.getText().trim().toString())){
					if(updateData("1")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						loadProductName(1);
						setProductInfo();
						rootItem.getChildren().clear();
						ass(rootItem,CategoryAll,headvalue="1");
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
					}
				}
				else{
					if(updateData("2")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						loadProductName(1);
						setProductInfo();
						rootItem.getChildren().clear();
						ass(rootItem,CategoryAll,headvalue="1");
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

			String catId=getCategoryId(txtCategoryName.getText().trim().toString());
			String unitId=getUnitId(cmbUnitType.getValue().toString());
			String divisionId=getDivisionId(cmbDivisionCode.getValue().toString());
			double Rate=Double.parseDouble(txtRate.getText().trim().toString());
			//double ActualRate=Rate+(Rate*5/100);

			String insertSql="insert into TbItemInfo "
					+ "("
					+ "itemCode,"
					+ "categoryId,"
					+ "divisionId,"
					+ "UnitId,"
					+ "Rate,"
					+ "Description,"
					+ "date,"
					+ "EntryTime,"
					+ "CreateBy,UserIp,PcName)"
					+ " values "
					+ "("
					+ "'"+txtItemCode.getText().trim().toString()+"',"
					+ "'"+catId+"',"
					+ "'"+divisionId+"',"
					+ "'"+unitId+"',"
					+ "'"+Rate+"',"
					//+ "'"+ActualRate+"',"
					+ "'"+txtDescription.getText().trim().toString().replaceAll("'", "`")+"',"
					+ "CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','"+sessionBeam.getPCName()+"')";


			DBUtil.dbExecuteUpdate(insertSql);

			insert=false;
			return true;
		} catch (Exception e) {

			insert=true;
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
			return false;
		}

	}
	private boolean updateData(String Type){
		try {
			String proId=txtItemId.getText().trim().toString();
			String catId=getCategoryId(txtCategoryName.getText().trim().toString());
			String unitId=getUnitId(cmbUnitType.getValue().toString());
			String divisionId=getDivisionId(cmbDivisionCode.getValue().toString());
			double Rate=Double.parseDouble(txtRate.getText().trim().toString());
			//double ActualRate=Rate+(Rate*5/100);

			String updateSql="";
			if(Type.equals("1")){
				updateSql="update TbItemInfo set  "
						+ "itemCode='"+txtItemCode.getText().trim().toString()+"',"
						+ "categoryId='"+catId+"',"
						+ "divisionId='"+divisionId+"',"
						+ "UnitId='"+unitId+"',"
						+ "Rate='"+Rate+"',"
						//+ "Rate='"+ActualRate+"',"
						+ "Description='"+txtDescription.getText().trim().toString().replaceAll("'", "`")+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where itemId='"+proId+"'";
			}
			else{
				updateSql="update TbItemInfo set  "
						+ "categoryId='"+catId+"',"
						+ "divisionId='"+divisionId+"',"
						+ "UnitId='"+unitId+"',"
						+ "Rate='"+Rate+"',"
						//+ "Rate='"+ActualRate+"',"
						+ "Description='"+txtDescription.getText().trim().toString().replaceAll("'", "`")+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "CreateBy='"+sessionBeam.getUserId()+"' where itemId='"+proId+"'";
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

	private void setProductInfo(){
		model.getProductItem().clear();
		String selectStmt = "select itemId,itemcode,description,(select categoryTitle from tbCategoryInfo where categoryid=TbItemInfo.categoryId) as CategroyName,(select UnitName from tbUnitInfo where unitId=TbItemInfo.UnitId) as UnitName,Rate from TbItemInfo order by categoryId,itemcode";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				System.out.println("itemId "+rs.getString("itemId"));
				model.getProductItem().add(new ItemCreateModel(rs.getString("itemId"),rs.getString("itemcode"),rs.getString("description"),rs.getString("CategroyName"),rs.getString("UnitName"),Double.toString(Double.parseDouble(rs.getString("Rate").toString().isEmpty()?"0":rs.getString("Rate").toString()))));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}

	private boolean checkDoplicateName(String name){
		try {
			String selectStmt = "select itemCode from TbItemInfo where itemCode='"+name+"'";
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
	public void loadProductName(int type){
		try {
			cmbItemCode.data.clear();
			String selectStmt="";
			if(type==1){
				selectStmt = "select itemCode as Value from TbItemInfo order by itemCode asc";
			}
			else if(type==2){
				selectStmt = "select Rate as Value from TbItemInfo order by Rate asc";
			}

			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbItemCode.data.add(rs.getString("Value"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}



	public void loadUnitName(){
		try {
			cmbUnitType.data.clear();
			String selectStmt = "select unitName from tbUnitInfo order by unitName asc";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbUnitType.data.add(rs.getString("unitName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	public void loadDivisionCode(){
		try {
			cmbDivisionCode.data.clear();
			String selectStmt = "select DivisionCode from TbDivisionInfo order by DivisionCode asc";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbDivisionCode.data.add(rs.getString("DivisionCode"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private String getCategoryId(String CatName){
		String Id="";

		try {
			String selectStmt = "select categoryid from tbCategoryInfo where categoryTitle='"+CatName+"'";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("categoryid");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}


	private String getUnitId(String UnitName){
		String Id="";

		try {
			String selectStmt = "select UnitId from tbUnitInfo where UnitName='"+UnitName+"'";
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
	private String getDivisionId(String DivisionName){
		String Id="";

		try {
			if(DivisionName!=null){
				String selectStmt = "select DivisionId from tbDivisionInfo where DivisionCode='"+DivisionName+"'";
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				while(rs.next()){
					Id=rs.getString("DivisionId");
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}
	private String getMaxItemId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(itemId),0)+1)as itemId from TbItemInfo";
		try {

			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("itemId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	private String getParentid(String pHeadName){
		String Id="";
		String selectStmt = "select categoryid from tbCategoryInfo where categoryTitle='"+pHeadName+"'";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("categoryid");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	public void ass(TreeItem Prarent,TreeItem[] all,String headvalue){


		String selectStmt = "select tbCategoryInfo.categoryTitle from tbCategoryInfo where pheadId='"+headvalue+"'";
		try {
			int i=0;
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				all[i]=new TreeItem(rs.getString("categoryTitle"));
				Prarent.getChildren().add(all[i]);
				Prarent.setExpanded(true);
				i++;
			}
			loadSubHead(i,all);
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	private void loadSubHead(int i,TreeItem[] sendNode){

		try {
			String id="",parentId="";
			int j=0;
			int temp=0;
			TreeItem[] newItem=new TreeItem[100];
			for(j=0;j<i;j++){
				int k=0;
				String SendValue="";
				//System.out.println("Ac "+sendNode[j].toString());
				StringTokenizer token=new StringTokenizer(sendNode[j].toString().substring(sendNode[j].toString().indexOf("[")+2, sendNode[j].toString().indexOf("]")),":");
				while(token.hasMoreTokens()){
					SendValue=token.nextToken().trim().toString();
				}


				String selectStmt = "select categoryid,pheadId from tbCategoryInfo where tbCategoryInfo.categoryTitle='"+SendValue+"' and pHeadId!=0 ";
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				while(rs.next()){
					id=rs.getString("categoryid");
					parentId=rs.getString("pheadId");
				}

				String selectStmt1 = "select tbCategoryInfo.categoryTitle from tbCategoryInfo where pHeadId='"+id+"' and pHeadId!=0";
				ResultSet rs1 = DBUtil.dbExecuteQuery(selectStmt1);
				while(rs1.next()){
					newItem[k]=new TreeItem(rs1.getString("categoryTitle"));
					sendNode[j].getChildren().add(newItem[k]);
					sendNode[j].setExpanded(true);
					k++;
				}

				loadSubHead(k,newItem);
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
		txtItemId.setText(getMaxItemId());
		cmbDivisionCode.setValue("");
		txtItemCode.setText("");
		txtDescription.setText("");
		txtCategoryName.setText("");
		cmbUnitType.setValue("");
		txtRate.setText("");
		insert=true;
		update=false;
		find=false;
	}
	private void ComponentEnable(boolean t){

		cmbDivisionCode.setDisable(t);
		txtItemCode.setDisable(t);
		txtCategoryName.setDisable(t);
		txtDescription.setDisable(t);
		cmbUnitType.setDisable(t);
		txtRate.setDisable(t);
		treeView.setDisable(t);
		table.setDisable(t);
	}
	private boolean checkEmptyFeild()
	{
		if(!txtItemCode.getText().trim().toString().isEmpty()){
			if(!txtCategoryName.getText().trim().toString().isEmpty()){
				if(cmbUnitType.getValue()!=null){
					if(!txtRate.getText().trim().toString().isEmpty()){
						return true;
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Rate");
					}
				}
				else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Unit Type");
				}
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Group");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Name.");
		}

		return false;
	} 

	private void addCmp(){

		Label lblSearchBy=new Label("Search By");
		lblSearchBy.setLayoutX(10);
		lblSearchBy.setLayoutY(10);


		btnItemCode.setMinWidth(140);
		btnItemCode.setMinHeight(28);
		btnItemCode.setLayoutX(80);
		btnItemCode.setLayoutY(5);
		//btnForm.setId("btnModule");
		btnItemCode.setToggleGroup(gpCR);
		btnItemCode.setId("form");
		btnItemCode.setSelected(true);

		btnItemRate.setMinWidth(160);
		btnItemRate.setMinHeight(28);
		btnItemRate.setLayoutX(235);
		btnItemRate.setLayoutY(5);
		//btnForm.setId("btnModule");
		btnItemRate.setToggleGroup(gpCR);
		btnItemRate.setId("form");

		cmbItemCode.setLayoutX(10);
		cmbItemCode.setLayoutY(40);
		cmbItemCode.setPrefWidth(255);
		cmbItemCode.setMinHeight(28);

		btnFind.setLayoutX(270);
		btnFind.setLayoutY(40);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);



		treeView.setLayoutX(10);
		treeView.setLayoutY(70);
		treeView.setPrefHeight(370);
		treeView.setPrefWidth(260);
		treeView.setRoot(rootItem);

		rootItem.setExpanded(true);

		Label lblItemId=new Label("Item Id");
		lblItemId.setLayoutX(280);
		lblItemId.setLayoutY(85);

		txtItemId=new TextRead();
		txtItemId.setMaxWidth(120);
		txtItemId.setLayoutX(375);
		txtItemId.setLayoutY(80);

		Label lblDivisionCode=new Label("Division Code");
		lblDivisionCode.setLayoutX(280);
		lblDivisionCode.setLayoutY(115);

		cmbDivisionCode=new FxComboBox<>();
		cmbDivisionCode.setMinWidth(180);
		cmbDivisionCode.setLayoutX(375);
		cmbDivisionCode.setLayoutY(110);

		Label lblItemCode=new Label("Item Code");
		lblItemCode.setLayoutX(280);
		lblItemCode.setLayoutY(145);

		txtItemCode=new TextField();
		txtItemCode.setMinWidth(180);
		txtItemCode.setLayoutX(375);
		txtItemCode.setLayoutY(140);

		Label lblItemIdR=new Label("*");
		lblItemIdR.setLayoutX(560);
		lblItemIdR.setLayoutY(145);
		lblItemIdR.setId("Required");

		Label lblCategoryName=new Label("Group");
		lblCategoryName.setLayoutX(280);
		lblCategoryName.setLayoutY(175);

		txtCategoryName=new TextRead();
		txtCategoryName.setMinWidth(180);
		txtCategoryName.setLayoutX(375);
		txtCategoryName.setLayoutY(170);

		Label lblCategoryNameR=new Label("*");
		lblCategoryNameR.setLayoutX(560);
		lblCategoryNameR.setLayoutY(157);
		lblCategoryNameR.setId("Required");


		Label lblDescription=new Label("Description");
		lblDescription.setLayoutX(280);
		lblDescription.setLayoutY(205);

		txtDescription=new TextArea();
		txtDescription.setMaxWidth(230);
		txtDescription.setMaxHeight(55);
		txtDescription.setLayoutX(375);
		txtDescription.setLayoutY(200);

		Label lblDescriptionR=new Label("*");
		lblDescriptionR.setLayoutX(605);
		lblDescriptionR.setLayoutY(205);
		lblDescriptionR.setId("Required");


		Label lblUnitType=new Label("Unit Type");
		lblUnitType.setLayoutX(280);
		lblUnitType.setLayoutY(265);

		cmbUnitType=new FxComboBox<>();
		cmbUnitType.setMinWidth(200);
		cmbUnitType.setLayoutX(375);
		cmbUnitType.setLayoutY(260);

		Label lblUnitTypeR=new Label("*");
		lblUnitTypeR.setLayoutX(580);
		lblUnitTypeR.setLayoutY(265);
		lblUnitTypeR.setId("Required");

		Label lblRate=new Label("Rate");
		lblRate.setLayoutX(280);
		lblRate.setLayoutY(295);

		txtRate=new NumberField();
		txtRate.setMaxWidth(120);
		txtRate.setLayoutX(375);
		txtRate.setLayoutY(290);

		Label lblRetailPriceR=new Label("*");
		lblRetailPriceR.setLayoutX(500);
		lblRetailPriceR.setLayoutY(295);
		lblRetailPriceR.setId("Required");


		TableColumn IdCol = new TableColumn("Id");
		IdCol.setMinWidth(50);
		IdCol.setCellValueFactory(new PropertyValueFactory<ItemCreateModel, Label>("ProductId"));

		TableColumn NameCol = new TableColumn("Item Code");
		NameCol.setMinWidth(100);
		NameCol.setCellValueFactory(new PropertyValueFactory<ItemCreateModel, Label>("ItemCode"));

		TableColumn DescriptionCol = new TableColumn("Description");
		DescriptionCol.setMinWidth(425);
		DescriptionCol.setCellValueFactory(new PropertyValueFactory<ItemCreateModel, Label>("Description"));

		/*		TableColumn catCol = new TableColumn("Category");
		catCol.setMinWidth(80);
		catCol.setCellValueFactory(new PropertyValueFactory<ItemCreateModel, Label>("CategoryName"));


		TableColumn UnitCol = new TableColumn("Unit");
		UnitCol.setMinWidth(50);
		UnitCol.setCellValueFactory(new PropertyValueFactory<ItemCreateModel, Label>("UnitName"));*/

		TableColumn OpeningStockCol = new TableColumn("Rate");
		OpeningStockCol.setMinWidth(80);
		OpeningStockCol.setCellValueFactory(new PropertyValueFactory<ItemCreateModel, Label>("Rate"));

		table.setPrefWidth(680);
		table.setPrefHeight(370);
		table.setItems(model.getProductItem());
		table.getColumns().addAll(IdCol, NameCol,DescriptionCol,OpeningStockCol);
		table.setLayoutX(610);
		table.setLayoutY(50);


		cButton.setLayoutX(140);
		cButton.setLayoutY(440);

		pane.getChildren().addAll(lblSearchBy,btnItemCode,btnItemRate,lblDivisionCode,cmbDivisionCode,cmbItemCode,btnFind,treeView,cButton,lblItemId,txtItemId,lblItemIdR,
				lblItemCode,txtItemCode,lblCategoryName,txtCategoryName,lblCategoryNameR,lblDescription,txtDescription,lblDescriptionR,
				lblUnitType,cmbUnitType,lblUnitTypeR,lblRate,txtRate,lblRetailPriceR
				,table);

		final Control ob[] = {txtItemId,txtCategoryName};	
		new FocusMoveByEnter(ob);

	}
}


