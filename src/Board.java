import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Board {
    public ArrayList<ArrayList<DiceInBoard>> board = new ArrayList<>();

    public Board(){
        for(int i = 0; i < 5; i++){

            ArrayList<DiceInBoard> tmpArr = new ArrayList<>();
            for(int j = 0; j < 5; j++){
                
                DiceInBoard tmp = new DiceInBoard(i, j);
                tmpArr.add(tmp);

            }
            board.add(tmpArr);
        }
    }

    public void showAllWhite(){
        for(int i = 0; i < 5; i++){

            for(int j = 0; j < 5; j++){
                if(! board.get(i).get(j).haveDice){
                    board.get(i).get(j).showWhite();
                }   
            }
        }
    }
    public void showAllBoard(){
        for(int i = 0; i < 5; i++){

            for(int j = 0; j < 5; j++){
                board.get(i).get(j).updateOnBoard();
            }
        }
    }

    public void oneModecheckAndClear(int putDice1X, int putDice1Y){
        //若發生第一次消除，需改成true並繼續消除
        Boolean keepCheck1 = false;

        Boolean fistClear_Dice1 = checkAndClear_oneStep(putDice1X, putDice1Y, Main.diceOneee.dice_num); // 此處已消除，但還未更新Dice1位置

        if(fistClear_Dice1){
            keepCheck1 = true;
            Main.gameBoard.board.get(putDice1X).get(putDice1Y).setDiceUp(Main.diceOneee.dice_num);  // 更新
        }

        int n1 = 0;
        while(keepCheck1){
            n1 += 1;
            if(checkAndClear_oneStep(putDice1X, putDice1Y, Main.diceOneee.dice_num + n1)){
                keepCheck1 = true;
                Main.gameBoard.board.get(putDice1X).get(putDice1Y).setDiceUp(Main.diceOneee.dice_num + n1);  // 更新
            }
            else{
                keepCheck1 = false;
            }
        }
    }



    public void checkAndClear(int putDice1X, int putDice1Y, int putDice2X, int putDice2Y){
        //若發生第一次消除，需改成true並繼續消除
        Boolean keepCheck1 = false;  // for dice1
        Boolean keepCheck2 = false;  // for dice2
        
        Boolean fistClear_Dice1 = checkAndClear_oneStep(putDice1X, putDice1Y, Main.dice.dice1_num); // 此處已消除，但還未更新Dice1位置
        Boolean fistClear_Dice2 = checkAndClear_oneStep(putDice2X, putDice2Y, Main.dice.dice2_num);


        if(fistClear_Dice1){
            keepCheck1 = true;
            Main.gameBoard.board.get(putDice1X).get(putDice1Y).setDiceUp(Main.dice.dice1_num);  // 更新
        }
        if(fistClear_Dice2){
            keepCheck2 = true;
            Main.gameBoard.board.get(putDice2X).get(putDice2Y).setDiceUp(Main.dice.dice2_num);
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 平行處理後，判斷接下來情況
        if(! fistClear_Dice1 && !fistClear_Dice2){
            // 兩個皆無消除，沒事情發生
        }
        else if(! (fistClear_Dice1 && fistClear_Dice2)){
            // 只有一方消除
            if(fistClear_Dice1){
                int n1 = 0;
                while(keepCheck1){
                    n1 += 1;
                    if(checkAndClear_oneStep(putDice1X, putDice1Y, Main.dice.dice1_num + n1)){
                        keepCheck1 = true;
                        Main.gameBoard.board.get(putDice1X).get(putDice1Y).setDiceUp(Main.dice.dice1_num + n1);  // 更新
                    }
                    else{
                        keepCheck1 = false;
                    }
                }
            }
            if(fistClear_Dice2){
                int n2 = 0;
                while(keepCheck2){
                    n2 += 1;
                    if(checkAndClear_oneStep(putDice2X, putDice2Y, Main.dice.dice2_num + n2)){
                        keepCheck2 = true;
                        Main.gameBoard.board.get(putDice2X).get(putDice2Y).setDiceUp(Main.dice.dice2_num + n2);  // 更新
                    }
                    else{
                        keepCheck2 = false;
                    }
                }
            }
        }
        else{
            // 兩個皆消，繼續平行處理?
            Main.dice.dice1_num += 1;
            Main.dice.dice2_num += 1;
            checkAndClear(putDice1X, putDice1Y, putDice2X, putDice2Y);
        }
        
    }

    // 檢查並消除一次，有消除 回傳true
    // 處理當下random情況
    public Boolean checkAndClear_oneStep(int X, int Y, int diceNum){
        Boolean canClear = false;
        ArrayList<ArrayList<Integer>> sameArr = new ArrayList<>();
        int numAround = 0;

        // 檢查上下左右
        ////////////////////////////////////////
        // 檢查上方
        if(Y != 0){  // 上方還有格子
            if(Main.gameBoard.board.get(X).get(Y - 1).haveDice && Main.gameBoard.board.get(X).get(Y - 1).diceNum == diceNum){  // 此處有骰子、且數字相同
                
                numAround += 1;
                sameArr.add(new ArrayList<>(Arrays.asList(X, Y - 1)));

                if(Y - 1 != 0){  // 上上還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X).get(Y - 2).haveDice && Main.gameBoard.board.get(X).get(Y - 2).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X, Y - 2)));
                    }
                }

                if(X != 0){  // 上左還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X - 1).get(Y - 1).haveDice && Main.gameBoard.board.get(X - 1).get(Y - 1).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X - 1, Y - 1)));
                    }
                }

                if(X != 4){  // 上右還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X + 1).get(Y - 1).haveDice && Main.gameBoard.board.get(X + 1).get(Y - 1).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X + 1, Y - 1)));
                    }
                }
            }
        }
        ////////////////////////////////////////

        ////////////////////////////////////////
        // 檢查下方
        if(Y != 4){  // 下方還有格子
            if(Main.gameBoard.board.get(X).get(Y + 1).haveDice && Main.gameBoard.board.get(X).get(Y + 1).diceNum == diceNum){  // 此處有骰子、且數字相同
                numAround += 1;
                sameArr.add(new ArrayList<>(Arrays.asList(X, Y + 1)));

                if(Y + 1 != 4){  // 下下還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X).get(Y + 2).haveDice && Main.gameBoard.board.get(X).get(Y + 2).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X, Y + 2)));
                    }
                }

                if(X != 0){  // 下左還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X - 1).get(Y + 1).haveDice && Main.gameBoard.board.get(X - 1).get(Y + 1).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X - 1, Y + 1)));
                    }
                }

                if(X != 4){  // 上右還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X + 1).get(Y + 1).haveDice && Main.gameBoard.board.get(X + 1).get(Y + 1).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X + 1, Y + 1)));
                    }
                }
            }
        }
        ////////////////////////////////////////

        ////////////////////////////////////////
        // 檢查左方
        if(X != 0){  // 左方還有格子
            if(Main.gameBoard.board.get(X - 1).get(Y).haveDice && Main.gameBoard.board.get(X - 1).get(Y).diceNum == diceNum){  // 此處有骰子、且數字相同
                numAround += 1;
                sameArr.add(new ArrayList<>(Arrays.asList(X - 1, Y)));

                if(X - 1 != 0){  // 左左還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X - 2).get(Y).haveDice && Main.gameBoard.board.get(X - 2).get(Y).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X - 2, Y)));
                    }
                }

                if(Y != 0){  // 左上還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X - 1).get(Y - 1).haveDice && Main.gameBoard.board.get(X - 1).get(Y - 1).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X - 1, Y - 1)));
                    }
                }

                if(Y != 4){  // 左下還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X - 1).get(Y + 1).haveDice && Main.gameBoard.board.get(X - 1).get(Y + 1).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X - 1, Y + 1)));
                    }
                }
            }
        }
        ////////////////////////////////////////

        //////////////////////////////////////// 
        // 檢查右方
        if(X != 4){  // 右方還有格子
            if(Main.gameBoard.board.get(X + 1).get(Y).haveDice && Main.gameBoard.board.get(X + 1).get(Y).diceNum == diceNum){  // 此處有骰子、且數字相同
                
                numAround += 1;
                sameArr.add(new ArrayList<>(Arrays.asList(X + 1, Y)));
                
                if(X + 1 != 4){  // 右右還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X + 2).get(Y).haveDice && Main.gameBoard.board.get(X + 2).get(Y).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X + 2, Y)));
                    }
                }

                if(Y != 0){  // 右上還有格、有骰、數字同
                    
                    if(Main.gameBoard.board.get(X + 1).get(Y - 1).haveDice && Main.gameBoard.board.get(X + 1).get(Y - 1).diceNum == diceNum){
                        canClear = true;
                        
                        sameArr.add(new ArrayList<>(Arrays.asList(X + 1, Y - 1)));
                    }
                }

                if(Y != 4){  // 右下還有格、有骰、數字同
                    if(Main.gameBoard.board.get(X + 1).get(Y + 1).haveDice && Main.gameBoard.board.get(X + 1).get(Y + 1).diceNum == diceNum){
                        canClear = true;
                        sameArr.add(new ArrayList<>(Arrays.asList(X + 1, Y + 1)));
                    }
                }
            }
        }
        ////////////////////////////////////////

        // 周圍有兩個以上相同
        if(numAround >= 2){
            canClear = true;
        }
        HashSet<ArrayList<Integer>> hashSet = new HashSet<>(sameArr);
        sameArr.clear();
        sameArr.addAll(hashSet);
        // 若可消除
        if(canClear){
            // 把array儲存可以消除的方格，變成白色
            Score.totalScore += diceNum * (sameArr.size() + 1);
            Main.score.updateScore();
            for(int i = 0; i < sameArr.size(); i++){

                Main.gameBoard.board.get( sameArr.get(i).get(0) ).get( sameArr.get(i).get(1) ).showWhite();
            }
            //////////////////////////////
            // 有消過5 或 6
            if(diceNum == 4){
                RandomRule.haveClear5 = true;
            }
            if(diceNum == 5){
                RandomRule.haveClear6 = true;
            }
            
            if(diceNum == 6){
                Main.gameBoard.board.get(X).get(Y).showWhite();
                Score.totalScore += 50;
                return false;
            }

        }

        return canClear;
    }

    // 查看是否開啟一格模式
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Boolean checkOneSpace(){
        Boolean oneMode = true;
        ////////////////////////////////////////////////////////// 四角
        // (X, Y) = (0, 0)、      (X, Y) = (0, 4)、     (X, Y) = (4, 0)、     (X, Y) = (4, 4)
        if(! board.get(0).get(0).haveDice && (! board.get(0).get(1).haveDice || ! board.get(1).get(0).haveDice)){
            oneMode = false;
        }
        if(! board.get(0).get(4).haveDice && (! board.get(0).get(3).haveDice || ! board.get(1).get(4).haveDice)){
            oneMode = false;
        }
        if(! board.get(4).get(0).haveDice && (! board.get(3).get(0).haveDice || ! board.get(4).get(1).haveDice)){
            oneMode = false;
        }
        if(! board.get(4).get(4).haveDice && (! board.get(3).get(4).haveDice || ! board.get(4).get(3).haveDice)){
            oneMode = false;
        }

        ////////////////////////////////////////////////////////// 四邊
        // 上、下
        for(int i = 1; i < 4; i++){
            if(! board.get(i).get(0).haveDice && (! board.get(i - 1).get(0).haveDice || ! board.get(i).get(1).haveDice || ! board.get(i + 1).get(0).haveDice)){
                oneMode = false;
            }
            if(! board.get(i).get(4).haveDice && (! board.get(i - 1).get(4).haveDice || ! board.get(i).get(3).haveDice || ! board.get(i + 1).get(4).haveDice)){
                oneMode = false;
            }
        }
        // 左、右
        for(int i = 1; i < 4; i++){
            if(! board.get(0).get(i).haveDice && (! board.get(0).get(i - 1).haveDice || ! board.get(1).get(i).haveDice || ! board.get(0).get(i + 1).haveDice)){
                oneMode = false;
            }
            if(! board.get(4).get(i).haveDice && (! board.get(4).get(i - 1).haveDice || ! board.get(3).get(i).haveDice || ! board.get(4).get(i + 1).haveDice)){
                oneMode = false;
            }
        }
        ////////////////////////////////////////////////////////// 四邊
        // 中間
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j ++){
                if(! board.get(i).get(j).haveDice){
                    if(! board.get(i - i).get(j).haveDice || ! board.get(i + 1).get(j).haveDice || ! board.get(i).get(j - 1).haveDice || ! board.get(i).get(j + 1).haveDice){
                        oneMode = false;
                    }
                }
            }
        }

        return oneMode;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    // 查看是否滿版，滿版則修該Main裡的game變數，結束遊戲
    public void fulledBoard(){
        Boolean full = true;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(! board.get(i).get(j).haveDice){
                    full = false;
                }
            }
        }
        if(full){
            Main.game = false;
        }
    }




}
