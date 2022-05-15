package lab6;

import java.util.InputMismatchException;
import java.util.Scanner;
public class Kalkulator {
    private float x;
    private float y;
    public void reading()
    {
        Scanner skan = new Scanner(System.in);
        try {
        System.out.println("Podaj x : ");
        this.x = skan.nextFloat();
        System.out.println("Podaj y : ");
        this.y = skan.nextFloat();
        }
        catch (InputMismatchException e){
            System.out.println("Podano zle dane, podaj dane jeszcze raz");
            reading();
        }

    }
    void view(float wynik)
    {
        System.out.printf("Wynik jest rowny : %.3f \n",wynik);
    } 
    public void dividing(float x, float y){
        try {
            float wynik = x/y;
            view(wynik);
        } catch (ArithmeticException e) {
            System.out.println("Zle dane, podaj nowe dane");
            reading();
        }
        
        
    }
    public void add(float x, float y)
    {
        view(x+y);
    }
    public void multiplication(float x, float y)
    {
        view(x*y);
    }
    public void substraction(float x, float y)
    {
        view(x-y);
    }
    

    public void calc(){
        reading();
        Scanner skan = new Scanner(System.in);
        System.out.println("Co chcesz zrobić?");
        String wyb;
        wyb = skan.nextLine();
        switch (wyb)
        {
            case "+": add(this.x,this.y);break;
            case "-": substraction(this.x,this.y);break;
            case "/": dividing(this.x,this.y);break;
            case "*": multiplication(this.x,this.y);break;
        }
    }
    
}
