package com.kozikov.crs.service;

import com.kozikov.crs.entity.Polynomial;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(PolynomialService.NAME)
public class PolynomialServiceBean implements PolynomialService {

    @Override
    public Polynomial getFromStringArray(String[] coefficients, int modF) {
        List<Integer> c = new ArrayList<>();
        for (String s : coefficients) {
            if (StringUtil.isNumeric(s)) {
                c.add(Integer.valueOf(s));
            }
        }
        return new Polynomial(c, modF);
    }
}