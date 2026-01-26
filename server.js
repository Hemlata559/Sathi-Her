const express = require("express");
const app = express();

app.use(express.json());

/* ---------------- FAKE DATABASE ---------------- */

const users = [
  {
    id: 1,
    name: "Aisha",
    verified: true,
    route: "A-C",
    mode: "metro",
    available: true,
    trustScore: 4.8
  },
  {
    id: 2,
    name: "Neha",
    verified: true,
    route: "A-B",
    mode: "metro",
    available: true,
    trustScore: 4.5
  },
  {
    id: 3,
    name: "Riya",
    verified: false,
    route: "A-B",
    mode: "bus",
    available: true,
    trustScore: 4.2
  }
];

/* ---------------- FEATURE 6 & 7 ---------------- */

app.get("/match", (req, res) => {
  const { route, mode } = req.query;

  const matches = users
    .filter(user =>
      user.verified &&
      user.available &&
      user.route === route &&
      user.mode === mode
    )
    .map(user => ({
      name: user.name,
      routeOverlap: "High",
      trustScore: user.trustScore
    }));

  // 👇 NEW PART (important)
  if (matches.length === 0) {
    return res.json({
      message: "No match found yet",
      matches: []
    });
  }

  res.json({
    count: matches.length,
    matches
  });
});


/* ---------------- FEATURE 9 (OTP) ---------------- */

let otpStore = {};

app.post("/send-otp", (req, res) => {
  const { userId } = req.body;

  const otp = Math.floor(1000 + Math.random() * 9000);
  otpStore[userId] = otp;

  console.log("OTP sent:", otp);

  res.json({ message: "OTP sent successfully" });
});

app.post("/verify-otp", (req, res) => {
  const { userId, otp } = req.body;

  if (otpStore[userId] == otp) {
    res.json({ success: true, message: "OTP verified. Meet confirmed." });
  } else {
    res.json({ success: false, message: "Invalid OTP" });
  }
});

/* ---------------- SERVER START ---------------- */

app.listen(3000, () => {
  console.log("Server running on port 3000");
});
