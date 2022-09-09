
// query 4: find user pairs (A,B) that meet the following constraints:
// i) user A is male and user B is female
// ii) their Year_Of_Birth difference is less than year_diff
// iii) user A and B are not friends
// iv) user A and B are from the same hometown city
// The following is the schema for output pairs:
// [
//      [user_id1, user_id2],
//      [user_id1, user_id3],
//      [user_id4, user_id2],
//      ...
//  ]
// "user_id" is the field from the users collection that you should use.
// Do not use the "_id" field in the users collection.

function suggest_friends(year_diff, dbname) {
    db = db.getSiblingDB(dbname);
    var pairs = [];

      db.users.find({"gender":"male"}).forEach( function(male) {
          db.users.find({"gender":"female"}).forEach(function(female){
          if(Math.abs(male.YOB - female.YOB) < year_diff){
            if(male.hometown.city == female.hometown.city){
              if (male.friends.indexOf(female.user_id) == -1 && female.friends.indexOf(male.user_id) == -1){
                let temp = [male.user_id,female.user_id];
                pairs.push(temp);
              }
            }
          }
        })
      });


    // TODO: implement suggest friends
    // Return an array of arrays.
    return pairs;
}



//db.users.find({"gender":"male"}).forEach( function(male) { db.users.find({"gender":"female"}).forEach(function(female){ if(Math.abs(male.YOB - female.YOB) < year_diff){ if(male.hometown.city != female.hometome.city){ if (male.friends.indexOf(female.user_id) == -1){ let temp = [male.user_id,female.user_id]; pairs.push(temp);}}}}});
