package ago.gizahangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.database.sqlite.SQLiteDatabase;



public class WordDecider {


	private String answerSoFar;
	
	
	List<OneQandA> listQAAlternate = Arrays.asList(
			new OneQandA("When was Khufu's pyramid built?", "between c.", "2560-2540", "BC"),
			new OneQandA("Where was Khufu's pyramid located?", "", "Giza, Egypt", ""),
			new OneQandA("Who built Khufu's pyramid?", "", "20,000-30,000", "locals"),			
			new OneQandA("Why was Khufu's pyramid built?", "Pharaoh's", "tomb", ""),
			new OneQandA("What was Khufu's pyramid made out of?", "", "limestone", ""),
			new OneQandA("How tall is Khufu's pyramid?", "", "138.2", "meters"),
			new OneQandA("What is the side length?", "", "230.4", "meters"),
			new OneQandA("How long has Khufu's pyramid been around for?", "", "45", "centuries"),
			new OneQandA("For how long has it been the tallest structure?", "over", "3800", "years"),
			new OneQandA("How were pyramid's rocks carried?", "by", "boat", ""),
			new OneQandA("What happened in the AD 1300 earthquake?", "loosened", "stones", ""),
			new OneQandA("How many meters above ground was the entrance?", "", "17", "meters"),
			new OneQandA("Where was Pharaoh Khufu buried? ", "in the", "highest", "chamber"),
			new OneQandA("What is the king's rectangular sarcophagus made of?", "", "granite", ""),
			new OneQandA("What surrounds Khufu's pyramid?", "", "pyramids", ""),
			new OneQandA("How many boat pits are there around Khufu's pyramid?", "", "4", "pits"),
			new OneQandA("Which wonder of the world is Khufu's pyramid part of?", "the", "ancient", "wonders")

			//new OneQandA("What is your name?", "Mr.", "Armand", "Agopian"),
			//new OneQandA("What is your last name?", "Mr.", "Agopian", "")
			);
	
	
	OneQandA myQandA;
	DBQandA	 myDBQA;
	
	int countOK;
	int countMissed;
	
	Random randomGenerator;

	
    public void  eventPause (DataOutputStream dos) throws IOException {
    	 	
    	dos.writeUTF(answerSoFar);
    	myQandA.eventPause(dos);
    	dos.writeUTF(String.valueOf(countOK));
    	dos.writeUTF(String.valueOf(countMissed));
    	
    }
    
    public void eventResume (DataInputStream dis) throws IOException {

    	answerSoFar = dis.readUTF();
    	myQandA.eventResume(dis);
    	countOK = Integer.parseInt(dis.readUTF());
    	countMissed = Integer.parseInt(dis.readUTF());
    
    }

	
	
	public WordDecider()
	{
		randomGenerator = new Random();
		
		SQLiteDatabase db = DBAdapter.mDB();
		myDBQA = new DBQandA(db);
	}
	
	public OneQandA QA()  { return myQandA; }

	public String getAnswerSoFar()
	{
		return answerSoFar;
	}

	public void ReadyNewWord()
	{
		countOK = 0;
		countMissed = 0;

		try
		{		
		/*
		int rand_index = randomGenerator.nextInt(listQA.size());
		myQandA = listQA.get(rand_index);
		 */
			ArrayList<Long> allIDs = myDBQA.getUnaskedIDs();
			int IDcount = allIDs.size();
		
			if (IDcount == 0)
			{
				myDBQA.clearAsked();
				allIDs = myDBQA.getUnaskedIDs();
				IDcount = allIDs.size();
			}
		
			int rand_index = randomGenerator.nextInt(IDcount);
		
			long qID = allIDs.get(rand_index);
			myQandA = myDBQA.get(qID);
			myDBQA.setAsked(qID);
		
		}
		catch (Exception ex)
		{
			int AlternateCount = listQAAlternate.size();
			int alt_index = randomGenerator.nextInt(AlternateCount);

			myQandA = listQAAlternate.get(alt_index);

		}

		answerSoFar = "";
		for (int i = 0; i < myQandA.getExpectedAnswer().length(); i++)
		{
			//	answerSoFar += "_ ";
			answerSoFar += " ";
		}

	}
	
	
	// return false, the key does not exist in the expected answer.
	// return true,  the key exists in the expected answer.
	public boolean onNewKey(String oneKey)
	{
		String oneKeyLC = oneKey.toLowerCase();
		
		// is this new key in the answer?
		// if not, return false, this is a wrong answer.
		int pos_key = myQandA.getExpectedAnswer().toLowerCase().indexOf(oneKeyLC);
		if (pos_key < 0) 
		{
			this.countMissed++;
			return false;
		}
		
		
		
		// if yes, update answerSoFar, return true.
		char chKey = oneKeyLC.charAt(0);
		
		char[] buff = answerSoFar.toCharArray();
		
		for (int i = 0; i < myQandA.getExpectedAnswer().length(); i++)
		{
			if (myQandA.getExpectedAnswer().toLowerCase().charAt(i) == chKey)
			{
				//buff[i*2] = chKey;		
				buff[i] = chKey;
			}
		}
	
		// upcase the first letter.
		answerSoFar = String.valueOf(buff);
		if (answerSoFar.length() > 0)
		{
			answerSoFar = answerSoFar.substring(0,1).toUpperCase() + answerSoFar.substring(1);
		}
		
		
		this.countOK++;
		return true;
	}
	
	
	public boolean HasAnsweredCompletely()
	{
		String expandedExpectedAnswer = this.QA().getExpectedAnswer();
		return (answerSoFar.equalsIgnoreCase(expandedExpectedAnswer));
	}

	/*
	public String getExpectedAnswerExpanded()
	{
		[*
		char[] buff = answerSoFar.toCharArray();
		
		for (int i = 0; i < myQandA.getExpectedAnswer().length(); i++)
		{
			//buff[i*2] = myQandA.getExpectedAnswer().charAt(i);
			buff[i] = myQandA.getExpectedAnswer().charAt(i);
		}

		String expandedExpectedAnswer = String.valueOf(buff);
		*]
		String expandedExpectedAnswer = myQandA.getExpectedAnswer();
		

		if (expandedExpectedAnswer.length() > 0)
		{
			expandedExpectedAnswer = expandedExpectedAnswer.substring(0,1).toUpperCase() + expandedExpectedAnswer.substring(1);
		}
		
		return expandedExpectedAnswer;
	}
*/
}
