package ago.gizahangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GallowsPanel {
	
	Activity actv;
	Handler  gizaHandler;
	
	View  galInclude;
	RelativeLayout galPanel;
	ImageView		gallo;
	ImageView		noose;
	RelativeLayout	martug;
	ImageSequencer  grag;
	
	List<Integer>		martug_ids;
	List<ImageView> 	martug_maser;
	
	int MG_KLUKH = 0;
	int MG_GABKLUKH = 1;
	
    public void  eventPause (DataOutputStream dos) throws IOException {
    	
		for (int i = 0; i < martug_maser.size(); i++)
		{
			int visib = martug_maser.get(i).getVisibility();
	    	dos.writeUTF(String.valueOf(visib));
		}

    }
    
    public void eventResume (DataInputStream dis) throws IOException {

		for (int i = 0; i < martug_maser.size(); i++)
		{
	    	int visib = Integer.parseInt(dis.readUTF());
			martug_maser.get(i).setVisibility(visib);
		}
    }

	
	public GallowsPanel (Activity my_actv, Handler my_hndl)
	{
		actv = my_actv;
		gizaHandler = my_hndl;
	
		galInclude = (View)my_actv.findViewById(R.id.gallows_include);
		galPanel = (RelativeLayout)my_actv.findViewById(R.id.gallows_panel);
		
		gallo = (ImageView)my_actv.findViewById(R.id.imageBarzGakhaghan);
		noose = (ImageView)my_actv.findViewById(R.id.img_baran);
		martug = (RelativeLayout)my_actv.findViewById(R.id.gallows_martug);
		
		grag = (ImageSequencer)my_actv.findViewById(R.id.sequencer);
		grag.onSequenceEndInvoke(gizaHandler, 1);
		
        //seqCer.CommenceSequence();

		martug_maser = new ArrayList<ImageView>();
		martug_ids = new ArrayList<Integer>();

		int[] mg_ids = {
			      R.id.img_mg_klukh, R.id.img_mg_gabklukh, R.id.img_mg_aap, R.id.img_mg_ago,
			      R.id.img_mg_atv, R.id.img_mg_avd, R.id.img_mg_grnag, R.id.img_mg_tap,
			      R.id.img_mg_tgo, R.id.img_mg_ttv, R.id.img_mg_tvd
			    };
			    
		

		for (int i = 0; i < mg_ids.length; i++)
		{
			int mg_id = mg_ids[i];
			martug_ids.add(mg_id);
			martug_maser.add((ImageView)actv.findViewById(mg_id));
		}


		HideGallows();
		HideMartug();
	}

	private void HideGallows()
	{
		noose.setVisibility(View.INVISIBLE);
		gallo.setVisibility(View.INVISIBLE);
	}

	public void Resize(int dispWidth, int dispHeight)
	{
		
		int border_width = (int)(dispWidth * 0.06);

		int gal_width = (int)(dispWidth * 0.30);
		int gal_height = dispHeight - border_width - border_width;
		float rel_x =  gal_width / (float)130.0;
		float rel_y =  gal_height / (float)232.0;

		// -------------- gallows ----------------
		RelativeLayout.LayoutParams params = (LayoutParams) new RelativeLayout.LayoutParams(gal_width, gal_height);
		params.leftMargin = border_width;
		params.topMargin = border_width;
		//params.bottomMargin = border_width;
		
		
		galInclude.setLayoutParams(params);

		// ----------- noose --------
		int bar_left = (int)(rel_x * 67);
		int bar_top = (int)(rel_y * 21);
		int bar_width = (int)(rel_x * 35);
		int bar_height = (int)(rel_y * 50);

		
		RelativeLayout.LayoutParams bar_params = (LayoutParams)noose.getLayoutParams();
		bar_params.leftMargin = bar_left;
		bar_params.topMargin = bar_top;
		bar_params.width = bar_width;
		bar_params.height = bar_height;
		noose.setLayoutParams(bar_params);
		
		// ----------- grag --------
		int grg_left = (int)(bar_left * 0.5); 
		int grg_top = (int)(bar_top * 1.1);
		int grg_width = (int)(bar_width * 0.8);
		int grg_height = (int)(bar_height * 0.8);

		
		RelativeLayout.LayoutParams grg_params = (LayoutParams)grag.getLayoutParams();
		grg_params.leftMargin = grg_left;
		grg_params.topMargin = grg_top;
		grg_params.width = grg_width;
		grg_params.height = grg_height;
		grag.setLayoutParams(grg_params);
		
		// ---------- martug ---------------
		int mar_left = (int)(rel_x * 35);
		int mar_top = (int)(rel_y * 30);
		int mar_width = (int)(rel_x * 130);
		int mar_height = (int)(rel_y * 175);

		
		RelativeLayout.LayoutParams mar_params = (LayoutParams)martug.getLayoutParams();
		mar_params.leftMargin = mar_left;
		mar_params.topMargin = mar_top;
		mar_params.width = mar_width;
		mar_params.height = mar_height;
		martug.setLayoutParams(mar_params);
		
	}
	
	
	public void ReadyNewWord()
	{
		//gallo.setVisibility(View.VISIBLE);
		//noose.setVisibility(View.VISIBLE);
		
		// fade in the gallows and noose
		int animTime = 500;
		Animation animation = new AlphaAnimation(0.0f, 1.0f); 
        animation.setFillAfter(true); 
        animation.setDuration(animTime);
        noose.startAnimation(animation);
        gallo.startAnimation(animation);

        // make sure these stay visible.
        noose.postDelayed(new Runnable()
        {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				noose.setVisibility(View.VISIBLE);
				gallo.setVisibility(View.VISIBLE);
			}
        	
        	
        }, animTime+10);
	}
	
	
	public void HideMartug()
	{
			      
		for (ImageView martug_mas : martug_maser)
		{
			martug_mas.setVisibility(View.INVISIBLE);
		}
		

	}

	public void ShowMartugHappy()
	{
			    
		for (int i = 0; i < martug_maser.size(); i++)
		{
			if (i == MG_GABKLUKH) continue;
			martug_maser.get(i).setVisibility(View.VISIBLE);
		}
	}

	
	public void ShowMartugMas(int img_mg)
	{
		int mg_nr = 0;
		
		for (int i = 0; i < martug_ids.size(); i++)
		{
			if (martug_ids.get(i) == img_mg) { mg_nr = i; break; }
		}
		
		//ImageView martug_mas = (ImageView)actv.findViewById(img_mg);
		ImageView martug_mas = martug_maser.get(mg_nr);
		martug_mas.setVisibility(View.VISIBLE);
	}
	
	public void ShowFire (int delayMillis, boolean isCorrect)
	{
		grag.postDelayed(delayedFire, delayMillis);

		if (isCorrect)
			grag.postDelayed(delayedHappy, delayMillis);
	}

	Runnable delayedHappy = new Runnable() {
		@Override
		public void run() {
			ShowMartugHappy();
		}		
	};
	
	Runnable delayedFire = new Runnable() {

		@Override
		public void run() {
			try 			
			{
				// TODO Auto-generated method stub
				grag.CommenceSequence();
				
				// animate noose + gallo to fade visibility.
				//noose.setVisibility(View.INVISIBLE);			
				//gallo.setVisibility(View.INVISIBLE);	
				
				// this is the duration of the flame
				int animTime = (int)(grag.duration * 0.75);
				
				// this one fades the gallows and the noose
				Animation animation = new AlphaAnimation(1.0f, 0.0f); 
		        animation.setFillAfter(true); 
		        animation.setDuration(animTime);
		        noose.startAnimation(animation);
		        gallo.startAnimation(animation);
	
		        // this one makes the noose and gallows invisible.
		        noose.postDelayed(new Runnable()
		        {
	
					@Override
					public void run() {
						// TODO Auto-generated method stub
						noose.setVisibility(View.INVISIBLE);
						gallo.setVisibility(View.INVISIBLE);
						
						// change klukh
						//ImageView martug_mas1 = (ImageView)actv.findViewById(R.drawable.mg_klukh);
						//martug_maser.get(MG_KLUKH).setVisibility(View.VISIBLE);
	
						//ImageView martug_mas2 = (ImageView)actv.findViewById(R.drawable.mg_gabklukh);
						//martug_maser.get(MG_GABKLUKH).setVisibility(View.INVISIBLE);
					}
		        	
		        	
		        }, animTime+10);

			}
			catch(Exception ex)
			{
				// stop the animation
				
			}
	        
	        // at the end of animation, start a new word
			//Message msg = Message.obtain(gizaHandler, 1);
			//gizaHandler.sendMessageDelayed(msg, grag.duration + 200);

		}
		
	};
	
}
