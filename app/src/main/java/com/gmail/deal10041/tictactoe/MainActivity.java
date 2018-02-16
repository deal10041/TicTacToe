package com.gmail.deal10041.tictactoe;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // variable for game
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // start a new game
        game = new Game();
    }

    @Override
    protected void onSaveInstanceState(Bundle Outstate) {
        super.onSaveInstanceState(Outstate);

        // saves game
        Outstate.putSerializable("game",game);

        // saves tile states
        Button[] buttons = getButtons();
        String[] tileStates = new String[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            tileStates[i] = (String) buttons[i].getText();
        }
        Outstate.putStringArray("tileStates", tileStates);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        // restore game
        game = (Game) inState.getSerializable("game");

        // restore tile states
        Button[] buttons = getButtons();
        String[] tileStates = inState.getStringArray("tileStates");
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(tileStates[i]);
        }
    }

    public void tileClicked(View view) {

        // get id of clicked button
        int id = view.getId();

        // determine button and coordinates
        Button button = findViewById(id);
        String tagContent = (String)button.getTag();
        int tag = Integer.parseInt(tagContent);
        int row = tag / game.BOARD_SIZE;
        int column = tag % game.BOARD_SIZE;

        // update selected button
        Tile tile = game.draw(row,column);
        switch(tile) {
            case CROSS:
                button.setText("X");
                break;
            case CIRCLE:
                button.setText("O");
                break;
            case INVALID:
                // initiate variables
                Context context = getApplicationContext();
                String text = "Invalid move.";
                int duration = Toast.LENGTH_SHORT;

                // show toast
                Toast.makeText(context, text, duration).show();
        }

        game.updateGameState();
        // check if there is a win
        if(game.getGameState() == GameState.DRAW) {
            postMessage("It's a draw!");
        }
        else if(game.getGameState() == GameState.PLAYER_ONE) {
            postMessage("Player One wins!");
        }
        else if(game.getGameState() == GameState.PLAYER_TWO) {
            postMessage("Player Two wins!");
        }
    }

    public void resetClicked(View view) {
        // reset game
        game = new Game();

        // reset text of all buttons
        int buttonCount = game.BOARD_SIZE * game.BOARD_SIZE;
        Button[] buttons = getButtons();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
        }
    }

    private Button[] getButtons() {
        // initialise variables
        int buttonCount = game.BOARD_SIZE * game.BOARD_SIZE;
        Button[] buttons = new Button[buttonCount];

        // get all buttons from grid
        for(int i = 0; i < buttonCount; i++) {
            int id = getResources().getIdentifier("tile" + i, "id", getPackageName());
            buttons[i] = (Button) findViewById(id);
        }

        return buttons;
    }

    private void postMessage(String text) {
        // initiate variables
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        // show toast
        Toast.makeText(context, text, duration).show();
    }
}
