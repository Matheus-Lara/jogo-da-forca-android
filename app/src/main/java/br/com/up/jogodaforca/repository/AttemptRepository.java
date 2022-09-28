package br.com.up.jogodaforca.repository;

import java.util.ArrayList;

import br.com.up.jogodaforca.models.Attempt;

public class AttemptRepository {
    private static AttemptRepository repository;
    private ArrayList<Attempt> attempts = new ArrayList<>();

    public static AttemptRepository getInstance(){
        if (repository == null) {
            repository = new AttemptRepository();
        }
        return repository;
    }

    public static void resetRepository() {
        repository = null;
    }

    private AttemptRepository(){ }

    public void save(Attempt attempt){
        attempts.add(attempt);
    }

    public ArrayList<Attempt> getAll(){
        return attempts;
    }

    public boolean existsByDescription(String description) {
        for (Attempt attempt : attempts) {
            if (attempt.getDescription().equals(description)) {
                return true;
            }
        }
        return false;
    }
}
