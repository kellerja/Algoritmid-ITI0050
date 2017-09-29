package ee.ttu.algoritmid.guessinggame;

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
        for (int i = 0; i < fruitArray.length;i++) {
            Fruit guess = fruitArray[i];
            String result = oracle.isIt(guess);
            if (result.equals("correct!")) {
                return guess.getName();
            }
        }
        return "";
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
        Oracle oracle = new Oracle(fruits[3]);
        GuessingGame guessingGame = new GuessingGame(oracle);
        guessingGame.play(fruits);
    }
}
