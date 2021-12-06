package com.mobatia.nasmanila.activities.cca.adapter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.model.CCADetailModel;
import com.mobatia.nasmanila.activities.cca.model.WeekListModel;
import com.mobatia.nasmanila.appcontroller.AppController;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.activities.cca.model.CCAchoiceModel;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class CCAsActivityAdapter extends RecyclerView.Adapter<CCAsActivityAdapter.MyViewHolder> {
    //    ArrayList<String> mCcaArrayList;
//    ArrayList<String> mCcaArrayListAdapter;
//  /*  ArrayList<String> list = new ArrayList<String>() {{
//        add("A");
//        add("B");
//        add("C");
//    }};*/
    Context mContext;
    GridLayoutManager recyclerViewLayoutManager;
    GridLayoutManager recyclerViewLayoutManager2;
    ArrayList<CCADetailModel> mCCAmodelArrayList;
    ArrayList<CCAchoiceModel> mCCAchoiceModel2;
    ArrayList<CCAchoiceModel> mCCAchoiceModel1;
    ArrayList<WeekListModel> weekList;
    RecyclerView recyclerWeek;
    int dayPosition = 0;
    int count = 2;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTxtView;
        RecyclerView recycler_review;
        RecyclerView recycler_review2;


        public MyViewHolder(View view) {
            super(view);

            listTxtView = (TextView) view.findViewById(R.id.textViewCCAaItem);
            recycler_review = (RecyclerView) view.findViewById(R.id.recycler_view_adapter_cca);
            recycler_review2 = (RecyclerView) view.findViewById(R.id.recycler_view_adapter_cca2);


        }
    }

    public CCAsActivityAdapter(Context mContext, ArrayList<CCADetailModel> mCcaArrayList) {
        this.mContext = mContext;
        this.mCCAmodelArrayList = mCcaArrayList;
    }

    public CCAsActivityAdapter(Context mContext, ArrayList<CCAchoiceModel> mCCAchoiceModel1, ArrayList<CCAchoiceModel> mCCAchoiceModel2, int mdayPosition) {
        this.mContext = mContext;
        this.mCCAchoiceModel1 = mCCAchoiceModel1;
        this.mCCAchoiceModel2 = mCCAchoiceModel2;
        this.dayPosition = mdayPosition;
    }

    public CCAsActivityAdapter(Context mContext, ArrayList<CCAchoiceModel> mCCAchoiceModel1, ArrayList<CCAchoiceModel> mCCAchoiceModel2, int mdayPosition, ArrayList<WeekListModel> mWeekList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.mCCAchoiceModel1 = mCCAchoiceModel1;
        this.mCCAchoiceModel2 = mCCAchoiceModel2;
        this.dayPosition = mdayPosition;
        this.weekList = mWeekList;
        this.recyclerWeek=recyclerView;
        this.count = 2;

    }

    public CCAsActivityAdapter(Context mContext, int mcount) {
        this.mContext = mContext;
        this.mCCAchoiceModel1 = new ArrayList<>();
        this.mCCAchoiceModel2 = new ArrayList<>();
//        this.dayPosition=mdayPosition;
//        this.weekList=mWeekList;
        this.count = mcount;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cca_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (count != 0) {
            holder.recycler_review.setHasFixedSize(true);
            holder.recycler_review2.setHasFixedSize(true);
            recyclerViewLayoutManager = new GridLayoutManager(mContext, 1);
            recyclerViewLayoutManager2 = new GridLayoutManager(mContext, 1);
//        int spacing = 5; // 50px
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, spacing);
//
//        holder.recycler_review.addItemDecoration(itemDecoration);
            holder.recycler_review.setLayoutManager(recyclerViewLayoutManager);
            holder.recycler_review2.setLayoutManager(recyclerViewLayoutManager2);
//        mCcaArrayListAdapter=new ArrayList<>();
//        mCcaArrayListAdapter.add("CCA"+(position+1));
//        mCcaArrayListAdapter.add("CCA"+(position+2));
            if (position == 0) {
                if (mCCAchoiceModel1.size() > 0) {
                    if (mCCAchoiceModel2.size() <= 0) {
                        AppController.weekList.get(dayPosition).setChoiceStatus1("1");
                    }

                    holder.listTxtView.setText("Choose First Choice : ");// + (position + 1)
                    CCAsChoiceListActivityAdapter mCCAsActivityAdapter = new CCAsChoiceListActivityAdapter(mContext, mCCAchoiceModel1, dayPosition, weekList, 0,recyclerWeek);
                    holder.recycler_review.setAdapter(mCCAsActivityAdapter);
                }
            } else {
                if (mCCAchoiceModel2.size() > 0) {
                    if (mCCAchoiceModel1.size() <= 0) {
                        AppController.weekList.get(dayPosition).setChoiceStatus("1");
                    }
//                    holder.listTxtView.setText("Choice:" + (position + 1));
                    holder.listTxtView.setText("Choose Second Choice : ");// + (position + 1)

                    CCAsChoiceListActivityAdapter mCCAsActivityAdapter = new CCAsChoiceListActivityAdapter(mContext, mCCAchoiceModel2, dayPosition, weekList, 1,recyclerWeek);
                    holder.recycler_review2.setAdapter(mCCAsActivityAdapter);
                }
            }
            holder.recycler_review.addOnItemTouchListener(new RecyclerItemListener(mContext, holder.recycler_review,
                    new RecyclerItemListener.RecyclerTouchListener() {
                        public void onClickItem(View v, int pos) {
                            for (int i = 0; i < mCCAchoiceModel1.size(); i++) {
                                if (pos == i) {
                                    mCCAchoiceModel1.get(i).setStatus("1");
                                    System.out.println("Choice1:" + mCCAchoiceModel1.get(pos).getCca_item_name());

                                } else {
                                    mCCAchoiceModel1.get(i).setStatus("0");
                                    System.out.println("Choice1Else:" + mCCAchoiceModel1.get(pos).getCca_item_name());
                                }

                            }
                            CCAsChoiceListActivityAdapter mCCAsActivityAdapter = new CCAsChoiceListActivityAdapter(mContext, mCCAchoiceModel1, dayPosition, weekList, 0,recyclerWeek);
                            mCCAsActivityAdapter.notifyDataSetChanged();
                            holder.recycler_review.setAdapter(mCCAsActivityAdapter);
                        }

                        public void onLongClickItem(View v, int position) {
                            System.out.println("On Long Click Item interface");
                        }
                    }));
            holder.recycler_review2.addOnItemTouchListener(new RecyclerItemListener(mContext, holder.recycler_review2,
                    new RecyclerItemListener.RecyclerTouchListener() {
                        public void onClickItem(View v, int pos) {
                            for (int i = 0; i < mCCAchoiceModel2.size(); i++) {
                                if (pos == i) {
                                    mCCAchoiceModel2.get(i).setStatus("1");
                                } else {
                                    mCCAchoiceModel2.get(i).setStatus("0");

                                }

                            }
                            CCAsChoiceListActivityAdapter mCCAsActivityAdapter = new CCAsChoiceListActivityAdapter(mContext, mCCAchoiceModel2, dayPosition, weekList, 1,recyclerWeek);
                            mCCAsActivityAdapter.notifyDataSetChanged();
                            holder.recycler_review2.setAdapter(mCCAsActivityAdapter);

                        }

                        public void onLongClickItem(View v, int position) {
                            System.out.println("On Long Click Item interface");
                        }
                    }));
        }

    }


    @Override
    public int getItemCount() {
//       System.out.println("Adapter---size" + mCcaArrayList.size());

        return count;
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
