package com.android.two_moons_project.Ui.Activities.AddDebtor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.two_moons_project.R;
import com.android.two_moons_project.common.model.Debtor;

import java.util.List;

public class DebtorAdapter extends BaseAdapter {

    private Context context;
    List<Debtor> listitems;

    public DebtorAdapter(Context context, List<Debtor> items){
        this.listitems=items;
        this.context = context;

    }

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int i) {
        return listitems.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       // LayoutInflater layoutInflater=LayoutInflater.from(context).getLayoutInflater();
         view = LayoutInflater.from(context).inflate(R.layout.spiner_row, viewGroup, false);
       // View view3=layoutInflater.inflate(R.layout.row_lay,null);
        final Debtor debtor = listitems.get(i);
        final TextView textView=view.findViewById(R.id.textname);
            textView.setText(debtor.getName());

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               if (tablesSpinnerInterAction!=null)
//                   tablesSpinnerInterAction.onClickTableItem(table);
//            }
//        });
        return view;
    }
}