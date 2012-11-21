package com.wordwise.gameengine;

import java.util.List;

import com.wordwise.gameengine.dto.DTOWord;

public interface ServerCommunication {

	public boolean addWord(DTOWord word);

	public boolean rateWord(String word, int rate);

	public List<DTOWord> listWords(String lang, int difficulty);

	public List<DTOWord> listWords(String lang, int difficulty, int number);
	
}
