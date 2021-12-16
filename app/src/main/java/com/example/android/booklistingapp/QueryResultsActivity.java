package com.example.android.booklistingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QueryResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /** URL for books data from the Google books API */
    private String REQUEST_URL="https://www.googleapis.com/books/v1/volumes?q=";


    /** Adapter for the list of book titles */
    private BookAdapter bookAdapter;

    /** Constant value for the BOOK loader ID */
    private static final int Book_Loader_ID = 1;

    /** Indeterminate progress bar for loading books */
    private ProgressBar progressBar;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStatetextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_books);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter((ListAdapter) bookAdapter);

        mEmptyStatetextView = findViewById(R.id.empty_text_view);
        bookListView.setEmptyView(mEmptyStatetextView);

        progressBar = findViewById(R.id.progress_spinner);
        progressBar.setIndeterminate(true);

        // Get the spawn intent
        Intent queryIntent = getIntent();
        // Get the search text typed by the user
        String searchText = getIntent().getStringExtra("topic");
        // Initialize variable to hold the processed search query
        String processedQuery = "";
        // No filters used
        processedQuery = searchText;
        // Build the url from user search
        REQUEST_URL += processedQuery + "&maxResults40";


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.app.LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(Book_Loader_ID, null, (android.app.LoaderManager.LoaderCallbacks<List<Book>>)this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progress_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStatetextView.setText(R.string.no_internet_connection);
        }


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new BookLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.progress_spinner);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStatetextView.setText("NO Books Found");
        // Clear the adapter of previous data
        bookAdapter.clear();

        // Add valid list of books to the adapter
        if (books != null && !books.isEmpty()) {
            bookAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        // Clear existing data on adapter as loader is reset
        bookAdapter.clear();
    }
}
