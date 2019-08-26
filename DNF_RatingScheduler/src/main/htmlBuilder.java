package main;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class htmlBuilder {

	private StringBuffer sb = null;
	private Stack<String> endtag = null;
	
	/**
	 * @description 첫 생성자에선 <html>를 생성
	 */
	public htmlBuilder() {
		this.sb = new StringBuffer();
		this.endtag = new Stack<String>();
	}
	
	/**
	 * @return this
	 * @description 마지막으로 호출했던 tag를 가져와 태그를 끝맽음 </tag>
	 */
	public htmlBuilder endTag() {
		if(endtag == null) {
		}
		try {
			this.sb.append("</" + endtag.pop() + ">");
		}catch(EmptyStackException e) {
			System.out.println("마지막으로 tag가 입려되지 않았거나 이미 tagEnd를 호출하여 태그를 끝맞췄습니다.");
			throw e;
		}
		return this;
	}
	
	/**
	 * @return this
	 * @description <your tag>
	 */
	public htmlBuilder tag(String tag) {
		this.sb.append("<" + tag + ">");
		this.endtag.push(tag);
		return this;
	}
	
	/**
	 * @return this
	 * @param attrText
	 * @description <your tag> and attrText
	 * {@code} tag("td","style='width:100px; color:red;'");
	 * 
	 */
	public htmlBuilder tag(String tag, String attrText) {
		this.sb.append("<" + tag + " " + attrText + ">");
		this.endtag.push(tag);
		return this;
	}
	
	/**
	 * @param tag
	 * @param attr
	 * @return this
	 * @description <your tag> and attribute map
	 */
	public htmlBuilder tag(String tag, Map attr) {
		this.sb.append("<" + tag + " ");
		
		Set setkey = attr.keySet();
		for(Iterator intertor = setkey.iterator(); intertor.hasNext();) {
			String key = (String)intertor.next();
			String value = (String)attr.get(key);
			sb.append(key + "='" + value + "'; ");
		}
		this.sb.append(">");
		
		this.endtag.push(tag);
		return this;
	}
	
	/**
	 * @return this
	 * @description <br>
	 */
	public htmlBuilder setText(String text) {
		this.sb.append(text);
		return this;
	}	
	
	/**
	 * @return this
	 * @description <br>
	 */
	public htmlBuilder br() {
		this.sb.append("<br>");
		return this;
	}	
	
	/**
	 * @return this
	 * @param number
	 * @description <br> * number 
	 */
	public htmlBuilder br(int number) {
		for(int i=0; i<number; i++)
			this.sb.append("<br>");
		
		return this;
	}
	
	/**
	 * @return this
	 * @description <hr>
	 */
	public htmlBuilder hr() {
		this.sb.append("<hr>");
		return this;
	}
	
	/**
	 * @return String
	 * @description 완성된 html코드를 반환
	 */
	public String build() {
		return sb.toString();
	}
	
}
