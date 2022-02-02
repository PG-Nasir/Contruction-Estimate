package com.ContructionModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.ptg.AddPtg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ComShare.CommonButton;
import com.ComShare.DBUtil;
import com.ComShare.FocusMoveByEnter;
import com.ComShare.FxComboBox;
import com.ComShare.Notification;
import com.ComShare.NumberField;
import com.ComShare.SessionBean;
import com.ComShare.TableUtils;
import com.ComShare.TextRead;
import com.Setting.AuthenticaionModel;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import com.sun.javafx.scene.control.skin.TableViewSkin;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class EstimateCreate extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();
	
	

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","Preview","","");
	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,1350, 660);;
	public Stage mainItemStage=new Stage();
	
	Button btnPaste=new Button("Paste");
	Button btnFind=new Button("Find");
	Button btnExport=new Button("Export");
	Button btnImport=new Button("Import");

	private TableView<AditionalItemModel> tableAditional = new TableView<>();
	private TableView<EstimateCreateModel> table = new TableView<>();
	FxComboBox cmbProjectFind=new FxComboBox();
	FxComboBox cmbItemCode=new FxComboBox();
	FxComboBox cmbDescription=new FxComboBox();
	FxComboBox cmbUnit= new FxComboBox<>();
	FxComboBox cmbaditionalItemCode=new FxComboBox();

	CheckBox checkaditionalItemCode=new CheckBox();

	CheckBox checkCsv=new CheckBox("CSV");

	TextField txtProjectName,txtProjectCode,txtADP;
	TextRead txtProjectId,txtGrandTotal,txtTotalRate,txtTotalQty;
	TextArea txtDescription;

	private CheckBox headerEntryCheckbox= new CheckBox("Deduction");
	private CheckBox headerCopyCheckbox= new CheckBox("Copy");
	
	
	private boolean headerEntryCheckboxAction;

	private CheckBox headerItemAditionalCheckbox;


	private CheckBox headerRefreshAditional;
	private boolean headerAditionalCheckboxAction;

	private RadioButton btnEstimate=new RadioButton("Estimate");
	private RadioButton btnSchedule=new RadioButton("Schedule");
	private RadioButton btnSalvage=new RadioButton("Salvage");
	private ToggleGroup gpds=new ToggleGroup();

	private RadioButton btnOtm=new RadioButton("OTM");
	private RadioButton btnLtm=new RadioButton("LTM");

	private ToggleGroup gpwq=new ToggleGroup();

	Button btnNewAdd=new Button("New _Item");

	AditionalItemModel modeladitional=new AditionalItemModel(new Boolean(false),null,"","");
	EstimateCreateModel model=new EstimateCreateModel("","",null,null,null,new Boolean(false),"","",null,"","","","",null,"","","","",null,"","","","",new Boolean(false),new Boolean(false),modeladitional);
	boolean insert=true,update=false,find=false;

	DecimalFormat df = new DecimalFormat("#.000");

	CheckBox checkSalvageDeduction=new CheckBox("Salvage Deduction");

	TextField txtSalvageItemCode,txtSalvageDescription,txtSalvageQty,txtSalvageRate;

	TextRead txtSalvageAmount;


	private ContextMenu tableContextMenu;

	//private Dialog<ButtonType> checkAditionalCol;
	public EstimateCreate(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}

	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Estimate Create");
		addCmp();
		btnActionEvent();
		setUnit();
		setItemCode();

		ComponentEnable(true);
		ButtonEnable(true);
		loadProjectName();
		setAditionalItemCode();
		btnNewEvent();
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
		btnNewAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnAddNewRow();
			}
		});

		btnPaste.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnPasteEvent();
			}
		});
		
		cButton.btnPreview.setOnAction(new EventHandler<ActionEvent>()
		{
			@SuppressWarnings("unchecked")
			public void handle(ActionEvent e)
			{
				btnPreviewEvent();
			}
		});

		btnExport.setOnAction(new EventHandler<ActionEvent>()
		{
			@SuppressWarnings("unchecked")
			public void handle(ActionEvent e)
			{
				btnExportEvent();
			}
		});

		btnImport.setOnAction(new EventHandler<ActionEvent>()
		{
			@SuppressWarnings("unchecked")
			public void handle(ActionEvent e)
			{
				btnImportEvent();
			}
		});

		headerRefreshAditional.setOnAction(event -> {

			setAditionalItemCode();

		});
		headerEntryCheckbox.setOnAction(event -> {
			System.out.println("Deduction ");
			setGrandTotal();
		});

		headerCopyCheckbox.setOnAction(event -> {
			System.out.println("Deduction ");
			setGrandTotal();
		});

		txtSalvageQty.textProperty().addListener((observable, oldValue, newValue) -> {
			
			if(checkSalvageDeduction.isSelected()){
				Double salvateQty=Double.parseDouble(txtSalvageQty.getText().trim().toString().isEmpty()?"0":txtSalvageQty.getText().trim().toString());
				Double salvateRate=Double.parseDouble(txtSalvageRate.getText().trim().toString().isEmpty()?"0":txtSalvageRate.getText().trim().toString());
				
				Double salvateAmount=salvateQty*salvateRate;
				txtSalvageAmount.setText(df.format(salvateAmount));
				setGrandTotal();
			}
			
		});
		
		txtSalvageRate.textProperty().addListener((observable, oldValue, newValue) -> {
			if(checkSalvageDeduction.isSelected()){
				Double salvateQty=Double.parseDouble(txtSalvageQty.getText().trim().toString().isEmpty()?"0":txtSalvageQty.getText().trim().toString());
				Double salvateRate=Double.parseDouble(txtSalvageRate.getText().trim().toString().isEmpty()?"0":txtSalvageRate.getText().trim().toString());
				
				Double salvateAmount=salvateQty*salvateRate;
				txtSalvageAmount.setText(df.format(salvateAmount));
				setGrandTotal();
			}
			
		});
		
	}

	private void ExportEstimateInCSV(String sql){
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Export to .csv");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files", "*.csv"));
			File file = fileChooser.showSaveDialog(new Stage());
			if (file == null){
				return;
			}

			ResultSet rs = DBUtil.dbExecuteQuery(sql);
			String ADP="";
			StringBuilder sb = new StringBuilder();
			sb.append("Project Id");
			sb.append(',');
			sb.append("Project Name");
			sb.append(',');
			sb.append("Project Code");
			sb.append(',');
			sb.append("ADP No");
			sb.append(',');
			sb.append("Description");
			sb.append(',');
			sb.append("Deduction");
			sb.append(',');
			sb.append("Text");
			sb.append(',');
			sb.append("Item Code");
			sb.append(',');
			sb.append("Aditional Code");
			sb.append(',');
			sb.append("Part");
			sb.append(',');
			sb.append("Item Description");
			sb.append(',');
			sb.append("Unit");
			sb.append(',');
			sb.append("Nos");
			sb.append(',');
			sb.append("Nos Unit");
			sb.append(',');
			sb.append("Site");
			sb.append(',');
			sb.append("Lenght");
			sb.append(',');
			sb.append("Breadth");
			sb.append(',');
			sb.append("Height");
			sb.append(',');
			sb.append("LBHUnit");
			sb.append(',');
			sb.append("Qty");
			sb.append(',');
			sb.append("Rate");
			sb.append(',');
			sb.append("Amount");
			sb.append(',');
			sb.append("Total Qty");
			sb.append(',');
			sb.append("Total Rate");
			sb.append(',');
			sb.append("Total Amount");
			sb.append('\n');

			while(rs.next()) {
				sb.append(rs.getString("ProjectId"));
				sb.append(',');
				sb.append(rs.getString("ProjectName"));
				sb.append(',');
				sb.append(rs.getString("ProjectCode"));
				sb.append(',');

				ADP=rs.getString("ADPNO");

				sb.append(ADP);
				sb.append(',');

				String projectDescription=rs.getString("description");

				if(projectDescription.length()>70){
					projectDescription=projectDescription.substring(0,70);
				}
				sb.append(projectDescription);
				sb.append(',');


				boolean deductionvalue=rs.getString("deduction").toString().equals("0")?false:true;


				sb.append(deductionvalue);
				sb.append(',');

				sb.append(rs.getString("ItemText"));
				sb.append(',');

				sb.append(rs.getString("ItemCode1"));
				sb.append(',');

				sb.append(rs.getString("ItemCode2"));
				sb.append(',');

				sb.append(rs.getString("Part"));
				sb.append(',');

				String itemDescription=rs.getString("ItemDescription");


				String ItemCodeC=rs.getString("ItemCode1").substring(0, 3).trim();
				if(ItemCodeC.equals("000")){
					System.out.println("Match "+ItemCodeC);
					itemDescription=rs.getString("Description00000");
				}

				if(itemDescription.length()>70){
					itemDescription=itemDescription.substring(0,70);
				}


				sb.append(itemDescription);
				sb.append(',');

				sb.append(rs.getString("Unit"));
				sb.append(',');

				sb.append(rs.getString("Nos"));
				sb.append(',');

				sb.append(rs.getString("NSUnit"));
				sb.append(',');

				sb.append(rs.getString("Site"));
				sb.append(',');

				sb.append(rs.getString("Length"));
				sb.append(',');

				sb.append(rs.getString("Base"));
				sb.append(',');

				sb.append(rs.getString("Height"));
				sb.append(',');

				sb.append(rs.getString("LBHUnit"));
				sb.append(',');

				sb.append(rs.getString("Qty"));
				sb.append(',');

				sb.append(rs.getString("Rate"));
				sb.append(',');

				sb.append(rs.getString("Amount"));
				sb.append(',');

				sb.append(rs.getString("TotalQty"));
				sb.append(',');

				sb.append(rs.getString("TotalRate"));
				sb.append(',');

				sb.append(rs.getString("TotalAmount"));
				sb.append('\n');

			}

			//String outputDirPath = "E:/CHTDB/"+ADP+"Estimate.csv";
			PrintWriter pw = new PrintWriter(file);

			pw.write(sb.toString());
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!!,"+e.getMessage());
		}
	}
	
	private void ExportSalvageInCSV(){
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Export to .csv");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files", "*.csv"));
			File file = fileChooser.showSaveDialog(new Stage());
			if (file == null){
				return;
			}


			String ADP="";
			StringBuilder sb = new StringBuilder();
			sb.append("Project Id");
			sb.append(',');
			sb.append("Project Name");
			sb.append(',');
			sb.append("Adp No");
			sb.append(',');
			sb.append("Item Code");
			sb.append(',');
			sb.append("Description");
			sb.append(',');
			sb.append("Qty");
			sb.append(',');
			sb.append("Rate");
			sb.append(',');
			sb.append("Amount");
			sb.append('\n');
					
			sb.append(txtProjectId.getText().trim().toString());
			sb.append(',');
			sb.append(txtProjectName.getText().trim().toString());
			sb.append(',');
			sb.append(txtADP.getText().trim().toString());
			
			sb.append(txtSalvageItemCode.getText().trim().toString());
			sb.append(',');
			sb.append(txtSalvageDescription.getText().trim().toString());
			sb.append(',');
			sb.append(txtSalvageQty.getText().trim().toString());
			sb.append(',');		
			sb.append(txtSalvageRate.getText().trim().toString());
			sb.append(',');	
			sb.append(txtSalvageAmount.getText().trim().toString());
			sb.append('\n');
			
			PrintWriter pw = new PrintWriter(file);

			pw.write(sb.toString());
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!!,"+e.getMessage());
		}
	}
	
	private void ExportScheduleInExcelLTM(String sql){
		try {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Export to .csv");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files", "*.csv"));
			File file = fileChooser.showSaveDialog(new Stage());
			if (file == null){
				return;
			}

			ResultSet rs = DBUtil.dbExecuteQuery(sql);
			String ADP="";
			int row = 1;


			StringBuilder sb = new StringBuilder();
			sb.append("Item No");
			sb.append(',');
			sb.append("Group Name");
			sb.append(',');
			sb.append("Item Code");
			sb.append(',');
			sb.append("Description");
			sb.append(',');
			sb.append("Unit");
			sb.append(',');
			sb.append("Qty");
			sb.append(',');
			sb.append("Rate");
			sb.append(',');
			sb.append("Amount");
			/*	        sb.append(',');
	        sb.append("Deduction");*/
			sb.append('\n');
			while(rs.next()) {
				ADP = rs.getString("ADPNO");
				String GroupName = rs.getString("GroupName");
				String ItemCode1 = rs.getString("ItemCode1");
				String itemD=rs.getString("ItemDescription")==null?"":rs.getString("ItemDescription");
				String ItemDescription = itemD.replace(",", "");
				String Unit = rs.getString("Unit");
				String Qty = rs.getString("Qty");
				String Rate = rs.getString("Rate");
				String Amount = rs.getString("Amount");
				String Deduction = rs.getString("Deduction").equals("0")?"No":"Yes";


				sb.append(row);
				sb.append(',');
				sb.append(GroupName);
				sb.append(',');
				sb.append(ItemCode1);
				sb.append(',');
				sb.append(ItemDescription);
				sb.append(',');
				sb.append(Unit);
				sb.append(',');
				sb.append(df.format(Double.parseDouble(Qty)));
				sb.append(',');
				sb.append(df.format(Double.parseDouble(Rate)));
				sb.append(',');
				sb.append(df.format(Double.parseDouble(Amount)));
				/*		        sb.append(',');
		        sb.append(Deduction);*/
				sb.append('\n');

				row = row + 1;

			}

			//String outputDirPath = "E:/CHTDB/"+ADP+"LTM.csv";
			PrintWriter pw = new PrintWriter(file);

			pw.write(sb.toString());
			pw.close();


		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!!,"+e.getMessage());
		}
	}

	private void ExportScheduleInExcelOTM(String sql){
		try {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Export to .csv");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files", "*.csv"));
			File file = fileChooser.showSaveDialog(new Stage());
			if (file == null){
				return;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("Item No");
			sb.append(',');
			sb.append("Group Name");
			sb.append(',');
			sb.append("Item Code");
			sb.append(',');
			sb.append("Description");
			sb.append(',');
			sb.append("Unit");
			sb.append(',');
			sb.append("Qty");
			/*     sb.append(',');
	        sb.append("Deduction");*/
			sb.append('\n');
			ResultSet rs = DBUtil.dbExecuteQuery(sql);
			int row = 1;
			String ADP="";
			while(rs.next()) {
				ADP = rs.getString("ADPNO");
				String GroupName = rs.getString("GroupName");
				String ItemCode1 = rs.getString("ItemCode1");
				String itemD=rs.getString("ItemDescription")==null?"":rs.getString("ItemDescription");
				String ItemDescription = itemD.replace(",", "");
				String Unit = rs.getString("Unit");
				String Qty = rs.getString("Qty");
				//String Deduction = rs.getString("Deduction").equals("0")?"No":"Yes";


				sb.append(row);
				sb.append(',');
				sb.append(GroupName);
				sb.append(',');
				sb.append(ItemCode1);
				sb.append(',');
				sb.append(ItemDescription);
				sb.append(',');
				sb.append(Unit);
				sb.append(',');
				sb.append(df.format(Double.parseDouble(Qty)));
				/*		        sb.append(',');
		        sb.append(Deduction);*/
				sb.append('\n');

				row = row + 1;
			}



			//String outputDirPath = "E:/CHTDB/"+ADP+"OTM.csv";
			PrintWriter pw = new PrintWriter(file);

			pw.write(sb.toString());
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!!,"+e.getMessage());
		}
	}
	private void btnExportEvent(){
		String ProjectId="",AdpNo="";
		StringTokenizer token=new StringTokenizer(cmbProjectFind.getValue().toString(),"/");
		while(token.hasMoreTokens()){
			ProjectId=token.nextToken();
			AdpNo=token.nextToken();
			System.out.println("ProjectId "+ProjectId);
			System.out.println("AdpNo "+AdpNo);
			break;
		}
		if(btnSchedule.isSelected()){

			if(checkCsv.isSelected()){
				if(btnLtm.isSelected()){
					ExportScheduleInExcelLTM("select  b.Deduction,b.ItemText,b.ItemCode1,b.ItemCode2,(select CategoryTitle from tbCategoryInfo where categoryId=(select categoryId from TbItemInfo where ItemCode=b.ItemCode1)) as GroupName,(select Description from TbItemInfo where ItemCode=b.ItemCode1) as ItemDescription,b.Unit,sum(b.Nos) as Nos,sum(b.length) as length,sum(b.Base) as Base,sum(b.Height) as Height,sum(b.Qty) as Qty,b.rate,sum(b.Amount) as Amount,a.projectid,a.projectCode,a.projectName,a.AdpNo,a.description,a.TotalQty,a.TotalRate,a.TotalAmount from tbprojectinfo a join tbprojectdetails b on a.projectId=b.projectId where a.projectid='"+ProjectId+"' and b.Deduction='0' group by b.ItemCode1,b.Deduction order by b.autoId ");
				}
				else{
					ExportScheduleInExcelOTM("select  b.Deduction,b.ItemText,b.ItemCode1,b.ItemCode2,(select CategoryTitle from tbCategoryInfo where categoryId=(select categoryId from TbItemInfo where ItemCode=b.ItemCode1)) as GroupName,(select Description from TbItemInfo where ItemCode=b.ItemCode1) as ItemDescription,b.Unit,sum(b.Nos) as Nos,sum(b.length) as length,sum(b.Base) as Base,sum(b.Height) as Height,sum(b.Qty) as Qty,b.rate,sum(b.Amount) as Amount,a.projectid,a.projectCode,a.projectName,a.AdpNo,a.description,a.TotalQty,a.TotalRate,a.TotalAmount from tbprojectinfo a join tbprojectdetails b on a.projectId=b.projectId where a.projectid='"+ProjectId+"' and b.Deduction='0' group by b.ItemCode1,b.Deduction order by b.autoId");	
				}
			}

		}
		else if(btnEstimate.isSelected()){
			ExportEstimateInCSV("select b.ItemText,b.ItemCode1,b.ItemCode2,(select Description from TbItemInfo where ItemCode=b.ItemCode1) as ItemDescription,b.Unit,b.Part,b.Description as Description00000,b.Nos,  b.Site,ifnull(b.NSUnit,'') as NSUnit,b.length,b.Base,b.Height,ifnull(b.LBHUnit,'') as LBHUnit,printf('%.3f', b.Qty) as Qty,printf('%.3f', b.rate) as rate,printf('%.3f', b.Amount) as Amount,b.deduction,a.projectid,a.projectCode,a.projectName,a.AdpNo,a.description,printf('%.3f', a.TotalQty) as TotalQty,printf('%.3f', a.TotalRate) as TotalRate,printf('%.3f', a.TotalAmount) as TotalAmount from tbprojectinfo a join tbprojectdetails b on a.projectId=b.projectId where a.projectid='"+ProjectId+"' ");
		}
		else if(btnSalvage.isSelected()){
			ExportSalvageInCSV();
		}
	}

	private void btnImportEvent(){
		try {
			
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(null);

			if (selectedFile != null) {
				btnRefreshEvent();
				model.getItemCodeList().clear();
				String FieldDelimiter = ",";

				BufferedReader br;
				br = new BufferedReader(new FileReader(selectedFile));

				int i=1;

				String line;
				while ((line = br.readLine()) != null) {
					String[] fields = line.split(FieldDelimiter, -1);
					if(i>=2){
						txtProjectId.setText(fields[0]);
						txtProjectName.setText(fields[1]);
						txtProjectCode.setText(fields[2]);
						txtADP.setText(fields[3]);
						txtDescription.setText(fields[4]);

						txtTotalQty.setText(fields[22]);
						txtTotalRate.setText(fields[23]);
						txtGrandTotal.setText(fields[24]);
						
						EstimateCreateModel record = new EstimateCreateModel(Integer.toString(i),fields[6],cmbItemCode,fields[7],checkaditionalItemCode,new Boolean(fields[5]),fields[8],fields[9],cmbDescription,fields[10],fields[11],fields[12],fields[14],cmbUnit,fields[13],fields[15],fields[16],fields[17],cmbUnit,fields[18],fields[19],fields[20],fields[21],new Boolean(fields[5]),new Boolean(false),modeladitional);

						model.getItemCodeList().add(record);

					}
					i++;

				}

				if(i!=1){
					cButton.btnNew.setDisable(true);
					cButton.btnEdit.setDisable(false);
					table.setDisable(false);
					btnEditEvent();
					find=true;
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private void btnPreviewEvent(){
		try {
			HashMap map= new HashMap<>();
			DBUtil.dbConnect();


			map.put("orgName",sessionBeam.getClientCompanyName());
			map.put("orgAddress",sessionBeam.getClientAddress());
			map.put("orgMobile",sessionBeam.getClientPhone());

			map.put("devName",sessionBeam.getDeveloperCompanyName());
			map.put("devEmail",sessionBeam.getEmail());
			map.put("devTele",sessionBeam.getTele());

			if(btnEstimate.isSelected()){
				String ProjectId="";
				String value=cmbProjectFind.getValue().toString().trim();

				StringTokenizer token=new StringTokenizer(value, "/");
				while(token.hasMoreTokens()){
					ProjectId=token.nextToken();
					break;
				}

				String Sql = "select  (select group_concat((select Description from tbItemInfo where ItemCode=tbProjectAditionalItem.aditionalItemCode),'\n') from tbProjectAditionalItem where projectId=a.projectId and parentItemCode=b.ItemCode1) as AditionalCodeDes,b.autoId,b.ItemText,b.ItemCode1,b.ItemCode2,(select Description from TbDivisionInfo where DivisionId=(select DivisionId from TbItemInfo where ItemCode=b.ItemCode1)) as DivisionDes,b.autoId,b.ItemText,b.ItemCode1,b.ItemCode2,(select Description from TbDivisionInfo where DivisionId=(select DivisionId from TbItemInfo where ItemCode=b.ItemCode1)) as DivisionDes,(select Description from TbItemInfo where ItemCode=b.ItemCode1) as ItemDescription,b.Description as Description00000,b.Part,b.Unit,printf('%.3f', b.Nos) as Nos,b.Nos as AcNos, printf('%.3f', b.Site) as Site,b.site as AcSite,ifnull(b.NSUnit,'') as NSUnit,printf('%.3f', b.length) as length,b.length as Aclength,printf('%.3f', b.Base) as Base,b.base as AcBase,printf('%.3f', b.Height) as Height,b.Height as AcHeight,ifnull(b.LBHUnit,'') as LBHUnit,b.Qty,b.rate,b.Amount,b.deduction,a.projectid,a.projectCode,a.projectName,a.AdpNo,a.description,a.TotalQty,a.TotalRate,a.TotalAmount, c.ItemCode,c.Description,c.qty as salvageQty,c.rate as salvageRate,c.amount as salvageAmount from tbprojectinfo a, tbSalvageDeduction c join tbprojectdetails b on a.projectId=b.projectId where a.projectid='"+ProjectId+"' ";
				System.out.print(Sql);
				String report="src/Report/EsmitateReport.jrxml";
				JasperDesign jd=JRXmlLoader.load(report);
				JRDesignQuery jq=new JRDesignQuery();
				jq.setText(Sql);
				jd.setQuery(jq);
				JasperReport jr=JasperCompileManager.compileReport(jd);
				JasperPrint jp=JasperFillManager.fillReport(jr, map,DBUtil.con);
				JasperViewer.viewReport(jp, false);
			}
			else{
				String ProjectId="";
				String value=cmbProjectFind.getValue().toString().trim();

				StringTokenizer token=new StringTokenizer(value, "/");
				while(token.hasMoreTokens()){
					ProjectId=token.nextToken();
					break;
				}

				String Sql = "select b.Deduction,b.ItemText,b.ItemCode1,b.ItemCode2,(select CategoryTitle from tbCategoryInfo where categoryId=(select categoryId from TbItemInfo where ItemCode=b.ItemCode1)) as GroupName,(select Description from TbDivisionInfo where DivisionId=(select DivisionId from TbItemInfo where ItemCode=b.ItemCode1)) as DivisionDes,(select Description from TbItemInfo where ItemCode=b.ItemCode1) as ItemDescription,b.Description as Description00000,b.Unit,sum(b.Nos) as Nos,sum(b.length) as length,sum(b.Base) as Base,sum(b.Height) as Height,sum(b.Qty) as Qty,avg(b.rate) as rate,sum(b.Amount) as Amount,a.projectid,a.projectCode,a.projectName,a.AdpNo,a.description,a.TotalQty,a.TotalRate,a.TotalAmount from tbprojectinfo a join tbprojectdetails b on a.projectId=b.projectId where a.projectid='"+ProjectId+"' and b.Deduction='0' group by b.ItemCode1, b.Deduction order by b.autoId";
				System.out.print(Sql);
				String report=btnLtm.isSelected()?"src/Report/ProjectEstimateSummery.jrxml":"src/Report/ProjectEstimateSummeryWithoutValue.jrxml";

				JasperDesign jd=JRXmlLoader.load(report);
				JRDesignQuery jq=new JRDesignQuery();
				jq.setText(Sql);
				jd.setQuery(jq);
				JasperReport jr=JasperCompileManager.compileReport(jd);
				JasperPrint jp=JasperFillManager.fillReport(jr, map,DBUtil.con);
				JasperViewer.viewReport(jp, false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private void btnFindEvent(){
		String ProjectId="",AdpNo="";
		StringTokenizer token=new StringTokenizer(cmbProjectFind.getValue().toString(),"/");
		while(token.hasMoreTokens()){
			ProjectId=token.nextToken();
			break;
		}

		if(checkProjectId(ProjectId)){
			try {
				model.getItemCodeList().clear();
				String selectStmt = "select b.ItemText,b.ItemCode1,b.ItemCode2,(select Description from TbItemInfo where ItemCode=b.ItemCode1) as ItemDescription,b.Unit,b.Part,b.Description as Description00000,printf('%.3f', b.Nos) as Nos,b.Nos as AcNos, printf('%.3f', b.Site) as Site,b.site as AcSite,ifnull(b.NSUnit,'') as NSUnit,printf('%.3f', b.length) as length,b.length as Aclength,printf('%.3f', b.Base) as Base,b.base as AcBase,printf('%.3f', b.Height) as Height,b.Height as AcHeight,ifnull(b.LBHUnit,'') as LBHUnit,printf('%.3f', b.Qty) as Qty,printf('%.3f', b.rate) as rate,printf('%.3f', b.Amount) as Amount,b.deduction,a.projectid,a.projectCode,a.projectName,a.AdpNo,a.description,printf('%.3f', a.TotalQty) as TotalQty,printf('%.3f', a.TotalRate) as TotalRate,printf('%.3f', a.TotalAmount) as TotalAmount,c.ItemCode as salvageItem,c.Description as salvageDescription,c.qty as salvageQty,c.rate as salvageRate,c.amount as salvageAmount from tbprojectinfo a, tbSalvageDeduction c join tbprojectdetails b on a.projectId=b.projectId where a.projectid='"+ProjectId+"'  ";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				int i=1;
				while(rs.next()){
					txtProjectCode.setText(rs.getString("projectCode"));
					txtProjectName.setText(rs.getString("projectName"));
					txtProjectId.setText(rs.getString("projectId"));
					txtADP.setText(rs.getString("AdpNo"));
					txtDescription.setText(rs.getString("description"));
					txtTotalQty.setText(df.format(Double.parseDouble(rs.getString("TotalQty"))));
					txtTotalRate.setText(df.format(Double.parseDouble(rs.getString("TotalRate"))));
					txtGrandTotal.setText(df.format(Double.parseDouble(rs.getString("TotalAmount"))));
					
					txtSalvageItemCode.setText(rs.getString("salvageItem"));
					txtSalvageDescription.setText(rs.getString("salvageDescription"));
					txtSalvageQty.setText(df.format(Double.parseDouble(rs.getString("salvageQty"))));
					txtSalvageRate.setText(df.format(Double.parseDouble(rs.getString("salvageRate"))));
					txtSalvageAmount.setText(df.format(Double.parseDouble(rs.getString("salvageAmount"))));
					
					if(Double.parseDouble(rs.getString("salvageAmount"))>0){
						checkSalvageDeduction.setSelected(true);
					}

					boolean value=rs.getString("deduction").toString().equals("0")?false:true;

					String itemDescription=rs.getString("ItemDescription");

					String ItemCodeC=rs.getString("ItemCode1").substring(0, 3).trim();
					if(ItemCodeC.equals("000")){
						System.out.println("Match "+ItemCodeC);
						itemDescription=rs.getString("Description00000");
					}
					//String nosValue=Double.parseDouble(rs.getString("Nos"))<=0?"":rs.getString("Nos");
					//String siteValue=Double.parseDouble(rs.getString("Site"))<=0?"":rs.getString("Site");

					//String lengthValue=Double.parseDouble(rs.getString("Length"))<=0?"":rs.getString("Length");
					//String heightValue=Double.parseDouble(rs.getString("Height"))<=0?"":rs.getString("Height");
					//String baseValue=Double.parseDouble(rs.getString("Base"))<=0?"":rs.getString("Base");

					String nosValue=rs.getString("AcNos");
					String siteValue=rs.getString("AcSite");
					String lengthValue=rs.getString("AcLength");
					String heightValue=rs.getString("AcHeight");
					String baseValue=rs.getString("AcBase");
					/*				if(rs.getString("AcNos").indexOf("%")>0){

					}
					if(rs.getString("AcSite").indexOf("%")>0){

					}
					if(rs.getString("AcLength").indexOf("%")>0){

					}
					if(rs.getString("AcHeight").indexOf("%")>0){

					}
					if(rs.getString("AcBase").indexOf("%")>0){

					}*/

					model.getItemCodeList().add(new EstimateCreateModel(Integer.toString(i),rs.getString("ItemText"),cmbItemCode,rs.getString("ItemCode1"),checkaditionalItemCode,new Boolean(value),rs.getString("ItemCode2"),rs.getString("Part"),cmbDescription,itemDescription,rs.getString("Unit"),nosValue,siteValue,cmbUnit,rs.getString("NSUnit"),lengthValue,baseValue,heightValue,cmbUnit,rs.getString("LBHUnit"),rs.getString("Qty").toString(),rs.getString("Rate").toString(),rs.getString("Amount").toString(),new Boolean(value),new Boolean(false),modeladitional));

					i++;
					cButton.btnNew.setDisable(true);
					cButton.btnEdit.setDisable(false);
					table.setDisable(false);
					btnEditEvent();
					find=true;
				}
				/*				if(find){
					cButton.btnSave.setDisable(true);
				}*/
			} catch (Exception e) {
				e.printStackTrace();
				new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid name never allow");
		}
	}
	private void btnSaveEvent(){
		if(checkEmptyFeild()){
			if(insert){
				System.out.println("insert");
				if(!checkDoplicateName(txtProjectName.getText().trim().toString())){
					if(insertData()){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setItemCode();
						loadProjectName();
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
				System.out.println("update");
				if(!checkDoplicateName(txtProjectName.getText().trim().toString())){
					if(updateData("1")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setItemCode();
						loadProjectName();
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
					}
				}
				else{
					if(updateData("2")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
						setItemCode();
						loadProjectName();
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
			String ProjectId=getMaxProjectId();
			String insertSql="insert into tbprojectinfo (projectId,projectCode,projectName,AdpNo,description,TotalQty,TotalRate,TotalAmount,date,EntryTime,userId,userIp) values "
					+ "('"+ProjectId+"',"
					+ "'"+txtProjectCode.getText().trim().toString()+"',"
					+ "'"+txtProjectName.getText().trim().toString()+"',"
					+ "'"+txtADP.getText().trim().toString()+"',"
					+ "'"+txtDescription.getText().trim().toString()+"',"
					+ "'"+txtTotalQty.getText().trim().toString()+"',"
					+ "'"+txtTotalRate.getText().trim().toString()+"',"
					+ "'"+txtGrandTotal.getText().trim().toString()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"')";

			DBUtil.dbExecuteUpdate(insertSql);

			String insertUdSql="insert into tbUdprojectinfo (projectId,projectCode,projectName,AdpNo,description,TotalQty,TotalRate,TotalAmount,date,EntryTime,userId,userIp,Flag) values "
					+ "('"+ProjectId+"',"
					+ "'"+txtProjectCode.getText().trim().toString()+"',"
					+ "'"+txtProjectName.getText().trim().toString()+"',"
					+ "'"+txtADP.getText().trim().toString()+"',"
					+ "'"+txtDescription.getText().trim().toString()+"',"
					+ "'"+txtTotalQty.getText().trim().toString()+"',"
					+ "'"+txtTotalRate.getText().trim().toString()+"',"
					+ "'"+txtGrandTotal.getText().trim().toString()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','New')";

			DBUtil.dbExecuteUpdate(insertUdSql);



			for(int a=0;a<table.getItems().size();a++){

				if(table.getItems().get(a).cmbItemCode1.getValue()!=null){
					String deduction=table.getItems().get(a).isCheckEntryStatus()?"1":"0";
					String AutoId=getMaxAutoId();
					String ItemCode1=table.getItems().get(a).getItemCode1().getValue()==null?"":table.getItems().get(a).getItemCode1().getValue().toString();
					String ItemCode2=table.getItems().get(a).getaditonalItemCode();

					String description="";

					if(ItemCode1.length()>3){
						String ItemCodeC=ItemCode1.substring(0, 3).trim();
						System.out.println("ItemCodeC "+ItemCodeC);
						if(ItemCodeC.equals("000")){
							description=table.getItems().get(a).getDescription().getValue().toString();
						}
					}


					int aditionItemLenght=ItemCode2.length();
					if(aditionItemLenght>0){
						insertAditionalItemCode(ProjectId,ItemCode1,ItemCode2);
					}

					String insertdetailsSql="insert into tbProjectDetails (autoId,ItemText,projectid,ItemCode1,ItemCode2,Unit,Part,Description,Nos,Site,NSUnit,length,Base,Height,LBHUnit,Qty,Rate,Amount,deduction,date,EntryTime,userId,userIp) values "
							+ "('"+AutoId+"',"
							+ "'"+table.getItems().get(a).getItemTextValue().getText().toString().trim()+"',"
							+ "'"+ProjectId+"',"
							+ "'"+ItemCode1+"',"
							+ "'"+ItemCode2+"',"
							+ "'"+table.getItems().get(a).getUnit().getText().trim().toString()+"',"
							+ "'"+table.getItems().get(a).getPart().getText().trim().toString()+"',"
							+ "'"+description+"',"
							+ "'"+table.getItems().get(a).getNos().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getSite().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getNSUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getLength().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getBase().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getHeight().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getLBHUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getQty().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getRate().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getAmount().getText().toString().trim()+"',"
							+ "'"+deduction+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"')";

					DBUtil.dbExecuteUpdate(insertdetailsSql);

					String insertdetailsUdSql="insert into tbUdProjectDetails (autoId,ItemText,projectid,ItemCode1,ItemCode2,Unit,Nos,Site,NSUnit,length,Base,Height,LBHUnit,Qty,Rate,Amount,deduction,date,EntryTime,userId,userIp,Flag) values "
							+ "('"+AutoId+"',"
							+ "'"+table.getItems().get(a).getItemTextValue().getText().toString().trim()+"',"
							+ "'"+ProjectId+"',"
							+ "'"+ItemCode1+"',"
							+ "'"+ItemCode2+"',"
							+ "'"+table.getItems().get(a).getUnit().getText().trim().toString()+"',"
							+ "'"+table.getItems().get(a).getNos().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getSite().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getNSUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getLength().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getBase().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getHeight().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getLBHUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getQty().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getRate().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getAmount().getText().toString().trim()+"',"
							+ "'"+deduction+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','New')";

					DBUtil.dbExecuteUpdate(insertdetailsUdSql);
				}
			}

			if(checkSalvageDeduction.isSelected()){
				String salvageId=getMaxSalvageId();
				String insertsalvageSql="insert into TbSalvageDeduction ("
						+ "autoId,"
						+ "projectId,"
						+ "ItemCode,"
						+ "Description,"
						+ "qty,"
						+ "rate,"
						+ "amount,"
						+ "entryTime,"
						+ "date,"
						+ "userId,userIp"
						+ ") values ("
						+ "'"+salvageId+"',"
						+ "'"+ProjectId+"',"
						+ "'"+txtSalvageItemCode.getText().trim().toString()+"',"
						+ "'"+txtSalvageDescription.getText().trim().toString()+"',"
						+ "'"+txtSalvageQty.getText().trim().toString()+"',"
						+ "'"+txtSalvageRate.getText().trim().toString()+"',"
						+ "'"+txtSalvageAmount.getText().trim().toString()+"',"
						+ "CURRENT_TIMESTAMP,"
						+ "CURRENT_TIMESTAMP,"
						+ "'"+sessionBeam.getUserId()+"',"
						+ "'"+sessionBeam.getUserIp()+"')";
				
				DBUtil.dbExecuteUpdate(insertsalvageSql);
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
			String ProjectId=txtProjectId.getText().trim().toString();
			String updateSql="";
			if(Type.equals("1")){
				updateSql="update TbProjectInfo set  "
						+ "projectCode='"+txtProjectCode.getText().trim().toString()+"',"
						+ "projectName='"+txtProjectName.getText().trim().toString()+"',"
						+ "Adpno='"+txtADP.getText().trim().toString()+"',"
						+ "Description='"+txtDescription.getText().trim().toString()+"',"
						+ "TotalQty='"+txtTotalQty.getText().trim().toString()+"',"
						+ "TotalRate='"+txtTotalRate.getText().trim().toString()+"',"
						+ "TotalAmount='"+txtGrandTotal.getText().trim().toString()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "UserId='"+sessionBeam.getUserId()+"' where ProjectId='"+ProjectId+"'";
			}
			else{
				updateSql="update TbProjectInfo set  "
						+ "projectCode='"+txtProjectCode.getText().trim().toString()+"',"
						+ "Adpno='"+txtADP.getText().trim().toString()+"',"
						+ "Description='"+txtDescription.getText().trim().toString()+"',"
						+ "TotalQty='"+txtTotalQty.getText().trim().toString()+"',"
						+ "TotalRate='"+txtTotalRate.getText().trim().toString()+"',"
						+ "TotalAmount='"+txtGrandTotal.getText().trim().toString()+"',"
						+ "EntryTime=CURRENT_TIMESTAMP,"
						+ "UserId='"+sessionBeam.getUserId()+"' where ProjectId='"+ProjectId+"'";
			}
			DBUtil.dbExecuteUpdate(updateSql);


			String insertSql="insert into tbUdprojectinfo (projectId,projectCode,projectName,AdpNo,description,TotalQty,TotalRate,TotalAmount,date,EntryTime,userId,userIp,Flag) values "
					+ "('"+ProjectId+"',"
					+ "'"+txtProjectCode.getText().trim().toString()+"',"
					+ "'"+txtProjectName.getText().trim().toString()+"',"
					+ "'"+txtADP.getText().trim().toString()+"',"
					+ "'"+txtDescription.getText().trim().toString()+"',"
					+ "'"+txtTotalQty.getText().trim().toString()+"',"
					+ "'"+txtTotalRate.getText().trim().toString()+"',"
					+ "'"+txtGrandTotal.getText().trim().toString()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','Edit')";

			DBUtil.dbExecuteUpdate(insertSql);

			String insertTraceData="insert into tbUdProjectDetails select *,'DELETE' from tbProjectDetails where projectId='"+ProjectId+"'";

			String deleteSql="delete from tbProjectDetails where projectId='"+ProjectId+"'";
			DBUtil.dbExecuteUpdate(deleteSql);

			String addeleteSql="delete from tbProjectAditionalItem where projectId='"+ProjectId+"' ";
			DBUtil.dbExecuteUpdate(addeleteSql);

			for(int a=0;a<table.getItems().size();a++){
				if(table.getItems().get(a).cmbItemCode1.getValue()!=null){
					String deduction=table.getItems().get(a).isCheckEntryStatus()?"1":"0";
					String AutoId=getMaxAutoId();
					String ItemCode1=table.getItems().get(a).getItemCode1().getValue()==null?"":table.getItems().get(a).getItemCode1().getValue().toString();
					String ItemCode2=table.getItems().get(a).getaditonalItemCode();

					String description="";
					if(ItemCode1.length()>3){
						String ItemCodeC=ItemCode1.substring(0, 3).trim();
						System.out.println("ItemCodeC "+ItemCodeC);
						if(ItemCodeC.equals("000")){
							description=table.getItems().get(a).getDescription().getValue().toString();
						}
					}


					int aditionItemLenght=ItemCode2.length();
					if(aditionItemLenght>0){
						insertAditionalItemCode(ProjectId,ItemCode1,ItemCode2);
					}

					String insertdetailsSql="insert into tbProjectDetails (autoId,ItemText,projectid,ItemCode1,ItemCode2,Unit,Part,Description,Nos,Site,NSUnit,length,Base,Height,LBHUnit,Qty,Rate,Amount,deduction,date,EntryTime,userId,userIp) values "
							+ "('"+AutoId+"',"
							+ "'"+table.getItems().get(a).getItemTextValue().getText().toString().trim()+"',"
							+ "'"+ProjectId+"',"
							+ "'"+ItemCode1+"',"
							+ "'"+ItemCode2+"',"
							+ "'"+table.getItems().get(a).getUnit().getText().trim().toString()+"',"
							+ "'"+table.getItems().get(a).getPart().getText().trim().toString()+"',"
							+ "'"+description+"',"
							+ "'"+table.getItems().get(a).getNos().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getSite().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getNSUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getLength().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getBase().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getHeight().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getLBHUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getQty().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getRate().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getAmount().getText().toString().trim()+"',"
							+ "'"+deduction+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"')";

					DBUtil.dbExecuteUpdate(insertdetailsSql);

					String insertdetailsNewSql="insert into tbUdProjectDetails (autoId,ItemText,projectid,ItemCode1,ItemCode2,Unit,Nos,Site,NSUnit,length,Base,Height,LBHUnit,Qty,Rate,Amount,deduction,date,EntryTime,userId,userIp,Flag) values "
							+ "('"+AutoId+"',"
							+ "'"+table.getItems().get(a).getItemTextValue().getText().toString().trim()+"',"
							+ "'"+ProjectId+"',"
							+ "'"+ItemCode1+"',"
							+ "'"+ItemCode2+"',"
							+ "'"+table.getItems().get(a).getUnit().getText().trim().toString()+"',"
							+ "'"+table.getItems().get(a).getNos().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getSite().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getNSUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getLength().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getBase().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getHeight().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getLBHUnitValue().trim()+"',"
							+ "'"+table.getItems().get(a).getQty().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getRate().getText().toString().trim()+"',"
							+ "'"+table.getItems().get(a).getAmount().getText().toString().trim()+"',"
							+ "'"+deduction+"',"
							+ "CURRENT_TIMESTAMP,"
							+ "CURRENT_TIMESTAMP,"
							+ "'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','After Delete')";

					DBUtil.dbExecuteUpdate(insertdetailsNewSql);
				}
			}
			
			if(checkSalvageDeduction.isSelected()){
				
				String salvageSql="delete from TbSalvageDeduction where projectId='"+ProjectId+"'";
				DBUtil.dbExecuteUpdate(salvageSql);
				
				String salvageId=getMaxSalvageId();
				String insertsalvageSql="insert into TbSalvageDeduction ("
						+ "autoId,"
						+ "projectId,"
						+ "ItemCode,"
						+ "Description,"
						+ "qty,"
						+ "rate,"
						+ "amount,"
						+ "entryTime,"
						+ "date,"
						+ "userId,userIp"
						+ ") values ("
						+ "'"+salvageId+"',"
						+ "'"+ProjectId+"',"
						+ "'"+txtSalvageItemCode.getText().trim().toString()+"',"
						+ "'"+txtSalvageDescription.getText().trim().toString()+"',"
						+ "'"+txtSalvageQty.getText().trim().toString()+"',"
						+ "'"+txtSalvageRate.getText().trim().toString()+"',"
						+ "'"+txtSalvageAmount.getText().trim().toString()+"',"
						+ "CURRENT_TIMESTAMP,"
						+ "CURRENT_TIMESTAMP,"
						+ "'"+sessionBeam.getUserId()+"',"
						+ "'"+sessionBeam.getUserIp()+"')";
				
				DBUtil.dbExecuteUpdate(insertsalvageSql);
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

	private void insertAditionalItemCode(String ProjectId,String ParentItem,String ItemCode2){
		try {

			String[] aditionItem=ItemCode2.split("[+]");
			for(int a=0;a<aditionItem.length;a++){

				String AutoId=getMaxaditionalId();
				String insertsql="insert into tbProjectAditionalItem (autoId,projectId,parentItemCode,aditionalItemCode,EntryTime,date,UserId,UserIp) values ('"+AutoId+"','"+ProjectId+"','"+ParentItem+"','"+aditionItem[a]+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"')";
				DBUtil.dbExecuteUpdate(insertsql);
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}


	private boolean checkDoplicateName(String name){
		try {
			String selectStmt = "select projectName from tbprojectinfo where projectName='"+name+"'";
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
	private boolean checkProjectId(String projectId){
		try {
			String selectStmt = "select ProjectId from tbprojectinfo where ProjectId='"+projectId+"'";
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
	public void loadProjectName(){
		try {
			cmbProjectFind.data.clear();
			String selectStmt = "select projectId,AdpNo from tbprojectinfo order by projectId,AdpNo";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbProjectFind.data.add(rs.getString("projectId")+"/"+rs.getString("AdpNo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}

	private String getMaxaditionalId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(autoId),0)+1)as autoId from tbProjectAditionalItem";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("autoId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}

		return Id;
	}

	private String getMaxSalvageId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(autoId),0)+1)as autoId from TbSalvageDeduction";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("autoId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}

		return Id;
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
		model.getItemCodeList().clear();
		setItemCode();
		txtProjectId.setText(getMaxProjectId());
		txtProjectCode.setText("");
		txtProjectName.setText("");
		txtADP.setText("");
		txtDescription.setText("");
		txtTotalQty.setText("");
		txtTotalRate.setText("");
		txtGrandTotal.setText("");
		txtSalvageItemCode.setText("");
		txtSalvageDescription.setText("");
		txtSalvageQty.setText("");
		txtSalvageRate.setText("");
		txtSalvageAmount.setText("");
		insert=true;
		update=false;
		find=false;
	}
	private void ComponentEnable(boolean t){

		table.setDisable(t);
		txtProjectCode.setDisable(t);
		txtProjectName.setDisable(t);
		txtADP.setDisable(t);
		txtDescription.setDisable(t);
	}
	private String getMaxProjectId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(projectId),0)+1)as projectId from TbProjectInfo";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("projectId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	private String getMaxAutoId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(autoId),0)+1)as autoId from tbProjectDetails";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("autoId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	private void btnAddNewRow(){
		String selectStmt = "select ItemCode from tbiteminfo order by ItemCode";
		cmbItemCode.data.clear();
		try {
			int i=1;
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbItemCode.data.add(rs.getString("ItemCode"));
			}
			//model.getItemCodeList().add(new EstimateCreateModel(Integer.toString(table.getItems().size()+1),"",cmbItemCode,null,new Boolean(false),"","","","1","1","1","1","1","1","1",new Boolean(false)));
			model.getItemCodeList().add(new EstimateCreateModel(Integer.toString(table.getItems().size()+1),"",cmbItemCode,null,checkaditionalItemCode,new Boolean(false),"","",cmbDescription,"","","","",cmbUnit,"Nos","","","",cmbUnit,"Meter","","","",new Boolean(false),new Boolean(false),modeladitional));
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}

	private void btnPasteEvent(){
		

		int count=model.getItemCodeList().size();
		
		for(int i=0;i<count;i++){
			if(table.getItems().get(i).isCheckCopyStatus()){
				String ItemCode1=table.getItems().get(i).getItemCode1().getValue()==null?"":table.getItems().get(i).getItemCode1().getValue().toString();
				String ItemCode2="";

				String Description=table.getItems().get(i).getDescription().getValue().toString();

				String Unit=table.getItems().get(i).getUnit().getText().trim().toString();
				String lenght=table.getItems().get(i).getLength().getText().trim().toString();
				String breadth=table.getItems().get(i).getBase().getText().trim().toString();
				String height=table.getItems().get(i).getHeight().getText().trim().toString();
				String text=table.getItems().get(i).getItemTextValue().getText().trim().toString();
				//String unitlbh=table.getItems().get(i).getCmbLBHUnit().getValue().toString();
				//String nosunit=table.getItems().get(i).getCmbNSUnit().getValue()==null?"":table.getItems().get(i).getCmbNSUnit().getValue().toString();
				String qty=table.getItems().get(i).getQty().getText().trim().toString();
				String Rate=table.getItems().get(i).getRate().getText().trim().toString();
				String amount=table.getItems().get(i).getAmount().getText().trim().toString();
				String nos=table.getItems().get(i).getNos().getText().trim().toString();
				String site=table.getItems().get(i).getSite().getText().trim().toString();
				
				model.getItemCodeList().add(table.getItems().size(), new EstimateCreateModel(Integer.toString(table.getItems().size()+1),text,cmbItemCode,ItemCode1,checkaditionalItemCode,new Boolean(false),"","",cmbDescription,Description,Unit,nos,site,cmbUnit,"",lenght,breadth,height,cmbUnit,"Meter",qty,Rate,amount,new Boolean(false),new Boolean(false),modeladitional));
				

			}
		}
		
		setGrandTotal();
		
	}
	
	private void setItemCode(){
		model.getItemCodeList().clear();
		String selectStmt = "select ItemCode,Description from tbiteminfo order by ItemCode";
		cmbItemCode.data.clear();
		cmbDescription.data.clear();
		try {
			int i=1;
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbItemCode.data.add(rs.getString("ItemCode"));
				if(!rs.getString("Description").isEmpty()){
					cmbDescription.data.add(rs.getString("Description"));
				}

			}
			model.getItemCodeList().add(new EstimateCreateModel(Integer.toString(table.getItems().size()+1),"",cmbItemCode,null,checkaditionalItemCode,new Boolean(false),"","",cmbDescription,"","","","",cmbUnit,"Nos","","","",cmbUnit,"Meter","","","",new Boolean(false),new Boolean(false),modeladitional));

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}

	private void setUnit() {
		// TODO Auto-generated method stub

		cmbUnit.data.clear();
		String sql = "select unitname from tbunitinfo";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(sql);
			while(rs.next()) {
				cmbUnit.data.add(rs.getString("unitname"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}


	private void setAditionalItemCode(){
		modeladitional.getAditionalItemCodeList().clear();
		String selectStmt = "select ItemCode from tbiteminfo order by ItemCode";
		cmbaditionalItemCode.data.clear();
		try {
			int i=1;
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbaditionalItemCode.data.add(rs.getString("ItemCode"));
			}

			for(int a=0;a<10;a++){
				modeladitional.getAditionalItemCodeList().add(new AditionalItemModel(new Boolean(false),cmbaditionalItemCode,"",""));
			}



		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}

	private String getRate(String ItemCode){
		String Rate="";
		String selectStmt = "select Rate from tbItemInfo where ItemCode='"+ItemCode+"'";
		try {

			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Rate=rs.getString("Rate");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}

		return Rate;
	}

	private boolean checkEmptyFeild()
	{
		if(!txtProjectName.getText().trim().toString().isEmpty()){
			if(!txtProjectCode.getText().trim().toString().isEmpty()){
				return true;
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Project Code");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Project Name.");
		}
		return false;
	} 

	private void setGrandTotal(){
		double totalAmount=0;
		double totalRate=0;
		double totalQty=0;

		double dtotalAmount=0;
		double dtotalRate=0;
		double dtotalQty=0;

		for(int a=0;a<table.getItems().size();a++){
			if(table.getItems().get(a).cmbItemCode1.getValue()!=null){
				if(table.getItems().get(a).isCheckEntryStatus()){
					dtotalAmount=dtotalAmount+Double.parseDouble(table.getItems().get(a).getAmount().getText().trim().toString());
					dtotalRate=Double.parseDouble(table.getItems().get(a).getRate().getText().trim().toString());
					dtotalQty=dtotalQty+Double.parseDouble(table.getItems().get(a).getQty().getText().trim().toString());
					System.out.println("dtotalAmount "+dtotalAmount);
				}
				else{
					totalAmount=totalAmount+Double.parseDouble(table.getItems().get(a).getAmount().getText().trim().toString());
					totalRate=Double.parseDouble(table.getItems().get(a).getRate().getText().trim().toString().isEmpty()?"0":table.getItems().get(a).getRate().getText().trim().toString());
					totalQty=totalQty+Double.parseDouble(table.getItems().get(a).getQty().getText().trim().toString());
					System.out.println("totalAmount "+totalAmount);
				}
			}
		}
		//double salvageQty=Double.parseDouble(txtSalvageAmount.getText().trim().toString().isEmpty()?"0":txtSalvageAmount.getText().trim().toString());
		double avgrate=(totalAmount-dtotalAmount)/(totalQty-dtotalQty);
		
		txtGrandTotal.setText(df.format(totalAmount-dtotalAmount));
		txtTotalRate.setText(df.format(avgrate));
		txtTotalQty.setText(df.format(totalQty-dtotalQty));
	}
	@SuppressWarnings("unchecked")
	private void addCmp(){



		cmbProjectFind.setLayoutX(850);
		cmbProjectFind.setLayoutY(10);
		cmbProjectFind.setPrefWidth(280);
		cmbProjectFind.setMinHeight(28);

		btnFind.setLayoutX(1135);
		btnFind.setLayoutY(10);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);



		Label lblProjectId=new Label("Project Id");
		lblProjectId.setLayoutX(10);
		lblProjectId.setLayoutY(15);

		txtProjectId=new TextRead();
		txtProjectId.setMaxWidth(60);
		txtProjectId.setLayoutX(100);
		txtProjectId.setLayoutY(10);
		txtProjectId.setText(getMaxProjectId());

		Label lblProjectCode=new Label("Project Code");
		lblProjectCode.setLayoutX(10);
		lblProjectCode.setLayoutY(45);

		txtProjectCode=new TextField();
		txtProjectCode.setMaxWidth(140);
		txtProjectCode.setLayoutX(100);
		txtProjectCode.setLayoutY(40);

		Label lblProjectName=new Label("Project Name");
		lblProjectName.setLayoutX(10);
		lblProjectName.setLayoutY(75);

		txtProjectName=new TextField();
		txtProjectName.setMinWidth(300);
		txtProjectName.setLayoutX(100);
		txtProjectName.setLayoutY(70);


		Label lblProjectADP=new Label("ADP No");
		lblProjectADP.setLayoutX(10);
		lblProjectADP.setLayoutY(105);

		txtADP=new TextField();
		txtADP.setMaxWidth(140);
		txtADP.setLayoutX(100);
		txtADP.setLayoutY(100);

		Label lblDescription=new Label("Description");
		lblDescription.setLayoutX(10);
		lblDescription.setLayoutY(145);

		txtDescription=new TextArea();
		txtDescription.setMaxWidth(300);
		txtDescription.setMaxHeight(60);
		txtDescription.setLayoutX(100);
		txtDescription.setLayoutY(130);



		final TableColumn<EstimateCreateModel, String> SLCol = new TableColumn<EstimateCreateModel, String>("SL#");
		//final TableColumn<EstimateCreateModel, TextField> IdCol = new TableColumn<EstimateCreateModel, TextField>("Text");
		final TableColumn<EstimateCreateModel, TextField> textCol = new TableColumn<EstimateCreateModel, TextField>("Text");
		//final TableColumn<EstimateCreateModel, FxComboBox> ItemCodeCol = new TableColumn<EstimateCreateModel, FxComboBox>("ITEM CODE");
		final TableColumn<EstimateCreateModel, CheckBox> checkAditionalCol = new TableColumn<EstimateCreateModel, CheckBox>("AD. I");
		final TableColumn<EstimateCreateModel, FxComboBox> ItemCodeCol1 = new TableColumn<EstimateCreateModel, FxComboBox>("Parent Item");

		final TableColumn<EstimateCreateModel, String> aditionalItemCodeCol = new TableColumn<EstimateCreateModel, String>("Aditional Code");
		final TableColumn<EstimateCreateModel, TextField> partCol = new TableColumn<EstimateCreateModel, TextField>("Part");

		final TableColumn<EstimateCreateModel, FxComboBox> DescriptionCol = new TableColumn<EstimateCreateModel, FxComboBox>("Description");
		final TableColumn<EstimateCreateModel, TextField> UnitCol = new TableColumn<EstimateCreateModel, TextField>("Unit");
		final TableColumn<EstimateCreateModel, TextField> NosCol = new TableColumn<EstimateCreateModel, TextField>("NOS");
		final TableColumn<EstimateCreateModel, TextField> SiteCol = new TableColumn<EstimateCreateModel, TextField>("Site");
		final TableColumn<EstimateCreateModel, FxComboBox> nsUnitCol = new TableColumn<EstimateCreateModel, FxComboBox>("NOS Unit");
		final TableColumn<EstimateCreateModel, TextField> LenthCol = new TableColumn<EstimateCreateModel, TextField>("Length");
		final TableColumn<EstimateCreateModel, TextField> BaseCol = new TableColumn<EstimateCreateModel, TextField>("Breadth");
		final TableColumn<EstimateCreateModel, TextField> HeightCol = new TableColumn<EstimateCreateModel, TextField>("Height");
		final TableColumn<EstimateCreateModel, FxComboBox> lbhUnitCol = new TableColumn<EstimateCreateModel, FxComboBox>("(L-B-H)Unit");
		final TableColumn<EstimateCreateModel, TextRead> QtyCol = new TableColumn<EstimateCreateModel, TextRead>("Qty");
		final TableColumn<EstimateCreateModel, NumberField> RateCol = new TableColumn<EstimateCreateModel, NumberField>("Rate");
		final TableColumn<EstimateCreateModel, NumberField> AmountCol = new TableColumn<EstimateCreateModel, NumberField>("Amount");


		final TableColumn<EstimateCreateModel, Boolean> checkEntryCol = new TableColumn<EstimateCreateModel, Boolean>("");

		checkEntryCol.setGraphic(headerEntryCheckbox);

		final TableColumn<EstimateCreateModel, Boolean> checkCopyCol = new TableColumn<EstimateCreateModel, Boolean>("");
		checkCopyCol.setGraphic(headerCopyCheckbox);



		SLCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, String>("SL"));


		ItemCodeCol1.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, FxComboBox>("ItemCode1"));		
		aditionalItemCodeCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, String>("aditonalItemCode"));

		//aditionalItemCodeCol.setPrefWidth(120);
		//ItemCodeCol.getColumns().addAll(ItemCodeCol1, checkAditionalCol,aditionalItemCodeCol);



		DescriptionCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, FxComboBox>("Description"));
		UnitCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("Unit"));
		partCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("part"));

		textCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("ItemTextValue"));
		NosCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("Nos"));
		SiteCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("Site"));
		nsUnitCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, FxComboBox>("cmbNSUnit"));
		LenthCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("Length"));
		BaseCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("Base"));
		HeightCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextField>("Height"));
		lbhUnitCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, FxComboBox>("cmbLBHUnit"));
		QtyCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, TextRead>("Qty"));
		RateCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, NumberField>("Rate"));
		AmountCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, NumberField>("Amount"));

		checkEntryCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, Boolean>("CheckEntryStatus"));
		checkEntryCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkEntryCol));
		
		checkCopyCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, Boolean>("CheckCopyStatus"));
		checkCopyCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkCopyCol));

		checkAditionalCol.setCellValueFactory(new PropertyValueFactory<EstimateCreateModel, CheckBox>("AditionalItemCodeCheck"));
		//checkAditionalCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkAditionalCol));

		addButtonToTable();
		addDeleteButtonToTable();
		table.getColumns().addAll(checkCopyCol,checkEntryCol,textCol,ItemCodeCol1,checkAditionalCol,aditionalItemCodeCol,partCol,DescriptionCol,UnitCol,NosCol,nsUnitCol,SiteCol,LenthCol,BaseCol,HeightCol,lbhUnitCol,QtyCol,RateCol,AmountCol);





		table.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				setGrandTotal(); 


				/*	                if( e.getCode() == KeyCode.TAB) { // commit should be performed implicitly via focusedProperty, but isn't
	                    table.getSelectionModel().selectNext();
	                   setGrandTotal();
	                }
	                else if( e.getCode() == KeyCode.ENTER) { // commit should be performed implicitly via focusedProperty, but isn't
	                    table.getSelectionModel().selectBelowCell();
	                    System.out.println("enter");
	                }

	                // switch to edit mode on keypress, but only if we aren't already in edit mode
	                if( table.getEditingCell() == null) {
	                    if( e.getCode().isLetterKey() || e.getCode().isDigitKey()) {  

	                        TablePosition focusedCellPosition = table.getFocusModel().getFocusedCell();
	                        table.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
	                        System.out.println("edit call");
	                    }
	                }*/

			}
		});


		SLCol.setPrefWidth(40);
		textCol.setPrefWidth(80);
		//ItemCodeCol.setPrefWidth(140);
		ItemCodeCol1.setPrefWidth(200);
		checkAditionalCol.setPrefWidth(80);
		aditionalItemCodeCol.setPrefWidth(140);
		partCol.setPrefWidth(120);
		NosCol.setPrefWidth(100);
		nsUnitCol.setPrefWidth(100);
		LenthCol.setPrefWidth(100);
		BaseCol.setPrefWidth(100);
		HeightCol.setPrefWidth(100);
		lbhUnitCol.setPrefWidth(130);

		DescriptionCol.setPrefWidth(300);
		UnitCol.setPrefWidth(60);
		QtyCol.setPrefWidth(70);



		RateCol.setPrefWidth(95);
		AmountCol.setPrefWidth(110);
		checkEntryCol.setPrefWidth(40);
		checkCopyCol.setPrefWidth(90);

		table.setPrefWidth(1330);
		table.setPrefHeight(340);
		table.setEditable(true);
		table.setLayoutX(10);
		table.setLayoutY(200);
		table.setItems(model.getItemCodeList());



		final TableColumn<AditionalItemModel, Boolean> checkadCol = new TableColumn<AditionalItemModel, Boolean>("");
		headerRefreshAditional = new CheckBox("Refresh Aditional");
		checkadCol.setGraphic(headerRefreshAditional);

		final TableColumn<AditionalItemModel, FxComboBox> adItemCodeCol = new TableColumn<AditionalItemModel, FxComboBox>("ITEM CODE");
		final TableColumn<AditionalItemModel, TextRead> adRateCol = new TableColumn<AditionalItemModel, TextRead>("RATE");



		tableAditional.getColumns().addAll(checkadCol,adItemCodeCol,adRateCol);
		tableAditional.setLayoutX(410);
		tableAditional.setLayoutY(10);
		tableAditional.setPrefWidth(430);
		tableAditional.setPrefHeight(190);
		tableAditional.setItems(modeladitional.getAditionalItemCodeList());
		tableAditional.setEditable(true);

		checkadCol.setPrefWidth(130);
		adItemCodeCol.setPrefWidth(190);
		adRateCol.setPrefWidth(100);

		adItemCodeCol.setCellValueFactory(new PropertyValueFactory<AditionalItemModel, FxComboBox>("AditionalItemCode"));
		adRateCol.setCellValueFactory(new PropertyValueFactory<AditionalItemModel, TextRead>("Rate"));
		checkadCol.setCellValueFactory(new PropertyValueFactory<AditionalItemModel, Boolean>("CheckAditonalStatus"));

		checkadCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkadCol));



		//TableViewUtils.addCustomTableMenu(table);




		btnNewAdd.setMnemonicParsing(true);
		btnNewAdd.setLayoutX(1150);
		btnNewAdd.setLayoutY(170);
		btnNewAdd.setMaxHeight(30);
		btnNewAdd.setMaxWidth(105);
		
		btnPaste.setMnemonicParsing(true);
		btnPaste.setLayoutX(1240);
		btnPaste.setLayoutY(170);
		btnPaste.setMaxHeight(30);
		btnPaste.setPrefWidth(90);

		Label lblGrandTotal=new Label("Grand Total");
		lblGrandTotal.setLayoutX(1020);
		lblGrandTotal.setLayoutY(540);

		txtTotalQty=new TextRead();
		txtTotalQty.setMaxWidth(100);
		txtTotalQty.setLayoutX(1020);
		txtTotalQty.setLayoutY(570);

		txtTotalRate=new TextRead();
		txtTotalRate.setMaxWidth(100);
		txtTotalRate.setLayoutX(1120);
		txtTotalRate.setLayoutY(570);

		txtGrandTotal=new TextRead();
		txtGrandTotal.setMaxWidth(125);
		txtGrandTotal.setLayoutX(1220);
		txtGrandTotal.setLayoutY(570);

		btnExport.setPrefWidth(60);
		btnExport.setPrefHeight(28);
		btnExport.setLayoutX(430);
		btnExport.setLayoutY(610);

		btnImport.setPrefWidth(60);
		btnImport.setPrefHeight(28);
		btnImport.setLayoutX(495);
		btnImport.setLayoutY(610);

		btnEstimate.setMinWidth(160);
		btnEstimate.setMinHeight(28);
		btnEstimate.setLayoutX(610);
		btnEstimate.setLayoutY(610);
		//btnForm.setId("btnModule");
		btnEstimate.setToggleGroup(gpds);
		btnEstimate.setSelected(true);

		btnSchedule.setMinWidth(160);
		btnSchedule.setMinHeight(28);
		btnSchedule.setLayoutX(610);
		btnSchedule.setLayoutY(590);
		//btnForm.setId("btnModule");
		btnSchedule.setToggleGroup(gpds);

		btnSalvage.setMinWidth(160);
		btnSalvage.setMinHeight(28);
		btnSalvage.setLayoutX(610);
		btnSalvage.setLayoutY(630);
		//btnForm.setId("btnModule");
		btnSalvage.setToggleGroup(gpds);



		btnLtm.setMinWidth(160);
		btnLtm.setMinHeight(28);
		btnLtm.setLayoutX(710);
		btnLtm.setLayoutY(620);
		//btnForm.setId("btnModule");
		btnLtm.setToggleGroup(gpwq);
		btnLtm.setSelected(true);


		btnOtm.setMinWidth(160);
		btnOtm.setMinHeight(28);
		btnOtm.setLayoutX(710);
		btnOtm.setLayoutY(590);
		//btnForm.setId("btnModule");
		btnOtm.setToggleGroup(gpwq);



		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter( table);

		vbox.getChildren().addAll( borderPane);
		vbox.setLayoutX(10);
		vbox.setLayoutY(10);

		table.setTableMenuButtonVisible(true);

		table.skinProperty().addListener(event -> {
			// ChangeListener.changed event
			table.tableMenuButtonVisibleProperty()
			.addListener((observable, oldValue, newValue) -> {
				if (newValue == true) {
					registerListeners();
				}
			});
			if (table.isTableMenuButtonVisible()) {
				registerListeners();
			}
		});


		checkCsv.setPrefWidth(60);
		checkCsv.setPrefHeight(30);
		checkCsv.setLayoutX(780);
		checkCsv.setLayoutY(590);


		checkSalvageDeduction.setLayoutX(10);
		checkSalvageDeduction.setLayoutY(550);
		checkSalvageDeduction.setSelected(false);



		Label lblSalvageItemCode=new Label("Item Code");
		lblSalvageItemCode.setLayoutX(10);
		lblSalvageItemCode.setLayoutY(575);

		txtSalvageItemCode=new TextField();
		txtSalvageItemCode.setLayoutX(80);
		txtSalvageItemCode.setLayoutY(570);
		txtSalvageItemCode.setPrefWidth(120);

		Label lblSalvageDescription=new Label("Description");
		lblSalvageDescription.setLayoutX(210);
		lblSalvageDescription.setLayoutY(575);

		txtSalvageDescription=new TextField();
		txtSalvageDescription.setLayoutX(290);
		txtSalvageDescription.setLayoutY(570);
		txtSalvageDescription.setPrefWidth(300);

		Label lblSalvageQty=new Label("Qty");
		lblSalvageQty.setLayoutX(600);
		lblSalvageQty.setLayoutY(575);

		txtSalvageQty=new TextField();
		txtSalvageQty.setLayoutX(630);
		txtSalvageQty.setLayoutY(570);
		txtSalvageQty.setPrefWidth(80);

		Label lblSalvageRate=new Label("Rate");
		lblSalvageRate.setLayoutX(720);
		lblSalvageRate.setLayoutY(575);

		txtSalvageRate=new TextField();
		txtSalvageRate.setLayoutX(760);
		txtSalvageRate.setLayoutY(570);
		txtSalvageRate.setPrefWidth(100);

		Label lblSalvageAmount=new Label("Amount");
		lblSalvageAmount.setLayoutX(870);
		lblSalvageAmount.setLayoutY(575);

		txtSalvageAmount=new TextRead();
		txtSalvageAmount.setLayoutX(920);
		txtSalvageAmount.setLayoutY(570);
		txtSalvageAmount.setPrefWidth(100);

		pane.getChildren().addAll(checkSalvageDeduction,lblSalvageItemCode,txtSalvageItemCode,lblSalvageDescription,txtSalvageDescription,lblSalvageQty,txtSalvageQty,
				lblSalvageRate,txtSalvageRate,lblSalvageAmount,txtSalvageAmount,lblProjectId,txtProjectId,lblProjectCode,txtProjectCode,lblProjectName,txtProjectName,
				lblProjectADP,txtADP,lblDescription,txtDescription,cmbProjectFind,btnFind,table,tableAditional,cButton,btnNewAdd,btnPaste,lblGrandTotal,txtTotalQty,txtTotalRate,txtGrandTotal,btnEstimate,btnSchedule,btnSalvage,
				btnLtm,btnOtm,vbox,btnExport,btnImport,checkCsv);

		cButton.setLayoutX(10);
		cButton.setLayoutY(610);


		final Control ob[] = {txtProjectCode,txtProjectName,txtADP,txtDescription,model.getItemTextValue(),model.getItemCode1()};	
		new FocusMoveByEnter(ob);

	}


	private void registerListeners() {

		final Node buttonNode = getMenuButton();
		// replace mouse listener on "+" node
		buttonNode.setOnMousePressed(event -> {
			showContextMenu();
			event.consume();
		});
	}
	private Node getMenuButton() {
		final TableHeaderRow tableHeaderRow = getTableHeaderRow();
		if (tableHeaderRow == null) {
			return null;
		}
		// child identified as cornerRegion in TableHeaderRow.java
		return tableHeaderRow.getChildren().stream().filter(child -> child
				.getStyleClass().contains("show-hide-columns-button")).findAny()
				.get();
	}
	private TableHeaderRow getTableHeaderRow() {
		final TableViewSkin<?> tableSkin = (TableViewSkin<?>) table
				.getSkin();
		if (tableSkin == null) {
			return null;
		}
		// find the TableHeaderRow child
		return (TableHeaderRow) tableSkin.getChildren().stream()
				.filter(child -> child instanceof TableHeaderRow).findAny()
				.get();
	}

	protected void showContextMenu() {
		final Node buttonNode = getMenuButton();
		// When the menu is already shown clicking the + button hides it.
		if (tableContextMenu != null) {
			tableContextMenu.hide();
		} else {
			// Show the menu
			// rebuilds the menu each time it is opened
			tableContextMenu = createContextMenu();
			tableContextMenu.setOnHidden(event -> tableContextMenu = null);
			tableContextMenu.show(buttonNode, Side.BOTTOM, 0, 0);
			// Repositioning the menu to be aligned by its right side (keeping
			// inside the table view)
			tableContextMenu.setX(
					buttonNode.localToScreen(buttonNode.getBoundsInLocal())
					.getMaxX() - tableContextMenu.getWidth());
		}
	}

	// adds custom menu items to the context menu which allow us to control
	// the on hide property
	private ContextMenu createContextMenu() {
		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().add(createSelectAllMenuItem(contextMenu));
		contextMenu.getItems().add(createDeselectAllMenuItem(contextMenu));
		contextMenu.getItems().add(new SeparatorMenuItem());
		addColumnCustomMenuItems(contextMenu);
		return contextMenu;
	}

	private CustomMenuItem createSelectAllMenuItem(
			final ContextMenu contextMenu) {
		final Label selectAllLabel = new Label("Select all");
		// adds listener to the label to change the size so the user
		// can click anywhere in the menu items area and not just on the
		// text to activate its onAction
		contextMenu.focusedProperty().addListener(event -> selectAllLabel
				.setPrefWidth(contextMenu.getWidth() * 0.75));
		final CustomMenuItem selectAllMenuItem = new CustomMenuItem(
				selectAllLabel);
		selectAllMenuItem.setOnAction(event -> selectAll(event));
		// set to false so the context menu stays visible after click
		selectAllMenuItem.setHideOnClick(false);
		return selectAllMenuItem;
	}


	private void selectAll(final Event event) {
		table.getColumns().forEach(column -> column.setVisible(true));
		event.consume();
	}
	private CustomMenuItem createDeselectAllMenuItem(
			final ContextMenu contextMenu) {
		final Label deselectAllLabel = new Label("Deselect all");
		// adds listener to the label to change the size so the user
		// can click anywhere in the menu items area and not just on the
		// text to activate its onAction
		contextMenu.focusedProperty().addListener(event -> deselectAllLabel
				.setPrefWidth(contextMenu.getWidth() * 0.75));
		final CustomMenuItem deselectAllMenuItem = new CustomMenuItem(
				deselectAllLabel);
		deselectAllMenuItem.setOnAction(event -> deselectAll(event));
		// set to false so the context menu stays visible after click
		deselectAllMenuItem.setHideOnClick(false);
		return deselectAllMenuItem;
	}

	private void deselectAll(final Event event) {
		table.getColumns().forEach(column -> column.setVisible(false));
		event.consume();
	}

	private void addColumnCustomMenuItems(final ContextMenu contextMenu) {
		// menu item for each of the available columns
		table.getColumns().forEach(column -> contextMenu.getItems()
				.add(createColumnCustomMenuItem(contextMenu, column)));
	}



	private CustomMenuItem createColumnCustomMenuItem(
			final ContextMenu contextMenu, final TableColumn<?, ?> column) {
		final CheckBox checkBox = new CheckBox(column.getText());
		// adds listener to the check box to change the size so the user
		// can click anywhere in the menu items area and not just on the
		// text to activate its onAction
		contextMenu.focusedProperty().addListener(
				event -> checkBox.setPrefWidth(contextMenu.getWidth() * 0.75));
		// the context menu item's state controls its bound column's visibility
		checkBox.selectedProperty().bindBidirectional(column.visibleProperty());
		final CustomMenuItem customMenuItem = new CustomMenuItem(checkBox);
		customMenuItem.getStyleClass().set(1, "check-menu-item");
		customMenuItem.setOnAction(event -> {
			checkBox.setSelected(!checkBox.isSelected());
			event.consume();
		});
		// set to false so the context menu stays visible after click
		customMenuItem.setHideOnClick(false);
		return customMenuItem;
	}

	@SuppressWarnings("unchecked")
	private void addButtonToTable() {
		TableColumn<EstimateCreateModel, Void> colBtn = new TableColumn("Add");

		colBtn.setPrefWidth(46);
		Callback<TableColumn<EstimateCreateModel, Void>, TableCell<EstimateCreateModel, Void>> cellFactory = new Callback<TableColumn<EstimateCreateModel, Void>, TableCell<EstimateCreateModel, Void>>() {
			@Override
			public TableCell<EstimateCreateModel, Void> call(final TableColumn<EstimateCreateModel, Void> param) {
				final TableCell<EstimateCreateModel, Void> cell = new TableCell<EstimateCreateModel, Void>() {

					private Button  btn = new Button("_+");

					{
						btn.setCache(false);
						btn.setPrefWidth(26);
						btn.setPrefHeight(26);
						btn.setOnAction((ActionEvent event) -> {
							int a=getIndex();
							System.out.println("a "+a);
							String ItemCode1=table.getItems().get(a).getItemCode1().getValue()==null?"":table.getItems().get(a).getItemCode1().getValue().toString();
							String ItemCode2="";

							String Description=table.getItems().get(getIndex()).getDescription().getValue().toString();

							String Unit=table.getItems().get(getIndex()).getUnit().getText().trim().toString();
							String Rate=getRate(ItemCode1);
							if(getIndex()>=0){
								int index = getIndex();
								//boolean checkstatus=table.getItems().get(getIndex()).isCheckAditionalItemCodeStatus();
								//String adItemCode=table.getItems().get(getIndex()).getaditonalItemCode();
								table.getItems().add(index + 1, new EstimateCreateModel(Integer.toString(table.getItems().size()+1),"",cmbItemCode,ItemCode1,checkaditionalItemCode,new Boolean(false),"","",cmbDescription,Description,Unit,"","",cmbUnit,"Meter","","","",cmbUnit,"Meter","",Rate,Rate,new Boolean(false),new Boolean(false),modeladitional));


								//model.getItemCodeList().add(new EstimateCreateModel(Integer.toString(table.getItems().size()+1),"",cmbItemCode,ItemCode1,cmbItemCode,ItemCode2,Unit,"1","1","1","1","1",Rate,Rate));
							}


							setGrandTotal();

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

	@SuppressWarnings("unchecked")
	private void addDeleteButtonToTable() {
		TableColumn<EstimateCreateModel, Void> colBtn = new TableColumn("Delete");

		colBtn.setPrefWidth(50);
		Callback<TableColumn<EstimateCreateModel, Void>, TableCell<EstimateCreateModel, Void>> cellFactory = new Callback<TableColumn<EstimateCreateModel, Void>, TableCell<EstimateCreateModel, Void>>() {
			@Override
			public TableCell<EstimateCreateModel, Void> call(final TableColumn<EstimateCreateModel, Void> param) {
				final TableCell<EstimateCreateModel, Void> cell = new TableCell<EstimateCreateModel, Void>() {

					private Button  btn = new Button("_-");

					{
						btn.setCache(false);
						btn.setPrefWidth(26);
						btn.setPrefHeight(26);


						btn.setOnAction((ActionEvent event) -> {

							int a=getIndex();
							table.getItems().remove(a);
							setGrandTotal();

							update=true;
							if(update){
								if(!checkDoplicateName(txtProjectName.getText().trim().toString())){
									if(updateData("1")){
										btnFindEvent();
									}
									else{
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
									}
								}
								else{
									if(updateData("2")){
										btnFindEvent();
									}
									else{
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due to some error information don't save.!!");
									}
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
	


}


