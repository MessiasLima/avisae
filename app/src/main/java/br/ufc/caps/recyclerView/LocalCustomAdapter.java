package br.ufc.caps.recyclerView;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.ufc.caps.R;
import br.ufc.caps.database.BD;
import br.ufc.caps.geofence.GeofencingManager;
import br.ufc.caps.geofence.Local;

/**
 * Created by messias on 6/13/16.
 *
 * @author Messias Lima
 */
public class LocalCustomAdapter extends RecyclerView.Adapter<LocalViewHolder> {

    private static final int CARD_INTIAL_SIZE = 300;
    private static final int TEXT_BOX_INTIAL_SIZE = 56;

    Context context;
    List<Local> locals;
    boolean expanded = false;
    private BD bd;

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

        holder.textViewText.setText(local.toString());

//        boolean enabled = false;
//        if (local.getAtivo() == Local.VERDADEIRO) {
//            enabled = true;
//        }
//        holder.enabledSwitch.setChecked(enabled);

        switch (local.getImagem()){
            case 1:
                holder.backgroundImageView.setImageResource(R.drawable.ic_1);
                break;
            case 2:
                holder.backgroundImageView.setImageResource(R.drawable.ic_2);
                break;
            case 3:
                holder.backgroundImageView.setImageResource(R.drawable.ic_3);
                break;
            case 4:
                holder.backgroundImageView.setImageResource(R.drawable.ic_4);
                break;
        }
        holder.backgroundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clique", local.getNome());

                if (holder.expanded) {
                    holder.colapse();
                } else {
                    holder.expand();
                }
            }
        });

        if (local.getAtivo() == Local.VERDADEIRO){
            holder.cardToolbar.getMenu().getItem(0).setTitle(R.string.desativar);
        }
        holder.cardToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_card_excluir){
                    local.excluir(context);
                }
                if (item.getItemId() == R.id.menu_card_ativar){
                    local.toggleAtivacao(context);
                }
                if (item.getItemId() == R.id.menu_card_editar){
                    local.editar(context);
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return locals.size();
    }

}
