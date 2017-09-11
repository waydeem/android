package com.somecompanydomain.producthuntapplicationtest;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ProductViewHolder> {
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView productCard;
        TextView productName;
        TextView productDescription;
        TextView upvotes;
        ImageView thumbnail;

        ProductViewHolder(View itemView) {
            super(itemView);
            productCard = (CardView) itemView.findViewById(R.id.product_card);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productDescription = (TextView) itemView.findViewById(R.id.product_description);
            upvotes = (TextView) itemView.findViewById(R.id.upvotes);
        }
    }

    List<ProductCard> productCards;
    RecyclerViewAdapter(List<ProductCard> productCards) {
            this.productCards = productCards;
    }

    @Override
    public int getItemCount() {
        return productCards.size();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder productViewHolder, int i) {
        productViewHolder.thumbnail.setImageResource(productCards.get(i).getThumbnail());
        productViewHolder.productName.setText(productCards.get(i).getProductName());
        productViewHolder.productDescription.setText(productCards.get(i).getProductDescription());
        productViewHolder.upvotes.setText(productCards.get(i).getUpvotes());
    }
}
