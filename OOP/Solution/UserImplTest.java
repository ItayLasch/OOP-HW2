package OOP.Solution;

import OOP.Provided.Song;
import OOP.Provided.User;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.HTMLDocument;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserImplTest {
    UserImpl user;
    UserImplTest() throws User.IllegalRateValue, User.SongAlreadyRated {
        user = new UserImpl(1, "n1", 12);
        SongImpl s = new SongImpl(11, "s1", 23, "singer1");
        user.rateSong(s, 1);
        s = new SongImpl(12, "s2", 21, "singer1");
        user.rateSong(s, 5);
        s = new SongImpl(13, "s3", 22, "singer2");
        user.rateSong(s, 5);
        s = new SongImpl(14, "s4", 22, "singer3");
        user.rateSong(s, 5);
    }
    @Test
    void getID() {
        assert(user.getID() == 1);
    }

    @Test
    void getName() {
        assert(Objects.equals(user.getName(), "n1"));
    }

    @Test
    void getAge() {
        assertEquals(user.getAge(), 12);
    }

    @Test
    void rateSong() {
    }

    @Test
    void getAverageRating() {
        assertEquals(4, user.getAverageRating());
    }

    @Test
    void getPlaylistLength() {
        System.out.println(user.getPlaylistLength());
        assertEquals(88, user.getPlaylistLength());
    }

    @Test
    void getRatedSongs() {
        //s2 -> s4 -> s3 -> s1
        Collection<Song> songs = user.getRatedSongs();
        Iterator<Song> it = songs.iterator();
        assertEquals(it.next().getName(), "s2");
        assertEquals(it.next().getName(), "s4");
        assertEquals(it.next().getName(), "s3");
        assertEquals(it.next().getName(), "s1");
    }

    @Test
    void getFavoriteSongs() throws User.IllegalRateValue, User.SongAlreadyRated {
        Collection<Song> songs = user.getFavoriteSongs();
        Iterator<Song> it = songs.iterator();
        assert(!it.hasNext());
        Song s5 = new SongImpl(100, "s7", 3, "singer");
        Song s6 = new SongImpl(99, "s8", 5, "singer");
        user.rateSong(s5, 9);
        user.rateSong(s6, 8);
        songs = user.getFavoriteSongs();
        it = songs.iterator();
        assertEquals(it.next(), s6);
        assertEquals(it.next(), s5);
        Song s = new SongImpl(s5);
        assert(s != s5);
        assertEquals(s, s5);
    }

    @Test
    void addFriend() {
    }

    @Test
    void favoriteSongInCommon() {
    }

    @Test
    void getFriends() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void compareTo() {
    }
}