package easyquiz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ResultController {

    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    public ProgressIndicator correct_progress, wrong_progress;

    int correct;
    int wrong;

    @FXML
    private void initialize() {
        correct = QuizController.correct;
        wrong = QuizController.wrong;

        correcttext.setText("Réponses correctes : " + correct);
        wrongtext.setText("Réponses incorrectes: " + String.valueOf(QuizController.wrong));

        marks.setText(correct + "/10");
        float correctf = (float) correct/10;
        correct_progress.setProgress(correctf);

        float wrongf = (float) wrong/10;
        wrong_progress.setProgress(wrongf);


        markstext.setText(correct + " Notes obtenues");

        if (correct<2) {
            remark.setText("\n" + "Oh non! Vous avez échoué au quiz.Vérifiez vos résultats ici.");
        } else if (correct>=2 && correct<5) {
            remark.setText("\n" +
                    "Oops..! Vous avez obtenu moins de points. Consultez vos résultats ici.");
        } else if (correct>=5 && correct<=7) {
            remark.setText("\n" +
                    "Bien. Un peu plus d'amélioration pourrait vous aider à obtenir de meilleurs résultats. Vérifiez vos résultats ici.");
        } else if (correct==8 || correct==9) {
            remark.setText("bravo! . Vérifiez vos résultats ici.");
        } else if (correct==10) {
            remark.setText("parfait ! Vous avez réussi le quiz avec la note maximale. Continuez ainsi ! Vérifiez vos résultats ici.");
        }


    }

}
