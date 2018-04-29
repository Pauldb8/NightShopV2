package nightshop.debuck.info.nightshop.Tools;

/**
 * Created by steeves on 15/11/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import nightshop.debuck.info.nightshop.AppClass.Building;
import nightshop.debuck.info.nightshop.R;

import java.util.List;
import java.util.Locale;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.MyViewHolder> {

    private List<Building> buildingsList;
    public RelativeLayout displayDistance;
    public LinearLayout mBuilding;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, description, status, hours, address, distance;
        private Button bt_go;
        private Context context;



        public MyViewHolder(View view) {
            super(view);

            displayDistance = (RelativeLayout) view.findViewById(R.id.display_distance);
            mBuilding = (LinearLayout) view.findViewById(R.id.building);

            name = (TextView) view.findViewById(R.id.tv_name);
            description = (TextView) view.findViewById(R.id.tv_subtitle);
            status = (TextView) view.findViewById(R.id.tv_status);
            hours = (TextView) view.findViewById(R.id.tv_hours);
            address = (TextView) view.findViewById(R.id.tv_address);
            distance = (TextView) view.findViewById(R.id.tv_distance);
            bt_go =(Button) view.findViewById(R.id.bt_go);


        }
    }


    public BuildingAdapter(List<Building> buildingsList) {
        this.buildingsList = buildingsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.building_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", building.getLat(), building.getLng());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    v.getContext().startActivity(intent);
                }
            });




        }




    }



    @Override
    public int getItemCount() {
        return buildingsList.size();
    }


}
