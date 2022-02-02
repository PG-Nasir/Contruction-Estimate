package com.ContructionModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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
import org.joda.time.LocalDateTime;

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
import javafx.scene.control.DatePicker;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Billing extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	CommonButton cButton=new CommonButton("New","Save","","Edit","","","","Refresh","","Preview","","");
	public Pane pane =new Pane();
	public Scene scene=new Scene(pane, 1250, 640);;
	public Stage mainItemStage=new Stage();
	Button btnFind=new Button("Find");
	Button btnBillFind=new Button("Find");

	FxComboBox cmbBillFind=new FxComboBox();
	FxComboBox cmbProjectFind=new FxComboBox();
	FxComboBox cmbContractorSupplier=new FxComboBox();

	NumberField txtDeductionValue;

	ComboBox cmbRunningBillNo=new ComboBox();
	ComboBox cmbDeduction=new ComboBox();

	CheckBox checkBillConfrim=new CheckBox();
	CheckBox checkIsFinalBill=new CheckBox("Is Final Bill");

	TextField txtDBENo,txtRecorderPage;



	private TableView<BillCreateModal> table = new TableView<>();

	TextField txtCashBookNo,txtBillNo,txtReferenceToAgreement,txtActualCompletionWork,txtWorkOrderCommensNo;
	DatePicker txtBillDate,txtWorkOrderDate,txtCompletionOfWork;
	TextRead txtGrandTotal,txtTotalQty,txtADP,txtProjectCode,txtProjectId;
	TextField txtProjectName,txtMeasurementMadeBy;

	BillCreateModal model=new BillCreateModal("",checkBillConfrim,new Boolean(false),"","","","","","","","","");
	boolean insert=true,update=false,find=false;

	DecimalFormat df = new DecimalFormat("#.000");

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private ContextMenu tableContextMenu;

	private Dialog<ButtonType> checkAditionalCol;
	public Billing(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Billing");
		addCmp();
		btnActionEvent();
		ComponentEnable(true);
		ButtonEnable(true);
		loadProjectName();
		btnNewEvent();
		loadContractorName();
		loadProjectBillNO();
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

		btnBillFind.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnBillFindEvent();
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

			String ProjectId="",AdpNo="",RunningBill="";
			StringTokenizer token=new StringTokenizer(cmbBillFind.getValue().toString(),"/");
			while(token.hasMoreTokens()){
				ProjectId=token.nextToken();
				AdpNo=token.nextToken();
				RunningBill=token.nextToken();
				break;
			}
			
			String Sql = " select a.itemCode,(select Description from tbItemInfo where ItemCode=a.ItemCode) as ItemDescription,(select UnitName from TbUnitInfo where UnitId=(select UnitId from tbItemInfo where ItemCode=a.ItemCode)) as Unit,(select sum(qty) from tbProjectDetails where ProjectId=b.ProjectId and ItemCode1=a.ItemCode) as Qty,a.ExecuteQty,(select ExecuteQty from TbProjectBillDetails where ProjectId=b.ProjectId and BillId=(select BillId from TbProjectBillDetails where ProjectId=b.ProjectId and BillId<b.BillId and ItemCode=a.ItemCode order by BillId desc) and ItemCode=a.ItemCode) as PreviousExeQty,(select avg(rate) from tbProjectDetails where ProjectId=b.ProjectId and ItemCode1=a.ItemCode) as Rate,(select ProjectCode from tbProjectInfo where ProjectId=b.ProjectID) as ProjectCode,(select AdpNo from tbProjectInfo where ProjectId=b.ProjectID) as AdpNo, b.ProjectId,b.MeasurementMadeBy,b.IsFinalBill,b.BDENo,b.RecorderPage,b.CompletionDate,b.CashBookNo,(select Description from tbProjectInfo where ProjectId=b.ProjectID) as ProjectDescription,b.BillId, b.ProjectId,b.CashBookNo,(select Name from TbContractorInfo where AutoId=b.ContractorId) as ContractorName,b.BillDate,b.ReferenceAgreement,b.WorkOrderDate,b.WorkOrderCommentNo,b.RunningBillNo,b.TotalQty,b.TotalAmount,b.DeductionType,b.DeductionValue from TbProjectBillDetails a join TbProjectBill b on b.ProjectId=a.ProjectId and b.BillId=a.BillId  where b.ProjectId='"+ProjectId+"' and RunningBillNo='"+RunningBill+"'";
			System.out.print(Sql);
			String report="src/Report/RunningAccountBill.jrxml";
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(Sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,DBUtil.con);
			JasperViewer.viewReport(jp, false);

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
			AdpNo=token.nextToken();
			System.out.println("ProjectId "+ProjectId);
			System.out.println("AdpNo "+AdpNo);
			break;
		}

		if(checkADPName(AdpNo)){
			try {
				model.getItemCodeList().clear();

				btnRefreshEvent();
				String selectStmt = " select b.ItemCode1,b.ItemCode2,(select CategoryTitle from tbCategoryInfo where categoryId=(select categoryId from TbItemInfo where ItemCode=b.ItemCode1)) as GroupName,(select Description from TbDivisionInfo where DivisionId=(select DivisionId from TbItemInfo where ItemCode=b.ItemCode1)) as DivisionDes,(select Description from TbItemInfo where ItemCode=b.ItemCode1) as ItemDescription,b.Description as Description00000,b.Unit,sum(b.Nos) as Nos,sum(b.length) as length,sum(b.Base) as Base,sum(b.Height) as Height,sum(b.Qty) as Qty,(select IFNULL(sum(ExecuteQty),0) as ExecuteQty from TbProjectBillDetails where  ProjectId=a.ProjectId and ItemCode=b.ItemCode1) as ExecuteQty,avg(b.rate) as rate,sum(b.Amount) as Amount,a.projectid,a.projectCode,a.projectName,a.AdpNo,a.description,a.TotalQty,a.TotalRate,a.TotalAmount from tbprojectinfo a join tbprojectdetails b on a.projectId=b.projectId where a.projectid='"+ProjectId+"' and b.Deduction='0' group by b.ItemCode1, b.Deduction order by b.autoId";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				int i=1;
				while(rs.next()){
					txtProjectCode.setText(rs.getString("projectCode"));
					txtProjectName.setText(rs.getString("description"));
					txtADP.setText(rs.getString("AdpNo"));
					txtProjectId.setText(rs.getString("ProjectId"));


					boolean value=Double.parseDouble(rs.getString("ExecuteQty"))>0?true:false;

					model.getItemCodeList().add(new BillCreateModal(Integer.toString(i),checkBillConfrim,new Boolean(value),rs.getString("ItemCode1"),rs.getString("ItemDescription"),rs.getString("Unit"),df.format(Double.parseDouble(rs.getString("qty"))),df.format(Double.parseDouble(rs.getString("ExecuteQty"))),df.format(Double.parseDouble(rs.getString("rate"))),"0",rs.getString("ProjectId"),""));

					i++;
				}
				cButton.btnNew.setDisable(true);
				cButton.btnSave.setDisable(false);
				cButton.btnEdit.setDisable(true);
				ComponentEnable(false);
			} catch (Exception e) {
				e.printStackTrace();
				new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Invalid name never allow");
		}
	}

	private void btnBillFindEvent(){
		String ProjectId="",AdpNo="",RunningBill="";
		StringTokenizer token=new StringTokenizer(cmbBillFind.getValue().toString(),"/");
		while(token.hasMoreTokens()){
			ProjectId=token.nextToken();
			AdpNo=token.nextToken();
			RunningBill=token.nextToken();
			break;
		}

		if(checkDoplicateName(ProjectId, RunningBill)){
			try {
				model.getItemCodeList().clear();

				String selectStmt = "select a.itemCode,(select Description from tbItemInfo where ItemCode=a.ItemCode) as ItemDescription,(select UnitName from TbUnitInfo where UnitId=(select UnitId from tbItemInfo where ItemCode=a.ItemCode)) as Unit,(select sum(qty) from tbProjectDetails where ProjectId=b.ProjectId and ItemCode1=a.ItemCode) as Qty,a.ExecuteQty,a.Rate,(select ProjectCode from tbProjectInfo where ProjectId=b.ProjectID) as ProjectCode,(select AdpNo from tbProjectInfo where ProjectId=b.ProjectID) as AdpNo, b.ProjectId,b.CashBookNo,(select Description from tbProjectInfo where ProjectId=b.ProjectID) as ProjectDescription,b.BillId, b.ProjectId,b.MeasurementMadeBy,b.IsFinalBill,b.BDENo,b.RecorderPage,b.CompletionDate,b.CashBookNo,(select Name from TbContractorInfo where AutoId=b.ContractorId) as ContractorName,b.BillDate,b.ReferenceAgreement,b.WorkOrderDate,b.WorkOrderCommentNo,b.RunningBillNo,b.TotalQty,b.TotalAmount,b.DeductionType,b.DeductionValue from TbProjectBillDetails a join TbProjectBill b on b.ProjectId=a.ProjectId and b.BillId=a.BillId where b.ProjectId='"+ProjectId+"' and RunningBillNo='"+RunningBill+"'";
				System.out.print(selectStmt);
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				int i=1;
				while(rs.next()){
					txtProjectCode.setText(rs.getString("projectCode"));
					txtProjectName.setText(rs.getString("Projectdescription"));
					txtADP.setText(rs.getString("AdpNo"));
					txtProjectId.setText(rs.getString("ProjectId"));
					txtDBENo.setText(rs.getString("BDENo"));
					txtRecorderPage.setText(rs.getString("RecorderPage"));
					boolean check=rs.getString("IsFinalBill").equals("Final")?true:false;
					checkIsFinalBill.setSelected(check);
					cmbRunningBillNo.setValue(rs.getString("RunningBillNo"));
					txtCashBookNo.setText(rs.getString("CashBookNo"));
					cmbContractorSupplier.setValue(rs.getString("ContractorName"));
					txtBillNo.setText(rs.getString("BillId"));
					txtWorkOrderCommensNo.setText(rs.getString("WorkOrderCommentNo"));
					txtBillDate.getEditor().setText(rs.getString("BillDate"));
					txtWorkOrderDate.getEditor().setText(rs.getString("WorkOrderDate"));
					txtCompletionOfWork.getEditor().setText(rs.getString("CompletionDate"));

					txtDeductionValue.setText(rs.getString("DeductionValue"));
					cmbDeduction.setValue(rs.getString("DeductionType"));
					txtMeasurementMadeBy.setText(rs.getString("MeasurementMadeBy"));
					txtBillNo.setText(rs.getString("BillId"));
					txtReferenceToAgreement.setText(rs.getString("ReferenceAgreement"));
					txtTotalQty.setText(rs.getString("TotalQty"));
					txtGrandTotal.setText(rs.getString("TotalAmount"));

					double amount=Double.parseDouble(rs.getString("ExecuteQty"))*Double.parseDouble(rs.getString("rate"));
					model.getItemCodeList().add(new BillCreateModal(Integer.toString(i),checkBillConfrim,new Boolean(true),rs.getString("ItemCode"),rs.getString("ItemDescription"),rs.getString("Unit"),df.format(Double.parseDouble(rs.getString("qty"))),df.format(Double.parseDouble(rs.getString("Executeqty"))),df.format(Double.parseDouble(rs.getString("rate"))),df.format(amount),rs.getString("ProjectId"),rs.getString("BillId")));

					i++;
					cButton.btnNew.setDisable(true);
					cButton.btnEdit.setDisable(false);
					table.setDisable(false);
					btnEditEvent();
					find=true;
				}


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

				if(!checkDoplicateName(txtProjectId.getText().trim().toString(),cmbRunningBillNo.getValue().toString())){
					if(insertData()){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
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
				if(checkDoplicateName(txtProjectId.getText().trim().toString(),cmbRunningBillNo.getValue().toString())){
					if(updateData()){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!","Information save successfully!!");
						btnRefreshEvent();
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

			String BillId=getMaxBillId();
			String isFinal=checkIsFinalBill.isSelected()?"Final":"";

			String ContractorId=getContractorId(cmbContractorSupplier.getValue().toString());
			String sql="insert into TbProjectBill (BillId,IsFinalBill,BDENo,RecorderPage,CompletionDate,ProjectId,CashBookNo,MeasurementMadeBy,ContractorId,BillDate,ReferenceAgreement,WorkOrderDate,WorkOrderCommentNo,RunningBillNo,TotalQty,TotalAmount,DeductionValue,DeductionType,EntryTime,UserId,UserIp) values ('"+BillId+"','"+isFinal+"','"+txtDBENo.getText().trim().toString()+"','"+txtRecorderPage.getText().trim().toString()+"','"+formatter.format(txtWorkOrderDate.getValue())+"','"+txtProjectId.getText().trim().toString()+"','"+txtCashBookNo.getText().trim().toString()+"','"+txtMeasurementMadeBy.getText().trim().toString()+"','"+ContractorId+"','"+formatter.format(txtBillDate.getValue())+"','"+txtReferenceToAgreement.getText().trim().toString()+"','"+formatter.format(txtWorkOrderDate.getValue())+"','"+txtWorkOrderCommensNo.getText().trim().toString()+"','"+cmbRunningBillNo.getValue()+"','"+txtTotalQty.getText().trim().toString()+"','"+txtGrandTotal.getText().trim().toString()+"','"+txtDeductionValue.getText().trim().toString()+"','"+cmbDeduction.getValue()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"')";
			System.out.println(sql);
			DBUtil.dbExecuteUpdate( sql);

			String udSql="insert into TbUdProjectBill (BillId,IsFinalBill,BDENo,RecorderPage,CompletionDate,ProjectId,CashBookNo,ContractorId,BillDate,ReferenceAgreement,WorkOrderDate,WorkOrderCommentNo,RunningBillNo,TotalQty,TotalAmount,DeductionValue,DeductionType,EntryTime,UserId,UserIp,Flag) values ('"+BillId+"','"+isFinal+"','"+txtDBENo.getText().trim().toString()+"','"+txtRecorderPage.getText().trim().toString()+"','"+formatter.format(txtWorkOrderDate.getValue())+"','"+txtProjectId.getText().trim().toString()+"','"+txtCashBookNo.getText().trim().toString()+"','"+ContractorId+"','"+formatter.format(txtBillDate.getValue())+"','"+txtReferenceToAgreement.getText().trim().toString()+"','"+formatter.format(txtWorkOrderDate.getValue())+"','"+txtWorkOrderCommensNo.getText().trim().toString()+"','"+cmbRunningBillNo.getValue()+"','"+txtTotalQty.getText().trim().toString()+"','"+txtGrandTotal.getText().trim().toString()+"','"+txtDeductionValue.getText().trim().toString()+"','"+cmbDeduction.getValue()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','New')";
			System.out.println(udSql);
			DBUtil.dbExecuteUpdate( udSql);


			for(int a=0;a<table.getItems().size();a++){
				if(table.getItems().get(a).getBillConfrimCheck().isSelected()){
					String autoId=getMaxAutoId();
					String insertSql="insert into TbProjectBillDetails (AutoId,BillId,ProjectId,ItemCode,ExecuteQty,Rate,EntryTime,UserId,UserIp) values ('"+autoId+"','"+BillId+"','"+txtProjectId.getText().trim().toString()+"','"+table.getItems().get(a).getItemCode()+"','"+table.getItems().get(a).getExecuteQty().getText().trim().toString()+"','"+table.getItems().get(a).getRate().getText().trim().toString()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"') ";
					DBUtil.dbExecuteUpdate(insertSql);;

					String insertUdSql="insert into TbUdProjectBillDetails (AutoId,BillId,ProjectId,ItemCode,ExecuteQty,Rate,EntryTime,UserId,UserIp,Flag) values ('"+autoId+"','"+BillId+"','"+txtProjectId.getText().trim().toString()+"','"+table.getItems().get(a).getItemCode()+"','"+table.getItems().get(a).getExecuteQty().getText().trim().toString()+"','"+table.getItems().get(a).getRate().getText().trim().toString()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','New') ";
					DBUtil.dbExecuteUpdate(insertUdSql);;
				}
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

	private boolean updateData(){
		try {

			String BillId=txtBillNo.getText().trim().toString();;

			String ContractorId=getContractorId(cmbContractorSupplier.getValue().toString());
			
			String isFinal=checkIsFinalBill.isSelected()?"Final":"";
			String sql="update TbProjectBill set IsFinalBill='"+isFinal+"',BDENo='"+txtDBENo.getText().trim().toString()+"',RecorderPage='"+txtRecorderPage.getText().trim().toString()+"',CompletionDate='"+formatter.format(txtWorkOrderDate.getValue())+"',CashBookNo='"+txtCashBookNo.getText().trim().toString()+"',MeasurementMadeBy='"+txtMeasurementMadeBy.getText().trim().toString()+"',ContractorId='"+ContractorId+"',BillDate='"+formatter.format(txtBillDate.getValue())+"',ReferenceAgreement='"+txtReferenceToAgreement.getText().trim().toString()+"',WorkOrderDate='"+formatter.format(txtWorkOrderDate.getValue())+"',WorkOrderCommentNo='"+txtWorkOrderCommensNo.getText().trim().toString()+"',RunningBillNo='"+cmbRunningBillNo.getValue()+"',TotalQty='"+txtTotalQty.getText().trim().toString()+"',TotalAmount='"+txtGrandTotal.getText().trim().toString()+"',DeductionValue='"+txtDeductionValue.getText().trim().toString()+"',DeductionType='"+cmbDeduction.getValue()+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+sessionBeam.getUserId()+"',UserIp='"+sessionBeam.getUserIp()+"' where BillId='"+BillId+"'";
			System.out.println(sql);
			DBUtil.dbExecuteUpdate( sql);

			String udSql="insert into TbUdProjectBill (BillId,ProjectId,IsFinalBill,BDENo,RecorderPage,CompletionDate,CashBookNo,ContractorId,BillDate,ReferenceAgreement,WorkOrderDate,WorkOrderCommentNo,RunningBillNo,TotalQty,TotalAmount,DeductionValue,DeductionType,EntryTime,UserId,UserIp,Flag) values ('"+BillId+"','"+txtProjectId.getText().trim().toString()+"','"+isFinal+"','"+txtDBENo.getText().trim().toString()+"','"+txtRecorderPage.getText().trim().toString()+"','"+formatter.format(txtWorkOrderDate.getValue())+"','"+txtCashBookNo.getText().trim().toString()+"','"+ContractorId+"','"+formatter.format(txtBillDate.getValue())+"','"+txtReferenceToAgreement.getText().trim().toString()+"','"+formatter.format(txtWorkOrderDate.getValue())+"','"+txtWorkOrderCommensNo.getText().trim().toString()+"','"+cmbRunningBillNo.getValue()+"','"+txtTotalQty.getText().trim().toString()+"','"+txtGrandTotal.getText().trim().toString()+"','"+txtDeductionValue.getText().trim().toString()+"','"+cmbDeduction.getValue()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','Edit')";
			System.out.println(udSql);
			DBUtil.dbExecuteUpdate( udSql);

			String deSql="delete from TbProjectBillDetails where BillId='"+BillId+"'";
			DBUtil.dbExecuteUpdate(deSql);

			for(int a=0;a<table.getItems().size();a++){
				if(table.getItems().get(a).getBillConfrimCheck().isSelected()){
					String autoId=getMaxAutoId();
					String insertSql="insert into TbProjectBillDetails (AutoId,BillId,ProjectId,ItemCode,ExecuteQty,Rate,EntryTime,UserId,UserIp) values ('"+autoId+"','"+BillId+"','"+txtProjectId.getText().trim().toString()+"','"+table.getItems().get(a).getItemCode()+"','"+table.getItems().get(a).getExecuteQty().getText().trim().toString()+"','"+table.getItems().get(a).getRate().getText().trim().toString()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"') ";
					DBUtil.dbExecuteUpdate(insertSql);;

					String insertUdSql="insert into TbUdProjectBillDetails (AutoId,BillId,ProjectId,ItemCode,ExecuteQty,Rate,EntryTime,UserId,UserIp,Flag) values ('"+autoId+"','"+BillId+"','"+txtProjectId.getText().trim().toString()+"','"+table.getItems().get(a).getItemCode()+"','"+table.getItems().get(a).getExecuteQty().getText().trim().toString()+"','"+table.getItems().get(a).getRate().getText().trim().toString()+"',CURRENT_TIMESTAMP,'"+sessionBeam.getUserId()+"','"+sessionBeam.getUserIp()+"','Edit') ";
					DBUtil.dbExecuteUpdate(insertUdSql);;
				}
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


	private boolean checkDoplicateName(String projectId,String RunningBill){
		try {
			String selectStmt = "select * from TbProjectBill where ProjectId='"+projectId+"' and RunningBillNo='"+RunningBill+"'";
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
	private boolean checkADPName(String name){
		try {
			String selectStmt = "select ADPNo from tbprojectinfo where ADPNo='"+name+"'";
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

	public void loadProjectBillNO(){
		try {
			cmbBillFind.data.clear();
			String selectStmt = "select ProjectId,RunningBillNo,(select ADPNO from tbProjectInfo where ProjectId=TbProjectBill.ProjectId) as ADPNO from TbProjectBill";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbBillFind.data.add(rs.getString("ProjectId")+"/"+rs.getString("AdpNo")+"/"+rs.getString("RunningBillNo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	public void loadContractorName(){
		try {
			cmbContractorSupplier.data.clear();
			String selectStmt = "select Name from TbContractorInfo order by Name";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				cmbContractorSupplier.data.add(rs.getString("Name"));
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
		txtBillNo.setText(getMaxBillId());
		checkIsFinalBill.setSelected(false);
		txtProjectCode.setText("");
		txtProjectName.setText("");
		txtRecorderPage.setText("");
		txtDBENo.setText("");
		
		txtADP.setText("");
		txtTotalQty.setText("");
		txtGrandTotal.setText("");
		txtBillDate.setValue(LocalDate.now());
		txtWorkOrderDate.setValue(LocalDate.now());
		txtCashBookNo.setText("");
		cmbRunningBillNo.setValue(null);
		cmbContractorSupplier.setValue(null);
		txtReferenceToAgreement.setText("");
		txtWorkOrderCommensNo.setText("");
		txtDeductionValue.setText("");
		cmbDeduction.setValue("");
		txtMeasurementMadeBy.setText("");
		txtWorkOrderDate.setValue(LocalDate.now());
		txtBillDate.setValue(LocalDate.now());
		txtCompletionOfWork.setValue(LocalDate.now());
		insert=true;
		update=false;
		find=false;
		loadProjectBillNO();
	}
	private void ComponentEnable(boolean t){

		table.setDisable(t);
		txtProjectCode.setDisable(t);
		txtProjectName.setDisable(t);
		txtADP.setDisable(t);
		txtCashBookNo.setDisable(t);
		txtWorkOrderCommensNo.setDisable(t);
		txtWorkOrderDate.setDisable(t);
		txtBillDate.setDisable(t);
		txtReferenceToAgreement.setDisable(t);
		cmbContractorSupplier.setDisable(t);
	}
	private String getMaxBillId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(BillId),0)+1) as BillId from TbProjectBill";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("BillId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	private String getContractorId(String Name){
		String Id="";
		try {
			String selectStmt = "select AutoId from TbContractorInfo where Name='"+Name+"'";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("AutoId");
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}

		return Id;
	}
	private String getMaxAutoId(){
		String Id="";
		String selectStmt = "select (IFNULL(max(AutoId),0)+1)as AutoId from TbProjectBillDetails";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				Id=rs.getString("AutoId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return Id;
	}

	private boolean checkEmptyFeild()
	{
		if(!txtProjectName.getText().trim().toString().isEmpty()){
			if(!txtProjectCode.getText().trim().toString().isEmpty()){
				if(!txtADP.getText().trim().toString().isEmpty()){
					if(cmbRunningBillNo.getValue()!=null){
						if(!txtCashBookNo.getText().trim().toString().isEmpty()){
							if(cmbContractorSupplier.getValue()!=null){
								if(txtBillDate.getValue()!=null){
									if(txtWorkOrderDate.getValue()!=null){
										if(!txtReferenceToAgreement.getText().trim().toString().isEmpty()){
											if(!txtWorkOrderCommensNo.getText().trim().toString().isEmpty()){
												return true;
											}
											else{
												new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Work Order Commance No Date");
											}
										}
										else{
											new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Reference Agreement No");
										}
									}
									else{
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Work Order Date");
									}
								}
								else{
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Bill Date");
								}
							}
							else{
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Contractor Name");
							}
						}
						else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Cash Book");
						}
					}
					else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide Running Bill");
					}
				}
				else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide ADP");
				}
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
		double totalQty=0;

		for(int a=0;a<table.getItems().size();a++){
			if(table.getItems().get(a).getBillConfrimCheck().isSelected()){
				totalQty=totalQty+Double.parseDouble(table.getItems().get(a).getExecuteQty().getText().trim().toString().isEmpty()?"0":table.getItems().get(a).getExecuteQty().getText().trim().toString());
				totalAmount=totalAmount+Double.parseDouble(table.getItems().get(a).getAmount());
			}
		}
		txtGrandTotal.setText(df.format(totalAmount));
		txtTotalQty.setText(df.format(totalQty));
	}
	@SuppressWarnings("unchecked")
	private void addCmp(){

		Label lblProjectADP1=new Label("ADP No/Project Code");
		lblProjectADP1.setLayoutX(10);
		lblProjectADP1.setLayoutY(12);

		cmbProjectFind.setLayoutX(140);
		cmbProjectFind.setLayoutY(8);
		cmbProjectFind.setPrefWidth(230);
		cmbProjectFind.setMinHeight(28);

		btnFind.setLayoutX(370);
		btnFind.setLayoutY(8);
		btnFind.setMinWidth(80);
		btnFind.setMinHeight(28);



		Label lblProjectCode=new Label("Project Code");
		lblProjectCode.setLayoutX(10);
		lblProjectCode.setLayoutY(45);

		txtProjectCode=new TextRead();
		txtProjectCode.setPrefWidth(160);
		txtProjectCode.setLayoutX(140);
		txtProjectCode.setLayoutY(40);


		Label lblRuningBill=new Label("Running Bill");
		lblRuningBill.setLayoutX(300);
		lblRuningBill.setLayoutY(45);


		cmbRunningBillNo=new ComboBox();
		cmbRunningBillNo.setPrefWidth(75);
		cmbRunningBillNo.setLayoutX(375);
		cmbRunningBillNo.setLayoutY(40);
		cmbRunningBillNo.getItems().addAll("","1st","2nd","3rd","4th","5th","6th","7th","8th","9th","10th","11th","12th","13th","14th","15th","16th","17th","18th","19th","20th");

		checkIsFinalBill.setLayoutX(460);
		checkIsFinalBill.setLayoutY(40);

		Label lblProjectId=new Label("Project Id");
		lblProjectId.setLayoutX(300);
		lblProjectId.setLayoutY(75);

		txtProjectId=new TextRead();
		txtProjectId.setPrefWidth(75);
		txtProjectId.setLayoutX(375);
		txtProjectId.setLayoutY(70);

		Label lblDBNo=new Label("BDE/B/Ban");
		lblDBNo.setMaxWidth(100);
		lblDBNo.setLayoutX(460);
		lblDBNo.setLayoutY(75);

		txtDBENo=new TextField();
		txtDBENo.setPrefWidth(100);
		txtDBENo.setLayoutX(540);
		txtDBENo.setLayoutY(70);

		Label lblRecorderPage=new Label("Recorded Page");
		lblRecorderPage.setMaxWidth(100);
		lblRecorderPage.setLayoutX(460);
		lblRecorderPage.setLayoutY(140);

		txtRecorderPage=new TextField();
		txtRecorderPage.setPrefWidth(140);
		txtRecorderPage.setLayoutX(565);
		txtRecorderPage.setLayoutY(135);

		Label lblCompletionWork=new Label("Comp. Work Date");
		lblCompletionWork.setLayoutX(460);
		lblCompletionWork.setLayoutY(170);

		txtCompletionOfWork=new DatePicker();
		txtCompletionOfWork.setPrefWidth(140);
		txtCompletionOfWork.setLayoutX(565);
		txtCompletionOfWork.setLayoutY(165);

		Label lblProjectADP=new Label("ADP NO");
		lblProjectADP.setLayoutX(10);
		lblProjectADP.setLayoutY(75);

		txtADP=new TextRead();
		txtADP.setPrefWidth(160);
		txtADP.setLayoutX(140);
		txtADP.setLayoutY(70);

		Label lblProjectName=new Label("Name Of Work");
		lblProjectName.setLayoutX(10);
		lblProjectName.setLayoutY(110);

		txtProjectName=new TextField();
		txtProjectName.setPrefWidth(565);
		txtProjectName.setPrefHeight(25);
		txtProjectName.setLayoutX(140);
		txtProjectName.setLayoutY(100);

		Label lblMeasurementMadeBy=new Label("Measurement Made By");
		lblMeasurementMadeBy.setLayoutX(10);
		lblMeasurementMadeBy.setLayoutY(140);

		txtMeasurementMadeBy=new TextField();
		txtMeasurementMadeBy.setPrefWidth(305);
		txtMeasurementMadeBy.setPrefHeight(25);
		txtMeasurementMadeBy.setLayoutX(140);
		txtMeasurementMadeBy.setLayoutY(130);


		Label lblCashBooNo=new Label("Cash Book No");
		lblCashBooNo.setLayoutX(10);
		lblCashBooNo.setLayoutY(170);


		txtCashBookNo=new TextField();
		txtCashBookNo.setPrefWidth(305);
		txtCashBookNo.setLayoutX(140);
		txtCashBookNo.setLayoutY(165);

		Label lblNameOfContractor=new Label("Contractor/Supplier");
		lblNameOfContractor.setLayoutX(10);
		lblNameOfContractor.setLayoutY(195);


		cmbContractorSupplier=new FxComboBox<>();
		cmbContractorSupplier.setPrefWidth(290);
		cmbContractorSupplier.setLayoutX(140);
		cmbContractorSupplier.setLayoutY(195);


		Label lblSerialNoThisBill=new Label("Serial No");
		lblSerialNoThisBill.setLayoutX(1020);
		lblSerialNoThisBill.setLayoutY(15);


		txtBillNo=new TextRead();
		txtBillNo.setPrefWidth(160);
		txtBillNo.setLayoutX(1080);
		txtBillNo.setLayoutY(10);

		Label lblDateOfBill=new Label("Bill Date");
		lblDateOfBill.setLayoutX(1020);
		lblDateOfBill.setLayoutY(45);


		txtBillDate=new DatePicker();
		txtBillDate.setPrefWidth(160);
		txtBillDate.setLayoutX(1080);
		txtBillDate.setLayoutY(40);

		Label lblReferenceToAgreement=new Label("Reference To Agreement");
		lblReferenceToAgreement.setLayoutX(930);
		lblReferenceToAgreement.setLayoutY(75);

		txtReferenceToAgreement=new TextField();
		txtReferenceToAgreement.setPrefWidth(160);
		txtReferenceToAgreement.setLayoutX(1080);
		txtReferenceToAgreement.setLayoutY(70);

		Label lblWorkOrderDate=new Label("Work Order Date");
		lblWorkOrderDate.setLayoutX(975);
		lblWorkOrderDate.setLayoutY(105);

		txtWorkOrderDate=new DatePicker();
		txtWorkOrderDate.setPrefWidth(160);
		txtWorkOrderDate.setLayoutX(1080);
		txtWorkOrderDate.setLayoutY(100);

		Label lblWorkOrderCommenceNo=new Label("Work Order Commence No");
		lblWorkOrderCommenceNo.setLayoutX(910);
		lblWorkOrderCommenceNo.setLayoutY(135);


		txtWorkOrderCommensNo=new TextField();
		txtWorkOrderCommensNo.setPrefWidth(160);
		txtWorkOrderCommensNo.setLayoutX(1080);
		txtWorkOrderCommensNo.setLayoutY(130);

		Label lblBillNo=new Label("Bill No");
		lblBillNo.setLayoutX(870);
		lblBillNo.setLayoutY(175);

		cmbBillFind.setPrefWidth(240);
		cmbBillFind.setMinHeight(28);
		cmbBillFind.setLayoutX(913);
		cmbBillFind.setLayoutY(170);


		btnBillFind.setMinWidth(80);
		btnBillFind.setMinHeight(28);
		btnBillFind.setLayoutX(1160);
		btnBillFind.setLayoutY(170);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter( table);

		vbox.getChildren().addAll( borderPane);
		vbox.setLayoutX(10);
		vbox.setLayoutY(10);


		Label lblGrandTotal=new Label("Grand Qty");
		lblGrandTotal.setLayoutX(825);
		lblGrandTotal.setLayoutY(570);

		txtTotalQty=new TextRead();
		txtTotalQty.setMaxWidth(100);
		txtTotalQty.setLayoutX(890);
		txtTotalQty.setLayoutY(565);

		Label lblDeduction=new Label("Deduction %");
		lblDeduction.setLayoutX(825);
		lblDeduction.setLayoutY(600);

		txtDeductionValue=new NumberField();
		txtDeductionValue.setMaxWidth(100);
		txtDeductionValue.setLayoutX(890);
		txtDeductionValue.setLayoutY(595);

		Label lblTotalAmount=new Label("Amount");
		lblTotalAmount.setMaxWidth(100);
		lblTotalAmount.setLayoutX(1020);
		lblTotalAmount.setLayoutY(570);

		txtGrandTotal=new TextRead();
		txtGrandTotal.setMaxWidth(125);
		txtGrandTotal.setLayoutX(1105);
		txtGrandTotal.setLayoutY(565);

		Label lblDeductionType=new Label("Deduction");
		lblDeductionType.setMaxWidth(100);
		lblDeductionType.setLayoutX(1030);
		lblDeductionType.setLayoutY(600);

		cmbDeduction=new ComboBox();
		cmbDeduction.setPrefWidth(125);
		cmbDeduction.setLayoutX(1105);
		cmbDeduction.setLayoutY(595);
		cmbDeduction.getItems().addAll("","Asper","Above","Less");



		pane.getChildren().addAll(lblProjectADP1,lblProjectCode,txtProjectCode,lblProjectName,txtProjectName,lblMeasurementMadeBy,txtMeasurementMadeBy,
				lblProjectADP,txtADP,cmbProjectFind,btnFind,lblRuningBill,cmbRunningBillNo,lblProjectId,txtProjectId,lblCashBooNo,txtCashBookNo,lblNameOfContractor,cmbContractorSupplier,lblSerialNoThisBill,txtBillNo,
				lblDateOfBill,txtBillDate,lblReferenceToAgreement,txtReferenceToAgreement,lblWorkOrderDate,txtWorkOrderDate,lblWorkOrderCommenceNo,txtWorkOrderCommensNo,lblBillNo,cmbBillFind,btnBillFind,table,cButton,lblGrandTotal,txtTotalQty,lblTotalAmount,txtGrandTotal,
				lblDeduction,txtDeductionValue,lblDeductionType,cmbDeduction,vbox,checkIsFinalBill,lblDBNo,txtDBENo,
				lblRecorderPage,txtRecorderPage,lblCompletionWork,txtCompletionOfWork);
		
		cButton.setLayoutX(10);
		cButton.setLayoutY(570);


		final Control ob[] = {txtProjectCode,txtADP,txtProjectName,txtCashBookNo,cmbContractorSupplier,txtBillDate,txtReferenceToAgreement,txtWorkOrderDate,txtWorkOrderCommensNo};	
		new FocusMoveByEnter(ob);


		final TableColumn<BillCreateModal, String> SLCol = new TableColumn<BillCreateModal, String>("SL#");
		final TableColumn<BillCreateModal, CheckBox> BillConfrimCol = new TableColumn<BillCreateModal, CheckBox>("Bill Confrim");
		final TableColumn<BillCreateModal, String> ItemCodeCol = new TableColumn<BillCreateModal, String>("Item Code");
		final TableColumn<BillCreateModal, String> DescriptionCol = new TableColumn<BillCreateModal, String>("Items of work or supplies (grouped under 'sub-heads') ans sub-work of estimate");
		final TableColumn<BillCreateModal, String> UnitCol = new TableColumn<BillCreateModal, String>("Unit");
		final TableColumn<BillCreateModal, String> QtyCol = new TableColumn<BillCreateModal, String>("Qty");
		final TableColumn<BillCreateModal, NumberField> ExecuteQtyCol = new TableColumn<BillCreateModal, NumberField>("Quantity executed for supplied up to date as per measurement");

		final TableColumn<BillCreateModal, String> RateCol = new TableColumn<BillCreateModal, String>("Rate");
		final TableColumn<BillCreateModal, String> AmountCol = new TableColumn<BillCreateModal, String>("Amount");



		SLCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, String>("SL"));
		ItemCodeCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, String>("ItemCode"));
		DescriptionCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, String>("Description"));
		UnitCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, String>("Unit"));
		QtyCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, String>("Qty"));
		ExecuteQtyCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, NumberField>("ExecuteQty"));
		BillConfrimCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, CheckBox>("BillConfrim"));
		RateCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, String>("Rate"));
		AmountCol.setCellValueFactory(new PropertyValueFactory<BillCreateModal, String>("Amount"));


		table.getColumns().addAll(SLCol,BillConfrimCol,ItemCodeCol,DescriptionCol,UnitCol,QtyCol,ExecuteQtyCol,RateCol,AmountCol);

		table.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				setGrandTotal(); 
			}
		});



		SLCol.setPrefWidth(60);
		ItemCodeCol.setPrefWidth(140);
		DescriptionCol.setPrefWidth(400);
		BillConfrimCol.setPrefWidth(100);
		UnitCol.setPrefWidth(60);
		QtyCol.setPrefWidth(100);
		ExecuteQtyCol.setPrefWidth(100);
		RateCol.setPrefWidth(130);
		AmountCol.setPrefWidth(130);



		table.setPrefWidth(1230);
		table.setPrefHeight(340);
		table.setEditable(true);
		table.setLayoutX(10);
		table.setLayoutY(230);
		table.setItems(model.getItemCodeList());






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


}


