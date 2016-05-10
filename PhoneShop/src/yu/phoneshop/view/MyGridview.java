package yu.phoneshop.view;

import yu.phoneshop.R;
import yu.phoneshop.util.CommonUtil;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class MyGridview extends GridView{

	public MyGridview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyGridview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyGridview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	//解决Scroll view与List view的冲突
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		View localView1 = getChildAt(0);
		if(localView1!=null){
			int column = getWidth() / localView1.getWidth();
	        int childCount = getChildCount();
	        Paint localPaint;
	        localPaint = new Paint();
	        localPaint.setStyle(Paint.Style.STROKE);
	        localPaint.setStrokeWidth(CommonUtil.dip2px(getContext(), 0.1f));
	        localPaint.setColor(getContext().getResources().getColor(R.color.gray_line));
	        for(int i = 0;i < childCount;i++){
	            View cellView = getChildAt(i);
	            if((i + 1) % column == 0){
	                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
	            }else if((i + 1) > (childCount - (childCount % column))){
	                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
	            }else{
	                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
	                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
	            }
	        }
	        if(childCount % column != 0){
	            for(int j = 0 ;j < (column-childCount % column) ; j++){
	                View lastView = getChildAt(childCount - 1);
	                canvas.drawLine(lastView.getRight() + lastView.getWidth() * j, lastView.getTop(), lastView.getRight() + lastView.getWidth()* j, lastView.getBottom(), localPaint);
	            }
	        }
		}
	}
}
