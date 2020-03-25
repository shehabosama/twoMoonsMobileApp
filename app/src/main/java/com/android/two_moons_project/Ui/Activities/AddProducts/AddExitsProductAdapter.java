package com.android.two_moons_project.Ui.Activities.AddProducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.two_moons_project.R;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.android.two_moons_project.common.model.QrCodeModel.ProductQr;

import java.util.List;
public class AddExitsProductAdapter extends RecyclerView.Adapter<AddExitsProductAdapter.AddProductViewHolder> {
    private AddProductInterAction interAction;
    private Context context;
    private List<ProductQr> list;

    public AddExitsProductAdapter(Context context, List<ProductQr> list, AddProductInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public AddProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_display_add_order, parent, false);

        return new AddProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductViewHolder holder, int position) {
        ProductQr product = list.get(position);
        holder.ProductName.setText("اسم المنتج : "+product.getName());
        holder.ProductPrice.setText("سعر بيع المنتج : "+String.valueOf(product.getPrice()));
        holder.ProductId.setText("رقم المنتج : "+String.valueOf(product.getId()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface AddProductInterAction {

    }

    public class AddProductViewHolder extends RecyclerView.ViewHolder {

        private TextView ProductName,ProductId,ProductPrice;

        public AddProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.product_name);
            ProductId = itemView.findViewById(R.id.product_id);
            ProductPrice = itemView.findViewById(R.id.product_price);

        }

        public void setListener() {

        }
    }
}
