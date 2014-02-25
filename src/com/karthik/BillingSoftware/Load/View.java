package com.karthik.BillingSoftware.Load;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.karthik.BillingSoftware.Operations.Operation;
import com.karthik.BillingSoftware.Operations.OperationEditor;
import com.karthik.BillingSoftware.Operations.OperationEditorInput;
import com.karthik.BillingSoftware.Utils.Utils;

public class View extends ViewPart {
	public static final String ID = "com.karthik.BillingSoftware.OperationsView";

	private TableViewer viewer;
	
	private boolean editorOpened = false;
	
	private Utils utils;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		
		@Override
		public void inputChanged(final Viewer v, Object oldInput, Object newInput) {
			
			v.addSelectionChangedListener(new ISelectionChangedListener(){
				
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage page = window.getActivePage();
					View view = (View) page.findView(View.ID);
					// Get the selection
					ISelection selection = view.getSite().getSelectionProvider()
							.getSelection();
					if (selection != null && selection instanceof IStructuredSelection) {
						Object obj = ((IStructuredSelection) selection).getFirstElement();
						// If we have a selection lets open the editor
						if (obj != null) {
							Operation operation = (Operation) obj;
							OperationEditorInput input = new OperationEditorInput(operation);
							try {
								IEditorPart activeEditor = page.getActiveEditor();
								if (activeEditor == null)
									page.openEditor(input, OperationEditor.ID);
								else {
									for (int i = 0; i < page.getEditorReferences().length; i++) {
										editorOpened = false;
										IEditorReference editorRef = page.getEditorReferences()[i];
										if (editorRef.getEditorInput().getName().equals(input.getName())) {
											editorOpened = true;
											editorRef.getPage().bringToTop(editorRef.getEditor(true));
											return;
										}
									}
									if (editorOpened == false)
										page.openEditor(input, OperationEditor.ID);
								}						
							}
							catch (PartInitException e) {
								throw new RuntimeException(e);
							}
						}
					}
				}		
			});			
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object parent) {
			return Operation.values();
		}
	}

	class ViewLabelProvider extends LabelProvider{
		
		@Override
		public String getText(Object obj) {
			Operation operation = (Operation)obj;
			return Operation.getOperationName(operation);
		}

		@Override
		public Image getImage(Object obj) {
			Operation operation = (Operation)obj;
			return Operation.getOperationImage(operation);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);
		Color color = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		parent.setBackground(color);		
		utils = Utils.getInstance();		
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		// Provide the input to the ContentProvider
		getSite().setSelectionProvider(viewer);
		viewer.setInput("");
		new Label(parent, SWT.CENTER).setImage(utils.getImage(utils.getImageDescriptor("truck1.jpg")));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}