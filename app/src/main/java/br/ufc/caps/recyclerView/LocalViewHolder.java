package br.ufc.caps.recyclerView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import br.ufc.caps.R;

/**
 * Created by messias on 6/13/16.
 * @author Messias Lima
 */
public class LocalViewHolder extends RecyclerView.ViewHolder {

    private static final int CARD_INTIAL_SIZE = 300;
    private static final int TEXT_BOX_INTIAL_SIZE =  56;

    Context context;
    TextView textViewTitle , textViewText;
    ImageView backgroundImageView;
    Switch enabledSwitch;
    CardView cardView;
    LinearLayout textBar;
    boolean expanded  = false;

    public LocalViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        textViewTitle = (TextView) itemView.findViewById(R.id.card_title_text);
        textViewText = (TextView) itemView.findViewById(R.id.card_text);
        backgroundImageView = (ImageView) itemView.findViewById(R.id.card_background_image);
        enabledSwitch = (Switch) itemView.findViewById(R.id.card_switcher);
        cardView = (CardView) itemView.findViewById(R.id.card);
        textBar = (LinearLayout) itemView.findViewById(R.id.card_text_bar);

        backgroundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded) {
                    colapse();
                } else {
                    expand();
                }
            }
        });
    }

    public void expand() {
        final int targetHeightCard = cardView.getHeight();
        final int targetHeightTextBox = textBar.getHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                cardView.getLayoutParams().height = targetHeightCard + (int) (targetHeightCard * interpolatedTime);
                cardView.requestLayout();
                textBar.getLayoutParams().height = targetHeightTextBox + (int) ((targetHeightCard) * interpolatedTime);
                textBar.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                expanded = true;
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (targetHeightCard / cardView.getContext().getResources().getDisplayMetrics().density));
        cardView.startAnimation(a);
    }

    public void colapse() {
        final int targetHeightCard = cardView.getHeight();
        final int targetHeightTextBox = textBar.getHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                cardView.getLayoutParams().height = targetHeightCard - (int) (CARD_INTIAL_SIZE * interpolatedTime);
                cardView.requestLayout();

                textBar.getLayoutParams().height = targetHeightTextBox - (int) ((CARD_INTIAL_SIZE) * interpolatedTime);
                textBar.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                expanded = false;
                return true;
            }
        };
        // 1dp/ms
        a.setDuration((int) (targetHeightCard / cardView.getContext().getResources().getDisplayMetrics().density));
        cardView.startAnimation(a);
    }
}
