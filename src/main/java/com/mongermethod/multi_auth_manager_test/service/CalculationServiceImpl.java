package com.mongermethod.multi_auth_manager_test.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("calculationService")
public class CalculationServiceImpl implements CalculationService {
    @Override
    public BigDecimal fractionToPercentage(int numerator, int denominator) {
        BigDecimal n = new BigDecimal(numerator);
        BigDecimal d = new BigDecimal(denominator);
        
        return new BigDecimal(numerator).divide(new BigDecimal(denominator));
    }
}
