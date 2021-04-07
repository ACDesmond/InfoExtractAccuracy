package com.nju.infoextract.service;


import com.nju.infoextract.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("xxxcqService")
public class XxxcqService {

    @Autowired
    private AyDao ayDao;
    @Autowired
    private AyXxxDao ayXxxDao;

    @Autowired
    private XxxDao xxxDao;

//    @Transactional(readOnly = true)
//    public Document xxxcq(String aydm, MultipartFile file) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, DocumentException {
//
//        // 通过aydm获取信息项list（顺序且信息项拥有抽取子程序）
//        List<XxxEntity> xxxEntityList = getXxxEntityListByAydm(aydm);
//        // 通过信息项list获取抽取子程序list（顺序）
//        List<CqzcxEntity> cqzcxEntityList = getCqzcxEntityListByXxxEntityList(xxxEntityList);
//
//        // 通过file获取全文
//        String qw = getQwByFile(file);
//
//        // 创建write根节点
//        Element root = DocumentHelper.createElement("write");
//
//        // 添加QW节点（全文）
//        Element node = root.addElement("QW");
//        node.addAttribute("nameCN", "全文");
//        node.addAttribute("value", qw);
//
//        // 遍历加载并执行抽取子程序，添加执行结果
//        SAXReader reader = new SAXReader();
//        for (CqzcxEntity cqzcxEntity : cqzcxEntityList) {
//
//            // 加载并执行抽取子程序，获取结果
//            Byte[] zjm = cqzcxEntity.getZjm();
//            byte[] zjm_primitive = ArrayUtils.toPrimitive(zjm);
//            Map<String, byte[]> bytecode = new HashMap<String, byte[]>();
//            bytecode.put("Extract", zjm_primitive);
//
//            DynamicLoader.MemoryClassLoader classLoader = new DynamicLoader.MemoryClassLoader(bytecode);
//
//            Class cqzcx = classLoader.loadClass("Extract");
//            Object object = cqzcx.newInstance();
//            Method method = cqzcx.getMethod("extract", String.class);
//            String cqxxx = (String) method.invoke(object, qw);
//
//            // 添加节点
//            Document document = reader.read(new ByteArrayInputStream(cqxxx.getBytes()));
//            Element element = document.getRootElement();
//            root.add((Element) element.clone());
//        }
//
//        // 根据XML文档根节点创建输出文档，并返回
//        Document document = DocumentHelper.createDocument(root);
//        return document;
//    }
//
//    // 通过信息项list获取抽取子程序list（顺序）
//    private List<CqzcxEntity> getCqzcxEntityListByXxxEntityList(List<XxxEntity> xxxEntityList) {
//
//        // 通过信息项id获取抽取子程序id
//        List<XxxCqzcxEntity> xxxCqzcxEntityList = new ArrayList<XxxCqzcxEntity>(xxxEntityList.size());
//        QueryWrapper<XxxCqzcxEntity> queryWrapper_XxxCqzcxEntity = new QueryWrapper<>();
//        for (XxxEntity xxxEntity : xxxEntityList) {
//            queryWrapper_XxxCqzcxEntity.select("cqzcx_id").eq("xxx_id", xxxEntity.getId());
//            XxxCqzcxEntity xxxCqzcxEntity = xxxCqzcxDao.selectOne(queryWrapper_XxxCqzcxEntity);
//            xxxCqzcxEntityList.add(xxxCqzcxEntity);
//
//            queryWrapper_XxxCqzcxEntity.clear();
//        }
//
//        // 通过抽取子程序id获取抽取子程序
//        List<CqzcxEntity> cqzcxEntityList = new ArrayList<CqzcxEntity>(xxxCqzcxEntityList.size());
//        QueryWrapper<CqzcxEntity> queryWrapper_CqzcxEntity = new QueryWrapper<>();
//        for (XxxCqzcxEntity xxxCqzcxEntity : xxxCqzcxEntityList) {
//            queryWrapper_CqzcxEntity.select("zjm").eq("id", xxxCqzcxEntity.getCqzcxId());
//            CqzcxEntity cqzcxEntity = cqzcxDao.selectOne(queryWrapper_CqzcxEntity);
//            cqzcxEntityList.add(cqzcxEntity);
//            queryWrapper_CqzcxEntity.clear();
//        }
//
//        return cqzcxEntityList;
//    }
//
//    // 通过aydm获取信息项list（顺序且信息项拥有抽取子程序）
//    private List<XxxEntity> getXxxEntityListByAydm(String aydm) {
//        // 通过aydm获取案由id
//        QueryWrapper<AyEntity> queryWrapper_AyEntity = new QueryWrapper<>();
//        queryWrapper_AyEntity.select("id").eq("aydm", aydm);
//        AyEntity ayEntity = ayDao.selectOne(queryWrapper_AyEntity);
//        Long ay_id = ayEntity.getId();
//
//        // 通过案由id获取信息项id（升序排序）
//        QueryWrapper<AyXxxEntity> queryWrapper_AyXxxEntity = new QueryWrapper<>();
//        queryWrapper_AyXxxEntity.select("xxx_id").eq("ay_id", ay_id).orderByAsc("sx");
//        List<AyXxxEntity> ayXxxEntityList = ayXxxDao.selectList(queryWrapper_AyXxxEntity);
//
//        // 通过信息项id获取信息项list（顺序）
//        List<XxxEntity> xxxEntityList = new ArrayList<XxxEntity>(ayXxxEntityList.size());
//        QueryWrapper<XxxEntity> queryWrapper_XxxEntity = new QueryWrapper<>();
//        for (AyXxxEntity ayXxxEntity : ayXxxEntityList) {
//            queryWrapper_XxxEntity.select("id", "xxxmc", "xxxsxjc").eq("id", ayXxxEntity.getXxxId()).eq("sfyycqzxc", 1);
//            XxxEntity xxxEntity = xxxDao.selectOne(queryWrapper_XxxEntity);
//            xxxEntityList.add(xxxEntity);
//            queryWrapper_XxxEntity.clear();
//        }
//
//        return xxxEntityList;
//    }
//
//    // 通过file获取全文
//    private String getQwByFile(MultipartFile file) throws IOException {
//        InputStream is = file.getInputStream();
//        XWPFDocument doc = new XWPFDocument(is);
//        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//        String qw = extractor.getText();
//        return qw;
//    }

}

