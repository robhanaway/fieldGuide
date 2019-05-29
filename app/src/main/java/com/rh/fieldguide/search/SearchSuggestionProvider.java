package com.rh.fieldguide.search;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.rh.fieldguide.authority";
    public static final int MODE = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES;
    private UriMatcher matcher;

    private static final int SUGGESTIONS_CODE = 5;
    public SearchSuggestionProvider() {

        matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SUGGESTIONS_CODE);
        setupSuggestions(AUTHORITY, MODE);
    }
}
