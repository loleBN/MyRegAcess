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
 * Created by lolebn on 08/06/17.
 */

public class AdapterRegPorData extends BaseAdapter {
    private final List<RegIN> registros;
    private final Activity act;

    public AdapterRegPorData(List<RegIN> listReg, Activity act) {
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
                .inflate(R.layout.lista_registros_data, parent, false);
        RegIN reg = registros.get(position);

        //pegando as referências das Views
        TextView nome = (TextView)
                view.findViewById(R.id.lista_reg_bydate_nome);
        TextView hora = (TextView)
                view.findViewById(R.id.lista_reg_bydate_hora);
        TextView data = (TextView)
                view.findViewById(R.id.lista_reg_bydate_data);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_reg_bydate_imagem);

        //populando as Views
        nome.setText(reg.getNome());
        data.setText(Utils.getSoData(reg.getData_hora()));
        hora.setText(Utils.getSoHorario(reg.getData_hora()));
        imagem.setImageResource(R.drawable.avatar);

        if(reg.getStatus()==1) {
            view.setBackgroundColor(view.getResources().getColor(R.color.mediumSeaGreen));
        }else {
            view.setBackgroundColor(view.getResources().getColor(R.color.salmon));
        }
        return view;
    }
}
