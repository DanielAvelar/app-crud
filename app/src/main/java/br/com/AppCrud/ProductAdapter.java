package br.com.AppCrud;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

import br.com.AppCrud.model.Produto;

public class ProductAdapter extends ArrayAdapter<Produto> {

    private Context mContext;
    private List<Produto> productList;

    public ProductAdapter(@NonNull Context context, @LayoutRes ArrayList<Produto> list) {
        super(context, 0, list);
        mContext = context;
        productList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        Produto currentProduct = productList.get(position);

        ImageView image = listItem.findViewById((R.id.imageView_image));

        Glide.with(mContext)
                .load(currentProduct.getUrlImage()) // Image URL
                .centerCrop() // Image scale type
                .crossFade()
                .override(800,500) // Resize image
                .placeholder(R.drawable.ic_launcher_background) // Place holder image
                .error(R.drawable.ic_launcher_foreground) // On error image
                .into(image); // ImageView to display image

        TextView name = listItem.findViewById(R.id.textView_name);
        name.setText(currentProduct.getName());

        //TextView description = listItem.findViewById(R.id.textView_description);
        //description.setText(currentProduct.getDescription());

        //TextView amount = listItem.findViewById(R.id.textView_amount);
        //amount.setText(currentProduct.getAmount());

        //TextView idProduct = listItem.findViewById(R.id.textView_idProduct);
        //idProduct.setText(currentProduct.getIdProduct());

        //TextView category = listItem.findViewById(R.id.textView_category);
        //category.setText(currentProduct.getCategory());

        //TextView price = listItem.findViewById(R.id.textView_price);
        //price.setText(currentProduct.getPrice());

        return listItem;
    }
}
