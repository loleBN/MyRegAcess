package com.tcc.lolebn.myregacess.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Tag;
import com.tcc.lolebn.myregacess.basics.Utils;

import java.util.List;

/**
 * Created by lolebn on 04/07/17.
 */

public class AdapterRegCHPorTag extends BaseAdapter {
    private final List<Tag> registros;
    private final Activity act;

    public AdapterRegCHPorTag(List<Tag> listReg, Activity act) {
        this.registros = listReg;
        this.act = act;
    }

    @Override
    public int getCount() {
        return registros.size();
    }

    @Override
    public Object getItem(int position) {
        return registros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.lista_registros_ch, parent, false);
        Tag reg = registros.get(position);

        //pegando as referências das Views
        TextView nome = (TextView)
                view.findViewById(R.id.lista_reg_bych_nome);
        TextView ch = (TextView)
                view.findViewById(R.id.lista_reg_bych_ch);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_reg_bych_imagem);

        //populando as Views
        nome.setText(reg.getNome());
        ch.setText("carga horaria: "+reg.getFrequencia_semanal());
        imagem.setImageResource(R.drawable.avatar);

        return view;
    }
}
