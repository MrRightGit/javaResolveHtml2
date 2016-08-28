package com.down;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.main.Main;

/**
 * ���س������а����˻�ȡ�μ���ַ�ĺ���
 * 
 * @author Administrator
 *
 */
public class DownloadCourse {

	/**
	 * ͨ���μ���Set��ȡ�μ������ص�ַ�Ϳμ���
	 * 
	 * @param set
	 *            setΪ�μ��ĵ�ַ
	 * @param savePath
	 *            ·��
	 */
	public static void getCourseURLAndName(Set<String> set, String savePath) {
		for (String string : set) {
			// �ڴ���Set��stringʱ���ԣ�Ϊ�罫��ַ��filename�ֿ���
			String[] path = string.split("!");
			String url = path[0];
			String fileName = path[1];

			downloadByURLAndName2(url, fileName, savePath);

		}
	}

	/**
	 * ���ֽ�������,��Ȼ����
	 * 
	 * @param url
	 *            ��ַ
	 * @param fileName
	 *            �ļ���
	 * @param savePath
	 *            ·��
	 */
	public static void downloadByURLAndName2(String url, String fileName, String savePath) {

		long startTime;
		long bytes;
		startTime = System.currentTimeMillis();
		bytes = 0;
		try {
			// �������ӣ����������
			URL httpUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) httpUrl.openConnection();
			con.setReadTimeout(60 * 1000);
			InputStream in = con.getInputStream();
			// ���ñ���·������ȡ�����
			File file;
			new File(savePath).mkdirs();
			file = new File(savePath + fileName);
			FileOutputStream fos = new FileOutputStream(file);
			// ��ȡ��д��
			byte[] b = new byte[512 * 1024];
			int len;
			while ((len = in.read(b)) != -1) {
				bytes += len;
				fos.write(b, 0, len);
			}
			fos.close();
			in.close();
			System.out.println(fileName + "�������");
			// ��Ҫ�Ǽ�¼�ֽڣ�ʱ��Ĳ���
			long endTime = System.currentTimeMillis();
			Map<String, Long> map = new HashMap<String, Long>();
			map.put("time", endTime - startTime);
			map.put("bytes", bytes);
			Main.bps.add(map);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
