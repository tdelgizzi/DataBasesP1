// query 8: Find the average user friend count for all users with the same hometown city
// using MapReduce
// Using the same terminology in query7, we are asking you to write the mapper,
// reducer and finalizer to find the average friend count for each hometown city.


var city_average_friendcount_mapper = function() {
  // implement the Map function of average friend count
  var key = this.hometown.city;
  var value = {count:1, qty:this.friends.length};

  emit(key,value);

};

var city_average_friendcount_reducer = function(key, values) {
  // implement the reduce function of average friend count
  reducedVal = { count: 0, qty: 0 };
   for (var idx = 0; idx < values.length; idx++) {
       reducedVal.count += values[idx].count;
       reducedVal.qty += values[idx].qty;
   }


  return (reducedVal);

};

var city_average_friendcount_finalizer = function(key, reduceVal) {
  // We've implemented a simple forwarding finalize function. This implementation
  // is naive: it just forwards the reduceVal to the output collection.
  // You may need to change it to pass the test case for this query


  var ret = (reduceVal.qty / reduceVal.count);
  return ret;
}
