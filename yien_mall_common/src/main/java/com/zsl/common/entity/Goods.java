package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "mall_goods", type = "goods", shards = 5, replicas = 1)
public class Goods implements Serializable {
	/**
	 * 主键，自增
	 */
	@Id
	private Integer id;

	/**
	 * 商品表的唯一索引
	 */
	@Field(type = FieldType.Keyword)
	private String goodsId;

	/**
	 * 商品对应的 商家的id
	 */
	@Field(type = FieldType.Integer)
	private Integer goodsMerchantsId;

	/**
	 * 商品对应的 商家的name
	 */
	@Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
	private String goodsMerchantsName;

	/**
	 * 商品名称
	 */
	@Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
	private String goodsName;

	/**
	 * 商品现在的价格
	 */
	@Field(type = FieldType.Double)
	private BigDecimal goodsNowAmount;

	/**
	 * 商品的默认价格
	 */
	@Field(type = FieldType.Double)
	private BigDecimal goodsDefaultAmount;

	/**
	 * 商品的生产地
	 */
	@Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
	private String goodsProdPlace;

	/**
	 * 商品的生产时间
	 */
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date goodsProdTime;

	/**
	 * 商品的保质时间
	 */
	@Field(type = FieldType.Keyword)
	private String goodsShelfLife;

	/**
	 * 商品的过期时间
	 */
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date goodsExpirTime;

	/**
	 * 商品的总体描述
	 */
	@Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
	private String goodsDesc;

	/**
	 * 商品的细节1
	 */
	@Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
	private String goodsDetail1;

	/**
	 * 商品的细节2
	 */
	@Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
	private String goodsDetail2;

	/**
	 * 商品的细节3
	 */
	@Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
	private String goodsDetail3;

	/**
	 * 商品的图片1
	 */
	@Field(type = FieldType.Text, index = false)
	private String goodsImage1;

	/**
	 * 商品的图片2
	 */
	@Field(type = FieldType.Text, index = false)
	private String goodsImage2;

	/**
	 * 商品的图片3
	 */
	@Field(type = FieldType.Text, index = false)
	private String goodsImage3;

	/**
	 * 商品所属类别的id
	 */
	@Field(type = FieldType.Integer)
	private Integer goodsTypeId;

	/**
	 * selling_will：准备开始销售；selling_now：正在销售；discontinued：已停售
	 */
	@Field(type = FieldType.Text, index = false)
	private String goodsState;

	/**
	 * 商品开始销售的时间
	 */
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date goodsSellTime;

	/**
	 * 商品停止销售的时间
	 */
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date goodsDiscontinuedTime;

	/**
	 * 商品的库存
	 */
	@Field(type = FieldType.Integer)
	private Integer goodsInventory;

	/**
	 * 新增时间
	 */
	@Field(type = FieldType.Date, index = false, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date rawAddTime;

	/**
	 * 修改时间
	 */
	@Field(type = FieldType.Date, index = false, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date rawUpdateTime;

}