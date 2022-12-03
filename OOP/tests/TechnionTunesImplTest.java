package OOP.tests;

import OOP.Provided.Song;
import OOP.Provided.TechnionTunes;
import OOP.Provided.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import OOP.Solution.SongImpl;
import OOP.Solution.TechnionTunesImpl;
import OOP.Solution.UserImpl;

import java.util.*;

import static org.junit.Assert.*;

public class TechnionTunesImplTest {

    TechnionTunes tunes;

    public TechnionTunesImplTest()
    {
        tunes = new TechnionTunesImpl();
    }

    @Before
    public void setUp() throws TechnionTunes.UserAlreadyExists, TechnionTunes.SongAlreadyExists, User.IllegalRateValue, User.SongAlreadyRated, TechnionTunes.SongDoesntExist, TechnionTunes.UserDoesntExist
    {
        User user1 = new UserImpl(1, "TestUser1", 9);
        User user2 = new UserImpl(2, "TestUser2", 10);
        tunes.addUser(1, "TestUser1", 9);
        tunes.addUser(2, "TestUser2", 10);
        tunes.addUser(3, "TestUser2", 11);
        tunes.addUser(4, "TestUser2", 10);
        tunes.addUser(5, "TestUser2", 7);

        tunes.addSong(1, "BirthdaySong",3 ,"Eden Band");
        tunes.addSong(2, "WhereAreYou",9 ,"Nathan Goshen");
        tunes.addSong(3, "3:15(Breathe)",3 ,"Russ");
        tunes.addSong(4, "Animal",9 ,"SpringKing");
        tunes.addSong(5, "ShadowMoses",9 ,"Bring Me The Horizon");

        // song 2 -> rate 7 len 4
        // song 3 -> rate 7 len 3
        // song 4 -> rate 6 len 9 id 4
        // song 5 -> rate 6 len 9 id 5

        // song 3 -> 3 raters len 3
        // song 5 -> 3 raters len 9
        // song 1 -> 2 raters len 3
        // song 4 -> 1 rater len 9 id 4
        // song 2 -> 1 rater len 9 id 2

        // user 2 -> avg rate 7 age 10 id 2
        // user 4 -> avg rate 7 age 10 id 4
        // user 3 -> avg rate 6 age 11
        // user 5 -> avg rate 3 age 7
        // user 1 -> avg rate 0 age 9

        tunes.rateSong(2,2,7);
        tunes.rateSong(2,3,8);
        tunes.rateSong(2,5,6);

        tunes.rateSong(3,4,6);
        tunes.rateSong(3,5,3);
        tunes.rateSong(3,3,6);

        tunes.rateSong(4,5,9);
        tunes.rateSong(4,3,7);
        tunes.rateSong(4,1,5);

        tunes.rateSong(5,1,1);

    }

    @Test
    public void addUserTest_UserDoesntExist_Success() throws Exception
    {
        User user = new UserImpl(99, "TestUser1", 9);
        tunes.addUser(99, "TestUser1", 9);
    }

    @Test(expected = TechnionTunes.UserAlreadyExists.class)
    public void addUserTest_UserAlreadyExist_ThrowsException() throws Exception
    {
        User user = new UserImpl(1, "TestUser1", 9);
        tunes.addUser(1, "TestUser1", 9);
        tunes.addUser(1, "TestUser2", 10);
    }

    @Test
    public void getUser_UserExits_Success() throws TechnionTunes.UserDoesntExist
    {

        tunes.getUser(1);
    }

    @Test(expected = TechnionTunes.UserDoesntExist.class)
    public void getUser_UserDoesNotExits_ThrowsException() throws TechnionTunes.UserDoesntExist
    {
        tunes.getUser(99);
    }


    @Test
    public void makeFriends_UsersExistAndNotFriends_Success() throws TechnionTunes.UserDoesntExist, User.AlreadyFriends, User.SamePerson
    {
        tunes.makeFriends(1,2);
    }

    @Test(expected = User.SamePerson.class)
    public void makeFriends_SamePerson_ThrowsException() throws TechnionTunes.UserDoesntExist, User.AlreadyFriends, User.SamePerson
    {
        tunes.makeFriends(1,1);
    }

    @Test(expected = TechnionTunes.UserDoesntExist.class)
    public void makeFriends_SamePersonNotExist_ThrowsException() throws TechnionTunes.UserDoesntExist, User.AlreadyFriends, User.SamePerson
    {
        tunes.makeFriends(99,99);
    }

    @Test(expected = User.AlreadyFriends.class)
    public void makeFriends_AlreadyFriends_ThrowsException() throws TechnionTunes.UserDoesntExist, User.AlreadyFriends, User.SamePerson
    {
        tunes.makeFriends(1,2);
        tunes.makeFriends(2,1);
    }

