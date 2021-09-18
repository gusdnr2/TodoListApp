package com.todo.service;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== �׸� �߰� ===\n"
				+ "���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��˴ϴ�!");
			return;
		}
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("���������� �߰��Ͽ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== �׸� ���� ===\n"
				+ "���� �� �׸��� ���� > ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("���������� ���� �Ǿ����ϴ�.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=== �׸� ���� ===\n"
				+ "���� �� �׸��� ���� > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�׸��� �����ϴ�!");
			return;
		}

		System.out.print("���ο� ������ �Է��� �ּ��� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		sc.nextLine();
		System.out.print("���ο� ������ �Է��� �ּ��� > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�׸��� ������Ʈ�Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l,String filename) {
		try {
			Writer w  = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("������ �Է� �Ϸ�.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l,String filename) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			int count = 0;
			String eachline;
			while ((eachline = r.readLine()) != null) {
				count++;
				StringTokenizer st = new StringTokenizer(eachline,"##");
				String name = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				TodoItem t = new TodoItem(name,desc,date);
				l.addItem(t);
			}
			r.close();
			System.out.println(count+" ���� �׸��� �о����ϴ�.");
		} catch (FileNotFoundException e) {
			System.out.println("TodoList.txt ������ �����ϴ�.");
		} catch (IOException e) {
			System.out.println("������ ���� ���߽��ϴ�.");
		}
		
	}
}
