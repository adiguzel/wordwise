package com.wordwise.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.content.res.Configuration;
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
	
	private static void isLanguagesInitiated()
	{
		if (languages == null) {
			throw new RuntimeException(
					"Call method Init() before using other methods!");
		}
	}

	public static List<Language> getAllLanguages() throws RuntimeException {
		isLanguagesInitiated();
		return languages;
	}

	public static Language getByCode(String code) {
		isLanguagesInitiated();
		for (Language language : languages) {
			if (language.getCode().equalsIgnoreCase(code)) {
				return language;
			}
		}
		return null;
	}

	/*
	 * Returns the language instance with the given language name
	 */
	public static Language getByName(String name) {
		isLanguagesInitiated();
		for (Language language : languages) {
			if (language.getLanguage().equalsIgnoreCase(name)) {
				return language;
			}
		}
		return null;
	}

	public static String[] toLanguageNameArray() {
		isLanguagesInitiated();
		List<String> langs = new ArrayList<String>();
		String[] langNames = new String[1];

		for (Language lang : languages)
			langs.add(lang.getLanguage());

		return langs.toArray(langNames);
	}

	public static Integer getIndex(Language l) {
		isLanguagesInitiated();
		return languages.indexOf(l);
	}

	public static Set<String> languageSetToCodeSet(Set<Language> languages) {
		isLanguagesInitiated();
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
	
	public static void setLocale(Locale locale,String languageCode, Context context) {
		locale = new Locale(languageCode);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config,
				context.getResources().getDisplayMetrics());
	}
	

}