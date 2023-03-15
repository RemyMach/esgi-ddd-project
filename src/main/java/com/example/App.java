package com.example;

import com.example.use_case.CreateReservation;

public class App
{
    public static void main( String[] args )
    {
        CreateReservation createReservation = new CreateReservation();
        createReservation.execute();
    }

    public String getMessage() {
        return "Hello World!";
    }
}
