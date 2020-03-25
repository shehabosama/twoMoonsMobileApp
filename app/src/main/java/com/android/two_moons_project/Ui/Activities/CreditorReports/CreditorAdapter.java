package com.android.two_moons_project.Ui.Activities.CreditorReports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.model.CreditorOperations;

import java.util.List;

public class CreditorAdapter extends RecyclerView.Adapter<CreditorAdapter.SalesViewHolder> {
    private CreditorsInterAction interAction;
    private Context context;
    private List<CreditorOperations> list;

    public CreditorAdapter(Context context, List<CreditorOperations> list, CreditorsInterAction interAction) {
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
        CreditorOperations creditorOperations = list.get(position);
        holder.productId.setText("رقم العمليه : "+creditorOperations.getId());
        holder.productDate.setText("تاريخ العمليه : "+creditorOperations.getCreate_at());
        if(creditorOperations.getOper_type() ==1){
            holder.productPrice.setText("اضافه ادانه جديد بمبلغ : "+creditorOperations.getThe_payment()+ "  "+ "مبلغ الادانه بعد الاضافه : "+creditorOperations.getRemaining());
        }else{
            holder.productPrice.setText("سداد ادانه جديده بمبلغ : "+creditorOperations.getThe_payment()+ "  "+ "مبلغ الادانه بعد السداد : "+creditorOperations.getRemaining());
        }
        holder.productName.setText("اسم الدائن : "+creditorOperations.getCreditor_name());
        if(creditorOperations.getOper_type() == 1){
            holder.oper_type.setText("نوع العمليه : اضافه ادانه");

        }else{
            holder.oper_type.setText("نوع العمليه : سداد ادانه");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface CreditorsInterAction {

    }


    public class SalesViewHolder extends RecyclerView.ViewHolder {

        TextView productId,productName,productPrice,productDate,oper_type;

        public SalesViewHolder(@NonNull View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.product_id);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.price_of_product);
            productDate = itemView.findViewById(R.id.date_of_process);
            oper_type = itemView.findViewById(R.id.oper_type);
        }

        public void setListener() {

        }
    }
}
