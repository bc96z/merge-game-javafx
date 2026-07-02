import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


// //////////////////////////////////////////////////
// 陣列裡的物件
// Rectangle存放照片
// Boolean haveDice 有骰子存在時為true
// //////////////////////////////////////////////////
public class DiceInBoard{
    public Rectangle currDice;
    public Boolean haveDice = false;
    public int diceNum = 0;

    //該格在視窗位置
    public double boardX;
    public double boardY;
    //上下左右界，判定拖曳是否在格子內
    public double minX; 
    public double maxX;
    public double minY;
    public double maxY;

    public DiceInBoard(int arrX, int arrY){
        currDice = new Rectangle(78 + Main.cube_size * arrX, 123 + Main.cube_size * arrY, 64, 64);
        currDice.setFill(Color.WHITE);
        this.boardX = 78 + Main.cube_size * arrX;
        this.boardY = 123 + Main.cube_size * arrY;


        this.minX = this.boardX;
        this.minY = this.boardY;
        this.maxX = this.boardX + 64;
        this.maxY = this.boardY + 64;
    }

    public void showYellow(){
        this.currDice.setFill(Color.YELLOW);
    }
    
    // 變空格
    public void showWhite(){
        this.currDice.setFill(Color.WHITE);
        this.haveDice = false;
        this.diceNum = 0;
    }

    public void setDice(Rectangle d, int num){
        this.currDice.setFill(d.getFill());
        this.haveDice = true;
        this.diceNum = num;
    }

    // merge dice +1 (Board.java使用)
    // n為當前數字，換成n+1
    public void setDiceUp(int n){
        this.currDice.setFill(new ImagePattern(DiceForm.images.get(n)));
        this.haveDice = true;
        this.diceNum = n + 1;

    }


    public void updateOnBoard(){
        Main.root.getChildren().add(currDice);
    }
    public void removeFromBoard(){
        Main.root.getChildren().remove(currDice);
    }

    // 給DragController判斷
    public Boolean checkDrag(double x, double y){
        if(x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY){
            return true;
        }
        else{
            return false;
        }
    }
}
