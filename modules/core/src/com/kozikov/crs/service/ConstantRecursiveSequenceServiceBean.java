package com.kozikov.crs.service;

import com.kozikov.crs.entity.RecurrentRelation;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(ConstantRecursiveSequenceService.NAME)
public class ConstantRecursiveSequenceServiceBean implements ConstantRecursiveSequenceService {

    @Override
    public RecurrentRelation getRelationFromStringArray(String[] coefficients, int modF) {
        List<Integer> c = new ArrayList<>();
        for (String s : coefficients) {
            if (StringUtil.isNumeric(s)) {
                c.add(Integer.valueOf(s));
            }
        }
        return new RecurrentRelation(c, modF);
    }

    @Override
    public List<Integer> getVectorFromStringArray(String[] coefficients) {
        List<Integer> c = new ArrayList<>();
        for (String s : coefficients) {
            if (StringUtil.isNumeric(s)) {
                c.add(Integer.valueOf(s));
            }
        }
        return c;
    }
}