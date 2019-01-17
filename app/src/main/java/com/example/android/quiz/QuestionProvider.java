package com.example.android.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuestionProvider {

    public List<Question> getQuestions() {

        List<Answer> ans = new ArrayList<>();
        ans.add(new Answer("szó"));
        ans.add(new Answer("ti"));
        ans.add(new Answer("lá", true));
        ans.add(new Answer("dó"));

        Question q = new Question("Mi a cisz, ha fisz a ré?", ans);
        List<Question> questions = new ArrayList<>();
        questions.add(q);

        List<Answer> ans1 = new ArrayList<>();
        ans1.add(new Answer("Régi dicsőségünk, hol késel az éji homályban?", true));
        ans1.add(new Answer("Régi dicsőségünk, hogy késel az éji homályban?"));
        ans1.add(new Answer("Régi dicsőségünk, halkések az éji homályban?"));
        ans1.add(new Answer("Régi dicsőségünk, hol vész el az éji homályban?"));
        Question q1 = new Question("Melyiket írta Vörösmarty Mihály?", ans1);
        questions.add(q1);
        return questions;
    }
}
