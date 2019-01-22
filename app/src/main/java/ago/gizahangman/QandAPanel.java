package ago.gizahangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class QandAPanel {

	View  qaInclude;
	LinearLayout qaPanel;
	
	TextView		question;
	TextView		suffix;
	TextView		prefix;
	//TextView		answerSoFar;
	LinearLayout	llAnswerSoFar;
	
	int	txt_color;
	int	question_color;
	int dkgreen_color;

	List<LinearLayout>	ansCols;
	List<TextView>	txCols;
	List<ImageView>  ivCols;
 	final int MAX_COLS = 20;
	// vert LL
	// char TX
	
	int chars_needed;
	
	Activity my_actv;
	
	
    public void  eventPause (DataOutputStream dos) throws IOException {
    	 	
    	dos.writeUTF(question.getText().toString());
    	dos.writeUTF(prefix.getText().toString());
    	dos.writeUTF(suffix.getText().toString());
    	
    	dos.writeUTF(String.valueOf(chars_needed));
    	
    	for (int j = 0; j < ansCols.size(); j++)
    	{
    		int visib = ansCols.get(j).getVisibility();
    		dos.writeUTF(String.valueOf(visib));
    	}

    	for (int j = 0; j < txCols.size(); j++)
    	{
    		String stxt = txCols.get(j).getText().toString();
    		dos.writeUTF(stxt);
    	}

    }
    
    public void eventResume (DataInputStream dis) throws IOException {

    	//answerSoFar = dis.readUTF();
    	//myQandA.onResume(dis);
    	//countOK = Integer.parseInt(dis.readUTF());
    	//countMissed = Integer.parseInt(dis.readUTF());
    	
    	question.setText(dis.readUTF());
    	prefix.setText(dis.readUTF());
    	suffix.setText(dis.readUTF());
    
    	chars_needed = Integer.parseInt(dis.readUTF());
    	
       	for (int j = 0; j < ansCols.size(); j++)
    	{
       		int visib = Integer.parseInt(dis.readUTF());
       		ansCols.get(j).setVisibility(visib);
    	}

    	for (int j = 0; j < txCols.size(); j++)
    	{
    		String stxt = dis.readUTF();
       		txCols.get(j).setText(stxt);
    	}
    	
    }

	
	public QandAPanel(Activity actv)
	{
		my_actv = actv;
		txt_color = Color.argb(255, 128, 74, 43);
		question_color = Color.WHITE;
		dkgreen_color = Color.argb(255,27,137,60);
		
		
		qaInclude = (View)my_actv.findViewById(R.id.qa_include);
		qaPanel = (LinearLayout)my_actv.findViewById(R.id.qa_panel);
		
		question = (TextView)actv.findViewById(R.id.txQuestion);
		suffix = (TextView)actv.findViewById(R.id.txSuffix);
		prefix = (TextView)actv.findViewById(R.id.txPrefix);
		//answerSoFar = (TextView)actv.findViewById(R.id.txAnswerSoFar);
		llAnswerSoFar = (LinearLayout)actv.findViewById(R.id.llAnswerSoFar);
		
		find_ans(actv);
		find_tx(actv);
		find_iv(actv);
		
	
		question.setTextColor(question_color);
		suffix.setTextColor(txt_color);
		prefix.setTextColor(txt_color);
		//answerSoFar.setTextColor(txt_color);
		tx_setColor(txt_color);

	}
	
	private void find_ans(Activity actv)
	{
		ansCols = new ArrayList<LinearLayout>();
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol0));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol1));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol2));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol3));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol4));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol5));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol6));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol7));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol8));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol9));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol10));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol11));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol12));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol13));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol14));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol15));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol16));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol17));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol18));
		ansCols.add((LinearLayout)actv.findViewById(R.id.ansCol19));

	}
	
	private void find_tx(Activity actv)
	{
		txCols = new ArrayList<TextView>();
		txCols.add((TextView)actv.findViewById(R.id.txCol0));
		txCols.add((TextView)actv.findViewById(R.id.txCol1));		
		txCols.add((TextView)actv.findViewById(R.id.txCol2));
		txCols.add((TextView)actv.findViewById(R.id.txCol3));
		txCols.add((TextView)actv.findViewById(R.id.txCol4));
		txCols.add((TextView)actv.findViewById(R.id.txCol5));
		txCols.add((TextView)actv.findViewById(R.id.txCol6));		
		txCols.add((TextView)actv.findViewById(R.id.txCol7));
		txCols.add((TextView)actv.findViewById(R.id.txCol8));
		txCols.add((TextView)actv.findViewById(R.id.txCol9));
		txCols.add((TextView)actv.findViewById(R.id.txCol10));
		txCols.add((TextView)actv.findViewById(R.id.txCol11));		
		txCols.add((TextView)actv.findViewById(R.id.txCol12));
		txCols.add((TextView)actv.findViewById(R.id.txCol13));
		txCols.add((TextView)actv.findViewById(R.id.txCol14));
		txCols.add((TextView)actv.findViewById(R.id.txCol15));
		txCols.add((TextView)actv.findViewById(R.id.txCol16));		
		txCols.add((TextView)actv.findViewById(R.id.txCol17));
		txCols.add((TextView)actv.findViewById(R.id.txCol18));
		txCols.add((TextView)actv.findViewById(R.id.txCol19));
	}
	
	private void find_iv(Activity actv)
	{
		ivCols = new ArrayList<ImageView>();
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol0));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol1));		
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol2));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol3));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol4));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol5));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol6));		
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol7));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol8));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol9));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol10));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol11));		
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol12));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol13));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol14));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol15));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol16));		
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol17));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol18));
		ivCols.add((ImageView)actv.findViewById(R.id.ivCol19));				
	}
	
	private void tx_setColor(int color)
	{
		for (TextView tx : txCols)
		{
			tx.setTextColor(color);
		}
	}
	
	public void Resize(int dispWidth, int dispHeight)
	{
		int border_width = (int)(dispWidth * 0.02);

		int qa_width = (int)(dispWidth * 0.58);
		int qa_height = (int)(dispHeight * 0.20) - border_width;


		//float rel_x =  gal_width / (float)130.0;
		//float rel_y =  gal_height / (float)232.0;
		
		// question font size
		//float qaTextSize = qa_width * 11 / 240;
		
		float qaTextSize = 14; //is_tablet ? 20 : 14;
		if (android.os.Build.PRODUCT.startsWith("GALAXY"))  qaTextSize = 16;
		if (android.os.Build.PRODUCT.startsWith("NOOK"))  qaTextSize = 20;
		if (android.os.Build.MODEL.startsWith("BNRV"))  qaTextSize = 20;
		if (android.os.Build.MODEL.startsWith("BNTV"))  qaTextSize = 20;


		question.setTextSize(qaTextSize);
		
		
		// kdzig is 16 x 2
		int kidz_width = dispWidth * 16 / 480;
		int kidz_height = dispWidth * 2 / 480;
		// ------- prefix suffix ---------
		
		prefix.setTextSize(qaTextSize);
		suffix.setTextSize(qaTextSize);
		
		
		for (TextView tx : txCols) {
			
			tx.setTextSize(qaTextSize);
			tx.setWidth(kidz_width);
		}
				
		ImageView iv0 = (ImageView)ivCols.get(0);
	    Bitmap bmp = BitmapFactory.decodeResource(my_actv.getResources(), R.drawable.kdzig); 

	    int width = kidz_width; 
	    int height = kidz_height; 
	    
	    Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, width, height, true); 

		for (ImageView iv : ivCols) {
			iv.setImageBitmap (resizedbitmap); 
		}
		
		// -------------- qa include----------------
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(qa_width, qa_height);
		params.rightMargin = border_width;
		params.topMargin = border_width;
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
		
		
		qaInclude.setLayoutParams(params);

		/*
		// ---------- prefix
		int pfx_width = (int)(qa_width * 0.2);
		
		LinearLayout.LayoutParams pfxParams = (LinearLayout.LayoutParams) prefix.getLayoutParams();
		pfxParams.width = pfx_width;
		prefix.setLayoutParams(pfxParams);
		
		// ---------- suffix
		int sfx_width = (int)(qa_width * 0.2);
		
		LinearLayout.LayoutParams sfxParams = (LinearLayout.LayoutParams) suffix.getLayoutParams();
		sfxParams.width = sfx_width;
		suffix.setLayoutParams(sfxParams);	
		
		
		// ---------- answer so far
		int asf_width = qa_width - pfx_width - sfx_width - 10 - 10;
		
		LinearLayout.LayoutParams asfParams = (LinearLayout.LayoutParams) llAnswerSoFar.getLayoutParams();
		asfParams.width = asf_width;
		llAnswerSoFar.setLayoutParams(asfParams);
		*/
	}
	
	
	public void ForNewWord(OneQandA oneQA, String answer_so_far)
	{
		question.setText(oneQA.getQuestion());

		suffix.setText(oneQA.getSuffix());
		suffix.setTextColor(txt_color);
		
		prefix.setText(oneQA.getPrefix());
		prefix.setTextColor(txt_color);
	
		//answerSoFar.setText(answer_so_far);
		//answerSoFar.setTextColor(txt_color);

		this.chars_needed = oneQA.getExpectedAnswer().length();
		// hide (as gone) chars not needed.
		for (int j = 0; j < this.chars_needed; j++)
		{
			String exp_char = oneQA.getExpectedAnswer().substring(j,j+1);
			if (exp_char.equalsIgnoreCase(" "))
			{
				if (j < ansCols.size())
				ansCols.get(j).setVisibility(View.INVISIBLE); // keep the space
			}
			else
			{
				if (j < ansCols.size())
				ansCols.get(j).setVisibility(View.VISIBLE);
			}
		}
		
		for (int j = chars_needed; j < MAX_COLS; j++)
		{
			ansCols.get(j).setVisibility(View.GONE);
		}
		
		
		
		set_answerSoFar(answer_so_far);
		
		tx_setColor(txt_color);
		
	}
	
	private void set_answerSoFar (String answer)
	{
		// answer may be shorter or longer than chars_needed.

		// set as much of the answer (up to chars_needed)
		int use_these_many = answer.length();
		if (use_these_many >= this.chars_needed) use_these_many = this.chars_needed;
		
		for (int j = 0; j < use_these_many; j++)
		{
			String s = answer.substring(j, j+1);
			txCols.get(j).setText(s);
		}
		
		// set the rest up to chars_needed to blank.
		for (int k = use_these_many; k < this.chars_needed; k++)
		{
			txCols.get(k).setText(" ");
		}
	}
	
	public void ShowAnswerSoFar(String answer_so_far)
	{
		//answerSoFar.setText(answer_so_far);
		set_answerSoFar (answer_so_far);
	}

	public void ShowFinalAnswer(String answer_so_far, String expectedAnswerExpanded)
	{
		// TODO mark with red character which do not match the expectedAnswer
		
		if (answer_so_far.equalsIgnoreCase(expectedAnswerExpanded))
		{
			//answerSoFar.setText(expectedAnswerExpanded);
			//answerSoFar.setTextColor(dkgreen_color);
			set_answerSoFar (expectedAnswerExpanded);
			tx_setColor(dkgreen_color);
		}
		else
		{
			//answerSoFar.setText(expectedAnswerExpanded);
			//answerSoFar.setTextColor(Color.RED);
			set_answerSoFar (expectedAnswerExpanded);
			tx_setColor(Color.RED);
		}
	}

}
