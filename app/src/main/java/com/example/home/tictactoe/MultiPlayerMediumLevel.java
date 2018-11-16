package com.example.home.tictactoe;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;


public class MultiPlayerMediumLevel extends AppCompatActivity implements View.OnClickListener
{

    private Button[][] buttons = new Button[4][4];

    private TextView playerTurn;
    private Vibrator vibrator;


    // Winner Dialog
    private Dialog WinDialog;
    private Dialog DrawDialog;
    private TextView WinnerName, WinnerScore;
    private Button btn_restart;


    // first player
    private boolean player1Turn = true;


    private int roundCount;

    private int player1Points;
    private int player2Points;

    // player points placeholder
    private TextView textViewPlayerX;
    private TextView textViewPlayerO;

    // reset game
    private Button BtnResetGame;

    // sound for win or draw
    private MediaPlayer  mediaPlayer;

    private String player1Name, player2Name;

    int turn = 1;
    int flipValue = 0;
    String buttonID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_medium_level);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        makeScreen();


        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("Player 1");
        player2Name = intent.getExtras().getString("Player 2");

        // player turn
        playerTurn = (TextView)findViewById(R.id.player_turn);

        WinDialog = new Dialog(this);
        DrawDialog = new Dialog(this);

        /**
         * Initialize Score textview
         */
        textViewPlayerX = (TextView)findViewById(R.id.x_score);
        textViewPlayerO = (TextView)findViewById(R.id.o_score);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        BtnResetGame = (Button)findViewById(R.id.btn_reset_game);
        BtnResetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                playerTurn.setText("X");
            }
        });
    }

    private void resetBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
        enableAll();
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn && turn == 1 && !(((Button) v).getText().toString().equals("X")) && !((Button)v).getText().toString().equals("O"))
        {
            if(flipValue == 0)
            {
                playerTurn.setText("O");
            }
            else if(flipValue == 1)
            {
                playerTurn.setText("X");
            }
            ((Button)v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#B92B27"));
            vibrator.vibrate(50);
            turn = 2;

        } else if(turn ==2 && !(((Button) v).getText().toString().equals("X")) && !((Button)v).getText().toString().equals("O") )
        {
            if(flipValue == 0)
            {
                playerTurn.setText("X");
            }
            else if(flipValue == 1)
            {
                playerTurn.setText("O");
            }
            ((Button) v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#1565C0"));
            vibrator.vibrate(50);

            turn = 1;
        }
        roundCount++;

        if (checkForWin()) {
            if (player1Turn)
            {
                WinGameDialog();
                player1Wins();
                WinnerName.setText(player1Name);
                WinnerScore.setText("" + player1Points);
            }
            else {

                WinGameDialog();
                player2Wins();
                WinnerName.setText(player2Name);
                WinnerScore.setText("" + player2Points);

            }
        } else if (roundCount == 16) {
            draw();
            DrawGameDialog();

        } else {
            player1Turn = !player1Turn;

        }
    }

    private void player1Wins()
    {
        WinSound();
        player1Points++;
        updatePointsText();
        disableAll();
    }

    private void player2Wins()
    {
        WinSound();
        player2Points++;
        updatePointsText();
        disableAll();
    }

    private void updatePointsText() {
        textViewPlayerX.setText("" + player1Points);
        textViewPlayerO.setText("" + player2Points);
    }


    private void draw() {
        DrawSound();
    }

    private boolean checkForWin()
    {
        final Animation in = new AlphaAnimation(0.0f, 0.1f);
        in.setDuration(150);

        String[][] field = new String[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }


        for (int i = 0; i < 4; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && field[i][0].equals(field[i][3])
                    && !field[i][0].equals("")) {

//                if (!buttons[i][0].getText().toString().equals(" ")) {
//                    buttons[i][0].setTextColor(Color.GRAY);
//                    buttons[i][1].setTextColor(Color.GRAY);
//                    buttons[i][2].setTextColor(Color.GRAY);
//
//                }
                return true;


            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && field[0][i].equals(field[3][i])
                    && !field[0][i].equals("")) {

//                if (!buttons[0][i].getText().toString().equals(" ")) {
//                    buttons[0][i].setTextColor(Color.GRAY);
//                    buttons[1][i].setTextColor(Color.GRAY);
//                    buttons[2][i].setTextColor(Color.GRAY);
//                }


                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && field[0][0].equals(field[3][3])
                && !field[0][0].equals("")) {


//            if (!buttons[0][0].getText().toString().equals(" ")) {
//                buttons[0][0].setTextColor(Color.GRAY);
//                buttons[1][1].setTextColor(Color.GRAY);
//                buttons[2][2].setTextColor(Color.GRAY);
//            }

            return true;
        }

        if (field[0][3].equals(field[1][2])
                && field[0][3].equals(field[2][1])
                && field[0][3].equals(field[3][0])
                && !field[0][3].equals("")) {

//            if (!buttons[2][0].getText().toString().equals(" ")) {
//                buttons[2][0].setTextColor(Color.GRAY);
//                buttons[1][1].setTextColor(Color.GRAY);
//                buttons[0][2].setTextColor(Color.GRAY);
//            }


            return true;
        }

        return false;


    }

    /**
     *  navigating back to MainActivity
     */
    public void onBackPressed()
    {
        startActivity(new Intent(MultiPlayerMediumLevel.this,SplashActivity.class));
        finish();
    }

    /**
     * If No one wins
     */
    private void DrawSound()
    {
        mediaPlayer = MediaPlayer.create(MultiPlayerMediumLevel.this,R.raw.draw);
        mediaPlayer.start();
    }

    /**
     * If player wins the match
     */
    private void WinSound()
    {
        mediaPlayer = MediaPlayer.create(MultiPlayerMediumLevel.this,R.raw.snd_win);
        mediaPlayer.start();
    }

    private void makeScreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    private void disableAll() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setEnabled(false);
            }

        }
    }

    private void enableAll() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setEnabled(true);
            }

        }
    }

    private void WinGameDialog()
    {

        WinDialog.setContentView(R.layout.layout_winner);
        btn_restart = (Button)WinDialog.findViewById(R.id.btn_restart_game);

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                playerTurn.setText("X");
                WinDialog.dismiss();
            }
        });

        WinnerName = (TextView)WinDialog.findViewById(R.id.winner_name);
        WinnerScore = (TextView)WinDialog.findViewById(R.id.winner_score);
        WinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WinDialog.show();
        WinDialog.setCanceledOnTouchOutside(false);
        WinDialog.setCancelable(false);
    }

    private void DrawGameDialog()
    {
        DrawDialog.setContentView(R.layout.layout_draw);
        btn_restart = (Button)DrawDialog.findViewById(R.id.btn_restart_game);

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetBoard();
                playerTurn.setText("X");
                DrawDialog.dismiss();

            }
        });

        DrawDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DrawDialog.show();
        DrawDialog.setCanceledOnTouchOutside(false);
        DrawDialog.setCancelable(false);
    }


}