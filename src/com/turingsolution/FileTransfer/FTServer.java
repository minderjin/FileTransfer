package com.turingsolution.FileTransfer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
 
public class FTServer {
	public static void main(String[] args){
		Socket sock = null;
		try{

			// 1. 10001번 포트에서 동작하는 ServerSocket을 생성
			ServerSocket server = new ServerSocket(10001);
			System.out.println("Wating Connect ..");

			// 2. ServerSocket의 accept() 메소드를 실행해서 클라이언트의 접속을 대기
			// : 클라이언트가 접속할 경우 accept() 메소드는 Socket 객체를 반환
			sock = server.accept();

			InetAddress  inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress()+ " 로부터 접속했습니다.");

			// 3. 반환받은 Socket으로부터 InputStream과 OutputStream을 구함
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();

			// 4. InputStream은 BufferedReader 형식으로 변환
			//OutputStream은 PrintWriter 형식으로 변환
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = null;
			//5. BufferedReader의 readLine() 메소드를 이용해
			//   클라이언트가 보내는 문자열 한 줄을 읽어들임
			while((line = br.readLine()) != null){
				System.out.println("클라이언트로부터 전송받은 문자열 : "+line);
				// 6. PrintWriter의 println을 이용해 다시 클라이언트로 전송
				pw.println(line);
				pw.flush();
			}
			// 6. IO 객체와 소켓의 close() 메소드 호출
			pw.close();
			br.close();

			

			System.out.println("수신중....");
			try{
				BufferedReader rd = new BufferedReader(new InputStreamReader(in));
				String str = rd.readLine();
				System.out.println("수신중인 파일 이름 : " + str);
				File f = new File("c:\\", str);
				FileOutputStream output = new FileOutputStream(f);
				byte[] buf = new byte[1024];
				while(sock.getInputStream().read(buf)>0)
				{
					output.write(buf);
					output.flush();
				}
				rd.close();
				output.close();
				System.out.println(str+"수신완료");
           }
           catch(Exception e){
               System.out.println("서버 에러!!");
               e.printStackTrace();
           } 

            sock.close();
            
		} catch(Exception e){
			System.out.println(e);
		}
		finally{
        }
	}
}
