package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LottoGameTest {

    private static LottoReferee referee;
    private final Lotto firstPrizeLotto = createNewLotto(1, 2, 3, 4, 5, 6);
    private final Lotto secondPrizeLotto = createNewLotto(1, 2, 3, 4, 5, 7);
    private final Lotto thirdPrizeLotto = createNewLotto(1, 2, 3, 4, 5, 16);
    private final Lotto fourthPrizeLotto = createNewLotto(1, 2, 3, 4, 15, 16);
    private final Lotto fifthPrizeLotto = createNewLotto(1, 2, 3, 14, 15, 16);
    private final Lotto noPrizeLotto = createNewLotto(11, 12, 13, 14, 15, 16);

    @BeforeAll
    static void setup() {
        List<LottoNumber> winningNumbers = Stream
                .of(1, 2, 3, 4, 5, 6)
                .map(LottoNumber::of)
                .collect(Collectors.toList());

        LottoNumber bonusNumber = LottoNumber.of(7);

        referee = new LottoReferee(winningNumbers, bonusNumber);
    }

    @Test
    void getResultStatistics() {
        Lottos lottos = new Lottos(getLottosExample(firstPrizeLotto, secondPrizeLotto, noPrizeLotto));
        LottoGame game = new LottoGame(lottos, referee);

        Map<LottoResult, Integer> actual = game.getResultStatistics();

        assertThat(actual).containsOnlyKeys(LottoResult.values());
        assertThat(actual.get(LottoResult.FIRST)).isEqualTo(1);
        assertThat(actual.get(LottoResult.SECOND)).isEqualTo(1);
        assertThat(actual.get(LottoResult.THIRD)).isEqualTo(0);
        assertThat(actual.get(LottoResult.FOURTH)).isEqualTo(0);
        assertThat(actual.get(LottoResult.FIFTH)).isEqualTo(0);
    }

    private List<Lotto> getLottosExample(Lotto... lottos) {
        List<Lotto> lottosExample = new ArrayList<>();
        Collections.addAll(lottosExample, lottos);
        return lottosExample;
    }

    private Lotto createNewLotto(int... value) {
        List<LottoNumber> lottoNumbers = Arrays.stream(value)
                .boxed()
                .map(LottoNumber::of)
                .collect(Collectors.toList());

        return new Lotto(lottoNumbers);
    }
}
