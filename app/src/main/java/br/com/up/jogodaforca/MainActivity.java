package br.com.up.jogodaforca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutDiscoverWord;
    private TextInputLayout inputLayoutPlayerName;

    private TextInputEditText inputDiscoverWord;
    private TextInputEditText inputPlayerName;

    private Button buttonStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        inputLayoutDiscoverWord = findViewById(R.id.input_layout_discover_word);
        inputLayoutPlayerName = findViewById(R.id.input_layout_player_name);
        inputDiscoverWord = findViewById(R.id.input_text_discover_word);
        inputPlayerName = findViewById(R.id.input_text_player_name);
        buttonStartGame = findViewById(R.id.button_start_game);

        this.registerEvents();
    }

    private void registerEvents() {
        buttonStartGame.setOnClickListener(v -> {
            if (this.validateStartGame()) {
                this.startGame();
            }
        });
    }

    private boolean validateStartGame() {
        String discoverWord = Objects.requireNonNull(inputDiscoverWord.getText()).toString();
        String playerName = Objects.requireNonNull(inputPlayerName.getText()).toString();

        boolean noErrors = true;

        if (discoverWord.isEmpty()) {
            inputLayoutDiscoverWord.setError("Informe a palavra a ser descoberta");
            noErrors = false;
        } else if (discoverWord.length() < 3) {
            inputLayoutDiscoverWord.setError("A palavra deve ter no mínimo 3 letras");
            noErrors = false;
        }

        if (playerName.isEmpty()) {
            inputLayoutPlayerName.setError("Informe o nome do jogador");
            noErrors = false;
        }

        if (noErrors) {
            inputLayoutDiscoverWord.setError(null);
            inputLayoutPlayerName.setError(null);
        }

        return noErrors;
    }

    private void startGame() {

        Intent intent = new Intent(
                getApplicationContext(),
                ActivityDiscoverWord.class
        );

        intent.putExtra("discoverWord", Objects.requireNonNull(inputDiscoverWord.getText()).toString());
        intent.putExtra("playerName", Objects.requireNonNull(inputPlayerName.getText()).toString());

        Objects.requireNonNull(inputDiscoverWord.getText()).clear();
        Objects.requireNonNull(inputPlayerName.getText()).clear();

        startActivity(intent);
    }
}