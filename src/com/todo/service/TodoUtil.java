package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== �׸� �߰� ===\n"
				+ "���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��˴ϴ�!");
			return;
		}
		System.out.print("ī�װ� > ");
		category = sc.next();
		
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		
		System.out.println("���� ���� > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("���������� �߰��Ͽ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		try {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== �׸� ���� ===\n"
				+ "���� �� �׸��� ��ȣ > ");
		int title = Integer.parseInt(sc.next());
		
		for (TodoItem item : l.getList()) {
			if (title == l.indexOf(item)+1) {
				l.deleteItem(item);
				System.out.println("���������� ���� �Ǿ����ϴ�.");
				break;
			}
		}
		} catch (NumberFormatException e) {
			System.out.println("���ڸ� �Է��� �ּ���!");
		}
	}


	public static void updateItem(TodoList l) {
		try {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n"
				+ "=== �׸� ���� ===\n"
				+ "���� �� �׸��� ��ȣ > ");
		int title = Integer.parseInt(sc.next());		
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
		System.out.print("���ο� ī�װ��� �Է��� �ּ��� > ");
		String new_cate = sc.next().trim();
		sc.nextLine();
		System.out.print("���ο� ������ �Է��� �ּ��� > ");
		String new_description = sc.nextLine().trim();
		System.out.print("���ο� ���� ���ڸ� �Է��� �ּ��� > ");
		String new_ddat = sc.next().trim();
		
		for (TodoItem item : l.getList()) {
			if (l.isDuplicate(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_cate, new_ddat);
				l.addItem(t);
				System.out.println("�׸��� ������Ʈ�Ǿ����ϴ�.");
			}
		} 
		} catch(NumberFormatException e) {
			System.out.println("���ڸ� �Է��� �ּ���!");
			
		}
			
		}

	
	public static void find(TodoList l,String keyword) {
		int counter = 0;
		for (TodoItem item : l.getList()) {
			String itemname = item.getTitle();
			String descname = item.getDesc();
			if (itemname.contains(keyword)) {
				System.out.println((l.indexOf(item)+1)+". "+item.toString());
				counter++;
			}
			else if (descname.contains(keyword)) {
				System.out.println((l.indexOf(item)+1)+". "+item.toString());
				counter++;
			}
		}
		if (counter == 0) {
			System.out.println("�˻� ��� �׸��� ã�� ���߽��ϴ�.");
			return;
		} else {
			System.out.println("�� "+counter+"���� �׸��� ã�ҽ��ϴ�.");		
		}
		}
	

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���, �� "+l.sizeOf()+"��]");
		for (TodoItem item : l.getList()) {
			System.out.println((l.indexOf(item)+1)+". "+item.toString());
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
				String cate = st.nextToken();
				String name = st.nextToken();
				String desc = st.nextToken();
				String ddat = st.nextToken();
				String date = st.nextToken();
				TodoItem t = new TodoItem(name,desc,date,cate,ddat);
				l.addItem(t);
			}
			r.close();
			System.out.println(count+" ���� �׸��� �о����ϴ�.");
		} catch (FileNotFoundException e) {
			System.out.println("TodoList.txt ������ �����ϴ�.");
		} catch (IOException e) {
			System.out.println("������ ���� ���߽��ϴ�.");
		} catch (NoSuchElementException e) {
			System.out.println("������ ���� ���߽��ϴ�.");
		}
}
}
