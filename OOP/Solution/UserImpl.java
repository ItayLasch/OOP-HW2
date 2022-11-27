package OOP.Solution;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import OOP.Provided.*;

public class UserImpl implements OOP.Provided.User {

    int userID;
    String userName;
    int userAge;
    Map<Song, Integer> songMap;
    List<User> friendList;

    public UserImpl(int userID, String userName, int userAge) {
        this.userID = userID;
        this.userName = userName;
        this.userAge = userAge;
        this.songMap = new HashMap<Song, Integer>();
        this.friendList = new LinkedList<User>();
    }

    public int getID() {
        return userID;
    }

    public String getName() {
        return userName;
    }

    public int getAge() {
        return userAge;
    }

    public User rateSong(final Song song, int rate) throws IllegalRateValue, SongAlreadyRated {
        if (!(rate >= 1 && rate <= 10)) {
            throw new IllegalRateValue();
        }

        if (this.songMap.containsKey(song)) {
            throw new SongAlreadyRated();
        }

        this.songMap.put(song, rate);
        return this;
    }

    public double getAverageRating() {
        double sum = this.songMap.values().stream().reduce(0, Integer::sum);
        return (sum / this.songMap.size());
    }

    public int getPlaylistLength() {
        return this.songMap.keySet().stream().map(s -> s.getLength()).reduce(0, Integer::sum);
        /*for (Map.Entry<Song, Integer> entry : this.songMap.entrySet()) {
            totalLength += entry.getKey().getLength();
        }

        return totalLength;*/
    }

    public Collection<Song> getRatedSongs() {
        CompareRatedSongs comp = new CompareRatedSongs();
        return this.songMap.entrySet().stream().sorted(comp).map(e -> e.getKey())
                .collect(Collectors.toList());
    }

    public Collection<Song> getFavoriteSongs() {
        if (this.songMap.isEmpty()) {
            return new LinkedList<Song>();
        }

        return this.songMap.entrySet().stream().filter(entry -> entry.getValue() >= 8).map(e -> e.getKey()).collect(Collectors.toList());
    }

    public User AddFriend(User friend) throws AlreadyFriends, SamePerson {
        if (this.friendList.contains(friend)) {
            throw new AlreadyFriends();
        }

        if (this.equals(friend)) {
            throw new SamePerson();
        }

        this.friendList.add(friend);
        return this;
    }

    public boolean favoriteSongInCommon(User user) {
        if (!this.friendList.contains(user)) {
            return false;
        }

        LinkedList<Song> favoriteSongOther = (LinkedList<Song>) user.getFavoriteSongs();

        return (this.getFavoriteSongs().stream()
                .filter(entry -> favoriteSongOther.contains(entry)).count() >= 1);
    }

    public Map<User, Integer> getFriends() {
        return this.friendList.stream()
                .collect(Collectors.toMap(Function.identity(), user -> user.getRatedSongs().size()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || (o.getClass() != this.getClass())) {
            return false;
        }
        UserImpl other = (UserImpl) o;
        return this.userID == other.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userID);
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(this.userID, user.getID());
    }

    class CompareRatedSongs implements Comparator<Map.Entry<Song, Integer>> {
        @Override
        public int compare(Map.Entry<Song, Integer> e1, Map.Entry<Song, Integer> e2) {
            int diff = e1.getValue().compareTo(e2.getValue());
            if (diff != 0) {
                return diff;
            }

            diff = e1.getKey().getLength() - e2.getKey().getLength();
            if (diff != 0) {
                return (-diff);
            }

            return e1.getKey().getID() - e2.getKey().getID();
        }
    }
}
