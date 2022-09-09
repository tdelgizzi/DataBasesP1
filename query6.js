// query6 : Find the average user friend count
//
// Return a decimal as the average user friend count of all users
// in the users document.

function find_average_friendcount(dbname){
  db = db.getSiblingDB(dbname)
  // TODO: return a decimal number of average friend count
  var total = [];
  //db.users.find().forEach(function(in){total.push(in.friends.length)})
  db.users.find().forEach(function(myDoc) {total.push(myDoc.friends.length ) } );
  var sum = 0.0;
  var i;
  for (i = 0; i < total.length;i++){
    sum+=total[i];
  }
  return (sum / db.users.find().count());
  //return sum;

}
