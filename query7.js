// query7: Find the number of users born in each month using MapReduce

var num_month_mapper = function() {
  // Implement the map function
  var key = this.MOB;
  var value = {count:1, id:this.user_id};

  emit(key,value);

}

var num_month_reducer = function(key, values) {
  // Implement the reduce function
  reducedVal = { count: 0, id: 0 };
   for (var idx = 0; idx < values.length; idx++) {
       reducedVal.count += values[idx].count;
       reducedVal.id += values[idx].qty;
   }


  return (reducedVal);

}

var num_month_finalizer = function(key, reduceVal) {
  // We've implemented a simple forwarding finalize function. This implementation
  // is naive: it just forwards the reduceVal to the output collection.
  // Feel free to change it if needed.
  var ret = reduceVal.count;

  return ret;
}
