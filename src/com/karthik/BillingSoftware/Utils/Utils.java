package com.karthik.BillingSoftware.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.paperclips.Margins;
import net.sf.paperclips.PageNumber;
import net.sf.paperclips.PageNumberFormat;
import net.sf.paperclips.PageNumberPageDecoration;
import net.sf.paperclips.PagePrint;
import net.sf.paperclips.PaperClips;
import net.sf.paperclips.Print;
import net.sf.paperclips.PrintJob;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.karthik.BillingSoftware.Load.Activator;

public class Utils {
	
	public static Utils getInstance() {
		return new Utils();
	}
	
	public ArrayList<String> getCitiesFromExcel() {
		
		ArrayList<String> cities = new ArrayList<String>();
		try {
			Workbook workbook = Workbook.getWorkbook(this.getClass().getResourceAsStream("/resource/cities.xls"));
			int totalsheet = workbook.getNumberOfSheets();			
			for(int i = 0; i < totalsheet; i++) {
				Sheet sheet = workbook.getSheet(i);
				int rows = sheet.getRows();
			
				for (int j = 1; j < rows; j++) {					
					Cell cell1 = sheet.getCell(0,j);					 
					cities.add(cell1.getContents());				
				}             
			}
			return cities;
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (BiffException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void resetUI(Control... controls) {
		for (int i = 0; i < controls.length; i++) {
			if (controls[i] instanceof Text) {
				Text text= (Text)controls[i];
				text.setText("");
			}
			else if (controls[i] instanceof Combo) {
				Combo combo = (Combo)controls[i];
				combo.deselectAll();
			}
		}
	}
	
	public boolean validateInsert(Control... controls) {
		String text = "";
		for (int i = 0; i < controls.length; i++) {
			if (controls[i] instanceof Text)
				text = ((Text)controls[i]).getText();
			else if (controls[i] instanceof Combo)
				text = ((Combo)controls[i]).getText();
			
			if (text.equals("")) {
				MessageDialog.openInformation(
						 Display.getDefault().getActiveShell(), "Information", controls[i].getData() + " cannot be empty!!");
				return false;
			}
		}
		return true;
	}
	
	public FontData getSystemFontData() {
        return Display.getDefault().getSystemFont().getFontData()[0];
    }
	
	public ImageDescriptor getImageDescriptor(String operationName) {
		return AbstractUIPlugin
			      .imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/" + getIconName(operationName));
	}
	
	public Image getImage(ImageDescriptor imageDescriptor) {
		return imageDescriptor.createImage();
	}
	
	public String getIconName(String operationName) {
		if (operationName.endsWith(".jpg") || operationName.endsWith(".png"))
			return operationName;
		else
			return operationName.toLowerCase() + ".png";
	}
	
	public String[] splitAmount(String amount) {
		if (amount.contains(".")) {
			String[] str = amount.split("\\.");
			return str;
		}
		else
			return new String[]{amount, "" + 00};
	}
	
	public void setTime(Combo dayCombo, Combo monthCombo, Combo yearCombo) {
		Calendar cal = Calendar.getInstance();
		monthCombo.setText(Month.getMonthName(cal.get(Calendar.MONTH)));
		dayCombo.setItems(Month.getDays(monthCombo.getText()));
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day < 10)
			dayCombo.setText("0" + cal.get(Calendar.DAY_OF_MONTH));
		else
			dayCombo.setText("" + cal.get(Calendar.DAY_OF_MONTH));
		yearCombo.setText("" + cal.get(Calendar.YEAR));
	}
	
	public void goPrinting(Print grid, boolean isStatement) {
		PagePrint page = new PagePrint(grid);
		if (isStatement) {
			FontData fontData = getSystemFontData();
			PageNumberPageDecoration footer = new PageNumberPageDecoration(SWT.CENTER);
			footer.setFontData(fontData);
			footer.setFormat(new PageNumberFormat(){
				@Override
				public String format(PageNumber pageNumber) {
					return "" + (pageNumber.getPageNumber() + 1);
				}});
			page.setFooter(footer);
		}
		PrintJob job = new PrintJob("Printing...", page);
		job.setOrientation(PaperClips.ORIENTATION_LANDSCAPE);
		if (!isStatement) {
			Margins margins = job.getMargins();
			margins.top = margins.top - 30;
			margins.bottom = margins.bottom - 20;
			job.setMargins(margins);
		}
		
		PrintDialog dialog = new PrintDialog(Display.getDefault().getActiveShell(), SWT.NONE);
        PrinterData printerData = dialog.open();
        if (printerData != null) 
            PaperClips.print(job, printerData);
	}
}
