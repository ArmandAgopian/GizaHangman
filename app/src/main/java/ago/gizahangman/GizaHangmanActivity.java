package ago.gizahangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.Application;
//import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.RelativeLayout;
//import android.widget.Toast;





public class GizaHangmanActivity extends Activity {

	
	private static final String PAGE_SIG = "/Giza";

	static final int BKG_ALPHA = 165;
	Sequencer	 seq;
	GallowsPanel galPanel;
	WordDecider	 wdecid;
	QandAPanel	 qaPanel;
	KeyboardPanel	kbdPanel;
	
	ImageView imgTitle;
	ImageView imgBackground;
	//RelativeLayout rlGallowsPanel;

	
	AnimationListener titleAnimListener;
	Animation myFadeOutAnimation;
	
	
	boolean paused = false;
	
	
	//@Override
	//public void onStart()
	//{
	//	super.onStart();		
	//	Toast.makeText(getApplicationContext(), "on Start", Toast.LENGTH_SHORT).show();
	//}
	

	@Override
	public void onPause()
	{
		paused = true; 
		super.onPause();		
		//Toast.makeText(getApplicationContext(), "on Pause", Toast.LENGTH_SHORT).show();
		
		//outState.putString("GIZASI", "this was set onSaveInstanceState");
		
		FileOutputStream fOut;
		try {
			fOut = openFileOutput("GizaFile.txt", MODE_WORLD_READABLE);
			DataOutputStream dos = new DataOutputStream(fOut);
			
			// set the marker.
			dos.writeUTF("GIZA HANGMAN DATA");
			dos.writeUTF("second line");

			seq.eventPause(dos);
	    	wdecid.eventPause(dos);
	    	galPanel.eventPause(dos);
	    	qaPanel.eventPause(dos);
	    	kbdPanel.eventPause(dos);
	    	
			
			dos.flush();
			dos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

	}

	//@Override
	//public void onStop()
	//{
	//	super.onStop();		
	//	Toast.makeText(getApplicationContext(), "on Stop", Toast.LENGTH_SHORT).show();
	//}
	

	@Override
	public void onResume()
	{
		super.onResume();		
		paused = false; 
		//Toast.makeText(getApplicationContext(), "GIZA on Resume", Toast.LENGTH_LONG).show();
		
		try {
			FileInputStream fIn = openFileInput("GizaFile.txt");			
			DataInputStream dis = new DataInputStream(fIn);
			
			String marker = dis.readUTF();
			String marktwo = dis.readUTF();


			// if the first line is not the marker, skip this file.
			if (marker != null && marker.compareTo("GIZA HANGMAN DATA") == 0)
			{
				seq.eventResume(dis);
		    	wdecid.eventResume(dis);
		    	galPanel.eventResume(dis);
		    	qaPanel.eventResume(dis);
		    	kbdPanel.eventResume(dis);
			}
			
			dis.close();
			fIn.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}

	
	//@Override
	//public void onRestart()
	//{
	//	super.onRestart();
	//	Toast.makeText(getApplicationContext(), "GIZA on Restart", Toast.LENGTH_SHORT).show();

		
		//if (!this.waiting_for_game_results)
		//this.StartNewData("");
	//}
	

	//@Override
	//public void onSaveInstanceState(Bundle outState)
	//{
	//	super.onSaveInstanceState(outState);
		//Toast.makeText(getApplicationContext(), "GIZA on SaveInstanceState", Toast.LENGTH_LONG).show();
		
		
		
		//outState.putString(MTGamePad.OPER_KEY, this.many_oper.ToJson());
		//outState.putString(MTGamePad.OPERATOR_KEY, this.vw_operator.getText().toString());
		//outState.putString(MTGamePad.OPERAND_KEY, this.vw_operand.getText().toString());
		//outState.putString(MTGamePad.RESULT_KEY, this.vw_result.getText().toString());
		//outState.putString(MTGamePad.OPER_SIGN_KEY, this.vw_oper_sign.getText().toString());
	//}

	@Override  protected void onDestroy() 
	{    
		// close the database
		DBAdapter.EndActivity();
		
		// Stop the tracker when it is no longer needed.   
		GoogTrackManager.EndActivity();
		
		//tracker.stopSession();  
		super.onDestroy();    
	}


	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        boolean isNook = false;
        if (android.os.Build.PRODUCT.startsWith("NOOK"))  isNook = true;
		if (android.os.Build.MODEL.startsWith("BNRV"))  isNook = true;
		if (android.os.Build.MODEL.startsWith("BNTV"))  isNook = true;
		
		if (isNook) {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		}
		
        // if the private file exists, remove it.
		//FileOutputStream fOut;
		//try {
		//	fOut = openFileOutput("GizaFile.txt", MODE_WORLD_READABLE);
		//	DataOutputStream dos = new DataOutputStream(fOut);
			
		//	// set the marker.
		//	dos.writeUTF("just started");
		//	dos.writeUTF("just started");
		//}
		//catch (FileNotFoundException e) {
		//// TODO Auto-generated catch block
		////e.printStackTrace();
		//} catch (IOException e) {
		//	// do nothing.
		//}
        
		//Toast.makeText(getApplicationContext(), "GIZA on Create", Toast.LENGTH_LONG).show();

        if (savedInstanceState != null)
        {
        	//Toast.makeText(getBaseContext(), savedInstanceState.getString("GIZASI"), Toast.LENGTH_LONG).show();
        }
        
        Application app = getApplication();
    	GoogTrackManager.CommenceActivity(app);
    	GoogTrackManager.trackPageView(PAGE_SIG); 

        
    	DBAdapter.CommenceActivity(getApplication());


        
        
        wdecid = new WordDecider();
        galPanel = new GallowsPanel(this, gizaHandler);
        qaPanel = new QandAPanel(this);
        kbdPanel = new KeyboardPanel(this);
        seq = new Sequencer(galPanel, wdecid, qaPanel, kbdPanel);

        // resize panels
        Display display = getWindowManager().getDefaultDisplay(); 
        int dispWidth = display.getWidth();
        int dispHeight = display.getHeight(); 

        galPanel.Resize(dispWidth, dispHeight);
        kbdPanel.Resize(dispWidth, dispHeight);
        qaPanel.Resize(dispWidth, dispHeight);
        
        // startup
        imgTitle= (ImageView)findViewById(R.id.imageTitle); 
        //imgBackground = (ImageView)findViewById(R.id.imageBackground);
        //rlGallowsPanel = (RelativeLayout)findViewById(R.id.gallows_panel);

        // aganch for animation
        titleAnimListener = new AnimationListener()
        {

			@Override
			public void onAnimationEnd(Animation animation) {
				imgTitle.setVisibility(View.GONE);
				//imgBackground.setAlpha(BKG_ALPHA);
				//rlGallowsPanel.setVisibility(View.VISIBLE);
				
				
				//Toast.makeText(getBaseContext(), "Animation has ended", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
        };

        // fade out the title
    	myFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);     
        myFadeOutAnimation.setAnimationListener(titleAnimListener);        
        imgTitle.startAnimation(myFadeOutAnimation); //Set animation to your ImageView 


    }
    
    private int hasClicked = 0;
    public void kbdClick(View view)
    {
    	try
    	{
    		if (hasClicked > 0) { hasClicked--; return; }
    	
    		hasClicked = 10;
    		String str_tag = view.getTag().toString();
    		//Toast.makeText(getBaseContext(), str_tag, Toast.LENGTH_SHORT).show();

    		//if (str_tag.equalsIgnoreCase("/"))  { seq.ResetAll(); return; }
    	
    		seq.onNewKey(str_tag);
    	
    		hasClicked = 0;
    	}
    	catch (Exception ex)
    	{
    		// keys were clicked too fast.
    	}
    }
    
    Handler gizaHandler = new Handler() {
    	
    	public void handleMessage(Message message) {
    		switch (message.what)
    		{
    		case 1: seq.ResetAll(); break;
    		}
    	}
    };
    
}