package com.example.home.tictactoe;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    // player vs player and player vs phone and close buttons
    private Button btn_single, btn_multi, btn_close;

    // Dialogs
    Dialog ExitDialog, MultiPlayerNameLevelDialog, SinglePlayerNameLevelDialog;

    // Dialog buttons.
    private Button single_btn_play, multi_btn_play, btn_yes, btn_no;

    private ImageView multi_img_close, single_img_exit;

    // dialog EditText
    private EditText x_player_name, o_player_name, single_player_name;

    // strings that stores data getting from dialog editText
    private String player1NameText, player2NameText, single_playerNameText;

    // dialogs radio group
    RadioGroup MultiPlay_radioGroup, SinglePlay_radioGroup;

    // radiobutton for multi player dialog
    RadioButton multi_level_easy, multi_level_medium, multi_level_hard;

    // radiobutton for single player dialog
    RadioButton single_level_easy, single_level_hard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        makeScreen();


        // Instantiating MultiPlayer level dialog
        MultiPlayerNameLevelDialog = new Dialog(this);

        // Instantiating Single Player level dialog
        SinglePlayerNameLevelDialog = new Dialog(this);

        //Instantiating the close dialog
        ExitDialog = new Dialog(this);

        btn_single = (Button) findViewById(R.id.btn_single_ply);
        btn_multi = (Button) findViewById(R.id.btn_multi_ply);
        btn_close = (Button) findViewById(R.id.btn_exit_game);


        btn_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiNameLevelDialog();
            }
        });

        btn_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleNameLevelDialog();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitFromGame();
            }
        });
    }

    /**
     * Exit form game.
     */
    private void ExitFromGame() {

        ExitDialog.setContentView(R.layout.exit_dialog);
        btn_yes = (Button) ExitDialog.findViewById(R.id.btn_yes);
        btn_no = (Button) ExitDialog.findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitDialog.dismiss();
            }
        });

        ExitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ExitDialog.show();
        ExitDialog.setCanceledOnTouchOutside(false);
        ExitDialog.setCancelable(false);
    }


    /**
     * change player name and choose level for single player dialog
     */
    private void SingleNameLevelDialog(){
        SinglePlayerNameLevelDialog.setContentView(R.layout.dialog_name_and_level_with_computer);

        single_player_name = (EditText)SinglePlayerNameLevelDialog.findViewById(R.id.single_player_name);
        single_btn_play = (Button)SinglePlayerNameLevelDialog.findViewById(R.id.single_btn_start);


        SinglePlay_radioGroup = (RadioGroup)SinglePlayerNameLevelDialog.findViewById(R.id.choose_level);

        single_level_easy = (RadioButton)SinglePlayerNameLevelDialog.findViewById(R.id.single_level_easy);
        single_level_hard = (RadioButton)SinglePlayerNameLevelDialog.findViewById(R.id.single_level_hard);

        single_img_exit = (ImageView)SinglePlayerNameLevelDialog.findViewById(R.id.single_dialog_close);
        single_img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinglePlayerNameLevelDialog.dismiss();
            }
        });

        single_btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single_playerNameText = single_player_name.getText().toString();

                if (TextUtils.isEmpty(single_playerNameText)) {
                    single_player_name.setHint(Html.fromHtml("<font color='#FF0000'>Enter your name</font>"));
                    return;
                }

                if(single_level_easy.isChecked() || single_level_hard.isChecked()){
                    Log.d("SplashActivity", "Level Selected");
                }else{
                    Toast.makeText(SplashActivity.this, "Please select level", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(single_level_easy.isChecked()){
                    Intent intent = new Intent(SplashActivity.this, SinglePlayerGame.class);
                    intent.putExtra("Player 1", single_playerNameText);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        finish();
                    }
                }

                else if(single_level_hard.isChecked()){
                    Intent intent = new Intent(SplashActivity.this, SinglePlayerHardLevel.class);
                    intent.putExtra("Player 1", single_playerNameText);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        SinglePlayerNameLevelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SinglePlayerNameLevelDialog.show();
        SinglePlayerNameLevelDialog.setCanceledOnTouchOutside(false);
        SinglePlayerNameLevelDialog.setCancelable(false);
    }

    /**
     * change player name and choose level for multi player dialog
     */
    private void MultiNameLevelDialog(){
        MultiPlayerNameLevelDialog.setContentView(R.layout.dialog_name_and_level);

        x_player_name = (EditText) MultiPlayerNameLevelDialog.findViewById(R.id.XPlayerName);
        o_player_name = (EditText) MultiPlayerNameLevelDialog.findViewById(R.id.OPlayerName);

        MultiPlay_radioGroup = (RadioGroup)MultiPlayerNameLevelDialog.findViewById(R.id.choose_level);
        multi_level_easy = (RadioButton)MultiPlayerNameLevelDialog.findViewById(R.id.multi_level_easy);
        multi_level_medium = (RadioButton)MultiPlayerNameLevelDialog.findViewById(R.id.multi_level_medium);
        multi_level_hard = (RadioButton)MultiPlayerNameLevelDialog.findViewById(R.id.multi_level_hard);

        multi_btn_play = (Button)MultiPlayerNameLevelDialog.findViewById(R.id.multi_btn_play);

        multi_btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1NameText = x_player_name.getText().toString();
                player2NameText = o_player_name.getText().toString();

                if (TextUtils.isEmpty(player1NameText)) {
                    x_player_name.setHint(Html.fromHtml("<font color='#FF0000'>Enter your name</font>"));
                    return;
                }

                if (TextUtils.isEmpty(player2NameText)) {
                    o_player_name.setHint(Html.fromHtml("<font color='#FF0000'>Enter your name</font>"));
                    return;
                }

                if(multi_level_easy.isChecked() ||  multi_level_medium.isChecked() || multi_level_hard.isChecked()){
                    Log.d("SplashActivity", "Level Selected");
                }else{
                    Toast.makeText(SplashActivity.this, "Please select level", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(multi_level_easy.isChecked()){
                Intent intent = new Intent(SplashActivity.this, MultiPlayerGame.class);
                intent.putExtra("Player 1", player1NameText);
                intent.putExtra("Player 2", player2NameText);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    finish();
                } }

                else if( multi_level_medium.isChecked()){
                    Intent intent = new Intent(SplashActivity.this, MultiPlayerMediumLevel.class);
                    intent.putExtra("Player 1", player1NameText);
                    intent.putExtra("Player 2", player2NameText);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        finish();
                    }
                }

                else if(multi_level_hard.isChecked()){
                    Intent intent = new Intent(SplashActivity.this, MultiPlayerHardLevel.class);
                    intent.putExtra("Player 1", player1NameText);
                    intent.putExtra("Player 2", player2NameText);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });


        multi_img_close = (ImageView) MultiPlayerNameLevelDialog.findViewById(R.id.multi_dialog_close);
        multi_img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiPlayerNameLevelDialog.dismiss();
            }
        });

        MultiPlayerNameLevelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MultiPlayerNameLevelDialog.show();
        MultiPlayerNameLevelDialog.setCanceledOnTouchOutside(false);
        MultiPlayerNameLevelDialog.setCancelable(false);

    }


    /*
    use to make full screen.
     */
    private void makeScreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

}
