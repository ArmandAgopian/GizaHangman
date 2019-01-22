package ago.gizahangman;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageSequencer extends ImageView {

	
	int duration = 2000;
	int interframe = 40;
	int drawableArray = 0;
	
	private ArrayList<Drawable> drawables;
	private ImageSequencer me;
	private int visibility;
	
	int seqNr = 0;
	int totalDuration = 0;
	Drawable startDrawable = null;
	//int interimDuration;

	
	private Handler hndlr = null;
	private int  msgWhat;
	
	public ImageSequencer(Context context) {
		super(context);		
		
		CommonInit(null);
	}
	
	public ImageSequencer(Context context, AttributeSet attrs) {
		super (context, attrs);
		CommonInit(attrs);
	}
	
	public ImageSequencer(Context context, AttributeSet attrs, int defStyle) {
		super (context, attrs, defStyle);

		CommonInit(attrs);
	}

	private void CommonInit(AttributeSet attrs)
	{
		me = this;
		
		this.visibility = this.getVisibility();
		this.startDrawable = null;
		
		String src_name = "";
		String msg_array_id = "";
		
		
		if (attrs != null)
		{
			TypedArray a =getContext().obtainStyledAttributes( 
	         	attrs, 
	         	R.styleable.ImageSequencer); 
			
	 		src_name = a.getString(R.styleable.ImageSequencer_android_src);
			duration = a.getInt(R.styleable.ImageSequencer_duration, 2000);
			interframe = a.getInt(R.styleable.ImageSequencer_interframe, 40);
			msg_array_id = a.getString(R.styleable.ImageSequencer_drawableArray);
			
		
		
		}
		
		drawables = new ArrayList<Drawable>();
		if (msg_array_id.length() > 1)
		{
			Resources res = this.getResources();
			
			int mg_arr_id = Integer.valueOf(msg_array_id.substring(1)); // TODO: the string starts with @ID
			int id2 = R.array.mg_arr;
			
			TypedArray mg_arr = res.obtainTypedArray(mg_arr_id); //R.array.mg_arr);
			for (int i = 0; i < mg_arr.length(); i++)
			{
				Drawable drawable = mg_arr.getDrawable(i);
				drawables.add(drawable);
			}
			
		}
	}
	
	public void onSequenceEndInvoke (Handler my_hnd, int my_what)
	{
		this.hndlr = my_hnd;
		this.msgWhat = my_what;
	}
	

	
	
	public void CommenceSequence()
	{
		seqNr = 0;
		totalDuration = 0;
		//interimDuration = me.duration / me.drawables.size();

		startDrawable = this.getDrawable(); // may be null.
		this.setVisibility(VISIBLE);
		this.post(imageSequencer);		
	
	}
	
	Runnable imageSequencer = new Runnable() {

		@Override
		public void run() 
		{
			try 
			{
				if (me.seqNr >= me.drawables.size()) 
				{
					me.seqNr = 0;
				}
				
				if (totalDuration >= me.duration)
				{
					me.setVisibility(visibility);
	
					if (startDrawable != null)
					{
						me.setImageDrawable(startDrawable);
					}
	
					
					if (me.hndlr != null)
					{
						Message msg = Message.obtain(me.hndlr, me.msgWhat);
						me.hndlr.sendMessage(msg);
					}
					return;
				}
				
				me.setImageDrawable(drawables.get(seqNr));	
					
				seqNr++;
				me.totalDuration += interframe;
				me.postDelayed(imageSequencer, interframe);
			}
			catch (Exception ex)
			{
				totalDuration = me.duration; // stop it
			}
		}
		
		
	};
	
	
}
