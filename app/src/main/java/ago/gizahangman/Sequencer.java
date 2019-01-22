package ago.gizahangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Sequencer {

	WordDecider	 	wdecid;
	GallowsPanel 	galPanel;
	QandAPanel	 	qaPanel;
	KeyboardPanel	kbdPanel;

	int sequenceNumber;
	int MAX_INCORRECT = 11;
	
    public void  eventPause (DataOutputStream dos) throws IOException {
    	
    	dos.writeUTF(String.valueOf(sequenceNumber));
    	
    }
    
    public void eventResume (DataInputStream dis) throws IOException {

    	sequenceNumber = Integer.parseInt(dis.readUTF());    	
    	
    	if (sequenceNumber >= MAX_INCORRECT)
		{
    		sequenceNumber = 0;
		}
    }
	
	public Sequencer (GallowsPanel gpan, WordDecider wdec, QandAPanel qapan, KeyboardPanel kbdpan)
	{
		galPanel = gpan;
		wdecid = wdec;
		qaPanel = qapan;
		kbdPanel = kbdpan;
	
		ResetAll();
	}
	
	public void ResetAll()
	{
		kbdPanel.ResetKeys();
		wdecid.ReadyNewWord();
		galPanel.ReadyNewWord();
		
		qaPanel.ForNewWord(wdecid.QA(), wdecid.getAnswerSoFar());
		sequenceNumber = 0;
		have_new_sequence();
	}
	
	public void onNewKey(String oneKey)
	{
		if (sequenceNumber >= MAX_INCORRECT)
		{
			return;
		}

		boolean answered_correctly = wdecid.onNewKey(oneKey);
		qaPanel.ShowAnswerSoFar(wdecid.getAnswerSoFar()); 

		if (answered_correctly)
		{
			kbdPanel.AnsweredCorrectly(oneKey);
			
			if (wdecid.HasAnsweredCompletely())
			{
				// if answered fully
				qaPanel.ShowFinalAnswer(wdecid.getAnswerSoFar() , wdecid.QA().getExpectedAnswer());
				galPanel.ShowFire(700, true);
				// wait for reset from user.
			
				String qst = wdecid.QA().getQuestion();
				if (qst.length() > 20) qst = qst.substring(0, 20);
				
				String ans = wdecid.QA().getExpectedAnswer();
				if (ans.length() > 20) ans = ans.substring(0, 20);
				
				GoogTrackManager.trackEvent("Ans-ok", qst, ans, wdecid.countMissed);
			}
			return;
		}
		
		kbdPanel.AnsweredIncorrectly(oneKey);

		sequenceNumber++;
		have_new_sequence();

		// answered incorrectly.
		// are we past the max allowed?
		if (sequenceNumber == MAX_INCORRECT)
		{
			// mgrad, 
			// wait for reset from user.
			// show the missing answers with red.
			qaPanel.ShowFinalAnswer(wdecid.getAnswerSoFar() , wdecid.QA().getExpectedAnswer());			
			galPanel.ShowFire(1000, false);
	
			String qst = wdecid.QA().getQuestion();
			if (qst.length() > 20) qst = qst.substring(0, 20);
			
			String ans = wdecid.QA().getExpectedAnswer();
			if (ans.length() > 20) ans = ans.substring(0, 20);
			
			
			GoogTrackManager.trackEvent("Ans-missed", qst, ans, wdecid.countOK);
		
		
		}

	
	}
	
	
	private void have_new_sequence()
	{
		switch (this.sequenceNumber)
		{
		case 0:
				galPanel.HideMartug();
				break;
		case 1:
			galPanel.ShowMartugMas(R.id.img_mg_klukh);
			break;
			
		case 2:
			galPanel.ShowMartugMas(R.id.img_mg_grnag);
			break;
			
		case 3:
			galPanel.ShowMartugMas(R.id.img_mg_atv);
			break;
			
		case 4:
			galPanel.ShowMartugMas(R.id.img_mg_ttv);
			break;
			
		case 5:
			galPanel.ShowMartugMas(R.id.img_mg_aap);
			break;
			
		case 6:
			galPanel.ShowMartugMas(R.id.img_mg_tap);
			break;
		
		case 7:
			galPanel.ShowMartugMas(R.id.img_mg_avd);
			break;
			
		case 8:
			galPanel.ShowMartugMas(R.id.img_mg_tvd);
			break;
			
		case 9:
			galPanel.ShowMartugMas(R.id.img_mg_ago);
			break;
			
		case 10:
			galPanel.ShowMartugMas(R.id.img_mg_tgo);
			break;
			
		case 11:
			galPanel.ShowMartugMas(R.id.img_mg_gabklukh);
			break;


		}
	}
}
