package com.ford.navigation;

import android.content.Intent;
import android.os.Bundle;
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
        private final TextView descriptionTextView;
        private final TextView hopsCommaTextView;
        private final TextView modesCommaTextView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            sourceTextView = (TextView) v.findViewById(R.id.source_text);
            destinationTextView = (TextView) v.findViewById(R.id.source_duration);
            descriptionTextView = v.findViewById(R.id.detailed_description);
            hopsCommaTextView = v.findViewById(R.id.hops_csv);
            modesCommaTextView = v.findViewById(R.id.modes_csv);
        }

        public TextView getSourceTextView() {
            return sourceTextView;
        }

        public TextView getDestinationTextView() {
            return destinationTextView;
        }

        public TextView getModesTextView() {
            return modesCommaTextView;
        }

        public TextView getHopsTextView() {
            return hopsCommaTextView;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
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
        viewHolder.getDescriptionTextView().setText(mDataset.get(position).getHopsDetail());
        viewHolder.getHopsTextView().setText(mDataset.get(position).getHopsCommaSeparated());
        viewHolder.getModesTextView().setText(mDataset.get(position).getModesCommaSeparated());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TextView sourceTextView = (TextView) v.findViewById(R.id.detailed_description);
               TextView hops = (TextView) v.findViewById(R.id.hops_csv);
               TextView modes = (TextView) v.findViewById(R.id.modes_csv);

                   /* FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    ModalFragment yesnoDialog = new ModalFragment();
                    yesnoDialog.setCancelable(false);
                    yesnoDialog.setDialogTitle("Route Info");
                    yesnoDialog.setDialogContent(sourceTextView.getText().toString());
                    yesnoDialog.show(fragmentManager, "Yes/No Dialog");*/
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("hops", hops.getText().toString() );
                intent.putExtra("modes", modes.getText().toString());
                activity.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}