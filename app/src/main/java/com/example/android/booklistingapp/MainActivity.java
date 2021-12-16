package com.example.android.booklistingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mUserSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI(findViewById(R.id.main_parent));
        mUserSearch=findViewById(R.id.user_input_edit_text_view);
        final ImageButton search=findViewById(R.id.search_button);
        mUserSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE){
                    search.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    public void searchFor(View view) {
        EditText userInput=findViewById(R.id.user_input_edit_text_view);
        String input=userInput.getText().toString();
        if(!input.isEmpty()){

            Intent results=new Intent(MainActivity.this,QueryResultsActivity.class);
            results.putExtra("topic",mUserSearch.getText().toString().toLowerCase());

            startActivity(results);
        }
        else{
            // User has not entered any search text
            // Notify user to enter text via toast

            Toast.makeText(
                    MainActivity.this,
                    getString(R.string.enter_text),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
    /**
     * Set up touch listeners on all parts of the UI besides the {@link EditText} so that the user
     * can click out to hide the soft keypad and choose the necessary filter radio boxes befitting
     * their need
     */
    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Hide keypad
                    v.performClick();
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion
        if (view instanceof ViewGroup) {
            // Current view is a {@Link ViewGroup}
            // Traverse the {@link ViewGroup}, over each child
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                // Get the current child view
                View innerView = ((ViewGroup) view).getChildAt(i);
                // Set up touch listeners on non-text box views
                setupUI(innerView);
            }
        }
    }

    /**
     * This method hides the soft keypad that pops up when there are views that solicit user input
     */
    public static void hideSoftKeyboard(Activity activity) {
        // Get the activity's input method service
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        // Hide the soft keypad
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}