package com.algorithms.greedy.huffmancoding.applied;

import com.algorithms.greedy.huffmancoding.applied.CdrFieldCompressor.CompressionReport;
import com.algorithms.greedy.huffmancoding.classic.HuffmanCoding;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CdrFieldCompressorTest {

    private final CdrFieldCompressor compressor = new CdrFieldCompressor();

    @Test
    void compressesASkewedCauseCodeBatchAndRoundTrips() {
        String batchField = "NORMAL_CLEARING".repeat(50)
                + "BUSY".repeat(10)
                + "NO_ANSWER".repeat(5)
                + "NETWORK_CONGESTION";

        CompressionReport report = compressor.compress(batchField);

        assertThat(report.compressionRatio()).isLessThan(1.0);
        String decoded = HuffmanCoding.decode(report.huffman().encodedBits(), report.huffman().root());
        assertThat(decoded).isEqualTo(batchField);
    }

    @Test
    void rejectsNullOrEmptyBatchField() {
        assertThatThrownBy(() -> compressor.compress(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> compressor.compress("")).isInstanceOf(IllegalArgumentException.class);
    }
}
