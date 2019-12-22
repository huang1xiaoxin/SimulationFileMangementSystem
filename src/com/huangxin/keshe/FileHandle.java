package com.huangxin.keshe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileHandle {
	private final static int FILE_CODE=1;
	private final static int DISTORY_CODE=2;
	private FileModel rootFileModel;
	private FileModel currentPathFileModel;
	private static Map<String,FileModel> totailFileAndDistory;
	public FileHandle() {
		//创建一个总的集合，来存放所有的文件
		totailFileAndDistory=new HashMap<String,FileModel>();
		//初始一个根节点，作为文件的根目录。
		rootFileModel=new FileModel("root:",2);
		rootFileModel.setFatherFileModel(null);
		totailFileAndDistory.put("root:", rootFileModel);
		currentPathFileModel=rootFileModel;
		
	}
	//创建文件
	public void createFile(String fileName) {
		//当前的目录为空的时候，就说明是在当前的目录创建文件
		FileModel valus;
		if(fileName.indexOf("/")==-1) {
			//判断目录下是否含有该文件
			valus=currentPathFileModel.getFilesMap().get(fileName);
			//判断是否存在
			if(valus!=null) {
				//判断是否是文件还是目录
				//说明存在同名的文件夹
				if(valus.getAttr()==DISTORY_CODE) {
					FileModel newFile=new FileModel(fileName,FILE_CODE);
					//设置父目录
					newFile.setFatherFileModel(currentPathFileModel);
					//将改文件添加到父目录下
					currentPathFileModel.getFilesMap().put(newFile.getName(), newFile);
					totailFileAndDistory.put(forEachGetFather(currentPathFileModel)+"/"+fileName, newFile);
					System.out.println("文件创建成功！！");
				}else {
					//存在同名的文件，创建失败
					System.out.println("文件创建失败！！该目录下存在命名相同的文件。");
					return;
				}
			}else {
				//说明没有存在同名的文件和目录
				FileModel newFile=new FileModel(fileName,FILE_CODE);
				newFile.setFatherFileModel(currentPathFileModel);
				//将改文件添加到父目录下
				currentPathFileModel.getFilesMap().put(newFile.getName(), newFile);
				totailFileAndDistory.put(forEachGetFather(currentPathFileModel)+"/"+fileName, newFile);
				System.out.println("文件创建成功！！");
			}			
		}//如果不为空，则说明在指定的目录下创建文件夹
		else {
			String[] arr=getPathArray(fileName);
			FileModel newFileFather=getFatherFileModel(fileName);
			if(newFileFather==null) {
				System.out.println("文件创建失败！！绝对路径中的路径非法。");
			}else {
				FileModel newFile=new FileModel(arr[arr.length-1],FILE_CODE);
				newFile.setFatherFileModel(newFileFather);
				//将改文件添加到父目录下
				newFileFather.getFilesMap().put(newFile.getName(), newFile);
				totailFileAndDistory.put(fileName,newFile);
				System.out.println("文件创建成功！！");
			}
		}
	}
	//创建目录
	public void createDistory(String distoryName) {
		//判断传入的文件名称时当前的路径下，还是绝对路径
		int i=distoryName.indexOf("/");
		//在当前的目录下创建
		if(i==-1) {
			//判断当前的目录是否含有相同的目录
			FileModel valus=currentPathFileModel.getFilesMap().get(distoryName);
			if(valus==null) {
				FileModel fileModel=new FileModel(distoryName,DISTORY_CODE);
				fileModel.setFatherFileModel(currentPathFileModel);
				currentPathFileModel.getFilesMap().put(fileModel.getName(), fileModel);
				totailFileAndDistory.put(forEachGetFather(currentPathFileModel)+"/"+distoryName,fileModel);
				//System.out.println(forEachGetFather(currentPathFileModel)+"/"+distoryName);
				System.out.println("目录创建成功！！");
			}else {
				System.out.println("该目录已经存在！！");
			}
		}//指定的路径下创建目录
		else {
			String arr[]=getPathArray(distoryName);
			FileModel newDistoryFather=getFatherFileModel(distoryName);
			if(newDistoryFather==null) {
				System.out.println("目录创建失败！！指定的路径非法。");
			}else {
				FileModel valus=newDistoryFather.getFilesMap().get(arr[arr.length-1]);
				if(valus==null) {
					FileModel newDistory=new FileModel(arr[arr.length-1],DISTORY_CODE);
					newDistory.setFatherFileModel(newDistoryFather);
					newDistoryFather.getFilesMap().put(newDistory.getName(), newDistory);
					totailFileAndDistory.put(distoryName,newDistory);
					System.out.println("目录创建成功！！");
				}else {
					System.out.println("该目录已经存在！！");
				}
			}
	
		}
	}
	//cd切换当前的目录
	public void changeCurrentPath(String path) {
		//判断是否是当前的路径切换，还是切换到指定的路径中
		if((path.indexOf("/")==-1)) {
			if(path.equals("root:")) 
				path="root:";
			else
			  path=forEachGetFather(currentPathFileModel)+"/"+path;
		}
		//处理一种返回到跟目录
		
		FileModel valus=totailFileAndDistory.get(path);
		if(valus==null) {
			System.out.println("切换的目录不存在！！");
		}else {
			if(valus.getAttr()!=FILE_CODE) {
				currentPathFileModel=valus;
				System.out.println("切换目录成功！");
			}else {
				System.out.println("切换目录格式错误，不可以切换到文件中");
			}
		}
	}
	//dir显示目录的内容
	public void dirFiles(String distory) {
		if(distory.indexOf("/")==-1&&!distory.isEmpty()&&!distory.equals("root:")) {
			distory=forEachGetFather(currentPathFileModel)+"/"+distory;
		}
		//遍历目录的字目录和文件
		FileModel valus;
		if(distory.isEmpty()) {
			valus=currentPathFileModel;
		}else {
			valus=totailFileAndDistory.get(distory);
		}
		if(valus==null) {
			System.out.println("查看的目录不存在！！");
		}else {
			for(Map.Entry<String, FileModel> entries:valus.getFilesMap().entrySet()) {
				System.out.println("  "+entries.getValue().getName());
			}
		}
	}
	//删除文件
	public void deleteFile(String filePath) {
		if(filePath.indexOf("/")==-1) {
			filePath=forEachGetFather(currentPathFileModel)+"/"+filePath;
		}
		//获取指定路径或者当前目录的FileModel对象
		FileModel valus=totailFileAndDistory.get(filePath);
		//判断要删除的文件是否存在
		if(valus!=null) {
			if(valus.getAttr()==FILE_CODE) {
				//在所以的文件夹中删除
				totailFileAndDistory.remove(filePath);
				//在父类的目录删除
				valus.getFatherFileModel().getFilesMap().remove(valus.getName());
				System.out.println(valus.getName()+"文件已删除！");
			}else {
				System.out.println("删除失败，del不可以删除目录！");
			}
			
		}else {
			System.out.println("删除失败，文件不存在！");
		}	
	}
	//删除目录
	public void deleteDistory(String distoryPath) {
		if(distoryPath.indexOf("/")==-1) {
			distoryPath= forEachGetFather(currentPathFileModel)+"/"+distoryPath;
		}
		//获取目录的FileModel对象
		FileModel valus=totailFileAndDistory.get(distoryPath);
		//判断要删除的文件夹是否是正在打开的文件夹，如果是正提示用户不可以删除
		if(distoryPath.equals(returnCurrentPath())) {
			System.out.println("目录正在打开，不可以删除！！");
			return;
		}
		if(valus!=null) {
			//判断是否要删除整个目录
			if(valus.getName().equals("root:")) {
				System.out.println("根目录不可以删除！！");
			}else {
				//判断删除的目录是否为空
				if(!valus.getFilesMap().isEmpty()) {
					//提醒用户是否要删除文件
					System.out.println("删除的文件不为空，请确认是否删除文件。\n删除请输入Y,否则输入N");
					Scanner scan=new Scanner(System.in);
					if(scan.nextLine().toString().equals("Y")) {
						if(deleteAll(valus)) {
							//在它的父级文件夹将其移除
							valus.getFatherFileModel().getFilesMap().remove(valus.getName());
							System.out.println("删除成功！！");
						}else{;
						      System.out.println("删除失败！！");
						}
					}else {
						System.out.println("删除失败！！");
					}
					
				}else {
					valus.getFatherFileModel().getFilesMap().remove(valus.getName());
					totailFileAndDistory.remove(forEachGetFather(valus.getFatherFileModel())+"/"+valus.getName());
					System.out.println("删除成功！！");
				}
				
			}
		}else {
			System.out.println("删除失败，目录不存在！");
		}
	}
	//切换到上一级
	public void returnLastDistory() {
		if(currentPathFileModel.getName().equals("root:")){
			System.out.println("已切换到根目录！！无法再返回上一级");
		}else {
			currentPathFileModel=currentPathFileModel.getFatherFileModel();
		}
	}
	
	//切割路径，获取上级的路径
	private String[] getPathArray(String path) {
		return path.split("/");
	}
	//如果路径是有效的，就返回上一级的FileModel对象
	private FileModel getFatherFileModel(String s) {
		int i=s.lastIndexOf("/");
		return totailFileAndDistory.get(String.valueOf(s.substring(0,i)));
	}
	//利用递归函数遍历一个一个文件或者一个目录获得一个完整的路径
	private String forEachGetFather(FileModel fileModel) {
		if(fileModel.getName().equals("root:")) {
			return "root:";
		}
		return forEachGetFather(fileModel.getFatherFileModel())+"/"+fileModel.getName();
		
	}
	//利用递归的方式删除文件及子文件
	private boolean deleteAll(FileModel file) {
		if(file.getAttr()==DISTORY_CODE) {
			List<FileModel> files=new ArrayList<>();
			//因为Map在遍历的时候如果在的大小发生改变的时候，就会发生错误，所以要通过一个中间的List集合来
			//临时存储着所有的FileModel,然后进行删除
			Map<String,FileModel> mp=file.getFilesMap();
			for(Map.Entry<String, FileModel> entries:mp.entrySet()) {
				files.add(entries.getValue());
			}
			for(int i=0;i<files.
				size();i++) {
				boolean success=deleteAll(files.get(i));
				if(!success) {
					return false;
				}
			}
		}
		//不是文件的就直接删除，且不为空的就直接
		return delete(file);
	}
	private boolean delete(FileModel fileModel) {
	     //在总文件夹中移除目录或者文件夹
		 return fileModel.getFatherFileModel().getFilesMap().remove(fileModel.getName(),fileModel)
			&&totailFileAndDistory.remove(forEachGetFather(fileModel),fileModel);
		
	}
	//返回当前的路径
	public String returnCurrentPath() {
		return forEachGetFather(currentPathFileModel);
	}
}
