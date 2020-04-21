package com.packtpublishing.tddjava.ch02friendships;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FriendshipsAssertJTest {
    @Test
    public void test1() {
        Friendships friendships = new Friendships();

        assertThat(friendships.getFriendsList("Joe")).isEmpty();
    }

    @Test
    public void test2() {
        Friendships friendships = new Friendships();
        friendships.makeFriends("Joe", "Audrey");

        assertThat(friendships.areFriends("Joe", "Audrey")).isTrue();
        assertThat(friendships.areFriends("Audrey", "Joe")).isTrue();

        assertThat(friendships.getFriendsList("Joe")).hasSize(1).contains("Audrey");

        assertThat(friendships.getFriendsList("Audrey")).hasSize(1).contains("Joe");
    }

    @Test(timeout = 10)
    public void test3() {
        Friendships friendships = new Friendships();
        friendships.makeFriends("Joe", "Audrey");
        friendships.makeFriends("Joe", "Peter");
        friendships.makeFriends("Joe", "Michael");
        friendships.makeFriends("Joe", "Britney");
        friendships.makeFriends("Joe", "Paul");

        assertThat(friendships.areFriends("Joe", "Paul")).isTrue();
        assertThat(friendships.areFriends("Audrey", "Ralph")).isFalse();

        assertThat(friendships.getFriendsList("Joe")).containsOnly("Audrey", "Peter", "Michael", "Britney", "Paul");
    }
}
