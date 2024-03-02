package controles;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.Map;

public class AgeDistributionDialog {
    @FXML
    private ProgressBar progressBarLessThan12;

    @FXML
    private ProgressBar progressBar12To18;

    @FXML
    private ProgressBar progressBar19To30;

    @FXML
    private ProgressBar progressBarGreaterThan30;
    @FXML
    private Label labelLessThan12;

    @FXML
    private Label label12To18;

    @FXML
    private Label label19To30;

    @FXML
    private Label labelGreaterThan30;

    public void initData(Map<String, Double> agePercentages) {
        // Initialize progress bars with calculated percentages
        setProgressBarWidth(progressBarLessThan12, agePercentages.get("LessThan12"));
        setProgressBarWidth(progressBar12To18, agePercentages.get("12To18"));
        setProgressBarWidth(progressBar19To30, agePercentages.get("19To30"));
        setProgressBarWidth(progressBarGreaterThan30, agePercentages.get("GreaterThan30"));


    }
    private void setProgressBarWidth(ProgressBar progressBar, double percentage) {
        // Set the progress bar's progress directly to the percentage
        progressBar.setProgress(percentage / 100.0);
    }

}
