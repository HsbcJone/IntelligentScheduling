package com.etl.ds.core.engine;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * CDH测试环境支持提交hivesql的job
 */
@Component
public class Core1 implements CoreEngine {


    @Override
    public boolean runJob(String workflowId,String actionXmlName){
        String oozieUrl="http://cdh-10-21-17-94:11000/oozie/";
        Properties conf = new Properties();
        String userName = System.getProperty("user.name");
        conf.setProperty("user.name", "hive");
        conf.setProperty("nameNode", "hdfs://cdhtest");
        conf.setProperty("jobTracker", "yarnRM");
        conf.setProperty("launcher_queue", "root.jwth.etl");
        conf.setProperty("mapreduce_queue","root.jwth.etl");
        conf.setProperty("mapreduce.job.user.name","jwth");
        conf.setProperty("oozie.use.system.libpath", "true");
        conf.setProperty("oozie.wf.rerun.failnodes", "true");
        conf.setProperty("security_enabled", false+"");
        //hive的database
        conf.setProperty("catalogDatabase","seewo_cdm_dev");
        conf.setProperty("catalogTable","test_1");
        conf.setProperty("hiveMetaStoreUri","thrift://cdh-10-21-17-95:9083");
        //hcatlog的hdfs地址
        conf.setProperty("catalogHome","/opt/cloudera/parcels/CDH/lib/hive-hcatalog");
        conf.setProperty("dryrun","false");
        conf.setProperty("security_enabled","false");
        //mysql的配置
        conf.setProperty("mysqlUrl","jdbc:mysql://sr-dev-mysql-master-1.gz.cvte.cn:3306/dw_data_steam_beta?characterEncoding=UTF-8&useSSL=false");
        conf.setProperty("n","seewo");
        conf.setProperty("p","seewo@cvte");

        //workflow.xml的地址
        //conf.setProperty("oozie.wf.application.path","hdfs://cdhtest/user/mengxp/oozieHiveBak/my.xml");
        conf.setProperty("oozie.wf.application.path","hdfs://cdhtest/user/jwth/etl_ds/etl/"+workflowId+"/"+actionXmlName+".xml");
        conf.setProperty("hiveServer2Url","jdbc:hive2://psd-hadoop038:10000/default");
        conf.setProperty("hiveServerUrl","jdbc:hive2://psd-hadoop038:10000/default");
        conf.setProperty("hiveDb","seewo_cmd_dev");
        conf.setProperty("hivePassword","123456");
        //这个参数是Java的lib包的路径
        //存入hive的哪张表
        //workflow的参数
        conf.setProperty("etl_name",workflowId.concat("##").concat(actionXmlName));
        conf.setProperty("script","/user/jwth/etl_ds/etl/"+workflowId+"/"+actionXmlName+".hql");
        conf.setProperty("biz_date","2021-02-01");
        conf.setProperty("p","123456");
        try {
            OozieClient client=new OozieClient(oozieUrl);
            String oozieJobId=client.run(conf);
            System.out.println(conf);
            while (client.getJobInfo(oozieJobId).getStatus().equals(WorkflowJob.Status.RUNNING)){
                System.out.println("Workflow job running ...");
                Thread.sleep(10 * 1000);
            }
            System.out.println("Workflow job completed ...");
            System.out.println(client.getJobInfo(oozieJobId));
        } catch (OozieClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}


