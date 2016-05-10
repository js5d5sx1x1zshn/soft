package yu.phoneshop.view;

import yu.phoneshop.util.CommonUtil;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MySubButton extends View{
	private Paint paint;
	public MySubButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public MySubButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public MySubButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	private void init(){
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.parseColor("#2F2F2F"));
		paint.setStrokeWidth(CommonUtil.dip2px(this.getContext(),  0.5f));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(10, getHeight()/2, getWidth()-10, getHeight()/2, paint);
	}
}
