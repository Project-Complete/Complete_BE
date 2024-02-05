package org.complete.challang.drink.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DrinkRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;

    @Test
    @DisplayName("Drink Repository가 존재")
    public void existDrinkRepository(){
        assertThat(drinkRepository).isNotNull();
    }
}
