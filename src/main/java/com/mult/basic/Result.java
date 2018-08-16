package com.mult.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * 通用返回结果
 * @author Weirdo
 *
 */
@JsonInclude(Include.NON_NULL)
public class Result implements Serializable{
	private static final long serialVersionUID = 5014379068811962022L;
	/**0成功,1失败*/
	private Integer stat=1;
	/**返回码*/
	private String code;
	/**返回信息*/
	private String msg;
	/**返回数据*/
	private Object data;

	public Integer getStat() {
		return stat;
	}

	public void setStat(Integer stat) {
		this.stat = stat;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result{" +
				"stat=" + stat +
				", code='" + code + '\'' +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Result result = (Result) o;

		if (stat != null ? !stat.equals(result.stat) : result.stat != null) {
			return false;
		}
		if (code != null ? !code.equals(result.code) : result.code != null) {
			return false;
		}
		if (msg != null ? !msg.equals(result.msg) : result.msg != null) {
			return false;
		}
		return data != null ? data.equals(result.data) : result.data == null;
	}

	@Override
	public int hashCode() {
		int result = stat != null ? stat.hashCode() : 0;
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (msg != null ? msg.hashCode() : 0);
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}

}
