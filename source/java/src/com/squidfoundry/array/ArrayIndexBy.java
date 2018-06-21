package com.squidfoundry.array;

import java.util.Map.Entry;

import lucee.loader.engine.CFMLEngine;
import lucee.loader.engine.CFMLEngineFactory;
import lucee.runtime.PageContext;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.BIF;
import lucee.runtime.ext.function.Function;
import lucee.runtime.type.Array;
import lucee.runtime.type.Collection.Key;
import lucee.runtime.type.Objects;
import lucee.runtime.type.Struct;
import lucee.runtime.type.UDF;
import lucee.runtime.util.Creation;

public class ArrayIndexBy extends BIF implements Function {

	private static Creation creator;
	private static CFMLEngine engine;
	
	private static final long serialVersionUID = -7804739353353594984L;

	public static Object call(PageContext pc, Array array, Object keyOrClosure) throws PageException {
		
		java.util.Iterator<Entry<Key, Object>> it = array.entryIterator();
		
		Entry<Key, Object> e;
		
		Struct structToReturn = creator.createStruct();
		
		while(it.hasNext()) {
			e = it.next();
			
			if(keyOrClosure instanceof UDF) {
				String key = (String) ((UDF) keyOrClosure).call(pc, new Object[]{ e.getValue() }, true);
				structToReturn.setEL(creator.createKey(key), e.getValue());
			} else {
				String key = (String) ((Struct) e.getValue()).get(keyOrClosure);
				structToReturn.setEL(creator.createKey(key), e.getValue());
			}
					
		}
		
		return structToReturn;
	}
	
	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		engine = CFMLEngineFactory.getInstance();
		creator = engine.getCreationUtil();
		
		if(args.length == 2) {
			return call(pc, (Array)args[0], args[1]);
		}
		
		throw engine.getExceptionUtil().createFunctionException(pc, "ArrayIndexBy", 2, 2, args.length);
	}

	
	
}
