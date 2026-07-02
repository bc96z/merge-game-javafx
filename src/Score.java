import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Score {
    public static int totalScore = 0;
    public static int highScore = 0;
    public Text textScore;
    public Text textHighScore;

    public Score(){
        textScore = new Text();
        textScore.setText(String.valueOf(totalScore));
        textScore.setX((Main.root.getWidth() - textScore.getLayoutBounds().getWidth()) / 2);
        //textScore.setX(250 - textScore.getLayoutBounds().getWidth()*2.5);
        textScore.setY(80);
        textScore.setFill(Color.web("#0080ff"));
        textScore.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 70));

        textHighScore = new Text();
        textHighScore.setText("HIGH SCORE: " + String.valueOf(highScore));
        textHighScore.setX(10);
        textHighScore.setY(740);
        textHighScore.setFill(Color.web("#0080ff"));
        textHighScore.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 30));

    }

    public void updateScore(){
        textScore.setText(String.valueOf(totalScore));
        textScore.setX((Main.root.getWidth() - textScore.getLayoutBounds().getWidth()) / 2);
        Main.root.getChildren().remove(textScore);
        Main.root.getChildren().add(textScore);
    }
    public void updateHighScore(){
        textHighScore.setText("HIGH SCORE: " + String.valueOf(highScore));
        Main.root.getChildren().remove(textHighScore);
        Main.root.getChildren().add(textHighScore);
    }
    public void resetScore(){
        if(totalScore > highScore){
            highScore = totalScore;
        }
        totalScore = 0;
        updateHighScore();
        updateScore();
    }
}
