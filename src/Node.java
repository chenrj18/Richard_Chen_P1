public class Node {
    private final int f;
    private final int move;
    private final String choose;
    private final String[][] cu_state;
    private final Node par;

    public Node(int fn, int movement, Node parent, String choosen, String[][] c_state) {
        f = fn;
        move = movement;
        par = parent;
        choose = choosen;
        cu_state = c_state;
    }

    public int returnfn() {
        return f;
    }

    public int returnmovement() {
        return move;
    }

    public Node returnNode() {
        return par;
    }

    public String returnchoosen() {
        return choose;
    }

    public String[][] returnc_state() {
        return cu_state;
    }

    public void printc_State() {
        String toprint = "";
        for (int i = 0; i < cu_state.length; i++) {
            for (int j = 0; j < cu_state[i].length; j++) {
                toprint = toprint.concat(cu_state[i][j]);
            }
            toprint = toprint.concat(" ");
        }
        System.out.println(toprint);
    }

    public void printSolution(Node n) {
        if (n.returnmovement() == 0) {
            System.out.print("Start: ");
        } else {
            printSolution(n.returnNode());
            System.out.print(n.returnchoosen() + " ");
        }
    }
}
