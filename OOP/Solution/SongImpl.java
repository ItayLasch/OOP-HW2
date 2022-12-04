package OOP.Solution;

import java.util.*;
import java.util.stream.Collectors;

import OOP.Provided.*;

public class SongImpl implements OOP.Provided.Song {
    int songID;
    String songName;
    int length;
    String singerName;
    Map<User, Integer> raters;
    Map<Integer, Set<User>> totalRates;

    public SongImpl(int songID, String songName, int length, String singerName) {
        this.songID = songID;
        this.songName = songName;
        this.length = length;
        this.singerName = singerName;
        this.raters = new HashMap<User, Integer>();
        this.totalRates = new HashMap<Integer, Set<User>>();
    }

    SongImpl(Song other) {
        this.songID = ((SongImpl) other).getID();
        this.songName = ((SongImpl) other).getName();
        this.length = ((SongImpl) other).getLength();
        this.singerName = ((SongImpl) other).getSingerName();
        this.totalRates = ((SongImpl) other).totalRates;
    }

    public int getID() {
        return songID;
    }

    public String getName() {
        return songName;
    }

    public int getLength() {
        return length;
    }

    public String getSingerName() {
        return singerName;
    }

    public void rateSong(User user, int rate) throws User.IllegalRateValue, User.SongAlreadyRated {
        if (rate < 0 || rate > 10)
        {
            throw new User.IllegalRateValue();
        }
        if (this.raters.containsKey(user))
        {
            throw new User.SongAlreadyRated();
        }
        
        this.raters.put(user, rate);
        if (!this.totalRates.containsKey(rate)) {
            this.totalRates.put(rate, new HashSet<User>());
        }
        this.totalRates.get(rate).add(user);
    }

    public Collection<User> getRaters() {
        CompareRaters comp = new CompareRaters();
        return this.raters.entrySet().stream().sorted(comp).map(e->e.getKey()).collect(Collectors.toList());
    }

    public Map<Integer, Set<User>> getRatings() {
        return this.totalRates;
    }

    public double getAverageRating() {
        if (this.raters.size() == 0) {
            return 0;
        }

        double totalRatings = this.totalRates.entrySet().stream()
                .map(e -> e.getKey() * e.getValue().size())
                .reduce(0, Integer::sum);

        return (totalRatings / this.raters.size());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || (o.getClass() != this.getClass())) {
            return false;
        }

        SongImpl other = (SongImpl) o;

        return (this.songID == other.getID());
    }

    @Override
    public int compareTo(Song song) {
        return Integer.compare(this.songID, song.getID());
    }

    class CompareRaters implements Comparator<Map.Entry<User, Integer>> {
        @Override
        public int compare(Map.Entry<User, Integer> e1, Map.Entry<User, Integer> e2) {
            int diff = e1.getValue().compareTo(e2.getValue());
            if (diff != 0) {
                return diff;
            }

            diff = e1.getKey().getAge() - e2.getKey().getAge();
            if (diff != 0) {
                return (-diff);
            }

            return e1.getKey().getID() - e2.getKey().getID();
        }
    }
}
