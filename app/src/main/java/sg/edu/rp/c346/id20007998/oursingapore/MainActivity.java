package sg.edu.rp.c346.id20007998.oursingapore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnFilter,btnInsertPage,btnDisplayAll;
    ListView lv;
    ArrayList<island> islandList;
    CustomerAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFilter=findViewById(R.id.btnFilter);
        btnDisplayAll=findViewById(R.id.btnDisplayAll);
        btnInsertPage=findViewById(R.id.btnInsertPage);
        lv=findViewById(R.id.lv);

        islandList=new ArrayList<island>();
        ca = new CustomerAdapter(this, R.layout.row, islandList);
        lv.setAdapter(ca);

        DBHelper dbh = new DBHelper(MainActivity.this);

        islandList.clear();
        islandList.addAll(dbh.getAllIsland());
        Log.d("Debug",islandList.size()+"");
        ca.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                island data = islandList.get(position);
                Intent i = new Intent(MainActivity.this,
                        ModifyActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getIslandByFilter());
                ca.notifyDataSetChanged();

            }
        });

        btnDisplayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIsland());
                ca.notifyDataSetChanged();

            }
        });

        btnInsertPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog= inflater.inflate(R.layout.insert,null);

                final EditText edName=viewDialog.findViewById(R.id.etName),
                        edDes=viewDialog.findViewById(R.id.etDescription),
                edSquare=viewDialog.findViewById(R.id.etSquare);

                final RatingBar rbStars=viewDialog.findViewById(R.id.ratingStars_insert);

                AlertDialog.Builder myBuilder=new AlertDialog.Builder(MainActivity.this);

                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Insert Island");

                myBuilder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String data_name = edName.getText().toString();
                        String data_des=edDes.getText().toString();
                        int data_square=Integer.parseInt(edSquare.getText().toString());
                        float data_stars=0;
                        data_stars=rbStars.getRating();

                        DBHelper dbh = new DBHelper(MainActivity.this);
                        long inserted_id = dbh.insertIsland(data_name,data_des,data_square,data_stars);

                        if (inserted_id != -1){
                            Toast.makeText(MainActivity.this, "Insert successful",
                                    Toast.LENGTH_SHORT).show();

                        }
                        islandList.clear();
                        islandList.addAll(dbh.getAllIsland());
                        ca.notifyDataSetChanged();
                    }

                });
                myBuilder.setNegativeButton("CANCEL",null);
                AlertDialog myDialog=myBuilder.create();
                myDialog.show();
            }

        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(MainActivity.this);
        islandList.clear();
        islandList.addAll(dbh.getAllIsland());
        ca.notifyDataSetChanged();
    }
}