import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class DiceFormOneeeee{

    public Rectangle dice; // 矩形1
    public int dice_num;

    public static ArrayList<Image> images;

    public DiceFormOneeeee(){
        images = new ArrayList<>();
        images.add(new Image("dice_1_w.png"));
        images.add(new Image("dice_2_w.png"));
        images.add(new Image("dice_3_w.png"));
        images.add(new Image("dice_4_w.png"));
        images.add(new Image("dice_5_w.png"));
        images.add(new Image("dice_6_w.png"));


        dice = new Rectangle(250 - Main.dice_size /2, 580, Main.dice_size, Main.dice_size);

        dice.setFill(new ImagePattern(images.get(1 - 1)));
        dice_num = 1;

    }
    
    public void rotate(){
        
    }

    public void changeDice(int d){
        dice.setFill(new ImagePattern(images.get(d - 1)));
        dice_num = d;
    }

}