package com.huangxin.keshe;

import java.util.HashMap;
import java.util.Map;

public class FileModel {
	//该类的作用是定义文件或者文件夹的相关内容
	private String name;//目录或者文件的名称
	private int attr;//表示是文件还是目录，1:表示文件，2:表示目录
	//用来存储改节点下的文件和目录
	private Map<String,FileModel> filesMap=new HashMap<String,FileModel>();
	private FileModel fatherFileModel;//  用来记录上一级的目录
	
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
