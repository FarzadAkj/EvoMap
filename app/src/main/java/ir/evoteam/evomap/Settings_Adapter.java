package ir.evoteam.evomap;
/**
 * Created by root on 3/28/17.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Settings_Adapter extends BaseAdapter {

    private Context context;
    private List<String> Settings_list;

    public Settings_Adapter(){

    }


    public Settings_Adapter(Context context, List<String> settings_list) {
        this.context = context;
        Settings_list=settings_list;

    }

    @Override
    public int getCount() {
        return Settings_list.size();
    }

    @Override
    public Object getItem(int position) {
        return Settings_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.settings_item_layout, viewGroup, false);

        TextView settings_item = (TextView) view.findViewById(R.id.settings_item);

        settings_item.setText(Settings_list.get(position).toString());


        return view;
    }
}