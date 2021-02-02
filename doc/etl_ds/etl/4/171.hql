SET mapred.max.split.size=268435456;
SET hive.exec.reducers.bytes.per.reducer=2147483648;
SET hive.exec.reducers.max=512;
INSERT OVERWRITE TABLE seewo_cdm.zz PARTITION (dt_d) SELECT id,dt_d FROM seewo.base_x