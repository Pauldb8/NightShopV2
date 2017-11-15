package nightshop.debuck.info.nightshop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import nightshop.debuck.info.nightshop.AppClass.Building;
import nightshop.debuck.info.nightshop.Tools.BuildingAdapter;


public class MainActivity extends AppCompatActivity {

    private List<Building> buildingList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BuildingAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new BuildingAdapter(buildingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        PrepareBuildingData();


    }


    private void PrepareBuildingData() {
        Building building = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DESTINATION",1.2,2.4);
        buildingList.add(building);

        Building building2 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building2);

        Building building3 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building3);

        Building building4 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building4);

        Building building5 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building5);

        Building building6 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building6);

        Building building7 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building7);


        Building building8 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building8);

        Building building9 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building9);

        Building building10 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building10);

        Building building11 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building11);

        Building building12 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building12);

        Building building13 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building13);

        Building building14 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building14);

        Building building15 = new Building(1, "TEST ZARA", "TEST ADRESS","TEST DES",1.2,2.4);
        buildingList.add(building15);


        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
