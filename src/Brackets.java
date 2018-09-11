import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class Brackets {

	private long sum = 0;
	private long stack_depth = 0;
	private Stack<String> bracketStack= null;
	private Stack<stackInfo> bracketInnerStack= null;
	
	private String bracketsContent = "";
	private File ReadFile;
	private FileReader fileReader;
	private BufferedReader bufReader;
	private File WriteFile;
	private BufferedWriter bufWriter;
	
	//테스트 케이스 파일 이름 입력
	//ex : Brackets("1.input.txt")
	//output : "1.input.txt_result.txt"
	public void Brackets(String fileName){
		
		try {
			ReadFile = new File(fileName);
			fileReader = new FileReader(ReadFile);
			bufReader = new BufferedReader(fileReader);
			
			WriteFile = new File(fileName+"_result.txt");
			bufWriter = new BufferedWriter(new FileWriter(WriteFile));
			
			String readLine = "";
			int count = 0;
			int i = 0;
			
			if( ( readLine = bufReader.readLine() ) != null  ){
				count = Integer.valueOf(readLine);
				
				//테스트 케이스 수 검증
				if(Integer.signum(count) < 0 || Integer.signum(count) > 100)
					return;
			}
			
			while( ( readLine = bufReader.readLine() ) != null && i < count ){
				
				bracketsContent = readLine;
				
				if(WriteFile.isFile() && WriteFile.canWrite()){		
					bufWriter.write( String.valueOf(Process(bracketsContent)) );
					bufWriter.newLine();
					i++;
				}		
			}
			
			bufReader.close();
			fileReader.close();
			ReadFile = null;
			
			bufWriter.close();
			WriteFile = null;
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//테스트 케이스 문 수행
	public long Process(String BracketsString){
		
		bracketStack = new Stack<>();	//괄호스택
		bracketInnerStack = new Stack<stackInfo>();	//괄호 계산스택
		stack_depth=0;	//부호 연산 레벨 처리
		sum = 0;
		
		if(BracketsString.length()>10000)	//최대길이 검사
			return 0;
		
		//괄호 연산
		for(int i=BracketsString.length()-1;i>=0;i--)
		{
			
			String token = String.valueOf(BracketsString.charAt(i));
			
			//symbol check.
			if( token.equals('{') || token.equals('(') || token.equals( '[') || token.equals('}') || token.equals( ']') || token.equals( ')' ) )  
				return 0;
			
			//닫힌괄호만 삽입
			if(token.equals(")") ||token.equals("]") || token.equals("}") ){
				bracketStack.push(token);
				stack_depth++;
			}
			else{	//open symbol.
				String Popdata = "";
				
				if(!bracketStack.isEmpty())
					Popdata = bracketStack.pop();
				
				if(convertValue(token+Popdata) == 0 )	//symbol error.
					return 0;
				
				if(bracketInnerStack.size() == 0 ){
					bracketInnerStack.push(new stackInfo(convertValue(token+Popdata),stack_depth));
					stack_depth =bracketStack.size();
				}
				else{
					long popDatValue = 0;
					long depthCheck = 0;
					stackInfo popDataInfo;
					
					popDataInfo = bracketInnerStack.pop();

					if(stack_depth+1 == popDataInfo.getLevel())		// 연산대상이 다른 레벨인지 검사
						depthCheck = 1;
					else if(stack_depth == popDataInfo.getLevel())	// 연산대상이 같은 레벨인지 검사
						depthCheck = 0;
					bracketInnerStack.push(popDataInfo);
					
					while(bracketInnerStack.size() > 0 ) {
						
						popDataInfo = bracketInnerStack.pop();
						
						//위에서 지정한 연산레벨까지 꺼내서 계산
						if(depthCheck == popDataInfo.getLevel() - stack_depth )	
							popDatValue += popDataInfo.getValue()%100000000;
						else{
							bracketInnerStack.push(popDataInfo);
							break;
						}
					}
					
					if(depthCheck == 1)
						bracketInnerStack.push( new stackInfo(convertValue(token+Popdata)* (popDatValue%100000000) ,stack_depth) );
					else if(depthCheck == 0)
						bracketInnerStack.push( new stackInfo(convertValue(token+Popdata)+ (popDatValue%100000000) ,stack_depth) );
					else
						return 0;
					
					stack_depth = bracketStack.size();	
				}
				
			}	
			
		}
		
		//괄호 출력
		if(!bracketStack.isEmpty())
			return 0;
		else{
			while(bracketInnerStack.size() > 0){
				stackInfo popDataInfo = bracketInnerStack.pop();
				sum += popDataInfo.getValue();
			}
		}
				
		return sum%100000000;
	}
	
	//괄호->숫자 컨버팅
	private long convertValue(String Bracket){
		
		if( Bracket.equals( "()"))
			return 1;
		else if(Bracket.equals("{}"))
			return 2;
		else if(Bracket.equals("[]"))
			return 3;
		else
			return 0;
	}
}

//연산 스택 저장(값, 괄호  연산 레벨)
class stackInfo{
	
	long value;
	long level;
	
	stackInfo(){
		value=0;
		level=0;
	}
	
	stackInfo(long val, long lev){
		value = val;
		level = lev;
	}
	
	public long getValue(){
		return value;
	}
	public long getLevel(){
		return level;
	}
	
}
