## Test rest meals by curl (examples)

**GetAllMeals**

curl <http://localhost:8080/topjava/rest/meals>

**GetMealById**

curl <http://localhost:8080/topjava/rest/meals/100005>

**DeleteMealById**

curl -X DELETE <http://localhost:8080/topjava/rest/meals/100005>

**UpdateMeal**

curl -X PUT -H "Content-Type: application/json" -d '{"dateTime":"2015-05-31T10:30:00", "description":"breakfast_new", "calories":1000}' <http://localhost:8080/topjava/rest/meals/100005>

**NewMeal**

curl -H "Content-Type: application/json" -d '{"dateTime": "2019-05-31T10:00:00", "description": "Meal_new", "calories": 1500}' <http://localhost:8080/topjava/rest/meals>

**FilterMealsByDateTime**

curl <http://localhost:8080/topjava/rest/meals/filter?startDateTime=2015-05-31T09:00:00\&endDateTime=2015-05-31T14:00:00>

curl <http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-31&endDate=2015-05-31&startTime=09:00&endTime=14:00>