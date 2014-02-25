package com.karthik.BillingSoftware.Operations;

import org.eclipse.swt.graphics.Image;

import com.karthik.BillingSoftware.Utils.Utils;

/**
 * This interface represents all the operations that can be performed on the database.
 */
public enum Operation
{
	CREATE_NEW_BILL,
	VIEW_BILL_INFO,
	VIEW_VEHICLE_INFO,
	VIEW_INFO_BY_DATE,
	VIEW_INFO_BY_PLACE;

  public static String getOperationName(Operation operation)
  {
    String str = operation.toString();
    str = str.replace("_", "  ");    
    return str;
  }
  
  public static Image getOperationImage (Operation operation)
  {
	  if (operation.equals(Operation.CREATE_NEW_BILL))
		  return Utils.getInstance().getImage(Utils.getInstance().getImageDescriptor(Operation.CREATE_NEW_BILL.toString()));
	  else if (operation.equals(Operation.VIEW_BILL_INFO))
		  return Utils.getInstance().getImage(Utils.getInstance().getImageDescriptor(Operation.VIEW_BILL_INFO.toString()));
	  else if (operation.equals(Operation.VIEW_VEHICLE_INFO))
		  return Utils.getInstance().getImage(Utils.getInstance().getImageDescriptor(Operation.VIEW_VEHICLE_INFO.toString()));
	  else if (operation.equals(Operation.VIEW_INFO_BY_DATE))
		  return Utils.getInstance().getImage(Utils.getInstance().getImageDescriptor(Operation.VIEW_INFO_BY_DATE.toString()));
	  else
		  return Utils.getInstance().getImage(Utils.getInstance().getImageDescriptor(Operation.VIEW_INFO_BY_PLACE.toString()));
  }
  
}
