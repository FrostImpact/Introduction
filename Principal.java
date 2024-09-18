import java.util.Scanner;

public class Principal {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        System.out.println("Whats your name?: ");
        String name = sc.nextLine();
        System.out.println("Hello " + name);
        sc.close();

    }

}
