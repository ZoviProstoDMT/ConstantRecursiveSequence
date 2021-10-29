package com.kozikov.crs.service;

import com.kozikov.crs.entity.Polynomial;

public interface PolynomialService {
    String NAME = "crs_PolynomialService";

    Polynomial getFromStringArray(String[] coefficients, int modF);
}