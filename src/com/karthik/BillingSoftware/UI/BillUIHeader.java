package com.karthik.BillingSoftware.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.karthik.BillingSoftware.Utils.Utils;

public class BillUIHeader {
	
	private Utils utils;
	
	protected void createHeader(Composite rootComposite, FormToolkit toolkit, int columnSpan) {
		utils = Utils.getInstance();
		Composite topComposite = toolkit.createComposite(rootComposite, SWT.CENTER);
		GridData gd = new GridData();
		gd.horizontalSpan = columnSpan;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		topComposite.setLayoutData(gd);
		GridLayout gl = new GridLayout();
		gl.numColumns = 3;
		gl.horizontalSpacing = 15;
		topComposite.setLayout(gl);
		toolkit.createLabel(topComposite, "").setImage(utils.getImage(utils.getImageDescriptor("venky.jpg")));
		toolkit.createLabel(topComposite, "").setImage(utils.getImage(utils.getImageDescriptor("vst.jpg")));
		toolkit.createLabel(topComposite, "").setImage(utils.getImage(utils.getImageDescriptor("truck2")));
	}

}
