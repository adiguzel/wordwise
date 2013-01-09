package com.wordwise.util;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

import com.wordwise.R;
import com.wordwise.server.model.Language;

public class LanguageUtils
{
	private static List<Language> languages = null;
	
	public static void Init(Resources resources)
	{
		languages = new ArrayList<Language>();
		
		String[] languageCodes = resources.getStringArray(R.array.languageCodes);
		String[] languageNames = resources.getStringArray(R.array.languages);
		
		for (int i = 0; ((i < languageCodes.length) && (i < languageNames.length)); i++)
		{
			languages.add(new Language(languageNames[i], languageCodes[i]));
		}
	}
	
	public static List<Language> GetAllLanguages() throws RuntimeException
	{
		if (languages == null)
		{
			throw new RuntimeException("Call method Init() before using other methods!");
		}
		return languages;
	}
	
	public static Language GetByCode(String code)
	{
		for (Language language : languages)
		{
			if (language.getCode().equalsIgnoreCase(code))
			{
				return language;
			}
		}
		return null;
	}
}