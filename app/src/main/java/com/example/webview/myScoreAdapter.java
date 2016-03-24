package com.example.webview;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.webview.StudentAdapter.ViewHolder;
import com.koushikdutta.ion.Ion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class myScoreAdapter  extends BaseAdapter{	
		  
	private ArrayList<HashMap<String, Object>> list;
	private Context context;

	public myScoreAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
		//super(context, data);
		// TODO Auto-generated constructor stub
		this.list = data;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_scorelist, null);
			holder.itemNeiRong = (TextView) convertView.findViewById(R.id.itemNeiRong);
			holder.itemFenZhi = (TextView) convertView.findViewById(R.id.itemFenZhi);
			holder.segSeekBar= (SegmentSeekBarView) convertView.findViewById(R.id.SegSeekBar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < list.size())
		{
			HashMap<String, Object> map = list.get(position);
			/*Object uid = map.get("U_ID");
			
			final String imgUrl = url+uid.toString();
			if (imgUrl != null && !imgUrl.equals("")) {
				
				// Use Ion's builder set the google_image on an ImageView from a URL

                // start with the ImageView
                Ion.with(holder.img)
                // use a placeholder google_image if it needs to load from the network
                .placeholder(R.drawable.username)
                .error(R.drawable.username)
                // load the url
                .load(imgUrl);
			}*/
			
			holder.itemNeiRong.setText((String)map.get("itemNeiRong"));
			holder.itemFenZhi.setText((String)map.get("itemFenZhi"));
			SeekBar sb= holder.segSeekBar.getSb();
			sb.setProgress(Integer.parseInt(map.get("itemFenshu").toString()));
			TextView tv= holder.segSeekBar.getTextValue();
			tv.setText((String)map.get("itemFenshu"));
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView itemNeiRong;
		TextView itemFenZhi;
		SegmentSeekBarView segSeekBar;
	}
}