    @Test
    public void addSong_SongDoesNotExist_Success() throws TechnionTunes.SongAlreadyExists
    {
        tunes.addSong(99, "Mr. Jones", 4, "CountingCrows");
    }

    @Test(expected = TechnionTunes.SongAlreadyExists.class)
    public void addSong_SongAlreadyExist_ThrowsException() throws TechnionTunes.SongAlreadyExists
    {
        tunes.addSong(1, "Mr. Jones", 4, "CountingCrows");
    }

    @Test
    public void getSong_SongExists_Success() throws TechnionTunes.SongDoesntExist
    {
        tunes.getSong(1);
    }

    @Test(expected = TechnionTunes.SongDoesntExist.class)
    public void getSong_SongDoesNotExist_ThrowsExcetion() throws TechnionTunes.SongDoesntExist
    {
        tunes.getSong(99);
    }

    @Test
    public void rateSong_ValidRating_Success() throws TechnionTunes.UserDoesntExist, TechnionTunes.SongDoesntExist, User.SongAlreadyRated, User.IllegalRateValue
    {
        tunes.rateSong(1,1,3);
    }

    @Test(expected = TechnionTunes.UserDoesntExist.class)
    public void rateSong_UserDoesNotExist_ThrowsException() throws TechnionTunes.UserDoesntExist, TechnionTunes.SongDoesntExist, User.IllegalRateValue, User.SongAlreadyRated
    {
        tunes.rateSong(99,99,3);
    }

    @Test(expected = TechnionTunes.SongDoesntExist.class)
    public void rateSong_SongDoesNotExist_ThrowsException() throws TechnionTunes.UserDoesntExist, TechnionTunes.SongDoesntExist, User.IllegalRateValue, User.SongAlreadyRated
    {
        tunes.rateSong(1,99,99);
    }

    @Test(expected = User.SongAlreadyRated.class)
    public void rateSong_SongAlreadyRated_ThrowsException() throws TechnionTunes.UserDoesntExist, TechnionTunes.SongDoesntExist,  User.IllegalRateValue, User.SongAlreadyRated
    {
        tunes.rateSong(1,1,2);
        tunes.rateSong(1,1,5);
    }

    @Test(expected = User.IllegalRateValue.class)
    public void rateSong_IllegalRateValue_ThrowsException() throws TechnionTunes.UserDoesntExist, TechnionTunes.SongDoesntExist, User.IllegalRateValue, User.SongAlreadyRated
    {
        tunes.rateSong(1,1,3);
        tunes.rateSong(1,1,99);
    }

    @Test
    public void getIntersection_UsersExist_Success() throws TechnionTunes.UserDoesntExist
    {
        int[] ids = {2,3,4};
        Collection<Song> common = tunes.getIntersection(ids);
        Song song1 = new SongImpl(3, "",1,"");
        Song song2 = new SongImpl(5, "",1,"");
        Set<Song> excpectedCommon = new TreeSet<Song>();
        excpectedCommon.add(song1);
        excpectedCommon.add(song2);
        Assert.assertEquals(excpectedCommon, common);
    }

    @Test
    public void getIntersection_UsersExistNoCommon_Success() throws TechnionTunes.UserDoesntExist
    {
        int[] ids = {2,5,3,4};
        Collection<Song> common = tunes.getIntersection(ids);
        Song song1 = new SongImpl(3, "",1,"");
        Song song2 = new SongImpl(5, "",1,"");
        Set<Song> excpectedCommon = new TreeSet<Song>();
        Assert.assertEquals(excpectedCommon, common);
    }

    @Test(expected = TechnionTunes.UserDoesntExist.class)
    public void getIntersection_UsersDoesNotExist_ThrowException() throws TechnionTunes.UserDoesntExist
    {
        int[] ids = {2,3,12,4};
        Collection<Song> common = tunes.getIntersection(ids);
    }

    @Test
    public void sortSongs_sortById_Success()
    {
        Collection<Song> sortedSongs = tunes.sortSongs(new Comparator<Song>()
        {
            @Override
            public int compare(Song song1, Song song2) {
                return Integer.compare(song1.getID(), song2.getID());
            }
        });

        int[] idsByOrder = {1,2,3,4,5};
        int index = 0;
        for(Song song : sortedSongs)
        {
            Assert.assertTrue(song.getID() == idsByOrder[index]);
            index++;
       }
    }

    @Test
    public void getHighestRatedSongs()
    {
        Collection<Song> highestRatedSongs = tunes.getHighestRatedSongs(4);
        // song 2 -> rate 7 len 4
        // song 3 -> rate 7 len 3
        // song 4 -> rate 6 len 3 id 4
        // song 5 -> rate 6 len 3 id 5

        int index = 0;
        int[] idsByOrder = {2,3,4,5};
        Assert.assertEquals(4, highestRatedSongs.size());

        for(Song song : highestRatedSongs)
        {
            Assert.assertEquals(idsByOrder[index], song.getID());
            index++;
        }
    }

