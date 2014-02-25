package com.karthik.BillingSoftware.Print;

import java.util.HashMap;

import net.sf.paperclips.GridPrint;
import net.sf.paperclips.LinePrint;
import net.sf.paperclips.TextPrint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Constants.PrintConstants;
import com.karthik.BillingSoftware.Utils.Utils;

public class PrintBill extends PrintFormat {	
	
	Font font;
	Utils utils;
	GridPrint grid;
	StringBuffer buf = new StringBuffer();
	HashMap<String, String> billDetails;
	
	public PrintBill(Shell shell) {
		utils = Utils.getInstance();
		fontData= utils.getSystemFontData();
		font = new Font(Display.getDefault(), fontData);
	}
	
	public void printToPage(HashMap<String, String> billDetails) {		
		grid = setGridPrint();
		this.billDetails = billDetails;
		printBody();
		utils.goPrinting(grid, false);
	}
    
    @Override
	public void printBody() {
    	
    	grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
    	
    	grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(BillConstants.station, bodyFont), 9);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.station), bodyFont), 27);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(PrintConstants.risk, bodyFont), 17);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont));
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(BillConstants.date, bodyFont), 4);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.date), bodyFont), 17);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont));
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.billNo, bodyFont), 5);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.billNumber), bodyFont), 9);//edit this
		
		grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
		
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(BillConstants.deliveryAt, bodyFont), 12);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.deliveryAt), bodyFont), 24);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(BillConstants.truckNumber, bodyFont), 12);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.truckNumber), bodyFont), 42);//edit this
		
		grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
		
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(BillConstants.consignor, bodyFont), 12);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.consignor), bodyFont), 24);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(BillConstants.consignee, bodyFont), 12);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.consignee), bodyFont), 42);//edit this
		
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint("", bodyFont), 36);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint("", bodyFont), 54);
		
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(PrintConstants.cst_no_tngst, bodyFont), 36);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(PrintConstants.cst_no_apgst, bodyFont), 54);
		
		grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.packages, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.packagesDescription, bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.actualWeight, bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.weight_charged_for, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.paid, bodyFont), 15);
		
		grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.packages), bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.packagesDescription), bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.freightPerTonne, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.freightPerTonne))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.freightPerTonne))[1], bodyFont), 3);//edit this
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.checkPost, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.checkPost))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.checkPost))[1], bodyFont), 3);//edit this
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.sidePoles, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.sidePoles))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.sidePoles))[1], bodyFont), 3);//edit this
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.craineCharge, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.craineCharge))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.craineCharge))[1], bodyFont), 3);//edit this
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.roadExpansion, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.roadExpansion))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.roadExpansion))[1], bodyFont), 3);//edit this
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.doorOpen, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.doorOpen))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.doorOpen))[1], bodyFont), 3);//edit this
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.weighmentCharge, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.weighmentCharge))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.weighmentCharge))[1], bodyFont), 3);//edit this
		
		grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.privateMarks, cellFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.privateMarks), bodyFont), 24);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.valueOfGoods, bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.valueOfGoods))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.valueOfGoods))[1], bodyFont), 3);//edit this
		
		grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
		
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.remarks, cellFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(billDetails.get(BillConstants.remarks), bodyFont), 24);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint("", bodyFont), 12);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(BillConstants.total, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.topay, bodyFont), 9);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.total))[0], bodyFont), 9);//edit this
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.line, lineFont), 3);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(utils.splitAmount(billDetails.get(BillConstants.total))[1], bodyFont), 3);//edit this
		
		grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
		
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(PrintConstants.signature, cellFont), 39);
		grid.add(SWT.RIGHT, SWT.CENTER, new TextPrint(PrintConstants.sign, cellFont), 54);
    }

}
