package com.karthik.BillingSoftware.Operations;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;

import com.karthik.BillingSoftware.UI.CreateNewBillUI;
import com.karthik.BillingSoftware.UI.ViewBillInfoUI;
import com.karthik.BillingSoftware.UI.ViewInfoByDateUI;
import com.karthik.BillingSoftware.UI.ViewInfoByPlaceUI;
import com.karthik.BillingSoftware.UI.ViewVehicleInfoUI;

public class OperationEditor extends EditorPart {
	
	public static final String ID = "com.karthik.BillingSoftware.OperationsEditor";
	private OperationEditorInput input;
	private Operation operation;	
	private CreateNewBillUI createNewBillUI;
	private ViewBillInfoUI viewBillInfoUI;
	private ViewVehicleInfoUI viewVehicleInfoUI;
	private ViewInfoByDateUI viewInfoByDateUI;
	private ViewInfoByPlaceUI viewInfoByPlaceUI;

	public OperationEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof OperationEditorInput)) {
			throw new RuntimeException("Wrong input");
		}		
		this.input = (OperationEditorInput) input;
		setSite(site);
		setInput(input);
		operation = this.input.getOperation();
		setPartName(Operation.getOperationName(operation));
		setTitleImage(Operation.getOperationImage(operation));
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		
		ManagedForm mForm = new ManagedForm(parent);
		FormToolkit toolkit = mForm.getToolkit();
		toolkit.adapt(parent);
		ScrolledForm sform = mForm.getForm();
		sform.setLayoutData(new GridData(GridData.FILL_BOTH));
		sform.getBody().setLayout(new GridLayout(3, false));
		toolkit.decorateFormHeading(sform.getForm());
		sform.getBody().setFocus();
		
		if (operation.equals(Operation.CREATE_NEW_BILL)) {
			createNewBillUI = new CreateNewBillUI(toolkit, sform);
			createNewBillUI.createUI();
		}
		
		else if (operation.equals(Operation.VIEW_BILL_INFO)) {
			viewBillInfoUI = new ViewBillInfoUI(toolkit, sform);
			viewBillInfoUI.createUI();
		}
		
		else if (operation.equals(Operation.VIEW_VEHICLE_INFO)) {
			viewVehicleInfoUI = new ViewVehicleInfoUI(toolkit, sform);
			viewVehicleInfoUI.createUI();
		}
		
		else if (operation.equals(Operation.VIEW_INFO_BY_DATE)) {
			viewInfoByDateUI = new ViewInfoByDateUI(toolkit, sform);
			viewInfoByDateUI.createUI();
		}
		else {
			viewInfoByPlaceUI = new ViewInfoByPlaceUI(toolkit, sform);
			viewInfoByPlaceUI.createUI();
		}
	}

	@Override
	public void setFocus() {
	}
}