    @Test
    public void getMostRatedSongs()
    {
        Collection<Song> mostRatedSongs = tunes.getMostRatedSongs(10);
        // song 3 -> 3 raters len 3
        // song 5 -> 3 raters len 9
        // song 1 -> 2 raters len 3
        // song 4 -> 1 rater len 9 id 4
        // song 2 -> 1 rater len 9 id 2
        int index = 0;
        int[] idsByOrder = {3,5,1,4,2};
        Assert.assertEquals(5, mostRatedSongs.size());

        for(Song song : mostRatedSongs)
        {
            Assert.assertEquals(idsByOrder[index], song.getID());
            index++;
        }
    }

    @Test
    public void getTopLikers() throws TechnionTunes.SongDoesntExist, TechnionTunes.UserDoesntExist, User.SongAlreadyRated, User.IllegalRateValue
    {
        Collection<User> topLikers = tunes.getTopLikers(5);
        tunes.rateSong(5,2,9);
        // user 2 -> avg rate 7 age 10 id 2
        // user 4 -> avg rate 7 age 10 id 4
        // user 3 -> avg rate 6 age 11
        // user 5 -> avg rate 6 age 7
        // user 1 -> avg rate 0 age 9
        int index = 0;
        int[] idsByOrder = {2,4,3,5,1};
        Assert.assertEquals(5, topLikers.size());

        for(User user : topLikers)
        {
            Assert.assertEquals(idsByOrder[index], user.getID());
            index++;
        }

    }

    @Test
    public void canGetAlong_SamePerson_True() throws TechnionTunes.UserDoesntExist
    {
        Assert.assertTrue(tunes.canGetAlong(1,1));
    }

    @Test
    public void canGetAlong_FriendsWithCommonFavorite_True() throws TechnionTunes.UserDoesntExist, TechnionTunes.SongDoesntExist, User.IllegalRateValue, User.SongAlreadyRated, User.AlreadyFriends, User.SamePerson
    {
        tunes.rateSong(5,5,9);
        tunes.rateSong(1,5,8);
        tunes.makeFriends(1,5);
        Assert.assertTrue(tunes.canGetAlong(1,5));
    }

    @Test
    public void canGetAlong_FriendsWithoutCommonFavorite_false() throws TechnionTunes.UserDoesntExist, User.SamePerson, User.AlreadyFriends
    {
        tunes.makeFriends(1,5);
        Assert.assertFalse(tunes.canGetAlong(1,5));
    }

    @Test
    public void canGetAlong_NotFriendsGetAlong_True() throws TechnionTunes.UserDoesntExist, User.SongAlreadyRated, TechnionTunes.SongAlreadyExists, TechnionTunes.SongDoesntExist, User.IllegalRateValue, User.SongAlreadyRated, User.AlreadyFriends, User.SamePerson
    {
        //5 -> 2 -> 4 -> 3 -> 1
        // 5 -> 2 (song11)
        tunes.addSong(11, "", 1, "");
        tunes.rateSong(5,11,9);
        tunes.rateSong(2,11,9);
        tunes.makeFriends(5,2);

        // 2 -> 4
        tunes.addSong(12, "", 1, "");
        tunes.rateSong(2,12,9);
        tunes.rateSong(4,12,9);
        tunes.makeFriends(4,2);

        // 4 -> 3
        tunes.addSong(13, "", 1, "");
        tunes.rateSong(4,13,9);
        tunes.rateSong(3,13,9);
        tunes.makeFriends(4,3);

        // 3 -> 1
        tunes.addSong(14, "", 1, "");
        tunes.rateSong(1,14,9);
        tunes.rateSong(3,14,9);
        tunes.makeFriends(1,3);

        //just for fun
        tunes.makeFriends(5,4);

        Assert.assertTrue(tunes.canGetAlong(1,5));
    }

    @Test
    public void canGetAlong_NotFriendsDoesNotGetAlong_True() throws TechnionTunes.UserDoesntExist, TechnionTunes.SongDoesntExist, User.IllegalRateValue, User.SongAlreadyRated, User.AlreadyFriends, User.SamePerson
    {
        Assert.assertFalse(tunes.canGetAlong(1,5));
    }

    @Test(expected = TechnionTunes.UserDoesntExist.class)
    public void canGetAlong_UserDoesNotExist_ThrowsException() throws TechnionTunes.UserDoesntExist
    {
        Assert.assertTrue(tunes.canGetAlong(1,99));
    }

    @Test
    public void iterator()
    {
        //1,3,2,4,5
        int[] ids = {1,3,2,4,5};
        int index = 0;
        Iterator<Song> it = tunes.iterator();
        while(it.hasNext())
        {
            Assert.assertEquals(ids[index], it.next().getID());
            index++;
        }
    }
}