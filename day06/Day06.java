import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day06 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of("input"));
        System.out.println("PART 1: " + new Day06().process(4, lines.get(0)));
        System.out.println("PART 2: " + new Day06().process(14, lines.get(0)));
    }

    private int process(int windowSize, String line) throws Exception {
        List<String> list = List.of(line.split(""));
        return createSlidingWindow(list, windowSize)
                .filter(it -> it.size() == windowSize) // remove windows from the end of the list that are not of size 4
                .map(it -> String.join("", it)) // turn char list into string
                .filter(it -> it.chars().distinct().count() == windowSize) // find windows that do not contain duplicates
                .findFirst()// we want the first such window
                .map(it -> line.indexOf(it) + windowSize)  // find this windows index
                .orElseThrow(() -> new Exception("No match"));
    }

    // 1, 2, 3, 4 -> [1, 2], [2, 3], [3, 4]
    public static <T> Stream<List<T>> createSlidingWindow(List<T> list, int size) {
        return IntStream.range(0, list.size())
                .mapToObj(i -> list.subList(
                        i,
                        Math.min(list.size(), i + size)))
                .filter(l -> !l.isEmpty());
    }
}
