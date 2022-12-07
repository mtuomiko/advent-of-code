import com.mtuomiko.Main;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class MainTest {
    private int challengeCount = 2;
    private List<String> letters = List.of("a", "b");
    private String paddingTemplate = "%02d";
    private String inputSubfix = "_input.txt";
    private List<ArgumentPair> argumentPairs = IntStream.rangeClosed(1, challengeCount)
            .mapToObj(number -> String.format(paddingTemplate, number))
            .flatMap(paddedNumber -> letters.stream().map(letter -> {
                var solver = paddedNumber + letter;
                var inputFile = paddedNumber + inputSubfix;
                return new ArgumentPair(solver, inputFile);
            }))
            .toList();

    @Test
    public void allSolversCompleteUsingDefaultInputs() {
        argumentPairs.forEach(pair -> Main.main(new String[]{pair.solver(), pair.inputFile()}));
    }
}
