package com.micro.cloud.modules.process.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FieldConvertCache {

  public static Set<String> contactSet = new HashSet<>(8);
  // processKey,字段映射关系
  public static Map<String, Map<String, String>> fieldCacheMap = new HashMap<>(16);

  static {
    // 需携带附件processKey
    contactSet.add("process_htspzb");
    contactSet.add("process_htspfzb");
    // 存入变更
    Map<String, String> gcbglx = new HashMap<>();
    // 变更序号
    gcbglx.put("xmbh", "modifyNum");
    // 合同编号预留
    gcbglx.put("gcbgje", "modifyAmount");
    // 变更原因
    gcbglx.put("gcbgyy", "modifyItem");
    fieldCacheMap.put("process_gcbglx", gcbglx);

    // 存入招标需求
    Map<String, String> zbxqb = new HashMap<>();
    zbxqb.put("bdmc", "sectionName");
    zbxqb.put("bdhf", "sectionDivde");
    zbxqb.put("bdnr", "sectionContent");
    zbxqb.put("bdgsj", "sectionAmount");
    zbxqb.put("create_time", "createTime");

    fieldCacheMap.put("process_zbxqb", zbxqb);

    // 存入招标文件（资格预审）
    Map<String, String> zgys = new HashMap<>();
    // 标段名称
    zgys.put("bdmc", "sectionName");
    // 项目批文
    zgys.put("xmpw", "approvalDocuments");
    // 工程分类:0.特大型 1.中型 2.中型 3.小型 4.技术复杂型
    zgys.put("gcfl", "engineerType");
    // 发包类型
    zgys.put("fblx", "originationType");
    zgys.put("create_time", "createTime");
    // 合同估算价格
    zgys.put("htgsj", "contractEstimate");
    fieldCacheMap.put("process_zgys", zgys);

    // 存入项目立项
    Map<String, String> xmlx = new HashMap<>();
    xmlx.put("bt", "documentTitle");
    xmlx.put("fwzh", "documentCode1");
    xmlx.put("fwzh1", "documentCode2");
    xmlx.put("fwzh2", "documentCode3");
    xmlx.put("ztc", "documentKeywords");
    xmlx.put("xmdm", "xmdm");
    xmlx.put("lxpfwh", "lxpfwh");
    xmlx.put("ngr", "createUserName");
    xmlx.put("create_time", "createTime");
    // 工程费用
    xmlx.put("gcfy", "engCost");
    // 工程建设其他费
    xmlx.put("gcjsqtfy", "engConstCost");
    // 设备购置费
    xmlx.put("sbgzf", "deviceBuyCost");
    // 预备费
    xmlx.put("ybf", "prePareCost");
    // 拆迁补偿
    xmlx.put("cqbc", "demolitionCompensate");
    fieldCacheMap.put("process_xmlx", xmlx);

    // 存入设计概算
    Map<String, String> sjgs = new HashMap<>();
    sjgs.put("bt", "documentTitle");
    sjgs.put("fwzh1", "documentCode1");
    sjgs.put("fwzh2", "documentCode2");
    sjgs.put("fwzh3", "documentCode3");
    sjgs.put("ztc", "documentKeywords");
    sjgs.put("creator_time", "createTime");
    // 工程费用
    sjgs.put("gcfy", "engCost");
    // 工程建设其他费
    sjgs.put("gcjsqtf", "engConstCost");
    // 设备购置费
    sjgs.put("sbgzf", "deviceBuyCost");
    // 预备费
    sjgs.put("ybf", "prePareCost");
    // 拆迁补偿
    sjgs.put("cqbc", "demolitionCompensate");
    fieldCacheMap.put("process_sjgs", sjgs);

    // 存入施工图设计
    Map<String, String> sgtsj = new HashMap<>();
    sgtsj.put("bt", "documentTitle");
    sgtsj.put("fwzh1", "documentCode1");
    sgtsj.put("fwzh2", "documentCode2");
    sgtsj.put("fwzh3", "documentCode3");
    sgtsj.put("ztc", "documentKeywords");
    sgtsj.put("ngr", "createUserName");
    sgtsj.put("create_time", "createTime");

    fieldCacheMap.put("process_sgtsj", sgtsj);

    // 合同审批（招标）
    Map<String, String> htspzb = new HashMap<>();
    htspzb.put("htbh", "contractNumber");
    htspzb.put("xmlxwh", "approvalDocuments");
    htspzb.put("htmc", "contractName");
    htspzb.put("wtfs", "commissionType");
    htspzb.put("htjk", "contractAmount");
    htspzb.put("htjf", "partyA");
    htspzb.put("htyf", "partyB");
    htspzb.put("htbf", "partyC");
    htspzb.put("cbcs", "undertakeDept");
    htspzb.put("bsrq", "reportDate");
    fieldCacheMap.put("process_htspzb", htspzb);
    // 合同审批（非招标）
    Map<String, String> htspfzb = new HashMap<>();
    htspfzb.put("htbh", "contractNumber");
    htspfzb.put("xmlxwh", "approvalDocuments");
    htspfzb.put("htmc", "contractName");
    htspfzb.put("wtfs", "commissionType");
    htspfzb.put("htjk", "contractAmount");
    htspzb.put("htjf", "partyA");
    htspzb.put("htyf", "partyB");
    htspzb.put("htbf", "partyC");
    htspfzb.put("cbcs", "undertakeDept");
    htspfzb.put("bsrq", "reportDate");
    fieldCacheMap.put("process_htspfzb", htspfzb);
  }
}
