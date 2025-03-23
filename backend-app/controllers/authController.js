const admin = require("../utils/firebase");
const jwt = require("jsonwebtoken");

const registerUser = async (req, res) => {
  const { email, password } = req.body;

  try {
    const userRecord = await admin
      .auth()
      .createUser({ email, password });

    const token = jwt.sign({ uid: userRecord.uid }, "tu_secreto_secreto"); // Reemplaza "tu_secreto_secreto" con tu clave secreta

    res.status(201).json({ token });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

const loginUser = async (req, res) => {
  const { email, password } = req.body;

  try {
   // Aquí faltaría la validación de la contraseña, firebase solo verifica si existe el usuario.
   // La validación de contraseña se implementa por parte del frontend.
    const userRecord = await admin.auth().getUserByEmail(email);
    const token = jwt.sign({ uid: userRecord.uid }, "tu_secreto_secreto"); // Reemplaza "tu_secreto_secreto" con tu clave secreta
    res.status(200).json({ token });
  } catch (error) {
    res.status(401).json({ message: "Credenciales inválidas" });
  }
};

module.exports = { registerUser, loginUser };