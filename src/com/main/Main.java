package com.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.down.DownLoadVideo;
import com.down.DownloadCourse;

/**
 * �����򣬲�������learn�µĽ���
 * 
 * @author Administrator
 *
 */
// Android�������Ƶ
// int[]classNos={606,650,513,536,353,162,656,468,648,500,268,341,136,684,308,358,431,653,302,462,223};
// int[] classNos={304,142};

// Java�����
// ���ģʽ
// int[] classNos={112,261,214,146,415,257,165,145};
// ����
// int[]classNos={352,354,271,213,272,482};
// ����
// int[]classNos={587,630,631,632};
// ����
// int[]classNos={286,287,288,289};
// int[]classNos={289};
// ����
// int[] classNos={586,679,531,629,584,567,518,180,585,466,283,265,368,401};
// ���
// int[] classNos={558,524,465,498,452,478,154,260};
// ����
// int[] classNos={443,356,109};

// Python
// int[] classNos={177,475,458,563,416,457,550,317,581,712,532,595};

// ���ݿ�
// MySQL
// int[] classNos={589,533,449,427,398,194,117,435,572};
// ����
// int[] classNos={};
// Oracle
// int[] classNos={414,437,423,370,360,337};
// mongodb
// int[]
// classNos={614,575,552,534,521,325,298,297,295,255,595,594,582,578,562,528,490,501};

// Web
// ·��138,167,418,530,429,430,
// int[] classNos={385,250,56,101,21,147,100,204,50,137};
// ����710,680,668,588,643,565,542,488,(453),445,
// int[] classNos={403,367,357,256,276,243,186,121,
// 362,192,93,95,119,118,15,706,598,659,644,639,580,566,545,514,488,471,453,454,425,428,412,
// 386,366,367,359,191,277,12,22,176,174,144,62,34,44,120,99,270,675,496,374,222,172,
// 637,590,564,556,434,367,348,221,75,197};

// php
// ·��
// int[]
// classNos={175,54,116,349,164,26,329,115,219,148,69,245,404,440,467,491,520,696,111};
// ����177,475,458,563,416,457,550,317,581,712,532
// int[] classNos={
// 616,617,618,619,620,621,623,607,604,596,591,547,527,509,417,483,463,469,
// 438,433,419,380,350,330,278,236,244,26,205,219,170,184,150,155,115,113,94,68};

public class Main {

	// �μ����ϣ������ظ�
	public static Set<String> set = new HashSet<>();
	// ���ڴ洢���ص��ֽ����������������ٶ�
	public static List<Map<String, Long>> bps = new ArrayList<Map<String, Long>>();

	public static void MainDemo(List<Integer> classNos, String path) {
		// Jsoup��Document����

		Document doc = null;

		String title;
		String savePath;
		String[] videoNos;
		String videoName;

		int classNo;

		for (int i : classNos) {
			classNo = i;
			try {
				set.clear();
				bps.clear();
			} catch (Exception e) {
				System.out.println(e);
			}

			try {

				// ��ȡJsoup����
				Connection con = Jsoup.connect("http://www.imooc.com/learn/" + classNo).timeout(60 * 1000);
				// ��ȡDocument����
				doc = con.get();

			} catch (Exception e) {
				System.out.println(e);
			}
			// ��ȡ��ǩΪh2��Ԫ�أ�����ȡHTML����,����������γ̵�����
			title = doc.getElementsByTag("h2").html();
			// �����ļ��зǷ��ַ������д���ο�������ʽ
			title = title.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");
			title = classNo + "_" + title;
			// ��ַ��д������Ŀ¼��\\,��Ŀ¼��/
			savePath = path + title + "/";
			// File file = new File(savePath);
			System.out.println(title);
			// ����÷���û�ҵ����ҵ�����ǲ��ұ�ǩa�к���video�ı�ǩԪ��
			Elements videos = doc.select(".video a");
			// ѡ�����
			int videoDef = 1;

			for (Element element : videos) {
				videoNos = element.attr("href").split("/");
				if (videoNos[0].equals("")) {
					// System.out.println(videoNos[2]);
					DealChildrenHtml.doGetFile(videoNos[2]);
					// ��ȡԪ�ص��ı�
					videoName = element.text();
					videoName = videoName.substring(0, videoName.length() - 4).trim();
					videoName = videoName.replace(" ", "").replace("(", "").replace(")", "").replace(":", "")
							.replace("-", "_");
					// ��仰����Ҫ�ģ��ڴ���·��������γ����а���\ʱ����ᱨFileNotFoundException�������ر��Ϊ360�Ŀγ�ʱ������
					videoName = videoName.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");

					try {
						// ��ȡ��Ƶ���ص�ַ
						DownLoadVideo.getVideoURL(videoNos[2], videoDef, videoName, savePath);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}
			DownloadCourse.getCourseURLAndName(set, savePath);
			System.out.println("������ϣ�");
			getBps(title, savePath);
		}

	}

	/**
	 * �������ٶȵ���
	 * 
	 * @param title
	 */
	public static void getBps(String title, String savePath) {
		long times = 0;
		long bytes = 0;

		for (Map<String, Long> map : bps) {
			times += map.get("time");
			bytes += map.get("bytes");
		}
		String str = title + "������ϣ�" + "������������Ϊ��"
				+ (float) ((int) (((float) bytes * 1.0) / ((float) (times * 1024 * 1.0) / 1000.0 * 1024) * 100) * 1.0)
						/ (float) (100 * 1.0)
				+ "Mb/s";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
		String strData = sdf.format(date);
		System.out.println(str);

		File file = new File(savePath + "bps.txt");
		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(file, true);
			// \r\nʱ����ļ��Ļ��в���
			fileWriter.write(str + "��" + strData + "��" + "����" + bytes + "�ֽڡ�" + "��512*1024��" + "\r\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
