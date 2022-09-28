package br.com.up.jogodaforca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import br.com.up.jogodaforca.adapter.AttemptAdapter;
import br.com.up.jogodaforca.models.Attempt;
import br.com.up.jogodaforca.repository.AttemptRepository;

public class ActivityDiscoverWord extends AppCompatActivity {

    private String discoverWord;
    private String playerName;
    private int discoverWordLength;
    private String discoverWordHidden;
    private int remainingAttempts;

    private TextView textViewRemainingAttempts;
    private TextView textViewDiscoverWord;
    private RecyclerView recyclerViewAttempts;
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
        recyclerViewAttempts = findViewById(R.id.recycler_view_attempts);
        inputLayoutDiscoverWordAttempt = findViewById(R.id.input_layout_discover_word_attempt);
        inputTextDiscoverWordAttempt = findViewById(R.id.input_text_discover_word_attempt);
        buttonTryWordOrCharacter = findViewById(R.id.button_try_word_or_character);

        recyclerViewAttempts.setLayoutManager(
            new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        );

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
        boolean playerScored = false;
        if (discoverWordAttempt.length() == 1 && this.discoverWord.contains(discoverWordAttempt)) {
            this.updateHiddenWithDiscoveredChar(discoverWordAttempt);
            playerScored = true;
        } else if (this.discoverWord.equals(discoverWordAttempt)) {
            this.discoverWordHidden = this.discoverWord;
            playerScored = true;
        } else {
            this.handleUserFailedAttempt();
        }
        if (playerScored) {
            this.updateAttemptsList(discoverWordAttempt, playerScored);
            if (this.discoverWord.equals(this.discoverWordHidden)) {
                this.finishGame(true);
            }
        }
    }

    private void handleUserFailedAttempt() {
        this.remainingAttempts--;
        this.updateRemainingAttempts();
        if (this.remainingAttempts == 0) {
            this.finishGame(false);
        }
    }

    private void updateAttemptsList(String discoverWordAttempt, boolean playerScored) {
        AttemptRepository.getInstance().save(new Attempt(discoverWordAttempt, playerScored));
        this.updateAttemptsRecyclerView();
    }

    private void updateAttemptsRecyclerView() {
        recyclerViewAttempts.setAdapter(
            new AttemptAdapter(AttemptRepository.getInstance().getAll())
        );
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

        if (AttemptRepository.getInstance().existsByDescription(discoverWordAttempt)) {
            inputLayoutDiscoverWordAttempt.setError("Você já tentou essa letra/palavra");
            noErrors = false;
        }

        if (noErrors) {
            inputLayoutDiscoverWordAttempt.setError(null);
        }

        return noErrors;
    }

    @Override
    public void onBackPressed() {
        this.finishGame(false);
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
        this.remainingAttempts = 0;
    }

    private void finishGame(boolean playerWon) {
        AttemptRepository.resetRepository();
        this.updateAttemptsRecyclerView();
        this.clearAll();

        //   Intent intent = new Intent(this, GameOverActivity.class);
        //   intent.putExtra("playerWon", playerWon);
        //   intent.putExtra("playerName", this.playerName);
        //   intent.putExtra("discoverWord", this.discoverWord);
        //   startActivity(intent);
    }
}