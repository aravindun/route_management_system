package com.ford.navigation;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<TravelOptions> mDataset;
    private RouteInfoActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView sourceTextView;
        private final TextView destinationTextView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            sourceTextView = (TextView) v.findViewById(R.id.source_text);
            destinationTextView = (TextView) v.findViewById(R.id.source_duration);
        }

        public TextView getSourceTextView() {
            return sourceTextView;
        }

        public TextView getDestinationTextView() {
            return destinationTextView;
        }
    }

    public MyAdapter(List<TravelOptions> myDataset, RouteInfoActivity routeInfoActivity) {
        mDataset = myDataset;
        this.activity = routeInfoActivity;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                   int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_navigation_info, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.getSourceTextView().setText(mDataset.get(position).getModeOfTransport());
        viewHolder.getDestinationTextView().setText(mDataset.get(position).getDuration());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TextView sourceTextView = (TextView) v.findViewById(R.id.source_text);

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    ModalFragment yesnoDialog = new ModalFragment();
                    yesnoDialog.setCancelable(false);
                    yesnoDialog.setDialogTitle("Route Info");
                    yesnoDialog.setDialogContent("source1 - source 2 by bus"+ "\n"+"source2 - source3 by taxi ");
                    yesnoDialog.show(fragmentManager, "Yes/No Dialog");


            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}