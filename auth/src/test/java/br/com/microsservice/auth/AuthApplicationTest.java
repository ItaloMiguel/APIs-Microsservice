package br.com.microsservice.auth;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthApplicationTest {

    @Test
    public void contextLoads() {

    }

    @Test
    public void test() {
        System.out.println(new BCryptPasswordEncoder().encode("devdojo"));
    }
}