SET mapred.max.split.size=268435456;
SET hive.exec.reducers.bytes.per.reducer=2147483648;
SET hive.exec.reducers.max=512;
use seewo_cdm_ds;
        INSERT OVERWRITE TABLE seewo_cdm_ds.zz PARTITION (dt_d)
        SELECT id, dt_d
        FROM hh;
