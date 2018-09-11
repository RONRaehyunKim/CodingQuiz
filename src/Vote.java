import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Vote {
	
	private HashMap<String,Integer> map = new HashMap<String, Integer>();
	private int highVote = 0;
	private List<String> highVoteList = new ArrayList<String>();
	
	private FileReader fileReader;
	private BufferedReader bufReader;
	private BufferedWriter bufferedWriter;
	
	
	//테스트 케이스 파일 이름 입력
	//ex : Brackets("1.input.txt")
	//output : "1.input.txt_result.txt"
	public void Vote(String fileName){
		
		File file = new File(fileName);
		try {
			fileReader = new FileReader(file);
			bufReader = new BufferedReader(fileReader);
			String readLine = "";
			map.clear();
			highVoteList.clear();
			highVote=0;
			
			while( ( readLine = bufReader.readLine() ) != null){
			
				if(readLine.length()<=50)	//이름 50자 이내인 경우만 삽입
				{
					if(map.containsKey(readLine)){	//중복인이 있는 경우 값 1 증가
						int mapValue = map.get(readLine)+1;
						map.put(readLine, mapValue);					
					}
					else
						map.put(readLine, 1);
				}	
			}
			bufReader.close();
			fileReader.close();
			file = null;
			
			Process();
			
			//file write.
			file = new File(fileName+"_result.txt");
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			
			if(file.isFile() && file.canWrite()){
				
				for(String key : highVoteList){
					bufferedWriter.write(key);
					bufferedWriter.newLine();
				}
				bufferedWriter.close();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	private void Process(){
		
		for(String key : map.keySet()){
			int score = map.get(key);
			
			if(highVote < score){	//최고 득표인 경우
				highVote = score;
				highVoteList.clear();
				highVoteList.add(key);
				
			}
			else if(highVote == score)	//최고 득표자와 동일한 경우
				highVoteList.add(key);
		}
		
		//list sort.
		Collections.sort(highVoteList, new Comparator<String>() {
			@Override
			public int compare(String str1, String str2){
				return str1.compareTo(str2);
			}
		});
	}
}
