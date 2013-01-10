package com.wordwise.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.wordwise.R;

public class MultiSelectAdapter extends BaseAdapter {
	Selectable[] items;
	Context context;
	int textViewResourceId;

	public MultiSelectAdapter(Context context, int textViewResourceId,
			Selectable[] items) {
		this.items = items;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(textViewResourceId, null);
		}
		CheckedTextView post = (CheckedTextView) v.findViewById(R.id.checkList);
		post.setText(items[position].getName());
		post.setChecked(items[position].isSelected());

		return v;
	}

	public int getCount() {
		return items.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
}