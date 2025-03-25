const express = require("express");
const verifyToken = require("../middlewares/authMiddleware"); //  Mantén tu middleware actual (verifyToken)
const { getUser, updateUserProfile, getUserByEmail, getAllUsers } = require("../controllers/userController"); // Importa updateUserProfile

const router = express.Router();

router.get("/profile", verifyToken, getUser); //  Mantén tu ruta GET existente
router.put("/profile", verifyToken, updateUserProfile);//  Añade la nueva ruta para actualizar el perfil
router.get("/:email", getUserByEmail); //  Nueva ruta para obtener usuario por email
router.get("/", verifyToken, getAllUsers); // Nueva ruta para obtener todos los usuarios (solo admin)

module.exports = router;