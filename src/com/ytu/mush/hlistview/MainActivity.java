package com.ytu.mush.hlistview;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnScrollListener {
	private static final String TAG = "InfoActivity";
	ListView mListView1;
	RelativeLayout mHead;
	LinearLayout main;
	HolderAdapter holderAdapter;
	private int last_item_position;// ���item��λ��
	private boolean isLoading = false;// �Ƿ���ع�,���Ƽ��ش���
	private int currentPage = 1;// ��ǰҳ,Ĭ��Ϊ1
	private int pageSize = 20;// ÿҳ��ʾʮ����Ϣ
	private View loadingView;// ������ͼ�Ĳ���
	private TextView tv1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mHead = (RelativeLayout) findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setBackgroundColor(Color.BLACK);
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
	    tv1=(TextView) findViewById(R.id.textView1);
	    tv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "click textview1",1).show();
				
			}
		});
		mHead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"Header"+v.getId(), 1).show();
				
			}
		});

		// ������ͼ����
		loadingView = LayoutInflater.from(this).inflate(R.layout.list_page_load, null);

		mListView1 = (ListView) findViewById(R.id.listView1);
		mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mListView1.setCacheColorHint(0);
		// ��ӵײ�������ͼ
		mListView1.addFooterView(loadingView);
		// ����������
		mListView1.setOnScrollListener(this);
		
		// ������ǰ������ʾ��ͼ������
		List<Data> currentData = RemoteDataUtil.createUpdateData(currentPage, pageSize);
		currentPage = currentPage + 1;

		/*
		 * List<Data> datas = new ArrayList<Data>();
		 * 
		 * for (int i = 0; i < 10; i++) { Data data = new Data(); data.setStr1(i
		 * + "��"); data.setStr2(i + ""); data.setStr3(i + ""); data.setStr4(i +
		 * ""); data.setStr5(i + ""); data.setStr6(i + ""); data.setStr7(i +
		 * ""); data.setStr8(i + ""); datas.add(data); }
		 */
		holderAdapter = new HolderAdapter(this, R.layout.item, currentData, mHead);
		mListView1.setAdapter(holderAdapter);
		mListView1.setDrawSelectorOnTop(true);		
		// OnClick����
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Log.i("MainActivity ListView", "onItemClick Event");
				Toast.makeText(MainActivity.this, "���˵�" + arg2 + "��", Toast.LENGTH_SHORT).show();
			}
		});

	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		public boolean onTouch(View arg0, MotionEvent arg1) {
			// ������ͷ �� listView�ؼ���touchʱ�������touch���¼��ַ��� ScrollView
			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			HorizontalScrollView headSrcrollView2 = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView.onTouchEvent(arg1);
			headSrcrollView2.onTouchEvent(arg1);
			return false;
		}
	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		last_item_position = firstVisibleItem + visibleItemCount - 1;

		if (last_item_position == totalItemCount - 2) {
			// ������Ƶ���������ĳһ��λ��ʱ,��ʼ����.
			// ��ǰ���ڵ�9��λ�ÿ�ʼ����,��ΪtotalItemCount-1
			// ����ڵ�10��λ�ÿ�ʼ����

			/**
			 * Loading ��ǵ�ǰ��ͼ�Ƿ��ڼ�����,������ڼ���(isLoading=true)�Ͳ�ִ�и��²���
			 * ������ɺ�isLoading=false,�� loadingHandler �иı�״̬
			 */
			if (!isLoading) {
				// ����һ���̼߳�������
				isLoading = true;
				RemoteDataUtil.setRemoteDataByPage(currentPage, pageSize, new LoadStateInterface() {
					public void onLoadComplete(List<Data> remotDate) {
						holderAdapter.addItem(remotDate);
						handler.sendEmptyMessage(0);
					}
				});
			}
			;
		}
		;

		// ��ListViewû��FooterViewʱ,���FooterView(---loadingView---)
		if (mListView1.getFooterViewsCount() == 0) {
			handler.sendEmptyMessage(1);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				// ����
				holderAdapter.notifyDataSetChanged();
				// ɾ��FooterView
				mListView1.removeFooterView(loadingView);
				// ������һҳ����ʱ��ͼδ����.
				isLoading = false;
				// ��ǰҳ�Լ�
				currentPage = currentPage + 1;
				break;
			}
			case 1: {
				mListView1.addFooterView(loadingView);
				break;
			}
			default: {
				Log.w(TAG, "δ֪��Handler Message:" + msg.obj.toString());
			}
			}

		};
	};

	public void onScrollStateChanged(AbsListView view, int scrollState) {
	

	}

	/**
	 * ����ListView��OnItemClick�¼�
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Log.i("MainActivity ListView", "onItemClick Event");
		Toast.makeText(MainActivity.this, "���˵�" + arg2 + "��", Toast.LENGTH_SHORT).show();
	}
}
