package ClientPackage;

import Common.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class UserStatisticsController {

    private CatfishClient catfishClient;
    private User currentUser;
    private final ObservableList<PieChart.Data>pieChartData = FXCollections.observableArrayList();


    @FXML
    private Label HandleName;
    @FXML
    private Label AttemptedLabel;
    @FXML
    private Label SolvedLabel;

    @FXML
    private void BackButtonClicked() {

        try {
            catfishClient.showProblemList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void LogoutButtonClicked() {

        try {
            catfishClient.showRegistrationMenu();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    private PieChart pieChart;


    @FXML
    private Label NoSubmission;

    @FXML
    private void ExitButtonClicked() {

        try {
            System.exit(0);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    public void setUser(User currentUser) {
        this.currentUser = currentUser;
        System.out.println(currentUser);
        boolean flg = false;
        if(currentUser.getTotalWA()!=0)
            pieChartData.add(new PieChart.Data("Wrong Answer", currentUser.getTotalWA()));
        if(currentUser.getTotalCE()!=0)
            pieChartData.add(new PieChart.Data("Compilation Error", currentUser.getTotalCE()));
        if(currentUser.getTotalAC()!=0)
            pieChartData.add(new PieChart.Data("Accepted", currentUser.getTotalAC()));
        if(currentUser.getTotalTLE()!=0)
            pieChartData.add(new PieChart.Data("Time Limit Exceed", currentUser.getTotalTLE()));


//        if( currentUser.getTotalTLE() != 0 || currentUser.getTotalCE() != 0 || currentUser.getTotalAC()!=0 || currentUser.getTotalWA() != 0 ) {
//            pieChart.setData(pieChartData);
//        }
        if( pieChartData.size()!=0 ) {
            pieChart.setData(pieChartData);
        }

        else NoSubmission.setText("(No Submission)");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " (", data.getPieValue() +")"
                        )
                )
        );

        HandleName.setText(currentUser.getHandle());
        SolvedLabel.setText(String.valueOf(currentUser.getTotalSolved()));
        AttemptedLabel.setText(String.valueOf(currentUser.getTotalAttempted()));

    }

    public void setCatfishClient(CatfishClient catfishClient) {
        this.catfishClient = catfishClient;
    }
}
