package pl.bogumil.bai.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.Test;

/**
 * Created by bbierc on 2016-04-01.
 */
public class PasswordGeneratorTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void printSamplePassword() {

        System.out.println("Bolek123!");
        System.out.println(passwordEncoder.encode("Bolek123!"));

        System.out.println("Lolek123!");
        System.out.println(passwordEncoder.encode("Lolek123!"));

        System.out.println("Antek123!");
        System.out.println(passwordEncoder.encode("Antek123!"));
    }

}