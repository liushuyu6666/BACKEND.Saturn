### JWT
1. jwt contains username and role for convenience


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
2. retrieveUserById: token, userId
   - design for admin to check
   - same as retrieveUser, but just need userId
   - password is hidden
   - for users who just want to check his own profile, by using checkProfile in JwtCont
3. retrieveUser: token, username, role
   - design for admin to check detail information
   - retrieve one user by its name and role
   - password is hidden
   - need to check the token, only the role=admin could retrieve user
   - for users who just want to check his own profile, by using checkProfile in JwtController
4. listUser: token,
   - list all users whose role=customer or role=owner
   - need to check the token, only the role=admin could retrieve user
5. updateUser: token
   - verify token
   - for all customer and owner
   - password can be changed
   - find the user info
   - update and save

#### ShopController:
1. createShop: token, shop
   - verify token to confirm that the role=owner
   - since username is added from the frontend, in the backend it need to be convert into _id.
   - check if login's _id is in the ownersList, if not, add it automatically
2. retrieveShop: token, shopId
   - check token, if role = customer or admin, no permission need
   - check token, if role = owner, only could check its own shop
   - ownersId need to be convert to ownersName
3. listShop: token,
   - check token, if role = customer or admin, no permission need
   - check token, if role = owner, only could check its own shop
   - ownersId need to be convert to ownersName
4. updateShop: token, shopId
   - verify token to confirm that the role=owner
   - get the username
   - modification, even the owner can be changed
   - notice: if delete all owners, the shop can't be modified, so from the front end, this action need to be restricted
5. deleteShop: token, shopId
   - verify token to confirm that the role=owner
   - get the username
   - delete

#### DishController:
1. createDish: token, shopId, dish
   - verify token to confirm that the role=owner
   - verify the authorization of the owner under this shop
   - create it
2. retrieveDish: token, shopId, dishId
   - if role=owner authorization is needed, if role = customer or admin, needn't to check
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

#### Order:
1. createOrder: token
   - verify token to confirm that the role=customer
   - create
   - notice: can't make sure that the shopId and dishId are existed or valid, need to check when use.
2. listOrderDetail: token
   - verify token to confirm that the role=customer
   - list Order under this customer

