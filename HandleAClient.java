import javax.script.ScriptException;
import java.io.*;
import java.net.Socket;

public class HandleAClient implements  Runnable {
    private HttpRequest httpRequest;
    private  HttpResponse httpResponse;
    public HandleAClient(Socket socket) throws IOException {
            this.httpRequest=new HttpRequest(new DataInputStream(socket.getInputStream()));
            this.httpResponse=new HttpResponse(new DataOutputStream(socket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            try {
                httpResponse.response(httpRequest.HandleHttpRequests());
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}