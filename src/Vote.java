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
	
	
	//�׽�Ʈ ���̽� ���� �̸� �Է�
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
			
				if(readLine.length()<=50)	//�̸� 50�� �̳��� ��츸 ����
				{
					if(map.containsKey(readLine)){	//�ߺ����� �ִ� ��� �� 1 ����
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
			
			if(highVote < score){	//�ְ� ��ǥ�� ���
				highVote = score;
				highVoteList.clear();
				highVoteList.add(key);
				
			}
			else if(highVote == score)	//�ְ� ��ǥ�ڿ� ������ ���
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
