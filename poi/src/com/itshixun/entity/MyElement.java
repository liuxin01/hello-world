package com.itshixun.entity;

public class MyElement {
	private String id;
	private String prefix;//--前缀
	private String name;
	private String znName;
	private String enName;
	private String itemType;//--数据类型
	private String subsitutionGroup;//--替换组
	private String periodType;//--期间类型
	private String balance;//--借贷属性
	private String ghostElement;//--虚元素
	private String nullable;//--置空属性
	private String reference;//--参考
	private	String showznLabel;//--展示标签(zn)
	private String showenLabel;//--展示标签(en)
	private String firstLabel;//--首选标签
	
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
