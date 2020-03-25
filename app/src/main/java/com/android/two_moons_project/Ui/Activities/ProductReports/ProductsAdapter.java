package com.android.two_moons_project.Ui.Activities.ProductReports;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.model.Products;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductAdapterViewHolder> {
    private ProductsInterAction interAction;
    private Context context;
    private List<Products> list;

    public ProductsAdapter(Context context, List<Products> list, ProductsInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public ProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_display_product, parent, false);

        return new ProductAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterViewHolder holder, int position) {
        Products products = list.get(position);
        holder.productName.setText("اسم المنتج : "+products.getProductName());
        holder.ProductPrice.setText("سعر المنتج : "+products.getProductPrice());
        holder.ProductCount.setText("عدد القطع الموجوده : "+products.getProductCount());
        holder.ProductOrgPrice.setText("سعر جمله المنتج : "+products.getProductOrginalPrice());
        holder.createAt.setText("تم وضعه في : "+products.getCeateAt());
        holder.ProductId.setText("رقم المنتج"+products.getId());
        holder.totalOrgPrice.setText("اجمالي سعر الجمله لعدد القطع : "+products.getTotalOrgPrice());
        holder.totalPriceOfProduct.setText("اجمال السعر العادي لعدد القطع : "+products.getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ProductsInterAction {

    }

    public class ProductAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView productName,ProductPrice,ProductCount,ProductOrgPrice,createAt,ProductId,totalPriceOfProduct,totalOrgPrice;
        public ProductAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            ProductPrice = itemView.findViewById(R.id.product_price);
            ProductCount = itemView.findViewById(R.id.product_count);
            ProductOrgPrice = itemView.findViewById(R.id.product_orginal_price);
            createAt = itemView.findViewById(R.id.create_at);
            ProductId = itemView.findViewById(R.id.id);
            totalPriceOfProduct = itemView.findViewById(R.id.total_price);
            totalOrgPrice = itemView.findViewById(R.id.total_org_price);

        }

        public void setListener() {

        }
    }
}
