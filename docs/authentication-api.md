# Authentication API: Login and JWT Token Handling

The Authentication API allows registered users to log in and receive a JSON Web Token, or JWT, that identifies them in later API requests.

> **Status:** Planned
> Password verification exists, but real JWT generation and validation have not yet been implemented. The current `"temporary-token"` response must not be treated as valid authentication.

---

## Login Flow

The intended login flow is:

```text
User submits email and password
              ↓
Backend finds the user by email
              ↓
Backend compares the submitted password
with the stored BCrypt password hash
              ↓
Backend generates a signed JWT
              ↓
JWT is returned to the client
              ↓
Client includes the JWT in protected requests
              ↓
Backend validates the JWT and identifies the user
```

The raw password should never be stored in the database.

During registration, the password is hashed using BCrypt. During login, the submitted password is compared with the stored hash.

---

# Login Endpoint

```http
POST /api/auth/login
```

Authenticates a registered user using an email address and password.

## Authentication required

No. This endpoint must remain publicly accessible so that users can log in.

## Request body

```json
{
  "email": "user@example.com",
  "password": "ExamplePassword123"
}
```

## Request fields

| Field      | Type     | Required | Description                                    |
| ---------- | -------- | -------: | ---------------------------------------------- |
| `email`    | `String` |      Yes | Email address associated with the account      |
| `password` | `String` |      Yes | User's plaintext password submitted over HTTPS |

## Validation rules

Recommended validation rules include:

* `email` must not be blank.
* `email` must have a valid email format.
* `password` must not be blank.
* Input lengths should be limited.
* Invalid login attempts should return the same general response whether the email or password is incorrect.

The API should not reveal whether a particular email address exists.

---

# Successful Login Response

A successful login should return:

```http
200 OK
```

Example response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 12,
    "email": "user@example.com"
  }
}
```

## Response fields

| Field         | Type     | Description                                                  |
| ------------- | -------- | ------------------------------------------------------------ |
| `accessToken` | `String` | Signed JWT used to access protected endpoints                |
| `tokenType`   | `String` | Token scheme, normally `Bearer`                              |
| `expiresIn`   | `Long`   | Number of seconds until the token expires                    |
| `user`        | `Object` | Basic non-sensitive information about the authenticated user |

The response must never include:

* The user's raw password
* The stored password hash
* The JWT signing secret
* Internal security configuration

---

# JWT Structure

A JWT usually contains three encoded sections:

```text
header.payload.signature
```

Example:

```text
eyJhbGciOiJIUzI1NiJ9
.
eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIn0
.
signed-value
```

## Header

The header describes the token type and signing algorithm.

Conceptual example:

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

## Payload

The payload contains claims about the authenticated user and token.

Example payload:

```json
{
  "sub": "user@example.com",
  "userId": 12,
  "iat": 1785086400,
  "exp": 1785090000
}
```

Common claims include:

| Claim    | Meaning                                       |
| -------- | --------------------------------------------- |
| `sub`    | Subject represented by the token              |
| `userId` | Internal identifier of the authenticated user |
| `iat`    | Time when the token was issued                |
| `exp`    | Time when the token expires                   |

The payload is encoded, not encrypted. Sensitive values such as passwords must never be placed inside it.

## Signature

The signature allows the backend to detect whether the token was changed.

It is generated using:

* The encoded header
* The encoded payload
* A private signing secret or private key

A client may read the payload, but it cannot create a valid modified token without the signing secret.

---

# Sending the JWT

After login, the client sends the token using the HTTP `Authorization` header:

```http
Authorization: Bearer <access-token>
```

Example:

```http
GET /api/pantry
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

The word `Bearer` must appear before the token.

---

# Protected Request Flow

For a protected endpoint, the backend should:

1. Read the `Authorization` header.
2. Confirm that it begins with `Bearer `.
3. Extract the JWT.
4. Verify the token signature.
5. Verify that the token has not expired.
6. Extract the authenticated user's identity.
7. Load the user if necessary.
8. place the authenticated identity in the Spring Security context.
9. Continue the request only if validation succeeds.

Conceptually:

```text
Authorization header
        ↓
JWT authentication filter
        ↓
Signature and expiration validation
        ↓
User identity extracted
        ↓
SecurityContext updated
        ↓
Controller and service execute
```

---

# Using the Authenticated User

Protected services should obtain the current user from Spring Security.

The client should not decide which user owns a pantry or grocery item.

Unsafe request design:

```json
{
  "name": "Rice",
  "quantity": 2,
  "unit": "kg",
  "userId": 25
}
```

A malicious client could replace `25` with another user's ID.

Safer request:

```json
{
  "name": "Rice",
  "quantity": 2,
  "unit": "kg"
}
```

The backend should determine the owner from the JWT:

```text
JWT identifies user 25
        ↓
Backend loads user 25
        ↓
New pantry item is assigned to user 25
```

This design protects pantry data, grocery items, recommendations, saved recipes, and other user-specific resources.

---

# Token Expiration

JWT access tokens should have a limited lifetime.

Example:

```text
Token issued: 10:00
Token expires: 11:00
```

After expiration, protected requests should return:

```http
401 Unauthorized
```

Example response:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "The access token has expired",
  "path": "/api/pantry"
}
```

Token expiration limits the damage if a token is stolen.

---

# Invalid Login Response

Incorrect email or password:

```http
401 Unauthorized
```

Example:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password",
  "path": "/api/auth/login"
}
```

The API should use the same message for both:

* An email that does not exist
* An incorrect password

