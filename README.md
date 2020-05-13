
# TMQ: Timing Message Queue

定时消息队列

介绍 [TMQ:定时消息队列](https://blog.csdn.net/u010205879/article/details/106094316)

## API

### 投递消息

```
push(MessageInterface msg)
```

### 拉取消息
```
pull(Date t)
```

### 查询消息
```
poll(String msgId)
```

### 获取分片大小
```
size(Date t)
```

### 更新消息
```
consumed(MessageInterface msg)
```

## 开始使用

### 准备基础环境

MySQL连接

```
jdbc:mysql://127.0.0.1:3306/mydemo
```

Redis连接

```
localhost:6379
```

### 创建数据表

```
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
```

### 测试

见 com.limengxiang.tmq.TimingMessageQueueTest

### 查看数据

MySQL

```
> SELECT * FROM tmq_queue;
```

Redis

```
> keys *:slice:*

> lrange {slice key} 0 -1
```

## 消息ID生成算法

参考 [xid](https://github.com/rs/xid)