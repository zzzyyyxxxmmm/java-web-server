package project;

import java.lang.reflect.GenericArrayType;
import java.util.*;
public class RequestHeader {

	
	private HashMap<String,String>	headers=new HashMap<>();
	//存储请求参数的容器
	private HashMap<String, ArrayList<String>> params= new HashMap<>();
	/**
	 * 存储一个头消息
	 * @param headerName
	 * @param headerValue
	 * @return
	 */
	public boolean pushHeader(String headerName,String headerValue){
		
		try {
			headers.put(headerName, headerValue);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
	/**
	 * 获取头消息内容
	 * @param headerName
	 * @return
	 */
	public String getHeaderByName(String headerName){
		return headers.get(headerName);
	}
	
	public int getHeaderCount(){
		return headers.size();
	}
	/**
	 * 添加一个请求参数到容器中
	 * @param paramName
	 * @param param
	 * @return
	 */
	public boolean pushParam(String paramName,ArrayList<String> param){
		try {
			params.put(paramName, param);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	/**
	 * 根据参数名获取请求参数
	 * @param paramName
	 * @return
	 */
	public ArrayList<String> GetParamByName(String paramName){
		return params.get(paramName);
	}
	
	public int getParamsCount(){
		Collection<ArrayList<String>> values= params.values();
		int count=0;
		for (ArrayList<String> arrayList : values) {
			count+=arrayList.size();
		}
		return count;
	}
	
	@Override
	public String toString() {
		return "RequestHeader [headers=" + headers + "]"+"\r\n [params="+params+"]";
	}
	
	
}
