package com.example.home.tictactoe;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SinglePlayerGame extends AppCompatActivity {
    int gameState;
    private Button btn_reset;
    String player1Name, player2Name;

    // To vibrate while click on button
    private Vibrator vibrator;

    // sound for win or draw
    private MediaPlayer mediaPlayer;

    // Winner Dialog
    private Dialog WinDialog;
    private Dialog DrawDialog;
    private TextView WinnerName, WinnerScore;
    private Button btn_restart;

    private int player1Points;
    private int player2Points;

    // player points placeholder
    private TextView textViewPlayerX;
    private TextView textViewPlayerO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player_game);

        /**
         * Initialize Score textview
         */
        textViewPlayerX = (TextView) findViewById(R.id.x_score);
        textViewPlayerO = (TextView) findViewById(R.id.o_score);


        makeScreen();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Intent intent = this.getIntent();
        player1Name = intent.getExtras().getString("Player 1");
        player2Name = "Computer";

        WinDialog = new Dialog(this);
        DrawDialog = new Dialog(this);

        gameState = 1; // 1 - can play  2-GameOver  3-Draw;

        btn_reset = (Button) findViewById(R.id.btn_reset_game);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }


    public void GameBoardClick(View view) {
        ImageView selectedImage = (ImageView) view;

        int selectedBlock = 0;
        switch ((selectedImage.getId())) {
            case R.id.iv_11:
                selectedBlock = 1;
                break;
            case R.id.iv_12:
                selectedBlock = 2;
                break;
            case R.id.iv_13:
                selectedBlock = 3;
                break;

            case R.id.iv_21:
                selectedBlock = 4;
                break;
            case R.id.iv_22:
                selectedBlock = 5;
                break;
            case R.id.iv_23:
                selectedBlock = 6;
                break;

            case R.id.iv_31:
                selectedBlock = 7;
                break;
            case R.id.iv_32:
                selectedBlock = 8;
                break;
            case R.id.iv_33:
                selectedBlock = 9;
                break;

        }

        PlayGame(selectedBlock, selectedImage);
    }

    int activePlayer = 1;
    ArrayList<Integer> Player1 = new ArrayList<Integer>();
    ArrayList<Integer> Player2 = new ArrayList<Integer>();

    private void PlayGame(int selectedBlock, ImageView selectedImage) {
        if (gameState == 1) {
            if (activePlayer == 1) {
                selectedImage.setImageResource(R.drawable.x);
                Player1.add(selectedBlock);
                vibrator.vibrate(50);
                activePlayer = 2;

                AutoPlay();
            } else if (activePlayer == 2) {
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selectedImage.setImageResource(R.drawable.o);
                Player2.add(selectedBlock);
                vibrator.vibrate(50);
                activePlayer = 1;
            }

            selectedImage.setEnabled(false);
            CheckWinner();
        }
    }

    private void CheckWinner() {
        int winner = 0;

        /******** for Player 1 *********/

        // checking the winner in a row
        if (Player1.contains(1) && Player1.contains(2) && Player1.contains(3)) {
            winner = 1;
        }
        if (Player1.contains(4) && Player1.contains(5) && Player1.contains(6)) {
            winner = 1;
        }
        if (Player1.contains(7) && Player1.contains(8) && Player1.contains(9)) {
            winner = 1;
        }

        // checking the winner in column
        if (Player1.contains(1) && Player1.contains(4) && Player1.contains(7)) {
            winner = 1;
        }
        if (Player1.contains(2) && Player1.contains(5) && Player1.contains(8)) {
            winner = 1;
        }
        if (Player1.contains(3) && Player1.contains(6) && Player1.contains(9)) {
            winner = 1;
        }

        // checking the winner in diagonal
        if (Player1.contains(1) && Player1.contains(5) && Player1.contains(9)) {
            winner = 1;
        }
        if (Player1.contains(3) && Player1.contains(5) && Player1.contains(7)) {
            winner = 1;
        }


        /********* for Player 2 *********/

        // checking the winner in row
        if (Player2.contains(1) && Player2.contains(2) && Player2.contains(3)) {
            winner = 2;
        }
        if (Player2.contains(4) && Player2.contains(5) && Player2.contains(6)) {
            winner = 2;
        }
        if (Player2.contains(7) && Player2.contains(8) && Player2.contains(9)) {
            winner = 2;
        }

        // checking the winner in column
        if (Player2.contains(1) && Player2.contains(4) && Player2.contains(7)) {
            winner = 2;
        }
        if (Player2.contains(2) && Player2.contains(5) && Player2.contains(8)) {
            winner = 2;
        }
        if (Player2.contains(3) && Player2.contains(6) && Player2.contains(9)) {
            winner = 2;
        }

        // checking the winner in diagonal
        if (Player2.contains(1) && Player2.contains(5) && Player2.contains(9)) {
            winner = 2;
        }
        if (Player2.contains(3) && Player2.contains(5) && Player2.contains(7)) {
            winner = 2;
        }


        if (winner != 0 && gameState == 1) {
            if (winner == 1) {
                WinSound();
                player1Wins();
                WinGameDialog();
                WinnerName.setText(player1Name);
                WinnerScore.setText("" + player1Points);

                activePlayer = 0;
            } else if (winner == 2) {
                player2Wins();
                WinSound();
                WinGameDialog();
                WinnerName.setText(player2Name);
                WinnerScore.setText("" + player2Points);

            }
            gameState = 2;
        }
    }

    private void AutoPlay() {

        ArrayList<Integer> emptyBlocks = new ArrayList<Integer>();

        for (int i = 1; i <= 9; i++) {
            if (!(Player1.contains(i) || Player2.contains(i))) {
                emptyBlocks.add(i);
            }
        }

        if (emptyBlocks.size() == 0) {
            CheckWinner();
            if (gameState == 1) {
                DrawSound();
                DrawGameDialog();

            }
            gameState = 3;

        } else {
            Random r = new Random();
            int randomIndex = r.nextInt(emptyBlocks.size());
            int selectedBlock = emptyBlocks.get(randomIndex);

            ImageView selectedImage = (ImageView) findViewById(R.id.iv_11);

            switch (selectedBlock) {
                case 1:
                    selectedImage = (ImageView) findViewById(R.id.iv_11);
                    break;
                case 2:
                    selectedImage = (ImageView) findViewById(R.id.iv_12);
                    break;
                case 3:
                    selectedImage = (ImageView) findViewById(R.id.iv_13);
                    break;

                case 4:
                    selectedImage = (ImageView) findViewById(R.id.iv_21);
                    break;
                case 5:
                    selectedImage = (ImageView) findViewById(R.id.iv_22);
                    break;
                case 6:
                    selectedImage = (ImageView) findViewById(R.id.iv_23);
                    break;

                case 7:
                    selectedImage = (ImageView) findViewById(R.id.iv_31);
                    break;
                case 8:
                    selectedImage = (ImageView) findViewById(R.id.iv_32);
                    break;
                case 9:
                    selectedImage = (ImageView) findViewById(R.id.iv_33);
                    break;
            }
            PlayGame(selectedBlock, selectedImage);
        }
    }


    private void resetGame() {
        gameState = 1;
        activePlayer = 1;
        Player1 = new ArrayList<Integer>();
        Player2 = new ArrayList<Integer>();

        ImageView iv;
        iv = (ImageView) findViewById(R.id.iv_11);
        iv.setImageResource(0);
        iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_12);
        iv.setImageResource(0);
        iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_13);
        iv.setImageResource(0);
        iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv_21);
        iv.setImageResource(0);
        iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_22);
        iv.setImageResource(0);
        iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_23);
        iv.setImageResource(0);
        iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv_31);
        iv.setImageResource(0);
        iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_32);
        iv.setImageResource(0);
        iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_33);
        iv.setImageResource(0);
        iv.setEnabled(true);

    }

    private void makeScreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }


    private void WinGameDialog() {

        WinDialog.setContentView(R.layout.layout_winner);
        btn_restart = (Button) WinDialog.findViewById(R.id.btn_restart_game);

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                WinDialog.dismiss();
            }
        });

        WinnerName = (TextView) WinDialog.findViewById(R.id.winner_name);
        WinnerScore = (TextView) WinDialog.findViewById(R.id.winner_score);
        WinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WinDialog.show();
        WinDialog.setCanceledOnTouchOutside(false);
        WinDialog.setCancelable(false);
    }

    private void DrawGameDialog() {
        DrawDialog.setContentView(R.layout.layout_draw);
        btn_restart = (Button) DrawDialog.findViewById(R.id.btn_restart_game);

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                DrawDialog.dismiss();

            }
        });

        DrawDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DrawDialog.show();
        DrawDialog.setCanceledOnTouchOutside(false);
        DrawDialog.setCancelable(false);
    }

    /**
     * If No one wins
     */
    private void DrawSound() {
        mediaPlayer = MediaPlayer.create(SinglePlayerGame.this, R.raw.draw);
        mediaPlayer.start();
    }

    /**
     * If player wins the match
     */
    private void WinSound() {
        mediaPlayer = MediaPlayer.create(SinglePlayerGame.this, R.raw.snd_win);
        mediaPlayer.start();
    }

    private void player1Wins() {
        WinSound();
        player1Points++;
        updatePointsText();
        //   disableAll();
    }

    private void player2Wins() {
        WinSound();
        player2Points++;
        updatePointsText();
        //    disableAll();
    }

    private void updatePointsText() {
        textViewPlayerX.setText("" + player1Points);
        textViewPlayerO.setText("" + player2Points);
    }

    /**
     * navigating back to MainActivity
     */
    public void onBackPressed() {
        startActivity(new Intent(SinglePlayerGame.this, SplashActivity.class));
        finish();
    }
}