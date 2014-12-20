package org.xnat.test.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 1234);
			OutputStream os = s.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write("ddddddddddddddddddddd");
			bw.flush();
			bw.close(); os.close(); s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
