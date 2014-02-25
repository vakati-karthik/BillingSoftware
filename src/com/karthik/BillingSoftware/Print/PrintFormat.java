package com.karthik.BillingSoftware.Print;

import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.ImagePrint;
import net.sf.paperclips.TextPrint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import com.karthik.BillingSoftware.Constants.PrintConstants;
import com.karthik.BillingSoftware.Utils.Utils;

public abstract class PrintFormat {
	
	StringBuffer buf = new StringBuffer();
	FontData fontData, bodyFont, titleFont, addressFont, cellFont, jurisdictionFont, lineFont, blanklineFont, statementFont;
	
	GridPrint grid;
	Utils utils;
	
	protected GridPrint setGridPrint() {
		
		int length = 93; //Total number of columns that can fit on the page if each column is of length 10px (See colWidthPix).
		for (int i = 0; i < length; i++) {
			int colWidthPix = 10;
            float pts = convertToPoints(colWidthPix);
            buf.append("L:");
            buf.append((int) pts);
            buf.append(":G");
            if (i != length - 1) {
                buf.append(", ");
            }
        }		
		DefaultGridLook look = new DefaultGridLook(0, 0);
		grid = new GridPrint(buf.toString(), look);
		utils = Utils.getInstance();
		setFonts();
		printHeader();
		return grid;
	}
	
	private void setFonts() {
		bodyFont = new FontData("Courier", 12, SWT.None);
		cellFont = new FontData("Courier", 10, SWT.None);
		jurisdictionFont = new FontData("Courier", 7, SWT.None);
		titleFont = new FontData("Georgia", 32, SWT.BOLD);
		addressFont = new FontData("Courier", 14, SWT.None);
		lineFont = new FontData("Courier", 20, SWT.None);
		blanklineFont = new FontData("Courier", 0, SWT.None);
		statementFont = new FontData("Courier", 16, SWT.None);
	}
	
	protected void printHeader() {
		
		grid.add(SWT.RIGHT, SWT.CENTER, new TextPrint(PrintConstants.grams, cellFont), 30);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.jurisdiction, jurisdictionFont), 33);
		grid.add(SWT.RIGHT, SWT.CENTER, new TextPrint(PrintConstants.cell, cellFont), 30);
		grid.add(SWT.LEFT, SWT.CENTER, new ImagePrint(utils.getImage(utils.getImageDescriptor("venky_print.jpg")).getImageData()), 7);
		grid.add(SWT.LEFT, SWT.CENTER, new TextPrint(PrintConstants.title, titleFont), GridPrint.REMAINDER);
		grid.add(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.address, addressFont), GridPrint.REMAINDER);		
	}
	
	protected abstract void printBody();

	protected int convertToPoints(int pixels) {
        return 72 * pixels / Display.getDefault().getDPI().x;
    }
}
