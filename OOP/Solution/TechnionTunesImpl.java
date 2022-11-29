package OOP.Solution;

import java.util.*;
import java.util.stream.Collectors;

import OOP.Provided.*;

public class TechnionTunesImpl implements TechnionTunes {

    Map<Integer, User> users;
    Map<Integer, Song> songs;

    public TechnionTunesImpl() {
        users = new HashMap<Integer, User>();
        songs = new HashMap<Integer, Song>();
    }

    public void addUser(int userID, String userName, int userAge) throws UserAlreadyExists {
        if (users.containsKey(userID)) {
            throw new UserAlreadyExists();
        }
        this.users.put(userID, new UserImpl(userID, userName, userAge));
    }

    public User getUser(int id) throws UserDoesntExist {
        if (!this.users.containsKey(id)) {
            throw new UserDoesntExist();
        }

        return this.users.get(id);
    }

    public void makeFriends(int id1, int id2) throws UserDoesntExist, User.AlreadyFriends, User.SamePerson {
        if (!users.containsKey(id1) || !users.containsKey(id2)) {
            throw new UserDoesntExist();
        }

        User user1 = users.get(id1);
        User user2 = users.get(id2);

        user1.AddFriend(user2);
        user2.AddFriend(user1);
    }

    public void addSong(int songID, String songName, int length, String SingerName) throws SongAlreadyExists {
        if (this.songs.containsKey(songID)) {
            throw new SongAlreadyExists();
        }

        this.songs.put(songID, new SongImpl(songID, songName, length, SingerName));
    }

    public Song getSong(int id) throws SongDoesntExist {
        if (!this.songs.containsKey(id)) {
            throw new SongDoesntExist();
        }

        return this.songs.get(id);
    }

    public void rateSong(int userId, int songId, int rate)
            throws UserDoesntExist, SongDoesntExist, User.IllegalRateValue, User.SongAlreadyRated {
        User user = this.getUser(userId);
        Song song = this.getSong(songId);

        user.rateSong(song, rate);
        song.rateSong(user, rate);
    }

    public Set<Song> getIntersection(int IDs[]) throws UserDoesntExist {
        List<Collection<Song>> listSong = new LinkedList<Collection<Song>>();
        for (int id : IDs) {
            User u = this.getUser(id);
            listSong.add(u.getRatedSongs());
        }

        Set<Song> ratedByAll = new HashSet<Song>();
        for (Song s : songs.values()) {
            if (listSong.stream().allMatch(set -> set.contains(s))) {
                ratedByAll.add(s);
            }
        }

        return ratedByAll;
    }

    public Collection<Song> sortSongs(Comparator<Song> comp) {
        return this.songs.values().stream().sorted(comp).collect(Collectors.toList());
    }

    public Collection<Song> getHighestRatedSongs(int num) {
        CompareSongsByRatings comp = new CompareSongsByRatings();
        return this.songs.values().stream().sorted(comp).limit(num).collect(Collectors.toList());
    }

    public Collection<Song> getMostRatedSongs(int num) {
        CompareSongByRaters comp = new CompareSongByRaters();
        return this.songs.values().stream().sorted(comp).limit(num).collect(Collectors.toList());
    }

    public Collection<User> getTopLikers(int num) {
        CompareUserByRates comp = new CompareUserByRates();
        return this.users.values().stream().sorted(comp).limit(num).collect(Collectors.toList());
    }

    public boolean canGetAlong(int userId1, int userId2) throws UserDoesntExist {
        User user1 = this.getUser(userId1);
        User user2 = this.getUser(userId2);

        if (user1.equals(user2) || user1.favoriteSongInCommon(user2)) {
            return true;
        }

        Set<User> reachable = new HashSet<User>(); /* BFS Implementation - Algorithem Course Lecture 1 */
        Queue<User> queue = new LinkedList<User>();
        reachable.add(user1);
        queue.add(user1);

        while (!queue.isEmpty()) {
            User curr = queue.remove();
            if (curr.equals(user2)) {
                return true;
            }

            Set<User> toAdd = curr.getFriends().keySet().stream()
                    .filter(u -> curr.favoriteSongInCommon(u) && !reachable.contains(u)).collect(Collectors.toSet());
            reachable.addAll(toAdd);
            queue.addAll(toAdd);
        }

        return false;
    }

    class CompareSongsByRatings implements Comparator<Song> {
        @Override
        public int compare(Song s1, Song s2) {
            double diffAvg = s1.getAverageRating() - s2.getAverageRating();
            if (diffAvg > 0) {
                return 1;
            } else if (diffAvg < 0) {
                return -1;
            }

            int diffLength = s1.getLength() - s2.getLength();
            if (diffLength != 0) {
                return diffLength;
            }

            return s2.getID() - s1.getID();
        }
    }

    class CompareSongByRaters implements Comparator<Song> {
        @Override
        public int compare(Song s1, Song s2) {
            int diff = s1.getRaters().size() - s2.getRaters().size();
            if (diff != 0) {
                return diff;
            }

            diff = s1.getLength() - s2.getLength();
            if (diff != 0) {
                return -diff;
            }

            return s1.getID() - s2.getID();
        }
    }

    class CompareUserByRates implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            double diffAvg = u1.getAverageRating() - u2.getAverageRating();
            if (diffAvg > 0) {
                return 1;
            } else if (diffAvg < 0) {
                return -1;
            }

            int diffAge = u1.getAge() - u2.getAge();
            if (diffAge != 0) {
                return diffAge;
            }

            return u2.getID() - u1.getID();
        }
    }

    class TechnionTunesIterator implements Iterator<Song> {

        private List<Song> iteratableSongs;
        private int index;

        public TechnionTunesIterator(Map<Integer, Song> songs) {
            CompareIterable comp = new CompareIterable();
            iteratableSongs = songs.values().stream().sorted(comp).collect(Collectors.toList());
            index = 0;
        }

        public boolean hasNext() {
            return index < iteratableSongs.size();
        }

        public Song next() {
            return iteratableSongs.get(index++);
        }

        class CompareIterable implements Comparator<Song> {
            @Override
            public int compare(Song s1, Song s2) {
                int diff = s2.getLength() - s1.getLength();
                if (diff != 0) {
                    return diff;
                }

                return s2.getID() - s1.getID();
            }
        }
    }

    public Iterator<Song> iterator() {
        return new TechnionTunesIterator(this.songs);
    }
}
