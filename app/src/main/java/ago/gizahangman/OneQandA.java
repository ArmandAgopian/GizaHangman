package ago.gizahangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OneQandA {

	private long	rowid;
	private String question;
	private String expectedAnswer;
	private String auxiliaryPrefix;
	private String auxiliarySuffix;

    public void  eventPause (DataOutputStream dos) throws IOException {
    	
    	dos.writeUTF(String.valueOf(rowid));
    	dos.writeUTF(question);
    	dos.writeUTF(expectedAnswer);
    	dos.writeUTF(auxiliaryPrefix);
    	dos.writeUTF(auxiliarySuffix);

    }
    
    public void eventResume (DataInputStream dis) throws IOException {

    	rowid = Long.parseLong(dis.readUTF());
    	question = dis.readUTF();
    	expectedAnswer = dis.readUTF();
    	auxiliaryPrefix = dis.readUTF();
    	auxiliarySuffix = dis.readUTF();
    }

	
	
	public String getQuestion()
	{
		return question;
	}
	
	
	public String getExpectedAnswer()
	{
		return expectedAnswer;
	}
	
	public String getSuffix()
	{
		return auxiliarySuffix;
	}
	
	public String getPrefix()
	{
		return auxiliaryPrefix;
	}

	public OneQandA(long r)
	{
		rowid = r;
	}
	
	public OneQandA (String q,  String p, String a, String s)
	{
		rowid = 0;
		question = q;
		auxiliaryPrefix = p;
		expectedAnswer = a;
		auxiliarySuffix = s;
	}

	public OneQandA (long r, String q,  String p, String a, String s)
	{
		rowid = r;
		question = q;
		auxiliaryPrefix = p;
		expectedAnswer = a;
		auxiliarySuffix = s;
	}

}
