package com.squidfoundry.array;

import lucee.loader.engine.CFMLEngine;
import lucee.loader.engine.CFMLEngineFactory;
import lucee.runtime.PageContext;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.BIF;
import lucee.runtime.ext.function.Function;
import lucee.runtime.type.Array;
import lucee.runtime.util.Cast;

public class ArrayShift extends BIF implements Function {

	private static final long serialVersionUID = -3725521903087347848L;

	public static Object call(Array arrayToShift) throws PageException {
		if(arrayToShift.size() > 0) {
			return arrayToShift.removeE(1);
		}
		return arrayToShift;
	}
	
	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		
		CFMLEngine engine = CFMLEngineFactory.getInstance();
		Cast caster = engine.getCastUtil();
		
		if(args.length == 1) {
			return call(caster.toArray(args[0]));
		}
		
		throw engine.getExceptionUtil().createFunctionException(pc, "ArrayShift", 1, 1, args.length);
		
	}
	
}
