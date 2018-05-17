package pt.ulisboa.tecnico.cmu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pt.ulisboa.tecnico.cmu.DataObjects.Location;
import java.util.ArrayList;

public class CustomLocationListAdapter extends ArrayAdapter<Location> {

  public CustomLocationListAdapter(Context context, ArrayList<Location> locations) {
    super(context, R.layout.custom_location_row, locations);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    View customView = layoutInflater.inflate(R.layout.custom_location_row, parent, false);

    Location item = getItem(position);
    TextView textLocationName = (TextView) customView.findViewById(R.id.text_location_name);
    textLocationName.setText(item.getName());

    return customView;
  }
}
