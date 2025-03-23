const express = require("express");
const verifyToken = require("../middlewares/authMiddleware");
const { getUser } = require("../controllers/userController");

const router = express.Router();

router.get("/profile", verifyToken, getUser);

module.exports = router;