package org.complete.challang.drink.repository;

import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.complete.challang.app.drink.domain.entity.Food;
import org.complete.challang.app.drink.domain.repository.DrinkRepository;
import org.complete.challang.app.drink.domain.repository.FoodRepository;
import org.complete.challang.app.drink.controller.dto.item.FlavorStatisticDto;
import org.complete.challang.app.drink.controller.dto.item.FoodStatisticDto;
import org.complete.challang.app.review.domain.entity.Flavor;
import org.complete.challang.app.review.domain.entity.Review;
import org.complete.challang.app.review.domain.entity.ReviewFlavor;
import org.complete.challang.app.review.domain.entity.ReviewFood;
import org.complete.challang.app.review.domain.repository.FlavorRepository;
import org.complete.challang.app.review.domain.repository.ReviewFlavorRepository;
import org.complete.challang.app.review.domain.repository.ReviewFoodRepository;
import org.complete.challang.app.review.domain.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DrinkRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewFoodRepository reviewFoodRepository;

    @Autowired
    private FlavorRepository flavorRepository;

    @Autowired
    private ReviewFlavorRepository reviewFlavorRepository;

    @Test
    public void Repository존재() {
        assertThat(drinkRepository).isNotNull();
    }

    @Test
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

    @Test
    public void findFoodStatisticById성공() {
        //given
        final Drink drink = Drink.builder().build();
        final Food foodMeat = Food.builder()
                .category("육류")
                .build();
        final Food foodProMeat = Food.builder()
                .category("가공육")
                .build();
        final User user = User.builder().build();
        final Review review = Review.builder()
                .user(user)
                .drink(drink)
                .build();
        final ReviewFood reviewFood1 = ReviewFood.builder()
                .food(foodMeat)
                .review(review)
                .build();
        final ReviewFood reviewFood2 = ReviewFood.builder()
                .food(foodProMeat)
                .review(review)
                .build();
        final ReviewFood reviewFood3 = ReviewFood.builder()
                .food(foodMeat)
                .review(review)
                .build();

        //when
        //todo: Entity 로직 제공시 리팩토링
        drinkRepository.save(drink);
        foodRepository.save(foodMeat);
        foodRepository.save(foodProMeat);
        userRepository.save(user);
        reviewRepository.save(review);
        reviewFoodRepository.save(reviewFood1);
        reviewFoodRepository.save(reviewFood2);
        reviewFoodRepository.save(reviewFood3);
        final List<FoodStatisticDto> findResult = drinkRepository.findFoodStatisticById(drink.getId());//todo: autoincrement로 인해 getId 사용

        //then
        assertThat(findResult).isNotNull();
        assertThat(findResult.get(0).getCategory()).isEqualTo("육류");
        assertThat(findResult.get(0).getCount()).isEqualTo(2L);
    }

    @Test
    public void findFlavorStatisticById성공() {
        //given
        final Drink drink = Drink.builder().build();
        final Flavor flavorMandarine = Flavor.builder()
                .flavor("만다린")
                .build();
        final Flavor flavorBerry = Flavor.builder()
                .flavor("베리")
                .build();
        final User user = User.builder().build();
        final Review review = Review.builder()
                .user(user)
                .drink(drink)
                .build();
        final ReviewFlavor reviewFlavor1 = ReviewFlavor.builder()
                .flavor(flavorMandarine)
                .review(review)
                .build();
        final ReviewFlavor reviewFlavor2 = ReviewFlavor.builder()
                .flavor(flavorMandarine)
                .review(review)
                .build();
        final ReviewFlavor reviewFlavor3 = ReviewFlavor.builder()
                .flavor(flavorBerry)
                .review(review)
                .build();

        //when
        //todo: Entity 로직 제공시 리팩토링
        drinkRepository.save(drink);
        flavorRepository.save(flavorMandarine);
        flavorRepository.save(flavorBerry);
        userRepository.save(user);
        reviewRepository.save(review);
        reviewFlavorRepository.save(reviewFlavor1);
        reviewFlavorRepository.save(reviewFlavor2);
        reviewFlavorRepository.save(reviewFlavor3);
        final List<FlavorStatisticDto> findResult = drinkRepository.findFlavorStatisticById(drink.getId());//todo: autoincrement로 인해 getId 사용

        //then
        assertThat(findResult).isNotNull();
        assertThat(findResult.get(0).getFlavor()).isEqualTo("만다린");
        assertThat(findResult.get(0).getCount()).isEqualTo(2L);
    }
}
