import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


public class DragController {
    private final Node target;
    private final Node another;


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


    public DragController(Node target, Node another) {
        this(target,another,false);
    }
    public DragController(Node target, Node another,boolean isDraggable) {
        this.target = target;
        this.another = another;
        createHandlers();
        createDraggableProperty();
        this.isDraggable.set(isDraggable);
    }

    private void createHandlers() {

        // 1.
        setAnchor = event -> {
            if (event.isPrimaryButtonDown()) {
                DiceForm.canRotate = false;
                cycleStatus = ACTIVE;
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                mouseOffsetFromNodeZeroX = event.getX();
                mouseOffsetFromNodeZeroY = event.getY();
            }
            // 如果右鍵被點擊，回到原來位置
            if (event.isSecondaryButtonDown()) {
                cycleStatus = INACTIVE;
                // target.setTranslateX(0); //平移值為0
                // target.setTranslateY(0);
                // another.setTranslateX(0); //平移值為0
                // another.setTranslateY(0);
                if (DiceForm.now_case == 2){
                    target.setTranslateX(- (Main.dice_size/2+2));
                    target.setTranslateY(+ (Main.dice_size/2+2));
                    another.setTranslateX(+ (Main.dice_size/2+2));
                    another.setTranslateY(- (Main.dice_size/2+2));
                }
                else if (DiceForm.now_case == 3){
                    target.setTranslateX(- 2 * (Main.dice_size/2+2));
                    target.setTranslateY(0);
                    another.setTranslateX(2 * (Main.dice_size/2+2));
                    another.setTranslateY(0);
                }
                else if (DiceForm.now_case == 4){
                    target.setTranslateX(- (Main.dice_size/2+2));
                    target.setTranslateY(- (Main.dice_size/2+2));
                    another.setTranslateX(+ (Main.dice_size/2+2));
                    another.setTranslateY(+ (Main.dice_size/2+2));
                }
                else if (DiceForm.now_case == 1){
                    target.setTranslateX(0);
                    target.setTranslateY(0);
                    another.setTranslateX(0);
                    another.setTranslateY(0);
                }
            }
        };
        // 2.拖曳過程
        updatePositionOnDrag = event -> {
            if (cycleStatus != INACTIVE) {
                    //r (Main.dice_size/2+2)
                if (DiceForm.now_case == 2){
                    target.setTranslateX( event.getSceneX() - anchorX - (Main.dice_size/2+2));
                    target.setTranslateY( event.getSceneY() - anchorY + (Main.dice_size/2+2));
                    another.setTranslateX( event.getSceneX() - anchorX + (Main.dice_size/2+2));
                    another.setTranslateY( event.getSceneY() - anchorY - (Main.dice_size/2+2));
                }
                else if (DiceForm.now_case == 3){
                    target.setTranslateX(+ event.getSceneX() - anchorX - 2 * (Main.dice_size/2+2));
                    target.setTranslateY(+ event.getSceneY() - anchorY);
                    another.setTranslateX(+ event.getSceneX() - anchorX + 2 * (Main.dice_size/2+2));
                    another.setTranslateY(+ event.getSceneY() - anchorY);
                }
                else if (DiceForm.now_case == 4){
                    target.setTranslateX(+ event.getSceneX() - anchorX - (Main.dice_size/2+2));
                    target.setTranslateY(+ event.getSceneY() - anchorY - (Main.dice_size/2+2));
                    another.setTranslateX(+ event.getSceneX() - anchorX + (Main.dice_size/2+2));
                    another.setTranslateY(+ event.getSceneY() - anchorY + (Main.dice_size/2+2));
                }
                else if (DiceForm.now_case == 1){
                    target.setTranslateX(+ event.getSceneX() - anchorX);
                    target.setTranslateY(+ event.getSceneY() - anchorY);
                    another.setTranslateX(+ event.getSceneX() - anchorX);
                    another.setTranslateY(+ event.getSceneY() - anchorY);
                }

                // //////////////////////////////////////////////////
                // 處理拖曳過程中，骰子在格子內會變色
                // //////////////////////////////////////////////////
                Main.gameBoard.showAllWhite();
                for(int i = 0; i < 5; i++){
                    
                    for(int j = 0; j < 5; j++){
                        // System.out.println(target.getTranslateX());
                        // System.out.println(target.getTranslateY());
                        // System.out.println(another.getLayoutX());
                        // System.out.println(another.getLayoutY());
                        

                        // 成立:dice1的格子點有包含
                        if(Main.gameBoard.board.get(i).get(j).checkDrag(target.getTranslateX() + 32 + 252, target.getTranslateY() + 32 + 580) && ! Main.gameBoard.board.get(i).get(j).haveDice){
                            // 骰子組合 case 1
                            if (DiceForm.now_case == 1 && (target.getTranslateX() + 32 + 252 >= 78 + Main.cube_size)  ){
                                // dice2 有空格
                                if(! Main.gameBoard.board.get(i - 1).get(j).haveDice){ 
                                    Main.gameBoard.board.get(i).get(j).showYellow();
                                    Main.gameBoard.board.get(i - 1).get(j).showYellow();
                                }
                            }
                            // 骰子組合 case 2
                            else if (DiceForm.now_case == 2 && (target.getTranslateY() + 32 + 580 >= 123 + Main.cube_size) ){
                                if(! Main.gameBoard.board.get(i).get(j - 1).haveDice){ 
                                    Main.gameBoard.board.get(i).get(j).showYellow();
                                    Main.gameBoard.board.get(i).get(j - 1).showYellow();
                                }
                            }
                            // 骰子組合 case 3
                            else if (DiceForm.now_case == 3 && (target.getTranslateX() + 32 + 252 <= 78 + Main.cube_size * 4)){
                                if(! Main.gameBoard.board.get(i + 1).get(j).haveDice){ 
                                    Main.gameBoard.board.get(i).get(j).showYellow();
                                    Main.gameBoard.board.get(i + 1).get(j).showYellow();
                                }
                            }
                            else if (DiceForm.now_case == 4 && (target.getTranslateY() + 32 + 580 <= 123 + Main.cube_size * 4)){
                                if(! Main.gameBoard.board.get(i).get(j + 1).haveDice){ 
                                    Main.gameBoard.board.get(i).get(j).showYellow();
                                    Main.gameBoard.board.get(i).get(j + 1).showYellow();
                                }
                            }
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
            int putDice2X = -1;
            int putDice2Y = -1;
            if (cycleStatus != INACTIVE) {
                DiceForm.canRotate = true;
                Main.gameBoard.showAllWhite();
                Boolean havePutDice = false;
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 判斷是放時是否合法，並放入dice
                // 並重新設定下面那組的dice
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        // 成立:dice1的格子點有包含
                        if(Main.gameBoard.board.get(i).get(j).checkDrag(target.getTranslateX() + 32 + 252, target.getTranslateY() + 32 + 580) && ! Main.gameBoard.board.get(i).get(j).haveDice){
                            
                            // 骰子組合 case 1
                            if (DiceForm.now_case == 1 && (target.getTranslateX() + 32 + 252 >= 78 + Main.cube_size)){
                                // dice2 有空格
                                if(! Main.gameBoard.board.get(i - 1).get(j).haveDice){ 
                                    Main.gameBoard.board.get(i).get(j).setDice(Main.dice.dice1, Main.dice.dice1_num);
                                    Main.gameBoard.board.get(i - 1).get(j).setDice(Main.dice.dice2, Main.dice.dice2_num);
                                    Main.gameBoard.board.get(i).get(j).haveDice = true;
                                    Main.gameBoard.board.get(i - 1).get(j).haveDice = true;
                                    havePutDice = true;
                                    putDice1X = i;  // X = 0 是第一直行
                                    putDice1Y = j;
                                    putDice2X = i - 1;
                                    putDice2Y = j; 
                                    //////////////////////////還需要更新方塊main.dice
                                }
                            }
                            // 骰子組合 case 2
                            else if (DiceForm.now_case == 2 && (target.getTranslateY() + 32 + 580 >= 123 + Main.cube_size)){
                                if(! Main.gameBoard.board.get(i).get(j - 1).haveDice){
                                    Main.gameBoard.board.get(i).get(j).setDice(Main.dice.dice1, Main.dice.dice1_num);
                                    Main.gameBoard.board.get(i).get(j - 1).setDice(Main.dice.dice2, Main.dice.dice2_num);
                                    Main.gameBoard.board.get(i).get(j).haveDice = true;
                                    Main.gameBoard.board.get(i).get(j - 1).haveDice = true;
                                    havePutDice = true;
                                    putDice1X = i;  // X = 0 是第一直行
                                    putDice1Y = j;
                                    putDice2X = i;
                                    putDice2Y = j - 1;
                                }
                            }
                            // 骰子組合 case 3
                            else if (DiceForm.now_case == 3 && (target.getTranslateX() + 32 + 252 <= 78 + Main.cube_size * 4)){
                                if(! Main.gameBoard.board.get(i + 1).get(j).haveDice){ 
                                    Main.gameBoard.board.get(i).get(j).setDice(Main.dice.dice1, Main.dice.dice1_num);
                                    Main.gameBoard.board.get(i + 1).get(j).setDice(Main.dice.dice2, Main.dice.dice2_num);
                                    Main.gameBoard.board.get(i).get(j).haveDice = true;
                                    Main.gameBoard.board.get(i + 1).get(j).haveDice = true;
                                    havePutDice = true;
                                    putDice1X = i;  // X = 0 是第一直行
                                    putDice1Y = j;
                                    putDice2X = i + 1;
                                    putDice2Y = j;
                                }
                            }
                            else if (DiceForm.now_case == 4 && (target.getTranslateY() + 32 + 580 <= 123 + Main.cube_size * 4)){
                                if(! Main.gameBoard.board.get(i).get(j + 1).haveDice){ 
                                    Main.gameBoard.board.get(i).get(j).setDice(Main.dice.dice1, Main.dice.dice1_num);
                                    Main.gameBoard.board.get(i).get(j + 1).setDice(Main.dice.dice2, Main.dice.dice2_num);
                                    Main.gameBoard.board.get(i).get(j).haveDice = true;
                                    Main.gameBoard.board.get(i).get(j + 1).haveDice = true;
                                    havePutDice = true;
                                    putDice1X = i;  // X = 0 是第一直行
                                    putDice1Y = j;
                                    putDice2X = i;
                                    putDice2Y = j + 1;
                                }
                            }
                        }
                    }
                }
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                // 若放入，則隨機更新骰子
                if(havePutDice){
                    Main.gameBoard.checkAndClear(putDice1X, putDice1Y, putDice2X, putDice2Y);
                    //////////////////////
                    
                    /////////////////////////////////////////////////////////////////test 先弄成1 2
                    //Main.dice.changeDice(3, 5);
                    Main.randomRule.randomDice_1And2();
                    DiceForm.now_case = 1;
                }

                // 放回原本位置
                if (DiceForm.now_case == 2){
                    target.setTranslateX(- (Main.dice_size/2+2));
                    target.setTranslateY(+ (Main.dice_size/2+2));
                    another.setTranslateX(+ (Main.dice_size/2+2));
                    another.setTranslateY(- (Main.dice_size/2+2));
                }
                else if (DiceForm.now_case == 3){
                    target.setTranslateX(- 2 * (Main.dice_size/2+2));
                    target.setTranslateY(0);
                    another.setTranslateX(2 * (Main.dice_size/2+2));
                    another.setTranslateY(0);
                }
                else if (DiceForm.now_case == 4){
                    target.setTranslateX(- (Main.dice_size/2+2));
                    target.setTranslateY(- (Main.dice_size/2+2));
                    another.setTranslateX(+ (Main.dice_size/2+2));
                    another.setTranslateY(+ (Main.dice_size/2+2));
                }
                else if (DiceForm.now_case == 1){
                    target.setTranslateX(0);
                    target.setTranslateY(0);
                    another.setTranslateX(0);
                    another.setTranslateY(0);
                }

                Main.gameBoard.fulledBoard();
                Boolean changeToOne = Main.gameBoard.checkOneSpace();

                if(Main.twoMode && changeToOne){ 
                    //2->1
                    //System.out.println("changeMode");
                    Main.root.getChildren().remove(Main.dice.dice1);
                    Main.root.getChildren().remove(Main.dice.dice2);
                    Main.root.getChildren().add(Main.diceOneee.dice);
                    Main.twoMode = false;
                }
            } 
        };
    }
    public void createDraggableProperty() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                target.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);

                another.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                another.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                another.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            } else {
                target.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);

                another.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                another.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                another.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }
    public boolean isIsDraggable() {
        return isDraggable.get();
    }
    public BooleanProperty isDraggableProperty() {
        return isDraggable;
    }
    public void removeDragDice(){
        Main.root.getChildren().remove(target);
        Main.root.getChildren().remove(another);
    }
}
