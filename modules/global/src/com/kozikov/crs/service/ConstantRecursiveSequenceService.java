package com.kozikov.crs.service;

import com.kozikov.crs.entity.RecurrentRelation;

import java.util.List;

public interface ConstantRecursiveSequenceService {
    String NAME = "crs_ConstantRecursiveSequenceService";

    RecurrentRelation getRelationFromStringArray(String[] coefficients, int modF);

    List<Integer> getVectorFromStringArray(String[] coefficients);
}