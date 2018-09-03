package transformer.com.karan.dev.transform.transformer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import transformer.com.karan.dev.transform.transformer.activity.R;

/**
 * Created by KaranDeepSingh
 *
 * @author KaranDeepSingh
 */


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name, overall, rank;

    public MyViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tv_name_trans);
        overall = (TextView) itemView.findViewById(R.id.tv_overall);
        rank = (TextView) itemView.findViewById(R.id.tv_rank);
    }
}
