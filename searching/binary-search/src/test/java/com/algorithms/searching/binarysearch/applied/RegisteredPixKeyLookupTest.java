package com.algorithms.searching.binarysearch.applied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegisteredPixKeyLookupTest {

    @Test
    void reportsTrueForARegisteredKey() {
        RegisteredPixKeyLookup lookup = new RegisteredPixKeyLookup(
                new String[] {"alice@bank.com", "bob@bank.com", "carol@bank.com"});

        assertThat(lookup.isRegistered("bob@bank.com")).isTrue();
    }

    @Test
    void reportsFalseForAnUnregisteredKey() {
        RegisteredPixKeyLookup lookup = new RegisteredPixKeyLookup(
                new String[] {"alice@bank.com", "bob@bank.com", "carol@bank.com"});

        assertThat(lookup.isRegistered("dave@bank.com")).isFalse();
    }

    @Test
    void rejectsANullSnapshot() {
        assertThatThrownBy(() -> new RegisteredPixKeyLookup(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANullKey() {
        RegisteredPixKeyLookup lookup = new RegisteredPixKeyLookup(new String[] {"alice@bank.com"});

        assertThatThrownBy(() -> lookup.isRegistered(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
