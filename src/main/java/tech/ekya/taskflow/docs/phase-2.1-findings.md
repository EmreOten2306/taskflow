# Phase 2.1 – JSON Output Findings

## 1. PasswordHash Exposure

### Test

`GET /api/users`

### Result

The API response returned the `passwordHash` field for users.

### Problem

`passwordHash` is sensitive information and should not be exposed in the API response.

### Conclusion

The current implementation returns the `AppUser` entity directly. Because of this, all entity fields can be included in the JSON response.

### Planned Solution

Use DTOs to control which user fields are returned by the API.

---

## 2. Lazy Proxy Problem

### Test

`GET /api/projects`

### Result

The request failed with a `LazyInitializationException`.

### Problem

The `owner` field in `Project` is configured with `FetchType.LAZY`.

This means the owner is not loaded immediately when the project is loaded. It is loaded only when it is needed.

When Jackson tried to convert the project to JSON, it tried to access the lazy `owner` after the Hibernate session had already been closed.

### Error

`LazyInitializationException: Could not initialize proxy - no session`

### Conclusion

Returning the entity directly as JSON can cause problems with lazy-loaded relationships.

### Planned Solution

Use DTOs and explicitly control which project and owner information should be returned.
