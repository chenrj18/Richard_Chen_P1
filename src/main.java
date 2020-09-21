import java.io.FileInputStream;
import java.util.Scanner;

public class main extends Command {

    public static void main(String[] args) throws Exception {
        Scanner reader = null;
        if (args.length == 0) {
            System.out.println("No input file, using default");
            try {
                reader = new Scanner(new FileInputStream("cmd.txt"));
            } catch (Exception e) {
                System.out.println("Missing default");
            }
        } else {
            reader = new Scanner(new FileInputStream(args[0]));
        }
        Command commands = new Command();
        while (reader.hasNextLine()) {//takes file and executes the command from each line
            String line = reader.nextLine();
            String[] command = line.split(" ");
            if (command[0].equals("setState")) {
                commands.setState(command[1]);
            } else if (command[0].equals("printState")) {
                commands.printState();
            } else if (command[0].equals("move")) {
                commands.move(command[1]);
            } else if (command[0].equals("randomizeState")) {
                commands.randomizeState(Integer.parseInt(command[1]));
            } else if (command[0].equals("solve_A_star")) {
                commands.solve_A_star(command[1]);
            } else if (command[0].equals("solve_beam")) {
                commands.solve_beam(Integer.parseInt(command[1]));
            } else if (command[0].equals("maxNodes")) {
                commands.maxNodes(Integer.parseInt(command[1]));
            } else if (command[0].equals("resetmove")) {
                commands.resetmove();
            } else if (command[0].equals("total_move")) {
                commands.total_move(Boolean.parseBoolean(command[1]));
            } else if (command[0].equals("resetsolved")) {
                commands.resetsolved();
            } else if (command[0].equals("n_solved")) {
                commands.n_solved(Boolean.parseBoolean(command[1]));
            } else if (command[0].equals("randomize_exp_State")) {
                commands.randomize_exp_State(Integer.parseInt(command[1]));
            }

        }
        reader.close();
        // below is code for experiments
//        int mn = 0;
//        int rs = 0;
//        //mn = -1; // set this for when you want to measure with no node limit
//        final List<Integer> mov = new ArrayList<>();
//        final List<Integer> solution = new ArrayList<>();
//        for(int kk =0; kk< 10;kk++){
//            mn = mn + 5;
//            commands.maxNodes(mn);
//        long startTime1 = System.nanoTime();
//        for(int jj = 0; jj<10; jj++) {
//                rs = rs + 5;
//                for (int ii = 0; ii < 100; ii++) {
//                    commands.randomizeState(rs);
//                    commands.solve_A_star("h1");
//                }
//            }
//        long stopTime1 = System.nanoTime();
//        solution.add(commands.n_solved(false));
//            commands.resetsolved();
//            rs = 0;
//        long startTime2 = System.nanoTime();
//        for(int jj = 0; jj<10; jj++) {
//                rs = rs + 5;
//                for (int ii = 0; ii < 100; ii++) {
//                    commands.randomizeState(rs);
//                    commands.solve_A_star("h2");
//                }
//            }
//        long stopTime2 = System.nanoTime();
//        solution.add(commands.n_solved(false));
//            commands.resetsolved();
//            rs = 0;
//        long startTime3 = System.nanoTime();
//        for(int jj = 0; jj<10; jj++) {
//                rs = rs + 5;
//                for (int ii = 0; ii < 100; ii++) {
//                    commands.randomizeState(rs);
//                    commands.solve_beam(10);
//                }
//            }
//        long stopTime3 = System.nanoTime();
//        solution.add(commands.n_solved(false));
//            commands.resetsolved();
//            rs = 0;
//        long startTime4 = System.nanoTime();
//        for(int jj = 0; jj<10; jj++) {
//                rs = rs + 5;
//                for (int ii = 0; ii < 100; ii++) {
//                    commands.randomizeState(rs);
//                    commands.solve_beam(100);
//                }
//            }
//        long stopTime4 = System.nanoTime();
//        solution.add(commands.n_solved(false));
//            commands.resetsolved();
//       }
//        commands.resetsolved();
//        commands.resetmove();
//        commands.maxNodes(-1);
//        for(int jj = 0; jj<10; jj++) {
//            rs = rs + 5;
//            for (int ii = 0; ii < 100; ii++) {
//                commands.randomizeState(rs);
//                commands.solve_A_star("h1");
//            }
//        }
//        mov.add(commands.total_move(false));
//        commands.resetmove();
//        rs = 0;
//        for(int jj = 0; jj<10; jj++) {
//            rs = rs + 5;
//            for (int ii = 0; ii < 100; ii++) {
//                commands.randomizeState(rs);
//                commands.solve_A_star("h2");
//            }
//        }
//        mov.add(commands.total_move(false));
//        commands.resetmove();
//        rs = 0;
//        for(int jj = 0; jj<10; jj++) {
//            rs = rs + 5;
//            for (int ii = 0; ii < 100; ii++) {
//                commands.randomizeState(rs);
//                commands.solve_beam(10);
//            }
//        }
//        mov.add(commands.total_move(false));
//        commands.resetmove();
//        rs = 0;
//        for(int jj = 0; jj<10; jj++) {
//            rs = rs + 5;
//            for (int ii = 0; ii < 100; ii++) {
//                commands.randomizeState(rs);
//                commands.solve_beam(100);
//            }
//        }
//        mov.add(commands.total_move(false));
//         int counter = 0;
//        for (int ii=0; ii < mov.size(); ii++) {
//            System.out.print((double)mov.get(ii)/(double)1000 + " ");
//            counter++;
//            if ((counter)% 4== 0){
//                System.out.print("|");
//            }
//        }
//        counter = 0;
//        System.out.println("");
//        System.out.println("---------------------------------------------");
//        for (int ii=0; ii < solution.size(); ii++) {
//            System.out.print((double)solution.get(ii)/(double)1000+ " ");
//            counter++;
//            if ((counter)% 4== 0){
//                System.out.print("|");
//
//            }
//        }
//        System.out.println("");
//        System.out.println(stopTime1-startTime1);
//        System.out.println(stopTime2-startTime2);
//        System.out.println(stopTime3-startTime3);
//        System.out.println(stopTime4-startTime4);
    }
}





