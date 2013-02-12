package com.wordwise.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;
/**
 * This class is used as a hack to put gridviews into scroll views which is by default not
 * possible Stackoverflow discussion:
 * http://stackoverflow.com/questions/9889255/how-to-add-multiple-gridviews-to-a-scrollview-in-android-java
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 **/
public class ScrollableGridView extends GridView {
	boolean expanded = true;

	public ScrollableGridView(Context context) {
		super(context);
	}

	public ScrollableGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollableGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isExpanded() {
		return expanded;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// HACK! TAKE THAT ANDROID!
		if (isExpanded()) {
			// Calculate entire height by providing a very large height hint.
			// But do not use the highest 2 bits of this integer; those are
			// reserved for the MeasureSpec mode.
			int expandSpec = MeasureSpec.makeMeasureSpec(
					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);

			ViewGroup.LayoutParams params = getLayoutParams();
			params.height = getMeasuredHeight();
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
}