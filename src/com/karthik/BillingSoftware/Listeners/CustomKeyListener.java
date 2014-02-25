package com.karthik.BillingSoftware.Listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Text;

public class CustomKeyListener implements KeyListener {
	
	private Text targetTextControl;
	
	public CustomKeyListener(Text targetTextControl) {
		this.targetTextControl = targetTextControl;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Character.isDigit(e.character) || e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_UP
				 || e.keyCode == SWT.ARROW_LEFT || e.keyCode == SWT.ARROW_RIGHT || e.keyCode == SWT.DEL || e.keyCode == SWT.BS || ("" + e.character).equals("."))
			e.doit = true;
		else
			e.doit = false;
		if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
			targetTextControl.setFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
