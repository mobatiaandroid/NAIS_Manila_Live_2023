package com.mobatia.nasmanila.activities.cca.adapter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.model.CCAActivityModel;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class CCAsActivityCategoryItemAdapter extends RecyclerView.Adapter<CCAsActivityCategoryItemAdapter.MyViewHolder> {

    Context mContext;
    GridLayoutManager recyclerViewLayoutManager;
    ArrayList<CCAActivityModel> mCCAmodelArrayList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTxtView;
        ImageView bottomDivider;
        ImageView topDivider;


        public MyViewHolder(View view) {
            super(view);

            listTxtView = (TextView) view.findViewById(R.id.textViewCCAItem);
            bottomDivider =  view.findViewById(R.id.bottomDivider);
            topDivider =  view.findViewById(R.id.topDivider);


        }
    }

    public CCAsActivityCategoryItemAdapter(Context mContext, ArrayList<CCAActivityModel> mCcaArrayList) {
        this.mContext = mContext;
        this.mCCAmodelArrayList=new ArrayList<>();
        this.mCCAmodelArrayList = mCcaArrayList;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cca_category_item ,parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.listTxtView.setText(mCCAmodelArrayList.get(position).getItem_name());
      /*  if (position!=(mCCAmodelArrayList.size()-1))
        {
            holder.bottomDivider.setVisibility(View.VISIBLE);
            holder.topDivider.setVisibility(View.GONE);
        }
        else
        {
            holder.bottomDivider.setVisibility(View.GONE);
            holder.topDivider.setVisibility(View.VISIBLE);

        }*/
        holder.bottomDivider.setVisibility(View.VISIBLE);
        holder.topDivider.setVisibility(View.GONE);

    }


    @Override
    public int getItemCount() {
//       System.out.println("Adapter---size" + mCcaArrayList.size());

        return mCCAmodelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
