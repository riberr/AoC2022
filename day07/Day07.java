import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day07 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input"));
        new Day07().part1(lines);
    }

    Node topDir = new Node(Node.Type.DIR, "/", 0, null);
    Node currentDir = topDir;

    private void part1(List<String> l) {
        executeCommands(l);
        computeSizeOfNode(topDir);
        List<Node> matches = findDirs(topDir, 100000, new ArrayList<>());
        System.out.println("Part 1: " + matches.stream().mapToInt(it -> it.size).sum());
    }

    private List<Node> findDirs(Node n, int maxSize, List<Node> matches) {
        for (Node child : n.children) {
            findDirs(child, maxSize, matches);
        }

        if (n.type == Node.Type.DIR && n.size <= maxSize) matches.add(n);
        return matches;
    }

    private void computeSizeOfNode(Node n) {
        for (Node child : n.children) {
            computeSizeOfNode(child);
        }


        n.size += n.children.stream().mapToInt(it -> it.size).sum();
        System.out.println("At node " + n.name + " with size " + n.size);
    }

    private void executeCommands(List<String> l) {
        for (int i = 0; i < l.size();) {
            System.out.println("Current dir: " + currentDir.name + " | executing: " + l.get(i));
            if (l.get(i).equals("$ cd /")) {
                currentDir = topDir;
                i++;
            } else if (l.get(i).equals("$ cd ..")) {
                currentDir = currentDir.parent;
                i++;
            } else if (l.get(i).startsWith("$ cd ")) {
                String dir = l.get(i).split(" ")[2];
                currentDir = currentDir.children.stream()
                        .filter(node -> node.name.equals(dir))
                        .findFirst().orElse(null);
                i++;
            } else if (l.get(i).equals("$ ls")) {
                i = ls(l, i);
            }

        }
    }

    private int ls(List<String> l, int i) {
        List<String> response = new ArrayList<>();
        while (i++ < l.size() - 1) {
            String line = l.get(i);
            if (!line.startsWith("$")) response.add(line); else break;
        }

        for (String s : response) {
            System.out.println("-> " + s);
            String[] split = s.split(" ");
            if (currentDir.children.stream().noneMatch(it -> it.name.equals(split[1]))) {
                if (split[0].equals("dir")) {
                    currentDir.children.add(new Node(Node.Type.DIR, split[1], 0, currentDir));
                } else {
                    currentDir.children.add(new Node(Node.Type.FILE, split[1], Integer.parseInt(split[0]), currentDir));
                }
            }
        }
        return i;
    }

    class Node {
        enum Type {
            DIR,
            FILE,
        }

        Type type;
        String name;
        Node parent;
        List<Node> children;
        //int ownSize;    // i.e. directory = 0
        int size;       // includes children

        Node(Type type, String name, int size, Node parent) {
            this.type = type;
            this.name = name;
            //this.ownSize = ownSize;
            this.parent = parent;
            children = new ArrayList<>();
            //totalSize = 0;
            this.size = size;
        }

        public void addChild(Node node) {
            children.add(node);
        }
    }
}
