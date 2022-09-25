import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Formula formula = new Formula();
        System.out.println("Input I");
        formula.setI(sc.nextInt());
        System.out.println("Input R");
        formula.setR(sc.nextInt());
        System.out.println("Input T");
        formula.setT(sc.nextInt());
        String answer = String.format("Output - %.3f joule", formula.thermal_energy());
        System.out.println(answer);
    }
}