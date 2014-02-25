package com.karthik.BillingSoftware.UI;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Constants.UIConstants;
import com.karthik.BillingSoftware.JDBC.CreateNewBillJDBC;
import com.karthik.BillingSoftware.Listeners.CustomKeyListener;
import com.karthik.BillingSoftware.Listeners.CustomModifyListener;
import com.karthik.BillingSoftware.Print.PrintBill;
import com.karthik.BillingSoftware.Utils.JDBCUtils;
import com.karthik.BillingSoftware.Utils.Month;
import com.karthik.BillingSoftware.Utils.Utils;

public class CreateNewBillUI extends BillUIHeader{
	
	private ArrayList<String> cities;
	private String[] packagesArray = new String[100];
	
	private Composite rootComposite;
	
	private Combo stationCombo;
	private Combo deliveryAtCombo;
	private Text consignor;
	private Combo packagesCombo;
	private Text packagesDescription;
	private Text privateMarks;
	private Text remarks;
	
	private Combo monthCombo;
	private Combo dayCombo;
	private Combo yearCombo;
	private Text billNo;
	private Text truckNo;
	private Text consignee;
	private Text weight;
	private Combo weightCombo;
	private Text freightPerTonne;
	private Text checkPost;
	private Text sidePoles;
	private Text craineCharge;
	private Text roadExpansion;
	private Text doorOpen;
	private Text weighmentCharge;
	private Text valueOfGoods;
	private Text total;
	
	private Text driverCharges;
	private Text loadingCharges;
	private Text checkPost1;
	private Text sidePoles1;
	private Text otherExpenses;
	private Text total1;
	
	private Button create;
	private Button noPrint;
	private Button reset;
	
	FormToolkit toolkit;
	ScrolledForm sform;
	Color color;
	Display display;
	
	private HashMap<String, String> billDetails;
	
	private Utils utils;
	private JDBCUtils jdbcUtils;
	private CreateNewBillJDBC createNewBillJDBC;
	private PrintBill printBill;
	
	public CreateNewBillUI(FormToolkit toolkit, ScrolledForm sform) {
		this.toolkit = toolkit;
		this.sform = sform;
	}
	
