package br.ufc.caps.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.List;

import br.ufc.caps.R;
import br.ufc.caps.geofence.Local;

/**
 * Created by messias on 6/13/16.
 *
 * @author Messias Lima
 */
public class LocalCustomAdapter extends RecyclerView.Adapter<LocalViewHolder> {

    private static final int CARD_INTIAL_SIZE = 300;
    private static final int TEXT_BOX_INTIAL_SIZE =  56;

    Context context;
    List<Local> locals;
    boolean expanded = false;

    public LocalCustomAdapter(Context context, List<Local> locals) {
        this.context = context;
        this.locals = locals;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.local_list_item, parent, false);
        return new LocalViewHolder(context, view);

    }

    @Override
    public void onBindViewHolder(final LocalViewHolder holder, int position) {

        final Local local = locals.get(position);

        holder.textViewTitle.setText(local.getNome());
        holder.textViewText.setText(local.getTexto());

        boolean enabled = false;
        if (local.getAtivo() == Local.VERDADEIRO) {
            enabled = true;
        }
        holder.enabledSwitch.setChecked(enabled);

        holder.backgroundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clique", local.getNome());
                if (expanded) {
                    colapse(holder.cardView, holder.textBar);
                } else {
                    expand(holder.cardView, holder.textBar);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locals.size();
    }

    public void expand(final View card, final View textBox) {
        final int targetHeightCard = card.getHeight();
        final int targetHeightTextBox = textBox.getHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                card.getLayoutParams().height = targetHeightCard + (int) (targetHeightCard * interpolatedTime);
                card.requestLayout();
                textBox.getLayoutParams().height = targetHeightTextBox + (int) ((targetHeightCard) * interpolatedTime);
                textBox.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                expanded = true;
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (targetHeightCard / card.getContext().getResources().getDisplayMetrics().density));
        card.startAnimation(a);
    }

    public void colapse(final View card, final View textBox) {
        final int targetHeightCard = card.getHeight();
        final int targetHeightTextBox = textBox.getHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                card.getLayoutParams().height = targetHeightCard - (int) (CARD_INTIAL_SIZE / interpolatedTime);
                card.requestLayout();

                textBox.getLayoutParams().height = targetHeightTextBox - (int) ((CARD_INTIAL_SIZE) / interpolatedTime);
                textBox.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                expanded = false;
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (targetHeightCard / card.getContext().getResources().getDisplayMetrics().density));
        card.startAnimation(a);
    }

}
