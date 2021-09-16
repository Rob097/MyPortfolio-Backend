package com.rob.core.utils.java;

public interface IMapper<I,O> {
	O map(I input);
	O map(I input, O output);
}
