package com.algorithms.greedy.huffmancoding.classic;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Prefix-free binary encoding that gives frequent symbols shorter codes and rare symbols longer
 * ones, so the average bits-per-symbol drops below a fixed-width encoding (8 bits/char for plain
 * ASCII) whenever the input's symbol frequencies are skewed. The greedy choice — repeatedly merge
 * the two *currently* least-frequent nodes into one, treating their combined frequency as a single
 * new node — is provably optimal among all prefix-free binary codes for a known frequency
 * distribution (the exchange-argument proof is the classic example in both books cited in this
 * module's README). "Prefix-free" is what makes a single concatenated bitstream decodable at all
 * without separators: no symbol's code is ever a prefix of another symbol's code, so walking the
 * tree bit by bit always lands on exactly one leaf before the next code can start.
 */
public final class HuffmanCoding {

    private HuffmanCoding() {
    }

    public static HuffmanResult encode(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("input must not be null or empty");
        }
        Map<Character, Integer> frequencies = frequenciesOf(input);
        Node root = buildTree(frequencies);
        Map<Character, String> codes = new HashMap<>();
        assignCodes(root, new StringBuilder(), codes);
        StringBuilder encodedBits = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            encodedBits.append(codes.get(input.charAt(i)));
        }
        return new HuffmanResult(encodedBits.toString(), codes, root);
    }

    public static String decode(String encodedBits, Node root) {
        if (encodedBits == null || root == null) {
            throw new IllegalArgumentException("encodedBits and root must not be null");
        }
        StringBuilder result = new StringBuilder();
        if (root.leaf) {
            // A single distinct symbol never triggered a merge, so its code is one '0' per
            // occurrence rather than a path through a tree.
            for (int i = 0; i < encodedBits.length(); i++) {
                result.append(root.symbol);
            }
            return result.toString();
        }
        Node current = root;
        for (int i = 0; i < encodedBits.length(); i++) {
            current = encodedBits.charAt(i) == '0' ? current.left : current.right;
            if (current.leaf) {
                result.append(current.symbol);
                current = root;
            }
        }
        return result.toString();
    }

    private static Map<Character, Integer> frequenciesOf(String input) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            frequencies.merge(input.charAt(i), 1, Integer::sum);
        }
        return frequencies;
    }

    private static Node buildTree(Map<Character, Integer> frequencies) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            queue.add(Node.leaf(entry.getKey(), entry.getValue()));
        }
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            queue.add(Node.internal(left, right));
        }
        return queue.poll();
    }

    private static void assignCodes(Node node, StringBuilder prefix, Map<Character, String> codes) {
        if (node.leaf) {
            codes.put(node.symbol, prefix.isEmpty() ? "0" : prefix.toString());
            return;
        }
        prefix.append('0');
        assignCodes(node.left, prefix, codes);
        prefix.deleteCharAt(prefix.length() - 1);

        prefix.append('1');
        assignCodes(node.right, prefix, codes);
        prefix.deleteCharAt(prefix.length() - 1);
    }

    public record HuffmanResult(String encodedBits, Map<Character, String> codes, Node root) {
    }

    public static final class Node implements Comparable<Node> {
        private final char symbol;
        private final int frequency;
        private final boolean leaf;
        private final Node left;
        private final Node right;

        private Node(char symbol, int frequency, boolean leaf, Node left, Node right) {
            this.symbol = symbol;
            this.frequency = frequency;
            this.leaf = leaf;
            this.left = left;
            this.right = right;
        }

        static Node leaf(char symbol, int frequency) {
            return new Node(symbol, frequency, true, null, null);
        }

        static Node internal(Node left, Node right) {
            return new Node('\0', left.frequency + right.frequency, false, left, right);
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(frequency, other.frequency);
        }
    }
}
