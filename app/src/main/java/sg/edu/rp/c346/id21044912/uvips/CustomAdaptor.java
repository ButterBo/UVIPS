package sg.edu.rp.c346.id21044912.uvips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdaptor extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<UVLight> alUV;

    public CustomAdaptor(Context context, int resource, ArrayList<UVLight> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        alUV = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvValue = rowView.findViewById(R.id.textViewValue);
        TextView tvTime = rowView.findViewById(R.id.textViewTime);

        UVLight currentItem = alUV.get(position);

        tvValue.setText(currentItem.getValue()+"");
        tvTime.setText(currentItem.getTimestamp());

        return rowView;
    }
}
