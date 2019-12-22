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
		//����һ���ܵļ��ϣ���������е��ļ�
		totailFileAndDistory=new HashMap<String,FileModel>();
		//��ʼһ�����ڵ㣬��Ϊ�ļ��ĸ�Ŀ¼��
		rootFileModel=new FileModel("root:",2);
		rootFileModel.setFatherFileModel(null);
		totailFileAndDistory.put("root:", rootFileModel);
		currentPathFileModel=rootFileModel;
		
	}
	//�����ļ�
	public void createFile(String fileName) {
		//��ǰ��Ŀ¼Ϊ�յ�ʱ�򣬾�˵�����ڵ�ǰ��Ŀ¼�����ļ�
		FileModel valus;
		if(fileName.indexOf("/")==-1) {
			//�ж�Ŀ¼���Ƿ��и��ļ�
			valus=currentPathFileModel.getFilesMap().get(fileName);
			//�ж��Ƿ����
			if(valus!=null) {
				//�ж��Ƿ����ļ�����Ŀ¼
				//˵������ͬ�����ļ���
				if(valus.getAttr()==DISTORY_CODE) {
					FileModel newFile=new FileModel(fileName,FILE_CODE);
					//���ø�Ŀ¼
					newFile.setFatherFileModel(currentPathFileModel);
					//�����ļ���ӵ���Ŀ¼��
					currentPathFileModel.getFilesMap().put(newFile.getName(), newFile);
					totailFileAndDistory.put(forEachGetFather(currentPathFileModel)+"/"+fileName, newFile);
					System.out.println("�ļ������ɹ�����");
				}else {
					//����ͬ�����ļ�������ʧ��
					System.out.println("�ļ�����ʧ�ܣ�����Ŀ¼�´���������ͬ���ļ���");
					return;
				}
			}else {
				//˵��û�д���ͬ�����ļ���Ŀ¼
				FileModel newFile=new FileModel(fileName,FILE_CODE);
				newFile.setFatherFileModel(currentPathFileModel);
				//�����ļ���ӵ���Ŀ¼��
				currentPathFileModel.getFilesMap().put(newFile.getName(), newFile);
				totailFileAndDistory.put(forEachGetFather(currentPathFileModel)+"/"+fileName, newFile);
				System.out.println("�ļ������ɹ�����");
			}			
		}//�����Ϊ�գ���˵����ָ����Ŀ¼�´����ļ���
		else {
			String[] arr=getPathArray(fileName);
			FileModel newFileFather=getFatherFileModel(fileName);
			if(newFileFather==null) {
				System.out.println("�ļ�����ʧ�ܣ�������·���е�·���Ƿ���");
			}else {
				FileModel newFile=new FileModel(arr[arr.length-1],FILE_CODE);
				newFile.setFatherFileModel(newFileFather);
				//�����ļ���ӵ���Ŀ¼��
				newFileFather.getFilesMap().put(newFile.getName(), newFile);
				totailFileAndDistory.put(fileName,newFile);
				System.out.println("�ļ������ɹ�����");
			}
		}
	}
	//����Ŀ¼
	public void createDistory(String distoryName) {
		//�жϴ�����ļ�����ʱ��ǰ��·���£����Ǿ���·��
		int i=distoryName.indexOf("/");
		//�ڵ�ǰ��Ŀ¼�´���
		if(i==-1) {
			//�жϵ�ǰ��Ŀ¼�Ƿ�����ͬ��Ŀ¼
			FileModel valus=currentPathFileModel.getFilesMap().get(distoryName);
			if(valus==null) {
				FileModel fileModel=new FileModel(distoryName,DISTORY_CODE);
				fileModel.setFatherFileModel(currentPathFileModel);
				currentPathFileModel.getFilesMap().put(fileModel.getName(), fileModel);
				totailFileAndDistory.put(forEachGetFather(currentPathFileModel)+"/"+distoryName,fileModel);
				//System.out.println(forEachGetFather(currentPathFileModel)+"/"+distoryName);
				System.out.println("Ŀ¼�����ɹ�����");
			}else {
				System.out.println("��Ŀ¼�Ѿ����ڣ���");
			}
		}//ָ����·���´���Ŀ¼
		else {
			String arr[]=getPathArray(distoryName);
			FileModel newDistoryFather=getFatherFileModel(distoryName);
			if(newDistoryFather==null) {
				System.out.println("Ŀ¼����ʧ�ܣ���ָ����·���Ƿ���");
			}else {
				FileModel valus=newDistoryFather.getFilesMap().get(arr[arr.length-1]);
				if(valus==null) {
					FileModel newDistory=new FileModel(arr[arr.length-1],DISTORY_CODE);
					newDistory.setFatherFileModel(newDistoryFather);
					newDistoryFather.getFilesMap().put(newDistory.getName(), newDistory);
					totailFileAndDistory.put(distoryName,newDistory);
					System.out.println("Ŀ¼�����ɹ�����");
				}else {
					System.out.println("��Ŀ¼�Ѿ����ڣ���");
				}
			}
	
		}
	}
	//cd�л���ǰ��Ŀ¼
	public void changeCurrentPath(String path) {
		//�ж��Ƿ��ǵ�ǰ��·���л��������л���ָ����·����
		if((path.indexOf("/")==-1)) {
			if(path.equals("root:")) 
				path="root:";
			else
			  path=forEachGetFather(currentPathFileModel)+"/"+path;
		}
		//����һ�ַ��ص���Ŀ¼
		
		FileModel valus=totailFileAndDistory.get(path);
		if(valus==null) {
			System.out.println("�л���Ŀ¼�����ڣ���");
		}else {
			if(valus.getAttr()!=FILE_CODE) {
				currentPathFileModel=valus;
				System.out.println("�л�Ŀ¼�ɹ���");
			}else {
				System.out.println("�л�Ŀ¼��ʽ���󣬲������л����ļ���");
			}
		}
	}
	//dir��ʾĿ¼������
	public void dirFiles(String distory) {
		if(distory.indexOf("/")==-1&&!distory.isEmpty()&&!distory.equals("root:")) {
			distory=forEachGetFather(currentPathFileModel)+"/"+distory;
		}
		//����Ŀ¼����Ŀ¼���ļ�
		FileModel valus;
		if(distory.isEmpty()) {
			valus=currentPathFileModel;
		}else {
			valus=totailFileAndDistory.get(distory);
		}
		if(valus==null) {
			System.out.println("�鿴��Ŀ¼�����ڣ���");
		}else {
			for(Map.Entry<String, FileModel> entries:valus.getFilesMap().entrySet()) {
				System.out.println("  "+entries.getValue().getName());
			}
		}
	}
	//ɾ���ļ�
	public void deleteFile(String filePath) {
		if(filePath.indexOf("/")==-1) {
			filePath=forEachGetFather(currentPathFileModel)+"/"+filePath;
		}
		//��ȡָ��·�����ߵ�ǰĿ¼��FileModel����
		FileModel valus=totailFileAndDistory.get(filePath);
		//�ж�Ҫɾ�����ļ��Ƿ����
		if(valus!=null) {
			if(valus.getAttr()==FILE_CODE) {
				//�����Ե��ļ�����ɾ��
				totailFileAndDistory.remove(filePath);
				//�ڸ����Ŀ¼ɾ��
				valus.getFatherFileModel().getFilesMap().remove(valus.getName());
				System.out.println(valus.getName()+"�ļ���ɾ����");
			}else {
				System.out.println("ɾ��ʧ�ܣ�del������ɾ��Ŀ¼��");
			}
			
		}else {
			System.out.println("ɾ��ʧ�ܣ��ļ������ڣ�");
		}	
	}
	//ɾ��Ŀ¼
	public void deleteDistory(String distoryPath) {
		if(distoryPath.indexOf("/")==-1) {
			distoryPath= forEachGetFather(currentPathFileModel)+"/"+distoryPath;
		}
		//��ȡĿ¼��FileModel����
		FileModel valus=totailFileAndDistory.get(distoryPath);
		//�ж�Ҫɾ�����ļ����Ƿ������ڴ򿪵��ļ��У����������ʾ�û�������ɾ��
		if(distoryPath.equals(returnCurrentPath())) {
			System.out.println("Ŀ¼���ڴ򿪣�������ɾ������");
			return;
		}
		if(valus!=null) {
			//�ж��Ƿ�Ҫɾ������Ŀ¼
			if(valus.getName().equals("root:")) {
				System.out.println("��Ŀ¼������ɾ������");
			}else {
				//�ж�ɾ����Ŀ¼�Ƿ�Ϊ��
				if(!valus.getFilesMap().isEmpty()) {
					//�����û��Ƿ�Ҫɾ���ļ�
					System.out.println("ɾ�����ļ���Ϊ�գ���ȷ���Ƿ�ɾ���ļ���\nɾ��������Y,��������N");
					Scanner scan=new Scanner(System.in);
					if(scan.nextLine().toString().equals("Y")) {
						if(deleteAll(valus)) {
							//�����ĸ����ļ��н����Ƴ�
							valus.getFatherFileModel().getFilesMap().remove(valus.getName());
							System.out.println("ɾ���ɹ�����");
						}else{;
						      System.out.println("ɾ��ʧ�ܣ���");
						}
					}else {
						System.out.println("ɾ��ʧ�ܣ���");
					}
					
				}else {
					valus.getFatherFileModel().getFilesMap().remove(valus.getName());
					totailFileAndDistory.remove(forEachGetFather(valus.getFatherFileModel())+"/"+valus.getName());
					System.out.println("ɾ���ɹ�����");
				}
				
			}
		}else {
			System.out.println("ɾ��ʧ�ܣ�Ŀ¼�����ڣ�");
		}
	}
	//�л�����һ��
	public void returnLastDistory() {
		if(currentPathFileModel.getName().equals("root:")){
			System.out.println("���л�����Ŀ¼�����޷��ٷ�����һ��");
		}else {
			currentPathFileModel=currentPathFileModel.getFatherFileModel();
		}
	}
	
	//�и�·������ȡ�ϼ���·��
	private String[] getPathArray(String path) {
		return path.split("/");
	}
	//���·������Ч�ģ��ͷ�����һ����FileModel����
	private FileModel getFatherFileModel(String s) {
		int i=s.lastIndexOf("/");
		return totailFileAndDistory.get(String.valueOf(s.substring(0,i)));
	}
	//���õݹ麯������һ��һ���ļ�����һ��Ŀ¼���һ��������·��
	private String forEachGetFather(FileModel fileModel) {
		if(fileModel.getName().equals("root:")) {
			return "root:";
		}
		return forEachGetFather(fileModel.getFatherFileModel())+"/"+fileModel.getName();
		
	}
	//���õݹ�ķ�ʽɾ���ļ������ļ�
	private boolean deleteAll(FileModel file) {
		if(file.getAttr()==DISTORY_CODE) {
			List<FileModel> files=new ArrayList<>();
			//��ΪMap�ڱ�����ʱ������ڵĴ�С�����ı��ʱ�򣬾ͻᷢ����������Ҫͨ��һ���м��List������
			//��ʱ�洢�����е�FileModel,Ȼ�����ɾ��
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
		//�����ļ��ľ�ֱ��ɾ�����Ҳ�Ϊ�յľ�ֱ��
		return delete(file);
	}
	private boolean delete(FileModel fileModel) {
	     //�����ļ������Ƴ�Ŀ¼�����ļ���
		 return fileModel.getFatherFileModel().getFilesMap().remove(fileModel.getName(),fileModel)
			&&totailFileAndDistory.remove(forEachGetFather(fileModel),fileModel);
		
	}
	//���ص�ǰ��·��
	public String returnCurrentPath() {
		return forEachGetFather(currentPathFileModel);
	}
}
