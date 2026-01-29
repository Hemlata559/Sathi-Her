# Saathi-Her – Backend Features (Matching & OTP)

This repository is part of the **Saathi-Her** team project, a women-only travel companion safety platform.

## My Contribution

I implemented the following backend features using **Node.js and Express**:

---

## Match Validation Rules

- Only **verified women users** are considered for matching
- Users must be:
  - **Verified**
  - **Available**
  - Using the **same route**
  - Using the **same mode of transport**
- Invalid or unverified users are automatically filtered out

## Coordination & OTP Authentication

- OTP is generated and sent (mocked in backend)
- OTP is stored temporarily in memory
- User must verify OTP to confirm meeting
- Ensures secure identity confirmation without sharing phone numbers

## Tech Stack Used

- Node.js
- Express.js
- In-memory data (for prototype)
- Thunder Client (API testing inside VS Code)

## API Testing

All backend APIs were tested using **Thunder Client** in VS Code to verify:

- Match filtering logic
- OTP generation
- OTP verification flow

## ▶ How to Run the Project

npm install
node server.js

The server will start at
http://localhost:3000
