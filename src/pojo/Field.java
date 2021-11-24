package pojo;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public static int mod = 997;

    public static List<Integer> normalizeCoefficients(List<Integer> coefficients) {
        ArrayList<Integer> result = new ArrayList<>(coefficients);
        for (int i = 0; i < result.size(); i++) {
            while (result.get(i) < 0) {
                int integer = result.get(i);
                result.set(i, integer + mod);
            }
            result.set(i, result.get(i) % mod);
        }
        return result;
    }
}
