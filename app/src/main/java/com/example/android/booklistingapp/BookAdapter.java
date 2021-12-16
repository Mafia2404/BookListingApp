package com.example.android.booklistingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book>{

    private final String LOG_Tag=BookAdapter.class.getSimpleName();

    public BookAdapter(Context context,List<Book> bookList){
        super(context,0,bookList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listViewItem=convertView;
        if(listViewItem==null){

            listViewItem=LayoutInflater.from(getContext()).inflate(R.layout.book_card,parent,false);

        }
        //Get the current book that is being requested for display
        Book currentBook = getItem(position);

        TextView title=(TextView) listViewItem.findViewById(R.id.book_title_text_view);
        // Set the book title to the correct view
        title.setText(currentBook.getTitle());

        TextView mAuthor=listViewItem.findViewById(R.id.author_text_view);
        try {
            // Set the author of the book to the correct view
            String author = currentBook.getAuthor();

            // Check whether the book author information or not
            if (!author.isEmpty()) {

                mAuthor.setText(author);
            }
        }catch (NullPointerException e){

            // Author information is not available from the JSON response
            Log.v(LOG_Tag,"No author is available");

            // Hide view from book
            mAuthor.setVisibility(View.INVISIBLE);
        }

        RatingBar rating=listViewItem.findViewById(R.id.rating_bar);
        // Set the rating for the book
        rating.setRating(currentBook.getRating());

        TextView mPrice=listViewItem.findViewById(R.id.retail_price_text_view);
        // Initialize string variable to store book price
        String price="";
        if (currentBook.getPrice() > 0) {
            // Book is available for sale
            // Get the book price
            price = "$" + currentBook.getPrice();
            // Set the price of the book to the text view
            mPrice.setText(price);
        }
        return listViewItem;
    }
}
