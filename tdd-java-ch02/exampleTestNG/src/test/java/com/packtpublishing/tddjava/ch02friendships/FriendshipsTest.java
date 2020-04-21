package com.packtpublishing.tddjava.ch02friendships;

import com.packtpublishing.tddjava.ch02friendships.Friendships;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class FriendshipsTest {
    @Test(groups = { "base" })
    public void test1() {
        Friendships friendships = new Friendships();

        Assert.assertTrue(friendships.getFriendsList("Joe").isEmpty(), "Joe does not have friends");
    }

    @Test(groups = { "base" })
    public void test2() {
        Friendships friendships = new Friendships();
        friendships.makeFriends("Joe", "Audrey");

        Assert.assertTrue(friendships.areFriends("Joe", "Audrey"), "Joe and Audrey are friends");
        Assert.assertTrue(friendships.areFriends("Audrey", "Joe"), "Audrey and Joe are friends");

        Assert.assertTrue(friendships.getFriendsList("Joe").contains("Audrey"), "Joe is Audrey's friend");
        Assert.assertEquals(1, friendships.getFriendsList("Joe").size(), "Joe has 1 friend");

        Assert.assertTrue(friendships.getFriendsList("Audrey").contains("Joe"), "Audrey is Joe's friend");
        Assert.assertEquals(1, friendships.getFriendsList("Audrey").size(), "Joe has 1 friend");
    }

    @Test(groups = { "complex" }, dependsOnGroups = { "base" }, timeOut = 10)
    public void test3() {
        Friendships friendships = new Friendships();
        friendships.makeFriends("Joe", "Audrey");
        friendships.makeFriends("Joe", "Peter");
        friendships.makeFriends("Joe", "Michael");
        friendships.makeFriends("Joe", "Britney");
        friendships.makeFriends("Joe", "Paul");

        Assert.assertEquals(5, friendships.getFriendsList("Joe").size(), "Joe has 5 friends");
        Assert.assertTrue(friendships.areFriends("Joe", "Paul"), "Joe and Paul are friends");
        Assert.assertFalse(friendships.areFriends("Joe", "Ralph"), "Joe and Ralph are not friends");

        List<String> friendsOfJoe = Arrays.asList("Audrey", "Peter", "Michael", "Britney", "Paul");
        Assert.assertTrue(friendships.getFriendsList("Joe").containsAll(friendsOfJoe));
    }
}
