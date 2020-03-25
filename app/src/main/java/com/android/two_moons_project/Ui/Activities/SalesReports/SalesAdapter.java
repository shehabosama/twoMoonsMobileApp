package com.android.two_moons_project.Ui.Activities.SalesReports;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.model.SalesOperations;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {
    private SalesInterAction interAction;
    private Context context;
    private List<SalesOperations> list;

    public SalesAdapter(Context context, List<SalesOperations> list, SalesInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_display_sales, parent, false);

        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
        SalesOperations salesOperations = list.get(position);
        holder.productId.setText("رقم العمليه : "+salesOperations.getId());
        holder.productDate.setText("تاريخ العمليه : "+salesOperations.getDate_of_process());
        holder.productPrice.setText("السعر المنتج المباع : "+salesOperations.getPrice_of_product());
        holder.productName.setText("اسم المنتج : "+salesOperations.getProduct_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface SalesInterAction {

    }


    public class SalesViewHolder extends RecyclerView.ViewHolder {

        TextView productId,productName,productPrice,productDate;

        public SalesViewHolder(@NonNull View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.product_id);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.price_of_product);
            productDate = itemView.findViewById(R.id.date_of_process);
        }

        public void setListener() {

        }
    }
}
