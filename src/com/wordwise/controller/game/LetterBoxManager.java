package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Translation;
import com.wordwise.util.LanguageUtils;
import com.wordwise.util.game.LetterBoxPositionUtils;
import com.wordwise.util.game.LetterBoxPositionUtils.Direction;
import com.wordwise.view.activity.game.LetterBox;

public class LetterBoxManager
{
	private List<Translation> translations = generateTranslations();
	private List<String> letters = generateLetters(88);
	private List<GridItem> currentSelection = new ArrayList<GridItem>(){
		private static final long serialVersionUID = 1L;
		public boolean add(GridItem item) {
	         super.add(item);
	         Collections.sort(currentSelection);
	         return true;
	    }
	};
	
	private int numberOfFoundWords = 0;
	private List<Integer> positionsOfFoundWords = new ArrayList<Integer>();
	private LetterBox letterBoxActivity;
	
	public LetterBoxManager(LetterBox letterBoxActivity)
	{
		this.letterBoxActivity = letterBoxActivity;
	}
	
	public boolean isAllTranslationsFound(){
		Log.v("Letterbox", "found : " + numberOfFoundWords);
		Log.v("Letterbox", "actual : "+ translations.size());
		return numberOfFoundWords == translations.size();
	}
	
	private List<Translation> generateTranslations()
	{
		ServerCommunication server = new RESTfullServerCommunication();
		return server.listTranslations(new Language("English", "en"), 0, 6);
		
		/*
		
		List<Translation> returnList = new ArrayList<Translation>();
		
		Translation translation = new Translation();
		translation.setTranslation("cadeira");
		Word word = new Word();
		word.setWord("chair");
		translation.setWord(word);
		returnList.add(translation);
		
		translation = new Translation();
		translation.setTranslation("porta");
		word = new Word();
		word.setWord("door");
		translation.setWord(word);
		returnList.add(translation);
		
		translation = new Translation();
		translation.setTranslation("mesa");
		word = new Word();
		word.setWord("table");
		translation.setWord(word);
		returnList.add(translation);
		
		translation = new Translation();
		translation.setTranslation("cama");
		word = new Word();
		word.setWord("bed");
		translation.setWord(word);
		returnList.add(translation);
		
		translation = new Translation();
		translation.setTranslation("chão");
		word = new Word();
		word.setWord("floor");
		translation.setWord(word);
		returnList.add(translation);
		
		translation = new Translation();
		translation.setTranslation("luz");
		word = new Word();
		word.setWord("light");
		translation.setWord(word);
		returnList.add(translation);
		
		return returnList; */
	}
	
	private List<String> generateLetters(int numberOfLetters)
	{
		List<String> returnList = new ArrayList<String>();
		
		String[] alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

		Random random = new Random();
		
		for (int i = 0; i < numberOfLetters; i++)
		{
			returnList.add(alphabet[random.nextInt(alphabet.length)]);
		}
		
		insertTranslationsIntoLetters(returnList);
		
		for (int i = 0; i < returnList.size(); i++)
		{
			returnList.set(i, returnList.get(i).toUpperCase());
		}
		
		return returnList;
	}

