import java.util.ArrayList;
import java.util.Random;

public class RandomRule {
    public static int randomDice1;
    public static int randomDice2;
    public static int randomDiceOne;

    // 控制遊戲階段
    public static Boolean firstState = true;
    public static Boolean haveClear5 = false;  // 有合成過5後，開始出4
    public static Boolean haveClear6 = false;  // 有合成過6後，開始出5

    public RandomRule(){

    }

    public void randomDice_1And2(){
        if(firstState && ! haveClear5 && ! haveClear6){
            Random random = new Random();
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                numbers.add(i);
            }
            int index1 = random.nextInt(numbers.size());
            int number1 = numbers.get(index1);
            numbers.remove(index1);
            int index2 = random.nextInt(numbers.size());
            int number2 = numbers.get(index2);

            Main.dice.changeDice(number1, number2);
            //Main.dice.changeDice(5, 4);
        }
        if(haveClear5 && ! haveClear6){
            Random random = new Random();
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                numbers.add(i);
            }
            int index1 = random.nextInt(numbers.size());
            int number1 = numbers.get(index1);
            numbers.remove(index1);
            int index2 = random.nextInt(numbers.size());
            int number2 = numbers.get(index2);

            Main.dice.changeDice(number1, number2);
        }
        if(haveClear6){
            Random random = new Random();
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                numbers.add(i);
            }
            int index1 = random.nextInt(numbers.size());
            int number1 = numbers.get(index1);
            numbers.remove(index1);
            int index2 = random.nextInt(numbers.size());
            int number2 = numbers.get(index2);

            Main.dice.changeDice(number1, number2);
            //Main.dice.changeDice(4, 5);
        }
    }

    public void randomDice_Oneee(){
        if(firstState && ! haveClear5 && ! haveClear6){
            Random random = new Random();
            int randomNumber = random.nextInt(3) + 1;
            Main.diceOneee.changeDice(randomNumber);
        }
        if(haveClear5 && ! haveClear6){
            Random random = new Random();
            int randomNumber = random.nextInt(4) + 1;
            Main.diceOneee.changeDice(randomNumber);
        }
        if(haveClear6){
            Random random = new Random();
            int randomNumber = random.nextInt(5) + 1;
            Main.diceOneee.changeDice(randomNumber);
        }
    }

    public void resetRandom(){
        firstState = true;
        haveClear5 = false;
        haveClear6 = false;
    }

}
