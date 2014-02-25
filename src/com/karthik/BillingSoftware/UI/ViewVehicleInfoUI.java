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
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Constants.UIConstants;
import com.karthik.BillingSoftware.JDBC.ViewVehicleInfoJDBC;
import com.karthik.BillingSoftware.Print.PrintStatement;

public class ViewVehicleInfoUI extends BillUIHeader {

	private Composite rootComposite;
	private Text truckNo;	
	private Button getInfo;
	private Button print;	
	private Table table;
	
	private ArrayList<HashMap<String, String>> truckDetailsArray;
	
	private ViewVehicleInfoJDBC viewVehicleInfoJDBC;
	private PrintStatement printStatement;
	
	FormToolkit toolkit;
	ScrolledForm sform;
	Display display;
	Color color;
	
	public ViewVehicleInfoUI(FormToolkit toolkit, ScrolledForm sform) {
		this.toolkit = toolkit;
		this.sform = sform;
	}
	
	public void createUI () {
		
		viewVehicleInfoJDBC = new ViewVehicleInfoJDBC();
		display = Display.getCurrent();
		color = new Color(display, 234, 234, 234);
		
		rootComposite = toolkit.createComposite(sform.getBody(), SWT.CENTER);
		rootComposite.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, true, true));
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
		gl.verticalSpacing = 30;
		gl.horizontalSpacing = 15;
		topComposite.setLayout(gl);
		topComposite.setBackground(color);
		
		toolkit.createLabel(topComposite, BillConstants.truckNumber).setBackground(color);
		truckNo = toolkit.createText(topComposite, "", SWT.BORDER);
		truckNo.setLayoutData(new GridData(167, 17));
		
		toolkit.createLabel(topComposite, "");
		getInfo = toolkit.createButton(topComposite, "Get Vehicle Info", SWT.PUSH);
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
		String[] titles = { BillConstants.billNumber, BillConstants.date, BillConstants.station, BillConstants.deliveryAt, BillConstants.actualWeight, BillConstants.total};
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
		
		truckNo.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					setHandler();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}								
		});
		
		truckNo.addVerifyListener(new VerifyListener() {

			@Override
			public void verifyText(VerifyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					setHandler();
				}
				if (Character.isLowerCase(e.character)) {
					e.text = e.text.toUpperCase();
				}
			}								
		});
		
		getInfo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setHandler();
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
	
	private void setHandler() {
		String place = truckNo.getText();
		truckDetailsArray = viewVehicleInfoJDBC.getTruckDetails(truckNo.getText());
		printStatement = new PrintStatement(Display.getDefault().getActiveShell(), place);
		setTableItems(truckDetailsArray);
		if (truckDetailsArray.size() == 0)
			MessageDialog.openInformation(
					 Display.getDefault().getActiveShell(), UIConstants.information, "No Trucks found for the selected places!!");
	}
	
	private void setTableItems(ArrayList<HashMap<String, String>> truckDetailsArray) {
		table.removeAll();
		for (int i = 0; i < truckDetailsArray.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] {truckDetailsArray.get(i).get(BillConstants.billNumber),
					truckDetailsArray.get(i).get(BillConstants.date),
					truckDetailsArray.get(i).get(BillConstants.station),
					truckDetailsArray.get(i).get(BillConstants.deliveryAt),
					truckDetailsArray.get(i).get(BillConstants.actualWeight),
					truckDetailsArray.get(i).get(BillConstants.total)});
		}
	}
}
