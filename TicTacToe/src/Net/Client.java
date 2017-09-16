package Net;

import java.io.IOException;
import java.net.Socket;

import GameState.BoardState;

public class Client extends Connection {

    public Client(String username, String host, int port, BoardState b)
            throws IOException {
        super(username, new Socket(host, port), b);
    }

    @Override
    public void getInput2(String s) {
        if (s.startsWith("turn:")) {
            turn = s.charAt(5) - '0';
        }
    }
}
