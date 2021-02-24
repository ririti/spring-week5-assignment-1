package com.codesoom.assignment.product.domain;

import com.codesoom.assignment.user.dto.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PriceTest {

    @DisplayName("금액을 입력받아 가격객체를 생성한다")
    @Test
    void create() {
        Price price = Price.of(BigDecimal.valueOf(1000));

        assertThat(price.getPrice()).isEqualTo(BigDecimal.valueOf(1000));
    }

    @DisplayName("잘못된 금액을 입력받아 가격객체를 생성한다")
    @Test
    void create_with_invalid_price() {
        BigDecimal input = BigDecimal.valueOf(-1000);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Price.of(input));
    }

    @DisplayName("잘못된 금액을 입력받아 가격객체를 생성한다")
    @Test
    void create_with_null_price() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Price.of(null));
    }

    @Test
    void plus() {
        Price price = Price.of(BigDecimal.valueOf(1000));
        Price price2 = Price.of(BigDecimal.valueOf(1000));

        Price result = price.plus(price2);
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(2000));
    }

    @Test
    void minus() {
        Price price = Price.of(BigDecimal.valueOf(1000));
        Price price2 = Price.of(BigDecimal.valueOf(1000));

        Price result = price.minus(price2);
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void hash_code() {
        Price price = Price.of(BigDecimal.valueOf(1000));
        Price price2 = Price.of(BigDecimal.valueOf(1000));

        assertThat(price).hasSameHashCodeAs(price2);
    }

    @DisplayName("객체가 같은지 비교한다")
    @Test
    void equals() {
        Price price = Price.of(BigDecimal.valueOf(1000));
        Price price2 = Price.of(BigDecimal.valueOf(1000));

        assertThat(price.equals(price)).isTrue();
        assertThat(price.equals(price2)).isTrue();

        UserData dto = UserData.builder().build();
        assertThat(price.equals(dto)).isFalse();
    }
}
