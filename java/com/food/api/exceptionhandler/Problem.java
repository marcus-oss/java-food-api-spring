package com.food.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder

public class Problem {

	private Integer status;
	private String type;

	private String title;
	private String detail;

	private LocalDateTime timestamp;
	private String userMessage;
	private List<Objcet> objects;

	@Getter
	@Builder
	public static class Objcet {
		private String name;
		private String userMessage;
	}

}
