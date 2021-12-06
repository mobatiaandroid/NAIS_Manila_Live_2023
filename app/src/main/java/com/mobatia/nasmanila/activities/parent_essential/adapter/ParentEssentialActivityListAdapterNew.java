package com.mobatia.nasmanila.activities.parent_essential.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.parent_essentials.model.ParentEssentialsModel;

import java.util.ArrayList;

/**
 * Created by gayatri on 23/3/17.
 */
public class ParentEssentialActivityListAdapterNew extends RecyclerView.Adapter<ParentEssentialActivityListAdapterNew.MyViewHolder> {

    private Context mContext;
    private ArrayList<ParentEssentialsModel> mnNewsLetterModelArrayList;
    String dept;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIcon;
        TextView  pdfTitle;
        public MyViewHolder(View view) {
            super(view);
            imageIcon = (ImageView) view.findViewById(R.id.imageIcon);
              pdfTitle = (TextView) view.findViewById(R.id.pdfTitle);



        }
    }


    public ParentEssentialActivityListAdapterNew(Context mContext, ArrayList<ParentEssentialsModel> mnNewsLetterModelArrayList) {
        this.mContext = mContext;
        this.mnNewsLetterModelArrayList = mnNewsLetterModelArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_pdf_adapter_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        holder.submenu.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());
        holder.pdfTitle.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());

        if (mnNewsLetterModelArrayList.get(position).getFilename().endsWith(".pdf")) {
            holder.imageIcon.setBackgroundResource(R.drawable.pdfdownloadbutton);
        }
        else
        {
            holder.imageIcon.setBackgroundResource(R.drawable.webcontentviewbutton);

        }

    }


    @Override
    public int getItemCount() {
        return mnNewsLetterModelArrayList.size();
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
