package com.example.controlwork2carsharing.calculator;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeCalculator {
    public double timeCost(LocalDateTime start, LocalDateTime finish, double price){
        Duration duration = Duration.between(start, finish);
        double hours = Math.ceil(duration.toHours());

        return hours*price;
    }
}
