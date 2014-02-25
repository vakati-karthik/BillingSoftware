package com.karthik.BillingSoftware.Listeners;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

public class CustomModifyListener implements ModifyListener {
	
	private Text text1;
	private Text text2;
	private Text text3;
	private Text text4;
	private Text text5;
	private Text text6;
	private Text text7;
	private Text text8;
	private Text text9;
	private Text total;

	public CustomModifyListener(Text... texts) {
		if (texts.length == 9) {
			/*Code for total*/
			this.text1 = texts[0];
			this.text2 = texts[1];
			this.text3 = texts[2];
			this.text4 = texts[3];
			this.text5 = texts[4];
			this.text6 = texts[5];
			this.text7 = texts[6];
			this.text8 = texts[7];
			this.total = texts[8];
		}
		else if (texts.length == 6) {
			/*Code for total1*/
			this.text1 = texts[0];
			this.text2 = texts[1];
			this.text3 = texts[2];
			this.text4 = texts[3];
			this.text5 = texts[4];
			this.total = texts[5];
		}
		else {
			/*Code for total and to handle only the text fields SidePoles and CheckPost*/
			this.text1 = texts[0];
			this.text2 = texts[1];
			this.text3 = texts[2];
			this.text4 = texts[3];
			this.text5 = texts[4];
			this.text6 = texts[5];
			this.text7 = texts[6];
			this.text8 = texts[7];
			this.total = texts[8];
			this.text9 = texts[9];
		}
	}
	
	@Override
	public void modifyText(ModifyEvent e) {
		float v1 = text1.getText().equals("") ? 0 : Float.parseFloat(text1.getText());
		float v2 = text2.getText().equals("") ? 0 : Float.parseFloat(text2.getText());
		float v3 = text3.getText().equals("") ? 0 : Float.parseFloat(text3.getText());
		float v4 = text4.getText().equals("") ? 0 : Float.parseFloat(text4.getText());
		float v5 = text5.getText().equals("") ? 0 : Float.parseFloat(text5.getText());
		float v6 = 0, v7 = 0, v8 = 0;
		if (text6 != null)
			v6 = text6.getText().equals("") ? 0 : Float.parseFloat(text6.getText());
		if (text7 != null)
			v7 = text7.getText().equals("") ? 0 : Float.parseFloat(text7.getText());
		if (text8 != null)
			v8 = text8.getText().equals("") ? 0 : Float.parseFloat(text8.getText());
		if (text9 != null)
			text9.setText(text2.getText());
		total.setText("" + (v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8));
	}

}