This avoids revealing registered email addresses.

---

# Missing Token Response

When a protected endpoint is called without a token:

```http
401 Unauthorized
```

Example:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Authentication is required",
  "path": "/api/pantry"
}
```

---

# Invalid Token Response

When the token is malformed or has an invalid signature:

```http
401 Unauthorized
```

Example:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "The access token is invalid",
  "path": "/api/recommendations"
}
```

---

# Authorization Failure

Authentication and authorization are different:

* **Authentication:** Who is the user?
* **Authorization:** Is that user permitted to perform this action?

For example, a valid normal user may be authenticated but not permitted to delete recipes.

The API should return:

```http
403 Forbidden
```

Example:

```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "You do not have permission to delete recipes",
  "path": "/api/recipes/12"
}
```

---

# Public and Protected Endpoints

A suggested security policy is:

## Public endpoints

```http
POST /api/auth/register
POST /api/auth/login
GET  /api/health
GET  /api/recipes
GET  /api/recipes/{id}
```

## Authenticated-user endpoints

```http
GET    /api/pantry
POST   /api/pantry
PUT    /api/pantry/{id}
DELETE /api/pantry/{id}

GET    /api/grocery
POST   /api/grocery
PUT    /api/grocery/{id}
DELETE /api/grocery/{id}

GET    /api/recommendations
POST   /api/grocery/from-recipe/{recipeId}
```

## Administrative endpoints

```http
POST   /api/recipes
PUT    /api/recipes/{id}
DELETE /api/recipes/{id}
```

---

# JWT Secret Configuration

The JWT signing secret must not be written directly in committed source files.

Unsafe:

```properties
jwt.secret=myRealSecretKey
```

Recommended:

```properties
jwt.secret=${JWT_SECRET}
```

The actual secret should be stored as an environment variable:

```powershell
$env:JWT_SECRET="a-long-random-secret-value"
```

The same secret is used to verify tokens created by the application.

Changing the signing secret invalidates tokens signed using the previous secret.

---

# Client-Side Token Handling

The frontend must attach the token to protected requests.

Conceptual JavaScript example:

```javascript
fetch("/api/pantry", {
  headers: {
    Authorization: `Bearer ${accessToken}`
  }
});
```

The frontend should:

* Send tokens only over HTTPS in production.
* Avoid printing tokens in logs.
* Remove local authentication state when logging out.
* Handle `401 Unauthorized` responses.
* Redirect the user to login when authentication expires.

The final storage approach should be chosen carefully. Browser-accessible storage can be exposed through cross-site scripting, while cookie-based storage requires appropriate CSRF protections.

---

# Logout

With a simple stateless JWT design, logout commonly means deleting the token from the client.

```text
User selects logout
        ↓
Frontend removes the stored token
        ↓
Future protected requests no longer include it
```

The token may remain technically valid until it expires unless the backend implements token revocation.

For an initial MVP, short-lived access tokens and client-side removal may be sufficient. More advanced revocation or refresh-token handling can be introduced later.

---

# Current Implementation Gap

The current authentication implementation:

* Registers users
* Hashes passwords with BCrypt
* Verifies login passwords
* Returns the literal value `"temporary-token"`
* Does not generate a signed JWT
* Does not validate tokens on later requests
* Does not place a user in the Spring Security context
* Allows every endpoint through the existing security configuration

Therefore, this response is not secure authentication:

```json
{
  "token": "temporary-token"
}
```

The temporary token identifies no user and cannot prove that a request is authenticated.

It should be removed or replaced when JWT authentication is implemented.

---

# Recommended Implementation Order

1. Add JWT configuration using environment variables.
2. Define an `AuthResponse` DTO.
3. Generate a signed token after successful login.
4. Add token expiration.
5. Create a JWT validation service.
6. Create a request filter that reads bearer tokens.
7. Load the authenticated user into Spring Security.
8. Protect user-specific and mutating endpoints.
9. Derive resource ownership from the authenticated user.
10. Add authentication and authorization tests.

---

# Required Tests

The login and JWT implementation should test:
* Correct email and password return a token.
* Incorrect password returns `401`.
* Unknown email returns `401`.
* Blank or malformed input returns `400`.
* The returned token contains the correct user identity.
* A valid token permits access to a protected endpoint.
* A missing token returns `401`.
* A malformed token returns `401`.
* A modified token returns `401`.
* An expired token returns `401`.
* One user cannot access another user's pantry.
* A regular user cannot perform an administrator-only operation.
* Passwords and password hashes never appear in API responses.
.
---
# Security Notes

* JWT payloads are readable and must not contain secrets.
* JWTs must be signed using a strong secret or private key.
* Signing secrets must be stored outside Git.
* Passwords must remain BCrypt-hashed.
* Production traffic must use HTTPS.
* Tokens should have expiration times.
* Authentication errors should not expose account details.
* Protected data must always be scoped to the authenticated user.
* CORS must allow only trusted frontend origins.
* CSRF configuration must match the chosen token-storage design.

---

# API Status Summary

| Capability                      | Status          |
| ------------------------------- | --------------- |
| User registration               | Implemented     |
| BCrypt password hashing         | Implemented     |
| Password verification           | Implemented     |
| Real JWT generation             | Not implemented |
| JWT expiration                  | Not implemented |
| Bearer-token validation         | Not implemented |
| Security-context authentication | Not implemented |
| Protected endpoints             | Not implemented |
| User ownership enforcement      | Not implemented |
| Authorization roles             | Not implemented |
