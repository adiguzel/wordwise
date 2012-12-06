package com.wordwise.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListOps {

	/**
	 * Removes the duplicates elements from a given list 
	 * by maintaining the order of the initial list 
	 * @param list 
	 * List to be remove duplicate elements from
	 * @return 
	 * New list with the duplicate elements removed
	 */
	public static<T> List<T> removeDuplicateWithOrder(List<T> list) {
		Set<T> set = new HashSet<T>();
		List<T> newList = new ArrayList<T>();
		for (Iterator<T> iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add( (T) element))
				newList.add( (T) element);
		}
		return newList;
	}
}
