SET mapred.max.split.size=268435456;
SET hive.exec.reducers.bytes.per.reducer=2147483648;
SET hive.exec.reducers.max=512;
use seewo_cdm_ds;
        INSERT OVERWRITE TABLE seewo_cdm_ds.c PARTITION (dt_d)
        SELECT a.id, a.dt_d
        FROM a a
                 left join b b
                           on a.id = b.id and a.dt_d = b.dt_d;
