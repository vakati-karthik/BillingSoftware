package com.karthik.BillingSoftware.Print;

import net.sf.paperclips.PrintPiece;

interface TextPrintPiece extends PrintPiece {
    /**
     * Returns the ascent of the first line of text, in pixels.
     * 
     * @return the ascent of the first line of text, in pixels.
     */
    public int getAscent();
}
