package ago.gizahangman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class KeyboardPanel {

	Activity actv;
	View  kbdInclude;
	LinearLayout kbdPanel;
	
	ArrayList<Button> keys;
	int darkGreen;
	int retries;
	
	public void  eventPause (DataOutputStream dos) throws IOException {
	    	
		
	   	for (int j = 0; j < keys.size(); j++)
    	{
	   		ColorStateList mList = keys.get(j).getTextColors(); 
	   		int color = mList.getDefaultColor(); 

    		dos.writeUTF(String.valueOf(color));
    	}
	    	
	}
	    
	public void eventResume (DataInputStream dis) throws IOException {

		   	for (int j = 0; j < keys.size(); j++)
	    	{
	    		int color = Integer.parseInt(dis.readUTF());
	    		keys.get(j).setTextColor(color);
	    	}
	}
	
	public KeyboardPanel (Activity my_actv)
	{
		actv = my_actv;
		darkGreen = Color.argb(255, 33, 173, 75);
		keys = new ArrayList<Button>();
		retries = 0;
		
		
		kbdInclude = (View)my_actv.findViewById(R.id.keyboard_include);
		kbdPanel = (LinearLayout)my_actv.findViewById(R.id.keyboard_panel);
		
		if (kbdInclude instanceof ViewGroup)
		{
			ViewGroup vg = (ViewGroup)kbdInclude;
			getChildrenViews (vg);	
		}
	}


	
	private void getChildrenViews(ViewGroup parentView)
	{
					
		for (int i = 0; i < parentView.getChildCount(); i++) { 
						
	        View childView = parentView.getChildAt(i); 
	        
	        if (childView instanceof Button)
	        {
	        	keys.add((Button)childView);
	        }
	        
	        if (childView instanceof ViewGroup)
	        {
	        	ViewGroup newParentView = (ViewGroup)childView;
	        	getChildrenViews(newParentView);
	        }
		}
		// end of getChildrenViews
	}

	public void Resize(int dispWidth, int dispHeight)
	{
		int border_width = (int)(dispWidth * 0.02);

		int kbd_width = (int)(dispWidth * 0.58);
		int kbd_height = (int)(dispHeight * 0.43) - border_width;

		//String tstr = "dev " + android.os.Build.MODEL 
		//		 + " -- " + android.os.Build.PRODUCT				
		//		 + " -- " + android.os.Build.MANUFACTURER				
		//		  +	"KBD wdth:" + String.valueOf(kbd_width) + "  hgth:" + String.valueOf(kbd_height) 
		//		  + "  DSP wdth:" + String.valueOf(dispWidth) + "  hgth:" + String.valueOf(dispHeight);
		//Toast.makeText(actv.getBaseContext(), tstr, Toast.LENGTH_SHORT).show();
		
		
		// ------- key font size -------------
		// question font size
		//boolean is_tablet = (dispWidth > 1000);
		float keyTextSize = 14; //is_tablet ? 20 : 14;
		if (android.os.Build.PRODUCT.startsWith("GALAXY"))  keyTextSize = 18;
		if (android.os.Build.PRODUCT.startsWith("NOOK"))  keyTextSize = 22;
		if (android.os.Build.MODEL.startsWith("BNRV"))  keyTextSize = 22;
		if (android.os.Build.MODEL.startsWith("BNTV"))  keyTextSize = 22;

		for (Button btn : keys)
		{
			btn.setTextSize(keyTextSize);
		}


		// -------------- keyboard include ----------------
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(kbd_width, kbd_height);

		params.rightMargin = border_width;
		params.bottomMargin = border_width;
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
		
		kbdInclude.setLayoutParams(params);
	}

	
	public void ResetKeys()
	{
		for (Button btn : keys)
		{
			btn.setTextColor(Color.BLACK);
		}
		
	}
	
	public void AnsweredCorrectly(String oneKey)
	{
		for (Button oneBtn : keys)
		{
			String str_tag = oneBtn.getTag().toString();
			
			if (str_tag.equalsIgnoreCase(oneKey))
				oneBtn.setTextColor(darkGreen);
		}
		
	}
	
	public void AnsweredIncorrectly(String oneKey)
	{
		for (Button oneBtn : keys)
		{
			String str_tag = oneBtn.getTag().toString();
			
			if (str_tag.equalsIgnoreCase(oneKey))
				oneBtn.setTextColor(Color.RED);
		}		
	}
	
}


