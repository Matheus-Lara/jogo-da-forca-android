package br.com.up.jogodaforca.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.up.jogodaforca.R;
import br.com.up.jogodaforca.models.Attempt;

public class AttemptAdapter extends RecyclerView.Adapter<AttemptAdapter.AttemptViewHolder> {

    private final ArrayList<Attempt> attempts;

    public AttemptAdapter(ArrayList<Attempt> attempts){
        this.attempts = attempts;
    }

    @NonNull
    @Override
    public AttemptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View layout = layoutInflater.inflate(
            R.layout.view_attempt,
            parent,
            false
        );

        return new AttemptViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AttemptViewHolder holder, int position) {

        Attempt attempt = attempts.get(position);

        TextView textViewAttempt = holder.itemView.findViewById(R.id.text_view_item_attempt);
        TextView textViewResult = holder.itemView.findViewById(R.id.text_view_item_result);

        textViewAttempt.setText(attempt.getDescription());
        textViewResult.setText(attempt.getCorrect() ? "Acertou" : "Errou");
    }

    @Override
    public int getItemCount() {
        return attempts.size();
    }

    public static class AttemptViewHolder extends RecyclerView.ViewHolder {
        public AttemptViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
