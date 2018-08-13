-- 删除表
-- drop table seckill;
-- 创建秒杀库存表
create table seckill(
	seckill_id BIGINT not null auto_increment comment '商品库存id',
	name varchar(255) not null comment '商品名称',
	number int not null comment '商品库存数',
	create_time timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	start_time TIMESTAMP not null comment '开始时间',
	end_time TIMESTAMP not null comment '结束时间',
	PRIMARY KEY(seckill_id),
	key idx_start_time(start_time),
	key idx_end_time(end_time),
	key idx_create_time(create_time)
)ENGINE=INNODB auto_increment = 1000 default CHARSET=utf8 comment='秒杀库存表';

-- 插入数据
insert into seckill(name,number,start_time,end_time) VALUES
('1000秒杀iphone8',100,'2018-08-13 11:11:11','2018-08-13 11:11:11'),
('500秒杀小米',100,'2018-08-13 11:11:11','2018-08-13 11:11:11'),
('999秒杀ipad2',100,'2018-08-13 11:11:11','2018-08-13 11:11:11');

-- 创建秒杀记录表
create table success_killed(
	seckill_id BIGINT not null comment '商品库存id',
	phone bigint not null comment '手机号码',
	state tinyint not null comment '秒杀状态 0:秒杀成功,1:秒杀失败',
	create_time timestamp not null comment '秒杀时间',
	primary key(seckill_id,phone),
	key idx_create_time(create_time) -- 索引
)engine = innodb default charset = utf8 comment='秒杀成功明细表'