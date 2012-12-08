package com.wordwise.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wordwise.gameengine.Language;

public class LanguageManager {
	static LanguageManager instance = null;
	private List<Language> languages;
	private Map<String, Language> languageCodeToIndexMap;
	private Map<String, Language> languageNameToIndexMap;

	public static LanguageManager getInstance() {
		if (instance == null)
			return new LanguageManager();
		return instance;
	}

	private LanguageManager() {
		languages = readLanguagesFromFile();
		languageCodeToIndexMap = toLanguageCodeToIndexMap();
		languageNameToIndexMap = toLanguageNameToIndexMap();
	}

	private Map<String, Language> toLanguageCodeToIndexMap() {
		Map<String, Language> langMap = new HashMap<String, Language>();
		for (Language l : languages) {
			langMap.put(l.getCode(), l);
		}
		return langMap;
	}
	
	private Map<String, Language> toLanguageNameToIndexMap() {
		Map<String, Language> langMap = new HashMap<String, Language>();
		for (Language l : languages) {
			langMap.put(l.getName(), l);
		}
		return langMap;
	}

	// TODO implement reading languages from a language list
	/*
	 * Initiates the list of languages by reading it from a languages file
	 */
	private List<Language> readLanguagesFromFile() {
		List<Language> langList = new ArrayList<Language>();
		// DUMMY IMPLEMENTATION
		Language en = new Language("English", "en");
		Language de = new Language("German", "de");
		Language es = new Language("Spanish", "es");
		Language tr = new Language("Turkish", "tr");
		Language pt = new Language("Portugese", "pt");

		langList.add(en);
		langList.add(de);
		langList.add(es);
		langList.add(tr);
		langList.add(pt);
	
		// remove possible duplicates
		// langList = ListOps.removeDuplicateWithOrder(langList);
		return langList;
	}

	public String[] toLanguageNameArray() {
		System.out.println(languages.toString());
		List<String> langs = new ArrayList<String>();
		String[] langNames = new String[1];
		for (Language lang : languages)
			langs.add(lang.getName());

		return langs.toArray(langNames);
	}

	public String[] toLanguageCodeArray() {
		ArrayList<String> langs = new ArrayList<String>();
		String[] langCodes = new String[1];
		for (Language lang : languages)
			langs.add(lang.getCode());
		return langs.toArray(langCodes);
	}

	public Integer langCodeToIndex(String code){
		Language l = languageCodeToIndexMap.get(code);
		return languages.indexOf(l);
	}

	/*
	 * Returns the language instance with the given language code
	 */
	public Language fromCode(String code) {
		return languageCodeToIndexMap.get(code);
	}

	/*
	 * Returns the language instance with the given language name
	 */
	public Language fromName(String name) {
		return languageNameToIndexMap.get(name);
	}
}
