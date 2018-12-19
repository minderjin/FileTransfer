package com.turingsolution.FileTransfer;
import java.net.*;
import java.io.*;

public class FTClient {
	public static void main(String[] args){
		try{

			// 1. 서버의 IP와 서버의 동작 포트 값(10001)을 인자로 넣어 socket 생성
			Socket sock = new Socket("127.0.0.1", 10001);
			BufferedReader keyboard =
					new BufferedReader(new InputStreamReader(System.in));

			// 2. 생성된 Socket으로부터 InputStream과 OutputStream을 구함
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();

			// 3. InputStream은 BufferedReader 형식으로 변환
			//    OutputStream은 PrintWriter 형식으로 변환
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

			// 4. 키보드로부터 한 줄씩 입력받는 BufferedReader 객체 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = null;

			// 5. 키보드로부터 한 줄을 입력받음
			while((line = keyboard.readLine()) != null){
				if(line.equals("quit")) break;

				// 6. PrintWriter에 있는 println() 메소드를 이용해 서버에게 전송
				pw.println(line);
				pw.flush();

				// 7. 서버가 다시 반환하는 문자열을 BufferedReader에 있는
				//    readLine()을 이용해서 읽어들임
				String echo = br.readLine();
				System.out.println("서버로부터 전달받은 문자열 :" + echo);
			}

			pw.close();
			br.close();
			
			
			DataInputStream dis = new DataInputStream(
					new FileInputStream(new File("C:\\util\\maverick-ant.zip")));
			DataOutputStream dos = new DataOutputStream(out);
			byte[] buf = new byte[1024];
			while(dis.read(buf)>0)
			{
				dos.write(buf);
				dos.flush();
			}
			dos.close();
			dis.close();


			sock.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
}