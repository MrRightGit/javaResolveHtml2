package com.main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * video�������
 * 
 * @author Administrator
 *
 */
public class DealChildrenHtml {

	/**
	 * ���ؿμ�����ÿ�δ�һ��video��Ƶ����ҳ����ʾ�Ŀμ�
	 * 
	 * @param videoNo
	 *            videoNo�Ƿ�����ַvideo����������ַ���
	 *            ����144������ʵ���Ƶ��ַΪhttp://www.imooc.com/video/144
	 * @param title
	 *            title�ǿγ���
	 */
	public static void doGetFile(String videoNo) {
		Document document = null;
		String filePath;
		String[] s;
		String lastName;
		String fileName;
		try {
			document = Jsoup.connect("http://www.imooc.com/video/" + videoNo).timeout(60 * 1000).get();

		} catch (Exception e) {
			System.out.println(e);
		}
		Elements efilePaths = document.select(".coursedownload a");
		for (Element element : efilePaths) {
			filePath = element.attr("href");
			/*
			 * Java��String.split��������Ĳ�����һ��RegularExpr����һ��������ʽ��
			 * ��������ʽ�У���ű�ʾ�����ַ�����˼��������Ҫ��\��ת�塣 ��\\.��ʾ����\. ��.
			 */
			s = filePath.split("\\.");
			lastName = s[s.length - 1];
			fileName = element.attr("title");

			// ���μ��ĵ�ַ����Ϣ��ӵ�set������
			Main.set.add(filePath + "!" + fileName + "." + lastName);
		}
	}
}
