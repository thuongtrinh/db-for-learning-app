package com.example.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

class CustomTextView extends TextView {

	public CustomTextView(Context context) {
		super(context);
	}

	@Override
	   protected void onDraw(Canvas canvas) {
	    Paint paint = new Paint();
	    paint.setColor(Color.GREEN);
	    paint.setStyle(Paint.Style.FILL); 
	    paint.setFakeBoldText(true);
	    paint.setStrikeThruText(true);
	    paint.setStrokeWidth(Color.GREEN);
	    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	    super.onDraw(canvas);
	    float width = getWidth();
	    float heigh = getHeight();
	    canvas.drawLine(width/10, heigh/10, (width-width/10),(heigh-heigh/10), paint);
	}
}