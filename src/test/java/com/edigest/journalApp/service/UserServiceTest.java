package com.edigest.journalApp.service;

import com.edigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUserName() {

        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUserName("prince"));
    }


   @Disabled
    @ParameterizedTest
    @CsvSource({
        "ram",
        "mohit",
        "vipul"
    })
    public void testFindByUserName1(String name){
        assertNotNull(userRepository.findByUserName(name),"failed for:"+name);
    }



    @Disabled
    @ParameterizedTest
    @ValueSource(strings ={
            "ram",
            "mohit",
            "vipul"
    })
    public void testFindByUserName2(String name){
        assertNotNull(userRepository.findByUserName(name),"failed for:"+name);
    }




    @Disabled
    @ParameterizedTest
    @CsvSource({
         "1,1,2",
         "2,10,12",
         "3,3,6"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }


//    ALSO LEARN ABOUT THESE ANNOTATION
//    @BeforeAll, @BeforeEach, @AfterAll, @AfterEach annotation

}
