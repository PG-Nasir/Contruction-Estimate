package com.ComShare;
public class BackhandIdStore {
    private String ElementId;
    private String Element;
    public BackhandIdStore(String Elementid, String element) {
        this.ElementId = Elementid;
        this.Element = element;
    }
    public String getElementId()
    { 
    	return ElementId; 
    }
    public String getElement()
    { 
    	return Element; 
    }
}
