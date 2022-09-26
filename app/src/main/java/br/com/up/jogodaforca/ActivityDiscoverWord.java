package br.com.up.jogodaforca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ActivityDiscoverWord extends AppCompatActivity {

    private String discoverWord;
    private String discoverWordHidden;
    private int discoverWordLength;
    private String playerName;

    private TextView textViewRemainingAttempts;
    private TextView textViewDiscoverWord;
    private TextInputLayout inputLayoutDiscoverWordAttempt;
    private TextInputEditText inputTextDiscoverWordAttempt;
    private Button buttonTryWordOrCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_word);
        Objects.requireNonNull(getSupportActionBar()).hide();

        textViewRemainingAttempts = findViewById(R.id.text_view_remaining_attempts);
        textViewDiscoverWord = findViewById(R.id.text_view_discover_word);
        //TODO: verificar recyclerView de palavras que já foram tentadas
        inputLayoutDiscoverWordAttempt = findViewById(R.id.input_layout_discover_word_attempt);
        inputTextDiscoverWordAttempt = findViewById(R.id.input_text_discover_word_attempt);
        buttonTryWordOrCharacter = findViewById(R.id.button_try_word_or_character);

        this.setData();
        this.startWithData();
        this.registerEvents();
    }

    private void registerEvents() {
        buttonTryWordOrCharacter.setOnClickListener(v -> {
            if (this.validateTryWordOrCharacter()) {
                this.tryWordOrCharacter();
            }
        });
    }

    private void tryWordOrCharacter() {
        // TODO: implementar
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private boolean validateTryWordOrCharacter() {
        String discoverWordAttempt = Objects.requireNonNull(inputTextDiscoverWordAttempt.getText()).toString();
        boolean noErrors = true;

        if (discoverWordAttempt.isEmpty()) {
            inputLayoutDiscoverWordAttempt.setError("Informe a palavra ou letra a ser tentada");
            noErrors = false;
        } else if (discoverWordAttempt.length() > 1 && discoverWordAttempt.length() != discoverWordLength) {
            inputLayoutDiscoverWordAttempt.setError("Informe apenas uma letra ou a palavra completa");
            noErrors = false;
        }

        if (noErrors) {
            inputLayoutDiscoverWordAttempt.setError(null);
        }

        return noErrors;
    }

    @Override
    public void onBackPressed() {
        //TODO: tratar para tela de que o jogador perdeu
        this.clearAll();
        super.onBackPressed();
    }

    private void setData() {
        this.discoverWord = getIntent().getStringExtra("discoverWord");
        this.playerName = getIntent().getStringExtra("playerName");
        this.discoverWordLength = discoverWord.length();
        this.discoverWordHidden = "";

        for (int i = 0; i < discoverWordLength; i++) {
            this.discoverWordHidden += "_ ";
        }
    }

    private void startWithData() {
        this.updateRemainingAttempts(this.discoverWordLength * 2);
        this.updateDiscoverWordHidden();
    }

    private void updateRemainingAttempts(int remainingAttempts) {
        textViewRemainingAttempts.setText("Tentativas restantes: " + remainingAttempts);
    }

    private void updateDiscoverWordHidden() {
        textViewDiscoverWord.setText(this.discoverWordHidden);
    }

    private void clearAll() {
        Objects.requireNonNull(inputTextDiscoverWordAttempt.getText()).clear();
        this.discoverWord = null;
        this.playerName = null;
        this.discoverWordLength = 0;
        this.discoverWordHidden = null;
    }
}