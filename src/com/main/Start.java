package com.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * start��ڳ���
 * 
 * @author Administrator
 *
 */
public class Start {

	// ���ڽ��տ���̨��������Ϣ
	// private static Scanner scanner;

	private static Scanner scanner;

	public static void main(String[] args) {
		// ����������ַ���
		String str = "";
		scanner = new Scanner(System.in);
		System.out.println("****************************************");
		System.out.println("��ӭ�������س�������q�˳�����");
		System.out.println("�����Ķ���readme.txt�²����˳���");
		System.out.println("������  :moluchase");
		System.out.println("****************************************");
		// ������εĿγ̺�
		List<Integer> classNos = null;
		while (!str.equals("q")) {
			// ��տγ̺�
			try {
				classNos.clear();
			} catch (Exception e) {
				//
			}

			System.out.println("������������ָ����ʽ��:(����   123,324,34!D:\\\\imooc/Android/)");
			// ��ȡ�����һ��
			str = scanner.nextLine();
			if (str.equals("q"))
				return;
			str = str.replace("��", ",");
			String[] strings = str.split("!");
			if (strings.length > 1) {
				String path = strings[1].trim();
				if (path == null || path.length() < 4 || path.charAt(1) != ':'
						|| path.charAt(path.length() - 1) != '/') {
					System.out.println("�����ַ����");
					continue;
				}
				String[] courses = strings[0].split(",");
				classNos = new ArrayList<Integer>();
				for (String string : courses) {

					try {
						int i = Integer.parseInt(string);
						classNos.add(i);
					} catch (Exception e) {
						System.out.println("����γ�" + string + "���󣬽�ȡ���ÿγ�����");
					}

				}
				// ������Ŀγ̺ţ���ַ����Main����
				Main.MainDemo(classNos, path);
			} else {
				System.out.println("��������");
			}
		}
		while (!str.equals("q")) {
			str = scanner.nextLine();
			System.out.println(str);
		}
		scanner.close();
	}

}
