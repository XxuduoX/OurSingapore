package sg.edu.rp.c346.id20007998.oursingapore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;

public class ModifyActivity extends AppCompatActivity {
    EditText edName,edDes,edSquare,edID;
    Button btnUpdate,btnDelete,btnCancel;
    RatingBar rbStars;
    island data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        edName=findViewById(R.id.etName);
        edDes=findViewById(R.id.etDescription);
        edSquare=findViewById(R.id.etSquare);
        edID=findViewById(R.id.etID);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        btnCancel=findViewById(R.id.btnCancel);
        rbStars=findViewById(R.id.ratingStars_modify);

        Intent i = getIntent();
        data = (island) i.getSerializableExtra("data");
        edID.setText(data.getId()+"");
        edName.setText(data.getName());
        edDes.setText(data.getDescription());
        edSquare.setText(data.getSquare()+"");
        rbStars.setRating(data.getStars());

        edID.setEnabled(false);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                data.setName(edName.getText().toString().trim());
                data.setDescription(edDes.getText().toString().trim());
                int square=Integer.valueOf(edSquare.getText().toString().trim());
                data.setSquare(square);

                float stars=rbStars.getRating();
                data.setStars(stars);
                dbh.updateIsland(data);
                dbh.close();

                finish();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder=new AlertDialog.Builder(ModifyActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the island "+"\n"+data.getName());
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Cancel",null);

                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ModifyActivity.this);
                        dbh.deleteIsland(data.getId());

                        finish();
                    }
                });
                AlertDialog myDialog=myBuilder.create();
                myDialog.show();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder=new AlertDialog.Builder(ModifyActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the change? ");
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Do Not Discard",null);

                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog myDialog=myBuilder.create();
                myDialog.show();

            }
        });
    }
}