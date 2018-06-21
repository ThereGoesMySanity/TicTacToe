package Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import GameState.BoardState;

public abstract class Connection extends Thread {
    static Pattern p = Pattern.compile("move:(\\d+),(\\d+)");
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String userTwo;
    BoardState b;
    int turn;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Connection(String username, Socket s, BoardState b)
            throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("user:" + username);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (in.ready()) {
                    getInput(in.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void makeMove(int x, int y) {
        if (turn == b.getTurn()) {
            out.println("move:" + x + "," + y);
        }
    }

    public void getInput(String s) {
        getInput2(s);
        if (s.startsWith("user:")) {
            userTwo = s.substring(5);
        }
        if (s.startsWith("move:")) {
            Matcher m = p.matcher(s);
            if (m.matches()) {
                b.makeMove(Integer.parseInt(m.group(1)),
                        Integer.parseInt(m.group(2)));
            }
        }
    }

    public abstract void getInput2(String s);
}
