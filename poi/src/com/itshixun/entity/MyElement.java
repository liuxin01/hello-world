package com.itshixun.entity;

public class MyElement {
	private String id;
	private String prefix;//--ǰ׺
	private String name;
	private String znName;
	private String enName;
	private String itemType;//--��������
	private String subsitutionGroup;//--�滻��
	private String periodType;//--�ڼ�����
	private String balance;//--�������
	private String ghostElement;//--��Ԫ��
	private String nullable;//--�ÿ�����
	private String reference;//--�ο�
	private	String showznLabel;//--չʾ��ǩ(zn)
	private String showenLabel;//--չʾ��ǩ(en)
	private String firstLabel;//--��ѡ��ǩ
	
	public String getId(){
		return id;
	}	
	public String getPrefix() {
		return prefix;
	}
	public String getName() {
		return name;
	}
	public String getZnName() {
		return znName;
	}
	public String getEnName() {
		return enName;
	}
	public String getItemType() {
		return itemType;
	}
	public String getSubsitutionGroup() {
		return subsitutionGroup;
	}
	public String getPeriodType() {
		return periodType;
	}
	public String getBalance() {
		return balance;
	}
	public String getGhostElement() {
		return ghostElement;
	}
	public String getNullable() {
		return nullable;
	}
	public String getReference() {
		return reference;
	}
	public String getShowznLabel() {
		return showznLabel;
	}
	public String getShowenLabel() {
		return showenLabel;
	}
	public String getFirstLabel() {
		return firstLabel;
	}
	
	public void setId(){		
		this.id =this.prefix+"_"+this.name;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setZnName(String znName) {
		this.znName = znName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setSubsitutionGroup(String subsitutionGroup) {
		this.subsitutionGroup = subsitutionGroup;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public void setGhostElement(String ghostElement) {
		this.ghostElement = ghostElement;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public void setShowznLabel(String showznLabel) {
		this.showznLabel = showznLabel;
	}
	public void setShowenLabel(String showenLabel) {
		this.showenLabel = showenLabel;
	}
	public void setFirstLabel(String firstLabel) {
		this.firstLabel = firstLabel;
	}

}
