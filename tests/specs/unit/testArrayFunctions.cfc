component displayname="My test suite" extends="testbox.system.BaseSpec" {

	function testArrayPop() {
		var myArray = ['orange', 'bannana', 'apple', 'raspberry'];
		var fruit = ArrayPop(myArray);
		$assert.isEqual(['orange', 'bannana', 'apple'], myArray);
		$assert.isEqual('raspberry', fruit);
	}

	function testArrayShift() {
		var myArray = ['orange', 'bannana', 'apple', 'raspberry'];
		var fruit = ArrayShift(myArray);
		$assert.isEqual(['bannana', 'apple', 'raspberry'], myArray);
		$assert.isEqual('orange', fruit);
	}

	function testArraySpliceRemoveNoneInsertOne() {
		var myArray = ['angel', 'clown', 'mandarin', 'sturgeon'];
		var removed = ArraySplice(myArray, 3, 0, ['drum']);
		$assert.isEqual([], removed);
		$assert.isEqual(['angel', 'clown', 'drum', 'mandarin', 'sturgeon'], myArray);
	}

	function testArraySpliceRemoveOne() {
		var myArray = ['angel', 'clown', 'drum', 'mandarin', 'sturgeon'];
		var removed = ArraySplice(myArray, 4, 1);
		$assert.isEqual(['mandarin'], removed);
	}

	function testArraySpliceRemoveOneInsertOne() {
		var myArray = ['angel', 'clown', 'drum', 'sturgeon'];
		var removed = ArraySplice(myArray, 3, 1, ['trumpet']);
		$assert.isEqual(['drum'], removed);
		$assert.isEqual(['angel', 'clown', 'trumpet', 'sturgeon'], myArray);
	}

	function testArraySpliceRemoveTwoInsertThree() {
		var myArray = ['angel', 'clown', 'trumpet', 'sturgeon'];
		var removed = ArraySplice(myArray, 1, 2, ['parrot', 'anemone', 'blue']);
		$assert.isEqual(['angel', 'clown'], removed);
		$assert.isEqual(['parrot', 'anemone', 'blue', 'trumpet', 'sturgeon'], myArray);
	}

	function testArraySpliceRemoveTwoFromThirdIndex() {
		var myArray = ['parrot', 'anemone', 'blue', 'trumpet', 'sturgeon'];
		var removed = ArraySplice(myArray, myArray.len() - 2, 2);
		$assert.isEqual(['blue', 'trumpet'], removed);
	}

	function testArraySpliceRemoveOneFromNegativeIndex() {
		var myArray = ['angel', 'clown', 'mandarin', 'sturgeon'];
		var removed = ArraySplice(myArray, -2, 1);
		$assert.isEqual(['mandarin'], removed);
	}

	function testArraySpliceRemoveAllAfterThirdIndex() {
		var myArray = ['angel', 'clown', 'mandarin', 'sturgeon'];
		var removed = ArraySplice(myArray, 3);
		$assert.isEqual(['mandarin', 'sturgeon'], removed);	
	}

}