package nightshop.debuck.info.nightshop.Tools;

/**
 * Created by steeves on 15/11/2017.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import nightshop.debuck.info.nightshop.AppClass.Building;
import nightshop.debuck.info.nightshop.R;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.MyViewHolder> {

    private List<Building> buildingsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, status;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_building);
            description = (TextView) view.findViewById(R.id.distance);
            status = (TextView) view.findViewById(R.id.building_status);
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
        Building building = buildingsList.get(position);
        holder.name.setText(building.getName());
        holder.description.setText(building.getDescription());
        holder.status.setText("OPEN - CLOSE");
    }

    @Override
    public int getItemCount() {
        return buildingsList.size();
    }
}
