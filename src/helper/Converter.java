package helper;

import pojo.Field;
import pojo.polynomial.Polynomial;
import pojo.RecurrentRelation;

import java.util.ArrayList;
import java.util.List;

public interface Converter {

    default Polynomial convertFrom(RecurrentRelation recurrentRelation) {
        List<Integer> convertedCoefficients = new ArrayList<>();
        recurrentRelation.getCoefficients().forEach(coefficient -> {
            int revertedCoefficient = coefficient * -1;
            convertedCoefficients.add(revertedCoefficient == 0 ? 0 : revertedCoefficient + Field.mod);
        });
        convertedCoefficients.add(1);
        return new Polynomial(convertedCoefficients);
    }

    default RecurrentRelation convertFrom(Polynomial polynomial) {
        List<Integer> convertedCoefficients = new ArrayList<>();
        polynomial.getCoefficients().forEach(coefficient -> {
            int revertedCoefficient = coefficient * -1;
            convertedCoefficients.add(revertedCoefficient == 0 ? 0 : revertedCoefficient + Field.mod);
        });
        convertedCoefficients.remove(convertedCoefficients.size() - 1);
        return new RecurrentRelation(convertedCoefficients);
    }

}
