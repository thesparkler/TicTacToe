package com.example.home.tictactoe;

import java.util.Random;

public class TicTacToeGame
{
    private char mBoard[];
    private final static int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char ANDROID_PLAYER = 'O';
    public static final char EMPTY_SPACE = ' ';

    private Random mRand;

    public static int getBOARD_SIZE(){
        return BOARD_SIZE;
    }

    public TicTacToeGame(){
        mBoard = new char[BOARD_SIZE];

        for(int i =0; i < BOARD_SIZE; i++)
            mBoard[i] = EMPTY_SPACE;

        mRand = new Random();
    }

    public void clearBoard(){
        for(int i = 0; i < BOARD_SIZE; i++){
            mBoard[i] = EMPTY_SPACE;
        }
    }

    public void setMove(char player, int location){
        mBoard[location] = player;
    }

    public int getComputerMove(){
        int move;
        for(int i = 0; i < getBOARD_SIZE(); i++){
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER)
            {
                char curr = mBoard[i];
                mBoard[i] = ANDROID_PLAYER;
                if(checkForWinner() == 3)
                {
                    setMove(ANDROID_PLAYER, i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        for(int i = 0; i < getBOARD_SIZE(); i++){
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER)
            {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;
                if(checkForWinner() == 2)
                {
                    setMove(ANDROID_PLAYER, i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }

        }

        do{
            move = mRand.nextInt(getBOARD_SIZE());
        }while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == ANDROID_PLAYER);
            setMove(ANDROID_PLAYER, move);
        return move;
    }

    public int checkForWinner()
    {
        for(int i = 0; i <= 6; i += 3){

            if(mBoard[i] == HUMAN_PLAYER &&
               mBoard[i+1] == HUMAN_PLAYER  &&
               mBoard[i+2] == HUMAN_PLAYER)

                return 2;

            if(mBoard[i] == ANDROID_PLAYER &&
                    mBoard[i+1] == ANDROID_PLAYER  &&
                    mBoard[i+2] == ANDROID_PLAYER)

                return 3;
        }

        for(int i = 0; i <=2; i++){
            if(mBoard[i] == HUMAN_PLAYER &&
               mBoard[i+3] == HUMAN_PLAYER &&
               mBoard[i+6] == HUMAN_PLAYER)

                return 2;

            if(mBoard[i] == ANDROID_PLAYER &&
                    mBoard[i+3] == ANDROID_PLAYER &&
                    mBoard[i+6] == ANDROID_PLAYER)

                return 3;
        }

        if((mBoard[0] == HUMAN_PLAYER &&
            mBoard[4] == HUMAN_PLAYER &&
            mBoard[8] == HUMAN_PLAYER) ||
            mBoard[2] == HUMAN_PLAYER  &&
            mBoard[4] == HUMAN_PLAYER &&
            mBoard[6] == HUMAN_PLAYER)
            return 2;

        if ((mBoard[0] == ANDROID_PLAYER &&
             mBoard[4] == ANDROID_PLAYER &&
             mBoard[8] == ANDROID_PLAYER) ||
             mBoard[2] == ANDROID_PLAYER &&
             mBoard[4] == ANDROID_PLAYER &&
             mBoard[6] == ANDROID_PLAYER)
            return 3;


        for(int i = 0; i < getBOARD_SIZE(); i++){
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER)
                return 0;
        }
        return 1;
    }


}
