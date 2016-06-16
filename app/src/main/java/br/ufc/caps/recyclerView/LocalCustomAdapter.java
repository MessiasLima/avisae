package br.ufc.caps.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.ufc.caps.R;
import br.ufc.caps.geofence.Local;

/**
 * Created by messias on 6/13/16.
 *
 * @author Messias Lima
 */
public class LocalCustomAdapter extends RecyclerView.Adapter<LocalViewHolder> {
    Context context;
    List<Local> locals;

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
    public void onBindViewHolder(LocalViewHolder holder, int position) {

        Local local = locals.get(position);

        holder.textViewTitle.setText(local.getNome());
        holder.textViewText.setText(local.getTexto());

        boolean enabled = false;
        if (local.getAtivo() == Local.VERDADEIRO) {
            enabled = true;
        }
        holder.enabledSwitch.setChecked(enabled);
    }

    @Override
    public int getItemCount() {
        return locals.size();
    }
}
