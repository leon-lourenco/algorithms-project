package com.algorithms.greedy.huffmancoding.classic;

import com.algorithms.greedy.huffmancoding.classic.HuffmanCoding.HuffmanResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HuffmanCodingTest {

    @Test
    void roundTripsASkewedFrequencyInputExactly() {
        String input = "AAAAAAAAAABBBBCCD"; // A:10, B:4, C:2, D:1

        HuffmanResult result = HuffmanCoding.encode(input);

        assertThat(HuffmanCoding.decode(result.encodedBits(), result.root())).isEqualTo(input);
    }

    @Test
    void skewedFrequenciesCompressBelowFixedEightBitsPerCharacter() {
        String input = "AAAAAAAAAABBBBCCD"; // 17 chars

        HuffmanResult result = HuffmanCoding.encode(input);

        assertThat(result.encodedBits().length()).isLessThan(input.length() * 8);
    }

    @Test
    void noCodeIsAPrefixOfAnotherCode() {
        HuffmanResult result = HuffmanCoding.encode("AAAAAAAAAABBBBCCD");
        List<String> codes = List.copyOf(result.codes().values());

        for (int i = 0; i < codes.size(); i++) {
            for (int j = 0; j < codes.size(); j++) {
                if (i == j) {
                    continue;
                }
                assertThat(codes.get(j)).as("code %s should not prefix code %s", codes.get(i), codes.get(j))
                        .doesNotStartWith(codes.get(i));
            }
        }
    }

    @Test
    void aSingleDistinctCharacterGetsOneBitPerOccurrenceAndRoundTrips() {
        String input = "ZZZZZ";

        HuffmanResult result = HuffmanCoding.encode(input);

        assertThat(result.codes()).isEqualTo(Map.of('Z', "0"));
        assertThat(result.encodedBits()).isEqualTo("00000");
        assertThat(HuffmanCoding.decode(result.encodedBits(), result.root())).isEqualTo(input);
    }

    @Test
    void aTwoCharacterInputRoundTrips() {
        String input = "AB";

        HuffmanResult result = HuffmanCoding.encode(input);

        assertThat(HuffmanCoding.decode(result.encodedBits(), result.root())).isEqualTo(input);
    }

    @Test
    void rejectsNullOrEmptyInput() {
        assertThatThrownBy(() -> HuffmanCoding.encode(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> HuffmanCoding.encode("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNullArgumentsToDecode() {
        HuffmanResult result = HuffmanCoding.encode("AB");

        assertThatThrownBy(() -> HuffmanCoding.decode(null, result.root())).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> HuffmanCoding.decode(result.encodedBits(), null)).isInstanceOf(IllegalArgumentException.class);
    }
}
