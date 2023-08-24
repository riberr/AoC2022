import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day05 {

    public static void main(String[] args) throws Exception {
        // PART 1
        Data data1 = new Day05().setup();

        // evaluate
        data1.instructions.forEach(instruction -> {
            var fromStack = data1.stacks.get(instruction.from - 1);
            var toStack = data1.stacks.get(instruction.to - 1);
            for (int i = 0; i < instruction.moves; i++) {
                toStack.push(fromStack.pop());
            }
        });

        // whats on top?
        System.out.print("Part 1: ");
        data1.stacks.forEach(stack -> System.out.print(stack.peek()));
        System.out.println("");

        // PART 2
        Data data2 = new Day05().setup();

        // evaluate
        data2.instructions.forEach(instruction -> {
            var fromStack = data2.stacks.get(instruction.from - 1);
            var toStack = data2.stacks.get(instruction.to - 1);

            var elem = fromStack.stream().limit(instruction.moves).toList();
            //fromStack.removeAll(elem);
            for (int i = elem.size() - 1; i >= 0; i--) {
                fromStack.remove();
                toStack.push(elem.get(i));
            }
        });

        // whats on top?
        System.out.print("Part 2: ");
        data2.stacks.forEach(stack -> System.out.print(stack.peek()));
    }

    private Data setup() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("input"));

        // find line that indicate number of columns
        int nrOfStacks = lines.stream()//Files.lines(Path.of("example_input"))
                .filter(it -> it.startsWith(" 1"))
                .findFirst()
                .map(it -> Character.getNumericValue(it.charAt(it.length() - 1)))
                .orElseThrow(() -> new Exception("could not find row indicating nr of rows"));

        // create nrOfStacks # of stacks
        List<Deque<Character>> stacks = new ArrayList<>();
        for (int i = 0; i < nrOfStacks; i++) {
            stacks.add(new ArrayDeque<>());
        }

        // fill stacks with chars
        lines.stream()
                .takeWhile(it -> !it.startsWith(" 1"))
                .forEach(line -> {
                    if (line.startsWith(" 1")) return;

                    int stackNr = 0;
                    // 1..5..9
                    for (int i = 1; i < line.length(); i = i + 4) {
                        char c = line.charAt(i);
                        if (c != ' ') {
                            stacks.get(stackNr).addLast(c);
                        }
                        stackNr++;
                    }
                });


        // parse instructions
        String regexPattern = "\\d+";
        Pattern pattern = Pattern.compile(regexPattern);

        var instructions = lines.stream()
                .filter(it -> it.startsWith("move"))
                .map(line -> {

                    List<Integer> values = new ArrayList<>();
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String value = matcher.group();
                        values.add(Integer.parseInt(value));
                    }
                    return new Instruction(values.get(0), values.get(1), values.get(2));
                })
                .toList();

        return new Data(stacks, instructions);
    }
}

class Data {
    List<Deque<Character>> stacks;
    List<Instruction> instructions;

    Data(List<Deque<Character>> stacks, List<Instruction> instructions) {
        this.stacks = stacks;
        this.instructions = instructions;
    }
}

class Instruction {
    int moves, from, to;

    Instruction(int moves, int from, int to) {
        this.moves = moves;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "moves=" + moves +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
