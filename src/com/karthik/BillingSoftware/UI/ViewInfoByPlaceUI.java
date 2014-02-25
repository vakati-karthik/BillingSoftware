package com.karthik.BillingSoftware.UI;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
import com.karthik.BillingSoftware.Constants.PrintConstants;
import com.karthik.BillingSoftware.Constants.UIConstants;
import com.karthik.BillingSoftware.JDBC.ViewInfoByPlaceJDBC;
import com.karthik.BillingSoftware.Print.PrintStatement;
import com.karthik.BillingSoftware.Utils.Utils;

public class ViewInfoByPlaceUI extends BillUIHeader {

	private Composite rootComposite;
	
	private Combo stationCombo;
	private Combo deliveryAtCombo;
	
	private Button getInfo;
	private Button source;
	private Button destination;
	private Button source_destination;
	private Button print;
	
	private Table table;
	
	private Utils utils;
	
	FormToolkit toolkit;
	ScrolledForm sform;
	Display display;
	Color color;
	
	private ArrayList<HashMap<String, String>> truckDetailsArray;
	private ArrayList<String> cities;
	private boolean isSourceEnabled = true;
	private boolean isDestEnabled = true;
	private String place;
	
	ViewInfoByPlaceJDBC viewInfoByPlaceJDBC;
	PrintStatement printStatement;
	
	public ViewInfoByPlaceUI(FormToolkit toolkit, ScrolledForm sform) {
		this.toolkit = toolkit;
		this.sform = sform;
	}
	
	public void createUI () {
		
		utils = Utils.getInstance();
		cities = utils.getCitiesFromExcel();
		viewInfoByPlaceJDBC = new ViewInfoByPlaceJDBC();
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
		source = new Button(topComposite, SWT.RADIO);
		source.setText("View all Trucks for only selected Source");
		source.setBackground(color);
	
		toolkit.createLabel(topComposite, "");
		destination = new Button(topComposite, SWT.RADIO);
		destination.setText("View all Trucks for only selected Destination");
		destination.setBackground(color);
		
		toolkit.createLabel(topComposite, "");	
		source_destination = new Button(topComposite, SWT.RADIO);
		source_destination.setText("View all Trucks for both Source and Destination");
		source_destination.setSelection(true);
		source_destination.setBackground(color);
		
		toolkit.createLabel(topComposite, "Station").setBackground(color);
		stationCombo = new Combo(topComposite, SWT.BORDER);
		stationCombo.setLayoutData(new GridData(150, 14));
		stationCombo.setItems(cities.toArray(new String[0]));
		stationCombo.setText("Chennai");
		
		toolkit.createLabel(topComposite, "Delivery At").setBackground(color);
		deliveryAtCombo = new Combo(topComposite, SWT.BORDER);
		deliveryAtCombo.setLayoutData(new GridData(150, 14));
		deliveryAtCombo.setItems(cities.toArray(new String[0]));
		
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
		table.setLinesVisible(true);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 980;
		gd.heightHint = 400;
		table.setLayoutData(gd);
		
		table.setHeaderVisible(true);
		String[] titles = { BillConstants.billNumber, BillConstants.truckNumber, BillConstants.date, BillConstants.station, BillConstants.deliveryAt, BillConstants.actualWeight, BillConstants.total};
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
		
		source.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				stationCombo.setEnabled(true);
				isSourceEnabled = true;
				deliveryAtCombo.setEnabled(false);
				isDestEnabled = false;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
		
		destination.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				stationCombo.setEnabled(false);
				isSourceEnabled = false;
				deliveryAtCombo.setEnabled(true);
				isDestEnabled = true;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
		
		source_destination.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				stationCombo.setEnabled(true);
				isSourceEnabled = true;
				deliveryAtCombo.setEnabled(true);
				isDestEnabled = true;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}				
		});
		
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
								getInfo.setFocus();
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
		
		getInfo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isSourceEnabled && !isDestEnabled) {
					deliveryAtCombo.deselectAll();
					if (stationCombo.getText().equals("")) {
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "Station cannot be empty!!");
						return;
					}
					place = PrintConstants.truck + PrintConstants.from + stationCombo.getText() + PrintConstants.to + PrintConstants.all_stations;
					truckDetailsArray = viewInfoByPlaceJDBC.getPlaceDetails(true, false, stationCombo.getText(), "");
				}
				else if (!isSourceEnabled && isDestEnabled) {
					stationCombo.clearSelection();
					if (deliveryAtCombo.getText().equals("")) {
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "Delivery At cannot be empty!!");
						return;
					}
					place = PrintConstants.truck + PrintConstants.from + PrintConstants.all_stations + PrintConstants.to + deliveryAtCombo.getText();
					truckDetailsArray = viewInfoByPlaceJDBC.getPlaceDetails(false, true, "", deliveryAtCombo.getText());
				}
				else if (isSourceEnabled && isDestEnabled) {
					if (stationCombo.getText().equals("")) {
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "Station cannot be empty!!");
						return;
					}
					if (deliveryAtCombo.getText().equals("")) {
						MessageDialog.openInformation(
								 Display.getDefault().getActiveShell(), UIConstants.information, "Delivery At cannot be empty!!");
						return;
					}
					place = PrintConstants.truck + PrintConstants.from + stationCombo.getText() + PrintConstants.to + deliveryAtCombo.getText();
					truckDetailsArray = viewInfoByPlaceJDBC.getPlaceDetails(true, true, stationCombo.getText(), deliveryAtCombo.getText());
				}
				printStatement = new PrintStatement(Display.getDefault().getActiveShell(), place);
				setTableItems(truckDetailsArray);
				if (truckDetailsArray.size() == 0)
					MessageDialog.openInformation(
							 Display.getDefault().getActiveShell(), UIConstants.information, "No Trucks found for the selected places!!");
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
					truckDetailsArray.get(i).get(BillConstants.station),
					truckDetailsArray.get(i).get(BillConstants.deliveryAt),
					truckDetailsArray.get(i).get(BillConstants.actualWeight),
					truckDetailsArray.get(i).get(BillConstants.total)});
		}
	}

}
