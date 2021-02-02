SET mapred.max.split.size=268435456;
SET hive.exec.reducers.bytes.per.reducer=2147483648;
SET hive.exec.reducers.max=512;
use seewo_cdm_dev;
        drop table if exists i;

        create table i
        (
            id string comment 'id'
        ) partitioned by (dt_d string) stored as parquet tblproperties ('comment' = 'i');
