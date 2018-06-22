package com.squidfoundry.array;

import lucee.loader.engine.CFMLEngine;
import lucee.loader.engine.CFMLEngineFactory;
import lucee.runtime.PageContext;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.BIF;
import lucee.runtime.ext.function.Function;
import lucee.runtime.type.Array;
import lucee.runtime.util.Cast;
import lucee.runtime.util.Creation;

public final class ArraySplice extends BIF implements Function {
	
	private static final long serialVersionUID = -1317699629722097761L;
	
	private static CFMLEngine cfmlEngine;
	private static Creation creator;
	private static Cast caster;
	
	public static Object call(Array arrayToSplice, int offset) throws PageException {
		return _call(arrayToSplice, offset, arrayToSplice.size(), creator.createArray());
	}
	
	public static Object call(Array arrayToSplice, int offset, int length) throws PageException {
		return _call(arrayToSplice, offset, length, creator.createArray());
	}
	
	public static Object call(Array arrayToSplice, int offset, int length, Array replacement) throws PageException {
		return _call(arrayToSplice, offset, length, replacement);
	}

	private static Object _call(Array arrayToSplice, int offset, int length, Array replacement) throws PageException {	
		int arrayLength = arrayToSplice.size();
		int begin = calculateOffset(offset, arrayLength);
		int count = length;
		
		if(length < 0) {
			count = arrayLength - begin + length;
		} else if((begin + length) > arrayLength) {
			count = arrayLength - begin + 1;
		}
		
		int end = (int) (begin + count);
		Object result;
		if(count != 0) {
			if(count == 1) {
				Array resultArray = creator.createArray();
				resultArray.append(arrayToSplice.getE(begin));
				result = resultArray;
			} else {
				Array resultArray = creator.createArray();
				for(int last = begin; last != end; last++) {
					Object temp = arrayToSplice.getE(last);
					resultArray.append(temp);
				}
				result = resultArray;
			}
		} else {
			result = creator.createArray();
		}
		
		int delta = replacement.size() - count;
		
		if(delta > 0) {
			for(int last = arrayLength; last >= end; last--) {
				Object temp = arrayToSplice.getE(last);
				arrayToSplice.setE(last + delta, temp);
			}
		} else if(delta < 0) {
			for (int last = end; last < arrayLength; last++) {
                Object temp = arrayToSplice.getE(last);
                arrayToSplice.setE(last + delta, temp);
            }
            for (int k = arrayLength + delta; k < arrayLength; ++k) {
            	arrayToSplice.removeE(k);
            }
		}
		
		for(int i = 0; i < replacement.size(); i++) {
			arrayToSplice.setE(begin + i, replacement.getE(i + 1));
		}
		
		return result;
	}

	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		cfmlEngine = CFMLEngineFactory.getInstance();
		caster = cfmlEngine.getCastUtil();
		creator = cfmlEngine.getCreationUtil();
		
		if(args.length == 2) {
			return call(caster.toArray(caster.toArray(args[0])), caster.toIntValue(args[1]));
		}
		
		if(args.length == 3) {
			return call(caster.toArray(caster.toArray(args[0])), caster.toIntValue(args[1]), caster.toIntValue(args[2]));
		}
		
		if(args.length == 4) {
			return call(caster.toArray(caster.toArray(args[0])), caster.toIntValue(args[1]), caster.toIntValue(args[2]), caster.toArray(args[3]));
		}
		
		throw cfmlEngine.getExceptionUtil().createFunctionException(pc, "ArraySplice", 1, 4, args.length);
	}
	
	private static int calculateOffset(int offset, int arrayLength) {
		if(offset > arrayLength) {
			offset = arrayLength;
		} else if(offset < 0 && (offset = (arrayLength + offset + 1)) < 0) {
			offset = 0;
		}

		return offset;
	}
}
