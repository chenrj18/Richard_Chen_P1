import java.util.Random;
import java.util.*;

public class Command {
    private String[][] p_State = {{"b", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"}};
    private int m_nodes = -1;
    private int t_move = 0;
    private int num_solved = 0;

    
    public void setState(String state) {
        // detects the 9 string variables and sets present state based on it
        int jj = 0;
        for (int i = 0; i < state.length(); i++) {
            if (!String.valueOf(state.charAt(i)).equals(" ")) {
                if (jj < 3) {
                    p_State[0][jj] = String.valueOf(state.charAt(i));
                    jj++;
                } else if (jj < 6) {
                    p_State[1][jj - 3] = String.valueOf(state.charAt(i));
                    jj++;
                } else {
                    p_State[2][jj - 6] = String.valueOf(state.charAt(i));
                    jj++;
                }

            }
        }
    }

    public void printState() {
        //Prints state in the same set up as input string
        String toprint = "";
        for (int i = 0; i < p_State.length; i++) {
            for (int j = 0; j < p_State[i].length; j++) {
                toprint = toprint.concat(p_State[i][j]);
            }
            toprint = toprint.concat(" ");
        }
        System.out.println(toprint);
    }

    public boolean move(String direction) {
        //moves the blank space in 1 of 4 directions. Returns true if successful/doable, false if impossible
        int loc_x = 0;
        int loc_y = 0;
        boolean found = false;
        for (int i = 0; i < p_State.length; ++i) {
            if (found)
                break;
            for (int j = 0; j < p_State.length; ++j) {
                if (p_State[i][j].equals("b")) {
                    loc_x = j;
                    loc_y = i;
                    found = true;
                    break;
                }
            }
        }

        if (direction.equals("up")) {
            if (loc_y == 0)
                return false;
            else {
                String temp = p_State[loc_y][loc_x];
                p_State[loc_y][loc_x] = p_State[loc_y - 1][loc_x];
                p_State[loc_y - 1][loc_x] = temp;
                return true;
            }
        } else if (direction.equals("down")) {
            if (loc_y == 2)
                return false;
            else {
                String temp = p_State[loc_y][loc_x];
                p_State[loc_y][loc_x] = p_State[loc_y + 1][loc_x];
                p_State[loc_y + 1][loc_x] = temp;
                return true;
            }
        } else if (direction.equals("left")) {
            if (loc_x == 0)
                return false;
            else {
                String temp = p_State[loc_y][loc_x];
                p_State[loc_y][loc_x] = p_State[loc_y][loc_x - 1];
                p_State[loc_y][loc_x - 1] = temp;
                return true;
            }
        } else if (direction.equals("right")) {
            if (loc_x == 2)
                return false;
            else {
                String temp = p_State[loc_y][loc_x];
                p_State[loc_y][loc_x] = p_State[loc_y][loc_x + 1];
                p_State[loc_y][loc_x + 1] = temp;
                return true;
            }
        }
        return false;
    }

    public void randomizeState(int moves) {
        // initializes from goal state, and then makes n random doable moves
        String[][] goal_State = {{"b", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"}};
        setState(translate(goal_State));
        int direc;
        boolean moved;
        Random rand = new Random(0);
        for (int i = 0; i < moves; i++) {
            moved = false;
            while (!moved) {
                direc = rand.nextInt(4);
                if (direc == 0) {
                    if (move("up")) {
                        moved = true;
                    }
                } else if (direc == 1) {
                    if (move("down")) {
                        moved = true;
                    }
                } else if (direc == 2) {
                    if (move("left")) {
                        moved = true;
                    }
                } else if (direc == 3) {
                    if (move("right")) {
                        moved = true;
                    }
                }
            }
        }
    }

    public void solve_A_star(String heuristic) {
        // initializes the starting state
        String[][] goal_State = {{"b", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"}};
        String[][] start_state = new String[3][3];
        for (int i = 0; i < p_State.length; i++)
            for (int j = 0; j < p_State[i].length; j++)
                start_state[i][j] = p_State[i][j];
        int h_value = 0;
        int num_nodes = 1;
        int x;
        int y;
        // calculates initial heuristic of 1st state
        if (heuristic.equals("h1")) {
            for (int ii = 0; ii < p_State.length; ii++) {
                for (int j = 0; j < p_State[ii].length; j++) {
                    if (!p_State[ii][j].equals(goal_State[ii][j])) {
                        h_value++;
                    }
                }
            }
        } else if (heuristic.equals("h2")) {
            for (int ii = 0; ii < p_State.length; ii++) {
                for (int j = 0; j < p_State[ii].length; j++) {
                    if (p_State[ii][j].equals("b")) {
                        h_value = h_value + ii + j;
                    } else if (Integer.parseInt(p_State[ii][j]) <= 2) {
                        x = Math.abs(j - Integer.parseInt(p_State[ii][j]));
                        h_value = h_value + ii + x;
                    } else if (Integer.parseInt(p_State[ii][j]) <= 5) {
                        y = Math.abs(ii - 1);
                        x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 3));
                        h_value = h_value + y + x;
                    } else {
                        y = Math.abs(ii - 2);
                        x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 6));
                        h_value = h_value + y + x;
                    }
                }
            }
        }
        //sets up node of initial state
        Node root = new Node(h_value, 0, null, "", start_state);
        // sets up storage. hashmap for previous explored states, and queue for to explore staes
        String[] option = {"up", "down", "left", "right"};
        HashMap<String, Integer> explored = new HashMap<String, Integer>();
        explored.put(translate(start_state), 0);
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::returnfn));
        queue.add(root);
        Node next;
        boolean solved = false;
        boolean full = false;
        while (true) {//loops until max node or solution
            if (queue.peek() == null) {//check if empty
                break;
            }
            next = queue.poll();//pulls from queue
            if (Arrays.deepEquals(next.returnc_state(), goal_State)) {//checks solution
                solved = true;
                num_solved++;
                t_move = t_move + next.returnmovement();
                setState(translate(next.returnc_state()));
                next.printSolution(next);
                System.out.println("-> End");
                System.out.println("Number of movements: " + next.returnmovement());
                break;
            }
            if (full) {//check if max node reached
                break;
            }
            for (int i = 0; i < option.length; i++) {//explores option here
                setState(translate(next.returnc_state()));
                if (m_nodes >= 0 && num_nodes >= m_nodes) {
                    full = true;
                    break;
                }
                if (!move(option[i])) {
                    continue;
                }
                h_value = 0;
                if (heuristic.equals("h1")) {//calculate h1
                    for (int ii = 0; ii < p_State.length; ii++) {
                        for (int j = 0; j < p_State[ii].length; j++) {
                            if (!p_State[ii][j].equals(goal_State[ii][j])) {
                                h_value++;
                            }
                        }
                    }
                } else if (heuristic.equals("h2")) {//calculate h2
                    for (int ii = 0; ii < p_State.length; ii++) {
                        for (int j = 0; j < p_State[ii].length; j++) {
                            if (p_State[ii][j].equals("b")) {
                                h_value = h_value + ii + j;
                            } else if (Integer.parseInt(p_State[ii][j]) <= 2) {
                                x = Math.abs(j - Integer.parseInt(p_State[ii][j]));
                                h_value = h_value + ii + x;
                            } else if (Integer.parseInt(p_State[ii][j]) <= 5) {
                                y = Math.abs(ii - 1);
                                x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 3));
                                h_value = h_value + y + x;
                            } else {
                                y = Math.abs(ii - 2);
                                x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 6));
                                h_value = h_value + y + x;
                            }

                        }

                    }

                }

                String[][] s_state = new String[3][3];
                for (int iii = 0; iii < p_State.length; iii++) {
                    for (int jj = 0; jj < p_State[iii].length; jj++) {
                        s_state[iii][jj] = p_State[iii][jj];
                    }
                }

                int movement = next.returnmovement() + 1;
                int fn = movement + h_value;
                String direction = option[i];
                Node node = new Node(fn, movement, next, direction, s_state);//new node
                if (explored.containsKey(translate(s_state))) {//checks if already seen
                    if (explored.get(translate(s_state)) <= (next.returnmovement() + h_value)) {
                        continue;
                    } else {
                        explored.replace(translate(s_state), next.returnmovement() + h_value);
                    }
                } else {
                    explored.put(translate(s_state), next.returnmovement() + h_value);
                }
                queue.add(node);
                num_nodes++;
            }
        }
        if (full && !solved) {// checks if it solved before max node
            System.out.println("Reached max nodes");
        }
    }

    public void solve_beam(int k) {
        int x;
        int y;
        String[][] goal_State = {{"b", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"}};
        String[][] start_state = new String[3][3];
        for (int i = 0; i < p_State.length; i++)
            for (int j = 0; j < p_State[i].length; j++)
                start_state[i][j] = p_State[i][j];
        int h_value = 0;
        for (int ii = 0; ii < p_State.length; ii++) {
            for (int j = 0; j < p_State[ii].length; j++) {
                if (!p_State[ii][j].equals(goal_State[ii][j])) {
                    h_value++;
                }
            }
        }
        for (int ii = 0; ii < p_State.length; ii++) {
            for (int j = 0; j < p_State[ii].length; j++) {
                if (p_State[ii][j].equals("b")) {
                    h_value = h_value + ii + j;
                } else if (Integer.parseInt(p_State[ii][j]) <= 2) {
                    x = Math.abs(j - Integer.parseInt(p_State[ii][j]));
                    h_value = h_value + ii + x;
                } else if (Integer.parseInt(p_State[ii][j]) <= 5) {
                    y = Math.abs(ii - 1);
                    x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 3));
                    h_value = h_value + y + x;
                } else {
                    y = Math.abs(ii - 2);
                    x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 6));
                    h_value = h_value + y + x;
                }
            }
        }
        Node root = new Node(h_value, 0, null, "", start_state);//make node of initial state
        int num_nodes = 1;
        Node n_temp;
        String[] option = {"up", "down", "left", "right"};
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::returnfn));// where all succssor nodes go
        PriorityQueue<Node> evaluating = new PriorityQueue<>(Comparator.comparingInt(Node::returnfn));//to take k nodes from main queue
        HashMap<String, Integer> explored = new HashMap<String, Integer>();//for explored nodes
        explored.put(translate(start_state), h_value);
        queue.add(root);
        Node next;
        boolean solved = false;
        boolean full = false;
        while (true) {//loop until max node or solution
            if (queue.peek() == null) {
                break;
            }
            for (int z = 0; z < k; z++) {//take k nodes to explore from the generated node list
                n_temp = queue.poll();

                if (n_temp != null) {
                    evaluating.add(n_temp);
                } else {
                    continue;
                }
                if (n_temp.returnfn() == 0) {//check if found soluton
                    solved = true;
                    num_solved++;
                    t_move = t_move + n_temp.returnmovement();
                    setState(translate(n_temp.returnc_state()));
                    n_temp.printSolution(n_temp);
                    System.out.println("-> End");
                    System.out.println("Number of movements: " + n_temp.returnmovement());
                    break;
                }
            }
            if (solved)
                break;
            if (full)
                break;
            queue.clear();
            for (int z = 0; z < evaluating.size(); z++) {//repeats for all k nodes
                next = evaluating.poll();
                if (next == null) {
                    break;
                }
                if (m_nodes >= 0 && num_nodes >= m_nodes) {
                    full = true;
                    break;
                }
                for (int i = 0; i < option.length; i++) {//explore option of nodes
                    setState(translate(next.returnc_state()));
                    if (!move(option[i])) {
                        continue;
                    }
                    if (m_nodes >= 0 && num_nodes >= m_nodes) {
                        full = true;
                        break;
                    }
                    h_value = 0;
                    for (int ii = 0; ii < p_State.length; ii++) {
                        for (int j = 0; j < p_State[ii].length; j++) {
                            if (!p_State[ii][j].equals(goal_State[ii][j])) {
                                h_value++;
                            }
                        }
                    }
                    for (int ii = 0; ii < p_State.length; ii++) {
                        for (int j = 0; j < p_State[ii].length; j++) {
                            if (p_State[ii][j].equals("b")) {
                                h_value = h_value + ii + j;
                            } else if (Integer.parseInt(p_State[ii][j]) <= 2) {
                                x = Math.abs(j - Integer.parseInt(p_State[ii][j]));
                                h_value = h_value + ii + x;
                            } else if (Integer.parseInt(p_State[ii][j]) <= 5) {
                                y = Math.abs(ii - 1);
                                x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 3));
                                h_value = h_value + y + x;
                            } else {
                                y = Math.abs(ii - 2);
                                x = Math.abs(j - (Integer.parseInt(p_State[ii][j]) - 6));
                                h_value = h_value + y + x;
                            }
                        }
                    }
                    String[][] s_state = new String[3][3];
                    for (int iii = 0; iii < p_State.length; iii++) {
                        for (int jj = 0; jj < p_State[iii].length; jj++) {
                            s_state[iii][jj] = p_State[iii][jj];
                        }
                    }
                    int fn = h_value;
                    int movement = next.returnmovement() + 1;
                    String direction = option[i];
                    if (explored.containsKey(translate(s_state))) {
                        continue;
                    } else {
                        explored.put(translate(s_state), h_value);
                    }
                    Node node = new Node(fn, movement, next, direction, s_state);// new node generated
                    queue.add(node);
                    num_nodes++;
                }
            }
        }
        if (full && !solved) {
            System.out.println("Reached max nodes");
        }
    }

    public void maxNodes(int n) {
        m_nodes = n;
    }

    public String translate(String[][] orig) {// translates array into string
        String toreturn = "";
        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < orig[i].length; j++) {
                toreturn = toreturn.concat(orig[i][j]);
            }
        }
        return toreturn;
    }

    public void randomize_exp_State(int moves) {
        // initializes from goal state, and then makes n random doable moves
        String[][] goal_State = {{"b", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"}};
        setState(translate(goal_State));
        HashMap<String, Integer> explored = new HashMap<String, Integer>();
        explored.put(translate(goal_State), 0);
        int direc;
        boolean moved;
        String[][] previousState = new String[3][3];
        copyState(goal_State, previousState);
        Random rand = new Random(0);
        for (int i = 0; i < moves; i++) {
            String[][] copyState = new String[3][3];
            copyState(p_State, previousState);
            moved = false;
            while (!moved) {
                direc = rand.nextInt(4);
                if (direc == 0) {
                    if (move("up")) {
                        copyState(p_State, copyState);
                        if (explored.containsKey(translate(copyState))) {
                            setState(translate(previousState));
                        } else {
                            explored.put(translate(copyState), 0);
                            moved = true;
                        }
                    }
                } else if (direc == 1) {
                    if (move("down")) {

                        copyState(p_State, copyState);
                        if (explored.containsKey(translate(copyState))) {
                            setState(translate(previousState));
                        } else {
                            explored.put(translate(copyState), 0);
                            moved = true;
                        }
                    }
                } else if (direc == 2) {
                    if (move("left")) {
                        copyState(p_State, copyState);
                        if (explored.containsKey(translate(copyState))) {
                            setState(translate(previousState));
                        } else {
                            explored.put(translate(copyState), 0);
                            moved = true;
                        }
                    }
                } else if (direc == 3) {
                    if (move("right")) {
                        //printState();
                        copyState(p_State, copyState);
                        if (explored.containsKey(translate(copyState))) {
                            setState(translate(previousState));
                        } else {
                            explored.put(translate(copyState), 0);
                            moved = true;
                        }
                    }
                }
            }
        }
    }

    public String[][] copyState(String[][] orig, String[][] copy) {
        for (int ii = 0; ii < orig.length; ii++) {
            for (int j = 0; j < orig[ii].length; j++) {
                copy[ii][j] = orig[ii][j];
            }
        }

        return copy;
    }

    public void resetmove() {//resets total moves made
        t_move = 0;
    }

    public int total_move(boolean print) {//if true, prints total moves made. Otherwise, just returns the number
        if (print)
            System.out.println("Total moves made for all puzzles: " + t_move);
        return t_move;
    }

    public void resetsolved() {//resets total puzzles solved
        num_solved = 0;
    }

    public int n_solved(boolean print) {//if true, prints total puzzles solved. Otherwise, just returns the number
        if (print)
            System.out.println("Total solved puzzles: " + num_solved);
        return num_solved;
    }
}

