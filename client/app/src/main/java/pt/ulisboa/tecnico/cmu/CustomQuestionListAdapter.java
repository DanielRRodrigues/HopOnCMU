package pt.ulisboa.tecnico.cmu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmu.DataObjects.Question;

import java.util.List;

/**
 * Created by danif on 16-May-18.
 */

public class CustomQuestionListAdapter extends ArrayAdapter<Question> {
    public CustomQuestionListAdapter(@NonNull Context context, List<Question> questions) {
        super(context, pt.ulisboa.tecnico.cmu.R.layout.custom_question_row, questions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(pt.ulisboa.tecnico.cmu.R.layout.custom_question_row, parent, false);

        Question item = getItem(position);
        TextView txtQuestion = (TextView) customView.findViewById(pt.ulisboa.tecnico.cmu.R.id.txtQuestion_CustomAdapter);
        txtQuestion.setText(item.getText());

        List<String> options = item.getOptions();
        RadioButton checkOption1 = (RadioButton) customView.findViewById(pt.ulisboa.tecnico.cmu.R.id.radioOption1);
        RadioButton checkOption2 = (RadioButton) customView.findViewById(pt.ulisboa.tecnico.cmu.R.id.radioOption2);
        RadioButton checkOption3 = (RadioButton) customView.findViewById(pt.ulisboa.tecnico.cmu.R.id.radioOption3);
        RadioButton checkOption4 = (RadioButton) customView.findViewById(pt.ulisboa.tecnico.cmu.R.id.radioOption4);

        checkOption1.setText(options.get(0));
        checkOption2.setText(options.get(1));
        checkOption3.setText(options.get(2));
        checkOption4.setText(options.get(3));

        return customView;
    }
}
