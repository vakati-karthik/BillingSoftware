package com.karthik.BillingSoftware.Operations;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.karthik.BillingSoftware.Utils.Utils;

public class OperationEditorInput implements IEditorInput {
	
	private final Operation operation;
	 
    public OperationEditorInput(Operation operation) {
    	this.operation = operation;
    }
    
    public Operation getOperation() {
        return operation;
    }
    
    @SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return Utils.getInstance().getImageDescriptor("Tirumalatemple.jpg");
	}

	@Override
	public String getName() {
		return Operation.getOperationName(operation);
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		return "";
	}

}
