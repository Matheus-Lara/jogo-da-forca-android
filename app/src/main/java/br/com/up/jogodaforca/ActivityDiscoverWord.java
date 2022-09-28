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
    private int remainingAttempts;
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
        String discoverWordAttempt = this.getAttempt();
        this.clearAttempt();
        if (discoverWordAttempt.length() == 1 && this.discoverWord.contains(discoverWordAttempt)) {
            this.updateHiddenWithDiscoveredChar(discoverWordAttempt);
        } else if (this.discoverWord.equals(discoverWordAttempt)) {
            this.discoverWordHidden = this.discoverWord;
        } else {
            //TODO: implementar erro do jogador e decrementar tentativas (atualizar em tela)
            throw new UnsupportedOperationException("Ainda não implementada a finalização do jogo");
        }

        //TODO: Adicionar no recyclerView tentativas já realizadas em um repository

        if (this.discoverWord.equals(this.discoverWordHidden)) {
            //TODO: implementar finalização para jogador venceu
            throw new UnsupportedOperationException("Ainda não implementada a finalização do jogo");
        }
    }

    private void updateHiddenWithDiscoveredChar(String discoverWordAttempt) {
        char[] discoverWordHiddenAsArray = this.discoverWordHidden.toCharArray();
        for (int i = 0; i < this.discoverWord.length(); i++) {
            if (String.valueOf(this.discoverWord.charAt(i)).equals(discoverWordAttempt)) {
                discoverWordHiddenAsArray[i] = discoverWordAttempt.charAt(0);
            }
        }
        this.discoverWordHidden = String.valueOf(discoverWordHiddenAsArray);
        this.updateDiscoverWordHidden();
    }

    private String getAttempt() {
        return Objects
            .requireNonNull(inputTextDiscoverWordAttempt.getText())
            .toString()
            .replaceAll("\\s+","")
            .toUpperCase();
    }

    private void clearAttempt() {
        Objects.requireNonNull(inputTextDiscoverWordAttempt.getText()).clear();
    }

    private boolean validateTryWordOrCharacter() {
        String discoverWordAttempt = this.getAttempt();
        boolean noErrors = true;

        if (discoverWordAttempt.isEmpty()) {
            inputLayoutDiscoverWordAttempt.setError("Informe a palavra ou letra a ser tentada");
            noErrors = false;
        } else if (discoverWordAttempt.length() > 1 && discoverWordAttempt.length() != discoverWordLength) {
            inputLayoutDiscoverWordAttempt.setError("Informe apenas uma letra ou a palavra completa");
            noErrors = false;
        }

        //TODO: verificar se o usuário já tentou a letra ou palavra

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

        for (int i = 0; i < this.discoverWordLength; i++) {
            this.discoverWordHidden += "_";
        }
    }

    private void startWithData() {
        this.remainingAttempts = this.discoverWordLength * 2;
        this.updateRemainingAttempts();
        this.updateDiscoverWordHidden();
    }

    private void updateRemainingAttempts() {
        String text = "Tentativas restantes: " + this.remainingAttempts;
        textViewRemainingAttempts.setText(text);
    }

    private void updateDiscoverWordHidden() {
        char[] discoverWordHiddenAsCharArray = this.discoverWordHidden.toCharArray();
        String[] discoverWordHiddenWithBlankSpaces = new String[this.discoverWordLength];

        int i = 0;
        for (char character : discoverWordHiddenAsCharArray) {
            discoverWordHiddenWithBlankSpaces[i] = String.valueOf(character);
            i++;
        }

        textViewDiscoverWord.setText(String.join(" ", discoverWordHiddenWithBlankSpaces));
    }

    private void clearAll() {
        this.clearAttempt();
        this.discoverWord = null;
        this.playerName = null;
        this.discoverWordLength = 0;
        this.discoverWordHidden = null;
    }
}