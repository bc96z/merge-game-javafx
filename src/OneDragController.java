import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


public class OneDragController {
    private final Node targetOne;

    private double anchorX;
    private double anchorY;
    private double mouseOffsetFromNodeZeroX;
    private double mouseOffsetFromNodeZeroY;

    private EventHandler<MouseEvent> setAnchor;
    private EventHandler<MouseEvent> updatePositionOnDrag;
    private EventHandler<MouseEvent> commitPositionOnRelease;

    private final int ACTIVE = 1;
    private final int INACTIVE = 0;
    private int cycleStatus = INACTIVE;
    private BooleanProperty isDraggable;


    public OneDragController(Node targetOne) {
        this(targetOne, false);
    }
    public OneDragController(Node targetOne,boolean isDraggable) {
        this.targetOne = targetOne;
        createHandlers();
        OnecreateDraggableProperty();
        this.isDraggable.set(isDraggable);
    }

    private void createHandlers() {

        // 1.
        setAnchor = event -> {
            if (event.isPrimaryButtonDown()) {
                cycleStatus = ACTIVE;
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                mouseOffsetFromNodeZeroX = event.getX();
                mouseOffsetFromNodeZeroY = event.getY();
            }
            
        };
        // 2.拖曳過程
        updatePositionOnDrag = event -> {
            if (cycleStatus != INACTIVE) {
                    //r (Main.dice_size/2+2)
                
                targetOne.setTranslateX( event.getSceneX() - anchorX );
                targetOne.setTranslateY( event.getSceneY() - anchorY );

                // //////////////////////////////////////////////////
                // 處理拖曳過程中，骰子在格子內會變色
                // //////////////////////////////////////////////////
                Main.gameBoard.showAllWhite();
                for(int i = 0; i < 5; i++){
                    
                    for(int j = 0; j < 5; j++){
                        

                        // 成立:dice 的格子點有包含
                        if(Main.gameBoard.board.get(i).get(j).checkDrag(targetOne.getTranslateX() + 32 + 250 - Main.dice_size /2, targetOne.getTranslateY() + 32 + 580) && ! Main.gameBoard.board.get(i).get(j).haveDice){
                            
                            Main.gameBoard.board.get(i).get(j).showYellow();
                        }
                    }
                }
            }
        };
        // 3. 滑鼠釋放
        // //////////////////////////////////////////////////
        // 功能:
        // (1)判定並放入
        // (2)消除、更新底下兩個骰子、整理版面
        // (3)查看game over
        // //////////////////////////////////////////////////
        commitPositionOnRelease = event -> {
            int putDice1X = -1;  // X = 0 是第一直行
            int putDice1Y = -1;
            if (cycleStatus != INACTIVE) {

                Main.gameBoard.showAllWhite();
                

                Boolean havePutDice = false;
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 判斷是放時是否合法，並放入dice
                // 並重新設定下面那組的dice
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        // 成立:dice1的格子點有包含
                        if(Main.gameBoard.board.get(i).get(j).checkDrag(targetOne.getTranslateX() + 32 + 250 - Main.dice_size /2, targetOne.getTranslateY() + 32 + 580) && ! Main.gameBoard.board.get(i).get(j).haveDice){
                            Main.gameBoard.board.get(i).get(j).setDice(Main.diceOneee.dice, Main.diceOneee.dice_num);
                            Main.gameBoard.board.get(i).get(j).haveDice = true;
                            havePutDice = true;
                            putDice1X = i;  // X = 0 是第一直行
                            putDice1Y = j;
                            
                        }
                    }
                }
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                
                // 若放入，則隨機更新骰子
                if(havePutDice){
                    Main.gameBoard.oneModecheckAndClear(putDice1X, putDice1Y);
                    Main.randomRule.randomDice_Oneee();
                    
                }
                //回歸原位
                targetOne.setTranslateX(0);
                targetOne.setTranslateY(0);

                Main.gameBoard.fulledBoard();
                Boolean changeToOne = Main.gameBoard.checkOneSpace();
                if(Main.twoMode && changeToOne){ 
                    //2->1
                    Main.root.getChildren().remove(Main.dice.dice1);
                    Main.root.getChildren().remove(Main.dice.dice2);
                    Main.root.getChildren().add(Main.diceOneee.dice);
                    Main.twoMode = false;

                    
                }
                if(! Main.twoMode && ! changeToOne){
                    //1->2
                    Main.root.getChildren().remove(Main.diceOneee.dice);
                    Main.root.getChildren().add(Main.dice.dice1);
                    Main.root.getChildren().add(Main.dice.dice2);
                    Main.twoMode = true;
                }
            }
        };
    }

    public void OnecreateDraggableProperty() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                targetOne.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                targetOne.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                targetOne.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
            else {
                targetOne.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                targetOne.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                targetOne.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }
    public boolean isIsDraggable() {
        return isDraggable.get();
    }
    public BooleanProperty isDraggableProperty() {
        return isDraggable;
    }
}
