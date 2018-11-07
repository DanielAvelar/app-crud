package br.com.AppCrud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.AppCrud.model.Produto;

public class ProductAdapter extends ArrayAdapter<Produto> {
    private List<Produto> productList;

    public ProductAdapter(Context context, int textViewResourceId, List<Produto> items) {
        super(context, textViewResourceId, items);
        this.productList = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            Context ctx = getContext();
            LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
        }
        Produto produto = productList.get(position);
        if (produto != null) {
            ImageView image = v.findViewById((R.id.imageView_image));

            Glide.with(getContext())
                    .load(produto.getUrlImage()) // Image URL
                    .centerCrop() // Image scale type
                    .crossFade()
                    .override(100, 100) // Resize image
                    .placeholder(R.drawable.ic_launcher_background) // Place holder image
                    .error(R.drawable.ic_launcher_foreground) // On error image
                    .into(image); // ImageView to display image

            ((TextView) v.findViewById(R.id.textView_name)).setText(produto.getName());
            ((TextView) v.findViewById(R.id.textView_description)).setText(produto.getDescription());
        }
        return v;
    }
}
