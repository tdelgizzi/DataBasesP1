// query3
// create a collection named "cities". Each document in the collection should contain
// two fields: a field called "_id" holding the city name, and a "users" field holding
// an array of user_ids who currently live in that city.
// Example: each document has following schema:
/*
{
  _id: city
  users:[userids]
}
*/

function cities_table(dbname) {
    db = db.getSiblingDB(dbname);
    // TODO: implemente cities collection here
    db.users.aggregate([ {$group : { _id: "$current.city", users: {$push: "$user_id" } } }, {$out: "cities"} ])
    //db.users.aggregate([ {$group : { _id: "$hometown.city", users: {$push: "$user_id" } } }, {$out: "test3"} ])
    // Returns nothing. Instead, it creates a collection inside the datbase.

}
