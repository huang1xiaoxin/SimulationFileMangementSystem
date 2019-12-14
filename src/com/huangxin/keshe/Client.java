package com.huangxin.keshe;

import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		HandelMessage(new FileHandle());
	}
	
	@SuppressWarnings("resource")
	private static void HandelMessage(FileHandle fileHandle) {
		Scanner scan=new Scanner(System.in);
		String str="";
		System.out.println("**************��ӭʹ���ļ�������**************");
		System.out.print(fileHandle.returnCurrentPath()+" ");
		while((str=scan.nextLine())!=null) {
			String[] strArr=str.split(" ");
			if(strArr.length>2){
				System.out.println("�����ָ������,����ͨ��help�鿴ָ���ʹ�á�");
				System.out.print(fileHandle.returnCurrentPath()+" ");
				continue;
			}
			if((strArr[0].equals("exit")||strArr[0].equals("help")||strArr[0].equals("cd.."))&&strArr.length>1) {
				System.out.println("�����ָ������,����ͨ��help�鿴ָ���ʹ�á�");
				System.out.print(fileHandle.returnCurrentPath()+" ");
				continue;
			}
			boolean isExit=false;
			if(strArr.length>1) {
				switch(strArr[0].toString().toLowerCase()) {
				//�л�Ŀ¼��ָ��
				case "cd":
					fileHandle.changeCurrentPath(strArr[1]);
					break;
					//��ʾ�ļ���Ŀ¼
				case "dir":
					fileHandle.dirFiles(strArr[1]);
					break;
					//����Ŀ¼
				case "md":
					fileHandle.createDistory(strArr[1]);
					break;
					//ɾ��Ŀ¼
				case "rd":
					fileHandle.deleteDistory(strArr[1]);
					break;
					//ɾ���ļ�
				case "del":
					fileHandle.deleteFile(strArr[1]);
					break;
					//�����ļ�
				case "edit":
					fileHandle.createFile(strArr[1]);
					break;
					//�˳�
				default :
					System.out.println("ָ�����,��������ȷ��ָ��:");
				}
				
			}else {
				switch(strArr[0].toString().toLowerCase()) {
					//��ʾ�ļ���Ŀ¼
				case "dir":
					fileHandle.dirFiles("");
					break;
					//�˳�
				case "exit":
					isExit=true;
					break;
					//�л�����һ��Ŀ¼
				case "cd..":
					fileHandle.returnLastDistory();
					break;
					//����
				case "help":
					System.out.println("*****************�ļ�ϵͳָ��˵��*****************\n"
							+ "\t�ı�Ŀ¼��cd   <Ŀ¼·��-(���·�����߾���·��)>\n"
							+ "\t��ʾĿ¼��dir  <Ŀ¼·��-(���·�����߾���·��)>\n"
							+ "\t����Ŀ¼��md   <Ŀ¼·��-(���·�����߾���·��)>\n"
							+ "\tɾ��Ŀ¼��rd   <Ŀ¼·��-(���·�����߾���·��)>\n"
							+ "\t�����ļ���edit <�ļ�����-(���������·�����߾���·���´���)>\n"
							+ "\tɾ���ļ���del  <�ļ�����-(���������·�����߾���·���´���)>\n"
							+ "\t�˳�ϵͳ��exit\n"
							+ "\t������һ��Ŀ¼��cd..\n"
							+ "\t������      help");
						break;
					default :
						System.out.println("ָ�����,��������ȷ��ָ��:");
					  }
				}
				if(isExit) {
					System.out.println("�˳��ļ�ϵͳ��������");
					break;
				}else {
					System.out.print(fileHandle.returnCurrentPath()+" ");
				}
		}
		
	}
}
