package com.example.home.tictactoe;


import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;

public class SinglePlayerHardLevel extends AppCompatActivity{

    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private TextView playerTurn;


    private boolean mHumanFirst = true;
    private boolean mGameOver = false;

    // Used for reset the game
    private Button btnResetGame;

    // Used to get pressed feel while playing the game
    private Vibrator vibrator;

    // score placeholder for both player and comp
    TextView humanScore, compScore;

    private int humanPoints;
    private int computerPoints;

    // sound for win or draw
    private MediaPlayer  mediaPlayer;

    // Dialogs
    private Dialog WinDialog;
    private Dialog DrawDialog;
    private TextView WinnerName, WinnerScore;
    private Button btn_restart;

    String humanName, computerName;


    /**
     * Called when the activity is first created
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_hard_level);

        // retrieving data from the Bundle
        Intent intent = this.getIntent();
        humanName = intent.getExtras().getString("Player 1");
        computerName = "Computer";


        // Instantiating dialogs
        WinDialog = new Dialog(this);
        DrawDialog = new Dialog(this);

        // player turn
        playerTurn = (TextView)findViewById(R.id.player_turn);


        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        humanScore = (TextView)findViewById(R.id.human_score);
        compScore = (TextView)findViewById(R.id.computer_score);

        btnResetGame = (Button)findViewById(R.id.btn_reset_game);
        btnResetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                playerTurn.setText("X");
            }
        });

        mBoardButtons = new Button[mGame.getBOARD_SIZE()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mGame = new TicTacToeGame();

       startNewGame();
        makeScreen();
    }

    private void startNewGame(){
        mGame.clearBoard();

        for(int i = 0; i < mBoardButtons.length; i++){
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        if(mHumanFirst){
            playerTurn.setText("X");
            mHumanFirst = false;
        }
        else{
            playerTurn.setText("O");
            int move = mGame.getComputerMove();
            setMove(mGame.ANDROID_PLAYER, move);
            mHumanFirst = true;
        }
        mGameOver = false;
    }

    private void setMove(char player, int location){
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player == mGame.HUMAN_PLAYER){
            mBoardButtons[location].setTextColor(Color.parseColor("#B92B27"));
            vibrator.vibrate(50);
          //  enableAll();
        }
        else{
            mBoardButtons[location].setTextColor(Color.parseColor("#1565C0"));
            vibrator.vibrate(50);
        //    enableAll();
    }}

    private class ButtonClickListener implements View.OnClickListener
    {

        int location;

        public ButtonClickListener(int location){
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if(!mGameOver){
                if(mBoardButtons[location].isEnabled()){
                    setMove(mGame.HUMAN_PLAYER, location);

                    int winner = mGame.checkForWinner();

                    if(winner == 0){
                        playerTurn.setText("O");
                        int move = mGame.getComputerMove();
                        setMove(mGame.ANDROID_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }

                    if(winner == 0)
                        playerTurn.setText("X");
                    else if(winner == 1)
                    {
                        draw();
                        DrawGameDialog();
                        mGameOver = true;
                    }

                    else if(winner == 2){
                        humanWins();
                        WinGameDialog();
                        updatePointsText();

                        WinnerName.setText(humanName);
                        WinnerScore.setText(" "+ humanPoints);
                        mGameOver = true;
                    }
                    else{
                        ComputerWins();
                        WinGameDialog();
                        updatePointsText();
                        WinnerName.setText(computerName);
                        WinnerScore.setText(" "+ computerPoints);
                        mGameOver = true;
                    }
                }
            }
        }
    }

    private void makeScreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    private void resetGame(){
        for(int i = 0; i < mBoardButtons.length; i++){
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
        }

    }

    private void humanWins(){
        WinSound();
        humanPoints++;
        updatePointsText();

    }

    private void ComputerWins(){
        WinSound();
        computerPoints++;
        updatePointsText();

    }


    private void updatePointsText()
    {
        humanScore.setText(" " + humanPoints);
        compScore.setText(" " + computerPoints);
    }


    /**
     * If human or computer wins the match
     */
    private void WinSound()
    {
        mediaPlayer = MediaPlayer.create(SinglePlayerHardLevel.this,R.raw.snd_win);
        mediaPlayer.start();
    }

    /**
     * If No one wins
     */
    private void DrawSound()
    {
        mediaPlayer = MediaPlayer.create(SinglePlayerHardLevel.this,R.raw.draw);
        mediaPlayer.start();
    }

    private void draw() {
        DrawSound();
    }

    private void WinGameDialog() {

        WinDialog.setContentView(R.layout.layout_winner);
        btn_restart = (Button) WinDialog.findViewById(R.id.btn_restart_game);

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
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
                startNewGame();
                DrawDialog.dismiss();
            }
        });

        DrawDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DrawDialog.show();
        DrawDialog.setCanceledOnTouchOutside(false);
        DrawDialog.setCancelable(false);
    }

    /**
     *  navigating back to MainActivity
     */
    public void onBackPressed()
    {
        startActivity(new Intent(SinglePlayerHardLevel.this,SplashActivity.class));
        finish();
    }
}