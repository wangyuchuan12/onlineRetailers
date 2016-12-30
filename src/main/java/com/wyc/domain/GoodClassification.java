package com.wyc.domain;

import javax.persistence.Entity;
import javax.persistence.Id;


//商品分类
@Entity(name = "good_classification")
public class GoodClassification {
	 @Id
	 private String id;
}
