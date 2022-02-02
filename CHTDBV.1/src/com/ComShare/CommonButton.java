package com.ComShare;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CommonButton extends HBox{
	public Button btnNew= new Button("_New");
	public Button btnEdit= new Button("_Edit");
	public Button btnSave= new Button("_Save");
	public Button btnSubmit= new Button("_Submit");
	public Button btnConfirm= new Button("_Confirm");
	public Button btnRefresh= new Button("_Refresh");
	public Button btnDelete= new Button("_Delete");
	public Button btnFind= new Button("_Find");
	public Button btnCancel= new Button("_Cancel");
	public Button btnPreview= new Button("_Preview");
	public Button btnChequePrint= new Button("_Print");
	public Button btnExit= new Button("Exit");

	public CommonButton(String New, String Save,String Submit, String Edit, String Delete, String Find,String Confrim, String Refresh, String Cancel, String Preview, String Print,String Exit){

		setSpacing(4);
		if(New.equals("New")){
			SetButtonSize(btnNew);
			//btnNew.setIcon(new ImageIcon("icon/new.png"));	
			this.getChildren().add(btnNew);
		}
		if(Save.equals("Save")){
			SetButtonSize(btnSave);
			//btnSave.setIcon(new ImageIcon("icon/save.png"));	
			this.getChildren().add(btnSave);
		}
		if(Submit.equals("Submit")){
			SetButtonSize(btnSubmit);
			//btnSubmit.setIcon(new ImageIcon("icon/save.png"));	
			this.getChildren().add(btnSubmit);
		}
		if(Edit.equals("Edit")){
			SetButtonSize(btnEdit);
			//btnEdit.setIcon(new ImageIcon("icon/edt.png"));
			this.getChildren().add(btnEdit);
		}
		if(Delete.equals("Delete")){
			SetButtonSize(btnDelete);
			//btnDelete.setIcon(new ImageIcon("icon/delete.png"));	
			this.getChildren().add(btnDelete);
		}
		if(Find.equals("Find")){
			SetButtonSize(btnFind);
			//btnFind.setIcon(new ImageIcon("icon/find.png"));	

			this.getChildren().add(btnFind);
		}
		if(Confrim.equals("Confirm")){
			SetButtonSize(btnConfirm);
			//btnConfirm.setIcon(new ImageIcon("icon/save.png"));	
			this.getChildren().add(btnConfirm);
		}
		if(Refresh.equals("Refresh")){
			SetButtonSize(btnRefresh);
			//btnRefresh.setIcon(new ImageIcon("icon/reresh.png"));	
			this.getChildren().add(btnRefresh);
		}
		if(Cancel.equals("Cancel")){
			SetButtonSize(btnCancel);
			//btnCancel.setIcon(new ImageIcon("icon/reresh.png"));	
			this.getChildren().add(btnCancel);
		}
		if(Preview.equals("Preview")){
			SetButtonSize(btnPreview);
			//btnPreview.setIcon(new ImageIcon("icon/Preview.png"));	
			this.getChildren().add(btnPreview);
		}
		if(Print.equals("Print")){
			SetButtonSize(btnChequePrint);
			//btnChequePrint.setIcon(new ImageIcon("icon/print.png"));	
			this.getChildren().add(btnChequePrint);
		}
		if(Exit.equals("Exit")){
			SetButtonSize(btnExit);
			this.getChildren().add(btnExit);
		}
	}
	private void SetButtonSize(Button Btn){
		
		Btn.setMinWidth(80);
		Btn.setMinHeight(28);
	}
}
