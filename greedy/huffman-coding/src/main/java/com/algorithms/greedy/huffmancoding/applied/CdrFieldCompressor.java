package com.algorithms.greedy.huffmancoding.applied;

import com.algorithms.greedy.huffmancoding.classic.HuffmanCoding;
import com.algorithms.greedy.huffmancoding.classic.HuffmanCoding.HuffmanResult;

/**
 * A telecom carrier archives millions of call detail records (CDRs) a day. Free-text fields
 * inside a CDR batch — cause codes, status strings — repeat the same handful of values overwhelmingly
 * often ({@code NORMAL_CLEARING} far more than {@code NETWORK_CONGESTION}, for instance), which is
 * exactly the skewed-frequency shape Huffman coding is built to exploit before the batch is
 * written to archival storage.
 */
public final class CdrFieldCompressor {

    public CompressionReport compress(String batchField) {
        if (batchField == null || batchField.isEmpty()) {
            throw new IllegalArgumentException("batchField must not be null or empty");
        }
        HuffmanResult result = HuffmanCoding.encode(batchField);
        int originalBits = batchField.length() * 8;
        int compressedBits = result.encodedBits().length();
        return new CompressionReport(originalBits, compressedBits, result);
    }

    public record CompressionReport(int originalBits, int compressedBits, HuffmanResult huffman) {
        public double compressionRatio() {
            return (double) compressedBits / originalBits;
        }
    }
}
