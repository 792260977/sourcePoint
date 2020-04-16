package com.source.point.sourcePoint.utils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonUtils {

	private final static Gson GSON = GsonUtils.getGson();

	private static Gson getGson() {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			@Override
			public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}
		}).registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>() {
			@Override
			public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(src.getTime());
			}
		}).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
			@Override
			public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			}
		}).registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type type,
					JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String datetime = json.getAsJsonPrimitive().getAsString();
				return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
		}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			@Override
			public LocalDate deserialize(JsonElement json, Type type,
					JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String datetime = json.getAsJsonPrimitive().getAsString();
				return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}
		}).registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>() {
			@Override
			public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				long time = json.getAsLong();
				return new Timestamp(time);
			}
		}).addSerializationExclusionStrategy(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				// 这里作判断，决定要不要排除该字段,return true为排除
				JsonIgnore ignore = f.getAnnotation(JsonIgnore.class);
				if (ignore != null) {
					return true;
				}
				return false;
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				// 直接排除某个类 ，return true为排除
				if (!Serializable.class.isAssignableFrom(clazz)) {
					// return true;
				}
				return false;
			}
		}).serializeNulls().disableHtmlEscaping().create();

		return gson;
	}

	public static String toJson(Object src) {
		return GSON.toJson(src);
	}

	public static String fromObject2Json(Object src) {
		return GSON.toJson(src);
	}

	/**
	 * 支持解析泛型
	 *
	 * @param jsonStr
	 *            json串
	 * @param cls
	 *            解析对象
	 * @param clazz
	 *            泛型属性
	 * @author:summer
	 * @date:2017年6月14日 下午5:34:36
	 */
	public static <T> T fromJson2Object(String jsonStr, Class<T> cls, Class<?>... clazz) {
		TypeUtils.parameterize(cls, clazz);
		Type objectType = type(cls, clazz);
		return GSON.fromJson(jsonStr, objectType);
	}

	/**
	 * Json串解析为java对象
	 * 
	 * @param jsonStr
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> T fromJson2Object(String jsonStr, Class<T> cls) {
		return GSON.fromJson(jsonStr, cls);
	}

	/**
	 * Map类型转为java对象
	 * 
	 * @param map
	 * @param cls
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T fromMap2Object(Map map, Class<T> cls) {
		return fromJson2Object(toJson(map), cls);
	}

	/**
	 * 对象转Map
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map fromObject2Map(Object obj) {
		return fromJson2Object(fromObject2Json(obj), Map.class);
	}

	@SuppressWarnings("unchecked")
	public static MultiValueMap<String, Object> fromObject2Multimap(Object obj) {
		if (obj == null) {
			return null;
		}
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		Map<String, Object> tempMap = fromObject2Map(obj);
		for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
			Object v = entry.getValue();
			String k = entry.getKey();
			if (v instanceof String) {
				params.add(k, v);
			} else if (isPrimitive(v)) {
				params.add(k, String.valueOf(v));
			} else {
				params.add(k, fromObject2Json(v));
			}
		}
		return params;
	}

	/** 判断一个对象是否是基本类型或基本类型的封装类型 */
	public static boolean isPrimitive(Object obj) {
		try {
			return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}

	}

	static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {
			@Override
			public Type getRawType() {
				return raw;
			}

			@Override
			public Type[] getActualTypeArguments() {
				return args;
			}

			@Override
			public Type getOwnerType() {
				return null;
			}
		};
	}

}
