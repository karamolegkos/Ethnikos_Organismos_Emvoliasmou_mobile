package com.example.ethinkosorganismosemboliasmou.ui.questions;

import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ethinkosorganismosemboliasmou.R;

public class QuestionsFragment extends Fragment {

    private QuestionsModel questionsModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questionsModel =
                new ViewModelProvider(this).get(QuestionsModel.class);
        View root = inflater.inflate(R.layout.fragment_questions, container, false);

        // An array holding all the questions
        TextView[] questions = {
                (TextView) root.findViewById(R.id.question1),
                (TextView) root.findViewById(R.id.question2),
                (TextView) root.findViewById(R.id.question3),
                (TextView) root.findViewById(R.id.question4),
                (TextView) root.findViewById(R.id.question5),
                (TextView) root.findViewById(R.id.question6),
                (TextView) root.findViewById(R.id.question7),
                (TextView) root.findViewById(R.id.question8),
                (TextView) root.findViewById(R.id.question9),
                (TextView) root.findViewById(R.id.question10)

        };

        // An array holding all the answers
        TextView[] answers = {
                (TextView) root.findViewById(R.id.answer1),
                (TextView) root.findViewById(R.id.answer2),
                (TextView) root.findViewById(R.id.answer3),
                (TextView) root.findViewById(R.id.answer4),
                (TextView) root.findViewById(R.id.answer5),
                (TextView) root.findViewById(R.id.answer6),
                (TextView) root.findViewById(R.id.answer7),
                (TextView) root.findViewById(R.id.answer8),
                (TextView) root.findViewById(R.id.answer9),
                (TextView) root.findViewById(R.id.answer10)

        };

        // Remove the answers
        for(int i=0; i<answers.length; i++){
            answers[i].setVisibility(View.GONE);
        }

        // for every question
        for(int i=0; i<questions.length; i++){
            // use the position of the question as a final value to use it in the OnClickListener
            final int j = i;
            // Put an onClickListener to remove or add the answer when clicked
            questions[j].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(answers[j].isShown()){
                        answers[j].setVisibility(View.GONE);
                    }
                    else{
                        answers[j].setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        /*
        final Button button = root.findViewById(R.id.button_questions);
        questionsModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                button.setText(s);
            }
        });
        */
        return root;
    }
}