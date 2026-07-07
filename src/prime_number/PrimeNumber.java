package prime_number;

public class PrimeNumber {

    public static void main(String[] args) {

        int number = 11;
        boolean isPrime = true;

        if (number >= 2){
            for (int i = 2; i < number; i++) {
                if (number % i == 0){
                    isPrime = false;
                    break;
                }
            }
            System.out.println(isPrime);
        }
        else {
            System.out.println("ERROR!");
        }
    }
}