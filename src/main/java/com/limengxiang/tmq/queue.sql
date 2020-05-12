CREATE TABLE tmq_queue (
  id INT NOT NULL AUTO_INCREMENT,
  queue_name VARCHAR(64) NOT NULL COMMENT '队列名称',
  msg_id VARCHAR(24) NOT NULL COMMENT '消息ID',
  body VARCHAR(1024) NOT NULL COMMENT '消息体',
  schedule DATETIME NOT NULL COMMENT '投递时间',
  status TINYINT NOT NULL COMMENT '消息状态',
  receive_at DATETIME NOT NULL COMMENT '接收时间',
  consume_at DATETIME COMMENT '消费时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_msg_id (msg_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;