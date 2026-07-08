# Customer Manager 

A simple Android app for managing customers.

## Features

- Three screens: customers list, customer details, add/edit customer.
- Toolbar and title on every screen.
- Edge-to-edge enabled on the main screen.
- Portrait and landscape are supported by default Android configuration changes.
- Customer list supports two fixed object types: `REGULAR` and `VIP`.
- Customer fields: type, name, email, phone.
- CRUD:
  - Create customer.
  - Read customer list and details.
  - Update customer.
  - Delete customer.
- Persistent storage with Room database.
- List/table display mode toggle on the main screen.
  
## Tech Stack

- Language: Kotlin
- UI: XML layouts + Material Components
- Architecture: MVVM + StateFlow
- Database: Room (SQLite)
- DI: Manual dependency injection via AppContainer
- Build system: Gradle Kotlin DSL
  
## How to Open

1. Open Android Studio.
2. Choose `Open`.
3. Select the `CustomerManagerFinal` folder.
4. Wait for Gradle sync.
5. Run the app.

## Main Package

`com.example.customerlist`
