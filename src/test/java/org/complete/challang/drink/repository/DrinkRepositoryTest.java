package org.complete.challang.drink.repository;

import org.complete.challang.drink.domain.entity.Drink;
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
    public void Repository존재() {
        assertThat(drinkRepository).isNotNull();
    }

    @Test
    @DisplayName("Drink 상세 조회")
    public void findById성공() {
        //given
        final Drink drink = Drink.builder()
                .name("트롤브루 레몬 라들러")
                .summary("유럽식 상큼한 레몬향이 풍부하게 느껴지는 라들러")
                .description("만하임 지방")
                .abv(5.0)
                .imageUrl("http://localhost")
                .reviewCount(0L)
                .reviewSumRating(0L)
                .build();

        //when
        drinkRepository.save(drink);
        final Drink findResult = drinkRepository.findById(1L).orElse(null);

        //then
        assertThat(findResult).isNotNull();
        assertThat(findResult.getId()).isNotNull();
        assertThat(findResult.getName()).isEqualTo("트롤브루 레몬 라들러");
        assertThat(findResult.getSummary()).isEqualTo("유럽식 상큼한 레몬향이 풍부하게 느껴지는 라들러");
        assertThat(findResult.getDescription()).isEqualTo("만하임 지방");
        assertThat(findResult.getAbv()).isEqualTo(5.0);
        assertThat(findResult.getImageUrl()).isEqualTo("http://localhost");
        assertThat(findResult.getReviewCount()).isEqualTo(0L);
        assertThat(findResult.getReviewSumRating()).isEqualTo(0L);
    }
}
