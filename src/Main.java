import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;


public class Main extends Application {
    private Stage primaryStage;
    public static int cube_size = 70;
    public static int dice_size = 64;
    public static Pane root;
    public static Board gameBoard;
    public static Boolean game = true;
    public static DiceForm dice;
    public static DiceFormOneeeee diceOneee;
    public static RandomRule randomRule;
    public static Score score;
    public static Boolean twoMode = true;  // 兩個方塊模式，預設是true

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainScene();
        gameLoop();

    }


    // //////////////////////////////////////////////////
    // 顯示主要畫面
    // //////////////////////////////////////////////////
    private void showMainScene() {
        
        // //////////////////////////////////////////////////
        // 顯示設置
        // //////////////////////////////////////////////////
        root = new Pane();
        Scene scene = new Scene(root, 500, 750, ColorMode.background_color);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("M E R G E");
        primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
        primaryStage.show();
    }

    // //////////////////////////////////////////////////
    // 遊戲迴圈
    // //////////////////////////////////////////////////
    private void gameLoop(){
        setBoard();
        render();
        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                
                if(game){
                }
                else{
                    setBoard();
                    game = true;
                    randomRule.resetRandom();
                    Main.root.getChildren().remove(Main.diceOneee.dice);
                    Main.root.getChildren().add(Main.dice.dice1);
                    Main.root.getChildren().add(Main.dice.dice2);
                    Main.twoMode = true;
                    score.resetScore();
                }
            }
        };
        loop.start();
    }


    // //////////////////////////////////////////////////
    // 渲染物件
    // //////////////////////////////////////////////////
    private void render(){
        // //////////////////////////////////////////////////
        // 格線
        // //////////////////////////////////////////////////
        for(int i = 0; i < 6; i++){
            Line line1 = new Line(75, 120 + i*cube_size, 75 + cube_size*5, 120 + i*cube_size);
            Line line2 = new Line(75 + i*cube_size, 120, 75 + i*cube_size, 120 + cube_size*5);
            line1.setStroke(ColorMode.line_color);
            line2.setStroke(ColorMode.line_color);
            line1.setStrokeWidth(5.0);
            line2.setStrokeWidth(5.0);
            root.getChildren().addAll(line1,line2);
        }

        randomRule = new RandomRule();
        score = new Score();
        if (!root.getChildren().contains(score.textScore)) {
            root.getChildren().add(score.textScore);
        }
        if (!root.getChildren().contains(score.textHighScore)) {
            root.getChildren().add(score.textHighScore);
        }
        // //////////////////////////////////////////////////
        // 第一組骰子 two mode
        // //////////////////////////////////////////////////
        dice = new DiceForm(1,2);
        root.getChildren().add(dice.dice1);
        root.getChildren().add(dice.dice2);

        // //////////////////////////////////////////////////
        // 骰子 one mode
        // //////////////////////////////////////////////////
        diceOneee = new DiceFormOneeeee();
        
        
        // //////////////////////////////////////////////////
        // 兩種mode的拖曳功能
        // //////////////////////////////////////////////////
        DragController dragController1 = new DragController(dice.dice1, dice.dice2, true);
        dragController1.isDraggableProperty();
        OneDragController oneDragController = new OneDragController(diceOneee.dice, true);
        oneDragController.isDraggableProperty();


        
        // //////////////////////////////////////////////////
        // 空白鍵功能
        // //////////////////////////////////////////////////
        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && DiceForm.canRotate  && twoMode) {
                dice.rotate();
            }
        });
        
    }

    public void setBoard(){
        gameBoard = new Board();
        gameBoard.showAllBoard();
    }
}
