package com.example.online_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<ItemsDomain> listItemSelected;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<ItemsDomain> listItemSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartBinding binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.binding.titleTxt.setText(listItemSelected.get(position).getTitle());
        holder.binding.feeEachltem.setText("$" + listItemSelected.get(position).getPiece());
        holder.binding.totalEachltem.setText("$" + Math.round((listItemSelected.get(position).getNumberInCart() * listItemSelected.get(position).getPiece())));
        holder.binding.numberItemTxt.setText(String.valueOf(listItemSelected.get(position).getNumberInCart()));

        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getPicUrl().get(0))
                .apply(RequestOptions.centerCropTransform())
                .into(holder.binding.pic);

        holder.binding.plusCartBtn.setOnClickListener(v -> {
            managmentCart.plusItems(listItemSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            });
        });

        holder.binding.minusCartBtn.setOnClickListener(v -> {
            managmentCart.minusItems(listItemSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            });
        });
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;

        public ViewHolder(ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
