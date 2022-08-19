package cn.edu.guet.SecurityCore.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class RestResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    // 响应业务状态
    private String status;
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;

    public static RestResult build(String status, String msg, Object data) {
        return new RestResult(status, msg, data);
    }


    public static RestResult ok(Object data) {
        return new RestResult(data);
    }

    public static RestResult ok() {
        return new RestResult(null);
    }

    public RestResult() {

    }

    public static RestResult build(String status, String msg) {
        return new RestResult(status, msg, null);
    }

    public RestResult(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public RestResult(Object data) {
        this.status = "200";
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == "200";
    }

    /*
     * 功能描述：将json结果集转化为RestResult对象
     */
    public static RestResult formatToPojo(String jsonData, Class<?> clazz) {


        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, RestResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").asText(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * 功能描述：没有object对象的转化
     */
    public static RestResult format(String json) {
        try {
            return MAPPER.readValue(json, RestResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 功能描述：Object是集合转化
     */
    public static RestResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").asText(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
