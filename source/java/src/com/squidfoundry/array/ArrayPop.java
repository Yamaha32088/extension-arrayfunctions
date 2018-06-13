package com.squidfoundry.array;

import lucee.loader.engine.CFMLEngine;
import lucee.loader.engine.CFMLEngineFactory;
import lucee.runtime.PageContext;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.BIF;
import lucee.runtime.ext.function.Function;
import lucee.runtime.type.Array;
import lucee.runtime.util.Cast;

public class ArrayPop extends BIF implements Function {

	private static final long serialVersionUID = 849213838350625616L;

	public static Object call(Array arrayToPop) throws PageException {
		if(arrayToPop.size() > 0) { 
			return arrayToPop.removeE(arrayToPop.size());
		}
		return null;
	}
	
	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		
		CFMLEngine engine = CFMLEngineFactory.getInstance();
		Cast caster = engine.getCastUtil();
		
		if(args.length == 1) {
			return call(caster.toArray(args[0]));
		}
		
		throw engine.getExceptionUtil().createFunctionException(pc, "ArrayPop", 1, 1, args.length);
		
	}

}
