package sg.edu.rp.c346.id20007998.oursingapore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomerAdapter extends ArrayAdapter {
    Context context;
    int layout_id;
    ArrayList<island> islandList;


    public CustomerAdapter(Context context, int resource, ArrayList<island> islandList) {
        super(context, resource,islandList);
        this.context=context;
        this.layout_id=resource;
        this.islandList=islandList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //R.Layout.row
        View rowView = inflater.inflate(layout_id,parent,false);
        //inflate the view for each row
        //line 32 and 34 will always exist and has no change to it.
        TextView tvName=rowView.findViewById(R.id.tvIslandName);
        TextView tvDescription=rowView.findViewById(R.id.tvIslandDes);
        TextView tvSquare=rowView.findViewById(R.id.tvIslandSquare);
        RatingBar rbStars=rowView.findViewById(R.id.ratingStarsListView);

        //Obtain UI component and do the necessary binding.

        island currentRow=islandList.get(position);
        Log.d("Debug",currentRow.getId()+"");
        tvName.setText(currentRow.getName());
        Integer intSqaure=currentRow.getSquare();
        String strSquare=intSqaure.toString();
        tvSquare.setText(strSquare);
        tvDescription.setText(currentRow.getDescription());
        rbStars.setRating(currentRow.getStars());
        //String stars="";


        return rowView;
    }
}
