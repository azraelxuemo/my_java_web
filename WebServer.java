import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public  class WebServer {
    public WebServer(){
        try{
            ServerSocket serverSocket=new ServerSocket(8080);
            System.out.println("Listen 0.0.0.0:8080");
            System.out.println("You can visit from http://127.0.0.1:8080/");
            while (true){
                Socket socket=serverSocket.accept();
                HandleAClient task=new HandleAClient(socket);
                new Thread(task).start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new WebServer();
    }
}