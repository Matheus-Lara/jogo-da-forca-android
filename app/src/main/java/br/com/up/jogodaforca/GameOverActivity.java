package br.com.up.jogodaforca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverActivity extends AppCompatActivity {

    private TextView textViewFinishGameTitle;
    private TextView textViewShowWord;
    private Button playAgainButton;
    private String discoverWord;
    private String playerName;
    private Boolean playerWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);


        textViewFinishGameTitle = findViewById(R.id.text_view_finish_game_title);
        textViewShowWord = findViewById(R.id.text_view_show_word);
        playAgainButton = findViewById(R.id.button_play_again);


        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        this.setData();
    }

    //set data from previous activity

    // get the data from the previous activity


    private void setData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            playerName = bundle.getString("playerName");
            discoverWord = bundle.getString("discoverWord");
            playerWon = bundle.getBoolean("playerWon");
            if (playerWon) {
                textViewFinishGameTitle.setText("Parabéns " + playerName + "!");
                textViewShowWord.setText("Você ganhou! A palavra era:  " + discoverWord);
            } else {
                textViewFinishGameTitle.setText("Que pena " + playerName + "!");
                textViewShowWord.setText("Você perdeu! A palavra era:  " + discoverWord);
            }
        }
    }
}