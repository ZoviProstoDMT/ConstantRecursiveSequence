package pojo;

public class LeastCommonMultiple {

    public static int get(int a, int b){
        return a*(b/GreatestCommonDivisor.get(a,b));
    }
}
