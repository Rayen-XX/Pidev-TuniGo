package com.esprit.gu.util.json;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of JSONObject to replace the missing org.json dependency
 */
public class JSONObject {
    private Map<String, Object> map;

    public JSONObject() {
        this.map = new HashMap<>();
    }

    public JSONObject(String json) {
        this();
        // Very simple parsing for demo purposes
        if (json != null && json.startsWith("{") && json.endsWith("}")) {
            String content = json.substring(1, json.length() - 1);
            String[] pairs = content.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim().replace("\"", "");
                    put(key, value);
                }
            }
        }
    }

    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public boolean has(String key) {
        return map.containsKey(key);
    }

    public String getString(String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    public boolean getBoolean(String key) {
        Object value = map.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return "true".equalsIgnoreCase(String.valueOf(value));
    }

    public int getInt(String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean isNull(String key) {
        return map.get(key) == null || "null".equals(map.get(key));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else {
                sb.append(value);
            }
        }
        sb.append("}");
        return sb.toString();
    }
} 