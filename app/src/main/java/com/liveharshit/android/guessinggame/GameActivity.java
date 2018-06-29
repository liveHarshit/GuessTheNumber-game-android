package com.liveharshit.android.guessinggame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
    boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        enterButton = findViewById(R.id.enter_button);
        resetButton = findViewById(R.id.reset_button);
        numberEditText = findViewById(R.id.number_edit_text);
        resultTextView = findViewById(R.id.result_text_view);
        remainingGuessesTextView = findViewById(R.id.remaining_guesses_text_view);

        remainingGuessesTextView.setText(Integer.toString(remainingGuesses));
        answer = choseRandomNumber();
        Log.d("Answer is: ",Integer.toString(answer));

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remainingGuesses > 0 && !userWon) {
                    resultTextView.setText("");
                    checkCurrentNumber(answer);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((remainingGuesses==0||remainingGuesses==7)||userWon) {
                    resetData();
                } else {
                    showResetAlertDialog();
                }
            }
        });
    }

    private void showResetAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_settings_backup_restore_black_48dp);
        builder.setTitle("RESET");
        builder.setMessage("Are you sure to want to reset?");
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null) {
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetData();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void checkCurrentNumber(int answer) {
        String  numberString = numberEditText.getText().toString().trim();
        if (numberString.isEmpty()) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
            return;
        }
        int number = Integer.parseInt(numberString);
        if(number<answer) {
            if (remainingGuesses > 1) {
                resultTextView.append(numberString);
                resultTextView.append(" ");
                resultTextView.append(getString(R.string.low));
            } else {
                showLossAlertDialog();
            }
        }
        if(number>answer) {
            if (remainingGuesses > 1) {
                resultTextView.append(numberString);
                resultTextView.append(" ");
                resultTextView.append(getString(R.string.high));
            } else {
                showLossAlertDialog();
            }
        }
        if(number==answer) {
            showWonAlertDialog();
            userWon = true;
        }
        remainingGuesses--;
        remainingGuessesTextView.setText(Integer.toString(remainingGuesses));
        numberEditText.setText("");
    }

    private void showLossAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.loss_face);
        builder.setTitle(R.string.loss);
        builder.setMessage("Let's play again!");
        builder.setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetData();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showWonAlertDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.won_face);
        builder.setTitle(R.string.won);
        builder.setMessage("Remaining chances: " + Integer.toString(remainingGuesses - 1) + "\nNice try, Let's play again...");
        builder.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetData();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
        if (isBackPressed) {
            super.onBackPressed();
            return;
        }

        this.isBackPressed = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                isBackPressed =false;
            }
        }, 2000);
    }
}
