package com.gmail.deal10041.tictactoe;

import java.io.Serializable;

/**
 * Created by Dylan Wellner on 15-2-2018.
 * Defines the game class for tic tac toe
 */

public class Game implements Serializable {

    // Variables of class
    final public int BOARD_SIZE = 3;
    private Tile[][] board;
    private Boolean playerOneTurn;

    // constructor of class
    public Game() {
        // initiate board
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Tile.BLANK;
            }
        }

        playerOneTurn = true;
    }

    // draws a new symbol
    public Tile draw(int row, int column) {
        // retrieve current value
        Tile tileValue = board[row][column];

        if (tileValue == Tile.BLANK) {

            if(playerOneTurn) {
                // put a cross on current tile
                board[row][column] = Tile.CROSS;
                playerOneTurn = false;
                return Tile.CROSS;
            }
            else {
                // put a circle on current tile
                board[row][column] = Tile.CIRCLE;
                playerOneTurn = true;
                return Tile.CIRCLE;
            }
        }
        else {
            return Tile.INVALID;
        }
    }
}
