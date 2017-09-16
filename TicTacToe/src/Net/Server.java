package Net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import GameState.BoardState;

public class Server extends Connection {
    static ServerSocket s;

    public Server(String username, int port, BoardState b) throws IOException {
        super(username, makeSocket(port), b);
        turn = (int) (Math.random() * 2) + 1;
        out.println("turn:" + (turn == 1 ? 2 : 1));
    }

    public static Socket makeSocket(int port) throws IOException {
        s = new ServerSocket(port);
        return s.accept();
    }

    @Override
    public void getInput2(String s) {
        // TODO Auto-generated method stub

    }

}
