package com.wordwise.model;

public class Language {
	// Name of the language in English
	private String name;
	// Language code
	private String code;

	public Language(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString() {
		return name;
	}
    /*Two languages are equal if they have the same name and language code*/
	@Override
	public boolean equals(Object other) {
		if (other instanceof Language) {
			Language otherLang = (Language) other;
			return (name == otherLang.name) && (code == otherLang.code);
		}
		return false;
	}
}
