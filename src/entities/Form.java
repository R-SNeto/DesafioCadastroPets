package entities;

import java.util.ArrayList;
import java.util.List;

public class Form {

    //Class responsible for store all questions found in the forms.txt

    private List<String> questionList = new ArrayList<>();

    public void addQuestion(String question){
        if (!questionList.contains(question)) {
            questionList.add(question);
        }
    }

    public String getQuestion(int i) {
        return questionList.get(i);
    }

    public int getQuestionListSize(){
        return questionList.size();
    }


}
