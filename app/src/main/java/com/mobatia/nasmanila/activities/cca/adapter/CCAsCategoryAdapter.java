package com.mobatia.nasmanila.activities.cca.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.model.CCACategoryModel;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class CCAsCategoryAdapter extends RecyclerView.Adapter<CCAsCategoryAdapter.MyViewHolder> {

    Context mContext;
    GridLayoutManager recyclerViewLayoutManager;
    ArrayList<CCACategoryModel> mCCAmodelArrayList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTxtView;
        RecyclerView recycler_review;


        public MyViewHolder(View view) {
            super(view);

            listTxtView = (TextView) view.findViewById(R.id.textViewCCAaItem);
            recycler_review = (RecyclerView) view.findViewById(R.id.recycler_view_adapter_cca);


        }
    }

    public CCAsCategoryAdapter(Context mContext, ArrayList<CCACategoryModel> mCcaArrayList) {
        this.mContext = mContext;
        this.mCCAmodelArrayList=new ArrayList<>();
        this.mCCAmodelArrayList = mCcaArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cca_category_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.listTxtView.setText(mCCAmodelArrayList.get(position).getName());
        holder.recycler_review.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(mContext, 1);
//        int spacing = 5; // 50px
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, spacing);
//
//        holder.recycler_review.addItemDecoration(itemDecoration);
//        holder.recycler_review.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));

        holder.recycler_review.setLayoutManager(recyclerViewLayoutManager);
        CCAsActivityCategoryItemAdapter mCCAsCategoryAdapter=new CCAsActivityCategoryItemAdapter(mContext,mCCAmodelArrayList.get(position).getmCCAActivityModelList());
        holder.recycler_review.setAdapter(mCCAsCategoryAdapter);
        holder.recycler_review.addOnItemTouchListener(new RecyclerItemListener(mContext, holder.recycler_review,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int pos) {
                        showDetails(mCCAmodelArrayList.get(position).getmCCAActivityModelList().get(pos).getItem_name(),mCCAmodelArrayList.get(position).getmCCAActivityModelList().get(pos).getDescription());
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
    }


    @Override
    public int getItemCount() {
//       System.out.println("Adapter---size" + mCcaArrayList.size());

        return mCCAmodelArrayList.size();
    }
    private void showDetails(String eventNameStr, String eventDescriptionStr) {
        final Dialog dialog = new Dialog(mContext, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custome_layout_alert_scroll);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        // set the custom dialog components - edit text, button
        TextView eventName = (TextView) dialog.findViewById(R.id.eventName);
        TextView eventDescription = (TextView) dialog.findViewById(R.id.eventDescription);
        eventName.setText(eventNameStr);
        eventDescription.setText(eventDescriptionStr);
        Button dismiss = (Button) dialog.findViewById(R.id.dismiss);

        // if button is clicked, close the custom dialog


        // if button is clicked, close the custom dialog

        dismiss.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {

                                           dialog.dismiss();
                                       }
                                   }

        );
        dialog.show();
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
