package br.ufc.caps.recyclerView;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import br.ufc.caps.R;

/**
 * Created by messias on 6/13/16.
 *
 * @author Messias Lima
 */
public class LocalViewHolder extends RecyclerView.ViewHolder {

    private static final int CARD_INTIAL_SIZE = 300;
    private static final int TEXT_BOX_INTIAL_SIZE = 56;

    Context context;
    TextView textViewTitle, textViewText;
    ImageView backgroundImageView;
    Switch enabledSwitch;
    CardView cardView;
    LinearLayout textBar;
    boolean expanded = false;
    boolean busy = false;
    PopupMenu popupMenu;
    Toolbar cardToolbar;

    public LocalViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        textViewTitle = (TextView) itemView.findViewById(R.id.card_title_text);
        textViewText = (TextView) itemView.findViewById(R.id.card_text);
        backgroundImageView = (ImageView) itemView.findViewById(R.id.card_background_image);
        //enabledSwitch = (Switch) itemView.findViewById(R.id.card_switcher);
        cardView = (CardView) itemView.findViewById(R.id.card);
        textBar = (LinearLayout) itemView.findViewById(R.id.card_text_bar);
        cardToolbar = (Toolbar) itemView.findViewById(R.id.card_toobar);


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

        cardToolbar.inflateMenu(R.menu.menu_card);

    }

    public void expand() {
        if (!busy) {

            final int targetHeightCard = cardView.getHeight();
            final int targetHeightTextBox = textBar.getHeight();
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    cardView.getLayoutParams().height = targetHeightCard + (int) (targetHeightCard / 2 * interpolatedTime);
                    cardView.requestLayout();
                    textBar.getLayoutParams().height = targetHeightTextBox + (int) ((targetHeightCard / 2) * interpolatedTime);
                    textBar.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    expanded = true;
                    return true;
                }
            };
            // 1dp/ms
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    busy = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    busy = false;
                    cardToolbar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            a.setDuration((int) (targetHeightCard / cardView.getContext().getResources().getDisplayMetrics().density));
            cardView.startAnimation(a);
        }
    }

    public void colapse() {
        if (!busy) {
            final int targetHeightCard = cardView.getHeight();
            final int targetHeightTextBox = textBar.getHeight();
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    cardView.getLayoutParams().height = targetHeightCard - (int) (CARD_INTIAL_SIZE / 2 * interpolatedTime);
                    cardView.requestLayout();

                    textBar.getLayoutParams().height = targetHeightTextBox - (int) ((CARD_INTIAL_SIZE / 2) * interpolatedTime);
                    textBar.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    expanded = false;
                    return true;
                }
            };
            // 1dp/ms
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    busy = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    busy = false;
                    cardToolbar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            a.setDuration((int) (targetHeightCard / cardView.getContext().getResources().getDisplayMetrics().density));
            cardView.startAnimation(a);
        }
    }
}
