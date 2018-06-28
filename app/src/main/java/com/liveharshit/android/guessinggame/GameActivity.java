package com.liveharshit.android.guessinggame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private Button enterButton;
    private Button resetButton;
    private EditText numberEditText;
    private TextView resultTextView;
    private TextView remainingGuessesTextView;
    private int answer;
    private int remainingGuesses = 7;
    private boolean userWon =false;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        enterButton = (Button)findViewById(R.id.enter_button);
        resetButton = (Button)findViewById(R.id.reset_button);
        numberEditText = (EditText)findViewById(R.id.number_edit_text);
        resultTextView = (TextView)findViewById(R.id.result_text_view);
        remainingGuessesTextView = (TextView)findViewById(R.id.remaining_guesses_text_view);

        remainingGuessesTextView.setText(Integer.toString(remainingGuesses));
        answer = choseRandomNumber();
        Log.d("Answer is: ",Integer.toString(answer));

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remainingGuesses > 0 && !userWon) {
                    checkCurrentNumber(answer);
                    remainingGuesses--;
                    remainingGuessesTextView.setText(Integer.toString(remainingGuesses));
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });
    }

    private void checkCurrentNumber(int answer) {
        String  numberString = numberEditText.getText().toString().trim();
        int number = Integer.parseInt(numberString);
        if(number<answer) {
            if (remainingGuesses > 1) {
                resultTextView.setText(R.string.low);
            } else {
                resultTextView.setText(R.string.loss);
            }
        }
        if(number>answer) {
            if (remainingGuesses > 1) {
                resultTextView.setText(R.string.high);
            } else {
                resultTextView.setText(R.string.loss);
            }
        }
        if(number==answer) {
            resultTextView.setText(R.string.won);
            userWon = true;
        }
    }

    private int choseRandomNumber() {
        return (int) Math.round(Math.random()*100);
    }

    private void resetData () {
        remainingGuesses = 7;
        answer = choseRandomNumber();
        Log.d("Answer is: ",Integer.toString(answer));
        resultTextView.setText("");
        remainingGuessesTextView.setText(Integer.toString(remainingGuesses));
        numberEditText.setText("");
        userWon = false;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
