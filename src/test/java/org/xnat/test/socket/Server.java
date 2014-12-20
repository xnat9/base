package org.xnat.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			while (true) {
				ServerSocket ss = new ServerSocket(1234);
				Socket s = ss.accept();
				InputStream is = s.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String str = br.readLine();
				System.out.println(str);
				br.close(); is.close(); s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
