package com.example.twoactivities_LifeCycle2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

// The response data from the second Activity back to the main Activity is sent in an Intent extra.
// You construct this return Intent and put the data into it in much the same way you do for the sending Intent.

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }


    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    // add a public constant to define the key for the Intent extra
    public static final String EXTRA_REPLY = "com.example.android.twoactivities_LifeCycle2.extra.REPLY"; // URL

    // Add a private variable at the top of the class to hold the EditText.
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        // Get the Intent that activated this Activity.
        Intent intent = getIntent();

        // Get the string containing the message from the Intent extras
        // using the MainActivity.EXTRA_MESSAGE static variable as the key
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Use findViewByID() to get a reference to the TextView for the message from the layout
        TextView textView = findViewById(R.id.text_message);

        // Set the text of the TextView to the string from the Intent extra
        textView.setText(message);

        // use findViewByID() to get a reference to the EditText and assign it to that private variable mReply
        mReply = findViewById(R.id.editText_second);
    }

    public void returnReply(View view) {

        // get the text of the EditText as a string
        String reply = mReply.getText().toString();

        // create a new intent for the response—don't reuse the Intent object that you received from the original request.
        Intent replyIntent = new Intent();

        /**
         * When you use an explicit Intent to start another Activity,
         * you may not expect to get any data back—you're just activating that Activity.
         * In that case, you use startActivity() to start the new Activity.
         *
         * If you want to get data back from the activated Activity (SecondActivity), however,
         * you need to start it (in MainActivity) with startActivityForResult().
         * */

        // Add the reply string from the EditText to the new intent as an Intent extra.
        // Because extras are key/value pairs, here the key is EXTRA_REPLY, and the value is the reply
        replyIntent.putExtra(EXTRA_REPLY, reply);

        // Set the result to RESULT_OK to indicate that the response was successful.
        setResult(RESULT_OK, replyIntent);
        // The Activity class defines the result codes, including RESULT_OK and RESULT_CANCELLED.


        Log.d(LOG_TAG, "End SecondActivity");

        // Call finish() to close the Activity and return to MainActivity.
        finish();
    }
}