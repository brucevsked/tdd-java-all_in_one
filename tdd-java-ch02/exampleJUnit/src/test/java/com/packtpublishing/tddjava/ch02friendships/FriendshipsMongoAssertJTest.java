package com.packtpublishing.tddjava.ch02friendships;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

public class FriendshipsMongoAssertJTest {
    @Mock
    FriendsCollection friends;

    @InjectMocks
    FriendshipsMongo friendships;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() {
        doReturn(new Person("Joe")).when(friends).findByName(anyString());

        assertThat(friendships.getFriendsList("Joe")).isEmpty();
    }

    @Test
    public void test2() {
        Person joe = new Person("Joe");
        doReturn(joe).when(friends).findByName("Joe");

        Person audrey = new Person("Audrey");
        doReturn(audrey).when(friends).findByName("Audrey");

        doNothing().when(friends).save(any(Person.class));


        friendships.makeFriends("Joe", "Audrey");

        assertThat(friendships.areFriends("Joe", "Audrey")).isTrue();
        assertThat(friendships.areFriends("Audrey", "Joe")).isTrue();

        assertThat(friendships.getFriendsList("Joe")).hasSize(1).contains("Audrey");

        assertThat(friendships.getFriendsList("Audrey")).hasSize(1).contains("Joe");
    }

    @Test(timeout = 500)
    public void test3() {
        Person joe = new Person("Joe");
        doReturn(joe).when(friends).findByName("Joe");

        doNothing().when(friends).save(any(Person.class));

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
