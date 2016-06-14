package br.ufc.caps.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    RelativeLayout backgroundImageView;
    Switch enabledSwitch;
    public LocalViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        textViewTitle = (TextView) itemView.findViewById(R.id.card_title_text);
        textViewText = (TextView) itemView.findViewById(R.id.card_text);
        backgroundImageView = (RelativeLayout) itemView.findViewById(R.id.card_background_layout);
        enabledSwitch = (Switch) itemView.findViewById(R.id.card_switcher);
    }
}
