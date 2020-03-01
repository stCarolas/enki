package com.github.stcarolas.enki.core.util;

import io.vavr.Function1;

import static io.vavr.API.*;

public class Spy1<T1,T2> {
	private Function1<T1,T2> fn;
	private Boolean isCalled = false;

	public static <T1,T2>Spy1<T1,T2> spy(Function1<T1,T2> fn){
		var spy = new Spy1<T1,T2>();
		spy.fn = fn;
		return spy;
	}

	public Function1<T1,T2>  fn(){
		return Function( arg1 -> {
			isCalled = true;
			return fn.apply(arg1);
		});
	}

	public Boolean isCalled(){
		return isCalled;
	}

}
