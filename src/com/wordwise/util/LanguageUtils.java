package com.wordwise.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.content.res.Resources;

import com.wordwise.R;
import com.wordwise.server.model.Language;

public class LanguageUtils {
	private static List<Language> languages = null;

	public static void init(Resources resources) {
		languages = new ArrayList<Language>();

		String[] languageCodes = resources
				.getStringArray(R.array.languageCodes);
		String[] languageNames = resources.getStringArray(R.array.languages);

		for (int i = 0; ((i < languageCodes.length) && (i < languageNames.length)); i++) {
			languages.add(new Language(languageNames[i], languageCodes[i]));
		}
	}

	public static List<Language> getAllLanguages() throws RuntimeException {
		if (languages == null) {
			throw new RuntimeException(
					"Call method Init() before using other methods!");
		}
		return languages;
	}

	public static Language getByCode(String code) {
		// this check is necessary, otherwise Android throws null pointer
		// runtime exception
		if (languages != null) {
			for (Language language : languages) {
				if (language.getCode().equalsIgnoreCase(code)) {
					return language;
				}
			}
		}
		return null;
	}

	/*
	 * Returns the language instance with the given language name
	 */
	public static Language getByName(String name) {
		for (Language language : languages) {
			if (language.getLanguage().equalsIgnoreCase(name)) {
				return language;
			}
		}
		return null;
	}

	public static String[] toLanguageNameArray() {
		List<String> langs = new ArrayList<String>();
		String[] langNames = new String[1];

		for (Language lang : languages)
			langs.add(lang.getLanguage());

		return langs.toArray(langNames);
	}

	public static Integer getIndex(Language l) {
		return languages.indexOf(l);
	}

	public static Set<String> languageSetToCodeSet(Set<Language> languages) {
		Set<String> langCodeSet = new HashSet<String>();
		for (Language l : languages) {
			if (l != null)
				langCodeSet.add(l.getCode());
		}

		return langCodeSet;
	}
	
	public static List<Language> getProficientLanguages(Set<Language> proficientLanguagesSet) {
		List<Language> proficientLanguagesList  = new ArrayList<Language>();
		for (Language l : proficientLanguagesSet) {
			if (l != null) {
				proficientLanguagesList.add(l);
			}
		}
		return proficientLanguagesList;
	}
	
	public static Language getRandomProficientLanguage(List<Language> l) {
		Random randomGenerator = new Random();
		int maxRandomNumber = l.size();
		int randomLanguageNumber = randomGenerator.nextInt(maxRandomNumber);
		Language randomLanguage = l.get(randomLanguageNumber);
		return randomLanguage;
	}

}