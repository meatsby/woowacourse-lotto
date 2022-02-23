package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.junit.jupiter.params.ParameterizedTest.DISPLAY_NAME_PLACEHOLDER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LottoRefereeTest {

    public static final String PARAMETERIZED_TEST_DISPLAY_FORMAT =
            DISPLAY_NAME_PLACEHOLDER + " [" + ARGUMENTS_PLACEHOLDER + "]";

    private static LottoReferee referee;

    @BeforeAll
    static void setUp() {
        List<Integer> winningNumbers = Stream
                .of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.toList());
        int bonusNumber = 7;

        referee = new LottoReferee(winningNumbers, bonusNumber);
    }

    @Test
    void getLottoResult_firstOnSixMatchingNumbers() {
        Lotto lotto = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));

        assertThat(referee.getLottoResult(lotto)).isEqualTo(LottoResult.FIRST);
    }

    @Test
    void getLottoResult_secondOnFiveMatchingNumbersWithBonus() {
        Lotto lotto = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 7));

        assertThat(referee.getLottoResult(lotto)).isEqualTo(LottoResult.SECOND);
    }

    @Test
    void getLottoResult_thirdOnFiveMatchingNumbersWithoutBonus() {
        Lotto lotto = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 11));

        assertThat(referee.getLottoResult(lotto)).isEqualTo(LottoResult.THIRD);
    }

    @Test
    void getLottoResult_fourthOnFourMatchingNumbers() {
        Lotto lotto = new Lotto(Arrays.asList(1, 2, 3, 4, 11, 12));

        assertThat(referee.getLottoResult(lotto)).isEqualTo(LottoResult.FOURTH);
    }

    @Test
    void getLottoResult_fifthOnThreeMatchingNumbers() {
        Lotto lotto = new Lotto(Arrays.asList(1, 2, 3, 11, 12, 13));

        assertThat(referee.getLottoResult(lotto)).isEqualTo(LottoResult.FIFTH);
    }

    @ParameterizedTest(name = PARAMETERIZED_TEST_DISPLAY_FORMAT)
    @ValueSource(strings = {"11,12,13,14,15,16", "1,11,12,13,14,15", "1,2,11,12,13,14"})
    void getLottoResult_noneOnLessThanThreeMatchingNumbers(String value) {
        List<Integer> lottoNumbers = Arrays.stream(value.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Lotto lotto = new Lotto(lottoNumbers);

        assertThat(referee.getLottoResult(lotto)).isEqualTo(LottoResult.NONE);
    }
}