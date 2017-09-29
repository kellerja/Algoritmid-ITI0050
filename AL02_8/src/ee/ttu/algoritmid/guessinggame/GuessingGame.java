package ee.ttu.algoritmid.guessinggame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GuessingGame {

    Oracle oracle;

    public GuessingGame(Oracle oracle) {
        this.oracle = oracle;
    }

    /**
     * @param fruitArray - All the possible fruits.
     * @return the name of the fruit.
     */
    public String play(Fruit[] fruitArray) {
        Arrays.sort(fruitArray, Comparator.comparingInt(Fruit::getWeight));
        int low = 0, high = fruitArray.length - 1;
        int mid = (low+high)/2;
        Fruit guess = fruitArray[mid];
        String result = oracle.isIt(guess);
        while (!result.equals("correct!")) {
            if (result.equals("heavier")) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
            mid = (low+high)/2;
            if (low > high) return guess.getName();
            guess = fruitArray[mid];
            result = oracle.isIt(guess);
        }
        return guess.getName();
    }

    public static void main(String[] args) {
        Fruit[] fruits = {
                new Fruit("Apelsin", 5),
                new Fruit("Pirn", 3),
                new Fruit("Õun", 7),
                new Fruit("Banaan", 2),
                new Fruit("Greip", 1),
                new Fruit("Mango", 8),
                new Fruit("Ploom", 15),
                new Fruit("Virsik", 11)
        };
        int selectedFruit = 6;
        Oracle oracle = new Oracle(fruits[selectedFruit]);
        GuessingGame guessingGame = new GuessingGame(oracle);
        System.out.println("Correct " + fruits[selectedFruit].getName());
        System.out.println("Guess " + guessingGame.play(fruits));
    }
}
