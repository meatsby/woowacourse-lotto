package controller;

import static validator.LottoNumberValidators.validateNoDuplicateInList;
import static validator.LottoNumberValidators.validateNoDuplicates;

import domain.LottoGame;
import domain.LottoNumber;
import domain.LottoReferee;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import validator.LottoNumberValidators;
import view.InputView;
import view.OutputView;

public class LottoController {

    LottoGame lottoGame;

    public void run() {
        initLottoGame();
        initLottoReferee();
    }

    private void initLottoReferee() {
        List<LottoNumber> winningNumbers = registerWinningNumbers();
        LottoNumber bonusNumber = registerBonusNumber(winningNumbers);
        LottoReferee referee = new LottoReferee(winningNumbers, bonusNumber);
    }

    private List<LottoNumber> registerWinningNumbers() {
        String winningNumbersInput = InputView.requestWinningNumbers();
        List<LottoNumber> winningNumbers = Arrays.stream(winningNumbersInput.split(", "))
                .map(LottoNumberValidators::validateAndParseNumber)
                .map(LottoNumber::of)
                .collect(Collectors.toList());
        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException("6개의 당첨 번호를 입력해야 합니다.");
        }
        validateNoDuplicates(winningNumbers.stream().map(LottoNumber::getNumber).collect(Collectors.toList()));

        return winningNumbers;
    }

    private LottoNumber registerBonusNumber(List<LottoNumber> winningNumbers) {
        int bonusNumber = InputView.requestBonusNumber();
        validateNoDuplicateInList(bonusNumber,
                winningNumbers.stream().map(LottoNumber::getNumber).collect(Collectors.toList()));
        return LottoNumber.of(bonusNumber);
    }

    private void initLottoGame() {
        int money = InputView.requestUserMoney();
        int count = money / 1000;
        this.lottoGame = LottoGame.create(count);
        OutputView.printPurchaseInfo(this.lottoGame.getLottos());
    }
}
