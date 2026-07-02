import javafx.scene.paint.Color;

public class ColorMode{
    public static String mode = "w";

    public static Color background_color = Color.web("FFFFFF");  // 背景顏色 白色
    public static Color line_color = Color.web("000000");  // 格線顏色 黑色


    public static void change_mode(){
        if(mode.equals("w")){
            background_color = Color.web("000000");
            line_color = Color.web("BBBBBB");


            mode = "b";
        }
        else{
            background_color = Color.web("FFFFFF");
            line_color = Color.web("000000");


            mode = "w";
        }
        
    }
}
