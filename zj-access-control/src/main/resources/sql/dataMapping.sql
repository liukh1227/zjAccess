

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_datamapping
-- ----------------------------
DROP TABLE IF EXISTS `sys_datamapping`;
CREATE TABLE `sys_datamapping` (
  `catalogString` varchar(50) NOT NULL,
  `keyString` varchar(50) NOT NULL,
  `valueString` varchar(100) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `orderSeq` int(11) DEFAULT NULL,
  PRIMARY KEY (`catalogString`,`keyString`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 初始化数据
-- 数据字典
insert into DataMapping(catalogString,keyString,valueString,enabled,orderSeq) values('ROOT','SYS_LEVEL','系统级别',1,0);
insert into DataMapping(catalogString,keyString,valueString,enabled,orderSeq) values('ROOT','TORF','是/否',1,1);
insert into DataMapping(catalogString,keyString,valueString,enabled,orderSeq) values('ROOT','ENABLED','启/停用',1,2);
insert into DataMapping(catalogString,keyString,valueString,enabled,orderSeq) values('ROOT','GENDER','性别',1,3);



insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('SYS_LEVEL','S','系统',1,1);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('SYS_LEVEL','U','用户',1,2);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('TORF','T','是',1,1);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('TORF','F','否',1,2);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('ENABLED','1','启用',1,1);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('ENABLED','0','停用',1,2);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('GENDER','M','男',1,1);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('GENDER','F','女',1,2);


insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('ROOT','DECLGOODSXLS','报关品名Excel格式',1,71);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','tradeNo','新贸序号',1,50);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','*name','商品品名',1,50);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','seqNo','海关HS编号',1,20);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','addiCode','附加编码',1,2);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','specNo','型号/料号',1,60);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','goodsId','货号',1,20);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','amount','申报数量',1,0);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','unit','申报单位',1,10);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','unitPrice','单价',1,0);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','totalPrice','总价',1,0);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','currency','币种',1,10);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','legalAmount','法定数量',1,0);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','legalUnit','法定单位',1,10);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','versionNo','版本号',1,10);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','purpose','用途',1,10);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','secAmount','第二数量',1,0);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','secUnit','第二单位',1,10);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','orign','原产国地',1,50);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','destinationCountry','目的国',1,50);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','taxExempt','征免方式',1,50);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','processingFees','工缴费',1,50);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','standardContent','规范申报',1,500);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','customerOrderId','客户订单号',1,50);
insert into DataMapping(catalog,keyString,valueString,enabled,orderSeq) values('DECLGOODSXLS','customerGoodsNo','客户商品编号',1,50);