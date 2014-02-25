package com.karthik.BillingSoftware.Print;

import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.LineBorder;
import net.sf.paperclips.TextPrint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.karthik.BillingSoftware.Constants.PrintConstants;
import com.karthik.BillingSoftware.Utils.Utils;

public class PrintStatement extends PrintFormat {
	
	FontData fontData;
	Font font;
	GC concatGC;
	Utils utils;
	Table table;
	GridPrint masterGrid;
	DefaultGridLook masterGridLook;
	
	StringBuffer buf1 = new StringBuffer();
	StringBuffer stmtPeriod = new StringBuffer();
	
	public PrintStatement(Shell shell, String stmtPeriod) {
		utils = Utils.getInstance();
		fontData= utils.getSystemFontData();
		font = new Font(Display.getDefault(), fontData);
		concatGC = new GC(shell);
		this.stmtPeriod.append(stmtPeriod);
		masterGridLook = new DefaultGridLook(0, 5);
		masterGrid = new GridPrint("C:P:N", masterGridLook);
	}
	
	public void printToPage(Table table) {
		grid = setGridPrint();
		this.table = table;
		printBody();
		utils.goPrinting(masterGrid, true);
	}

	@Override
	public void printBody() {
		
		int numColumns = table.getColumns().length;
		
		for (int i = 0; i < numColumns; i++) {
			int colWidthPix = table.getColumns()[i].getWidth();
			colWidthPix = colWidthPix - 30;
            float pts = convertToPoints(colWidthPix);
            buf1.append("L:");
            buf1.append((int) pts);
            buf1.append(":N");

            if (i != numColumns - 1) {
                buf1.append(", ");
            }
        }
		
		DefaultGridLook look = new DefaultGridLook(0, 0);
		look.setCellBorder(new LineBorder());
		GridPrint bodyGrid = new GridPrint(buf1.toString(), look);
		
		bodyGrid.addHeader(SWT.CENTER, SWT.CENTER, new TextPrint(PrintConstants.subject + " " + stmtPeriod, statementFont), GridPrint.REMAINDER);
		
		for (int i = 0; i < numColumns; i++)
			bodyGrid.addHeader(SWT.CENTER, SWT.CENTER, new TextPrint(table.getColumns()[i].getText()));	
		
		for (int row = 0; row < table.getItemCount(); row++) {
			for (int column = 0; column < numColumns; column++) {
				String value = table.getItems()[row].getText(column);
				bodyGrid.add(SWT.CENTER, SWT.CENTER, new TextPrint(value, fontData));
			}
		}
		masterGrid.add(grid);
		masterGrid.add(bodyGrid);
	}

}
