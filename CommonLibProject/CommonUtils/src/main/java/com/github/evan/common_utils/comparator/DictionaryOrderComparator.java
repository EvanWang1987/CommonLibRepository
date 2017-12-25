package com.github.evan.common_utils.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Evan on 2017/12/25.
 */
public class DictionaryOrderComparator implements Comparator<String> {
    private Collator mCollator;

    public DictionaryOrderComparator(Locale locale) {
        mCollator = Collator.getInstance(locale);
    }

    @Override
    public int compare(String firstElement, String lastElement) {
        return mCollator.compare(firstElement, lastElement);
    }
}
