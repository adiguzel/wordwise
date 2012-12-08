package com.wordwise.gameengine.dto;

import java.util.List;

public class DTOTranslationRating {

	private int qualityRating;
	// translation that is being rated
	private List<DTOTranslation> translation;

	public List<DTOTranslation> getTranslation() {
		return translation;
	}

	public void setTranslation(List<DTOTranslation> translation) {
		this.translation = translation;
	}

	public int getQualityRating() {
		return qualityRating;
	}

	public void setQualityRating(int qualityRating) {
		this.qualityRating = qualityRating;
	}
}
