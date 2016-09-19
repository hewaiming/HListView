package com.ytu.mush.hlistview;

import java.util.List;

import com.ytu.mush.hlistview.MyHScrollView.OnScrollChangedListener;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HolderAdapter extends BaseAdapter {
	private static final String TAG = "HolderAdapter";

	/**
	 * List�е�����
	 */
	private List<Data> currentData;
	/**
	 * ListViewͷ��
	 */
	private RelativeLayout mHead;
	/**
	 * layout ID 
	 */
	private int id_row_layout;
	private LayoutInflater mInflater;

	int[] colors = { Color.rgb(102, 102, 51), Color.rgb(153, 153, 51) };
	// int[] colors = { Color.BLACK, Color.BLACK };

	public HolderAdapter(Context context, int id_row_layout,
			List<Data> currentData, RelativeLayout mHead) {
		Log.v(TAG + ".HolderAdapter", " ��ʼ��");
		
		this.id_row_layout = id_row_layout;
		this.mInflater = LayoutInflater.from(context);
		this.currentData = currentData;
		this.mHead = mHead;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this.currentData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ��List���������
	 * 
	 * @param items
	 */
	public void addItem(List<Data> items) {
		for (Data item : items) {
			currentData.add(item);
		}
	}

	/**
	 * ��յ�List�е�����
	 */
	public void cleanAll() {
		this.currentData.clear();
	}

	public View getView(int position, View convertView, ViewGroup parentView) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(id_row_layout, null);
			holder = new ViewHolder();

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);

			holder.scrollView = scrollView1;
			holder.txt1 = (TextView) convertView.findViewById(R.id.textView1);
			holder.txt2 = (TextView) convertView.findViewById(R.id.textView2);
			holder.txt3 = (TextView) convertView.findViewById(R.id.textView3);
			holder.txt4 = (TextView) convertView.findViewById(R.id.textView4);
			holder.txt5 = (TextView) convertView.findViewById(R.id.textView5);

			MyHScrollView headSrcrollView = (MyHScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView
					.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
							scrollView1));

			convertView.setTag(holder);
			// ���б�ɫ
			convertView.setBackgroundColor(colors[position % 2]);
			// mHolderList.add(holder);
		} else {
			// ���б�ɫ
			convertView.setBackgroundColor(colors[position % 2]);
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt1.setText(currentData.get(position).getStr1() + 1 + "��");
		holder.txt2.setText(currentData.get(position).getStr1() + 2 + "��");
		holder.txt3.setText(currentData.get(position).getStr1() + 3 + "��");
		holder.txt4.setText(currentData.get(position).getStr1() + 4 + "��");
		holder.txt5.setText(currentData.get(position).getStr1() + 5 + "��");

		return convertView;
	}

	class OnScrollChangedListenerImp implements OnScrollChangedListener {
		MyHScrollView mScrollViewArg;

		public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
			mScrollViewArg = scrollViewar;
		}

		public void onScrollChanged(int l, int t, int oldl, int oldt) {
			mScrollViewArg.smoothScrollTo(l, t);
		}
	};

	class ViewHolder {
		TextView txt1;
		TextView txt2;
		TextView txt3;
		TextView txt4;
		TextView txt5;
		HorizontalScrollView scrollView;
	}

}
