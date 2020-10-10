package com.example.twoactivities_LifeCycle2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities_LifeCycle2.extra.MESSAGE";

    private EditText mMessageEditText;

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

    //The first time onCreate() is called and your app starts,
    // the Bundle is null—there's no existing state the first time your app starts.
    // Subsequent calls to onCreate() have a bundle populated with the data you stored in onSaveInstanceState().
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageEditText = findViewById(R.id.editText_main);

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        // Use findViewByID() to get references from the layout to the reply header and reply TextView elements.
        // Assign those view instances to the private variables
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);

        // Restore the state.
        if (savedInstanceState != null) {

            // get the current visibility (true or false) out of the Bundle with the key "reply_visible".
            boolean isVisible = savedInstanceState.getBoolean("reply_visible");

            // If there's a reply_visible key in the state Bundle (and isVisible is therefore true),
            // you will need to restore the state.
            if (isVisible) {

                // make the header visible.
                mReplyHeadTextView.setVisibility(View.VISIBLE);

                // Get the text reply message from the Bundle with the key "reply_text",
                // and set the reply TextView to show that string.
                mReplyTextView.setText(savedInstanceState.getString("reply_text"));

                // Make the reply TextView visible as well
                mReplyTextView.setVisibility(View.VISIBLE);
            }

        }
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");

        Intent intent = new Intent(this, SecondActivity.class);
        String message = mMessageEditText.getText().toString();

        /*
         * getText():
         * Get and return string from the textview, and to be stored as type of Charsequence
         *
         * toString():
         * Convert Charsequence into String type
         *
         * References:
         * 1. https://developer.android.com/reference/android/widget/TextView#getText()
         * 2. https://developer.android.com/reference/java/lang/String#toString()
         * 3. https://web.archive.org/web/20201004125944/https://stackoverflow.com/questions/47150909/what-does-gettext-tostring-return
         * 4. https://web.archive.org/web/20201004125946/https://stackoverflow.com/questions/50539062/textview-gettext-tostring-vs-textview-tostring
         * */

        // Add that String message to the Intent as an extra
        // with the constant "EXTRA_MESSAGE" as the key and the string "message" as the value
        intent.putExtra(EXTRA_MESSAGE, message);
        //  startActivity(intent);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    // When your Activity is re-created, the system passes the state Bundle to onCreate() as its only argument.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

/**Remember that the reply header and text are marked invisible until there is a reply from the second Activity.
 * If the header is visible, then there is reply data that needs to be saved.
 * Note that we're only interested in that visibility state —
 * the actual text of the header doesn't need to be saved, because that text never changes. */

        // Check to see if the header is currently visible,
        // and if so, put that visibility state into the state Bundle with the putBoolean() method
        // and the key "reply_visible".
        if (mReplyHeadTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);

            //  add the reply text into the Bundle
            outState.putString("reply_text", mReplyTextView.getText().toString());
        }
    }

    // Add a public constant to define the key for a particular type of response you're interested in.
    public static final int TEXT_REQUEST = 1;

    // Add two private variables to hold the reply header and reply TextView elements, respectively.
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    /*
    The three arguments to onActivityResult() contain all the information you need to handle the return data:
    the requestCode you set when you launched the Activity with startActivityForResult(),
    the resultCode set in the launched Activity (usually one of RESULT_OK or RESULT_CANCELED), and
    the Intent data that contains the data returned from the launch Activity. */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Add code to test for TEXT_REQUEST to make sure you process the right Intent result,
        // in case there are several.
        if (requestCode == TEXT_REQUEST) {

            // Also test for RESULT_OK, to make sure that the request was successful.
            if (resultCode == RESULT_OK) {

                // get the Intent extra from the response Intent (data).
                // Here the key for the extra is the constant EXTRA_REPLY from SecondActivity.
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);

                // Set the visibility of the reply header to true.
                mReplyHeadTextView.setVisibility(View.VISIBLE);

                // Set the reply TextView text to the reply, and set its visibility to true.
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);

            }
            /**
             * The Activity class defines the result codes.
             * The code can be RESULT_OK (the request was successful),
             * RESULT_CANCELED (the user cancelled the operation),
             * or RESULT_FIRST_USER (for defining your own result codes).
             * */

        }

    }
}