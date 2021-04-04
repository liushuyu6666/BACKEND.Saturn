## Resources

### Document

#### User

1. **`id`**: <u>String</u>

   - `@Id` auto-generated

2. **`username`**: <u>String</u>,

   - `@NotBlank`

   - `@Size(max=50)`

3. **`password`**: <u>String</u>

   - `@NotBlank`
   - `@Size(max=100)`

4. **`email`**: <u>String</u>

   - `@Size(max=100)`
   - `@Email`

5. **`role`**: <u>set<Role></u>

   -  `@DBRef`

6. **`createAt`**: <u>Date</u>

   - `@CreatedDate`

7. **`modifiedAt`**: <u>Date</u>

   - `@LastModifiedDate`

#### Shop

1. **`id`**: <u>String</u>

   - `@Id` auto-generated

2. **`name`**: <u>String</u>

   - `@NotBlank`

   - `@Size(max=50)`

3. **`desc`**: <u>String</u>, description of the shop

   - `@Size(max=100)`

4. **`imgUrl`**: <u>String</u>, image URL in S3

5. **`categories`**: <u>set<String></u>

   - manual reference of  `ETaxonomy`

6. **`owners`**: <u>set<String></u>

   - manual reference of `User`

7. **`address`**: <u>Object</u>

   1. **`country`**: <u>String</u>
   2. **`city`**: <u>String</u>
   3. **`street`**: <u>String</u>

8. **`createAt`**: <u>Date</u>, auto-generate

9. **`modifiedAt`**: <u>Date</u>, auto-update

#### **Dish**

1. **`id`**: <u>String</u>, generate by time
2. **`shopId`**: <u>String</u>
3. **`name`**: <u>String</u>
4. **`imgUrl`**: <u>String</u>, name of image start with dish
5. **`desc`**: <u>String</u>, description
6. **`category`**: <u>set<String></u>
7. **`top3Reviews`**: <u>list<ShortReview></u>
8. **`price`**: <u>Float</u>
9. **`createAt`**: <u>Date</u>, auto-generate
10. **`modifiedAt`**: <u>Date</u>, auto-update

#### Review

1. **`id`**: auto-generated
2. **`userId`**: <u>String</u>
3. **`dishId`**: <u>String</u>
4. **`star`**: <u>Integer</u>
5. **`content`**: <u>String</u>
6. **`createAt`**: <u>Date</u>, auto-generate
7. **`modifiedAt`**: <u>Date</u>, auto-update

#### Order

1. **`id`**: `@Id` auto-generated
2. **`userId`**: <u>String</u>
3. **`createAt`**: <u>Date</u>, auto-generate
4. **`orderDetailList`**: <u>Array</u>, each order can cross shop
   1. **`shopId`**: <u>String</u>
   2. **`DishIdList`**: <u>Array</u>, ordered dishes' IDs

#### Role

1. `id`: auto-generated
2. `name`: `<u>ERole</u>`

### Other entities

#### `ERole`

an `enum` class contains:

- `ROLE_CUSTOMER`
- `ROLE_OWNER`
- `ROLE_ADMIN`

#### `ETaxonomy`

an `enum` class contains:

- Fast Food
- Breakfast and Brunch
- American
- Mexican
- Chinese
- Japanese
- Italian
- Healthy
- Asian
- Indian
- Thai
- Taiwanese
- Alcohol
- Halal
- Bakery
- Comfort Food
- Middle Eastern
- Pizza
- Turkish
- Korean
- Deli
- German
- Desserts
- Vegan

## System sketch

### security, authentication and authorization

#### filter chain

- `FilterChain`: `interface`

## Account

### user accounts

- `liushuyu`: ["customer", "admin", "owner"]
- `customer`: ["customer"]
- `admin`: ["admin"]
- `owner`: ["owner"]
- `yeager`: ["customer", "admin"]
- `mikasa`: ["customer", "owner"]
- `armin`: ["admin", "owner"]

## API

- http://javadox.com/io.jsonwebtoken/jjwt/0.4/io/jsonwebtoken/JwtParser.html#parseClaimsJwt-java.lang.String-)

## Files

### jwt

#### JWT: service

|                 | `verifyAndParseToken`                                        | `checkProfile` | `login`            |
| --------------- | ------------------------------------------------------------ | -------------- | ------------------ |
| HTTP method     | GET                                                          | GET            | POST               |
| API             | /v1/token                                                    | /v1/profile    | /v1/login          |
| `RequestHeader` | `token`                                                      | `token`        | -                  |
| `RequestBody`   | -                                                            | -              | `loginRequestBody` |
| `PathVariable`  | -                                                            | -              | -                  |
| return          | if parse token successfully return `UserDetail`, else return 401 |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |
|                 |                                                              |                |                    |

### security

#### WebSecurityConfig



### Issue Log

