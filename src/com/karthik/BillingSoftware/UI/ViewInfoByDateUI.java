package com.karthik.BillingSoftware.UI;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Constants.UIConstants;
import com.karthik.BillingSoftware.JDBC.ViewInfoByDateJDBC;
import com.karthik.BillingSoftware.Print.PrintStatement;
import com.karthik.BillingSoftware.Utils.Month;
import com.karthik.BillingSoftware.Utils.Utils;

public class ViewInfoByDateUI extends BillUIHeader {

	private Composite rootComposite;
	
	private Combo monthCombo;
	private Combo dayCombo;
	private Combo yearCombo;
	
	private Button getInfo;
	private Button daily;
	private Button monthly;
	private Button print;
	
	private Table table;
	
	FormToolkit toolkit;
	ScrolledForm sform;
	Display display;
	Color color;
	
	boolean byDate = true;
	private String date;
	private ArrayList<HashMap<String, String>> truckDetailsArray;
	
	ViewInfoByDateJDBC viewInfoByDateJDBC;
	PrintStatement printStatement;
	Utils utils;
	
	public ViewInfoByDateUI(FormToolkit toolkit, ScrolledForm sform) {
		this.toolkit = toolkit;
		this.sform = sform;
	}
	
	public void createUI () {
		
		viewInfoByDateJDBC = new ViewInfoByDateJDBC();
		utils = Utils.getInstance();
		display = Display.getCurrent();
		color = new Color(display, 234, 234, 234);
		
		rootComposite = toolkit.createComposite(sform.getBody(), SWT.CENTER);
		rootComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		gl.horizontalSpacing = 30;
		rootComposite.setLayout(gl);
		rootComposite.setBackground(color);
		
		createHeader(rootComposite, toolkit, 1);
		
		Composite topComposite = toolkit.createComposite(rootComposite, SWT.CENTER);
		topComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.verticalSpacing = 15;
		gl.horizontalSpacing = 15;
		topComposite.setLayout(gl);
		topComposite.setBackground(color);
		
		toolkit.createLabel(topComposite, "");	
		daily = new Button(topComposite, SWT.RADIO);
		daily.setText("View Statement for the selected Date");
		daily.setSelection(true);
		daily.setBackground(color);
	
		toolkit.createLabel(topComposite, "");
		monthly = new Button(topComposite, SWT.RADIO);
		monthly.setText("View Monthly Statement");
		monthly.setBackground(color);
		
		toolkit.createLabel(topComposite, "Date").setBackground(color);
		
		Composite dateComposite = toolkit.createComposite(topComposite, SWT.BEGINNING);
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
		dayCombo = new Combo(dateComposite, SWT.BORDER | SWT.READ_ONLY);
		dayCombo.setLayoutData(new GridData(10, 14));
		yearCombo = new Combo(dateComposite, SWT.BORDER | SWT.READ_ONLY);
		yearCombo.setLayoutData(new GridData(22, 14));
		yearCombo.setItems(UIConstants.years);
		
		utils.setTime(dayCombo, monthCombo, yearCombo);
		
		toolkit.createLabel(topComposite, "");
		getInfo = toolkit.createButton(topComposite, "Get Info", SWT.PUSH);
		getInfo.setLayoutData(new GridData(180, 25));
		
		Composite detailsComposite = toolkit.createComposite(rootComposite, SWT.CENTER);
		detailsComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		gl = new GridLayout();
		gl.numColumns = 1;
		gl.verticalSpacing = 15;
		gl.horizontalSpacing = 15;
		gl.marginTop = 20;
		detailsComposite.setLayout(gl);
		detailsComposite.setBackground(color);
		
		table = toolkit.createTable(detailsComposite, SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 840;
		gd.heightHint = 400;
		table.setLayoutData(gd);
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		String[] titles = { BillConstants.billNumber, BillConstants.truckNumber, BillConstants.date, BillConstants.actualWeight, BillConstants.total, BillConstants.otherCharges };
		for (int i = 0; i < titles.length; i++) {
		      TableColumn column = new TableColumn(table, SWT.CENTER);
		      column.setText(titles[i]);
		      column.setWidth(140);
		}
		
		print = toolkit.createButton(detailsComposite, "Print", SWT.PUSH);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.widthHint = 200;
		gd.heightHint = 40;
		gd.verticalIndent = 20;
		print.setLayoutData(gd);
		
		monthCombo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				dayCombo.setItems(Month.getDays(monthCombo.getText()));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
		
		daily.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				dayCombo.setEnabled(true);
				byDate = true;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
		
		monthly.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				dayCombo.setEnabled(false);
				byDate = false;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
		
		getInfo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (monthCombo.getText().equals("")) {
					MessageDialog.openInformation(
							 Display.getDefault().getActiveShell(), UIConstants.information, "Month cannot be empty!!");
					return;
				}
				if (yearCombo.getText().equals("")) {
					MessageDialog.openInformation(
							 Display.getDefault().getActiveShell(), UIConstants.information, "Year cannot be empty!!");
					return;
				}
				if (byDate) {
					if (dayCombo.getText().equals("")) {
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "Day cannot be empty!!");
						return;
					}
					date = dayCombo.getText() + " " + monthCombo.getText() + " " + yearCombo.getText();
					truckDetailsArray = viewInfoByDateJDBC.getTruckDetails(byDate, date, "");
				}
				else {
					date = monthCombo.getText() + " " + yearCombo.getText();
					truckDetailsArray = viewInfoByDateJDBC.getTruckDetails(byDate, date.split(" ")[0], date.split(" ")[1]);
				}
				printStatement = new PrintStatement(Display.getDefault().getActiveShell(), date);
				setTableItems(truckDetailsArray);
				if (truckDetailsArray.size() == 0)
					MessageDialog.openInformation(
							 Display.getDefault().getActiveShell(), UIConstants.information, "No Trucks found for the selected time!!");
			}

			@Override
			public void widgetDefaultSelected(
					SelectionEvent e) {
			}								
		});
		
		table.addListener(SWT.MeasureItem, new Listener() {
			   @Override
			public void handleEvent(Event event) {
			      event.height = 30;
			   }
		});
		
		table.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent e) {
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				sform.getBody().setFocus();
			}
			
			@Override
			public void mouseEnter(MouseEvent e) {
				table.setFocus();
			}
		});
		
		table.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				table.setFocus();
			}
		});
		
		print.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (table.getItemCount() == 0)
					MessageDialog.openInformation(
							 Display.getDefault().getActiveShell(), UIConstants.information, "No details to print!!");
				else
					printStatement.printToPage(table);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
	}
	
	private void setTableItems(ArrayList<HashMap<String, String>> truckDetailsArray) {
		table.removeAll();
		for (int i = 0; i < truckDetailsArray.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] {truckDetailsArray.get(i).get(BillConstants.billNumber),
					truckDetailsArray.get(i).get(BillConstants.truckNumber),
					truckDetailsArray.get(i).get(BillConstants.date),
					truckDetailsArray.get(i).get(BillConstants.actualWeight),
					truckDetailsArray.get(i).get(BillConstants.total),
					truckDetailsArray.get(i).get(BillConstants.otherCharges)});
		}
	}

}
