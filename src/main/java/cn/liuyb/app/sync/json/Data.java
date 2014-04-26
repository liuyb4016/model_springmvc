package cn.liuyb.app.sync.json;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

//以下注解含义：如何从Json中辨认Data的实现类？通过Json对象中的"dt"字段来辨别，通过其值找到对应的实现类。
@JsonTypeInfo(include = As.PROPERTY, property = "dt", use = Id.NAME)
public interface Data extends Serializable {

    
}
