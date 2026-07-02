import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class DiceForm{

    public Rectangle dice1; // 矩形1
    public Rectangle dice2; // 矩形2
    public int dice1_num;  // 用來計算分數、或看哪個小 先消除
    public int dice2_num;
    public Arc arc_1;
    public Arc arc_2;
    public Arc arc_3;
    public Arc arc_4;
    public static int now_case = 1;

    public static Boolean canRotate = true;  // 拖曳時，不能轉
    public static ArrayList<Image> images;

    public DiceForm(int d1, int d2){
        images = new ArrayList<>();
        images.add(new Image("dice_1_w.png"));
        images.add(new Image("dice_2_w.png"));
        images.add(new Image("dice_3_w.png"));
        images.add(new Image("dice_4_w.png"));
        images.add(new Image("dice_5_w.png"));
        images.add(new Image("dice_6_w.png"));

        dice1 = new Rectangle(252, 580, Main.dice_size, Main.dice_size);
        dice2 = new Rectangle(248 - Main.dice_size, 580, Main.dice_size, Main.dice_size);


        dice1.setFill(new ImagePattern(images.get(d1 - 1)));
        dice2.setFill(new ImagePattern(images.get(d2 - 1)));

        dice1_num = d1;
        dice2_num = d2;

        
        arc_1 = new Arc(250, 580+(Main.dice_size/2), Main.dice_size/2+2, Main.dice_size/2+2, 90, -90);
        arc_1.setStartAngle(0);
        arc_1.setType(ArcType.OPEN);
        arc_1.setStroke(Color.BLACK);
        arc_1.setStrokeWidth(1);
        arc_1.setFill(null);

        arc_4 = new Arc(250, 580+(Main.dice_size/2), Main.dice_size/2+2, Main.dice_size/2+2, 90, -90); //r = Main.dice_size/2+2
        arc_4.setStartAngle(90);
        arc_4.setType(ArcType.OPEN);
        arc_4.setStroke(Color.BLACK);
        arc_4.setStrokeWidth(1);
        arc_4.setFill(null);
        
        arc_3 = new Arc(250, 580+(Main.dice_size/2), Main.dice_size/2+2, Main.dice_size/2+2, 90, -90);
        arc_3.setStartAngle(180);
        arc_3.setType(ArcType.OPEN);
        arc_3.setStroke(Color.BLACK);
        arc_3.setStrokeWidth(1);
        arc_3.setFill(null);

        arc_2 = new Arc(250, 580+(Main.dice_size/2), Main.dice_size/2+2, Main.dice_size/2+2, 90, -90);
        arc_2.setStartAngle(270);
        arc_2.setType(ArcType.OPEN);
        arc_2.setStroke(Color.BLACK);
        arc_2.setStrokeWidth(1);
        arc_2.setFill(null);

    }
    
    public void rotate(){
        PathTransition pt1 = new PathTransition();
        PathTransition pt2 = new PathTransition();
        pt1.setDuration(Duration.millis(100));
        pt1.setNode(dice1);
        pt1.setOrientation(PathTransition.OrientationType.NONE);
        pt1.setCycleCount(1);
        pt1.setAutoReverse(false);
        pt1.setInterpolator(Interpolator.LINEAR);

        pt2.setDuration(Duration.millis(100));
        pt2.setNode(dice2);
        pt2.setOrientation(PathTransition.OrientationType.NONE);
        pt2.setCycleCount(1);
        pt2.setAutoReverse(false);
        pt2.setInterpolator(Interpolator.LINEAR);


        if(now_case == 1){
            pt1.setPath(arc_1);
            pt2.setPath(arc_3);
            now_case += 1;
        }
        else if(now_case == 2){
            pt1.setPath(arc_2);
            pt2.setPath(arc_4);
            now_case += 1;
        }
        else if(now_case == 3){
            pt1.setPath(arc_3);
            pt2.setPath(arc_1);
            now_case += 1;
        }
        else{
            pt1.setPath(arc_4);
            pt2.setPath(arc_2);
            now_case = 1;
        }
        
        pt1.play();
        pt2.play();
    }

    public void changeDice(int d1, int d2){
        dice1.setFill(new ImagePattern(images.get(d1 - 1)));
        dice2.setFill(new ImagePattern(images.get(d2 - 1))); 
        dice1_num = d1;
        dice2_num = d2;
    }

}