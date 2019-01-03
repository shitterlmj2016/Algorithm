import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class BaseballElimination {


    private int size;
    private ArrayList<Team> teams;
    private int[] isHopeless;

    private Bag<String>[] certify;

    private class Team {
        String name;
        int win;
        int loss;
        int remain;
        int[] game;


        public String toString() {
            String temp = new String();
            temp += "Teams: " + name + " W: " + win + " L: " + loss + " R: " + remain + " Games: ";
            for (int i = 0; i < game.length; i++) {
                temp += game[i] + " ";
            }
            return temp;
        }
    }


    public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
    {
        if (filename == null)
            throw new IllegalArgumentException("Empty filename!");
        In in = new In(filename);
        // System.out.println(in.readString());
        size = in.readInt();
        teams = new ArrayList<Team>();
        certify = new Bag[size];

        for (int i = 0; i < size; i++) {
            Team team = new Team();
            team.name = in.readString();
            team.win = in.readInt();
            team.loss = in.readInt();
            team.remain = in.readInt();

            int[] array = new int[size];
            for (int j = 0; j < size; j++) {
                array[j] = in.readInt();
            }
            team.game = array;
            teams.add(team);
        }

        isHopeless = new int[size];
        for (int i = 0; i < size; i++) {
            isHopeless[i] = -1;

        }

    }

    public int numberOfTeams()                        // number of teams
    {
        return size;
    }

    public Iterable<String> teams()                                // all teams
    {
        ArrayList<String> al = new ArrayList<String>();
        for (Team t : teams
        ) {
            al.add(t.name);
        }

        return al;
    }

    private void checkName(String team) {
        if (team == null)
            throw new IllegalArgumentException("Null name");
        for (Team t : teams
        ) {
            if (t.name.compareTo(team) == 0)
                return;
        }

        throw new IllegalArgumentException("No such team: " + team);

    }

    public int wins(String team)                      // number of wins for given team
    {
        checkName(team);
        int win = 0;
        for (Team t : teams
        ) {
            if (t.name.compareTo(team) == 0)
                win = t.win;
        }
        return win;
    }

    public int losses(String team)                    // number of losses for given team
    {
        checkName(team);
        int loss = 0;
        for (Team t : teams
        ) {
            if (t.name.compareTo(team) == 0)
                loss = t.loss;
        }
        return loss;
    }

    public int remaining(String team)                 // number of remaining games for given team
    {
        checkName(team);
        int remain = 0;
        for (Team t : teams
        ) {
            if (t.name.compareTo(team) == 0)
                remain = t.remain;
        }
        return remain;

    }


    public int against(String team1, String team2)    // number of remaining games between team1 and team2
    {
        checkName(team1);
        checkName(team2);

        int i = 0;
        int j = 0;
        for (int k = 0; k < size; k++) {
            if (teams.get(k).name.compareTo(team1) == 0)
                i = k;
            if (teams.get(k).name.compareTo(team2) == 0)
                j = k;

        }

        return teams.get(i).game[j];
    }

    public boolean isEliminated(String team)              // is given team eliminated?
    {
        checkName(team);
        //  One team
        if (size == 1)
            return false;

        //Check record
        int index = getIndex(team);
        if (isHopeless[index] != -1) {
            if (isHopeless[index] == 0)
                return false;
            return true;
        }

        //Check trivial
        if (checkTrivial(team) == true) {
            isHopeless[index] = 1;
            return true;
        }


        int n = size - 1;


        FlowNetwork flow = new FlowNetwork((int) (1 + 0.5 * n * (n - 1) + n + 1));

        ArrayList<String> names = (ArrayList<String>) teams();
        names.remove(team);

        int i = 1;
        int sum = 0;
        int start = (int) (0.5 * names.size() * (names.size() - 1) + 1);
        for (int j = 0; j < names.size(); j++) {
            for (int k = j + 1; k < names.size(); k++) {
                //   System.out.println(i + ": " + names.get(j) + " + " + names.get(k) + " + " + against(names.get(j), names.get(k)));
                flow.addEdge(new FlowEdge(0, i, against(names.get(j), names.get(k))));
                sum += against(names.get(j), names.get(k));
                flow.addEdge(new FlowEdge(i, start + j, Double.POSITIVE_INFINITY));
                flow.addEdge(new FlowEdge(i, start + k, Double.POSITIVE_INFINITY));
                i++;
            }
        }


        for (int j = start; j < start + names.size(); j++) {

            String teamname = names.get((j - start));
            //  System.out.println(teamname);

            int mostWin = wins(team) + remaining(team) - wins(teamname);

            // System.out.println(mostWin);

            flow.addEdge(new FlowEdge(j, start + names.size(), mostWin));

        }


        //  System.out.println("Use ff!");

        FordFulkerson ff = new FordFulkerson(flow, 0, (int) (1 + 0.5 * n * (n - 1) + n));
        // System.out.println("ff: "+ff.value());
        // System.out.println("Max: "+sum);


        boolean re = sum != (int) (ff.value());


        if (re == true) {

            Bag<String> bag = new Bag<String>();
            for (int j = start; j < start + names.size(); j++) {
                //  System.out.println(teamname);
                if (ff.inCut(j))
                    bag.add(names.get((j - start)));
            }

            certify[getIndex(team)] = bag;
        }
        return re;
    }

    //Get index of given string
    private int getIndex(String team) {
        int i = 0;
        for (; teams.get(i).name.compareTo(team) != 0; i++) {
        }
        return i;
    }

    //Check trivial situations
    private boolean checkTrivial(String team) {
        int index = getIndex(team);
        int max = teams.get(index).win + teams.get(index).remain;
        for (Team t : teams
        ) {
            if (t.win > max)
                return true;
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
    {
        checkName(team);
        int index = getIndex(team);

        if (!isEliminated(team))
            return null;
        if (certify[index] != null)
            return certify[index];

        int i = 0;

        int max = teams.get(0).win;

        for (int j = 1; j < size; j++) {

            if (teams.get(j).win > max) {
            //    System.out.println(teams.get(j).name);
                i = j;
                max = teams.get(j).win;
            }
        }


        Bag<String> bag = new Bag<String>();
        bag.add(teams.get(i).name);
        return bag;
    }


    public static void main(String[] args) {

        BaseballElimination be = new BaseballElimination("C:\\Users\\91593\\Desktop\\Algorithm\\A8\\baseball\\teams8.txt");

        Iterable<String> i = be.certificateOfElimination("Harvard");

        for (String s : i
        ) {
            System.out.println(s);
        }


//        int i = 0;
//


//        ArrayList<String> a = new ArrayList<>();
//        a.add("111");
//        a.add("222");
//        a.add("333");
//        a.remove("222");
//
//        System.out.println(a);
    }
}


