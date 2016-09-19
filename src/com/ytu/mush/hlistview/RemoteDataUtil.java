package com.ytu.mush.hlistview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mush
 *
 */
public class RemoteDataUtil {

	/**
	 * @param currentPage
	 * @param pageSize
	 * @return ģ����������
	 */
	public static List<Data> createUpdateData(int currentPage, int pageSize) {
		List<Data> list = new ArrayList<Data>();
		for (int i = (currentPage - 1) * pageSize; i < currentPage * pageSize; i++) {
			Data data = new Data();
			data.setStr1(i + "��");
			data.setStr2(i + "");
			data.setStr3(i + "");
			data.setStr4(i + "");
			data.setStr5(i + "");
			data.setStr6(i + "");
			data.setStr7(i + "");
			data.setStr8(i + "");
			list.add(data);
		}
		return list;
	}

	// ģ�����������Լ��������л�ȡ���ݻ��ѵ�ʱ��
	public static void setRemoteDataByPage(final int currentPage,
			final int pageSize, final LoadStateInterface onLoadComplete) {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// ��ȡ��ǰҪ���µ�����
				List<Data> updateDataList = createUpdateData(currentPage,
						pageSize);
				// ��Ҫ���µ����ݼ��뵱ǰ���ݼ���
				onLoadComplete.onLoadComplete(updateDataList);

			}
		}.start();

	}
}
