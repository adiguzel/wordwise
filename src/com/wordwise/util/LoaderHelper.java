package com.wordwise.util;

import java.util.List;

import android.app.Activity;
import android.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.loader.TranslationLoader;
import com.wordwise.loader.WordLoader;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;

public class LoaderHelper {
	public enum LoaderType{
		TRANSLATION_LOADER,
		WORD_LOADER,	
	}
	
	private LinearLayout loadingFailed;
	private TextView loadingFailedText;
	private Button retryButton;
	private LinearLayout loading;
	private ProgressBar progress;
	
	@SuppressWarnings("unchecked")
	public void initLoader(Activity activity, LoaderType loaderType){
		if(loaderType == LoaderType.TRANSLATION_LOADER){
			activity.getLoaderManager().initLoader(0, null,
					(android.app.LoaderManager.LoaderCallbacks<List<Translation>>) activity)
					.forceLoad();
		}
		else if(loaderType == LoaderType.WORD_LOADER){
			activity.getLoaderManager().initLoader(0, null,
					(android.app.LoaderManager.LoaderCallbacks<List<Word>>) activity)
					.forceLoad();
		}
	}
	
	public Loader<?> onLoadCreated(Activity activity, LoaderType loaderType){	
		if(loaderType == LoaderType.TRANSLATION_LOADER){
			initScreen(activity);
			return new TranslationLoader(activity);
		}
		else if(loaderType == LoaderType.WORD_LOADER){
			initScreen(activity);
			return new WordLoader(activity);
		}
		else return null;
	}
	
	private void initScreen(Activity activity){
		activity.setContentView(R.layout.loading_game);
		
	    progress = (ProgressBar) activity.findViewById(R.id.progress_bar);		
	    loadingFailed = (LinearLayout) activity.findViewById(R.id.loadingFailed);
	    loadingFailedText = (TextView) activity.findViewById(R.id.loadingFailedText);
	    retryButton = (Button) activity.findViewById(R.id.retryButton);
	    loading = (LinearLayout) activity.findViewById(R.id.loading);
		
		progress.setVisibility(View.VISIBLE);
	}
	
	public void loadFailed(String failureText){
		loading.setVisibility(View.INVISIBLE);
		loadingFailed.setVisibility(View.VISIBLE);
		loadingFailedText.setVisibility(View.VISIBLE);
		retryButton.setVisibility(View.VISIBLE);
		loadingFailedText.setText("Oh snap. Failed to load.");
	}
	


}