	private void insertTranslationsIntoLetters(List<String> letters)
	{
		Random random = new Random();
		int positionOfFirstLetter = random.nextInt(letters.size());
		for (Translation translation : translations)
		{
			while (!LetterBoxPositionUtils.InsertTranslationIntoPosition(translation.getWord().getWord(), positionOfFirstLetter, letters))
			{
				positionOfFirstLetter = random.nextInt(letters.size());
			}
		}
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	public List<String> getLetters() {
		return letters;
	}

	public void onLetterClick(TextView v, int position)
	{
		if (clickIsInsideExtremesOfCurrentSelection(position))
		{
			removeClickFromCurrentSelection(position, v);
			if(currentSelectionMatchesAWord())
			{
				makeCurrentSelectionAFoundWord();
			}
		}
		else if (clickIsValidForCurrentSelection(position))
		{
			addClickToCurrentSelection(position, v);
			if(currentSelectionMatchesAWord())
			{
				makeCurrentSelectionAFoundWord();
			}
		}
		else 
		{
			unselectCurrentSelection();
			addClickToCurrentSelection(position, v);
		}
	}
	
	private void makeCurrentSelectionAFoundWord()
	{
		numberOfFoundWords++;
		for (GridItem gridItem : currentSelection)
		{
			positionsOfFoundWords.add(gridItem.position);
			gridItem.textView.setBackgroundColor(gridItem.textView.getContext().getResources().getColor(R.color.wordwise_main_green_start));
		}
		if (getTranslation(getCurrentSelectionWord()) == null)
		{
			letterBoxActivity.markTranslationAsFound(getTranslation(getCurrentSelectionWordReversed()));
		}
		else
		{
			letterBoxActivity.markTranslationAsFound(getTranslation(getCurrentSelectionWord()));
		}
		
		if (isAllTranslationsFound())
		{
			gameOver();
		}
		clearCurrentSelection();
	}

	private Translation getTranslation(String word)
	{
		for (Translation translation : translations)
		{
			if (translation.getWord().getWord().equalsIgnoreCase(word))
			{
				return translation;
			}
		}
		return null;
	}

	private void gameOver()
	{
		letterBoxActivity.onGameOver();
	}

	private boolean currentSelectionMatchesAWord()
	{
		for (Translation word : translations)
		{
			if (word.getWord().getWord().equalsIgnoreCase(getCurrentSelectionWord()) ||
				word.getWord().getWord().equalsIgnoreCase(getCurrentSelectionWordReversed()))
			{
				return true;
			}
		}
		return false;
	}

	private String getCurrentSelectionWordReversed()
	{
		StringBuffer currentSelectionWord = new StringBuffer();
		for (int i = currentSelection.size() - 1; i >= 0 ; i--)
		{
			currentSelectionWord.append(currentSelection.get(i).textView.getText());
		}
		return currentSelectionWord.toString();
	}

	private String getCurrentSelectionWord()
	{
		StringBuffer currentSelectionWord = new StringBuffer();
		for (int i = 0; i < currentSelection.size(); i++)
		{
			currentSelectionWord.append(currentSelection.get(i).textView.getText());
		}
		return currentSelectionWord.toString();
	}
	
	private boolean clickIsInsideExtremesOfCurrentSelection(int position)
	{
		if (currentSelection.size() == 1
			&& currentSelection.get(0).position == position)
		{
			return true;
		}
		if (currentSelection.size() > 1 
			&& (currentSelection.get(0).position == position || currentSelection.get(currentSelection.size()-1).position == position))
		{
			return true;
		}
		return false;
	}

	private boolean clickIsValidForCurrentSelection(int position)
	{
		if (currentSelection.isEmpty())
		{
			return true;
		}
		if (currentSelection.size() == 1
			&& LetterBoxPositionUtils.ArePositionsAdjacent(currentSelection.get(0).position, position))
		{
			return true;
		}
		if (currentSelection.size() > 1)
		{
			Direction selectionDirection = LetterBoxPositionUtils.GetDirection(currentSelection.get(0).position, currentSelection.get(1).position);
			if (LetterBoxPositionUtils.ArePositionsAdjacent(currentSelection.get(currentSelection.size()-1).position, position)
				&& LetterBoxPositionUtils.GetDirection(currentSelection.get(currentSelection.size()-1).position, position) == selectionDirection)
			{
				return true;
			}
			if (LetterBoxPositionUtils.ArePositionsAdjacent(currentSelection.get(0).position, position)
				&& LetterBoxPositionUtils.GetDirection(currentSelection.get(0).position, position) == selectionDirection)
			{
				return true;
			}
		}
		return false;
	}

	private void unselectCurrentSelection()
	{
		for (GridItem gridItem : currentSelection)
		{
			if (positionsOfFoundWords.contains(gridItem.position))
			{
				gridItem.textView.setBackgroundColor(gridItem.textView.getContext().getResources().getColor(R.color.wordwise_main_green_start));
			}
			else
			{
				gridItem.textView.setBackgroundColor(gridItem.textView.getContext().getResources().getColor(R.color.divider_color));
			}
		}
		clearCurrentSelection();
	}
	
	private void clearCurrentSelection()
	{
		currentSelection.clear();
	}

	private void addClickToCurrentSelection(int position, TextView v)
	{
		if (!currentSelection.contains(new GridItem(position, v)))
		{
			currentSelection.add(new GridItem(position, v));
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.translucent_red));
		}
	}
	
	private void removeClickFromCurrentSelection(int position, TextView v)
	{
		if (currentSelection.contains(new GridItem(position, v)))
		{
			currentSelection.remove(new GridItem(position, v));
			if (positionsOfFoundWords.contains(position))
			{
				v.setBackgroundColor(v.getContext().getResources().getColor(R.color.wordwise_main_green_start));
			}
			else
			{
				v.setBackgroundColor(v.getContext().getResources().getColor(R.color.divider_color));
			}
		}
	}
	
	private class GridItem implements Comparable<GridItem>
	{
		public int position;
		public TextView textView;
		
		public GridItem(int position, TextView textView)
		{
			this.position = position;
			this.textView = textView;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			return position == ((GridItem)obj).position;
		}

		public int compareTo(GridItem another)
		{
			return Integer.valueOf(this.position).compareTo(another.position);
		}
	}
}
