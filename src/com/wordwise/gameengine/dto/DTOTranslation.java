package com.wordwise.gameengine.dto;

public class DTOTranslation {
	//Represents the language code of the translation
	private String lang;
	//Represents the text of the translation
	private String text;
	
	public DTOTranslation(String lang, String text){
		this.lang = lang;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setTex(String word) {
		this.text = word;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
