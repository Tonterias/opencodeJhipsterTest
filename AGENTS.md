# Agentic Coding Guidelines for opencodetest

This is a JHipster 9.0.0 + Angular 21 + Spring Boot 3 project. Generated application with Java 21.

## Build/Lint/Test Commands

### Frontend (Angular/TypeScript)

| Command                        | Description                                    |
| ------------------------------ | ---------------------------------------------- |
| `./npmw test`                  | Run frontend unit tests (Vitest) with coverage |
| `./npmw run test:watch`        | Run tests in watch mode                        |
| `./npmw run lint`              | Run ESLint                                     |
| `./npmw run lint:fix`          | Fix ESLint issues                              |
| `./npmw run prettier:check`    | Check formatting                               |
| `./npmw run prettier:format`   | Format all files                               |
| `./npmw run webapp:build:dev`  | Build frontend (dev)                           |
| `./npmw run webapp:build:prod` | Build frontend (prod)                          |
| `./npmw run start`             | Start dev server with HMR                      |
| `./npmw run webapp:dev`        | Start Angular dev server                       |

To run a single test file, use Vitest directly:

```bash
./npmw exec vitest run src/main/webapp/app/shared/sort/sort.service.spec.ts
```

Or with watch mode on specific files:

```bash
./npmw exec vitest run --watch src/main/webapp/app/shared/sort/
```

### Backend (Java/Gradle)

| Command                                        | Description                             |
| ---------------------------------------------- | --------------------------------------- |
| `./gradlew test`                               | Run backend unit tests                  |
| `./gradlew integrationTest`                    | Run integration tests                   |
| `./gradlew backend:unit:test`                  | Run backend tests with logging silenced |
| `./gradlew checkstyleNohttp`                   | Run nohttp checkstyle                   |
| `./gradlew bootJar`                            | Build JAR                               |
| `./gradlew bootJar -x test -x integrationTest` | Build JAR skipping tests                |
| `./gradlew -Pprod clean bootJar`               | Production build                        |
| `./npmw run clean`                             | Clean all build artifacts               |

Run single backend test:

```bash
./gradlew test --tests "com.opencode.test.service.UserServiceTest"
```

### Full CI Pipeline

```bash
# Frontend CI
./npmw run ci:frontend:test

# Backend CI
./npmw run ci:backend:test

# E2E tests
./npmw run ci:e2e:prepare && ./npmw run ci:e2e:server:start && ./npmw test
```

## Code Style Guidelines

### TypeScript/JavaScript

- **Strict mode enabled** - `strict: true`, `strictNullChecks: true` in tsconfig
- **Tab size: 2** for TypeScript (see .editorconfig)
- **Print width: 140** (Prettier)
- **Single quotes** for strings
- **Arrow functions**: use `error` style (braces required even for single expressions)
- **No semicolons** (handled by Prettier/ESLint)
- **Prefer `const`** over `let`, never `var`
- **Type definitions**: prefer `type` over `interface` unless needed for extension
- **Explicit return types** on functions, except simple arrow expressions
- **No `any`** - avoid, prefer `unknown` with type guards

### Angular

- **Prefix: `jhi`** - All components use `jhi` prefix in HTML (kebab-case), directives use camelCase
- **Component selector**: element type, `jhi` prefix, kebab-case
- **Directive selector**: attribute type, `jhi` prefix, camelCase
- **Structure per file**: fields (static first, then instance), constructor, methods
- **Prefer standalone components** where applicable
- **Use inject()** function for dependency injection instead of constructor injection when appropriate
- **Templates**: prefer arrow functions in expressions, use nullish coalescing

### Imports

- External imports first (alphabetical)
- Relative imports after (grouped by path depth)
- Always use named imports, avoid `import * as X`
- Use path aliases where configured (e.g., `app/shared/...`, `environments/...`)

### Naming Conventions

| Type              | Convention                    | Example                       |
| ----------------- | ----------------------------- | ----------------------------- |
| Components        | kebab-case                    | `user-settings.component.ts`  |
| Services          | PascalCase + `Service` suffix | `UserService.ts`              |
| Directives        | PascalCase + suffix           | `HasAnyAuthorityDirective.ts` |
| Models/Interfaces | PascalCase                    | `User.model.ts`, `SortState`  |
| Spec files        | Same name + `.spec`           | `user.service.spec.ts`        |
| Variables         | camelCase                     | `userData`, `isLoading`       |
| Constants         | UPPER_SNAKE_CASE              | `MAX_RETRY_COUNT`             |
| Boolean           | prefix `is`/`has`/`should`    | `isLoggedIn`, `hasPermission` |

### Error Handling

- **RxJS**: handle errors with `.pipe(catchError(...))`, never leave floating promises
- **No `console.log`** - use `console.warn` or `console.error` only
- **Exceptions**: prefer typed errors, use `throw new Error()` with descriptive messages
- **Async**: always handle promise rejections, use `try/catch` in async functions

### Java (Backend)

- **Tab size: 4** (see .editorconfig)
- **Follow standard Java conventions**: 4-space indent, braces on same line
- **Use MapStruct** for object mapping (already configured)
- **Use JPA/Liquibase** for database (entity files in `src/main/java/.../domain/`)
- **Spring Boot**: follow Spring conventions for controllers, services, repositories

### File Organization

```
src/main/webapp/app/
├── core/           # Singleton services, interceptors, guards
├── shared/         # Reusable components, directives, pipes, utilities
├── entities/       # Domain entities (if any)
├── admin/          # Admin-only features
├── account/        # User account features
├── home/           # Home page
└── app.ts          # Main app component

src/main/java/.../
├── domain/         # JPA entities
├── repository/     # Spring Data repositories
├── service/        # Business logic
├── web.rest/       # REST controllers
├── web.vm/         # View models (DTOs)
└── web.mapper/     # MapStruct mappers
```

### HTML/Templates

- Use Angular template syntax, not deprecated forms
- Prefer `async` pipe over manual subscriptions
- Use `@if`/`@for` (Angular 17+ control flow) over `*ngIf`/`*ngFor`
- Bindings: prefer strongly-typed, avoid `any`

### Testing

- **Frontend**: Vitest + Angular Testing Library
- **Spec files**: co-located with source, same name + `.spec.ts`
- **Backend**: JUnit 5 via Gradle
- **Coverage**: target reasonable coverage, critical paths >80%
- **Mocking**: use `vi.fn()` in Vitest, `@MockBean` in Spring tests
- **Test descriptions**: clear, descriptive names describing the scenario

### Pre-commit Hooks

- Husky + lint-staged configured
- Pre-commit runs Prettier formatting automatically on staged files
- Run `./npmw run prepare` to install hooks
- Ensure all files pass linting before committing

### Additional Notes

- **Base URL**: TypeScript imports use `src/main/webapp/` as base (tsconfig.baseUrl)
- **Environment files**: `src/main/webapp/environments/` for environment-specific config
- **PWA**: Service worker disabled by default, can enable in `app.config.ts`
- **i18n**: TranslationModule configured, use `TranslateService` or `TranslatePipe`
- **Images/icons**: Font Awesome configured (`@fortawesome`)
