package transformer.com.karan.dev.transform.transformer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import transformer.com.karan.dev.transform.transformer.activity.R;
import transformer.com.karan.dev.transform.transformer.holder.MyViewHolder;
import transformer.com.karan.dev.transform.transformer.model.Transformer;

/**
 * Created by KaranDeepSingh
 *
 * @author KaranDeepSingh
 */


public class TransformerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<Transformer> transformersList;


    public TransformerAdapter(ArrayList<Transformer> transformersList) {
        this.transformersList = transformersList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row_transformer, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Transformer transformer = transformersList.get(position);
        holder.name.setText(transformer.getName().toString());
        holder.overall.setText(String.valueOf(transformer.getOverall()));
        holder.rank.setText(String.valueOf(transformer.getRank()));
    }

    @Override
    public int getItemCount() {
        return transformersList.size();
    }

}
