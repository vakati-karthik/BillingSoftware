package com.karthik.BillingSoftware.Load;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.PlatformUI;

import com.karthik.BillingSoftware.Utils.Utils;

public class Perspective implements IPerspectiveFactory {
	
	private Utils utils;

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String displayArea = layout.getEditorArea();
		layout.addStandaloneView("com.karthik.BillingSoftware.OperationsView", true,
				IPageLayout.LEFT, 0.75f, displayArea);
		layout.setFixed(true);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		shell.setMaximized(true);
		shell.setText("Billing Software");
		utils = Utils.getInstance();
		Image image = utils.getImage(utils.getImageDescriptor("billing_software"));
		shell.setImage(image);
	}

}
