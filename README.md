### controller
#### JwtController:
1. verifyAndParseToken: token
   - verify token
   - parse the information contained in token
   - anyone can do this
2. checkProfile: token
   - verify token
   - find the login user info without password
3. login: loginRequestBody
   - authentication

#### UserController:
1. createUser: User
   - create user if role is owner or customer
   - if role = admin, block it.
   - API: register
2. retrieveUser: token, username
   - design for admin to check detail information
   - retrieve one user by its name and role
   - password is hidden
   - need to check the token, only the role=admin could retrieve user
   - for users who just want to check his own profile, by using checkProfile in JwtController
3. listUser: token,
   - list all users whose role=customer or role=owner
   - need to check the token, only the role=admin could retrieve user
4. updateUser: token
   - verify token
   - for all customer and owner
   - password can be changed
   - find the user info
   - update and save

#### shop:
1. createShop: token, shop
   - verify token to confirm that the role=owner
   - (frontend) get the username and fill in the owner automatically
   - check if username is in the ownersList, if not, add it automatically
2. retrieveShop: token, shopId
   - verify token to confirm that the role=owner
   - get the username
   - get the shop information
3. listShop: token,
   - verify token to confirm that the role=owner
   - get the username
   - get the shopsList
4. updateShop: token, shopId
   - verify token to confirm that the role=owner
   - get the username
   - modification, even the owner can be changed
5. deleteShop: token, shopId
   - verify token to confirm that the role=owner
   - get the username
   - delete

#### dishes:
1. createDish: token, shopId, dish
   - verify token to confirm that the role=owner
   - get the username and shopId
   - create it
2. retrieveDish: token, shopId, dishId
   - verify token to confirm that the role=owner
   - get the username, shopId and dishId
   - retrieve it
3. listDish: token, shopId
   - verify token to confirm that the role=owner
   - get the username and shopId
   - list it
4. updateDish: token, shopId, dishId, newDish
   - verify token to confirm that the role=owner
   - get the username, shopId and dishId
   - update
5. deleteDish: token, shopId, dishId
   - verify token to confirm that the role=owner
   - get the username, shopId and dishId
   - delete it

#### Review:
1. createReview: token, shopId, dishId, Review
   - verify token to confirm that the role=customer
   - get the orderedList to check if it contains the dishId
   - create
2. listReviewUnderDish:
   - get shopId and dishId
   - don't need to check token
3. listReviewUnderUser: token
   - verify token to confirm that the role=customer
   - get the username to list all reviews including shopname and dishname.

#### Order:
1. createOrder: token
   - verify token to confirm that the role=customer
   - create
2. listOrder: token
   - verify token to confirm that the role=customer
   - get username
   - list all order under this username

