package sample.util;

public class Conversao {

    public Conversao(){
        super();
    }

    public boolean StringToDouble(String number){
        try{
           Double.parseDouble(number);
        }catch (NumberFormatException e){
            return false; // ocorreu um erro durante a conversão.
        }
        return true;
    }

    public boolean StringToInteger(String number){
        try{
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            return false; // ocorreu um erro durante a conversão.
        }
        return true;
    }

    public Integer StringToInt(String number){
        int x = 0;
        try{
            x = Integer.parseInt(number);
        }catch (NumberFormatException e){
            System.out.println(e.getMessage()); // ocorreu um erro durante a conversão.
        }
        return x;
    }
}
