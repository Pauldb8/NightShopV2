package nightshop.debuck.info.nightshop.Tools;

/**
 * Created by steeves on 15/11/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import nightshop.debuck.info.nightshop.AppClass.Building;
import nightshop.debuck.info.nightshop.MainActivity;
import nightshop.debuck.info.nightshop.R;

import java.util.List;
import java.util.Locale;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.MyViewHolder> {

    private  Context mContext;
    private List<Building> buildingsList;
    public RelativeLayout displayDistance;
    public LinearLayout mBuilding;


    public class MyViewHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener {

        private TextView name, description, status, hours, address, distance;
        private SeekBar skDistance;
        private TextView tv_change_distance;
        private Button bt_go;

        public MyViewHolder(View view) {
            super(view);


            displayDistance = (RelativeLayout) view.findViewById(R.id.display_distance);
            mBuilding = (LinearLayout) view.findViewById(R.id.building);
            tv_change_distance = (TextView) view.findViewById(R.id.tv_change_distance);
            skDistance = (SeekBar) view.findViewById(R.id.sk_distance);
            skDistance.setProgress(((MainActivity) itemView.getContext()).mDistance );


            Log.d("BuildingActivity", "Inflating view.");

            tv_change_distance.setText(((MainActivity) view.getContext()).mDistance + "km");
            skDistance.setOnSeekBarChangeListener(this);
            Log.d("BuildingActivity", "adding listener.");

            name = (TextView) view.findViewById(R.id.tv_name);
            description = (TextView) view.findViewById(R.id.tv_subtitle);
            status = (TextView) view.findViewById(R.id.tv_status);
            hours = (TextView) view.findViewById(R.id.tv_hours);
            address = (TextView) view.findViewById(R.id.tv_address);
            distance = (TextView) view.findViewById(R.id.tv_distance);
            bt_go =(Button) view.findViewById(R.id.bt_go);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            ((MainActivity) itemView.getContext()).mDistance = i;
            tv_change_distance.setText(((MainActivity) itemView.getContext()).mDistance + "km");

            Log.d("BuildingAdapter", "Changing distance");
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            ((MainActivity) itemView.getContext()).getBuildings(((MainActivity) itemView.getContext()).lat,
                    ((MainActivity) itemView.getContext()).lng, ((MainActivity) itemView.getContext()).mDistance);
        }
    }


    public BuildingAdapter(List<Building> buildingsList) {
        this.buildingsList = buildingsList;
    }

    public BuildingAdapter(List<Building> buildingsList, Context mContext){
        this.buildingsList = buildingsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.building_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Building building = buildingsList.get(position);
        if(building.getId() == 999999){
            displayDistance.setVisibility(View.VISIBLE);
            mBuilding.setVisibility(View.GONE);

        }else{
            displayDistance.setVisibility(View.GONE);
            mBuilding.setVisibility(View.VISIBLE);
            holder.name.setText(building.getName());
            holder.description.setText(building.getDescription());
            holder.status.setText("OPEN");
            holder.hours.setText(building.getHoursFormatted());
            holder.address.setText(building.getTwoLinesAddress());
            holder.distance.setText("" + building.getDistance() + " km");

            holder.bt_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", building.getLat(), building.getLng());
                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    //v.getContext().startActivity(intent);

                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+building.getName()+","+building.getAddress());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    v.getContext().startActivity(mapIntent);
                }
            });
            /* Last card will get a margin bottom for ads */
            if(position == getItemCount()-1){
                RecyclerView.LayoutParams lp =  (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                Resources r = holder.itemView.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        50,
                        r.getDisplayMetrics()
                );
                lp.setMargins(0, 0, 0 , px);
            }
        }

    }


    @Override
    public int getItemCount() {
        return buildingsList.size();
    }

}
