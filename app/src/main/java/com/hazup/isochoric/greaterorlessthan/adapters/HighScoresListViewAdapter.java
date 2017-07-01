package com.hazup.isochoric.greaterorlessthan.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minir on 20/05/2017.
 */

public class HighScoresListViewAdapter extends ArrayAdapter<Score> {

    private List<Score> allScores = new ArrayList<>();
    private Activity context;

    private static class ScoresViewHolder {

        ScoresViewHolder() {
//            Blank constructor
        }

        TextView placeTextView;
        TextView playerNameTextView;
        TextView playerPointsTextView;
    }

    public HighScoresListViewAdapter(@NonNull Activity context, List<Score> scores) {
        super(context, R.layout.highscore_list_template, scores);
        this.allScores = scores;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View returnedView;
        Score score = allScores.get(position);
        ScoresViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ScoresViewHolder();
            View rootTemplateView = context.getLayoutInflater().inflate(R.layout.highscore_list_template, parent, false);
            viewHolder.placeTextView = (TextView) rootTemplateView.findViewById(R.id.template_place_textview);
            viewHolder.playerNameTextView = (TextView) rootTemplateView.findViewById(R.id.template_username_textview);
            viewHolder.playerPointsTextView = (TextView) rootTemplateView.findViewById(R.id.template_score_textview);
            returnedView = rootTemplateView;
            rootTemplateView.setTag(viewHolder);
        } else {
            viewHolder = (ScoresViewHolder) convertView.getTag();
            returnedView = convertView;
        }
        String placeStr;
        switch (position) {
            case 0:
                placeStr = "1st";
                break;
            case 1:
                placeStr = "2nd";
                break;
            case 2:
                placeStr = "3rd";
                break;
            default:
                placeStr = position + 1 + "th";
        }
        viewHolder.placeTextView.setText(placeStr);
        viewHolder.playerNameTextView.setText(score.getPlayer());
        viewHolder.playerPointsTextView.setText(score.getPoints());
        return returnedView;
    }
}
