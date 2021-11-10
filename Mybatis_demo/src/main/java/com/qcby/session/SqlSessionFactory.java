package com.qcby.session;

import com.qcby.conf.Configuration;
import com.qcby.conf.MappedStatement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class SqlSessionFactory {
    private static Configuration configuration = new Configuration();

    private final static String CONFIGURL = "D:\\Mybatis_demo\\src\\main\\resources\\SqlMapConfig.xml";
    private final static String DAOURL = "D:\\Mybatis_demo\\src\\main\\resources\\mapper\\UserDao.xml";

    //1-获取XML-IO流
    private static InputStream  getXmlInputStream(String xmlPath){
        InputStream inputStream = null;
        try {
            //1-把要解析的 XML 文档转化为输入流，以便 DOM 解析器解析它
            inputStream= new FileInputStream(xmlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    //2-解析XML-IO流 ，获取Document 对象，以及Document对象 的根节点
    private static Element getRootElementFromIs(InputStream inputStream) throws Exception {
        if(inputStream == null){
            return  null;
        }
        /*
         * javax.xml.parsers 包中的DocumentBuilderFactory用于创建DOM模式的解析器对象 ，
         * DocumentBuilderFactory是一个抽象工厂类，它不能直接实例化，但该类提供了一个newInstance方法 ，
         * 这个方法会根据本地平台默认安装的解析器，自动创建一个工厂的对象并返回。
         */
        //2-调用 DocumentBuilderFactory.newInstance() 方法得到创建 DOM 解析器的工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //3-调用工厂对象的 newDocumentBuilder方法得到 DOM 解析器对象。
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        //4-调用 DOM 解析器对象的 parse() 方法解析 XML 文档，得到代表整个文档的 Document 对象，进行可以利用DOM特性对整个XML文档进行操作了。
        Document doc = docBuilder.parse(inputStream);
        //5-得到 XML 文档的根节点
        Element root =doc.getDocumentElement();
        //6-关闭流
        if(inputStream != null){
            inputStream.close();
        }
        return root;
    }


    //3-从根元素解析得到元素
    private static void parseElementFromRoot(Element root) {

        // 解析获取Dao层的数据
        XMLparseElementDao(root);

        // 解析获取Dao层的数据
        XMLparseElementConfig(root);

    }

    //解析获取config当中的数据
    public static void XMLparseElementConfig(Element root){
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                //从元素解析特定子元素并解析(以property为例)
                getElementConfig(ele);
            }
        }
    }


    // 获取Dao层的数据
    public  static void XMLparseElementDao(Element root){
        String namespace = root.getAttribute("namespace");
        NodeList childNodes = root.getChildNodes();
        for (int i=0;i<childNodes.getLength();i++){
            Node item = childNodes.item(i);
            if (item instanceof  Element){
                Element ele = (Element) item;
                getElementFromDao(ele,namespace);
            }
        }
    }

    // 将获取的dao层数据放入 配置文件
    private static void getElementFromDao(Element ele,String namespace) {

        MappedStatement mappedStatement = new MappedStatement();

        String id = ele.getAttribute("id");
        String sql = ele.getTextContent().trim();
        String sourceId = namespace+"."+id;
        String resultType = ele.getAttribute("resultType");

        mappedStatement.setNamespace(namespace);
        mappedStatement.setSql(sql);
        mappedStatement.setSourceId(sourceId);
        mappedStatement.setResultType(resultType);

        configuration.getMappedStatements().put(sourceId,mappedStatement);
    }




    //4.加载config.xml中的数据
    private static void getElementConfig(Element ele) {
        NodeList propertyEleList = ele.getElementsByTagName("property");//根据标签名称获取标签元素列表
        for (int i = 0; i < propertyEleList.getLength(); i++) {
            Node node = propertyEleList.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                if (name.equals("driver")){
                    configuration.setDbDriver(propertyEle.getAttribute("value"));
                }else if (name.equals("url")){
                    configuration.setDbUrl(propertyEle.getAttribute("value"));
                }else if(name.equals("username")){
                    configuration.setDbUserName(propertyEle.getAttribute("value"));
                }else if (name.equals("password")){
                    configuration.setDbPassWord(propertyEle.getAttribute("value"));
                }
            }
        }

    }

    // 操作XML
    public static void Config() throws Exception {
        //1-获取XML-IO流
        InputStream xmlInputStream = getXmlInputStream(CONFIGURL);
        //2-解析XML-IO流 ，获取Document 对象，以及Document对象 的根节点
        Element rootElement = getRootElementFromIs(xmlInputStream);
        //3~5-从根元素解析得到元素
        parseElementFromRoot(rootElement);
    }

    //操作Dao层
    public static void Dao() throws Exception {
        //1-获取XML-IO流
        InputStream xmlInputStream = getXmlInputStream(DAOURL);
        //2-解析XML-IO流 ，获取Document 对象，以及Document对象 的根节点
        Element rootElement = getRootElementFromIs(xmlInputStream);
        //3~5-从根元素解析得到元素
        parseElementFromRoot(rootElement);
    }


    public static void main(String[] args) throws Exception {
        Config();
        Dao();
        System.out.println(configuration.toString());
    }

}
