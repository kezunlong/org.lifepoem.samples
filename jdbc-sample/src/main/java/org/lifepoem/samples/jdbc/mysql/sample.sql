CREATEDATABASE `library` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO users(name, password, email, birthday)
VALUES('zs', '123456', 'zs@sina.com', '1980-1-10');
INSERT INTO users(name, password, email, birthday)
VALUES('柯尊龙', 'IRIT*zaq1', 'lifepoem@163.com', '1981-3-6');
INSERT INTO users(name, password, email, birthday)
VALUES('test', '123456', 'test@sina.com', '1990-09-10');

CREATE PROCEDURE `add_pro`(a INT, b INT, OUT sum INT)
BEGIN
	SET SUM = a + b;
END

CREATE TABLE `testclob` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `resume` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `testblob` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `img` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


