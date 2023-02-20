package com.example.demo.club.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityConvert {

static ObjectMapper objectMapper = new ObjectMapper();
	
	public static<F,T> Object convert(F from){
		//ModelMapperconvert
        if(from == null) return null;
        return objectMapper.convertValue(from, opponentClass(from.getClass()));
	}
	
	// List를 변환해준다.
    public static<F,T> List<T> convertList(List<F> froms){
    	List<T> ts = new ArrayList<>();
        if(froms.size() == 0) return ts;
        for (F from : froms) {
           ts.add((T) objectMapper.convertValue(from, opponentClass(from.getClass())));
        }
        return ts;
    }
	
	public static<T> Class opponentClass(Class<T> from) {
		Class objectClass = null;
		String className = from.getName();
        // 경로를 포함한 클래스 이름이 나온다.
        className = className.substring(className.lastIndexOf(".")+1).toLowerCase();
        
        // 클래스의 전체 이름(경로를 포함)
        // com.semi.dto.MemberDTO
        // com.semi.entity.Member
        // 디렉토리 이름과 클래스 이름을 수정해서 ClassLoader를 통해 변환할 클래스를 찾아낸다.
        try {
            if(className.contains("dto")) {
                objectClass = from.getClassLoader().loadClass(from.getName().replace(".dto", ".domain").replace("DTO",""));
            }else {
            	objectClass = from.getClassLoader().loadClass(from.getName().replace(".domain", ".dto") + "DTO");
            	
            }
            	
        } catch (ClassNotFoundException e) {
            return null;
        }
        return objectClass;
	}
}
