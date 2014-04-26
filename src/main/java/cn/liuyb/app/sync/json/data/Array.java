package cn.liuyb.app.sync.json.data;

import java.util.Collections;
import java.util.List;
import org.codehaus.jackson.annotate.JsonTypeName;

import cn.liuyb.app.sync.json.Data;

@JsonTypeName("array")
public class Array implements Data {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2234877654960429467L;
	private List<? extends Data> array = Collections.emptyList();

    public Array(){}

    public Array(List<? extends Data> array) {
        this.array = array;
    }


    public List<? extends Data> getArray() {
        return array;
    }

    public void setArray(List< ? extends Data> array) {
        this.array = array;
    }

}