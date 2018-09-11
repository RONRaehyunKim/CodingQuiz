
public class Main {

	public static void main(String[] args) {

		
		//Execute Test Case.
		Vote voteList = new Vote();
		
		for(int i=1;i<12;i++)
			voteList.Vote("Vote_testcase\\"+i+".input.txt");
		
		Brackets brackets= new Brackets();
		
		for(int i=1;i<=10;i++)
			brackets.Brackets("Brackets_testcase\\"+i+".input.txt");
		
		
	}

}
