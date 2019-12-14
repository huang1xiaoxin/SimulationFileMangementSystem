package com.huangxin.keshe;

import java.util.HashMap;
import java.util.Map;

public class FileModel {
	//����������Ƕ����ļ������ļ��е��������
	private String name;//Ŀ¼�����ļ�������
	private int attr;//��ʾ���ļ�����Ŀ¼��1:��ʾ�ļ���2:��ʾĿ¼
	//�����洢�Ľڵ��µ��ļ���Ŀ¼
	private Map<String,FileModel> filesMap=new HashMap<String,FileModel>();
	private FileModel fatherFileModel;//  ������¼��һ����Ŀ¼
	
	public FileModel(String name, int attr) {
		super();
		this.name = name;
		this.attr = attr;
	}
	public String getName() {
		return name;
	}
	public int getAttr() {
		return attr;
	}
	public Map<String, FileModel> getFilesMap() {
		return filesMap;
	}
	public FileModel getFatherFileModel() {
		return fatherFileModel;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAttr(int attr) {
		this.attr = attr;
	}
	public void setFilesMap(Map<String, FileModel> filesMap) {
		this.filesMap = filesMap;
	}
	public void setFatherFileModel(FileModel fatherFileModel) {
		this.fatherFileModel = fatherFileModel;
	}
	
			

}
