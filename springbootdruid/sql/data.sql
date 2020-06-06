CREATE TABLE `tb_student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sno` varchar(10) DEFAULT NULL,
  `sname` varchar(100) DEFAULT NULL,
  `ssex` char(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `tb_student` VALUES ('1', '001', '张三', 'M');
INSERT INTO `tb_student` VALUES ('2', '002', '李四', 'M');
INSERT INTO `tb_student` VALUES ('3', '003', '王五', 'F');