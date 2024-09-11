package console.academy;
/*
	선행작업 :
		1. http://www.java2s.com/Code/Jar/j/Downloadjlayer101jar.htm에서
		   Jar File Download/j/jlayer를 클릭 - 5번인 Download jlayer-1.0.1-1.jar클릭해서        	
       	   jlayer-1.0.1-1.jar.zip( 128 k) 다운로드 한다
        2. 프로젝트 선택후 src와 같은 수준의 위치에 lib폴더 만든후 다운로드한 jar붙여넣기
           프로젝트 선택후 마우스 우클릭->Build Path->Configure Build Path->
           Libraries탭에서 Classpath선택후->Add External Jars후 lib폴더에 있는
           라이브러리 선택하여 클래스패스에 추가
        3. 프로젝트 선택후 music폴더 만들어 mp3파일 저장
*/

import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class MediaPlayer extends Thread{
	private Player player;
	private FileInputStream fis;
	String filePath;
	
	public MediaPlayer(String filePath) {
		this.filePath = filePath;
	} //MediaPlayer
	
	//재생
	void play() {
		try {
		fis = new FileInputStream(filePath);
		player = new Player(fis);
		player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} //play
	//중지
	void stop_() {
		if(player != null) {
			player.close();
			player=null;
		}
	} //stop_
	@Override
	public void run() {
		play(); //음악 재생
	} //run
	
} ////class MediaPlayer
