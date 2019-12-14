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
		System.out.println("**************欢迎使用文件管理器**************");
		System.out.print(fileHandle.returnCurrentPath()+" ");
		while((str=scan.nextLine())!=null) {
			String[] strArr=str.split(" ");
			if(strArr.length>2){
				System.out.println("输入的指令有误,可以通过help查看指令的使用。");
				System.out.print(fileHandle.returnCurrentPath()+" ");
				continue;
			}
			if((strArr[0].equals("exit")||strArr[0].equals("help")||strArr[0].equals("cd.."))&&strArr.length>1) {
				System.out.println("输入的指令有误,可以通过help查看指令的使用。");
				System.out.print(fileHandle.returnCurrentPath()+" ");
				continue;
			}
			boolean isExit=false;
			if(strArr.length>1) {
				switch(strArr[0].toString().toLowerCase()) {
				//切换目录的指令
				case "cd":
					fileHandle.changeCurrentPath(strArr[1]);
					break;
					//显示文件和目录
				case "dir":
					fileHandle.dirFiles(strArr[1]);
					break;
					//创建目录
				case "md":
					fileHandle.createDistory(strArr[1]);
					break;
					//删除目录
				case "rd":
					fileHandle.deleteDistory(strArr[1]);
					break;
					//删除文件
				case "del":
					fileHandle.deleteFile(strArr[1]);
					break;
					//创建文件
				case "edit":
					fileHandle.createFile(strArr[1]);
					break;
					//退出
				default :
					System.out.println("指令错误,请输入正确的指令:");
				}
				
			}else {
				switch(strArr[0].toString().toLowerCase()) {
					//显示文件和目录
				case "dir":
					fileHandle.dirFiles("");
					break;
					//退出
				case "exit":
					isExit=true;
					break;
					//切换到上一级目录
				case "cd..":
					fileHandle.returnLastDistory();
					break;
					//帮助
				case "help":
					System.out.println("*****************文件系统指令说明*****************\n"
							+ "\t改变目录：cd   <目录路径-(相对路径或者绝对路径)>\n"
							+ "\t显示目录：dir  <目录路径-(相对路径或者绝对路径)>\n"
							+ "\t创建目录：md   <目录路径-(相对路径或者绝对路径)>\n"
							+ "\t删除目录：rd   <目录路径-(相对路径或者绝对路径)>\n"
							+ "\t创建文件：edit <文件名称-(可以是相对路径或者绝对路径下创建)>\n"
							+ "\t删除文件：del  <文件名称-(可以是相对路径或者绝对路径下创建)>\n"
							+ "\t退出系统：exit\n"
							+ "\t返回上一级目录：cd..\n"
							+ "\t帮助：      help");
						break;
					default :
						System.out.println("指令错误,请输入正确的指令:");
					  }
				}
				if(isExit) {
					System.out.println("退出文件系统。。。。");
					break;
				}else {
					System.out.print(fileHandle.returnCurrentPath()+" ");
				}
		}
		
	}
}
