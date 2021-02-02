create table t_alert_subscribe
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    account varchar(128) null comment '账号名称',
    workflow_id int null comment '关联t_workflow表id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_account_workflow_id
        unique (account, workflow_id)
)
    comment '工作流告警订阅';

create table t_check_source_log
(
    id int(11) unsigned auto_increment
        primary key,
    db_url varchar(256) default '' not null comment '数据库url',
    db_port int not null comment '数据库端口',
    db_type tinyint not null comment '数据库类型（0：mysql, 1：hive）',
    db_name varchar(64) default '' not null comment '数据库名',
    table_name varchar(256) default '' not null comment '表名',
    state tinyint not null comment '是否就绪(0：未就绪,1：就绪)',
    duration int default -1 null comment '等待时间',
    task_instance_id int null comment '关联t_task_instance表id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '依赖表等待时间记录';

create table t_constant_info
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    table_name varchar(256) null comment '常量表名',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '常量表记录，这些表不检查就绪时间';

create table t_cron
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    name varchar(64) null comment ' 名称',
    expression varchar(128) null comment '定时调度cron表达式',
    start_date timestamp null comment '开始时间',
    end_date datetime null comment '结束时间',
    oozie_id varchar(255) null comment 'oozie id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '定时调度启动器';

create table t_data_source_info
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    type tinyint null comment '数据源类型（0：mysql, 1：hive）',
    env tinyint null comment '数据源环境（0：开发环境,1:测试环境，2:正式环境，3:临时环境）',
    url varchar(128) default '' null comment 'jdbc连接url',
    port int null comment '端口',
    username varchar(64) null comment '用户名',
    password varchar(64) null comment '密码',
    extra json null comment '其他信息',
    description varchar(1024) null comment '描述信息',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '数据库信息';

create table t_reflux_group
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    name varchar(64) null comment '组名',
    env tinyint null comment '环境，(0:开发环境，1：测试环境，2：正式环境，3:临时环境)',
    source_id int null comment '回流目标数据库id,关联t_data_source_info的id',
    source_db varchar(255) null,
    sink_id int null comment '回流目标数据库id,关联t_data_source_info的id',
    sink_db varchar(255) null,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table t_reflux_table
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    name varchar(64) default '' not null comment '组名',
    env tinyint not null comment '环境，(0:开发环境，1：测试环境，2：正式环境，3:临时环境)',
    sink_tb varchar(255) default '' not null comment '回流表名',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_env_sink_tb
        unique (env, sink_tb)
)
    comment '回流信息表，用于调度信息快速检索';

create table t_table_monitor
(
    id int(11) unsigned auto_increment
        primary key,
    identify varchar(255) null comment '唯一标识',
    deadline varchar(10) null comment '告警时间',
    tables varchar(1024) null comment '告警的表：库名.表名,库名.表名',
    alerting tinyint(3) null comment '0,没有告警，1告警'
);

create table t_table_scheduled
(
    id int(11) unsigned auto_increment
        primary key,
    db_url varchar(256) default '' not null comment '数据库url',
    db_port int not null comment '数据库端口',
    db_type tinyint not null comment '数据库类型（0：mysql, 1：hive）',
    db_name varchar(64) default '' not null comment '数据库名',
    table_name varchar(256) default '' not null comment '表名',
    biz_date date not null comment '业务日期',
    ready_date timestamp not null comment '就绪时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table t_table_scheduled_hour
(
    id int(11) unsigned auto_increment
        primary key,
    db_url varchar(256) default '' not null comment '数据库url',
    db_port int not null comment '数据库端口',
    db_type tinyint not null comment '数据库类型（0：mysql, 1：hive）',
    db_name varchar(64) default '' not null comment '数据库名',
    table_name varchar(256) default '' not null comment '表名',
    biz_date date not null comment '业务日期',
    biz_time time not null comment '就绪时间：小时分钟',
    ready_date timestamp not null comment '就绪时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '小时级别调度记录';

create table t_task
(
    id int(11) unsigned auto_increment comment '主键'
        primary key,
    name varchar(128) null comment '名称',
    type tinyint null comment '0: sql etl；1：回流',
    content json null comment '不同类別的內容',
    level int null comment '执行层次',
    version int null comment '版本号',
    is_delete tinyint null comment '0：未删除，1：删除',
    workflow_id int null comment '关联t_workflow表的id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间',
    priority tinyint default 0 null comment '敏感等级[0-10]'
)
    comment '工作流节点';

create table t_task_counter
(
    id int(11) unsigned auto_increment comment '主键'
        primary key,
    task_instance_id int not null comment '关联t_task_instance表id',
    task_counter json null comment 'task instance 采集的指标',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table t_task_instance
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    name varchar(128) null comment '名称',
    state tinyint(3) null comment '任务实例状态: 0 提交成功, 1 运行中, 2 等待暂定, 3 暂停, 4 等待停止, 5 停止, 6 失败, 7 成功, 8 kill, 9 等待依赖完成',
    submit_time datetime null comment '调度时间',
    start_time datetime null comment '开始时间',
    end_time datetime null comment '结束时间',
    run_times int null comment '执行时间，单位秒',
    param json null comment '运行时绑定的业务参数',
    oozie_id varchar(255) null comment 'ooze id',
    task_id int null comment '关联t_task表id',
    workflow_instance_id int null comment '关联workflow_instance表id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '任务实例';

create table t_workflow
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    name varchar(128) null comment '工作流名称',
    mark varchar(128) null comment '英文标识',
    env tinyint null comment '环境（0：开发；1：测试；2：正式）',
    cron_id int null comment '关联t_cron表id',
    description varchar(1024) null comment '描述',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    version int default 1 null comment '版本',
    is_delete tinyint default 0 null comment '是否删除（0：未删除；1：删除）',
    priority varchar(4) null comment '工作流优先级',
    type varchar(16) default 't+1' null comment '工作流类型(小时级别:real-time，离线：t+1',
    ready_time varchar(16) default '23:59' null comment '期待就绪时间',
    constraint uk_mark_env
        unique (mark, env)
)
    comment '工作流';

create table t_workflow_instance
(
    id int(11) unsigned auto_increment comment '自增主键'
        primary key,
    name varchar(128) null comment '工作流名称+时间戳',
    type tinyint(3) null comment '0：调度执行; 1：启动工作流, 2：重跑',
    state tinyint(3) null comment '工作流实例状态: 0 提交成功, 1 运行中, 2 等待暂定, 3 暂停, 4 等待停止, 5 停止, 6 失败, 7 成功, 8 kill, 9 等待依赖完成',
    rate tinyint(3) null comment '处理进度',
    param json null comment '运行时绑定的业务参数',
    schedule_time datetime null comment '调度时间',
    start_time datetime null comment '开始时间',
    end_time datetime null comment '结束时间',
    run_times int null comment '执行时间，单位秒',
    workflow_id int null comment '关联t_ workflow表id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '工作流实例';