	public void createUI () {
		
		utils = Utils.getInstance();
		jdbcUtils =  new JDBCUtils();
		cities = utils.getCitiesFromExcel();
		createNewBillJDBC = new CreateNewBillJDBC();
		display = Display.getCurrent();
		color = new Color(display, 234, 234, 234);
		
		rootComposite = toolkit.createComposite(sform.getBody(), SWT.CENTER);
		rootComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		GridLayout gl = new GridLayout();
		gl.numColumns = 5;
		gl.horizontalSpacing = 25;
		rootComposite.setLayout(gl);
		
		createHeader(rootComposite, toolkit, 5);
		
		Composite leftComposite = toolkit.createComposite(rootComposite, SWT.CENTER);
		leftComposite.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, true, true));
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.verticalSpacing = 15;
		gl.horizontalSpacing = 15;
		leftComposite.setLayout(gl);
		
		toolkit.createLabel(leftComposite, BillConstants.station).setBackground(color);
		stationCombo = new Combo(leftComposite, SWT.BORDER);
		stationCombo.setLayoutData(new GridData(150, 14));
		stationCombo.setItems(cities.toArray(new String[0]));
		stationCombo.setData("Start place");// This is useful in validateInsert() method
		
		toolkit.createLabel(leftComposite, BillConstants.deliveryAt).setBackground(color);
		deliveryAtCombo = new Combo(leftComposite, SWT.BORDER);
		deliveryAtCombo.setLayoutData(new GridData(150, 14));
		deliveryAtCombo.setItems(cities.toArray(new String[0]));
		deliveryAtCombo.setData("Destination place");// This is useful in validateInsert() method
		
		toolkit.createLabel(leftComposite, BillConstants.consignor).setBackground(color);
		consignor = toolkit.createText(leftComposite, "", SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		consignor.setLayoutData(new GridData(150, 50));
		
		toolkit.createLabel(leftComposite, BillConstants.packages).setBackground(color);
		packagesCombo = new Combo(leftComposite, SWT.BORDER | SWT.READ_ONLY);
		packagesCombo.setLayoutData(new GridData(150, 14));
		for (int i = 1; i <= 100; i++)
			packagesArray[i-1] = "" + i;
		packagesCombo.setItems(packagesArray);
		
		toolkit.createLabel(leftComposite, BillConstants.packagesDescription).setBackground(color);
		packagesDescription = toolkit.createText(leftComposite, "", SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		packagesDescription.setLayoutData(new GridData(150, 50));
		
		toolkit.createLabel(leftComposite, BillConstants.privateMarks).setBackground(color);
		privateMarks = toolkit.createText(leftComposite, "", SWT.BORDER);
		privateMarks.setLayoutData(new GridData(167, 50));
		
		toolkit.createLabel(leftComposite, BillConstants.remarks).setBackground(color);
		remarks = toolkit.createText(leftComposite, "", SWT.BORDER);
		remarks.setLayoutData(new GridData(167, 50));
		
		leftComposite.pack();
		
		Label seperator = toolkit.createSeparator(rootComposite, SWT.CENTER | SWT.COLOR_WIDGET_BACKGROUND);
		seperator.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		Composite middleComposite = toolkit.createComposite(rootComposite, SWT.CENTER);
		middleComposite.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, true, true));
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.verticalSpacing = 15;
		gl.horizontalSpacing = 15;
		middleComposite.setLayout(gl);
		
		toolkit.createLabel(middleComposite, "Date").setBackground(color);
		
		Composite dateComposite = toolkit.createComposite(middleComposite, SWT.BEGINNING);
		dateComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true));
		gl = new GridLayout();
		gl.numColumns = 3;
		gl.horizontalSpacing = 5;
		gl.marginLeft = -5;
		dateComposite.setLayout(gl);
		dateComposite.setBackground(color);
		
		monthCombo = new Combo(dateComposite, SWT.BORDER | SWT.READ_ONLY);
		monthCombo.setLayoutData(new GridData(60, 14));
		monthCombo.setItems(Month.getMonths());
		monthCombo.setData("Month");// This is useful in validateInsert() method
		dayCombo = new Combo(dateComposite, SWT.BORDER | SWT.READ_ONLY);
		dayCombo.setLayoutData(new GridData(10, 14));
		dayCombo.setData("Day");// This is useful in validateInsert() method
		yearCombo = new Combo(dateComposite, SWT.BORDER | SWT.READ_ONLY);
		yearCombo.setLayoutData(new GridData(22, 14));
		yearCombo.setItems(new String[]{"2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"});
		yearCombo.setData("Year"); // This is useful in validateInsert() method
		
		utils.setTime(dayCombo, monthCombo, yearCombo);
		
		toolkit.createLabel(middleComposite, BillConstants.billNumber).setBackground(color);
		billNo = toolkit.createText(middleComposite, "", SWT.BORDER | SWT.READ_ONLY);
		billNo.setLayoutData(new GridData(167, 17));
		billNo.setText(jdbcUtils.getNextBillNo());
		
		toolkit.createLabel(middleComposite, BillConstants.truckNumber).setBackground(color);
		truckNo = toolkit.createText(middleComposite, "", SWT.BORDER);
		truckNo.setLayoutData(new GridData(167, 17));
		truckNo.setData("Truck Number");// This is useful in validateInsert() method
		
		toolkit.createLabel(middleComposite, BillConstants.consignee).setBackground(color);
		consignee = toolkit.createText(middleComposite, "", SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		consignee.setLayoutData(new GridData(150, 50));
		
		toolkit.createLabel(middleComposite, BillConstants.actualWeight).setBackground(color);
		Composite weightComposite = toolkit.createComposite(middleComposite, SWT.BEGINNING);
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.horizontalSpacing = 15;
		gl.marginLeft = -5;
		gl.marginTop = -5;
		gl.marginBottom = -5;
		weightComposite.setLayout(gl);
		weight = toolkit.createText(weightComposite, "", SWT.BORDER);
		weight.setLayoutData(new GridData(75, 17));
		weight.setData("Weight");// This is useful in validateInsert() method
		weightCombo = new Combo(weightComposite, SWT.BORDER | SWT.READ_ONLY);
		weightCombo.setLayoutData(new GridData(50, 14));
		weightCombo.add("Kgs");
		weightCombo.add("Quintals");
		weightCombo.add("Tonnes");
		weightCombo.setText("Tonnes");
		weightComposite.setBackground(color);
		
		toolkit.createLabel(middleComposite, BillConstants.freightPerTonne).setBackground(color);
		freightPerTonne = toolkit.createText(middleComposite, "", SWT.BORDER);
		freightPerTonne.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.checkPost).setBackground(color);
		checkPost = toolkit.createText(middleComposite, "", SWT.BORDER);
		checkPost.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.sidePoles).setBackground(color);
		sidePoles = toolkit.createText(middleComposite, "", SWT.BORDER);
		sidePoles.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.craineCharge).setBackground(color);
		craineCharge = toolkit.createText(middleComposite, "", SWT.BORDER);
		craineCharge.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.roadExpansion).setBackground(color);
		roadExpansion = toolkit.createText(middleComposite, "", SWT.BORDER);
		roadExpansion.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.doorOpen).setBackground(color);
		doorOpen = toolkit.createText(middleComposite, "", SWT.BORDER);
		doorOpen.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.weighmentCharge).setBackground(color);
		weighmentCharge = toolkit.createText(middleComposite, "", SWT.BORDER);
		weighmentCharge.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.valueOfGoods).setBackground(color);
		valueOfGoods = toolkit.createText(middleComposite, "", SWT.BORDER);
		valueOfGoods.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(middleComposite, BillConstants.total).setBackground(color);
		total = toolkit.createText(middleComposite, "", SWT.BORDER);
		total.setLayoutData(new GridData(167, 17));
		
		Label seperator1 = toolkit.createSeparator(rootComposite, SWT.CENTER | SWT.COLOR_WIDGET_BACKGROUND);
		seperator1.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		middleComposite.pack();
		
		Composite rightComposite = toolkit.createComposite(rootComposite, SWT.CENTER);
		rightComposite.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, true, true));
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.verticalSpacing = 15;
		gl.horizontalSpacing = 15;
		rightComposite.setLayout(gl);
		
		toolkit.createLabel(rightComposite, BillConstants.driverCharges).setBackground(color);
		driverCharges = toolkit.createText(rightComposite, "", SWT.BORDER);
		driverCharges.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(rightComposite, BillConstants.loadingCharges).setBackground(color);
		loadingCharges = toolkit.createText(rightComposite, "", SWT.BORDER);
		loadingCharges.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(rightComposite, BillConstants.checkPost).setBackground(color);
		checkPost1 = toolkit.createText(rightComposite, "", SWT.BORDER);
		checkPost1.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(rightComposite, BillConstants.sidePoles).setBackground(color);
		sidePoles1 = toolkit.createText(rightComposite, "", SWT.BORDER);
		sidePoles1.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(rightComposite, BillConstants.otherExpenses).setBackground(color);
		otherExpenses = toolkit.createText(rightComposite, "", SWT.BORDER);
		otherExpenses.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(rightComposite, BillConstants.total).setBackground(color);
		total1 = toolkit.createText(rightComposite, "", SWT.BORDER);
		total1.setLayoutData(new GridData(167, 17));
		
		rightComposite.pack();
		
		noPrint = toolkit.createButton(rootComposite, "Create with No Print", SWT.PUSH);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.widthHint = 200;
		gd.heightHint = 40;
		gd.verticalIndent = 20;
		noPrint.setLayoutData(gd);
		
		toolkit.createLabel(rootComposite, "");
		
		reset = toolkit.createButton(rootComposite, "Reset", SWT.PUSH);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.widthHint = 200;
		gd.heightHint = 40;
		gd.verticalIndent = 20;
		reset.setLayoutData(gd);
		
		toolkit.createLabel(rootComposite, "");

		create = toolkit.createButton(rootComposite, "Create and Print", SWT.PUSH);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.widthHint = 200;
		gd.heightHint = 40;
		gd.verticalIndent = 20;
		create.setLayoutData(gd);
		
		leftComposite.setBackground(color);
		middleComposite.setBackground(color);
		rightComposite.setBackground(color);
		rootComposite.setBackground(color);

		/*Add Custom Listeners*/
		weight.addKeyListener(new CustomKeyListener(freightPerTonne));
		
		freightPerTonne.addKeyListener(new CustomKeyListener(checkPost));
		freightPerTonne.addModifyListener(new CustomModifyListener(freightPerTonne, checkPost, sidePoles, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total));
		
		checkPost.addKeyListener(new CustomKeyListener(sidePoles));
		checkPost.addModifyListener(new CustomModifyListener(freightPerTonne, checkPost, sidePoles, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total, checkPost1));
		
		sidePoles.addKeyListener(new CustomKeyListener(craineCharge));
		sidePoles.addModifyListener(new CustomModifyListener(freightPerTonne, sidePoles, checkPost, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total, sidePoles1));
		
		craineCharge.addKeyListener(new CustomKeyListener(roadExpansion));
		craineCharge.addModifyListener(new CustomModifyListener(freightPerTonne, checkPost, sidePoles, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total));
		
		roadExpansion.addKeyListener(new CustomKeyListener(doorOpen));
		roadExpansion.addModifyListener(new CustomModifyListener(freightPerTonne, checkPost, sidePoles, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total));
		
		doorOpen.addKeyListener(new CustomKeyListener(weighmentCharge));
		doorOpen.addModifyListener(new CustomModifyListener(freightPerTonne, checkPost, sidePoles, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total));
		
		weighmentCharge.addKeyListener(new CustomKeyListener(valueOfGoods));
		weighmentCharge.addModifyListener(new CustomModifyListener(freightPerTonne, checkPost, sidePoles, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total));
		
		valueOfGoods.addKeyListener(new CustomKeyListener(driverCharges));
		valueOfGoods.addModifyListener(new CustomModifyListener(freightPerTonne, checkPost, sidePoles, craineCharge,
				roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total));
		
		driverCharges.addKeyListener(new CustomKeyListener(loadingCharges));
		driverCharges.addModifyListener(new CustomModifyListener(driverCharges, loadingCharges, sidePoles1, checkPost1, otherExpenses, total1));
		
		loadingCharges.addKeyListener(new CustomKeyListener(sidePoles1));
		loadingCharges.addModifyListener(new CustomModifyListener(driverCharges, loadingCharges, sidePoles1, checkPost1, otherExpenses, total1));
		
		sidePoles1.addKeyListener(new CustomKeyListener(checkPost1));
		sidePoles1.addModifyListener(new CustomModifyListener(driverCharges, loadingCharges, sidePoles1, checkPost1, otherExpenses, total1));
		
		checkPost1.addKeyListener(new CustomKeyListener(otherExpenses));
		checkPost1.addModifyListener(new CustomModifyListener(driverCharges, loadingCharges, sidePoles1, checkPost1, otherExpenses, total1));
		
		otherExpenses.addModifyListener(new CustomModifyListener(driverCharges, loadingCharges, sidePoles1, checkPost1, otherExpenses, total1));
		
		stationCombo.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					String text = ("" + stationCombo.getText().charAt(0)).toUpperCase() + stationCombo.getText().substring(1);
					if (text.equals("")) {
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "Please select one from the list!!");
					}
					else {
						for(String city: cities) {
					       if (city.contains(text)) {
								stationCombo.setText(city);
								deliveryAtCombo.setFocus();
								return;
					       }
						}
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "No Such Town or City Exists!!");
					}
				}
				else {
					stationCombo.setListVisible(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}				
		});
		
		deliveryAtCombo.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					String text = ("" + deliveryAtCombo.getText().charAt(0)).toUpperCase() + deliveryAtCombo.getText().substring(1);
					if (text.equals("")) {
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "Please select one from the list!!");
					}
					else {
						for(String city: cities) {
					       if (city.contains(text)) {
					    	   	deliveryAtCombo.setText(city);
								consignor.setFocus();
								return;
					       }
						}
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "No Such Town or City Exists!!");
					}
				}
				else {
					deliveryAtCombo.setListVisible(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}				
		});
		
		monthCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				dayCombo.setItems(Month.getDays(monthCombo.getText()));
			}				
		});
		
		monthCombo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				dayCombo.setItems(Month.getDays(monthCombo.getText()));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
		
		create.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				createBill(UIConstants.print);
			}

			@Override
			public void widgetDefaultSelected(
					SelectionEvent e) {
			}								
		});
		
		reset.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				utils.resetUI(stationCombo, deliveryAtCombo, consignor, packagesCombo, packagesDescription, 
						privateMarks, remarks, dayCombo, monthCombo, yearCombo, truckNo, consignee, weight,
						weightCombo, freightPerTonne, checkPost, sidePoles, craineCharge,
						roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total, driverCharges,
						loadingCharges, sidePoles1, checkPost1, otherExpenses, total1);
			}

			@Override
			public void widgetDefaultSelected(
					SelectionEvent e) {
			}								
		});
		
		noPrint.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				createBill(UIConstants.no_print);
			}

			@Override
			public void widgetDefaultSelected(
					SelectionEvent e) {
			}								
		});
	}
	
	private void createBill(String operation) {
		
		if (utils.validateInsert(stationCombo, deliveryAtCombo, dayCombo, monthCombo, yearCombo, truckNo, weight)) {
			billDetails = new HashMap<String, String>();
			billDetails.put(BillConstants.station, stationCombo.getText());
			billDetails.put(BillConstants.deliveryAt, deliveryAtCombo.getText());
			billDetails.put(BillConstants.consignor, consignor.getText());
			billDetails.put(BillConstants.packages, packagesCombo.getText());
			billDetails.put(BillConstants.packagesDescription, packagesDescription.getText());
			billDetails.put(BillConstants.privateMarks, privateMarks.getText());
			billDetails.put(BillConstants.remarks, remarks.getText());
			billDetails.put(BillConstants.date, dayCombo.getText() + " " + monthCombo.getText() + " " + yearCombo.getText());
			billDetails.put(BillConstants.billNumber, billNo.getText());
			billDetails.put(BillConstants.truckNumber, truckNo.getText());
			billDetails.put(BillConstants.consignee, consignee.getText());
			billDetails.put(BillConstants.actualWeight, weight.getText() + " " + weightCombo.getText());
			billDetails.put(BillConstants.freightPerTonne,freightPerTonne.getText());
			billDetails.put(BillConstants.checkPost, checkPost.getText());
			billDetails.put(BillConstants.sidePoles, sidePoles.getText());
			billDetails.put(BillConstants.craineCharge, craineCharge.getText());
			billDetails.put(BillConstants.roadExpansion, roadExpansion.getText());
			billDetails.put(BillConstants.doorOpen, doorOpen.getText());
			billDetails.put(BillConstants.weighmentCharge, weighmentCharge.getText());
			billDetails.put(BillConstants.valueOfGoods, valueOfGoods.getText());
			billDetails.put(BillConstants.total, total.getText());
			billDetails.put(BillConstants.driverCharges, driverCharges.getText());
			billDetails.put(BillConstants.loadingCharges, loadingCharges.getText());
			billDetails.put(BillConstants.checkPost1, checkPost1.getText());
			billDetails.put(BillConstants.sidePoles1, sidePoles1.getText());
			billDetails.put(BillConstants.otherExpenses, otherExpenses.getText());
			
			if (createNewBillJDBC.addBill(billDetails)) {
				MessageDialog.openInformation(
						 Display.getDefault().getActiveShell(), UIConstants.information, "Bill added succesfully!!");
				utils.resetUI(stationCombo, deliveryAtCombo, consignor, packagesCombo, packagesDescription, 
						privateMarks, remarks, dayCombo, monthCombo, yearCombo, truckNo, consignee, weight,
						weightCombo, freightPerTonne, checkPost, sidePoles, craineCharge,
						roadExpansion, doorOpen, weighmentCharge, valueOfGoods, total, driverCharges,
						loadingCharges, sidePoles1, checkPost1, otherExpenses, total1);
				billNo.setText(jdbcUtils.getNextBillNo());
				if (operation.equals(UIConstants.print)) {
					printBill = new PrintBill(Display.getDefault().getActiveShell());
					printBill.printToPage(billDetails);
				}
			}
			else {
				MessageDialog.openInformation(
						 Display.getDefault().getActiveShell(), UIConstants.information, "Bill information cannot be added!!");
			}
		}
	}
}
