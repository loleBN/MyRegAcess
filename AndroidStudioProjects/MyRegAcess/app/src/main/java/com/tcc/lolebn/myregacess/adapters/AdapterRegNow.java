package com.tcc.lolebn.myregacess.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Utils;

import java.util.List;

/**
 * Created by lolebn on 07/06/17.
 */

public class AdapterRegNow extends BaseAdapter {

    private final List<RegIN> registros;
    private final Activity act;

    public AdapterRegNow(List<RegIN> listReg, Activity act) {
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
                .inflate(R.layout.lista_registros_now, parent, false);
        RegIN reg = registros.get(position);

        //pegando as referências das Views
        TextView nome = (TextView)
                view.findViewById(R.id.lista_registro_now_nome);
        TextView entrada = (TextView)
                view.findViewById(R.id.lista_registro_now_hora);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_registro_now_imagem);
        ImageView img_entrada = (ImageView)
                view.findViewById(R.id.img_registro_now_entrada);

        //populando as Views
        nome.setText(reg.getNome());
        entrada.setText(Utils.getSoHorario(reg.getData_hora()));
        imagem.setImageResource(R.drawable.avatar);
        img_entrada.setImageResource(R.drawable.icon_in);

        return view;
    }
}
