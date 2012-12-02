package com.wordwise.model;

public class Selectable {

 	private String name;
	private boolean selected;

 	public Selectable(String name) {
		this.name = name;
		selected = false;
	}

 	public String getName() {
		return name;
	}

 	public void setName(String name) {
		this.name = name;
	}

 	public boolean isSelected() {
		return selected;
	}

 	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}