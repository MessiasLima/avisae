package br.ufc.caps.recyclerView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    Context context;
    TextView textViewTitle , textViewText;
    ImageView backgroundImageView;
    Switch enabledSwitch;
    CardView cardView;
    LinearLayout textBar;
    public LocalViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        textViewTitle = (TextView) itemView.findViewById(R.id.card_title_text);
        textViewText = (TextView) itemView.findViewById(R.id.card_text);
        backgroundImageView = (ImageView) itemView.findViewById(R.id.card_background_image);
        enabledSwitch = (Switch) itemView.findViewById(R.id.card_switcher);
        cardView = (CardView) itemView.findViewById(R.id.card);
        textBar = (LinearLayout) itemView.findViewById(R.id.card_text_bar);
    }
}
